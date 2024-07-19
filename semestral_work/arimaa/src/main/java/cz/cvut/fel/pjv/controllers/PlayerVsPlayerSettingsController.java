package cz.cvut.fel.pjv.controllers;

import cz.cvut.fel.pjv.utils.GameColors;
import cz.cvut.fel.pjv.view.PlayerVsPlayerSettingsWindow;
import cz.cvut.fel.pjv.model.GameModel;
import cz.cvut.fel.pjv.model.PlayerModel;

/**
 * The controller class for managing player vs. player game settings.
 */
public class PlayerVsPlayerSettingsController {
    private GameModel gameModel;
    private PlayerVsPlayerSettingsWindow playerVsPlayerSettingWindow;
    /**
     * Constructs a new instance of the PlayerVsPlayerSettingsController.
     *
     * @param gameModel The GameModel object associated with the controller.
     */
    public PlayerVsPlayerSettingsController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.playerVsPlayerSettingWindow = new PlayerVsPlayerSettingsWindow(this);
    }

    /**
     * Handles the start game button click event.
     * Creates player models for player 1 and player 2 based on the provided names and default colors,
     * and initializes the PlayerVsPlayerController to start the game.
     *
     * @param player1Name The name of player 1.
     * @param player2Name The name of player 2.
     */
    public void handleStartGameButton(String player1Name, String player2Name) {
        PlayerModel player1 = new PlayerModel(GameColors.WHITE, player1Name);
        PlayerModel player2 = new PlayerModel(GameColors.BLACK, player2Name);
        gameModel.setPlayer1(player1);
        gameModel.setPlayer2(player2);
        PlayerVsPlayerController playerVsPlayerController = new PlayerVsPlayerController(this.gameModel);
    }
}
