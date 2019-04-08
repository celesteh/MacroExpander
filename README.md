# MacroExpander
Allows for Macro expansion in a Document in SuperCollider


An extension of IdentityDictionary that allows the user to specify keys for macro expansion.
Each key must be on a line by itself. The user types alt-down to do the expansion.

The keycodes specified have only been tested with Linux. If you know the keycodes for the down arrow on OS X or Windows please get in touch or submit a pull request.


Example:
```
(
m = MacroReplacer.new;
m.put(\owl, """
(
1 + 1
)
""");

)
```

This opens a new file. If you type "owl" or "\owl" on a line by itself and type alt-down while on that line, it will expand.
