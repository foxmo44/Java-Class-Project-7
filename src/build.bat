del *.class
javac -cp .;mysql-connector-java-5.1.40-bin.jar *.java
jar -cfv Project07Fox.jar *.java *.class

