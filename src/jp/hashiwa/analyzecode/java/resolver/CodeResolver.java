package jp.hashiwa.analyzecode.java.resolver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

import jp.hashiwa.analyzecode.java.Clazz;

public class CodeResolver {
  private static final String JAVA_LANG = "java.lang.";
  private static final boolean debug = false;

  private List<Clazz> clazzes;
  private final ClassLoader loader;
  
  public CodeResolver(List<String> libraryFiles) {
    if (libraryFiles!=null && !libraryFiles.isEmpty()) {
      URL[] urls = new URL[libraryFiles.size()];
      for (int i=0 ; i<urls.length ; i++) {
        String lib = libraryFiles.get(i);
        try {
          urls[i] = new File(lib).toURI().toURL();
        } catch (MalformedURLException e) {
          e.printStackTrace();
        }
      }
      loader = URLClassLoader.newInstance(
          new URL[]{ });
    } else {
      loader = null;
    }
  }
  
  public List<Clazz> resolve(List<Clazz> clazzes) {
    this.clazzes = clazzes;
    
    for (Clazz c: this.clazzes) {
      resolve(c);
    }
    
    return this.clazzes;
  }
  
  private void resolve(Clazz clazz) {
    
    List<String> superClasses = clazz.getSuperClasses();
    for (int i=0 ; i<superClasses.size() ; i++) {
      String name = superClasses.get(i);
      String full = findFullName(clazz, name);
      
      if (debug) {
        System.out.println("** " + name + " --> " + full );
      }
      
      superClasses.set(i, full);
    }
    
    List<String> implementedClasses = clazz.getInterfaceClasses();
    for (int i=0 ; i<implementedClasses.size() ; i++) {
      String name = implementedClasses.get(i);
      String full = findFullName(clazz, name);
      
      if (debug) {
        System.out.println("** " + name + " --> " + full );
      }
      
      implementedClasses.set(i, full);
    }
    
  }
  
  private String findFullName(Clazz clazz, String targetClassName) {
    String resolved;
    
    if (debug) {
      if (targetClassName.equals("Presenter.Menu")) {
        int i=0;
        i = i+1;
      }
    }
    
    // find class of raw class name
    resolved = findFullName(targetClassName);
    if (resolved != null) return resolved;
    
    // find from classes in declared package
    //
    // ---
    // package abc.def;
    // ...
    // ... AAA ...
    // ---
    //
    // ==> abc.def.AAA
    resolved = findFullName(
        clazz.getDeclaredPkg() + '.' + targetClassName);
    if (resolved != null) return resolved;
    
    // find from imported classes
    for (String imported: clazz.getImports()) {
      if (0 <= imported.indexOf('*')) {
        String full = imported.replace("*", targetClassName);
        resolved = findFullName(full);
        if (resolved != null) return resolved;
      } else {
        if (imported.endsWith(targetClassName)) {
          return imported;
        }
      }
    }
    
    // find java.lang.<class name>
    resolved = findFullName(JAVA_LANG + targetClassName);
    if (resolved != null) return resolved;
    
    System.out.println("Error: " + targetClassName);
    return null;
    
  }
  
  /**
   * find class from
   * - classes in given source files
   * - libraries of running app
   * - given libraries
   * @param name class or interface name
   */
  private String findFullName(String name) {
    // if found in source java codes
    // return raw name.
    for (Clazz c: clazzes) {
      String fullname = c.getFullClassName();
      if (fullname.equals(name)) {
        return name;
      }
    }
    
    if (loader != null) {
      try {
        loader.loadClass(name);
        return name;
      } catch (ClassNotFoundException e) {}
    }
    
    try {
      getClass().getClassLoader().loadClass(name);
      return name;
    } catch (ClassNotFoundException e) {}
    
    // not found
    return null;
    
    
  }
}
