package cz.cvut.fel.pjv.controllers;

import cz.cvut.fel.pjv.model.ComputerPlayerModel;
import cz.cvut.fel.pjv.model.GameModel;
import cz.cvut.fel.pjv.model.PlayerModel;
import cz.cvut.fel.pjv.utils.GameColors;

import static cz.cvut.fel.pjv.utils.Constants.NUMBER_OF_MOVES;

/**
 * The controller class for the player-vs-computer game mode.
 * Handles the game logic and user interactions for a game between a human player and a computer player.
 */
public class PlayerVsComputerController extends Controller {
    private PlayerModel player1;
    private ComputerPlayerModel computerPlayer;
    /**
     * Constructs a new PlayerVsComputerController with the given game model.
     *
     * @param gameModel the game model to associate with the controller
     */
    public PlayerVsComputerController(GameModel gameModel) {
        super(gameModel);
        getLogger().info("PlayerVsComputerController initialized.");
        // Assign player1 and computerPlayer from the gameModel
        this.player1 = this.getGameModel().getPlayer1();
        this.computerPlayer = this.getGameModel().getComputerPlayerModel();

        initializeComputerPlayer();

        // Set up player names and moves based on the computer player's color
        if (computerPlayer.getPlayerColor() == GameColors.WHITE) {
            setupPlayerNamesAndMoves(this.computerPlayer, this.player1);
        } else {
            setupPlayerNamesAndMoves(this.player1, this.computerPlayer);
        }
        setupPlayerTime(this.player1);

        this.getGameFrameView().getGameBoardView().redrawGameBoardView(this.getGameBoardModel().getBoard(), this);
    }

    private void initializeComputerPlayer() {
        if (this.computerPlayer.getPlayerColor() == GameColors.WHITE) {
            computerPlayer.increaseMoveNumber(); // Increase the move number of the computer player
            this.computerPlayer.setPieceOnBoard(this.getGameBoardModel()); // Set the computer player's piece on the board
            this.getGameModel().addRecordToGameRecords(this.computerPlayer.getCurrentArimaaNotation()); // Add the current Arimaa notation to the game records
            computerPlayer.setCurrentArimaaNotation(""); // Clear the current Arimaa notation
            this.getGameModel().setPlayerTurn(player1.getPlayerColor());
            settingPlayerTurn();
        }
    }

    private void setupPlayerNamesAndMoves(PlayerModel player1, PlayerModel player2) {
        // Set the player names and number of moves for the information views in the game frame
        this.getGameFrameView().getInformationView().getPlayer1InformationView().setPlayerName(player1.getPlayerName());
        this.getGameFrameView().getInformationView().getPlayer2InformationView().setPlayerName(player2.getPlayerName());
        this.getGameFrameView().getInformationView().getPlayer1InformationView().setNumberOfMoves(player1.getNumberOfMoves());
        this.getGameFrameView().getInformationView().getPlayer2InformationView().setNumberOfMoves(NUMBER_OF_MOVES);
    }

    private void setupPlayerTime(PlayerModel player) {
        this.getGameFrameView().getInformationView().getPlayer1InformationView().setElapsedTime(player.getPlayerElapsedTime());
    }

    /**
     * Handles the click event on a game square.
     * Starts the game timer if it is not already running.
     * If the game is not finished, proceeds with playing the human player's move and computer player's moves.
     * Finally, checks if the game is finished.
     *
     * @param squareId the ID of the clicked square
     */
    @Override
    public void handleSquareClick(int squareId) {
        this.getGameFrameView().getInformationView().getGameInformationView().getGameTimer().start();
        //Play game only if game is not finished
        if (this.getGameModel().isGameFinished() == false) {
            playPlayerVsComputer(squareId);
        }

        checkGameFinished();
    }

