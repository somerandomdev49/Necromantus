# Necromantus
Necromantus is a simple scriptiong language for java.

Simple java API and syntax.
```java
Tokenizer t = new Tokenizer(contents);
parser = new Parser(t);
Node src = parser.parseSource();
Scope scope = new Scope(null);
Walker walker = new Walker(scope);
walker.putNativeFunc(new NMNativeFunc("write",  (ArrayList<Object> args) -> {for(Object arg : args)System.out.print(arg);System.out.println();return null;}));
walker.putNativeFunc(new NMNativeFunc("read",   (ArrayList<Object> args) -> new Scanner(System.in).nextLine()));
for (Node statement : ((RootNode) src).children)
    walker.walk(statement);
```
Note: this can be found in Runtime.java file.

```necromantus
_ = call write("Hello, World!");
let name = call read();
_ = call write("Hello, ", name);
```

`:D`
