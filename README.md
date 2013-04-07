**Compiling:**  
	Command Line:  Run this in the root path of the project, make sure to create a bin/ directory there.  

*Windows:*
	<pre>
```javac -d "bin/" -classpath "lib/slick.jar;lib/lwjgl.jar" src/org/SnakeEater/Game.java
	src/org/SnakeEater/attributes/*.java src/org/SnakeEater/entities/*.java
	src/org/SnakeEater/geom/*.java src/org/SnakeEater/menus/*.java
	src/org/SnakeEater/states/*.java src/org/SnakeEater/ui/*.java src/org/SnakeEater/util/*.java``` </pre>
*Unix:*
	<pre>
```javac -d "bin/" -classpath "lib/slick.jar:lib/lwjgl.jar" src/org/SnakeEater/Game.java
	src/org/SnakeEater/attributes/*.java src/org/SnakeEater/entities/*.java
	src/org/SnakeEater/geom/*.java src/org/SnakeEater/menus/*.java
	src/org/SnakeEater/states/*.java src/org/SnakeEater/ui/*.java src/org/SnakeEater/util/*.java``` </pre>
**Running:**  
	Command Line:  Run this in the root path of the project  
  
*Windows:*
	<pre>
```java -cp "lib/*;bin/" -Djava.library.path="natives/" org.SnakeEater.Game``` </pre> 
*Unix:*  
	<pre>
```java -cp "lib/*:bin/" -Djava.library.path="natives/" org.SnakeEater.Game``` </pre>