package jp.hashiwa.analyzecode.java;

import japa.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

import jp.hashiwa.analyzecode.java.parser.CodeParser;
import jp.hashiwa.analyzecode.java.relation.CodeScanner;
import jp.hashiwa.analyzecode.java.relation.RelationPair;

public class Finder extends SimpleFileVisitor<Path> {

  private final PathMatcher matcher;
  private int numMatches = 0;
  private final boolean debug = true; //false;
  private final CodeScanner scanner = new CodeScanner();
  
  List<RelationPair> relations = new ArrayList<>();

  Finder(String pattern) {
    this.matcher = FileSystems.getDefault().getPathMatcher("glob:"+pattern);
  }

  @Override
  public FileVisitResult visitFile(Path file, BasicFileAttributes atts) {
    Path name = file.getFileName();
    if (name != null && this.matcher.matches(name)) {
      this.numMatches++;
      
      if (debug) {
        System.out.println(file);
        if (file.toString().contains("DataSourceAction.java")) {
          int i = 1;
          i = i + 1;
        }
      }
      
//      try {
//        
//        RelationPair pair = scanner.scan(name);
//        if (pair != null) relations.add(pair);
//        
//      } catch(FileNotFoundException e) {
//        e.printStackTrace();
//      }
      
      CodeParser parser = new CodeParser();
      try {
        parser.parse(file.toFile().getAbsolutePath());
        parser.print();
      } catch (FileNotFoundException | ParseException e) {
        e.printStackTrace();
      }

    }
    return FileVisitResult.CONTINUE;
  }

  @Override
  public FileVisitResult visitFileFailed(Path file, IOException exc) {
    System.err.println(exc);
    return FileVisitResult.CONTINUE;
  }
  
  public int getMatchedFileNum() {
    return numMatches;
  }
  
  public void done() {
    System.out.println(relations);
  }
}