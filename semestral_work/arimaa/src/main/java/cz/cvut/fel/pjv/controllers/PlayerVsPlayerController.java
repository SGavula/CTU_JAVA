package cz.cvut.fel.pjv.controllers;

import cz.cvut.fel.pjv.model.GameModel;
import cz.cvut.fel.pjv.model.PlayerModel;
import cz.cvut.fel.pjv.utils.GameColors;

import static cz.cvut.fel.pjv.utils.Constants.NUMBER_OF_MOVES;

/**
 * The controller class for the player-vs-player game mode.
 * Handles the game logic and user interactions for a game between the first player and the second player.
 */
public class PlayerVsPlayerController extends Controller {
    private PlayerModel player1;
    private PlayerModel player2;
    /**
     * Constructs a new instance of the PlayerVsPlayerController class with the specified game model.
     *
     * @param gameModel the game model
     */
    public PlayerVsPlayerController(GameModel gameModel) {
        super(gameModel);
        getLogger().info("PlayerVsPlayerController initialized.");
        player1 = gameModel.getPlayer1();
        player2 = gameModel.getPlayer2();
        //Setting number of moves of each player
        this.getGameFrameView().getInformationView().getPlayer1InformationView().setNumberOfMoves(player1.getNumberOfMoves());
        this.getGameFrameView().getInformationView().getPlayer2InformationView().setNumberOfMoves(player2.getNumberOfMoves());
        //Setting players name
        this.getGameFrameView().getInformationView().getPlayer1InformationView().setPlayerName(player1.getPlayerName());
        this.getGameFrameView().getInformationView().getPlayer2InformationView().setPlayerName(player2.getPlayerName());
        //Setting player time
        this.getGameFrameView().getInformationView().getPlayer1InformationView().setElapsedTime(player1.getPlayerElapsedTime());
        this.getGameFrameView().getInformationView().getPlayer2InformationView().setElapsedTime(player2.getPlayerElapsedTime());
        this.getGameFrameView().getGameBoardView().redrawGameBoardView(this.getGameBoardModel().getBoard(), this);
    }

    /**
     * Handles the click event on a square in the game board.
     *
     * @param squareId the ID of the clicked square
     */
    public void handleSquareClick(int squareId) {
        startTimers();
        playPlayerVsPlayer(squareId);
        checkGameFinished();
    }

    private void startTimers() {
        getGameFrameView().getInformationView().getGameInformationView().getGameTimer().start();

        // Start the player timer based on the current player turn
        if (getGameModel().getPlayerTurn() == GameColors.WHITE) {
            getGameFrameView().getInformationView().getPlayer1InformationView().getPlayerTimer().start();
        } else {
            getGameFrameView().getInformationView().getPlayer2InformationView().getPlayerTimer().start();
        }
    }

    private void playPlayerVsPlayer(int squareId) {
        //Check if players set their pieces on the board
        if (!getGameModel().isSetGameBoard()) {
            settingPiecesOnGameBoard(squareId);
        } else {
            updateGameBoardView();
            makePlayerMove(squareId);
            handlePullMoveDialog();
            updateGameModel();
        }

        handlePlayerTurn();
        updateGameBoardView();
        settingPlayerTurn();
    }

    private void updateGameBoardView() {
        this.getGameFrameView().getInformationView().getPlayer1InformationView().setNumberOfMoves(player1.getNumberOfMoves());
        this.getGameFrameView().getInformationView().getPlayer2InformationView().setNumberOfMoves(player2.getNumberOfMoves());
        getGameFrameView().getGameBoardView().redrawGameBoardView(getGameBoardModel().getBoard(), this);
    }

    private void makePlayerMove(int squareId) {
        PlayerModel currentPlayer = getGameModel().getPlayerTurn() == GameColors.BLACK ? player2 : player1;

        if (currentPlayer.getNumberOfMoves() != 0) {
            currentPlayer.makeMove(squareId, getGameBoardModel());
        }
    }

    private void handlePullMoveDialog() {
        PlayerModel currentPlayer = getGameModel().getPlayerTurn() == GameColors.BLACK ? player2 : player1;

        // Check if the dialog should be shown based on the game board model
        if (getGameBoardModel().getShowDialog()) {
            // Show the pull move dialog and get the user's choice
            boolean makePullMove = getGameFrameView().getPullMoveDialog().showDialog(getGameFrameView().getGameFrame());

            // Set the player's makePullMove flag based on the user's choice
            currentPlayer.setMakePullMove(makePullMove);

            // Highlight the valid pull moves for the current player on the game board
            currentPlayer.hightlightPullMoves(getGameBoardModel());
        }
    }

    private void updateGameModel() {
        //updates the game model by setting the frozen pieces
        getGameModel().setFrozenPieces(getGameBoardModel());
        getGameModel().deletePiecesOnTraps(getGameBoardModel(), player1, player2);
        getGameModel().checkIfGameFinished(getGameBoardModel(), player1, player2);
    }

