#  Necromantus [![Build Status](https://travis-ci.org/somerandomdev49/Necromantus.png?branch=master)](https://travis-ci.org/somerandomdev49/Necromantus.png)

<img width="500" height="500" src="https://raw.githubusercontent.com/somerandomdev49/Necromantus/master/NECROMANTUS-LOGO.png"></img>

Necromantus is a simple scripting language for java.

Simple java API and syntax.
```java
Tokenizer t = new Tokenizer(contents);
parser = new Parser(t);
Node dd = parser.parseSource();
Scope scope = new Scope(null);
Walker walker = new Walker(scope);
walker.putNativeFunc(new NMNativeFunc("write",  (ArrayList<Object> args) -> {for(Object arg : args)System.out.print(arg);System.out.println();return null;}));
walker.putNativeFunc(new NMNativeFunc("read",   (ArrayList<Object> args) -> new Scanner(System.in).nextLine()));
for (Node statement : ((RootNode) src).children)
    walker.walk(statement);
```
Note: this ⬆⬆⬆ can be found in Runtime.java file.

```necromantus
write("Hello, World!");
write("Enter your name:");
let weird = func (x, y) {
	out = (x + y) * (x - y);
};
let name = read();
write("Hello, ", name);
write("two plus two is ", 2 + 2);
if name is "Admin" {
	write("ACCESS GRANTED!");
	let correct = 1;
	let answer = "";
	loop correct is 1 {
		write("What is (2 + 2) * (2 - 2)?");
		answer = read();
		if answer not "0" {
			correct = 0;
		};
	};
	write("INCORRECT!", " TWO PLUS TWO IS ", weird(2, 2));
};
if name not "Admin" {
	write("ACCESS DENIED!");
};
```

if some unexpected behaviour appears, make an issue. (PLEASE!!!)

`:D`
