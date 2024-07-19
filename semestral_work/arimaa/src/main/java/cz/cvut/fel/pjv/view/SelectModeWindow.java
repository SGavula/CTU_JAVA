package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.SelectModeController;

import javax.swing.*;
import java.awt.*;

/**
 * A window for selecting the game mode (Player vs Player or Player vs Computer).
 */
public class SelectModeWindow {
    private SelectModeController selectModeController;
    private JFrame frame = new JFrame();
    private JLabel selectModeText = new JLabel("Select game mode");
    private JButton playerVsPlayerButton = new JButton("Player vs Player");
    private JButton playerVsComputerButton = new JButton("Player vs Computer");

    /**
     * Constructs a new SelectModeWindow with the specified controller.
     *
     * @param selectModeController The controller for handling events and user interactions.
     */
    public SelectModeWindow(SelectModeController selectModeController) {
        this.selectModeController = selectModeController;
        initializeComponents();
        configureFrame();
    }

    private void initializeComponents() {
        selectModeText.setBounds(115, 60, 200, 40);
        selectModeText.setFont(new Font("Arial", Font.BOLD, 20));
        frame.add(selectModeText);

        playerVsPlayerButton.setBounds(100, 120, 200, 40);
        playerVsPlayerButton.setFocusable(false);
        playerVsPlayerButton.addActionListener(e -> handlePlayerVsPlayerButtonClick());

        playerVsComputerButton.setBounds(100, 180, 200, 40);
        playerVsComputerButton.setFocusable(false);
        playerVsComputerButton.addActionListener(e -> handlePlayerVsComputerButtonClick());

        frame.add(playerVsPlayerButton);
        frame.add(playerVsComputerButton);
    }

    private void configureFrame() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private void handlePlayerVsPlayerButtonClick() {
        frame.dispose();
        selectModeController.handlePlayerVsPlayerButton();
    }

    private void handlePlayerVsComputerButtonClick() {
        frame.dispose();
        selectModeController.handlePlayerVsComputerButton();
    }
}
