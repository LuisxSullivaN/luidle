#!/bin/bash

java -jar ~/Descargas/java-cup-11b.jar Parser.cup
mv parser.java ../../java/com/sullivan/parser
rm sym.java
