package sourcecode;
import java.util.List;

public class Piece {
    private char[][] shape;
    private static int pieceCount = 0;
    
    public Piece(List<String> shape) {
        int rows = shape.size();
        int cols = 0;
        for (String line : shape) {
            if (line.length() > cols) {
                cols = line.length();
            }
        }
        this.shape = new char[rows][cols];
        for (int i = 0; i < rows; i++) {
            String line = shape.get(i);
            for (int j = 0; j < cols; j++) {
                this.shape[i][j] = (j < line.length()) ? line.charAt(j) : ' ';
            }
        }
        pieceCount++;
    }

    public static int getPieceCount() {
        return pieceCount;
    }
    public char[][] getShape() {
        return shape;
    }

    public void rotateClockwise() {
        int rows = shape.length;
        int cols = shape[0].length;
        char[][] rotated = new char[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        shape = rotated;
    }

    public void mirrorHorizontal() {
        int rows = shape.length;
        int cols = shape[0].length;
        char[][] mirrored = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mirrored[i][cols - 1 - j] = shape[i][j];
            }
        }
        shape = mirrored;
    }
    public void printPiece() {
        for (char[] row : shape) {
            System.out.println(new String(row));
        }
    }
}
