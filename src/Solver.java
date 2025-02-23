import java.util.List;

public class Solver {
    private Board board;
    private List<Piece> pieces;
    private boolean solved;
    private int iterationCount = 0;

    public Solver(Board board, List<Piece> pieces) {
        this.board = board;
        this.pieces = pieces;
        this.solved = false;
    }
    
    public int getIterationCount() {
        return iterationCount;
    }
    public boolean solve(int pieceIndex) {
        iterationCount++;
        if (pieceIndex == pieces.size()) {
            if (board.getRemainingEmptyCells() == 0) {
                solved = true;
                return true;
            }
            return false;
        }
        Piece piece = pieces.get(pieceIndex);
        for (int rotation = 0; rotation < 4; rotation++) { 
            for (int mirror = 0; mirror < 2; mirror++) { 
                
                for (int x = 0; x < board.getRows(); x++) {
                    for (int y = 0; y < board.getCols(); y++) {
                        if (board.canPlacePiece(piece, x, y)) {
                            board.placePiece(piece, x, y);
                            
                            if (solve(pieceIndex + 1)) {
                                return true; // Solution found
                            }
                            
                            board.removePiece(piece, x, y); // Backtrack
                        }
                    }
                }    
                piece.mirrorHorizontal();
            }
            piece.rotateClockwise(); 
        }
        return false; // No valid placement found for this piece
    }
    public void run() {
        if (solve(0)) {
            System.out.println("Solution found:");
            board.printBoard();
        } else {
            System.out.println("No solution found.");
        }
    }
}