    //This method is responsible for playing a turn in a player vs computer game.
    private void playPlayerVsComputer(int squareId) {
        settingPlayerTurn();
        //Check if players set their pieces on board
        if (this.getGameModel().isSetGameBoard() == false) {
            handleInitialGameSetup(squareId);
        } else {
            handlePlayerMove(squareId);
            this.getGameFrameView().getGameBoardView().redrawGameBoardView(this.getGameBoardModel().getBoard(), this);
            handlePullMoveDialog();
            handleGameUpdates();
            handleComputerMoves();
        }
        updateGameBoardView();
        this.getGameFrameView().getGameBoardView().redrawGameBoardView(this.getGameBoardModel().getBoard(), this);
    }

    private void handleInitialGameSetup(int squareId) {
        //Increasing move number for arimaa notation
        if (player1.getMoveNumber() == 0) {
            player1.increaseMoveNumber();
        }
        settingPiecesOnBoard(squareId);
    }

    private void handlePlayerMove(int squareId) {
        //Make move if player has moves
        if (player1.getNumberOfMoves() != 0) {
            player1.makeMove(squareId, this.getGameBoardModel());
        }
    }

    private void handlePullMoveDialog() {
        //Show dialog if player wants to make pull move
        if (this.getGameBoardModel().getShowDialog()) {
            player1.setMakePullMove(this.getGameFrameView().getPullMoveDialog().showDialog(this.getGameFrameView().getGameFrame()));
            player1.hightlightPullMoves(this.getGameBoardModel());
        }
    }

    private void handleGameUpdates() {
        this.getGameModel().setFrozenPieces(this.getGameBoardModel());
        PlayerModel opponentPlayer = (player1.getPlayerColor() == GameColors.WHITE) ? computerPlayer : player1;
        this.getGameModel().deletePiecesOnTraps(this.getGameBoardModel(), player1, opponentPlayer);
        this.getGameModel().checkIfGameFinished(this.getGameBoardModel(), player1, opponentPlayer);
    }

    private void handleComputerMoves() {
        // If the number of moves for the player is less than or equal to 0, it sets the player turn to the computer player
        if (player1.getNumberOfMoves() <= 0) {
            //Set player turn to computer player
            this.getGameModel().setPlayerTurn(computerPlayer.getPlayerColor());
            //Increase move number for arimaa notation
            computerPlayer.increaseMoveNumber();
            this.getGameModel().addRecordToGameRecords(this.player1.getCurrentArimaaNotation());
            player1.setCurrentArimaaNotation(""); // Reset the current Arimaa notation of the player
            computerPlayerMakeMoves();
            this.getGameModel().addRecordToGameRecords(this.computerPlayer.getCurrentArimaaNotation());
            computerPlayer.setCurrentArimaaNotation(""); // Reset the current Arimaa notation of the computer player
            player1.setNumberOfMoves(NUMBER_OF_MOVES);
            player1.increaseMoveNumber();
            this.getGameModel().setPlayerTurn(player1.getPlayerColor());
        }
    }

    private void settingPiecesOnBoard(int squareId) {
        //Player 1 can set his pieces
        player1.setPieceOnBoard(squareId, this.getGameBoardModel());

        //Check if player sets his pieces on board
        if (player1.getHasSetBoard()) {
            this.getGameModel().addRecordToGameRecords(this.player1.getCurrentArimaaNotation());
            player1.setCurrentArimaaNotation("");
            computerPlayer.increaseMoveNumber();
            handleComputerBoardSetup();
            this.getGameModel().setIsSetGameBoard(true);
            handleComputerMovesAfterInitialization();
            player1.increaseMoveNumber();
        }
    }

    private void handleComputerBoardSetup() {
        if (computerPlayer.getPlayerColor() == GameColors.BLACK) {
            this.getGameModel().setPlayerTurn(computerPlayer.getPlayerColor());
            computerPlayer.setPieceOnBoard(this.getGameBoardModel());
            this.getGameModel().addRecordToGameRecords(this.computerPlayer.getCurrentArimaaNotation());
            computerPlayer.setCurrentArimaaNotation("");
            this.getGameModel().setPlayerTurn(player1.getPlayerColor());
        }
    }
    private void handleComputerMovesAfterInitialization() {
        if (computerPlayer.getPlayerColor() == GameColors.WHITE) {
            computerPlayer.setNumberOfMoves(NUMBER_OF_MOVES);
            computerPlayerMakeMoves();
            this.getGameModel().addRecordToGameRecords(this.computerPlayer.getCurrentArimaaNotation());
            computerPlayer.setCurrentArimaaNotation("");
        }
    }

