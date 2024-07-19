package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.Controller;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The GameFrameView class represents the main game frame that contains the game board, information view,
 * and various dialogs for the Arimaa game.
 */
public class GameFrameView {
    private JFrame gameFrame;
    private GameBoardView gameBoardView;
    private InformationView informationView;
    private PullMoveDialog pullMoveDialog;
    private WinnerDialog winnerDialog;
    private Controller controller;

    /**
     * Constructs a new GameFrameView object with the specified controller.
     *
     * @param controller The controller object responsible for handling game interactions.
     */
    public GameFrameView(Controller controller) {
        this.controller = controller;
        this.gameFrame = new JFrame("Arimaa Game");
        this.gameFrame.setSize(800, 500);
        this.gameFrame.setLayout(null);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Perform actions when the window is closing
                controller.saveGameToFile();
            }
        });
        this.gameBoardView = new GameBoardView();
        this.gameFrame.add(this.gameBoardView);
        this.informationView = new InformationView();
        this.gameFrame.add(this.informationView);
        this.gameFrame.setResizable(false);
        this.pullMoveDialog = new PullMoveDialog();
        this.winnerDialog = new WinnerDialog();
        this.gameFrame.setVisible(true);
    }

    /**
     * Returns the game board view associated with this game frame.
     *
     * @return The game board view.
     */
    public GameBoardView getGameBoardView() {
        return gameBoardView;
    }

    /**
     * Returns the pull move dialog associated with this game frame.
     *
     * @return The pull move dialog.
     */
    public PullMoveDialog getPullMoveDialog() {
        return pullMoveDialog;
    }

    /**
     * Returns the winner dialog associated with this game frame.
     *
     * @return The winner dialog.
     */
    public WinnerDialog getWinnerDialog() {
        return winnerDialog;
    }

    /**
     * Returns the game frame.
     *
     * @return The game frame.
     */
    public JFrame getGameFrame() {
        return gameFrame;
    }
    /**
     * Returns the information view associated with this game frame.
     *
     * @return The information view.
     */
    public InformationView getInformationView() {
        return informationView;
    }
}
