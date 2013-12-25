package jp.hashiwa.analyzecode.java.printer;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.hashiwa.analyzecode.java.Clazz;

public class RelationPrinter {

  private List<Relation> roots;
  
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
  
  private void init(List<Clazz> clazzes) {
    Map<String, Relation> all =
        new HashMap<String, Relation>();
    roots = new ArrayList<Relation>();
    
    for (Clazz c: clazzes) {
      String name = c.getFullClassName();
      Relation relation = new Relation(name);
      all.put(name, relation);
      roots.add(relation);
    }
    
    for (Clazz clazz: clazzes) {
      String name = clazz.getFullClassName();
      
      Relation relation = all.get(name);
      
      for (String c: clazz.getSuperClasses()) {
        Relation r = all.get(c);
        
        if (r == null) {
          r = new Relation(c);
          all.put(c, r);
        }
        
        relation.addExtended(r);
        
        if (roots.contains(r)) {
          roots.remove(r);
        }
      }
      
      for (String c: clazz.getInterfaceClasses()) {
        Relation r = all.get(c);
        
        if (r == null) {
          r = new Relation(c);
          all.put(c, r);
        }
        
        relation.addImplemented(r);
        
        if (roots.contains(r)) {
          roots.remove(r);
        }
      }
    }
  }
  
}
