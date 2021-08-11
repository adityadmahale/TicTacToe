import java.util.Scanner;

public class TicTacToe {
    private final static int ROW = 0;
    private final static int COLUMN = 1;

    private final String[][] grid = new String[3][3];
    private final int[][][] count = new int[2][2][3];
    private final int[][] diagonals = new int[2][2];

    private String player = "X";
    private int valueCount;
    private int playerCode = 0;
    private String winner;

    private final Scanner scan = new Scanner(System.in);

    public void setInitialPlayer(String player) {
        if (!(player.equals("X") || player.equals("O")))
            throw new IllegalArgumentException();
        this.player = player;
        if (player.equals("X"))
            playerCode = 0;
        else
            playerCode = 1;
    }

    public void play() {
        while (true) {
            enterValue();
            displayGrid();
            if (isWinner() || isFull())
                break;
            switchPlayer();
        }
    }

    private boolean isWinner() {
        if (winner != null) {
            System.out.println("Player " + player + " won.");
            return true;
        }
        return false;
    }

    private boolean isFull() {
        if (valueCount == 9) {
            System.out.println("No player won.");
            return true;
        }
        return false;
    }

    private void enterValue() {
        while (true) {
            System.out.println("Player: " + player);
            var row = getIndex("row");
            var column = getIndex("column");
            if (grid[row][column] == null) {
                updateGrids(row, column);
                break;
            }
            System.out.println("This spot is occupied. Please enter again.");
        }
    }

    private void updateGrids(int row, int column) {
        grid[row][column] = player;
        checkWinner(++count[playerCode][ROW][row]);
        checkWinner(++count[playerCode][COLUMN][column]);
        if (row == column)
            checkWinner(++diagonals[playerCode][0]);
        if ((row + column) == 2)
            checkWinner(++diagonals[playerCode][1]);
        valueCount++;
    }

    private void checkWinner(int value) {
        if (value == 3)
            winner = player;
    }

    private void displayGrid() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                var value = grid[i][j];
                if (value == null) {
                    System.out.print("- ");
                    continue;
                }
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int getIndex(String dim) {
        int index;
        while (true) {
            System.out.print("Enter " + dim + ": ");
            index = scan.nextInt();
            if (index < 3 && index >= 0)
                break;
            System.out.println("Enter value less than 3 and greater than or equal to 0");
        }
        return index;
    }

    public void switchPlayer() {
        if (player.equals("X")) {
            player = "O";
            playerCode = 1;
        }
        else {
            player = "X";
            playerCode = 0;
        }
    }
}
