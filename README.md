# IDDFS-Rubik-s-Cube-Solver

## Overview
For this project, my objective was to create Rubik's Cube Solver with 
Iterative Deepening Depth First Search and sub-goal solvers.  

## Renderer
To display a 3d representation of the cube and all its changing states, I 
created my own basic 3D renderer from scratch only using built in Java
Libraries.

Polygons/Faces of cubies were created by storing 3d representations of every
point for every polygon and then converted into a 2d representation.
The entire 3x3 Rubik's Cube was created with a collection of 27 cubies 
(26 if you do not count the center cubie).

Face rotations was the most difficult task dealing with the renderer. This 
was solved by keeping track of every cube rotation and it's corresponding axis.

Basic UML diagram of how the Point Converter interacts with various parts of 
the project.
![alt text](https://imgur.com/a/oUIGPKY)

## 2D Representation
The cube was also expressed in a 2D representation using a 6x3x3 array of shorts.
This representation was needed to create a more efficient/smaller model of the cube
to perform IDDFS. The array can be looked at as such -> CUBE[FACE][ROW][COL]

## SOLVER
The solver relies on IDDFS to solve various sub-goals (and some sub-sub-goals).
This method is intuitive, as such it was simple to implement the Beginners Method, 
which is what I have done.

The sub-goals are as follows:
- White Cross
  - This sub-was divided into 4 smaller sub-goals, one for each part of cross.
- White Corners
  - This sub-was divided into 4 smaller sub-goals, one for each part of corner
- Middle Layer Edge Pieces
- Orientated Last Layer
- Permutate Last Layer

For the first 2 sub-goals, there existed 12 children, one for each rotation of 
each face, in both clock-wise and counter-clockwise directions.
This does not hold up entirely as some tree pruning was implemented with corner
solves.

For the last sub-goals, each child did not represent a single face rotation, rather
each child represented a different algorithmic move.

## How-To-Use
Dragging the cube rotates it upon its z-axis.
Dragging the cube and holding shift rotates it upon its y-axis.
Key-Binds are used to do face rotations and enact the solver.
They are as follows:
- F: Front
- B: Back
- R: Right
- L: Left
- D: Down
- U: Up
- X: Random 20-Move scramble
- M: Solver

## TODO
- Fix Face rotation glitch when rapidly changing what axis to rotate
- Implement more efficient method of calculating cube rotations
- Implement Thistlethwait's solver
- Implement Korf's solver
