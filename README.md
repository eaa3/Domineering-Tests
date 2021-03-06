# MiniMax-Tests

Testing script for part 1 of the domineering assingment.
- [Part 1](https://github.com/ToastNumber/domineering/blob/master/domineering-part1.md)


# Usage

## Make the script executable

```
chmod a+x run_tests4students.sh
```

## Running the tests

To execute the tests you will need to provide your zipped submission as input parameter. Alternatively you might allow it to search a default zip file in your current working directory if the parameter is not provided.

```
./run_tests4students.sh [submission.zip]
```

## About the tests

The game tree generated by your submission is going to be tested against a correctly pre-generated game tree, for different board sizes up to 4x4. First, all subtrees of your game tree should have the same height as the solution game tree for all corresponding board sizes. Finally, all subtrees of your game tree should have the same number of nodes (i.e. size) as the solution game tree all corresponding board sizes. Your submission is correct if all tests pass.


