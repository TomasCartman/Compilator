# Compiler
A compiler project to compile a language proposed by some teachers for the learning of the process of compilation

This compiler is being made in **kotlin** language and for now it has only the lexical analyzer part finished, and the parser in progress

## Lexical structure of language
| Description  | Expressions  |
| ------------ | ------------ |
| Reserved Words  |  **`var`** **`const`** **`typedef`** **`struct`** **`extends`** **`procedure`** **`function`** **`start`** **`return`** **`if`** **`else`** **`then`** **`while`** **`read`** **`print`** **`int`** **`real`** **`boolean`** **`string`** **`true`** **`false`** **`global`** **`local`** |
|  Identifiers  | Letter \( Letter &#124; Digit &#124; **`_`** \)\*  |
| Numbers  | Digit\+\(\.Digit\+\)?  |
| Digit  | \[0\-9\]  |
| Letter  | \[a\-z\] &#124; \[A\-Z\] |
| Arithmetic Operators  | **`+`** **`-`** **`*`** **`/`** **`++`** **`--`**  |
| Relational Operators  | **`==`** **`!=`** **`>`** **`>=`** **`<`** **`<=`** **`=`**  |
| Logical Operators  | **`&&`** <code>&#124;&#124;</code> **`!`**  |
| Comment Delimiters  | <code>**//**</code> This is a line comment </br > <code>/**\***</code> This is a block </br > comment <code>\***/**</code>  |
| Delimiters  | <code>;</code> <code>,</code>  <code>( )</code>  <code>{ }</code>  <code>[ ]</code>  <code>.</code>  |
| String  | \( Letter &#124; Digit &#124; Symbol &#124; **`\"`** \)  |
| Symbol  | ASCII from 32 to 126 (except 34)  |

## Syntax example of code
    procedure start {
        const {
            real pi = 3.1415;
        }
    
        typedef struct {
            var {
                int a = 1;
                string className = "test";
            }
        } test;
    
        var {
            int a, e = 3, d;
            string b="hi", c;
        }
    
        print("Choose the value of 'a'");
        read(a);
    
        d = pi * a;
        print(d);
    
    
        function int add(int a, int b) {
            return a + b;
        }
    
        procedure printSomething(string a) {
            print(a);
        }
    
        a = add(1, 2);
        printSomething("this.");
        add(3, 2);
    
        while(x > y) {
            if(a > b) {
                y = y + 1;
            } else if(h + 1 > c - b || p == 1) {
                y = y + h;
            }
        }
    }