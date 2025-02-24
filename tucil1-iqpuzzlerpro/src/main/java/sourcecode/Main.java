package sourcecode;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.print("Masukkan nama file: ");
        Scanner sc = new Scanner(System.in);
        String filename = sc.nextLine();
        try {
            FileReader reader = new FileReader("../test/" + filename);

            Board board = reader.readBoard();

            List<Piece> pieces = reader.readPieces();

            long startTime = System.currentTimeMillis(); 

            Solver solver = new Solver(board, pieces);  
            Boolean solved = solver.solve(0);
            long endTime = System.currentTimeMillis();  
            long elapsedTime = endTime - startTime;     
            if (solved) {
                System.out.println("Solution found:");
                board.printBoard();
                System.out.println("Waktu pencarian: " + elapsedTime + " ms");
                System.out.println("Banyak kasus yang ditinjau: " + solver.getIterationCount());
                System.out.println("Apakah anda ingin menyimpan solusi? (ya/tidak)");
                String save = sc.nextLine();
                if(save.equals("ya")){
                    board.saveBoardToTxtFile("../test/" + filename.substring(0,filename.lastIndexOf(".")) + "_solution.txt");
                    PuzzleImageSaver.savePuzzleImage(board, "../test/" + filename.substring(0,filename.lastIndexOf(".")) + "_solution.png");
                }
            } else {
                System.out.println("No solution found.");
                System.out.println("Waktu pencarian: " + elapsedTime + " ms");
                System.out.println("Banyak kasus yang ditinjau: " + solver.getIterationCount());
            }
            sc.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
    }
}