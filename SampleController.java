package application;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.StringTokenizer;


import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class SampleController {
	@FXML public Button A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,deleteBtn,resetBtn,enterBtn, loadBtn, saveBtn;
	@FXML private ButtonBar letterRow0, letterRow1, letterRow2,boardRow0, boardRow1,boardRow2, boardRow3,boardRow4, boardRow5;
	@FXML public Label currGuess;
	
	private ButtonBar[] gameBoard;
	private int numTries = 0;
	private String wordToGuess;
	private Boolean won = false;
	public List<String> allWords = new ArrayList<>(); 
	public List<String> currStats = new ArrayList<>(); 
	public List<ButtonBar> gameBoardLi = new ArrayList<ButtonBar>();
	public List<Button> keyboardLi = new ArrayList<Button>(); 
	
	
	public void initialize() {
		wordToGuess = getNewWord();
		
		gameBoardLi.add(boardRow0); gameBoardLi.add(boardRow1);gameBoardLi.add(boardRow2);
		gameBoardLi.add(boardRow3);gameBoardLi.add(boardRow4);gameBoardLi.add(boardRow5);
		keyboardLi.add(A);keyboardLi.add(B);keyboardLi.add(C);keyboardLi.add(D);keyboardLi.add(E);keyboardLi.add(F);keyboardLi.add(G);
		keyboardLi.add(H);keyboardLi.add(I);keyboardLi.add(J);keyboardLi.add(K);keyboardLi.add(L);keyboardLi.add(M);keyboardLi.add(N);
		keyboardLi.add(O);keyboardLi.add(P);keyboardLi.add(Q);keyboardLi.add(R);keyboardLi.add(S);keyboardLi.add(T);keyboardLi.add(U);
		keyboardLi.add(V);keyboardLi.add(W);keyboardLi.add(X);keyboardLi.add(Y);keyboardLi.add(Z);
		
		setupLetterButtons();
		setupResetButton();
		settingupGameBoardColors();
		deleteBtnAction();
		setupEnterButton();
		setupLoadButton();
		setupSaveButton();
		gameBoard = new ButtonBar[]{boardRow0, boardRow1,boardRow2, boardRow3,boardRow4, boardRow5};
		//actualGame(wordToGuess);
	}

    private void deleteBtnAction() {
    	deleteBtn.setOnAction(event -> {
            if (currGuess.getText().length() > 0) { // Check if less than 5 letters
            	String newGuess = currGuess.getText().substring(0, currGuess.getText().length() -1);
                currGuess.setText(newGuess);
            }
        });	
	}
    
    @FXML private void handleShowStats() {
        try {
        	System.out.println("wwww1");
        	FXMLLoader loader = new FXMLLoader(getClass().getResource("StatPopUp.fxml"));
        	System.out.println("wwww2");

        	// Load the FXML first, which also instantiates the controller
        	Pane statDocRoot = loader.load();
        	System.out.println("wwww3");

        	// Now retrieve the controller
        	//StatPopUpController controller = loader.getController();

        	Stage stage = new Stage(); 
        	System.out.println("wwww4");
        	stage.setScene(new Scene(statDocRoot));
        	System.out.println("wwww5");
        	stage.setTitle("Statistics Document");
        	System.out.println("wwww6");
        	stage.showAndWait();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void writingNewStats(boolean won, int guessNum) {
        // File path
        File statsFile = new File("./src/application/StatData.txt");
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

        // Increment the number of games
        int numGamesIndex = 0; // index where 'numGames' is stored in the file
        String[] numGamesParts = lines.get(numGamesIndex).split(" ");
        int numGames = Integer.parseInt(numGamesParts[1]) + 1;
        lines.set(numGamesIndex, numGamesParts[0] + " " + numGames);

        if (won) {
            // Increment the number of wins
            int numWinsIndex = 1; // index where 'numWins' is stored
            String[] numWinsParts = lines.get(numWinsIndex).split(" ");
            int numWins = Integer.parseInt(numWinsParts[1]) + 1;
            lines.set(numWinsIndex, numWinsParts[0] + " " + numWins);

            // Increment the specific guess count
            int guessIndex = guessNum + 1; // index where specific guess count is stored
            String[] guessParts = lines.get(guessIndex).split(" ");
            int guessCount = Integer.parseInt(guessParts[1]) + 1;
            lines.set(guessIndex, guessParts[0] + " " + guessCount);
            
            String[] currStreakParts = lines.get(8).split(" ");
            int currStreak = Integer.parseInt(currStreakParts[1]) + 1;
        	lines.set(8, currStreakParts[0] + " " + currStreak);
        	
            String[] maxStreakParts = lines.get(9).split(" ");
            int maxStreak = Integer.parseInt(currStreakParts[1]);
            if (currStreak > maxStreak) {lines.set(9, maxStreakParts[0] + " " + (maxStreak+1));}
        }
        else {
            String[] currStreakParts = lines.get(8).split(" ");
            int currStreak = Integer.parseInt(currStreakParts[1]);
        	if (currStreak != 0) {lines.set(8, currStreakParts[0] + " " + 0);}      	
        	
        }
        

        // Write updated lines back to the file
        try (FileWriter writer = new FileWriter(statsFile, false)) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	private void setupLetterButtons() {
        A.setOnAction(event -> handleLetterButton("A")); B.setOnAction(event -> handleLetterButton("B"));
        C.setOnAction(event -> handleLetterButton("C")); D.setOnAction(event -> handleLetterButton("D"));
        E.setOnAction(event -> handleLetterButton("E")); F.setOnAction(event -> handleLetterButton("F"));
        G.setOnAction(event -> handleLetterButton("G")); H.setOnAction(event -> handleLetterButton("H"));
        I.setOnAction(event -> handleLetterButton("I")); J.setOnAction(event -> handleLetterButton("J"));
        K.setOnAction(event -> handleLetterButton("K")); L.setOnAction(event -> handleLetterButton("L"));
        M.setOnAction(event -> handleLetterButton("M")); N.setOnAction(event -> handleLetterButton("N"));
        O.setOnAction(event -> handleLetterButton("O")); P.setOnAction(event -> handleLetterButton("P"));
        Q.setOnAction(event -> handleLetterButton("Q")); R.setOnAction(event -> handleLetterButton("R"));
        S.setOnAction(event -> handleLetterButton("S")); T.setOnAction(event -> handleLetterButton("T"));
        U.setOnAction(event -> handleLetterButton("U")); V.setOnAction(event -> handleLetterButton("V"));
        W.setOnAction(event -> handleLetterButton("W")); X.setOnAction(event -> handleLetterButton("X"));
        Y.setOnAction(event -> handleLetterButton("Y")); Z.setOnAction(event -> handleLetterButton("Z"));  
    }
	
	private void handleLetterButton(String letter) {
	    if (currGuess.getText().length() < 5) {
	        currGuess.setText(currGuess.getText() + letter);
	    } else {
	        showMaxLengthAlert();
	    }
	}
	
	private void settingupGameBoardColors() {
		for (int i =0; i<6;i++) {
	        ButtonBar row = gameBoardLi.get(i);
	        List<Node> buttons = row.getButtons();
	        for (int j = 0; j < 5; j++) {
	            Button btn = (Button) buttons.get(j);
		        btn.setStyle("-fx-background-color: white;");
		        btn.setOpacity(1);
	        }
		}
	}
    

	public String getNewWord() {
	     // Initialize the list List<String> allWords = new ArrayList<>(); 
	    Scanner infile = null;
	    try {
	    	//System.out.println(System.getProperty("user.dir"));

	        infile = new Scanner(new FileReader("./src/application/allWords.txt"));
	        while (infile.hasNextLine()) {
	            String line = infile.nextLine();
	            StringTokenizer tokenizer = new StringTokenizer(line);

	            while (tokenizer.hasMoreTokens()) {  // Check if more tokens are available
	                String currWord = tokenizer.nextToken().toUpperCase();
	                allWords.add(currWord);
	            }
	        }
	        shuffleList(allWords);  // Shuffle using a custom method
	        System.out.println(allWords.get(0));
	        if (!allWords.isEmpty()) {return allWords.get(0);}
	    } catch (FileNotFoundException e) {
	        System.out.println("File not found");
	        e.printStackTrace();
	        return null;  // Return null or consider throwing a RuntimeException
	    } finally {
	        if (infile != null) {infile.close();}
	    }
	    return null;  // Return null if no words were added or other issues occurred
	}
	
	private void showMaxLengthAlert() {
	    Alert alert = new Alert(AlertType.INFORMATION);
	    alert.setTitle("Maximum Length Reached");
	    alert.setHeaderText(null);
	    alert.setContentText("You cannot enter more than 5 letters!");
	    alert.showAndWait();
	}

	private void shuffleList(List<String> list) {
	    Random rnd = new Random();
	    for (int i = list.size() - 1; i > 0; i--) {
	        int index = rnd.nextInt(i + 1);
	        // Simple swap
	        String a = list.get(index);
	        list.set(index, list.get(i));
	        list.set(i, a);
	    }
	}

	private void setupEnterButton() {
	    enterBtn.setOnAction(event -> {
	        String guess = currGuess.getText();
	        if (guess.length() == 5 && allWords.contains(guess)) {
	            processGuess(guess);
	            currGuess.setText(""); // Reset the text after processing
	        } 
	        else if (guess.length() == 5 && !allWords.contains(guess)) {
	    	    Alert alert = new Alert(AlertType.INFORMATION);
	    	    alert.setTitle("Invalid Word");
	    	    alert.setHeaderText(null);
	    	    alert.setContentText("This word is not in the word list. Try another word!");
	    	    alert.showAndWait();
	        }
	        else {
	            showInvalidGuessAlert();
	        }
	    });
	}
	
	private void processGuess(String guess) {
	    if (numTries < gameBoard.length) {
	        ButtonBar row = gameBoardLi.get(numTries);
	        List<Node> buttons = row.getButtons();
	        for (int i = 0; i < guess.length(); i++) {
	            Button btn = (Button) buttons.get(i);
	            btn.setText(String.valueOf(guess.charAt(i)));
	            updateButtonStyle(btn, guess.charAt(i), i);
	        }
	        numTries++;
	        checkWinCondition(guess);
	        
	    } else {
	    	handleShowStats();
	        //showGameOver(false);
	    }
	}
	private void updateButtonStyle(Button btn, char guessedChar, int index) {
	    String charSeq = String.valueOf(guessedChar);
	    if (wordToGuess.charAt(index) == guessedChar) {
	        btn.setStyle("-fx-background-color: green;");
	    } else if (wordToGuess.contains(charSeq)) {
	        btn.setStyle("-fx-background-color: yellow;");
	    } else {
	    	for (Button i: keyboardLi) {
	    		if (i.getId().contains(charSeq)) {
	    			i.setStyle("-fx-background-color: gray;");
	    			btn.setStyle("-fx-background-color: gray;");
	    		}
	    	}
	    }
	}
	
	private void checkWinCondition(String guess) {
	    if (guess.equalsIgnoreCase(wordToGuess)) {
	        won = true;
	        writingNewStats(true,  numTries);
	        handleShowStats();
	        disableGameOver();
	    } else if (numTries >= gameBoard.length) {
	    	writingNewStats(false,  numTries);
	    	handleShowStats();
	    }
	}
	
	private void disableGameOver() {
		for (Button i: keyboardLi) {
    		i.setDisable(true);
    	}
	}
	
	private void showInvalidGuessAlert() {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle("Invalid Guess");
	    alert.setHeaderText(null);
	    alert.setContentText("Please enter exactly 5 letters.");
	    alert.showAndWait();
	}
	
	
	private void setupResetButton() {
	    resetBtn.setOnAction(event -> {
	    	for (int i =0; i<6; i++) {
		    	ButtonBar row = gameBoardLi.get(i);
		        List<Node> buttons = row.getButtons();
		        for (int j = 0; j < 5; j++) {
		            Button btn = (Button) buttons.get(j);
		            btn.setText(String.valueOf(""));
			        btn.setStyle("-fx-background-color: white;");
		        }
	    	}
	        for (Button letterBtn: keyboardLi) {
	        	letterBtn.setStyle(null);
	    	}
	        currGuess.setText("");
	        numTries =0;
	        wordToGuess = getNewWord();
	        
			for (Button i: keyboardLi) {
	    		i.setDisable(false);
	    	}
	    });
	}
	
	private void setupSaveButton() {
	    saveBtn.setOnAction(event -> {
	        File statsFile = new File("./src/application/CurrentGameData.txt");
	        List<String> lines = new ArrayList<>();
	        for (int i =0; i<6; i++) {
		    	ButtonBar row = gameBoardLi.get(i);
		        List<Node> buttons = row.getButtons();
		        for (int j = 0; j < 5; j++) {
		            lines.add(buttons.get(j).getAccessibleText());
		        }
	    	}
	        try (FileWriter writer = new FileWriter(statsFile, false)) {
	            for (String line : lines) {
	                writer.write(line + "\n");
	            }
	            writer.write("Tries: "+ numTries);
	            writer.write("Word to Guess: " + wordToGuess);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}
	
	private void setupLoadButton() {
	    loadBtn.setOnAction(event -> {
	    	Scanner infile = null;
	    	List<String> lines = new ArrayList<>();
		    try {
		    	//System.out.println(System.getProperty("user.dir"));
		        infile = new Scanner(new FileReader("./src/application/CurrentGameData.txt"));
		        while (infile.hasNextLine()) {
		            String line = infile.nextLine();
		            StringTokenizer tokenizer = new StringTokenizer(line);

		            while (tokenizer.hasMoreTokens()) {  // Check if more tokens are available
		                String currWord = tokenizer.nextToken().toUpperCase();
		                lines.add(currWord);
		            }
		        }
		    }
		    catch (Exception e) {
	            e.printStackTrace();
	        }
	    });
	}
}
