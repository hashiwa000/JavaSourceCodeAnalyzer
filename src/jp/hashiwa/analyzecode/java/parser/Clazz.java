package jp.hashiwa.analyzecode.java.parser;

import java.util.ArrayList;
import java.util.List;

public class Clazz {

  private final Header header;
  private final Clazz superPkg;
  private String className;
  private List<String> superClasses = new ArrayList<>();
  private List<String> interfaceClasses = new ArrayList<>();
  
  public Clazz(Header header) {
    this.header = header;
    this.superPkg = null;
  }
  
  public Clazz(Clazz superPkg) {
    this.header = null;
    this.superPkg = superPkg;
  }
  
  public String getClassName() {
    return className;
  }

  public void setClassName(String clazz) {
    this.className = clazz;
  }

  public List<String> getSuperClasses() {
    return superClasses;
  }

  public void setSuperClasses(List<String> superClasses) {
    this.superClasses = superClasses;
  }
  
  public void addSuperClass(String superClass) {
    this.superClasses.add(superClass);
  }

  public List<String> getInterfaceClasses() {
    return interfaceClasses;
  }

  public void setInterfaceClasses(List<String> interfaceClasses) {
    this.interfaceClasses = interfaceClasses;
  }
  
  public void addInterfaceClass(String interfaceClass) {
    this.interfaceClasses.add(interfaceClass);
  }

  public boolean hasInterface() {
    return interfaceClasses.isEmpty();
  }
  public boolean hasSuperClass() {
    return superClasses.isEmpty();
  }
  
  
  public String getFullClassName() {
    return prefixOfFullClassName() + className;
  }
  
  private String prefixOfFullClassName() {
    StringBuilder sb = new StringBuilder();
    
    if (header != null) {
      
      String pkg = header.getPkg();
      if (pkg!=null && !pkg.isEmpty()) {
        sb.append(pkg);
        sb.append('.');
        
      } else {
        // empty
      }
      
    } else if (superPkg != null) {
      
      sb.append(superPkg.getFullClassName());
      sb.append('.');
      
    } else {
      throw new Error("fatal error");
    }
    
    return sb.toString();
  }
}
