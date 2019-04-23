# mint
Java argument handler framework.

## basic example
> $ java TestClass.java --foobar

```
@Option(option = "foobar", type = Boolean.class)
private boolean fooBarOptionEnabled;

public static void main(final String... args) {
    final ArgumentHandler argumentHandler = new ArgumentHandler();
    argumentHandler.enrichAnnotated(args);
    
    System.out.println(fooBarOptionEnabled);
}

// This will output "true"
```
