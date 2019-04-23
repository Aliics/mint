# mint
Java argument handler framework.

## basic example
> $ java TestClass.class --foobar

```
@Option(option = "foobar", type = Boolean.class)
private boolean fooBarOptionEnabled;

public static void main(final String... args) {
    final ArgumentHandler argumentHandler = new ArgumentHandler(this.getClass());
    argumentHandler.enrichAnnotated(args);
    
    System.out.println(fooBarOptionEnabled);
}

// This will output "true"
```
