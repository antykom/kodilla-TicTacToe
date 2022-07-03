package com.example.tictactoe;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.ArrayList;
import java.util.Random;

public class TileBoard {
    final private InfoCenter infoCenter;
    final private StackPane pane;
    final private Tile[][] tiles = new Tile[10][10];
    final private ArrayList<XY> coordination = new ArrayList<>();
    final private Line winningLine;


    private char playerTurn = 'X';
    private int round = 1;
    private String playerName;
    private boolean isEndOfGame = false;
    final private Random rnd = new Random();


    private static class XY {
        final private int x;
        final private int y;

        public XY(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    public TileBoard(InfoCenter infoCenter) {
        this.infoCenter = infoCenter;
        pane = new StackPane();
        pane.setMinSize(UIConstants.APP_WIDTH, UIConstants.TITLE_BOARD_HEIGHT);
        pane.setTranslateX(UIConstants.APP_WIDTH / 2);
        pane.setTranslateY((UIConstants.TITLE_BOARD_HEIGHT / 2) + UIConstants.INFO_CENTER_HEIGHT);

        addAllTiles();
        winningLine = new Line();
        pane.getChildren().add(winningLine);
    }

    private void addAllTiles() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                Tile tile = new Tile();
                tile.getStackPane().setTranslateX((col * 50) - 225);
                tile.getStackPane().setTranslateY((row * 50) - 225);
                pane.getChildren().add(tile.getStackPane());
                tiles[row][col] = tile;

            }
        }
    }

    public void startNewGame() {
        isEndOfGame = false;
        playerTurn = 'X';
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                tiles[row][col].setValue("");
            }
        }
        winningLine.setVisible(false);
    }

    public void changePlayerTurn() {
        if (playerTurn == 'X') {
            playerTurn = 'O';
            playerName = infoCenter.getNameO();
        } else {
            playerTurn = 'X';
            playerName = infoCenter.getNameX();
        }
        infoCenter.updateMessage(playerName + "'s turn");
    }

    public void compTurnEasy() {
        boolean isEmpty = true;
        int tmpRow;
        int tmpCol;

        while (isEmpty) {
            tmpRow = rnd.nextInt(9);
            tmpCol = rnd.nextInt(9);

            if (tiles[tmpRow][tmpCol].getValue().isEmpty()) {
                tiles[tmpRow][tmpCol].setValue("O");
                isEmpty = false;
            }

        }
    }

    public void compTurnMedium() {
        boolean isEmpty = true;
        int index;
        int tmpRow;
        int tmpCol;

        coordinationCollect();

        while (isEmpty) {
            index = rnd.nextInt(coordination.size());

            tmpRow = getCoordinationX(index);
            tmpCol = getCoordinationY(index);

            if (tiles[tmpRow][tmpCol].getValue().isEmpty()) {
                tiles[tmpRow][tmpCol].setValue("O");
                isEmpty = false;
            }

        }
        coordination.clear();
    }

    public void compTurnHard() {
        if (check4Rows()) {

            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 7; col++) {
                    if (tiles[row][col].getValue().equals(tiles[row][col + 1].getValue()) &&
                            tiles[row][col].getValue().equals(tiles[row][col + 2].getValue()) &&
                            tiles[row][col].getValue().equals(tiles[row][col + 3].getValue()) &&
                            tiles[row][col].getValue().equals("X")) {
                        if ((col - 1 >= 0) && tiles[row][col - 1].getValue().isEmpty()
                                && !tiles[row][col - 1].getValue().equals("O")) {
                            tiles[row][col - 1].setValue("O");
                        } else if ((col + 4 <= 9) && tiles[row][col + 4].getValue().isEmpty()) {
                            tiles[row][col + 4].setValue("O");
                        } else {
                            compTurnMedium();
                        }
                    }
                }
            }

        } else if (check4Cols()) {

            for (int col = 0; col < 10; col++) {
                for (int row = 0; row < 7; row++) {
                    if (tiles[row][col].getValue().equals(tiles[row + 1][col].getValue()) &&
                            tiles[row][col].getValue().equals(tiles[row + 2][col].getValue()) &&
                            tiles[row][col].getValue().equals(tiles[row + 3][col].getValue()) &&
                            tiles[row][col].getValue().equals("X")) {
                        if ((row - 1 >= 0) && tiles[row - 1][col].getValue().isEmpty()
                                && !tiles[row - 1][col].getValue().equals("O")) {
                            tiles[row - 1][col].setValue("O");
                        } else if ((row + 4 <= 9) && tiles[row + 4][col].getValue().isEmpty()) {
                            tiles[row + 4][col].setValue("O");
                        } else {
                            compTurnMedium();
                        }
                    }
                }
            }

        } else if (check4TopRightToBottomLeft()) {

            for (int row = 0; row < 6; row++) {
                for (int col = 9; col > 3; col--) {
                    if (tiles[row][col].getValue().equals(tiles[row + 1][col - 1].getValue()) &&
                            tiles[row + 1][col - 1].getValue().equals(tiles[row + 2][col - 2].getValue()) &&
                            tiles[row + 2][col - 2].getValue().equals(tiles[row + 3][col - 3].getValue()) &&
                            tiles[row][col].getValue().equals("X")) {
                        if ((row - 1 >= 0) && tiles[row - 1][col + 1].getValue().isEmpty()) {
                            tiles[row - 1][col + 1].setValue("O");
                        } else if (tiles[row + 4][col - 4].getValue().isEmpty()) {
                            tiles[row + 4][col - 4].setValue("O");
                        } else {
                            compTurnMedium();
                        }

                    }
                }
            }

        } else if (check4TopLeftToBottomRight()) {

            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    if (tiles[row][col].getValue().equals(tiles[row + 1][col + 1].getValue()) &&
                            tiles[row + 1][col + 1].getValue().equals(tiles[row + 2][col + 2].getValue()) &&
                            tiles[row + 2][col + 2].getValue().equals(tiles[row + 3][col + 3].getValue()) &&
                            tiles[row][col].getValue().equals("X")) {
                        if ((row - 1 >= 0) && (col - 1 >= 0) & tiles[row - 1][col - 1].getValue().isEmpty()) {
                            tiles[row - 1][col - 1].setValue("O");
                        } else if (tiles[row + 4][col + 4].getValue().isEmpty()) {
                            tiles[row + 4][col + 4].setValue("O");
                        } else {
                            compTurnMedium();
                        }
                    }
                }
            }

        } else if (check3Rows()) {

            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 8; col++) {
                    if (tiles[row][col].getValue().equals(tiles[row][col + 1].getValue()) &&
                            tiles[row][col].getValue().equals(tiles[row][col + 2].getValue()) &&
                            tiles[row][col].getValue().equals("X")) {
                        if ((col - 1 >= 0) && !tiles[row][col - 1].getValue().isEmpty()) {
                            tiles[row][col - 1].setValue("O");
                        } else if ((col + 3 <= 9) && tiles[row][col + 3].getValue().isEmpty()) {
                            tiles[row][col + 3].setValue("O");
                        } else {
                            compTurnMedium();
                        }
                    }
                }
            }

        } else if (check3Cols()) {

            for (int col = 0; col < 10; col++) {
                for (int row = 0; row < 8; row++) {
                    if (tiles[row][col].getValue().equals(tiles[row + 1][col].getValue()) &&
                            tiles[row][col].getValue().equals(tiles[row + 2][col].getValue()) &&
                            tiles[row][col].getValue().equals("X")) {
                        if ((row - 1 >= 0) && !tiles[row - 1][col].getValue().isEmpty()) {
                            tiles[row - 1][col].setValue("O");
                        } else if ((row + 3 <= 9) && tiles[row + 3][col].getValue().isEmpty()) {
                            tiles[row + 3][col].setValue("O");
                        } else {
                            compTurnMedium();
                        }
                    }
                }
            }

        } else if (check3TopRightToBottomLeft()) {

            for (int row = 0; row < 6; row++) {
                for (int col = 9; col > 3; col--) {
                    if (tiles[row][col].getValue().equals(tiles[row + 1][col - 1].getValue()) &&
                            tiles[row + 1][col - 1].getValue().equals(tiles[row + 2][col - 2].getValue()) &&
                            tiles[row][col].getValue().equals("X")) {
                        if ((row - 1 >= 0) && tiles[row - 1][col + 1].getValue().isEmpty()) {
                            tiles[row - 1][col + 1].setValue("O");
                        } else if (tiles[row + 3][col - 3].getValue().isEmpty()) {
                            tiles[row + 3][col - 3].setValue("O");
                        } else {
                            compTurnMedium();
                        }
                    }
                }
            }

        } else if (check3TopLeftToBottomLeft()) {

            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    if (tiles[row][col].getValue().equals(tiles[row + 1][col + 1].getValue()) &&
                            tiles[row + 1][col + 1].getValue().equals(tiles[row + 2][col + 2].getValue()) &&
                            tiles[row][col].getValue().equals("X")) {
                        if ((row - 1 >= 0) && (col - 1 >= 0) & tiles[row - 1][col - 1].getValue().isEmpty()) {
                            tiles[row - 1][col - 1].setValue("O");
                        } else if (tiles[row + 3][col + 3].getValue().isEmpty()) {
                            tiles[row + 3][col + 3].setValue("O");
                        } else {
                            compTurnMedium();
                        }

                    }
                }
            }
        } else {

            compTurnMedium();

        }

        coordination.clear();
    }

    private boolean check4Rows() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 6; col++) {
                if (tiles[row][col].getValue().equals(tiles[row][col + 1].getValue()) &&
                        tiles[row][col].getValue().equals(tiles[row][col + 2].getValue()) &&
                        tiles[row][col].getValue().equals(tiles[row][col + 3].getValue()) &&
                        tiles[row][col].getValue().equals("X")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check3Rows() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 6; col++) {
                if (tiles[row][col].getValue().equals(tiles[row][col + 1].getValue()) &&
                        tiles[row][col].getValue().equals(tiles[row][col + 2].getValue()) &&
                        tiles[row][col].getValue().equals("X")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check4Cols() {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 6; row++) {
                if (tiles[row][col].getValue().equals(tiles[row + 1][col].getValue()) &&
                        tiles[row][col].getValue().equals(tiles[row + 2][col].getValue()) &&
                        tiles[row][col].getValue().equals(tiles[row + 3][col].getValue()) &&
                        tiles[row][col].getValue().equals("X")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check3Cols() {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 6; row++) {
                if (tiles[row][col].getValue().equals(tiles[row + 1][col].getValue()) &&
                        tiles[row][col].getValue().equals(tiles[row + 2][col].getValue()) &&
                        tiles[row][col].getValue().equals("X")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check4TopLeftToBottomRight() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (tiles[row][col].getValue().equals(tiles[row + 1][col + 1].getValue()) &&
                        tiles[row + 1][col + 1].getValue().equals(tiles[row + 2][col + 2].getValue()) &&
                        tiles[row + 2][col + 2].getValue().equals(tiles[row + 3][col + 3].getValue()) &&
                        tiles[row][col].getValue().equals("X")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check4TopRightToBottomLeft() {
        for (int row = 0; row < 6; row++) {
            for (int col = 9; col > 3; col--) {
                if (tiles[row][col].getValue().equals(tiles[row + 1][col - 1].getValue()) &&
                        tiles[row + 1][col - 1].getValue().equals(tiles[row + 2][col - 2].getValue()) &&
                        tiles[row + 2][col - 2].getValue().equals(tiles[row + 3][col - 3].getValue()) &&
                        tiles[row][col].getValue().equals("X")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check3TopLeftToBottomLeft() {
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 6; col++) {
                if (tiles[row][col].getValue().equals(tiles[row + 1][col + 1].getValue()) &&
                        tiles[row + 1][col + 1].getValue().equals(tiles[row + 2][col + 2].getValue()) &&
                        tiles[row][col].getValue().equals("X")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean check3TopRightToBottomLeft() {
        for (int row = 0; row < 6; row++) {
            for (int col = 9; col > 3; col--) {
                if (tiles[row][col].getValue().equals(tiles[row + 1][col - 1].getValue()) &&
                        tiles[row + 1][col - 1].getValue().equals(tiles[row + 2][col - 2].getValue()) &&
                        tiles[row][col].getValue().equals("X")) {
                    return true;
                }
            }
        }
        return false;
    }

    private void coordinationCollect() {
        for (int row = 0; row <= 9; row++) {
            for (int col = 0; col <= 9; col++) {
                if (tiles[row][col].getValue().equals("X")) {
                    coordination.add(new XY(row, col));
                }
            }
        }
    }

    private int getCoordinationY(int index) {
        int y = coordination.get(index).getY() + rnd.nextInt(2) - 1;
        if (y >= 0 && y <= 9) {
            return y;
        } else {
            return rnd.nextInt(9);
        }
    }

    private int getCoordinationX(int index) {
        int x = coordination.get(index).getX() + rnd.nextInt(2) - 1;
        if (x >= 0 && x <= 9) {
            return x;
        } else {
            return rnd.nextInt(9);
        }

    }

    public String getPlayerTurn() {
        return String.valueOf(playerTurn);
    }

    public StackPane getStackPane() {
        return pane;
    }

    public void checkForWinner() {
        checkRowsForWinner();
        checkColsForWinner();
        checkTopLeftToBottomRightForWinner();
        checkTopRightToBottomLeftForWinner();
        checkForStalemate();
    }

    private void checkForStalemate() {
        if (!isEndOfGame) {
            for (int row = 0; row < 10; row++) {
                for (int col = 0; col < 10; col++) {
                    if (tiles[row][col].getValue().isEmpty()) {
                        return;
                    }
                }
            }
            if (infoCenter.getRound() < infoCenter.getRoundNumbers()) {
                endRound();
            } else {
                endGame("", new WinningTiles(tiles[1][1], tiles[1][1], tiles[1][1]));
            }
        }
    }

    private void checkTopLeftToBottomRightForWinner() {
        if (!isEndOfGame) {
            for (int row = 0; row < 6; row++) {
                for (int col = 0; col < 6; col++) {
                    if (tiles[row][col].getValue().equals(tiles[row + 1][col + 1].getValue()) &&
                            tiles[row + 1][col + 1].getValue().equals(tiles[row + 2][col + 2].getValue()) &&
                            tiles[row + 2][col + 2].getValue().equals(tiles[row + 3][col + 3].getValue()) &&
                            tiles[row + 3][col + 3].getValue().equals(tiles[row + 4][col + 4].getValue()) &&
                            !tiles[row][col].getValue().isEmpty()) {
                        String winner;
                        if (tiles[row][col].getValue().equals("X")) {
                            winner = infoCenter.getNameX();
                        } else {
                            winner = infoCenter.getNameO();
                        }
                        if (infoCenter.getRound() < infoCenter.getRoundNumbers()) {
                            endRound(winner, new WinningTiles(tiles[row][col], tiles[row + 2][col + 2],
                                    tiles[row + 4][col + 4]));
                        } else {
                            endGame(winner, new WinningTiles(tiles[row][col], tiles[row + 2][col + 2],
                                    tiles[row + 4][col + 4]));
                        }
                        return;

                    }
                }
            }
        }
    }

    private void checkTopRightToBottomLeftForWinner() {
        if (!isEndOfGame) {
            for (int row = 0; row < 6; row++) {
                for (int col = 9; col > 3; col--) {
                    if (tiles[row][col].getValue().equals(tiles[row + 1][col - 1].getValue()) &&
                            tiles[row + 1][col - 1].getValue().equals(tiles[row + 2][col - 2].getValue()) &&
                            tiles[row + 2][col - 2].getValue().equals(tiles[row + 3][col - 3].getValue()) &&
                            tiles[row + 3][col - 3].getValue().equals(tiles[row + 4][col - 4].getValue()) &&
                            !tiles[row][col].getValue().isEmpty()) {
                        String winner;
                        if (tiles[row][col].getValue().equals("X")) {
                            winner = infoCenter.getNameX();
                        } else {
                            winner = infoCenter.getNameO();
                        }
                        if (infoCenter.getRound() < infoCenter.getRoundNumbers()) {
                            endRound(winner, new WinningTiles(tiles[row][col], tiles[row + 2][col - 2],
                                    tiles[row + 4][col - 4]));
                        } else {
                            endGame(winner, new WinningTiles(tiles[row][col], tiles[row + 2][col - 2],
                                    tiles[row + 4][col - 4]));
                        }
                        return;
                    }
                }
            }
        }
    }

    private void checkColsForWinner() {
        if (!isEndOfGame) {
            for (int col = 0; col < 10; col++) {
                for (int row = 0; row < 6; row++) {
                    if (tiles[row][col].getValue().equals(tiles[row + 1][col].getValue()) &&
                            tiles[row][col].getValue().equals(tiles[row + 2][col].getValue()) &&
                            tiles[row][col].getValue().equals(tiles[row + 3][col].getValue()) &&
                            tiles[row][col].getValue().equals(tiles[row + 4][col].getValue()) &&
                            !tiles[row][col].getValue().isEmpty()) {
                        String winner;

                        if (tiles[row][col].getValue().equals("X")) {
                            winner = infoCenter.getNameX();
                        } else {
                            winner = infoCenter.getNameO();
                        }

                        if (infoCenter.getRound() < infoCenter.getRoundNumbers()) {
                            endRound(winner, new WinningTiles(tiles[row][col], tiles[row + 2][col], tiles[row + 4][col]));
                        } else {
                            endGame(winner, new WinningTiles(tiles[row][col], tiles[row + 2][col], tiles[row + 4][col]));
                        }
                        return;
                    }
                }
            }
        }
    }

    private void checkRowsForWinner() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 6; col++) {
                if (tiles[row][col].getValue().equals(tiles[row][col + 1].getValue()) &&
                        tiles[row][col].getValue().equals(tiles[row][col + 2].getValue()) &&
                        tiles[row][col].getValue().equals(tiles[row][col + 3].getValue()) &&
                        tiles[row][col].getValue().equals(tiles[row][col + 4].getValue()) &&
                        !tiles[row][col].getValue().isEmpty()) {
                    String winner;

                    if (tiles[row][col].getValue().equals("X")) {
                        winner = infoCenter.getNameX();
                    } else {
                        winner = infoCenter.getNameO();
                    }

                    if (infoCenter.getRound() < infoCenter.getRoundNumbers()) {
                        endRound(winner, new WinningTiles(tiles[row][col], tiles[row][col + 2], tiles[row][col + 4]));
                    } else {
                        endGame(winner, new WinningTiles(tiles[row][col], tiles[row][col + 2], tiles[row][col + 4]));
                    }
                    return;
                }
            }

        }
    }

    private void endRound(String winner, WinningTiles winningTiles) {
        drawWinningLine(winningTiles);
        infoCenter.updateMessage(winner + " wins round!!");
        infoCenter.saveGame();
        infoCenter.showNextRoundButton();
        infoCenter.setRound(infoCenter.getRound() + 1);
        infoCenter.updateRoundNumber();
        if (infoCenter.getNameX().equals(winner)) {
            infoCenter.setScorePlayerX(infoCenter.getScorePlayerX() + 1);
        } else if (infoCenter.getNameO().equals(winner)) {
            infoCenter.setScorePlayerO(infoCenter.getScorePlayerO() + 1);
        }
        infoCenter.saveGame();
        infoCenter.updatePlayersLabel(infoCenter.getNameX(), infoCenter.getNameO());
    }

    private void endRound() {
        infoCenter.updateMessage("Stalemate...");
        infoCenter.showNextRoundButton();
        infoCenter.setRound(infoCenter.getRound() + 1);
        infoCenter.updateRoundNumber();
        infoCenter.saveGame();

    }

    private void endGame(String winner, WinningTiles winningTiles) {
        isEndOfGame = true;
        if (infoCenter.getNameX().equals(winner)) {
            infoCenter.setScorePlayerX(infoCenter.getScorePlayerX() + 1);
        } else if (infoCenter.getNameO().equals(winner)) {
            infoCenter.setScorePlayerO(infoCenter.getScorePlayerO() + 1);
        }
        infoCenter.updatePlayersLabel(infoCenter.getNameX(), infoCenter.getNameO());
        drawWinningLine(winningTiles);
        if (infoCenter.getScorePlayerX() > infoCenter.getScorePlayerO()) {
            infoCenter.updateMessage(infoCenter.getNameX() + " wins game!!");
        } else if (infoCenter.getScorePlayerX() < infoCenter.getScorePlayerO()) {
            infoCenter.updateMessage(infoCenter.getNameO() + " wins game!!");
        } else {
            infoCenter.updateMessage("STALEMATE...");
        }
        infoCenter.hideRound();
        infoCenter.showStartButton();
        infoCenter.setGameWithComp(false);
        infoCenter.setLevelEasy(false);
        infoCenter.setLevelMedium(false);
        infoCenter.setLevelHard(false);
        infoCenter.setRound(1);
        infoCenter.setScorePlayerX(0);
        infoCenter.setScorePlayerX(0);
    }

    private void endGame() {
        isEndOfGame = true;
        if (infoCenter.getScorePlayerX() > infoCenter.getScorePlayerO()) {
            infoCenter.updateMessage(infoCenter.getNameX() + " wins game!!");
        } else if (infoCenter.getScorePlayerX() < infoCenter.getScorePlayerO()) {
            infoCenter.updateMessage(infoCenter.getNameO() + " wins game!!");
        } else {
            infoCenter.updateMessage("STALEMATE...");
        }
        infoCenter.hideRound();
        infoCenter.showStartButton();
        infoCenter.setGameWithComp(false);
        infoCenter.setLevelEasy(false);
        infoCenter.setLevelMedium(false);
        infoCenter.setLevelHard(false);
        infoCenter.setRound(1);
        infoCenter.setScorePlayerX(0);
        infoCenter.setScorePlayerX(0);
    }

    private void drawWinningLine(WinningTiles winningTiles) {
        winningLine.setStartX(winningTiles.start.getStackPane().getTranslateX());
        winningLine.setStartY(winningTiles.start.getStackPane().getTranslateY());
        winningLine.setEndX(winningTiles.end.getStackPane().getTranslateX());
        winningLine.setEndY(winningTiles.end.getStackPane().getTranslateY());
        winningLine.setTranslateX(winningTiles.middle.getStackPane().getTranslateX());
        winningLine.setTranslateY(winningTiles.middle.getStackPane().getTranslateY());
        winningLine.setVisible(true);
    }

    private class WinningTiles {
        Tile start;
        Tile middle;
        Tile end;

        public WinningTiles(Tile start, Tile middle, Tile end) {
            this.start = start;
            this.middle = middle;
            this.end = end;
        }
    }

    private class Tile {
        private final StackPane pane;
        private final Label label;

        public Tile() {
            pane = new StackPane();
            pane.setMinSize(50, 50);

            Rectangle border = new Rectangle();
            border.setHeight(50);
            border.setWidth(50);
            border.setFill(Color.TRANSPARENT);
            border.setStroke(Color.BLACK);
            pane.getChildren().add(border);

            label = new Label("");
            label.setAlignment(Pos.CENTER);
            label.setFont(Font.font(24));
            pane.getChildren().add(label);

            pane.setOnMouseClicked(event -> {

                if (label.getText().isEmpty() && !isEndOfGame) {
                    label.setText(getPlayerTurn());


                    if (infoCenter.isGameWithComp()) {
                        if (infoCenter.isLevelEasy()) {
                            compTurnEasy();
                        } else if (infoCenter.isLevelMedium()) {
                            compTurnMedium();
                        } else if (infoCenter.isLevelHard()) {
                            compTurnHard();
                        }

                    } else {
                        changePlayerTurn();
                    }

                    checkForWinner();
                }
            });
        }

        public StackPane getStackPane() {
            return pane;
        }

        public String getValue() {
            return label.getText();
        }

        public void setValue(String value) {
            label.setText(value);
        }
    }

}
