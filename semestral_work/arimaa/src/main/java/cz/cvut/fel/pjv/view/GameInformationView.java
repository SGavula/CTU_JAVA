package cz.cvut.fel.pjv.view;

import javax.swing.*;
import java.awt.*;

/**
 * The GameInformationView class represents the panel that displays game information,
 * including the player's turn and the elapsed game time.
 */
public class GameInformationView extends JPanel {
    private JLabel playerTurn;
    private JLabel time;
    private int elapsedTime = 0;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;
    private String secondsString = String.format("%02d", seconds);
    private String minutesString = String.format("%02d", minutes);
    private String hoursString = String.format("%02d", hours);
    private Timer gameTimer = new Timer( 1000, e -> {
        this.elapsedTime = elapsedTime + 1000;
        this.hours = (elapsedTime/3600000);
        this.minutes = (elapsedTime/60000) % 60;
        this.seconds = (elapsedTime/1000) % 60;
        this.secondsString = String.format("%02d", seconds);
        this.minutesString = String.format("%02d", minutes);
        this.hoursString = String.format("%02d", hours);
        this.time.setText("Game Time: " + hoursString + ":" + minutesString + ":" + secondsString);
    });

    /**
     * Constructs a new GameInformationView object.
     * Sets the preferred size, border, and initializes the labels for player turn and game time.
     */
    public GameInformationView() {
        this.setPreferredSize(new Dimension(300, 200));
        this.setBorder(BorderFactory.createEmptyBorder(60, 0, 0, 0));
        this.time = new JLabel();
        this.time.setHorizontalAlignment(JLabel.CENTER);
        this.time.setVerticalAlignment(JLabel.CENTER);
        this.time.setFont(new Font("Arial", Font.BOLD, 16));
        this.time.setForeground(Color.black);
        this.add(this.time);
        this.playerTurn = new JLabel();
        this.playerTurn.setHorizontalAlignment(JLabel.CENTER);
        this.playerTurn.setVerticalAlignment(JLabel.CENTER);
        this.playerTurn.setFont(new Font("Arial", Font.BOLD, 16));
        this.playerTurn.setForeground(Color.black);
        // Add an empty border with 10 pixels of top margin to playerTurn label
        this.playerTurn.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        this.add(playerTurn);
    }

    /**
     * Sets the player's turn to be displayed in the information view.
     *
     * @param player The player's turn to be displayed.
     */
    public void setPlayerTurn(String player) {
        this.playerTurn.setText(player + " player's turn.");
    }

    /**
     * Returns the game timer associated with the information view.
     *
     * @return The game timer.
     */
    public Timer getGameTimer() {
        return this.gameTimer;
    }

    /**
     * Returns the elapsed time of the game.
     *
     * @return The elapsed time in milliseconds.
     */
    public int getElapsedTime() {
        return elapsedTime;
    }

    /**
     * Sets the elapsed time of the game and updates the game time label.
     *
     * @param elapsedTime The elapsed time in milliseconds.
     */
    public void setElapsedTime(int elapsedTime) {
        this.elapsedTime = elapsedTime;
        this.hours = (elapsedTime/3600000);
        this.minutes = (elapsedTime/60000) % 60;
        this.seconds = (elapsedTime/1000) % 60;
        this.secondsString = String.format("%02d", seconds);
        this.minutesString = String.format("%02d", minutes);
        this.hoursString = String.format("%02d", hours);
        this.time.setText("Game Time: " + hoursString + ":" + minutesString + ":" + secondsString);
    }
}
