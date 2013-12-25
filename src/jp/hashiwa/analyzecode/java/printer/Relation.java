package jp.hashiwa.analyzecode.java.printer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


class Relation {
  private static String ROOT_PREFIX      = "+--- ";
  private static String EXTEND_PREFIX    = "<--- ";
  private static String IMPLEMENT_PREFIX = "<... ";
  private static int INDENT = 2;
  
  private final String name;
  private final List<Relation> extended =
      new ArrayList<Relation>();
  private final List<Relation> implemented =
      new ArrayList<Relation>();
  
  Relation(String name) {
    this.name = name;
  }
  
  void addExtended(Relation r) {
    if (r == null) {
      throw new RuntimeException();
    }
    extended.add(r);
  }
  
  void addImplemented(Relation r) {
    if (r == null) {
      throw new RuntimeException();
    }
    implemented.add(r);
  }
  
  void printOn(PrintStream out) {
    printOn(out, ROOT_PREFIX, new Stack<Relation>());
    out.println();
  }
  
  private void printOn(PrintStream out, String prefix, Stack<Relation> stack) {
    for (Relation r: stack) {
      for (int i=0 ; i<INDENT ; i++) {
        out.print(' ');
      }
    }
    out.print(prefix);
    out.println(name);
    
    // push
    stack.push(this);
    
    // extends
    for (int i=0 ; i<extended.size() ; i++) {
      Relation r = extended.get(i);
      r.printOn(out, EXTEND_PREFIX, stack);
    }
    
    // implements
    for (int i=0 ; i<implemented.size() ; i++) {
      Relation r = implemented.get(i);
      r.printOn(out, IMPLEMENT_PREFIX, stack);
    }
    
    stack.pop();

  }
  
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Relation other = (Relation) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    return true;
  }
}