    private void computerPlayerMakeMoves() {
        computerPlayer.setNumberOfMoves(NUMBER_OF_MOVES);
        while (computerPlayer.getNumberOfMoves() > 0) {
            computerPlayer.makeMoves(this.getGameBoardModel());
            this.getGameModel().setFrozenPieces(this.getGameBoardModel());
            // Delete pieces on traps based on the player's colors
            if (player1.getPlayerColor() == GameColors.WHITE) {
                this.getGameModel().deletePiecesOnTraps(this.getGameBoardModel(), player1, computerPlayer);
            } else {
                this.getGameModel().deletePiecesOnTraps(this.getGameBoardModel(), computerPlayer, player1);
            }
            this.getGameModel().checkIfGameFinished(this.getGameBoardModel(), player1, computerPlayer);
            this.getGameFrameView().getGameBoardView().redrawGameBoardView(this.getGameBoardModel().getBoard(), this);
        }
    }

    private void updateGameBoardView() {
        if (player1.getPlayerColor() == GameColors.WHITE) {
            this.getGameFrameView().getInformationView().getPlayer1InformationView().getPlayerTimer().start();
            this.getGameFrameView().getInformationView().getPlayer1InformationView().setNumberOfMoves(player1.getNumberOfMoves());
        }
        if (player1.getPlayerColor() == GameColors.BLACK) {
            this.getGameFrameView().getInformationView().getPlayer2InformationView().getPlayerTimer().start();
            this.getGameFrameView().getInformationView().getPlayer2InformationView().setNumberOfMoves(player1.getNumberOfMoves());
        }
    }

    /**
     * Handles the click event on the "Finish Turn" button, updating the game state accordingly.
     * If the human player has already made the maximum number of moves, the method returns.
     * Otherwise, it adds the human player's current move to the game records, performs the computer player's moves,
     * updates the move counts, and redraws the game board view.
     * @param playerColor the color of the player who clicked the "Finish Turn" button
     */
    @Override
    public void handleFinishTurnButtonClick(GameColors playerColor) {
        // If the player has already made the maximum number of moves, return
        if (this.player1.getNumberOfMoves() == NUMBER_OF_MOVES) {
            return;
        }
        this.getGameModel().addRecordToGameRecords(this.player1.getCurrentArimaaNotation());
        player1.setCurrentArimaaNotation("");
        computerPlayer.increaseMoveNumber();
        computerPlayer.setNumberOfMoves(NUMBER_OF_MOVES);
        computerPlayerMakeMoves();
        this.getGameModel().addRecordToGameRecords(this.computerPlayer.getCurrentArimaaNotation());
        computerPlayer.setCurrentArimaaNotation("");
        player1.increaseMoveNumber();
        this.player1.setNumberOfMoves(NUMBER_OF_MOVES);
        // Update the number of moves in the information view based on the player's color
        if (playerColor == GameColors.WHITE && this.player1.getPlayerColor() == GameColors.WHITE) {
            this.getGameFrameView().getInformationView().getPlayer1InformationView().setNumberOfMoves(player1.getNumberOfMoves());
        }
        if (playerColor == GameColors.BLACK && this.player1.getPlayerColor() == GameColors.BLACK) {
            this.getGameFrameView().getInformationView().getPlayer2InformationView().setNumberOfMoves(player1.getNumberOfMoves());
        }
        this.getGameFrameView().getGameBoardView().redrawGameBoardView(this.getGameBoardModel().getBoard(), this);
        settingPlayerTurn();
    }
}