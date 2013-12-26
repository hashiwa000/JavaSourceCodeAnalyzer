package jp.hashiwa.analyzecode.java.printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.hashiwa.analyzecode.java.Clazz;

public class UpwardRelationPrinter extends RelationPrinter {

  @Override
  protected void init(List<Clazz> clazzes) {
    Map<String, Relation> all =
        new HashMap<String, Relation>();
    roots = new ArrayList<Relation>();
    
    for (Clazz c: clazzes) {
      String name = c.getFullClassName();
      Relation relation = new UpwardRelation(name);
      all.put(name, relation);
      roots.add(relation);
    }
    
    for (Clazz subClass: clazzes) {
      String subName = subClass.getFullClassName();
      
      Relation relation = all.get(subName);
      
      for (String superclass: subClass.getSuperClasses()) {
        Relation superRelation = all.get(superclass);
        
        if (superRelation == null) {
          superRelation = new UpwardRelation(superclass);
          all.put(superclass, superRelation);
        }
        
        relation.addExtended(superRelation);
        
        if (roots.contains(superRelation)) {
          roots.remove(superRelation);
        }
      }
      
      for (String c: subClass.getInterfaceClasses()) {
        Relation r = all.get(c);
        
        if (r == null) {
          r = new UpwardRelation(c);
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
