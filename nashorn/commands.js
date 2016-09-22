var lsa = 'ls -l';
print (lsa);
var lines = 'pwd'.split("\n");
for each (var line in lines) {
  print("|> " + line);
}
