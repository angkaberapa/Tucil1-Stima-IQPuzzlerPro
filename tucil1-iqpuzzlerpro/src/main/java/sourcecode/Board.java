package sourcecode;
import java.io.FileWriter;
import java.io.IOException;

public class Board {
    private char[][] grid;
    private int rows, cols;
    private int remainingEmptyCells;
    private int piecePlacedCount;
    private int totalPiece;
    private static final String[] COLORS = {
        "\u001B[31m", // A - Red
        "\u001B[32m", // B - Green
        "\u001B[34m", // C - Blue
        "\u001B[33m", // D - Yellow
        "\u001B[35m", // E - Magenta
        "\u001B[36m", // F - Cyan
        "\u001B[91m", // G - Light Red
        "\u001B[92m", // H - Light Green
        "\u001B[94m", // I - Light Blue
        "\u001B[95m", // J - Light Magenta
        "\u001B[96m", // K - Light Cyan
        "\u001B[97m", // L - White
        "\u001B[90m", // M - Dark Gray
        "\u001B[33m", // N - Orange
        "\u001B[32m", // O - Light Green
        "\u001B[34m", // P - Blue
        "\u001B[35m", // Q - Magenta
        "\u001B[36m", // R - Cyan
        "\u001B[91m", // S - Light Red
        "\u001B[92m", // T - Light Green
        "\u001B[94m", // U - Light Blue
        "\u001B[95m", // V - Light Magenta
        "\u001B[96m", // W - Light Cyan
        "\u001B[97m", // X - White
        "\u001B[90m", // Y - Dark Gray
        "\u001B[31m", // Z - Red
    };
    private static final String RESET = "\u001B[0m";
    public Board(char[][] grid, int totalPiece) {
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.grid = new char[rows][cols];
        this.remainingEmptyCells = 0;
        this.piecePlacedCount = 0;
        this.totalPiece = totalPiece;
        // '*' represents empty cells
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
            if (grid[i][j] == '*') {
                remainingEmptyCells++;
            }
            this.grid[i][j] = grid[i][j];
            }
        }
    }
    public int getRows() {
        return rows;
    }
    public int getCols() {
        return cols;
    }
    public char[][] getGrid() {
        return grid;
    }
    public int getRemainingEmptyCells() {
        return remainingEmptyCells;
    }
    public int piecePlacedCount(){
        return piecePlacedCount;
    }
    public int getTotalPiece(){
        return totalPiece;
    }
    public void printBoard() {
        for (char[] row : grid) {
            for (char cell : row) {
                int index = cell - 'A'; 
                if (index >= 0 && index < COLORS.length) {
                    System.out.print(COLORS[index] + cell + RESET);
                } else {
                    System.out.print(cell); 
                }
            }
            System.out.println();
        }
    }
    public boolean canPlacePiece(Piece piece, int x, int y) {
        char[][] shape = piece.getShape();
        int pieceRows = shape.length;
        int pieceCols = shape[0].length;

        for (int i = 0; i < pieceRows; i++) {
            for (int j = 0; j < pieceCols; j++) {
                if (shape[i][j] != ' ' && (x + i >= rows || y + j >= cols || grid[x + i][y + j] != '*')) {
                    return false; 
                }
            }
        }
        return true;
    }
    public void placePiece(Piece piece, int x, int y) {
        char[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != ' ') {
                    grid[x + i][y + j] = shape[i][j];
                    remainingEmptyCells--;
                    piecePlacedCount++;
                }
            }
        }
    }
    public void removePiece(Piece piece, int x, int y) {
        char[][] shape = piece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] != ' ') {
                    grid[x + i][y + j] = '*';
                    remainingEmptyCells++;
                    piecePlacedCount--;
                }
            }
        }
    }
        
    public void saveBoardToTxtFile(String txtSolutionPath) {
        try (FileWriter writer = new FileWriter(txtSolutionPath)) {
            for (char[] row : grid) {
                writer.write(row);
                writer.write("\n"); // New line for each row
            }
            // System.out.println("Board saved to " + txtSolutionPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
