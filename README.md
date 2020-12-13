# Genetic Algorithm for Function Maximization / Minimization

## Introduction

This program's purpose is to use "organisms" that each contain a binary code that can be converted to a decimal value and tested to determine
if it is the best possible solution to a single-variable function. 

The strongest organisms (determined by their fitness) are mated with other organisms that have good potential to create new and stronger organisms that have a better
chance of solving the problem. 

## Methodology

An organism starts with a randomly generated binary code of a default length 12. 
Each organism has the ability to:
<ul>
<li> Mate with other organisms (swap a random number of bits with each other) to produce two offspring. </li>
<li> Mutate its genetic code (each bit has a chance to flip values). </li>
</ul>

Each organism has its fitness checked by converting their binary code to a decimal value and then using that
value for x in a function. Organisms that are stronger will have a fitness that is close to the actual solution to the problem.

All organisms are then subjected to the following process:
<ol>
<li>Generate a set number of organisms.</li>
<li>Sort the organisms in order of fitness based on problem type (maximization / minimization).</li>
<li>Choose the strongest organism to be the first parent.</li>
<li>The first parent then mates with the top 50% of the organisms, including itself (elitism).</li>
<li>Two offspring are generated and mutated.</li>
<li>The offspring are added to the next generation.</li>
</ol>

This process then repeats based on the specified number of generations.

By the end of the process, the best solution to the function should have been determined.

## Testing
The program, by default, maximizes the function f(x) = 4 - x^2, which has a known maximum value of 4 when x = 0.
By default, the program uses 10 organisms for each generation and a total of 10 generations.
```
===== GENERATION 1 =====
000010001101
001001101000
001101000000
001101000101
001101000101
001101100100
001101110100
010000111101
011101000101
101101000111

The fitness of the strongest organism is: -72.5625
The decoded value of the strongest organism is: 8.75
```
In the first generation, the random organism has a value of 8.75, which when used in f(x), returns a value of -72.5625.
```
===== GENERATION 3 =====
000000001101
000010000000
000010000000
000010000000
000010000010
000010000101
000010000101
000010010000
000101000010
101110001000

The fitness of the strongest organism is: 3.4375
The decoded value of the strongest organism is: 0.75
```
By the third generation, the algorithm has narrowed in to the correct answer considerably. 
```
===== GENERATION 8 =====
000000000000
000000001000
000000001000
000000001000
000000001000
000000001000
000000001000
000000001100
000000011000
000100001000

The fitness of the strongest organism is: 4.0
The decoded value of the strongest organism is: -0.0
```
By the eighth generation, the problem has been solved.

## Customization
<ul>
<li>The program has two function methods that can be changed and used for the algorithm to solve. The only requirement is that they be single-variable functions.</li>
<li>Each organism can also have its binary code length changed as well as what part of it is used for the left and right of the decimal point.</li>
<li>The parameters for the organism generation can also be changed. The amount of organisms per population, the number of generations, and the mutation probability
can all be edited.</li>
</ul>
