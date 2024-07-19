package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.utils.GameColors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The `GameModel` class represents the game model in the Arimaa game.
 * It manages the game state, including the players, game board, game records, and game status.
 */
public class GameModel implements Serializable {
    private GameColors playerTurn = GameColors.WHITE;
    private boolean isSetGameBoard = false;
    private boolean isGameFinished = false;
    private GameColors winner;
    private boolean gameMode;
    //True gameMode = computer vs player; False gameMode = player vs player
    private PlayerModel player1;
    private PlayerModel player2;
    private ComputerPlayerModel computerPlayerModel;
    private GameBoardModel gameBoardModel;

    private ArrayList<String> gameRecords = new ArrayList<String>();

    int gameElapsedTime = 0;

    /**
     * Initializes a new instance of the `GameModel` class.
     * It creates a new game board model.
     */
    public GameModel() {
        this.gameBoardModel = new GameBoardModel();
    }

    /**
     * Deletes pieces on trap squares for the given game board and players.
     *
     * @param gameBoardModel The game board model.
     * @param player1        The first player model.
     * @param player2        The second player model.
     */
    public void deletePiecesOnTraps(GameBoardModel gameBoardModel, PlayerModel player1, PlayerModel player2) {
        List<Integer> trapSquarePositions = List.of(18, 21, 42, 45);

        for (int trapPosition : trapSquarePositions) {
            SquareModel trapSquare = gameBoardModel.getSquare(trapPosition);
            if (trapSquare.getIsOccupate() == false) {
                continue;
            }
            PieceModel pieceOnTrap = trapSquare.getPieceOnSquare();
            if (pieceOnTrap.getIsGuared()) {
                continue;
            }
            gameBoardModel.getSquare(trapPosition).deletePieceOnSquare();
            if (pieceOnTrap.getName().equals("rab_w")) {
                player1.decreaseNumberOfRabbits();
            }
            if (pieceOnTrap.getName().equals("rab_b")) {
                player2.decreaseNumberOfRabbits();
            }
        }
    }

    /**
     * Sets frozen pieces on the game board.
     *
     * @param gameBoardModel The game board model.
     */
    public void setFrozenPieces(GameBoardModel gameBoardModel) {
        for(SquareModel square : gameBoardModel.getBoard()) {
            if(square.getIsOccupate() == false) {
                continue;
            }
            gameBoardModel.getSquare(square.getSquarePosition()).getPieceOnSquare().calculateIsPieceFrozen(gameBoardModel);
        }
    }

    /**
     * Checks if the game is finished based on the game board and players' states.
     *
     * @param gameBoardModel The game board model.
     * @param player1        The first player model.
     * @param player2        The second player model.
     */
    public void checkIfGameFinished(GameBoardModel gameBoardModel, PlayerModel player1, PlayerModel player2) {
        // Is rabbit of some player on the other side
        if (checkRabbit(gameBoardModel, 56, 63, "rab_b")) {
            setGameFinished(GameColors.BLACK);
        }
        if (checkRabbit(gameBoardModel, 0, 8, "rab_w")) {
            setGameFinished(GameColors.WHITE);
        }

        // Has player moves
        checkPlayersMoves(gameBoardModel, player1, player2);

        // Has player figures
        if (player1.getNumberOfRabbits() <= 0) {
            setGameFinished(player2.getPlayerColor());
        }
        if (player2.getNumberOfRabbits() <= 0) {
            setGameFinished(player1.getPlayerColor());
        }
    }

    private void checkPlayersMoves(GameBoardModel gameBoardModel, PlayerModel player1, PlayerModel player2) {
        boolean hasPlayer1Moves = hasValidMoves(gameBoardModel, player1);
        boolean hasPlayer2Moves = hasValidMoves(gameBoardModel, player2);

        if (!hasPlayer1Moves) {
            setGameFinished(GameColors.BLACK);
        }

        if (!hasPlayer2Moves) {
            setGameFinished(GameColors.WHITE);
        }
    }

