package application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class StatPopUpController {
	@FXML private Label statTitle, numGames, winPercentage, currStreak, maxStreak;
	@FXML private Pane dataTitle;
	@FXML private BarChart guessDistribution;

    
    public void initialize() {
    	setStats();
    }
      
    private void setStats(){
        // File path
    	System.out.println("here1");
        File statsFile = new File("./src/application/StatData.txt");
    	System.out.println("here2");

        List<String> lines = new ArrayList<>();

        // Read all lines from the file
        try (Scanner scanner = new Scanner(statsFile)) {
            while (scanner.hasNextLine()) {
                lines.add(scanner.nextLine());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return; // Exit if file not found
        }
        
        guessDistribution.getData().clear();
        // Assuming the statistics for guesses are on lines 2 through 7 (for 1 to 6 guesses)
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Guesses Distribution");

        for (int i = 2; i <= 7; i++) {  // Assuming lines 2 to 7 store guesses 1 to 6
            String[] parts = lines.get(i).split(" ");
            String guessLabel = "Guess " + (i - 1); // Label like "Guess 1", "Guess 2", etc.
            int occurrences = Integer.parseInt(parts[1]); // The number of occurrences
            series.getData().add(new XYChart.Data<>(guessLabel, occurrences));
        }

        guessDistribution.getData().add(series);
        guessDistribution.setStyle("-fx-bar-fill: green;");
        
        String[] numGamesParts = lines.get(0).split(" ");
        int currNumGames = Integer.parseInt(numGamesParts[1]);
        numGames.setText(Integer.toString(currNumGames));
        
        String[] numWinsParts = lines.get(1).split(" ");
        int currWinGames = Integer.parseInt(numWinsParts[1]);
        int currPercentage;
        
        if (currNumGames == 0) {currPercentage = 100;}
        else {currPercentage = (currPercentage =(currWinGames*100)/currNumGames);}
        
        winPercentage.setText(Integer.toString(currPercentage));
        
        String[] numStreakParts = lines.get(8).split(" ");
        int currStreakNum = Integer.parseInt(numStreakParts[1]);
        currStreak.setText(Integer.toString(currStreakNum));
        
        String[] numMaxStreakParts = lines.get(9).split(" ");
        int currMax = Integer.parseInt(numMaxStreakParts[1]);
        maxStreak.setText(Integer.toString(currMax));
        
        if (currStreakNum != 0) {statTitle.setText("Congrats!");}
        else {statTitle.setText("Better Luck Next Time!");}
        
    }
}