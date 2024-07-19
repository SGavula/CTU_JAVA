package cz.cvut.fel.pjv.controllers;

import cz.cvut.fel.pjv.model.GameBoardModel;
import cz.cvut.fel.pjv.model.GameModel;
import cz.cvut.fel.pjv.utils.GameColors;
import cz.cvut.fel.pjv.view.GameFrameView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.logging.Logger;

/**
 * The Controller class handles the game logic and interactions between the model and view components.
 */
public class Controller {
    private static final Logger logger = Logger.getLogger(PlayerVsPlayerController.class.getName());
    private GameModel gameModel;
    private GameBoardModel gameBoardModel;
    private GameFrameView gameFrameView;

    /**
     * Constructs a new Controller object with the specified GameModel.
     *
     * @param gameModel The GameModel associated with the controller.
     */
    public Controller(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameBoardModel = gameModel.getGameBoardModel();
        this.gameFrameView = new GameFrameView(this);
        this.gameFrameView.getGameBoardView().initGameBoardView(this.gameBoardModel.getBoard(), this);
        gameFrameView.getInformationView().getPlayer1InformationView().setController(this);
        gameFrameView.getInformationView().getPlayer2InformationView().setController(this);
        settingPlayerTurn();
        //Setting time
        this.getGameFrameView().getInformationView().getGameInformationView().setElapsedTime(gameModel.getGameElapsedTime());
    }

    /**
     * Sets the player turn information in the game information view based on the current player turn.
     */
    protected void settingPlayerTurn() {
        if(gameModel.getPlayerTurn() == GameColors.WHITE) {
            this.gameFrameView.getInformationView().getGameInformationView().setPlayerTurn("White");
        }else {
            this.gameFrameView.getInformationView().getGameInformationView().setPlayerTurn("Black");
        }
    }

    /**
     * Saves the game data to a file.
     */
    public void saveGameToFile() {
        int gameElapsedTime = this.gameFrameView.getInformationView().getGameInformationView().getElapsedTime();
        this.gameModel.setGameElapsedTime(gameElapsedTime);
        if(this.gameModel.getGameMode() == false) {
            int player1ElapsedTime = this.gameFrameView.getInformationView().getPlayer1InformationView().getElapsedTime();
            int player2ElapsedTime = this.gameFrameView.getInformationView().getPlayer2InformationView().getElapsedTime();
            this.gameModel.getPlayer1().setPlayerElapsedTime(player1ElapsedTime);
            this.gameModel.getPlayer2().setPlayerElapsedTime(player2ElapsedTime);

        }
        if(this.gameModel.getGameMode() == true) {
            int player1ElapsedTime;
            if(this.getGameModel().getPlayer1().getPlayerColor() == GameColors.WHITE) {
                player1ElapsedTime = this.gameFrameView.getInformationView().getPlayer1InformationView().getElapsedTime();
            }else {
                player1ElapsedTime = this.gameFrameView.getInformationView().getPlayer2InformationView().getElapsedTime();
            }
            this.gameModel.getPlayer1().setPlayerElapsedTime(player1ElapsedTime);
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("game_data.ser"))) {
            oos.writeObject(this.gameModel);
            logger.info("Game was saved.");
        } catch (IOException e) {
            logger.warning("An error occurred:" + e);
        }
    }

    /**
     * Checks if the game has finished and displays the winner dialog if necessary.
     */
    protected void checkGameFinished() {
        if (getGameModel().isGameFinished()) {
            stopTimers();
            GameColors winner = getGameModel().getWinner();

            if (winner == GameColors.WHITE) {
                getLogger().info("Game has finished. White player won the game.");
                getGameFrameView().getWinnerDialog().showDialog(getGameFrameView().getGameFrame(), "White");
            } else {
                getLogger().info("Game has finished. Black player won the game.");
                getGameFrameView().getWinnerDialog().showDialog(getGameFrameView().getGameFrame(), "Black");
            }
        }
    }

    /**
     * Stops the timers associated with the game.
     */
    private void stopTimers() {
        getGameFrameView().getInformationView().getGameInformationView().getGameTimer().stop();
        getGameFrameView().getInformationView().getPlayer1InformationView().getPlayerTimer().stop();
        getGameFrameView().getInformationView().getPlayer2InformationView().getPlayerTimer().stop();
    }

    /**
     * Handles the click event on a square.
     *
     * @param squareId The ID of the clicked square.
     */
    public void handleSquareClick(int squareId) {}

    /**
     * Handles the finish turn button click event for the specified player color.
     *
     * @param playerColor The color of the player who clicked the finish turn button.
     */
    public void handleFinishTurnButtonClick(GameColors playerColor) {}

    /**
     * Retrieves the game board model associated with the controller.
     *
     * @return The GameBoardModel object.
     */
    public GameBoardModel getGameBoardModel() {
        return gameBoardModel;
    }

    /**
     * Retrieves the game frame view associated with the controller.
     *
     * @return The GameFrameView object.
     */
    public GameFrameView getGameFrameView() {
        return gameFrameView;
    }

    /**
     * Retrieves the game model associated with the controller.
     *
     * @return The GameModel object.
     */
    public GameModel getGameModel() {
        return gameModel;
    }

    /**
     * Retrieves the logger instance associated with the controller.
     *
     * @return The Logger object.
     */
    public Logger getLogger() {return this.logger; }
}
