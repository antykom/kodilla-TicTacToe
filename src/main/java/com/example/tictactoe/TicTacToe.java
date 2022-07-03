package com.example.tictactoe;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TicTacToe extends Application {

    private InfoCenter infoCenter;
    private TileBoard tileBoard;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            BorderPane root = new BorderPane();
            Scene scene = new Scene(root, UIConstants.APP_WIDTH, UIConstants.APP_HEIGHT);
            initLayout(root);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initLayout(BorderPane root) {
        initInfoCenter(root);
        initTileBoard(root);
    }

    private void initInfoCenter(BorderPane root) {
        infoCenter = new InfoCenter();
        infoCenter.setStartButtonPvPOnAction(startNewGamePvP());
        infoCenter.setStartButtonPvCOnAction(startNewGamePvC());
        infoCenter.setStartLevelEasyButtonOnAction(startNewGamePvCEasy());
        infoCenter.setStartLevelMediumButtonOnAction(startNewGamePvCMedium());
        infoCenter.setStartLevelHardButtonOnAction(startNewGamePvCHard());
        infoCenter.setUsersNameButtonOnAction(setUsersName());
        infoCenter.setAcceptRoundButtonOnAction(setRoundsNum());
        infoCenter.setNextRoundButtonOnAction(setNextRound());
        infoCenter.setLoadGameButtonOnAction(setLoadGame());

        root.getChildren().add(infoCenter.getStackPane());
    }

    private EventHandler<ActionEvent> startNewGamePvP() {
        return new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                infoCenter.hideStartButton();
                infoCenter.showUserNameFields();
                infoCenter.updateMessage("Enter name:");
            }
        };
    }

    private EventHandler<ActionEvent> startNewGamePvC() {
        return new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                infoCenter.hideStartButton();
                infoCenter.updateMessage("Enter name:");
                infoCenter.setGameWithComp(true);
                infoCenter.showUserNameFields();
            }
        };
    }

    private EventHandler<ActionEvent> setLoadGame(){
        return new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                infoCenter.loadGame();
                infoCenter.updateMessage(infoCenter.getNameX().concat("'s turn"));
                infoCenter.updatePlayersLabel(infoCenter.getNameX(), infoCenter.getNameO());
                infoCenter.updateRoundNumber();
                infoCenter.showRound();
                infoCenter.hideStartButton();
                tileBoard.startNewGame();
            }
        };
    }

    private EventHandler<ActionEvent> setUsersName() {
        return new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                infoCenter.setNameX();
                infoCenter.setNameO();
                infoCenter.hideUserNameFields();
                infoCenter.showRoundsNumberFields();
                infoCenter.updateMessage("How many rounds:");
            }
        };
    }

    private EventHandler<ActionEvent> setRoundsNum() {
        return new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                infoCenter.hideRoundsNumberFields();
                infoCenter.setRoundNumbers();
                if (infoCenter.isGameWithComp()) {
                    infoCenter.showLevelButton();
                    infoCenter.updateMessage("Choose level:");
                } else {
                    infoCenter.updateMessage(infoCenter.getNameX().concat("'s turn"));
                    infoCenter.updatePlayersLabel(infoCenter.getNameX(), infoCenter.getNameO());
                    infoCenter.updateRoundNumber();
                    infoCenter.showRound();
                    tileBoard.startNewGame();
                }
            }
        };
    }

    private EventHandler<ActionEvent> startNewGamePvCEasy() {
        return new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                infoCenter.hideLevelButton();
                infoCenter.updateMessage(infoCenter.getNameX().concat("'s turn"));
                infoCenter.setLevelEasy(true);
                infoCenter.updatePlayersLabel(infoCenter.getNameX(), infoCenter.getNameO());
                infoCenter.updateRoundNumber();
                infoCenter.showRound();
                tileBoard.startNewGame();
            }
        };
    }

    private EventHandler<ActionEvent> startNewGamePvCMedium() {
        return new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                infoCenter.hideLevelButton();
                infoCenter.updateMessage(infoCenter.getNameX().concat("'s turn"));
                infoCenter.setLevelMedium(true);
                infoCenter.updatePlayersLabel(infoCenter.getNameX(), infoCenter.getNameO());
                infoCenter.updateRoundNumber();
                infoCenter.showRound();
                tileBoard.startNewGame();
            }
        };
    }

    private EventHandler<ActionEvent> startNewGamePvCHard() {
        return new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                infoCenter.hideLevelButton();
                infoCenter.updateMessage(infoCenter.getNameX().concat("'s turn"));
                infoCenter.setLevelHard(true);
                infoCenter.updatePlayersLabel(infoCenter.getNameX(), infoCenter.getNameO());
                tileBoard.startNewGame();
            }
        };
    }

    private EventHandler<ActionEvent> setNextRound(){
        return new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                infoCenter.hideNextRoundButton();
                tileBoard.startNewGame();
            }
        };
    }

    private void initTileBoard(BorderPane root) {
        tileBoard = new TileBoard(infoCenter);
        root.getChildren().add(tileBoard.getStackPane());
    }
}
