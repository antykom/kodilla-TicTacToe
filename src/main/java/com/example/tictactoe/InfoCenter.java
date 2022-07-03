package com.example.tictactoe;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class InfoCenter {
    private final StackPane pane;
    private final Label message, playerXLabel, playerOLabel, roundLabel;
    private final Button startGameButtonPvP, startGameButtonPvC, loadGameButton;
    private final Button startLevelEasyButton, startLevelMediumButton, startLevelHardButton;
    private final Button acceptNameButton, acceptRoundsButton, nextRoundButton;
    private final TextField nameXPlayer, nameOPlayer, roundsNumberField;
    private int roundNumbers;
    private String nameX, nameO;

    File savedGamesFile = new File("src/main/resources/savedGames.txt");
    private Deque<Game> gamesSaved = new ArrayDeque<>();

    private int scorePlayerX = 0;
    private int scorePlayerO = 0;
    private int round = 1;
    private boolean gameWithComp = false;
    private boolean levelEasy = false;
    private boolean levelMedium = false;
    private boolean levelHard = false;


    public InfoCenter() {
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTER_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY(UIConstants.INFO_CENTER_HEIGHT / 2);

        message = new Label("Tic-Tac-Toe BIG EDITION");
        message.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTER_HEIGHT);
        message.setFont(Font.font(24));
        message.setAlignment(Pos.CENTER);
        message.setTranslateY(-20);
        pane.getChildren().add(message);

        playerXLabel = new Label();
        playerXLabel.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTER_HEIGHT);
        playerXLabel.setFont(Font.font(24));
        playerXLabel.setAlignment(Pos.CENTER_LEFT);
        playerXLabel.setTranslateY(-20);
        pane.getChildren().add(playerXLabel);

        playerOLabel = new Label();
        playerOLabel.setMinSize(UIConstants.APP_WIDTH, UIConstants.INFO_CENTER_HEIGHT);
        playerOLabel.setFont(Font.font(24));
        playerOLabel.setAlignment(Pos.CENTER_RIGHT);
        playerOLabel.setTranslateY(-20);
        pane.getChildren().add(playerOLabel);

        roundLabel = new Label();
        roundLabel.setMinSize(135, 30);
        roundLabel.setFont(Font.font(24));
        roundLabel.setAlignment(Pos.CENTER);
        roundLabel.setTranslateY(20);
        pane.getChildren().add(roundLabel);

        startGameButtonPvP = new Button("User vs User");
        startGameButtonPvP.setMinSize(135, 30);
        startGameButtonPvP.setTranslateX(-150);
        startGameButtonPvP.setTranslateY(20);
        pane.getChildren().add(startGameButtonPvP);

        startGameButtonPvC = new Button("User vs Comp");
        startGameButtonPvC.setMinSize(135, 30);
        startGameButtonPvC.setTranslateX(0);
        startGameButtonPvC.setTranslateY(20);
        pane.getChildren().add(startGameButtonPvC);

        loadGameButton = new Button("Load Game");
        loadGameButton.setMinSize(135, 30);
        loadGameButton.setTranslateX(150);
        loadGameButton.setTranslateY(20);
        pane.getChildren().add(loadGameButton);

        startLevelEasyButton = new Button("Easy");
        startLevelEasyButton.setVisible(false);
        startLevelEasyButton.setMinSize(135, 30);
        startLevelEasyButton.setTranslateX(-150);
        startLevelEasyButton.setTranslateY(20);
        pane.getChildren().add(startLevelEasyButton);

        startLevelMediumButton = new Button("Medium");
        startLevelMediumButton.setVisible(false);
        startLevelMediumButton.setMinSize(135, 30);
        startLevelMediumButton.setTranslateX(0);
        startLevelMediumButton.setTranslateY(20);
        pane.getChildren().add(startLevelMediumButton);

        startLevelHardButton = new Button("Hard");
        startLevelHardButton.setVisible(false);
        startLevelHardButton.setMinSize(135, 30);
        startLevelHardButton.setTranslateX(150);
        startLevelHardButton.setTranslateY(20);
        pane.getChildren().add(startLevelHardButton);

        nameXPlayer = new TextField("USER");
        nameXPlayer.setVisible(false);
        nameXPlayer.setMinSize(100, 30);
        nameXPlayer.setTranslateX(-150);
        nameXPlayer.setTranslateY(20);
        pane.getChildren().add(nameXPlayer);

        nameOPlayer = new TextField("COMP");
        nameOPlayer.setVisible(false);
        nameOPlayer.setMinSize(100, 30);
        nameOPlayer.setTranslateX(150);
        nameOPlayer.setTranslateY(20);
        pane.getChildren().add(nameOPlayer);

        acceptNameButton = new Button("ACCEPT");
        acceptNameButton.setVisible(false);
        acceptNameButton.setMinSize(135, 30);
        acceptNameButton.setTranslateX(0);
        acceptNameButton.setTranslateY(20);
        pane.getChildren().add(acceptNameButton);

        roundsNumberField = new TextField("1");
        roundsNumberField.setVisible(false);
        roundsNumberField.setMinSize(30, 30);
        roundsNumberField.setTranslateX(-70);
        roundsNumberField.setTranslateY(20);
        pane.getChildren().add(roundsNumberField);

        acceptRoundsButton = new Button("ACCEPT");
        acceptRoundsButton.setVisible(false);
        acceptRoundsButton.setMinSize(135, 30);
        acceptRoundsButton.setTranslateX(20);
        acceptRoundsButton.setTranslateY(20);
        pane.getChildren().add(acceptRoundsButton);

        nextRoundButton = new Button("Next round");
        nextRoundButton.setVisible(false);
        nextRoundButton.setMinSize(135, 30);
        nextRoundButton.setTranslateX(0);
        nextRoundButton.setTranslateY(20);
        pane.getChildren().add(nextRoundButton);
    }

    public StackPane getStackPane() {
        return pane;
    }

    public void updateMessage(String message) {
        this.message.setText(message);
    }

    public void updatePlayersLabel(String name1, String name2) {
        playerXLabel.setText(name1 + ": " + scorePlayerX);
        playerOLabel.setText(scorePlayerO + " :" + name2);
    }

    public void updateRoundNumber() {
        roundLabel.setText("Round: " + round + "/" + getRoundNumbers());

    }

    public void showStartButton() {
        startGameButtonPvP.setVisible(true);
        startGameButtonPvC.setVisible(true);
        loadGameButton.setVisible(true);
    }

    public void hideStartButton() {
        startGameButtonPvP.setVisible(false);
        startGameButtonPvC.setVisible(false);
        loadGameButton.setVisible(false);
    }

    public void showNextRoundButton() {
        nextRoundButton.setVisible(true);
    }

    public void hideNextRoundButton() {
        nextRoundButton.setVisible(false);
    }

    public void showLevelButton() {
        startLevelEasyButton.setVisible(true);
        startLevelMediumButton.setVisible(true);
        startLevelHardButton.setVisible(true);
    }

    public void hideLevelButton() {
        startLevelEasyButton.setVisible(false);
        startLevelMediumButton.setVisible(false);
        startLevelHardButton.setVisible(false);
    }

    public void showUserNameFields() {
        nameXPlayer.setVisible(true);
        if (!gameWithComp) {
            nameOPlayer.setVisible(true);
        }
        acceptNameButton.setVisible(true);
    }

    public void hideUserNameFields() {
        nameXPlayer.setVisible(false);
        nameOPlayer.setVisible(false);
        acceptNameButton.setVisible(false);
    }

    public void showRoundsNumberFields() {
        roundsNumberField.setVisible(true);
        acceptRoundsButton.setVisible(true);
    }

    public void hideRoundsNumberFields() {
        roundsNumberField.setVisible(false);
        acceptRoundsButton.setVisible(false);
    }

    public void showRound() {
        roundLabel.setVisible(true);
    }

    public void hideRound() {
        roundLabel.setVisible(false);
    }

    public void saveGame() {
        Game gameToSave = new Game(nameXPlayer.getText(), nameOPlayer.getText(), scorePlayerX, scorePlayerO, round,
                roundNumbers, gameWithComp, levelEasy, levelMedium, levelHard);
        gamesSaved.addFirst(gameToSave);

        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(savedGamesFile));
            oos.writeObject(gamesSaved);
            oos.close();
        } catch (Exception e) {
            System.out.println(e);

        }

    }

    public void loadGame() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(savedGamesFile));
            Object readArray = ois.readObject();
            if (readArray instanceof HashMap) {
                ArrayDeque<Game> array = new ArrayDeque<>((ArrayDeque) readArray);
                Game gameToLoad = array.getFirst();
                nameX = gameToLoad.playerXName;
                nameO = gameToLoad.playerOName;
                scorePlayerX = gameToLoad.scorePlayerX;
                scorePlayerO = gameToLoad.scorePlayerO;
                round = gameToLoad.round;
                roundNumbers = gameToLoad.totalRounds;
                gameWithComp = gameToLoad.gameWithComp;
                levelEasy = gameToLoad.easy;
                levelMedium = gameToLoad.medium;
                levelHard = gameToLoad.hard;
            }
            ois.close();
        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public void setNextRoundButtonOnAction(EventHandler<ActionEvent> onAction) {
        nextRoundButton.setOnAction(onAction);
    }

    public void setAcceptRoundButtonOnAction(EventHandler<ActionEvent> onAction) {
        acceptRoundsButton.setOnAction(onAction);
    }

    public void setStartButtonPvPOnAction(EventHandler<ActionEvent> onAction) {
        startGameButtonPvP.setOnAction(onAction);
    }

    public void setStartButtonPvCOnAction(EventHandler<ActionEvent> onAction) {
        startGameButtonPvC.setOnAction(onAction);
    }

    public void setLoadGameButtonOnAction(EventHandler<ActionEvent> onAction) {
        loadGameButton.setOnAction(onAction);
    }

    public void setUsersNameButtonOnAction(EventHandler<ActionEvent> onAction) {
        acceptNameButton.setOnAction(onAction);
    }

    public void setStartLevelEasyButtonOnAction(EventHandler<ActionEvent> onAction) {
        startLevelEasyButton.setOnAction(onAction);
    }

    public void setStartLevelMediumButtonOnAction(EventHandler<ActionEvent> onAction) {
        startLevelMediumButton.setOnAction(onAction);
    }

    public void setStartLevelHardButtonOnAction(EventHandler<ActionEvent> onAction) {
        startLevelHardButton.setOnAction(onAction);
    }

    public boolean isGameWithComp() {
        return gameWithComp;
    }

    public void setGameWithComp(boolean gameWithComp) {
        this.gameWithComp = gameWithComp;
    }

    public void setLevelEasy(boolean levelEasy) {
        this.levelEasy = levelEasy;
    }

    public void setLevelMedium(boolean levelMedium) {
        this.levelMedium = levelMedium;
    }

    public void setLevelHard(boolean levelHard) {
        this.levelHard = levelHard;
    }

    public boolean isLevelEasy() {
        return levelEasy;
    }

    public boolean isLevelMedium() {
        return levelMedium;
    }

    public boolean isLevelHard() {
        return levelHard;
    }

    public String getNameX() {
        return nameX;
    }

    public String getNameO() {
        return nameO;
    }

    public void setNameX() {
        nameX = nameXPlayer.getText();
    }

    public void setNameO() {
        nameO = nameOPlayer.getText();
    }

    public int getRoundNumbers() {
        return roundNumbers;
    }

    public void setRoundNumbers() {
        roundNumbers = Integer.parseInt(roundsNumberField.getText());
    }

    public int getScorePlayerX() {
        return scorePlayerX;
    }

    public void setScorePlayerX(int scorePlayerX) {
        this.scorePlayerX = scorePlayerX;
    }

    public int getScorePlayerO() {
        return scorePlayerO;
    }

    public void setScorePlayerO(int scorePlayerO) {
        this.scorePlayerO = scorePlayerO;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }
}

class Game {
    String playerXName;
    String playerOName;
    int scorePlayerX;
    int scorePlayerO;
    int round;
    int totalRounds;
    boolean gameWithComp;
    boolean easy;
    boolean medium;
    boolean hard;

    public Game(String playerXName, String playerOName, int scorePlayerX, int scorePlayerY, int round, int totalRounds,
                boolean gameWithComp, boolean easy, boolean medium, boolean hard) {
        this.playerXName = playerXName;
        this.playerOName = playerOName;
        this.scorePlayerX = scorePlayerX;
        this.scorePlayerO = scorePlayerY;
        this.round = round;
        this.totalRounds = totalRounds;
        this.gameWithComp = gameWithComp;
        this.easy = easy;
        this.medium = medium;
        this.hard = hard;
    }
}
