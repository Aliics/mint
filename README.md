# mint
Java option handling framework.

## information
Below is a table of the *two* useful annotations for marking fields and methods.

|annotation|fuctionality|
|:--|:--|
|*@CalledOption*|`Invokes a method` associated with an option given via command line arguments. _Can be given parameters *or* invoked as is._|
|*@AssignedOption*|`Assigns a field` associated with an option given via command line arguments. _Can be given a value via command line options *or* assigned based on their primitive `on` values._|
