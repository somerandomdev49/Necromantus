#  Necromantus [![Build Status](https://travis-ci.org/somerandomdev49/Necromantus.png?branch=master)](https://travis-ci.org/somerandomdev49/Necromantus.png)

<img width="500" height="500" src="https://raw.githubusercontent.com/somerandomdev49/Necromantus/master/NECROMANTUS-LOGO.png"></img>

Necromantus is a simple scripting language for java.

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
Note: this ⬆⬆⬆ can be found in Runtime.java file.

```necromantus
_ = call write("Hello, World!");
_ = call write("Enter your name:");
let plus = func (x, y) {
	out = x + y;
};
let name = call read();
_ = call write("Hello, ", name);
_ = call write("two plus two is ", (call plus(2, 2)));
_ = if name is "Admin" {
	_ = call write("ACCESS GRANTED!");
	let correct = 1;
	let answer = "";
	_ = loop correct is 1 {
		_ = call write("What is 2 + 2?");
		answer = call read();
		_ = if answer not "4" {
			correct = 0;
		};
	};
	_ = call write("INCORRECT!", " TWO PLUS TWO IS ", (call plus(2, 2)));
};
_ = if name not "Admin" {
	_ = call write("ACCESS DENIED!");
};
```

if some unexpected behaviour appears, make an issue. (PLEASE!!!)

`:D`
