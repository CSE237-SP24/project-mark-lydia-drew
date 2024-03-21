#!/bin/bash

cd src

javac bankapp/Menu.java

cd ..

java -cp .:src bankapp.Menu

rm -rf bankapp
