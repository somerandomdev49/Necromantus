# Necromantus
I will commit here, no worries! (it actually exists)


Necromantus is a simple scriptiong language for java.

Simple java API and syntax.
```java
...
// not full code, but api usage is here:
walker.put("result", null); // creates variable in walker's scope.
walker.putNativeFunc("write", (ArrayList<Object> args) -> {for(Object arg:args)System.out.print(arg);System.out.println();return null;}); // creates function that erites stuff to console.
walker.putNativeFunc("read", (ArrayList<Object>) -> new Scanner(System.in).nextLine()); // creates function that reads from console and returns read line.
...
```

```necromantus
_ = call write("Hello, World!");
let name = call read();
_ = call write("Hello, ", name);
```

`:D`
