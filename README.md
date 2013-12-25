# Java source codes analyzer

Tools for grasping projects having a lot of source codes.  
First of all, our first goal is to show extends/implements relationships among java classes.

## Build
* JDK 6 or later
* *javaparser 1.0.8* (http://code.google.com/p/javaparser/)

## Run
* JDK/JRE 6 or later
* *javaparser 1.0.8* (http://code.google.com/p/javaparser/)

    java jp.hashiwa.analyzecode.java.Main [-l <library dir>] [-d <output file>] <source dir>
      library dir : root directory for searching jar files
      output file : result output file path
      source dir  : root directory for searching java source files

## Target Java Code
* JDK/JRE 1.5

## Known Bug
* Some class cannot be resolved.

