package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.utils.GameColors;

import javax.swing.*;
import java.awt.*;

/**
 * The InformationView class represents the panel that displays player information
 * and game information.
 */
public class InformationView extends JPanel {
    private PlayerInformationView player1InformationView;
    private PlayerInformationView player2InformationView;
    private GameInformationView gameInformationView;
    /**
     * Constructs a new InformationView object.
     * Sets the bounds and preferred size of the panel, and initializes the player and game information views.
     */
    public InformationView() {
        this.setBounds(490, 0, 296, 463);
        this.setPreferredSize(new Dimension(300, 500));
        this.player2InformationView = new PlayerInformationView(GameColors.BLACK);
        this.add(player2InformationView, BorderLayout.NORTH);
        this.gameInformationView = new GameInformationView();
        this.add(gameInformationView, BorderLayout.CENTER);
        this.player1InformationView = new PlayerInformationView(GameColors.WHITE);
        this.add(player1InformationView, BorderLayout.SOUTH);
    }

    /**
     * Returns the game information view associated with the information panel.
     *
     * @return The game information view.
     */
    public GameInformationView getGameInformationView() {
        return gameInformationView;
    }

    /**
     * Returns the player 1 information view associated with the information panel.
     *
     * @return The player 1 information view.
     */
    public PlayerInformationView getPlayer1InformationView() {
        return player1InformationView;
    }

    /**
     * Returns the player 2 information view associated with the information panel.
     *
     * @return The player 2 information view.
     */
    public PlayerInformationView getPlayer2InformationView() {
        return player2InformationView;
    }
}
