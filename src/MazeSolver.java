import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class Coord {
	int x;
	int y;
	Coord(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

class Path {
	List<Coord> list;
	Path(int x, int y) {
		list = new ArrayList<>();
		list.add(new Coord(x, y));
	}
	Path(List<Coord> list) {
		this.list = list;
	}
	Coord getLast() {
		return list.get(list.size() - 1);
	}
	Path insert(int x, int y) {
		List<Coord> temp = new ArrayList<>(list);
		temp.add(new Coord(x, y));
		return new Path(temp);
	}
	int size(){
		return list.size();
	}
}

public class MazeSolver implements IMazeSolver {
	private static final int NORTH = 0, SOUTH = 1, EAST = 2, WEST = 3;
	private static int[] DIRECTION = {NORTH, SOUTH, EAST, WEST};
	private static int[][] DELTAS = new int[][] {
			{ -1, 0 }, // North
			{ 1, 0 }, // South
			{ 0, 1 }, // East
			{ 0, -1 } // West
	};
	Maze maze;
	HashMap<Integer, Integer> map;
	boolean[][] visited;

	public MazeSolver() {
		// TODO: Initialize variables.
		map = new HashMap<>();
	}

	@Override
	public void initialize(Maze maze) {
		// TODO: Initialize the solver.
		this.maze = maze;
		visited = new boolean[maze.getRows()][maze.getColumns()];
	}

	@Override
	public Integer pathSearch(int startRow, int startCol, int endRow, int endCol) throws Exception {
		// TODO: Find shortest path.
		//no maze
		if (this.maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}

		if (startRow < 0 || startCol < 0 || startRow >= maze.getRows() || startCol >= maze.getColumns() ||
				endRow < 0 || endCol < 0 || endRow >= maze.getRows() || endCol >= maze.getColumns()) {
			throw new IllegalArgumentException("Invalid start/end coordinate");
		}

		// set all visited flag to false
		// before we begin our search
		for (int i = 0; i < maze.getRows(); ++i) {
			for (int j = 0; j < maze.getColumns(); ++j) {
				this.visited[i][j] = false;
				maze.getRoom(i, j).onPath = false;
			}
		}

		Integer minSteps = null;
		List<Path> frontier = new ArrayList<>();
		frontier.add(new Path(startRow, startCol));
		map.clear();
		visited[startRow][startCol] = true;

		if (startCol == endCol && startRow == endRow) {
			minSteps = 0;
			maze.getRoom(startRow, startCol).onPath = true;
		}

		while (!frontier.isEmpty()) {
			List<Path> newFrontier = new ArrayList<>();
			map.merge(frontier.get(0).list.size() - 1, frontier.size(), Integer::sum);

			for (Path p: frontier) {
				Coord c = p.getLast();
				for (int dir: DIRECTION) {
					if (canGo(c.x, c.y, dir) ) {
						Path temp = p.insert(c.x + DELTAS[dir][0], c.y + DELTAS[dir][1]);
						visited[c.x + DELTAS[dir][0]][c.y + DELTAS[dir][1]] = true;
						newFrontier.add(temp);
						if(c.x + DELTAS[dir][0] == endRow && c.y + DELTAS[dir][1] == endCol &&
								minSteps == null) {
							minSteps = writePath(temp);
						}
					}
				}
			}
			frontier = newFrontier;
		}

		return minSteps;
	}

	int writePath(Path p) {
		for (Coord c: p.list) {
			maze.getRoom(c.x, c.y).onPath = true;
		}
		return p.size() - 1;
	}

	@Override
	public Integer numReachable(int k) throws Exception {
		// TODO: Find number of reachable rooms.
		if (this.maze == null) {
			throw new Exception("Oh no! You cannot call me without initializing the maze!");
		}
		return map.getOrDefault(k, 0);
	}

	private boolean canGo(int row, int col, int dir) {
		// check if path can go and not visited
		if (row + DELTAS[dir][0] < 0 || row + DELTAS[dir][0] >= maze.getRows()) return false;
		if (col + DELTAS[dir][1] < 0 || col + DELTAS[dir][1] >= maze.getColumns()) return false;
		if (visited[row + DELTAS[dir][0]][col + DELTAS[dir][1]] == true) return false;

		switch (dir) {
			case NORTH:
				return !maze.getRoom(row, col).hasNorthWall();
			case SOUTH:
				return !maze.getRoom(row, col).hasSouthWall();
			case EAST:
				return !maze.getRoom(row, col).hasEastWall();
			case WEST:
				return !maze.getRoom(row, col).hasWestWall();
		}

		return false;
	}

	public static void main(String[] args) {
		try {
			Maze maze = Maze.readMaze("maze-empty.txt");
			IMazeSolver solver = new MazeSolver();
			solver.initialize(maze);

			System.out.println(solver.pathSearch(0, 0, 3, 3));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 10; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}

			System.out.println(solver.pathSearch(1, 1, 1, 1));
			MazePrinter.printMaze(maze);

			for (int i = 0; i <= 10; ++i) {
				System.out.println("Steps " + i + " Rooms: " + solver.numReachable(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
