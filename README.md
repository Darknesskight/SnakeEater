**Compiling:**  
	Command Line:  Run this in the root path of the project, make sure to create a bin/ directory there.  

*Windows:*
	<pre>
```javac -d "bin/" -classpath "lib/slick.jar;lib/lwjgl.jar" src/org/tesseract/Game.java
	src/org/tesseract/attributes/*.java src/org/tesseract/entities/*.java
	src/org/tesseract/geom/*.java src/org/tesseract/menus/*.java
	src/org/tesseract/states/*.java src/org/tesseract/ui/*.java src/org/tesseract/util/*.java``` </pre>
*Unix:*
	<pre>
```javac -d "bin/" -classpath "lib/slick.jar:lib/lwjgl.jar" src/org/tesseract/Game.java
	src/org/tesseract/attributes/*.java src/org/tesseract/entities/*.java
	src/org/tesseract/geom/*.java src/org/tesseract/menus/*.java
	src/org/tesseract/states/*.java src/org/tesseract/ui/*.java src/org/tesseract/util/*.java``` </pre>
**Running:**  
	Command Line:  Run this in the root path of the project  
  
*Windows:*
	<pre>
```java -cp "lib/*;bin/" -Djava.library.path="natives/" org.tesseract.Game``` </pre> 
*Unix:*  
	<pre>
```java -cp "lib/*:bin/" -Djava.library.path="natives/" org.tesseract.Game``` </pre>