package jp.hashiwa.analyzecode.java.relation;

public class RelationPair {
  private final String superClass, subClass;
  private final RelationType type;
  
  public RelationPair(String superClass, 
      RelationType type, String subClass) {
    this.superClass = superClass;
    this.subClass = subClass;
    this.type = type;
  }
  
  public RelationPair(String superClass, 
      String typeName, String subClass) {
    this(superClass, valueOf(typeName), subClass);
  }
  
  public String superClass() {
    return superClass;
  }
  public String subClass() {
    return subClass;
  }
  public RelationType type() {
    return type;
  }
  
  public static RelationType valueOf(String typeName) {
    if (typeName == null) return null;
    
    switch (typeName.toLowerCase()) {
    case "extends":
      return RelationType.EXTENDS;

    case "implements":
      return RelationType.IMPLEMENTS;
      
    }
    
    return null;
  }
  
  public static enum RelationType {
    EXTENDS, IMPLEMENTS;
    
  }
}
