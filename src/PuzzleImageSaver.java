import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class PuzzleImageSaver {
    private static final int CELL_SIZE = 50; // Ukuran setiap blok
    private static final int PADDING = 5; // Jarak antar blok
    private static final Color[] COLORS = {
        new Color(255, 0, 0),    // Red
        new Color(0, 255, 0),    // Green
        new Color(0, 0, 255),    // Blue
        new Color(255, 255, 0),  // Yellow
        new Color(255, 165, 0),  // Orange
        new Color(128, 0, 128),  // Purple
        new Color(0, 255, 255),  // Cyan
        new Color(255, 192, 203),// Pink
        new Color(165, 42, 42),  // Brown
        new Color(0, 128, 0),    // Dark Green
        new Color(128, 128, 128),// Gray
        new Color(255, 0, 255),  // Magenta
        new Color(0, 0, 128),    // Navy
        new Color(128, 128, 0),  // Olive
        new Color(255, 69, 0),   // Red-Orange
        new Color(75, 0, 130),   // Indigo
        new Color(139, 69, 19),  // Saddle Brown
        new Color(210, 105, 30), // Chocolate
        new Color(244, 164, 96), // Sandy Brown
        new Color(255, 228, 181),// Moccasin
        new Color(173, 216, 230),// Light Blue
        new Color(60, 179, 113), // Medium Sea Green
        new Color(47, 79, 79),   // Dark Slate Gray
        new Color(154, 205, 50), // Yellow Green
        new Color(70, 130, 180), // Steel Blue
        new Color(199, 21, 133),  // Medium Violet Red
    };

    public static void savePuzzleImage(Board board, String filePath) {
        char[][] grid = board.getGrid();
        int rows = grid.length;
        int cols = grid[0].length;

        int width = cols * (CELL_SIZE + PADDING) + PADDING;
        int height = rows * (CELL_SIZE + PADDING) + PADDING;

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height); 

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                char piece = grid[i][j];

                if (piece != '.') {
                    int index = piece - 'A'; 
                    g2d.setColor(COLORS[index]);
                    g2d.fillRect(j * (CELL_SIZE + PADDING) + PADDING, i * (CELL_SIZE + PADDING) + PADDING, CELL_SIZE, CELL_SIZE);

                    g2d.setColor(Color.BLACK);
                    g2d.setFont(new Font("Arial", Font.BOLD, 20));
                    FontMetrics fm = g2d.getFontMetrics();
                    int x = j * (CELL_SIZE + PADDING) + PADDING + (CELL_SIZE - fm.charWidth(piece)) / 2;
                    int y = i * (CELL_SIZE + PADDING) + PADDING + (CELL_SIZE + fm.getAscent()) / 2 - fm.getDescent();
                    g2d.drawString(String.valueOf(piece), x, y);
                }
                else {
                    g2d.setColor( new Color(64, 64, 64)); // Dark Grey
                    g2d.fillRect(j * (CELL_SIZE + PADDING) + PADDING, i * (CELL_SIZE + PADDING) + PADDING, CELL_SIZE, CELL_SIZE);
                }
            }
        }

        g2d.dispose();

        try {
            ImageIO.write(image, "png", new File(filePath));
            System.out.println("Puzzle saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
