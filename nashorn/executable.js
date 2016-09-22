#!/usr/lib/jvm/java-8-oracle/bin/jjs -scripting
print("Arguments (${$ARG.length})");
for each (arg in $ARG) {
  print("- ${arg}")
}
$EXEC("ls -l\n");
print ($OUT);
