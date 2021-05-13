# Maze Solver

### Task
To start off for now, you are given a map of the maze you are lost in. Like most mazes, this maze consists of one or more rooms. Each of the rooms has doors to one or more other rooms. Your first job is to write a program that will explore the maze, as is, and discover the shortest path from your current location to a destination location. Simultaneously, your program will be expected to determine some other geographic information about the maze.

### Preliminaries
We have provided you with some basic framework code for handling the maze. A maze consists of an r × c grid of rooms, with adjacent rooms separated by either a wall or an empty space. Furthermore, the entire maze is bordered by walls. The rooms are numbered starting from the top left corner (which is (0, 0)). The first coordinate specifies the row number, and the second coordinate specifies the column number. For example, in a 5 × 5 maze, the bottom left corner is (4, 0) and the top right corner is (0, 4). Here is a pictorial example of a 5 × 5 maze:	
![alt text](https://github.com/marcuspeh/Maze-Solver/blob/main/img/example.PNG "Example")

In this diagram, each wall is depicted using a hash symbol, i.e., #, while each room is depicted using the letter R (this is for visualization purposes only – in the actual maze files, the rooms are represented as empty spaces as well!). Notice that there are exactly c rooms and at most c + 1 walls (including the left and right borders) on each row.
You may move between adjacent rooms in any of the four cardinal directions (north, south, east and west) if there is no wall in that direction. Diagonal movement is not allowed. In the above example, one can move from (0,0) to (0,1), but NOT from (0,0) to (1,0), nor from (0,0) to (1,1).

![alt text](https://github.com/marcuspeh/Maze-Solver/blob/main/img/fig1.PNG "Figure 1")
Figure 1: Example printMaze output of a solved maze

We provide you with two classes Maze and Room that represent the maze that your program will solve. The size of the maze is represented by the number of rows and columns in the maze (in the above example, both rows and columns are 5). The maze itself is represented by a matrix of rooms. You will be able to check if there exists a wall in the four directions of the room through the public methods hasNorthWall(), hasSouthWall(), hasEastWall() and hasWestWall(). On top of that, there is a public boolean attribute onPath that you can set for each room (for printing of mazes).

The Maze class has a static method readMaze(String fileName) that reads in a maze from a text file and returns the maze object. We have provided several sample mazes for you to experiment with. We also provide a simplistic way of visualizing a maze through the static class MazePrinter. The static method void printMaze(Maze maze) of the MazePrinter class prints out a maze to the standard output1 , as per the example in Figure 1.

### How it works
The codes run based on breadth first search and stores the path. The number of rooms reachable with each step is stored in a hashmap while computing the shortest path to solve the maze. This will reduce the computation needed

### Running the program
Run MazeSolver.java or
```
Maze maze = Maze.readMaze("maze-empty.txt");
IMazeSolver solver = new MazeSolver();
solver.initialize(maze);
solver.pathSearch(0, 0, 3, 3);
MazePrinter.printMaze(maze);
```

