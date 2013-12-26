# Java source codes analyzer

Tools for grasping projects having a lot of source codes.  
First of all, our first goal is to show extends/implements relationships among java classes.

## Build
* JDK 6 or later
* *javaparser 1.0.8* (http://code.google.com/p/javaparser/)

## Run
* JDK/JRE 6 or later
* *javaparser 1.0.8* (http://code.google.com/p/javaparser/)

    java.exe jp.hashiwa.analyzecode.java.Main [-l *library dir*] [-d *output file*] [-up|-down] *source dir*  
      *library dir* : root directory for searching jar files  
      *output file* : result output file path  
      *source dir*  : root directory for searching java source files  
      -up/-down     : print upward/downward relations (default is down)

## Target Java Code
* JDK/JRE 1.5

## Sample result

    +--- japa.parser.ast.Node
      <--- japa.parser.ast.body.BodyDeclaration
        <--- japa.parser.ast.body.AnnotationMemberDeclaration
        <--- japa.parser.ast.body.ConstructorDeclaration
        <--- japa.parser.ast.body.EmptyMemberDeclaration
        <--- japa.parser.ast.body.EnumConstantDeclaration
        <--- japa.parser.ast.body.FieldDeclaration
        <--- japa.parser.ast.body.InitializerDeclaration
        <--- japa.parser.ast.body.MethodDeclaration
        <--- japa.parser.ast.body.TypeDeclaration
          <--- japa.parser.ast.body.AnnotationDeclaration
          <--- japa.parser.ast.body.ClassOrInterfaceDeclaration
          <--- japa.parser.ast.body.EmptyTypeDeclaration
          <--- japa.parser.ast.body.EnumDeclaration
      <--- japa.parser.ast.body.Parameter

## Known Bugs
* Some class cannot be resolved.

