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
