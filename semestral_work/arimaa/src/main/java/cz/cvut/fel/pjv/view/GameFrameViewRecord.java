package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.Controller;
import cz.cvut.fel.pjv.controllers.RecordController;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * The GameFrameViewRecord class represents the game frame for recording Arimaa game sessions.
 * It contains the game board view for displaying game and an information view for displaying game information.
 */
public class GameFrameViewRecord {
    private JFrame gameFrame;
    private GameBoardView gameBoardView;
    private InformationViewRecord informationViewRecord;
    private RecordController recordController;

    /**
     * Constructs a new GameFrameViewRecord object with the specified record controller.
     *
     * @param recordController The record controller object responsible for handling game recording.
     */
    public GameFrameViewRecord(RecordController recordController) {
        this.recordController = recordController;
        this.gameFrame = new JFrame("Arimaa Game");
        this.gameFrame.setSize(800, 500);
        this.gameFrame.setLayout(null);
        this.gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.gameBoardView = new GameBoardView();
        this.gameFrame.add(this.gameBoardView);
        this.informationViewRecord = new InformationViewRecord(this.recordController);
        this.gameFrame.add(this.informationViewRecord);
        this.gameFrame.setResizable(false);
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
    public InformationViewRecord getInformationView() {
        return informationViewRecord;
    }
}
