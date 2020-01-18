#  Necromantus [![Build Status](https://travis-ci.org/somerandomdev49/Necromantus.png?branch=master)](https://travis-ci.org/somerandomdev49/Necromantus.png)

<img width="500" height="500" src="https://raw.githubusercontent.com/somerandomdev49/Necromantus/master/NECROMANTUS-LOGO.png"></img>

Necromantus is a simple scripting language for java.

# Looks like this, nothing special:

```javascript
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
	let correct = true;
	while correct {
		write("What is (2 + 2) * (2 - 2)?");
		let answer = read();
		if 0 not (stof(answer)) {
			correct = false;
		}
	}
	write("INCORRECT!", " TWO PLUS TWO IS ", weird(2, 2));
} elseif name is "SomeRandomDev49" {
    write("Hello, God.");
} elseif name is "hehehe" {
    write("uh oh!");
} else {
    write("ACCESS DENIED!");
}
```

Simple java API and syntax.
```java
class Example {
    public static void main(String[] args) {
        Tokenizer t = new Tokenizer(contents);
        parser = new Parser(t);
        Node dd = parser.parseSource();
        Scope scope = new Scope(null);
        Walker walker = new Walker(scope);
        walker.putNativeFunc(new NMNativeFunc("write", (ArrayList<Object> args) -> {
            for(Object arg : args)
                System.out.print(arg);
            System.out.println();
            return null;
        }));
        walker.putNativeFunc(new NMNativeFunc("read", (ArrayList<Object> args) -> 
            new Scanner(System.in).nextLine()
        ));
        for (Node statement : ((RootNode) src).children)
            walker.walk(statement);
    }
}
```
> Note: this ⬆⬆⬆ is default stuff, so you can use these functions straight away.

> A little thing: to disable all messages, remove an `if` statement in `nextToken()` of `Tokenizer.java` and recompile the program :)

if some unexpected behaviour appears, make an issue. (PLEASE!!!)
:smile:
`:D`

# Future stuff and TODO and some features

| Feature | Description            |  Status     | Emoji  |
|---------|------------------------|-------------|:------:|
| Escaping| String escaping        |   Soon      |   😐   |
| Types   | Add type support       |   Soon      |   🤔   |
| JS gen  | JavaScript code gen    |   Meh.      |   😎   |
| Modules | Add module support     | In Progress |   🙂   |
| Objects | Objects and stuff      | In Progress |   🤔   |
|Arrays|Arrays|Finished|🤔
|Array functions|Soon|🙂
| REPL    | Interactive mode (`-i`)|   Finished  |   😎   |

Everything that is `Finished` will be in the new update.

