package jp.hashiwa.analyzecode.java;

import japa.parser.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import jp.hashiwa.analyzecode.java.finder.CodeFinder;
import jp.hashiwa.analyzecode.java.parser.CodeParser;
import jp.hashiwa.analyzecode.java.resolver.CodeResolver;

public class Main {
  private static final boolean debug = true; //false;
  private static Arguments arg;
  
  public static void main(String[] args) throws IOException {
//    String rootdir;
//    rootdir = "e:\\MyFiles\\proc\\java\\visualvm_src\\visualvm\\core\\src";
//    
//    execute(Paths.get(rootdir));
    
    arg = new Arguments(args);
    
    execute(Paths.get(arg.getSourceDir()), 
        getLibraryFiles(Paths.get(arg.getLibraryDir())));
  }

  private static void execute(Path root, List<String> libraryFiles) throws IOException {
    CodeFinder finder = new CodeFinder("*.java");
    CodeParser parser = new CodeParser();
    CodeResolver resolver = new CodeResolver(libraryFiles);
    
    List<String> codeFiles;
    List<Clazz> clazzes = new ArrayList<Clazz>();
    
    // found java codes
    Files.walkFileTree(root, finder);
    codeFiles = finder.getFoundCodeFiles();
    
    // parse java codes
    for (String f: codeFiles) {
      try {
        
        List<Clazz> tmpClazzes = parser.parse(f);
        for (Clazz c: tmpClazzes) {
          clazzes.add(c);
        }
        
        if (debug) {
          System.out.println(f);
          System.out.println("-----------------");
          for (Clazz c: tmpClazzes) {
            System.out.print("  ");
            System.out.println(c.getFullClassName());
          }
          System.out.println("-----------------");
          System.out.println();
        }
        
      } catch (ParseException e) {
        Exception e1 = new Exception("parse error: " + f, e);
        e1.printStackTrace();
      }
    }
    
    // resolve java codes
    resolver.resolve(clazzes);
  }
  
  private static List<String> getLibraryFiles(Path root) {
    List<String> founds = null;
    CodeFinder finder = new CodeFinder("*.jar");
    try {
      Files.walkFileTree(root, finder);
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
    
    founds = finder.getFoundCodeFiles();
    
    if (debug) {
      System.out.println("------------------");
      for (String f: founds) {
        System.out.println("JAR: " + f);
      }
      System.out.println("------------------");
    }
    
    return founds;
  }

  private static void usage() {
    System.exit(-1);
  }
}
