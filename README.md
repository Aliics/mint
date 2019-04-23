# mint
Java argument handler framework.

## basic example
> $ java TestClass.class --foobar

```
@Option(option = "foobar")
private boolean fooBarOptionEnabled;

public static void main(final String... args) {
    final ArgumentHandler argumentHandler = new ArgumentHandler(this);
    argumentHandler.enrichAnnotated(args);
    
    System.out.println(fooBarOptionEnabled);
}

// This will output "true"
```
