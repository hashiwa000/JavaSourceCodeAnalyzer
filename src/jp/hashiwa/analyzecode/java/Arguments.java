package jp.hashiwa.analyzecode.java;


public class Arguments {
  private String libDir = null;
  private String srcDir = null;

  Arguments(String[] args) {
    try {
      
      for (int i=0 ; i<args.length ; i++) {
        String opt = args[i];
        if (opt.equals("-l")) {
          i++;
          libDir = args[i];
        } else {
          if (i != args.length-1) {
            usage();
          }
          srcDir = opt;
        }
      }
      
      if (srcDir == null) {
        usage();
      }
      
    } catch(Exception e) {
      usage();
    }
  }
  
  public String getLibraryDir() {
    return libDir;
  }
  
  public String getSourceDir() {
    return srcDir;
  }
  
  static void usage() {
    System.out.println("Usage: [-l <library search dir>] <source search dir>");
    System.out.println("  library search dir: ");
    System.out.println("  source search dir : ");
    System.exit(-1);
  }
}
