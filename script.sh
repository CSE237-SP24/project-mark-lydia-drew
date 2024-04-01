#!/bin/bash

if [ ! -d "src/accountData" ]; then
    # If the directory doesn't exist, create it
    mkdir -p src/accountData
fi

# Navigate to the source directory
cd src

# Compile Java files
javac bankapp/*.java

# Navigate back to the root directory
cd ..

# Run the Java program
java -cp .:src bankapp.Menu

# Clean up compiled Java files
rm -rf bankapp