package cz.cvut.fel.pjv.controllers;

import cz.cvut.fel.pjv.view.PlayerVsComputerSettingsWindow;
import cz.cvut.fel.pjv.view.SelectModeWindow;
import cz.cvut.fel.pjv.model.GameModel;

/**
 * The controller class for managing the game mode selection.
 */
public class SelectModeController {
    private GameModel gameModel;
    private SelectModeWindow selectModeWindow;

    /**
     * Constructs a new instance of the SelectModeController.
     *
     * @param gameModel The GameModel object associated with the controller.
     */
    public SelectModeController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.selectModeWindow = new SelectModeWindow(this);
    }

    /**
     * Handles the Player vs. Player button click event.
     * Sets the game mode to player vs. player and initializes the PlayerVsPlayerSettingsController
     * to configure player names and start the game.
     */
    public void handlePlayerVsPlayerButton() {
        this.gameModel.setGameMode(false);
        PlayerVsPlayerSettingsController playerVsPlayerSettingsController = new PlayerVsPlayerSettingsController(this.gameModel);
    }

    /**
     * Handles the Player vs. Computer button click event.
     * Sets the game mode to player vs. computer and initializes the PlayerVsComputerSettingsController
     * to configure player name, computer color, and start the game.
     */
    public void handlePlayerVsComputerButton() {
        this.gameModel.setGameMode(true);
        PlayerVsComputerSettingsController playerVsComputerSettingsController = new PlayerVsComputerSettingsController(this.gameModel);
    }
}
