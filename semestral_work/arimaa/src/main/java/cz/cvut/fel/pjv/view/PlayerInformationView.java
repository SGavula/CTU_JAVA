package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.Controller;
import cz.cvut.fel.pjv.utils.GameColors;

import static cz.cvut.fel.pjv.utils.Constants.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayerInformationView extends JPanel {
    private int elapsedTime = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private String secondsString = String.format("%02d", seconds);
    private String minutesString = String.format("%02d", minutes);
    private String hoursString = String.format("%02d", hours);
    private JLabel time;
    private JLabel numberOfMovesLabel;
    private Controller controller;
    private String playerName;
    private JLabel playerNameLabel;
    private GameColors playerColor;

    /**
     * Represents the information view for a player in the game.
     * It displays the player's name, number of moves, and elapsed time.
     */
    private Timer playerTimer = new Timer( 1000, e -> {
        elapsedTime = elapsedTime + 1000;
        hours = (elapsedTime/3600000);
        minutes = (elapsedTime/60000) % 60;
        seconds = (elapsedTime/1000) % 60;
        secondsString = String.format("%02d", seconds);
        minutesString = String.format("%02d", minutes);
        hoursString = String.format("%02d", hours);
        this.time.setText("Time: " + hoursString + ":" + minutesString + ":" + secondsString);
    });

    /**
     * Constructs a PlayerInformationView object responsible for displaying information of player.
     *
     * @param playerColor The color of the player.
     */
    public PlayerInformationView(GameColors playerColor) {
        this.playerColor = playerColor;
        setPreferredSize(new Dimension(300, 120));
        setLayout(new GridLayout(2, 2));
        initializeLabels();
        addComponentsToPanel();
        initializeButton();
    }

    private void initializeLabels() {
        initPlayerNameLabel();
        initNumberOfMovesLabel();
        initTimeLabel();
    }

    private void initPlayerNameLabel() {
        playerNameLabel = new JLabel();
        playerNameLabel.setHorizontalAlignment(JLabel.CENTER);
        playerNameLabel.setVerticalAlignment(JLabel.CENTER);
        playerNameLabel.setFont(new Font("Arial", Font.BOLD, 24));
        playerNameLabel.setForeground(Color.black);
    }

    private void initNumberOfMovesLabel() {
        numberOfMovesLabel = new JLabel();
        numberOfMovesLabel.setHorizontalAlignment(JLabel.CENTER);
        numberOfMovesLabel.setVerticalAlignment(JLabel.CENTER);
        numberOfMovesLabel.setFont(new Font("Arial", Font.BOLD, 16));
        numberOfMovesLabel.setForeground(Color.black);
    }

    private void initTimeLabel() {
        time = new JLabel("Time: 00:00:00");
        time.setHorizontalAlignment(JLabel.CENTER);
        time.setVerticalAlignment(JLabel.CENTER);
        time.setFont(new Font("Arial", Font.BOLD, 16));
        time.setForeground(Color.black);
    }

    private void initializeButton() {
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        JButton finishTurnButton = new JButton("Finish turn");
        finishTurnButton.setPreferredSize(new Dimension(100, 30));
        finishTurnButton.addMouseListener(new MouseAdapter() {
            @Override
             public void mouseClicked(MouseEvent e) {
                controller.handleFinishTurnButtonClick(playerColor);
            }
        });
        buttonPanel.add(finishTurnButton, new GridBagConstraints());
        add(buttonPanel);
    }

    private void addComponentsToPanel() {
        add(playerNameLabel);
        add(numberOfMovesLabel);
        add(time);
    }

    /**
     * Sets the number of moves for the player.
     *
     * @param numberOfMoves The number of moves to be set.
     */
    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMovesLabel.setText(NUMBER_OF_MOVES_STRING + String.valueOf(numberOfMoves));
    }
    /**
     * Retrieves the player's timer.
     *
     * @return The player's timer.
     */
    public Timer getPlayerTimer() {
        return playerTimer;
    }

    /**
     * Retrieves the elapsed time in milliseconds.
     *
     * @return The elapsed time in milliseconds.
     */
    public int getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Sets the elapsed time for the player.
     *
     * @param elapsedTime The elapsed time in milliseconds to be set.
     */
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
        this.hours = (elapsedTime/3600000);
        this.minutes = (elapsedTime/60000) % 60;
        this.seconds = (elapsedTime/1000) % 60;
        this.secondsString = String.format("%02d", seconds);
        this.minutesString = String.format("%02d", minutes);
        this.hoursString = String.format("%02d", hours);
        this.time.setText("Time: " + hoursString + ":" + minutesString + ":" + secondsString);
    }

    /**
     * Sets the controller for the player information view.
     *
     * @param controller The controller to be set.
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     * Sets the player's name to be displayed.
     *
     * @param playerName The player's name to be set.
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
        this.playerNameLabel.setText(this.playerName);
    }
}