    private void handlePlayerTurn() {
        PlayerModel currentPlayer = getGameModel().getPlayerTurn() == GameColors.BLACK ? player2 : player1;
        PlayerModel otherPlayer = getGameModel().getPlayerTurn() == GameColors.BLACK ? player1 : player2;

        // Check if the current player has no remaining moves
        if (currentPlayer.getNumberOfMoves() == 0) {
            // Set the player turn in the game model to the other player's color
            getGameModel().setPlayerTurn(otherPlayer.getPlayerColor());
            getLogger().info("Set player turn to " + otherPlayer.getPlayerColor() + " player");
            handlePlayerTimer(currentPlayer.getPlayerColor());
            settingPlayerTurn();
            // Add the current player's arimaa notation (move) to the game records
            getGameModel().addRecordToGameRecords(currentPlayer.getCurrentArimaaNotation());
            // Clear the current player's arimaa notation for the next turn
            currentPlayer.setCurrentArimaaNotation("");
            otherPlayer.increaseMoveNumber();
            // Reset the number of moves for the current player to the maximum allowed moves
            currentPlayer.setNumberOfMoves(NUMBER_OF_MOVES);
        }
    }

    private void settingPiecesOnGameBoard(int squareId) {
        if (player1.getHasSetBoard() == false) {
            setPlayer1PieceOnBoard(squareId);
        } else {
            setPlayer2PieceOnBoard(squareId);
        }
    }

    private void setPlayer1PieceOnBoard(int squareId) {
        if (player1.getHasSetBoard() == false) {
            getGameFrameView().getInformationView().getPlayer1InformationView().getPlayerTimer().start();
            //Increasing move number for arimaa notation
            if (player1.getMoveNumber() == 0) {
                player1.increaseMoveNumber();
            }
            // Set player 1 pieces
            player1.setPieceOnBoard(squareId, getGameBoardModel());
            if (player1.getHasSetBoard() == true) {
                getGameFrameView().getInformationView().getPlayer1InformationView().getPlayerTimer().stop();
                getGameModel().setPlayerTurn(GameColors.BLACK);
                getGameFrameView().getInformationView().getPlayer2InformationView().getPlayerTimer().start();
            }
        }
    }

    private void setPlayer2PieceOnBoard(int squareId) {
        //Increasing move number for arimaa notation and saving player 1 arimaa notation
        if (player2.getMoveNumber() == 0) {
            getGameModel().addRecordToGameRecords(player1.getCurrentArimaaNotation());
            player1.setCurrentArimaaNotation("");
            player2.increaseMoveNumber();
        }
        // Set player 2 pieces
        player2.setPieceOnBoard(squareId, getGameBoardModel());
        if (player2.getHasSetBoard() == true) {
            getGameModel().setIsSetGameBoard(true);
            getGameModel().setPlayerTurn(GameColors.WHITE);
            getGameFrameView().getInformationView().getPlayer2InformationView().getPlayerTimer().stop();
            getGameFrameView().getInformationView().getPlayer1InformationView().getPlayerTimer().start();
            getGameModel().addRecordToGameRecords(player2.getCurrentArimaaNotation());
            player2.setCurrentArimaaNotation("");
            player1.increaseMoveNumber();
        }
    }

    /**
     * Handles the click event on the "Finish Turn" button for the specified player color.
     * After processing the button click, the player turn is set.
     *
     * @param playerColor the color of the player for whom the finish turn button is clicked
     */
    @Override
    public void handleFinishTurnButtonClick(GameColors playerColor) {
        if (playerColor == GameColors.WHITE && player1.getNumberOfMoves() != 4 && getGameModel().getPlayerTurn() == GameColors.WHITE) {
            handleFinishTurnButtonClickForPlayer(player1, player2);
            getGameFrameView().getInformationView().getPlayer1InformationView().setNumberOfMoves(player1.getNumberOfMoves());
        }

        if (playerColor == GameColors.BLACK && player2.getNumberOfMoves() != 4 && getGameModel().getPlayerTurn() == GameColors.BLACK) {
            handleFinishTurnButtonClickForPlayer(player2, player1);
            getGameFrameView().getInformationView().getPlayer2InformationView().setNumberOfMoves(player2.getNumberOfMoves());
        }
        settingPlayerTurn();
    }

    private void handleFinishTurnButtonClickForPlayer(PlayerModel player, PlayerModel opponent) {
        // Check if the player has not completed the maximum number of moves
        if (player.getNumberOfMoves() != NUMBER_OF_MOVES) {
            handlePlayerTimer(player.getPlayerColor());
            getGameModel().setPlayerTurn(opponent.getPlayerColor());
            // Add the current player's arimaa notation (move) to the game records
            getGameModel().addRecordToGameRecords(player.getCurrentArimaaNotation());
            player.setCurrentArimaaNotation("");
            // Reset the number of moves for the current player to the maximum allowed moves
            player.setNumberOfMoves(NUMBER_OF_MOVES);
            opponent.increaseMoveNumber();
        }
    }

    private void handlePlayerTimer(GameColors currentPlayerColor) {
        if(currentPlayerColor == GameColors.WHITE) {
            this.getGameFrameView().getInformationView().getPlayer1InformationView().getPlayerTimer().stop();
            this.getGameFrameView().getInformationView().getPlayer2InformationView().getPlayerTimer().start();
        }else {
            this.getGameFrameView().getInformationView().getPlayer1InformationView().getPlayerTimer().start();
            this.getGameFrameView().getInformationView().getPlayer2InformationView().getPlayerTimer().stop();
        }
    }
}
