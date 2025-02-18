import java.util.*;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int N, M, P;
    N = sc.nextInt();
    M = sc.nextInt();
    P = sc.nextInt();
    sc.nextLine();
    // BOARD
    char[][] board = new char[N][M];
    String S = sc.nextLine();
    switch (S) {
        case "DEFAULT" -> {
            for (int i = 0; i < N; i++) {
                for(int j = 0; j < M; j++){
                    board[i][j] = 'X';
                }
            }
          }

        case "CUSTOM" -> {
            for (int i = 0; i < N; i++) {
                String input = sc.nextLine();
                for(int j = 0; j < M; j++){
                    board[i][j] = input.charAt(j);
                }
            }
          }

        case "PYRAMID" -> {
            // TO DO  
          }

        default -> {
            System.out.println("Invalid input");
          }
    }
    // PIECES
    // List<List<String>> pieces = new ArrayList<>();

  }
}