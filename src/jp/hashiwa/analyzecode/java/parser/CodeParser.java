package jp.hashiwa.analyzecode.java.parser;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.PackageDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;
import japa.parser.ast.visitor.GenericVisitorAdapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import jp.hashiwa.analyzecode.java.Clazz;
import jp.hashiwa.analyzecode.java.Header;

public class CodeParser {

  private static final boolean debug = false;

  private Stack<Clazz> stack;
  private List<Clazz> classes;
  private Header header;

  public List<Clazz> parse(String filename) throws ParseException, FileNotFoundException {
    init();

    // creates an input stream for the file to be parsed
    FileInputStream in = new FileInputStream(filename);

    CompilationUnit cu;
    try {
      // parse the file
      cu = JavaParser.parse(in);
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    CodeVisitor visitor = new CodeVisitor();
    
    // visit class
    visitor.visit(cu, null);

    return classes;
  }
  
  public void init() {
    stack = new Stack<Clazz>();
    classes = new ArrayList<Clazz>();
    header = new Header();
  }

  private class CodeVisitor extends GenericVisitorAdapter<Integer, Object> {

    public Integer visit(PackageDeclaration n, Object arg) {
      String s = n.getName().toString();
      if (debug) {
        System.out.println("package : " + s);
      }

      header.setPkg(s);

      return super.visit(n, arg);
    }

    public Integer visit(ImportDeclaration n, Object arg) {
      String s = getImported(n);
      if (debug) {
        System.out.println("import : " + s);
      }

      header.addImports(s);

      return super.visit(n, arg);
    }

    public Integer visit(ClassOrInterfaceDeclaration n, Object arg) {
      String className = n.getName();
      List<ClassOrInterfaceType> extendsClasses = n.getExtends();
      List<ClassOrInterfaceType> implementsClasses = n.getImplements();

      if (debug) {
        System.out.println("class : " + className);
        if (extendsClasses != null) {
          for (ClassOrInterfaceType t: extendsClasses) {
            System.out.println("extends : " + t.toString());
          }
        }
        if (implementsClasses != null) {
          for (ClassOrInterfaceType t: implementsClasses) {
            System.out.println("implements : " + t.toString());
          }
        }
      }

      Clazz clazz;
      if (stack.isEmpty()) {
        clazz = new Clazz(header);
      } else {
        clazz = new Clazz(stack.peek());
      }

      clazz.setClassName(className);
      if (extendsClasses != null) {
        for (ClassOrInterfaceType t: extendsClasses) {
          clazz.addSuperClass(t.toString());
        }
      }
      if (implementsClasses != null) {
        for (ClassOrInterfaceType t: implementsClasses) {
          clazz.addInterfaceClass(t.toString());
        }
      }

      if (debug) {
        System.out.println("full name : " + clazz.getFullClassName());
        System.out.println();
      }

      stack.push(clazz);

      Integer ret = super.visit(n, arg);

      classes.add(stack.pop());

      return ret;
    }
  }
  
  private static String getImported(ImportDeclaration d) {
    StringBuilder sb = new StringBuilder(d.toString());
    String IMPORT = "import";
    int start = sb.indexOf(IMPORT) + IMPORT.length();
    int end = sb.indexOf(";");

    return sb.substring(start, end).trim();
  }

  public static void main(String[] args) throws Exception {
    //    String filename = "e:\\MyFiles\\proc\\java\\visualvm_src\\visualvm\\core\\src\\com\\sun\\tools\\visualvm\\core\\datasource\\DataSourceRepository.java";
    String filename = "E:\\pleiades4.3\\workspace\\AnalyzeJavaSourceCode\\testcode\\Hoge.java";

    // creates an input stream for the file to be parsed
    FileInputStream in = new FileInputStream(filename);

    CompilationUnit cu;
    try {
      // parse the file
      cu = JavaParser.parse(in);
    } finally {
      in.close();
    }

    // visit class
    CodeParser parser = new CodeParser();
    List<Clazz> clazzes = parser.parse(filename);

    System.out.println("-----------------");
    for (Clazz clazz: clazzes) {
      System.out.print("  ");
      System.out.println(clazz.getFullClassName());
    }
    System.out.println("-----------------");
  }
}
