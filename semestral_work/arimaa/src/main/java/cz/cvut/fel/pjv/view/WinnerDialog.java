package cz.cvut.fel.pjv.view;

import javax.swing.*;

/**
 * A dialog for displaying the winner of the game.
 */
public class WinnerDialog {
    /**
     * Displays a dialog indicating the winner of the game.
     *
     * @param parentFrame The parent frame to which the dialog is attached.
     * @param winner      The color of winner as a string.
     */
    public void showDialog(JFrame parentFrame, String winner) {
        String message = winner + " player won the game.";
        // Show a dialog with a message and OK button
        JOptionPane.showMessageDialog(parentFrame, message, "The game is over", JOptionPane.CLOSED_OPTION);
    }
}