    private boolean hasValidMoves(GameBoardModel gameBoardModel, PlayerModel player) {
        for (SquareModel square : gameBoardModel.getBoard()) {
            if (!square.getIsOccupate()) {
                continue;
            }
            PieceModel piece = square.getPieceOnSquare();
            if (piece.getPieceColor() == player.getPlayerColor() && !piece.isFrozen()) {
                List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, player.getNumberOfMoves());
                if (!validMoves.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setGameFinished(GameColors winnerColor) {
        this.isGameFinished = true;
        this.winner = winnerColor;
    }

    /**
     * Checks if the rabbit of a specific color is present on the game board between the given start and end indices.
     *
     * @param gameBoardModel The game board model.
     * @param startIndex     The start index to search for the rabbit.
     * @param endIndex       The end index to search for the rabbit.
     * @param pieceName      The name of the rabbit piece.
     * @return `true` if the rabbit is present, `false` otherwise.
     */
    public boolean checkRabbit(GameBoardModel gameBoardModel, int startIndex, int endIndex, String pieceName) {
        for (int i = startIndex; i < endIndex; i++) {
            SquareModel square = gameBoardModel.getSquare(i);
            if (!square.getIsOccupate()) {
                continue;
            }
            String nameOfString = square.getPieceOnSquare().getName();
            if (nameOfString.equals(pieceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a game record to the game records list.
     *
     * @param gameRecord The game record to add.
     */
    public void addRecordToGameRecords(String gameRecord) {
        this.gameRecords.add(gameRecord);
    }

    /**
     * Returns the current player turn.
     *
     * @return The current player turn.
     */
    public GameColors getPlayerTurn() {
        return playerTurn;
    }

    /**
     * Sets the current player turn.
     *
     * @param playerTurn The player turn to set.
     */
    public void setPlayerTurn(GameColors playerTurn) {
        this.playerTurn = playerTurn;
    }

    /**
     * Checks if the game board is set.
     *
     * @return `true` if the game board is set, `false` otherwise.
     */
    public boolean isSetGameBoard() {
        return isSetGameBoard;
    }

    /**
     * Sets the flag indicating whether the game board is set.
     *
     * @param setGameBoard The flag value to set.
     */
    public void setIsSetGameBoard(boolean setGameBoard) {
        isSetGameBoard = setGameBoard;
    }

    /**
     * Checks if the game is finished.
     *
     * @return `true` if the game is finished, `false` otherwise.
     */
    public boolean isGameFinished() {
        return isGameFinished;
    }

    /**
     * Returns the winner of the game.
     *
     * @return The winner of the game.
     */
    public GameColors getWinner() {
        return winner;
    }

    /**
     * Checks the game mode.
     *
     * @return `true` for computer vs. player mode, `false` for player vs. player mode.
     */
    public boolean getGameMode() {
        return gameMode;
    }

    /**
     * Sets the game mode.
     *
     * @param gameMode The game mode to set. `true` for computer vs. player mode, `false` for player vs. player mode.
     */
    public void setGameMode(boolean gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * Returns the game board model.
     *
     * @return The game board model.
     */
    public GameBoardModel getGameBoardModel() {
        return gameBoardModel;
    }

    /**
     * Returns the first player model.
     *
     * @return The first player model.
     */
    public PlayerModel getPlayer1() {
        return player1;
    }

    /**
     * Sets the first player model.
     *
     * @param player1 The first player model to set.
     */
    public void setPlayer1(PlayerModel player1) {
        this.player1 = player1;
    }

    /**
     * Returns the second player model.
     *
     * @return The second player model.
     */
    public PlayerModel getPlayer2() {
        return player2;
    }

    /**
     * Sets the second player model.
     *
     * @param player2 The second player model to set.
     */
    public void setPlayer2(PlayerModel player2) {
        this.player2 = player2;
    }

    /**
     * Returns the game elapsed time.
     *
     * @return The game elapsed time.
     */
    public int getGameElapsedTime() {
        return gameElapsedTime;
    }

    /**
     * Sets the game elapsed time.
     *
     * @param gameElapsedTime The game elapsed time to set.
     */
    public void setGameElapsedTime(int gameElapsedTime) {
        this.gameElapsedTime = gameElapsedTime;
    }

    /**
     * Returns the computer player model.
     *
     * @return The computer player model.
     */
    public ComputerPlayerModel getComputerPlayerModel() {
        return computerPlayerModel;
    }

    /**
     * Sets the computer player model.
     *
     * @param computerPlayerModel The computer player model to set.
     */
    public void setComputerPlayerModel(ComputerPlayerModel computerPlayerModel) {
        this.computerPlayerModel = computerPlayerModel;
    }

    /**
     * Returns the game records.
     *
     * @return The game records.
     */
    public ArrayList<String> getGameRecords() {
        return gameRecords;
    }

}
