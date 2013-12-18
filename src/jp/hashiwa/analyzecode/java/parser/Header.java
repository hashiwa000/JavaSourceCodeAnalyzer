package jp.hashiwa.analyzecode.java.parser;

import java.util.ArrayList;
import java.util.List;

public class Header {
  private String pkg;
  private List<String> imports = new ArrayList<>();

  public String getPkg() {
    return pkg;
  }
  
  public void setPkg(String pkg) {
    this.pkg = pkg;
  }
  
  public List<String> getImports() {
    return imports;
  }
  
  public void setImports(List<String> imports) {
    this.imports = imports;
  }
  
  public void addImports(String importClass) {
    this.imports.add(importClass);
  }
}
