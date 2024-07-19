package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.PlayerVsPlayerSettingsController;

import javax.swing.*;

/**
 * The window for setting up player vs. player game settings.
 */
public class PlayerVsPlayerSettingsWindow {
    private PlayerVsPlayerSettingsController playerVsPlayerWindowController;
    private JFrame frame = new JFrame();
    private JButton startNewGameButton = new JButton("Start game");
    private JLabel player1Label = new JLabel("Nickname of first player (white player):");
    private JLabel player2Label = new JLabel("Nickname of second player (black player):");
    private JTextField player1NameTextfield = new JTextField();
    private JTextField player2NameTextfield = new JTextField();

    /**
     * Constructs the PlayerVsPlayerSettingsWindow.
     *
     * @param playerVsPlayerWindowController The controller for handling the game settings.
     */
    public PlayerVsPlayerSettingsWindow(PlayerVsPlayerSettingsController playerVsPlayerWindowController) {
        this.playerVsPlayerWindowController = playerVsPlayerWindowController;
        initializeComponents();
        configureFrame();
    }

    private void initializeComponents() {
        player1Label.setBounds(100, 40, 240, 40);
        frame.add(player1Label);
        player1NameTextfield.setBounds(100, 80, 200, 20);
        frame.add(player1NameTextfield);

        player2Label.setBounds(100, 120, 240, 40);
        frame.add(player2Label);
        player2NameTextfield.setBounds(100, 160, 200, 20);
        frame.add(player2NameTextfield);

        startNewGameButton.setBounds(100, 220, 200, 40);
        startNewGameButton.setFocusable(false);
        startNewGameButton.addActionListener(e -> handleStartGameButtonClick());
        frame.add(startNewGameButton);
    }

    private void configureFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void handleStartGameButtonClick() {
        frame.dispose();
        String player1Name = player1NameTextfield.getText();
        String player2Name = player2NameTextfield.getText();
        playerVsPlayerWindowController.handleStartGameButton(player1Name, player2Name);
    }
}
