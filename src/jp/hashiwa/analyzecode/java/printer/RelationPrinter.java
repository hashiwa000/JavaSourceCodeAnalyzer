package jp.hashiwa.analyzecode.java.printer;

import java.io.PrintStream;
import java.util.List;

import jp.hashiwa.analyzecode.java.Clazz;

public abstract class RelationPrinter {

  protected List<Relation> roots;
  
  /**
   * 'out' is not freed in this class.
   * @param out
   * @param clazzes
   */
  public void printOn(PrintStream out, List<Clazz> clazzes) {
    init(clazzes);
    
    for (Relation r: roots) {
      r.printOn(out);
    }
  }
  
  protected abstract void init(List<Clazz> clazzes);
}
