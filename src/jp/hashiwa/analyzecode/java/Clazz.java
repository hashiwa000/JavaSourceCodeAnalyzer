package jp.hashiwa.analyzecode.java;

import java.util.ArrayList;
import java.util.List;

public class Clazz {

  private final Header header;
  private final Clazz superPkg;
  private String className;
  private List<String> superClasses = new ArrayList<String>();
  private List<String> interfaceClasses = new ArrayList<String>();
  private String cachedFullClassName = null;
  
  public Clazz(Header header) {
    this.header = header;
    this.superPkg = null;
    cachedFullClassName = null;
  }
  
  public Clazz(Clazz superPkg) {
    this.header = null;
    this.superPkg = superPkg;
    cachedFullClassName = null;
  }
  
  public String getClassName() {
    return className;
  }

  public void setClassName(String clazz) {
    this.className = removeGenerics(clazz);
    cachedFullClassName = null;
  }
  
  public List<String> getImports() {
    return getHeader().getImports();
  }
  
  public String getDeclaredPkg() {
    return getHeader().getPkg();
  }

  public List<String> getSuperClasses() {
    return superClasses;
  }

  public void setSuperClasses(List<String> superClasses) {
    this.superClasses = superClasses;
    for (int i=0 ; i<this.superClasses.size() ; i++) {
      String classname = this.superClasses.get(i);
      this.superClasses.set(i, removeGenerics(classname));
    }
  }
  
  public void addSuperClass(String superClass) {
    this.superClasses.add(removeGenerics(superClass));
  }

  public List<String> getInterfaceClasses() {
    return interfaceClasses;
  }

  public void setInterfaceClasses(List<String> interfaceClasses) {
    this.interfaceClasses = interfaceClasses;
    for (int i=0 ; i<this.interfaceClasses.size() ; i++) {
      String classname = this.interfaceClasses.get(i);
      this.interfaceClasses.set(i, removeGenerics(classname));
    }
  }
  
  public void addInterfaceClass(String interfaceClass) {
    this.interfaceClasses.add(removeGenerics(interfaceClass));
  }

  public boolean hasInterface() {
    return interfaceClasses.isEmpty();
  }
  public boolean hasSuperClass() {
    return superClasses.isEmpty();
  }
  
  
  public String getFullClassName() {
    if (cachedFullClassName == null) {
      cachedFullClassName = prefixOfFullClassName() + className;
    }
    return cachedFullClassName;
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
  
  private Header getHeader() {
    Clazz c = this;
    while (c.header == null) {
      c = c.superPkg;
    }
    return c.header;
  }
  
  private static String removeGenerics(String classname) {
    int index = classname.indexOf('<');
    
    if (index < 0) return classname;
    
    return classname.substring(0, index);
  }
  
}
