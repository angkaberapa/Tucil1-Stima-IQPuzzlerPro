package sourcecode;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileReader {
    private Scanner sc;

    public FileReader(String filename) throws FileNotFoundException {
        sc = new Scanner(new File(filename));
    }

    public Board readBoard() {
        int N = sc.nextInt();
        int M = sc.nextInt();
        int P = sc.nextInt();
        sc.nextLine(); 

        String boardType = sc.nextLine();
        char[][] boardGrid = new char[N][M];

        switch (boardType) {
            case "DEFAULT":
                for (int i = 0; i < N; i++) {
                    for (int j = 0; j < M; j++) {
                        boardGrid[i][j] = '*'; 
                    }
                }
                break;
            case "CUSTOM":
                for (int i = 0; i < N; i++) {
                    String line = sc.nextLine();
                    for (int j = 0; j < M; j++) {
                        boardGrid[i][j] = line.charAt(j);
                        if(boardGrid[i][j] == 'X'){
                            boardGrid[i][j] = '*';
                        }   
                    }
                }
                break;
            default:
                System.out.println("Invalid board type!");
                return null;
        }

        return new Board(boardGrid, P);
    }

    public List<Piece> readPieces() {
        List<Piece> pieces = new ArrayList<>();
        List<String> currentPiece = new ArrayList<>();
        char currentPieceChar = ' ';

        while (sc.hasNextLine()) {
            String tempLine = sc.nextLine();
            if (tempLine.isEmpty()) break; 

            int idx = 0;
            int idx2 = tempLine.length();
            char tempChar = ' ';
            while((tempChar == ' ') && (idx < idx2)){
                tempChar = tempLine.charAt(idx);
                idx++;
            }   
            if(tempChar == ' '){
                break;
            }
            if (currentPieceChar == tempChar) {
                currentPiece.add(tempLine);
            } else {
                if (!currentPiece.isEmpty()) {
                    pieces.add(new Piece(currentPiece));
                    currentPiece.clear();
                }
                currentPieceChar = tempChar;
                currentPiece.add(tempLine);
            }
        }

        if (!currentPiece.isEmpty()) {
            pieces.add(new Piece(currentPiece));
        }

        return pieces;
    }

    public void close() {
        sc.close();
    }
}
