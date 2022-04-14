#!/bin/bash
USR='yourusernamehere'
PSWD='yourpasswordhere'

make
{ echo $USR; echo $PSWD; } | java -jar DataGen.jar