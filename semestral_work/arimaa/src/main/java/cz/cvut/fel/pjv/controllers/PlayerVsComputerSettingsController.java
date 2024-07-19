package cz.cvut.fel.pjv.controllers;

import cz.cvut.fel.pjv.model.ComputerPlayerModel;
import cz.cvut.fel.pjv.model.GameModel;
import cz.cvut.fel.pjv.model.PlayerModel;
import cz.cvut.fel.pjv.utils.GameColors;
import cz.cvut.fel.pjv.view.PlayerVsComputerSettingsWindow;

/**
 * The controller class for managing player vs. computer game settings.
 */
public class PlayerVsComputerSettingsController {
    private GameModel gameModel;
    private PlayerVsComputerSettingsWindow playerVsComputerSettingsWindow;
    /**
     * Constructs a new instance of the PlayerVsComputerSettingsController.
     *
     * @param gameModel The GameModel object associated with the controller.
     */
    public PlayerVsComputerSettingsController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.playerVsComputerSettingsWindow = new PlayerVsComputerSettingsWindow(this);
    }

    /**
     * Handles the start game button click event.
     * Creates player and computer player models based on the selected colors and player name,
     * and initializes the PlayerVsComputerController to start the game.
     *
     * @param playerColor    The color chosen for the human player.
     * @param computerColor  The color chosen for the computer player.
     * @param playerName     The name of the human player.
     */
    public void handleStartGameButton(GameColors playerColor, GameColors computerColor, String playerName) {
        PlayerModel player = new PlayerModel(playerColor, playerName);
        ComputerPlayerModel computerPlayer = new ComputerPlayerModel(computerColor, "PC");
        this.gameModel.setPlayer1(player);
        this.gameModel.setComputerPlayerModel(computerPlayer);
        PlayerVsComputerController playerVsComputerController = new PlayerVsComputerController(this.gameModel);
    }
}
