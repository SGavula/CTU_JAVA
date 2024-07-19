package cz.cvut.fel.pjv.view;

import javax.swing.*;

/**
 * A dialog box for confirming whether a pull move should be made.
 */
public class PullMoveDialog {
    /**
     * Shows the pull move dialog.
     *
     * @param parentFrame The parent JFrame to which the dialog is attached.
     * @return true if the user chooses to make a pull move, false otherwise.
     */
    public boolean showDialog(JFrame parentFrame) {
        // Show a dialog with a message and OK button
        int choice = JOptionPane.showConfirmDialog(parentFrame, "Do you want to make a pull move?", "Making move", JOptionPane.YES_NO_OPTION);
        System.out.println(choice);
        if (choice == JOptionPane.YES_OPTION) {
            return true;
        } else {
            return false;
        }

    }
}