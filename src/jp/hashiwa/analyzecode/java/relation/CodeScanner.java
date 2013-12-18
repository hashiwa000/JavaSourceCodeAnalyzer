package jp.hashiwa.analyzecode.java.relation;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CodeScanner {
  private static final String PACKAGE_PREFIX = "package ";
  
  private String readLine;
  
  public RelationPair scan(Path filename) throws FileNotFoundException {
    BufferedReader br = new BufferedReader(
        new FileReader(filename.toFile()));
    
    String thisPkgs = getThisPkgs(br);
    if (thisPkgs == null) {
      throw new Error("pkg is not found: " + filename);
    }
    
    List<String> importedPkgs = getImportedPkgs(br);
  }
  
  private String getThisPkgs(BufferedReader br) throws IOException {
    String pkg = null;
    
    while ((readLine=br.readLine()) != null) {
      pkg = readLine.trim();
      
      if (pkg.startsWith(PACKAGE_PREFIX)) {
        int startIndex = PACKAGE_PREFIX.length();
        int endIndex = readLine.indexOf(';');
        
        pkg = pkg.substring(startIndex, endIndex);
        pkg = pkg.trim();
      }
    }
    
    return pkg;
  }
  
  private List<String> getImportedPkgs(BufferedReader br) throws IOException {
    List<String> importedPkgs = new ArrayList<>();
    String 
    
    while ((readLine=br.readLine()) != null) {
      pkg = readLine.trim();
      
      if (pkg.startsWith(PACKAGE_PREFIX)) {
        int startIndex = PACKAGE_PREFIX.length();
        int endIndex = readLine.indexOf(';');
        
        pkg = pkg.substring(startIndex, endIndex);
        pkg = pkg.trim();
      }
    }
    
    return pkg;
  }
}
