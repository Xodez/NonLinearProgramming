package part2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

// Kasparas Skruibis
// R00179144

class Main {

    public static void main(String[] args) {
        // creates the two cost objects
        Cost cost1 = new Cost(1.1, 1.3, 2.5);
        Cost cost2 = new Cost(1.5, 1.2, 2.3);

        // allows user to enter size of n
        Scanner input = new Scanner(System.in);
        System.out.println("Put in size of n: ");
        // remove - 1 if you want to traverse through corners
        int n = input.nextInt() - 1;
        System.out.println();

        // calculates the solution for cost 1
        Solution solution1 = robotMoving(n, cost1);
        // prints the solution cost for cost 1
        System.out.println(String.format("Cost1: %.02f - %s", solution1.getCost(),
                // gets the optimal path
                String.join(", ", solution1.getPath())));
        System.out.println("==> Matrix ");

        // calculates the solution for cost 2
        Solution solution2 = robotMoving(n, cost2);
        // prints the solution cost for cost 2
        System.out.println(String.format("Cost2: %.02f - %s", solution2.getCost(),
                // gets the optimal path
                String.join(", ", solution2.getPath())));
    }


    private static Solution robotMoving(int n, Cost cost) {
        // creates the matrix of squares to store the solutions
        Square[][] matrix = new Square[n + 2][n + 2];

        for (int x = 1; x <= n + 1; x++) {
            for (int y = 1; y <= n + 1; y++) {
                if (x == 1 && y == 1) {
                    // sets the first square as 0
                    matrix[x][y] = new Square(0, Direction.UNKNOWN);
                } else if (x == 1) {
                    // if at the top row sets the price as cost of right + previous cost || I make a new Square object so that I can store the price as well as the direction
                    matrix[x][y] = new Square(
                            matrix[x][y - 1].getCost() + cost.getRight(), Direction.RIGHT);
                } else if (y == 1) {
                    // if at the left column sets the price as cost of down + previous cost
                    matrix[x][y] = new Square(
                            matrix[x - 1][y].getCost() + cost.getDown(), Direction.DOWN);
                } else {
                    // if there are multiple ways to get the cost it calculates all possible solutions
                    double downCost = matrix[x - 1][y].getCost() + cost.getDown();
                    double rightCost = matrix[x][y - 1].getCost() + cost.getRight();
                    double diagonalCost = matrix[x - 1][y - 1].getCost() + cost.getDiagonal();

                    double minCost;
                    Direction cameFrom;
                    // if down cost is greater than right cost sets the min cost and the direction to right
                    if (downCost >= rightCost) {
                        minCost = rightCost;
                        cameFrom = Direction.RIGHT;
                    }
                    // if right cost is greater than down cost sets the min cost and the direction to down
                    else {
                        minCost = downCost;
                        cameFrom = Direction.DOWN;
                    }
                    // if diagonal is less than the min cost which is either of the two previous calculations it sets the min cost and direction to diagonal
                    if (minCost >= diagonalCost) {
                        minCost = diagonalCost;
                        cameFrom = Direction.DIAGONAL;
                    }

                    // sets the new square with the correct solution
                    matrix[x][y] = new Square(minCost, cameFrom);
                }

            }
        }

        // sets the final cost to the cost of the last square
        double finalCost = matrix[n + 1][n + 1].getCost();
        // sets the path with the path function
        List<String> path = path(matrix, n + 1, n + 1);
        // prints out the matrix
        for (int x = 1; x <= n + 1; x++) {
            for (int y = 1; y <= n + 1; y++) {
                System.out.print(String.format("%.01f ", matrix[x][y].getCost()));
            }
            System.out.print("\n");
        }
        // returns the final cost and the path
        return new Solution(finalCost, path);
    }

    // function to format the path string
    private static List<String> path(Square[][] corners, int x, int y) {
        List<String> path = new ArrayList<>();

        loop:
        while (x != 0 && y != 0) {
            switch (corners[x][y].getDirection()) {
                // if square path is right adds right to the path string and takes one away from y (column) to traverse through the matrix
                case RIGHT:
                    path.add("right");
                    y -= 1;
                    continue;
                    // if square path is down adds down to the path string and takes one away from x (row) to traverse through the matrix
                case DOWN:
                    path.add("down");
                    x -= 1;
                    continue;
                    // if square path is diagonal adds diagonal tot he path string and takes one away from both x and y to traverse through the matrix
                case DIAGONAL:
                    path.add("diagonal");
                    x -= 1;
                    y -= 1;
                    continue;
                    // once at the start of the matrix breaks the loop
                case UNKNOWN:
                    break loop;
            }
        }

        // reverses the path as it was added going backwards through the matrix
        Collections.reverse(path);
        // returns path
        return path;
    }
}

// made an enum to get the directions easier
enum Direction {
    UNKNOWN,
    RIGHT,
    DOWN,
    DIAGONAL,
}

// solution object to store final cost and path string
class Solution {

    private final double cost;
    private final List<String> path;

    public Solution(double cost, List<String> path) {

        this.cost = cost;
        this.path = path;
    }

    public double getCost() {
        return cost;
    }

    public List<String> getPath() {
        return path;
    }
}


// square object that lets me store the cost and direction in the matrix
class Square {

    private final double cost;
    private final Direction direction;

    public Square(double cost, Direction direction) {
        this.cost = cost;
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public double getCost() {
        return cost;
    }
}

// cost object

class Cost {

    private final double right;
    private final double down;
    private final double diagonal;

    public Cost(double right, double down, double diagonal) {
        this.right = right;
        this.down = down;
        this.diagonal = diagonal;
    }

    public double getRight() {
        return right;
    }

    public double getDown() {
        return down;
    }

    public double getDiagonal() {
        return diagonal;
    }
}
