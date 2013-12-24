package jp.hashiwa.analyzecode.java.finder;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import jp.hashiwa.analyzecode.java.Clazz;

public class CodeFinder extends SimpleFileVisitor<Path> {

//  private final List<Clazz> allClazzes = new ArrayList<>();
//  private final CodeParser parser = new CodeParser();
  private final List<String> codeFiles = new ArrayList<String>();
  private final PathMatcher matcher;

  public CodeFinder(String pattern) {
    this.matcher = FileSystems.getDefault().getPathMatcher("glob:"+pattern);
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes atts) {
    Path name = file.getFileName();
    List<Clazz> clazzes = null;
    
    if (name != null && this.matcher.matches(name)) {
      
      String filename = file.toFile().getAbsolutePath();
      codeFiles.add(filename);
      
//      try {
//        // parse one file and get Clazz list
//        clazzes = parser.parse(file.toFile().getAbsolutePath());
//        
//        // add parsed Clazz list
//        for (Clazz clazz: clazzes) {
//          allClazzes.add(clazz);
//        }
//      } catch (FileNotFoundException | ParseException e) {
//        e.printStackTrace();
//      }
//
//      if (debug) {
//        System.out.println(file);
//        System.out.println("-----------------");
//        for (Clazz clazz: clazzes) {
//          System.out.print("  ");
//          System.out.println(clazz.getFullClassName());
//        }
//        System.out.println("-----------------");
//        System.out.println();
//      }
      
    }
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    System.err.println(exc);
    return FileVisitResult.CONTINUE;
  }
  
  public List<String> getFoundCodeFiles() {
    return codeFiles;
  }
}