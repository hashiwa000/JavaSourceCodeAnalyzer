package jp.hashiwa.analyzecode.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
  public static void main(String[] args) throws IOException {
    String rootdir;
    
//    if (args.length != 1) {
//      usage();
//    }
//    rootdir = args[1];
    
    rootdir = "e:\\MyFiles\\proc\\java\\visualvm_src\\visualvm\\core\\src";

    execute(Paths.get(rootdir), "*.java");
  }

  public static void execute(Path path, String pattern)throws IOException{
    Finder finder = new Finder(pattern);    // FileVisitor オブジェクト取得
    Files.walkFileTree(path, finder);    // 走査の開始
    finder.done();    // 結果の表示
  }

  private static void usage() {
    System.exit(-1);
  }
}
