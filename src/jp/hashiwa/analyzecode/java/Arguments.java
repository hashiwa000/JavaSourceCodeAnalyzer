package jp.hashiwa.analyzecode.java;


public class Arguments {
  private String libDir = null;
  private String outFile = null;
  private String srcDir = null;

  Arguments(String[] args) {
    try {
      
      for (int i=0 ; i<args.length ; i++) {
        String opt = args[i];
        if (opt.equals("-l")) {
          i++;
          libDir = args[i];
        } else if (opt.equals("-d")) {
            i++;
            outFile = args[i];
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
  
  public String getOutputFile() {
    return outFile;
  }
  
  static void usage() {
    System.out.println("Usage: [-l <library dir>] [-d <output file>] <source dir>");
    System.out.println("  library dir : root directory for searching jar files");
    System.out.println("  output file : result output file path");
    System.out.println("  source dir  : root directory for searching java source files");
    System.exit(-1);
  }
}
