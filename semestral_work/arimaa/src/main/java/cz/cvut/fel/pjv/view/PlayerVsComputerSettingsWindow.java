package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.PlayerVsComputerSettingsController;
import cz.cvut.fel.pjv.utils.GameColors;

import javax.swing.*;

/**
 * The window for setting up player vs. computer game settings.
 */
public class PlayerVsComputerSettingsWindow {
    final private PlayerVsComputerSettingsController playerVsComputerSettingsController;
    final private JFrame frame = new JFrame();
    final private JButton startNewGameButton = new JButton("Start game");
    final private JLabel playerLabel = new JLabel("Nickname of player:");
    final private JTextField playerNameTextfield = new JTextField();
    final private JLabel colorLabel = new JLabel("Choose a color:");
    final private String[] colors = {"White", "Black"};
    final private JComboBox colorSelect = new JComboBox(colors);

    /**
     * Constructs the PlayerVsComputerSettingsWindow.
     *
     * @param playerVsComputerSettingsController The controller for handling the game settings.
     */
    public PlayerVsComputerSettingsWindow(PlayerVsComputerSettingsController playerVsComputerSettingsController) {
        this.playerVsComputerSettingsController = playerVsComputerSettingsController;
        initializeComponents();
        configureFrame();
    }

    private void initializeComponents() {
        playerLabel.setBounds(100, 40, 240, 40);
        frame.add(playerLabel);

        playerNameTextfield.setBounds(100, 80, 200, 20);
        frame.add(playerNameTextfield);

        colorLabel.setBounds(100, 100, 240, 40);
        frame.add(colorLabel);

        colorSelect.setBounds(100, 140, 200, 20);
        frame.add(colorSelect);

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

    /**
     * Handles the click event of the "Start game" button.
     * Disposes the window, retrieves the player name and color selections,
     * and notifies the controller to start the game with the chosen settings.
     */
    private void handleStartGameButtonClick() {
        frame.dispose();
        String playerName = playerNameTextfield.getText();
        int selectedPlayerColor = colorSelect.getSelectedIndex();
        GameColors playerColor = GameColors.WHITE;
        GameColors computerColor = GameColors.BLACK;
        if (selectedPlayerColor == 1) {
            playerColor = GameColors.BLACK;
            computerColor = GameColors.WHITE;
        }
        playerVsComputerSettingsController.handleStartGameButton(playerColor, computerColor, playerName);
    }

}
