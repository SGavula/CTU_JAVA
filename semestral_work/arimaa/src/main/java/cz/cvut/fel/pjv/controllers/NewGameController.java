package cz.cvut.fel.pjv.controllers;

import cz.cvut.fel.pjv.view.NewGameWindow;
import cz.cvut.fel.pjv.model.GameModel;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.logging.Logger;

/**
 * The controller class for managing new game operations.
 */
public class NewGameController {
    private static final Logger logger = Logger.getLogger(NewGameController.class.getName());
    private GameModel gameModel;
    private NewGameWindow newGameWindow;

    /**
     * Constructs a new instance of the NewGameController.
     *
     * @param gameModel The GameModel object associated with the controller.
     */
    public NewGameController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.newGameWindow = new NewGameWindow(this);
    }

    /**
     * Handles the new game button click event.
     * Creates a SelectModeController to allow the user to select the game mode.
     */
    public void handleNewGameButton() {
        SelectModeController selectModeController = new SelectModeController(this.gameModel);
    }

    /**
     * Handles the load game button click event.
     * Loads a saved game from the "game_data.ser" file and initializes the corresponding controller based on the game mode.
     */
    public void handleLoadGameButton() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_data.ser"))) {
            this.gameModel = (GameModel) ois.readObject();
            logger.info("Game was loaded.");
            boolean gameMode = gameModel.getGameMode();
            if(gameMode == false) {
                PlayerVsPlayerController playerVsPlayerController = new PlayerVsPlayerController(this.gameModel);
            }else {
                PlayerVsComputerController playerVsComputerController = new PlayerVsComputerController(this.gameModel);
            }
        } catch (IOException | ClassNotFoundException err) {
            err.printStackTrace();
            logger.warning("An error occurred:" + err);
        }
    }

    /**
     * Handles the record button click event.
     * Loads a saved game record from the "game_data.ser" file and initializes the RecordController.
     */
    public void handleRecordButton() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_data.ser"))) {
            this.gameModel = (GameModel) ois.readObject();
            RecordController recordController = new RecordController(this.gameModel);
            logger.info("Game record was loaded.");
        } catch (IOException | ClassNotFoundException err) {
            logger.warning("An error occurred:" + err);
        }
    }
}
