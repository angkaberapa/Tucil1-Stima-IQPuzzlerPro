package com.myapp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sourcecode.Board;
import sourcecode.FileReader;
import sourcecode.Piece;
import sourcecode.PuzzleImageSaver;
import sourcecode.Solver;

public class Controller {

    @FXML
    private ScrollPane Canvas;

    @FXML
    private Button SavePngButton;

    @FXML
    private Button SaveTxtButton;

    @FXML
    private Button SolveButton;

    @FXML
    private Button UploadFileButton;

    @FXML
    private TextArea textArea;  // Added TextArea for output messages

    private File selectedFile;
    private Board board;
    private List<Piece> pieces;
    private Solver solver;
    private String solutionImagePath;
    private BufferedImage image;

    @FXML
    void uploadTxtFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setTitle("Select a Puzzle File");

        File file = fileChooser.showOpenDialog(new Stage());
        if (file != null) {
            selectedFile = file;
            textArea.appendText("File selected: " + selectedFile.getAbsolutePath() + "\n");

            try {
                FileReader reader = new FileReader(selectedFile.getAbsolutePath());
                board = reader.readBoard();
                pieces = reader.readPieces();
            } catch (FileNotFoundException e) {
                showAlert("Error", "File not found.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    void solvePuzzle(ActionEvent event) {
        if (selectedFile == null || board == null || pieces == null) {
            showAlert("Error", "No file selected or invalid puzzle data.", Alert.AlertType.ERROR);
            return;
        }   

        solver = new Solver(board, pieces);

        long startTime = System.currentTimeMillis();
        boolean solved = solver.solve(0);
        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;

        if (solved) {
            textArea.appendText("Solution found!\n");
            textArea.appendText("Waktu pencarian: " + elapsedTime + " ms\n");
            textArea.appendText("Banyak kasus yang ditinjau: " + solver.getIterationCount() + "\n");

            // Save solution image path
            solutionImagePath = selectedFile.getParent() + "/" + selectedFile.getName().replace(".txt", "_solution.png");
            image = PuzzleImageSaver.savePuzzleImage(board, solutionImagePath);
            Image fxImage = convertBufferedImageToFXImage(image);
            ImageView imageView = new ImageView(fxImage);

            // Show image in ScrollPane
            imageView.setPreserveRatio(true);
            imageView.setFitWidth(Canvas.getWidth() - 20);
            imageView.setFitHeight(Canvas.getHeight() - 20);
            imageView.setImage(fxImage);
            // Use StackPane to center the image
            StackPane container = new StackPane(imageView);
            container.setPrefSize(Canvas.getPrefWidth(), Canvas.getPrefHeight());

            // Set content to ScrollPane
            Canvas.setContent(container);

        } else {
            textArea.appendText("No solution found.\n");
            textArea.appendText("Waktu pencarian: " + elapsedTime + " ms\n");
            textArea.appendText("Banyak kasus yang ditinjau: " + solver.getIterationCount() + "\n");
        }
    }

    @FXML
    void saveToPng(ActionEvent event) {
        if (solutionImagePath != null) {
            PuzzleImageSaver.saveImageToPdf(image, solutionImagePath);
            showAlert("Success", "Solution saved as PNG: " + solutionImagePath, Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "No solution to save.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void saveToTxt(ActionEvent event) {
        if (selectedFile != null && board != null) {
            String txtSolutionPath = selectedFile.getParent() + "/" +
                    selectedFile.getName().replace(".txt", "_solution.txt");

            board.saveBoardToTxtFile(txtSolutionPath);
            showAlert("Success", "Solution saved as TXT: " + txtSolutionPath, Alert.AlertType.INFORMATION);
        } else {
            showAlert("Error", "No solution to save.", Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    public static Image convertBufferedImageToFXImage(BufferedImage bufferedImage) {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        WritableImage writableImage = new WritableImage(width, height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();
    
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = bufferedImage.getRGB(x, y);
                pixelWriter.setArgb(x, y, argb);
            }
        }
    
        return writableImage;
    }
    
}
