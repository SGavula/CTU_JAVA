package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.NewGameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The NewGameWindow class represents the window for creating a new game.
 * It allows the user to choose if he wants to start a new game, load game or watch record of the game
 */
public class NewGameWindow implements ActionListener {
    private NewGameController newGameWindowController;
    private JFrame frame = new JFrame();
    private JLabel newGameModeText = new JLabel("Arimaa Game");
    private JButton newGameButton = new JButton("New game");
    private JButton loadGameButtom = new JButton("Load game");
    private JButton recordButtom = new JButton("Record");

    /**
     * Constructs a new NewGameWindow object with the specified new game controller.
     *
     * @param newGameWindowController The new game controller object responsible for handling new game actions.
     */
    public NewGameWindow(NewGameController newGameWindowController) {
        this.newGameWindowController = newGameWindowController;
        initializeComponents();
        configureFrame();
    }

    private void initializeComponents() {
        newGameModeText.setBounds(137, 60, 200, 40);
        newGameModeText.setFont(new Font("Arial", Font.BOLD, 20));

        newGameButton.setBounds(100, 120, 200, 40);
        newGameButton.setFocusable(false);
        newGameButton.addActionListener(this);

        loadGameButtom.setBounds(100, 180, 200, 40);
        loadGameButtom.setFocusable(false);
        loadGameButtom.addActionListener(this);

        recordButtom.setBounds(100, 240, 200, 40);
        recordButtom.setFocusable(false);
        recordButtom.addActionListener(this);
    }

    private void configureFrame() {
        frame.add(newGameModeText);
        frame.add(newGameButton);
        frame.add(loadGameButtom);
        frame.add(recordButtom);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    /**
     * Performs the corresponding action based on the event triggered by a button click.
     *
     * @param e The ActionEvent object representing the event triggered by a button click.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        this.frame.dispose();
        if(e.getSource() == newGameButton) {
            newGameWindowController.handleNewGameButton();
        }
        if(e.getSource() == loadGameButtom) {
            newGameWindowController.handleLoadGameButton();
        }
        if(e.getSource() == recordButtom) {
            newGameWindowController.handleRecordButton();
        }
    }
}
