package jp.hashiwa.analyzecode.java.printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.hashiwa.analyzecode.java.Clazz;

public class DownwardRelationPrinter extends RelationPrinter {

  @Override
  protected void init(List<Clazz> clazzes) {
    Map<String, Relation> all =
        new HashMap<String, Relation>();
    roots = new ArrayList<Relation>();
    
    for (Clazz c: clazzes) {
      String name = c.getFullClassName();
      Relation relation = new DownwardRelation(name);
      all.put(name, relation);
      roots.add(relation);
    }
    
    for (Clazz subClass: clazzes) {
      String subName = subClass.getFullClassName();
      Relation subRelation = all.get(subName);
      
      if (subRelation == null) {
        subRelation = new DownwardRelation(subName);
        all.put(subName, subRelation);
      }
      
      for (String superName: subClass.getSuperClasses()) {
        Relation superRelation = all.get(superName);
        
        if (superRelation == null) {
          superRelation = new DownwardRelation(superName);
          all.put(superName, superRelation);
        }
        
        superRelation.addExtended(subRelation);
        
        if (roots.contains(subRelation)) {
          roots.remove(subRelation);
        }
      }
      
      for (String superName: subClass.getInterfaceClasses()) {
        Relation superRelation = all.get(superName);
        
        if (superRelation == null) {
          superRelation = new DownwardRelation(superName);
          all.put(superName, superRelation);
        }
        
        superRelation.addImplemented(subRelation);
        
        if (roots.contains(subRelation)) {
          roots.remove(subRelation);
        }
      }
    }
  }


}
