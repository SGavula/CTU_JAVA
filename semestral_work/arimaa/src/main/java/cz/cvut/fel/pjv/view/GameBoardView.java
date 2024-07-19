package cz.cvut.fel.pjv.view;

import cz.cvut.fel.pjv.controllers.Controller;
import cz.cvut.fel.pjv.model.SquareModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * The GameBoardView class represents the visual representation of the game board.
 * It extends the JPanel class and provides methods for initializing and redrawing the game board.
 */
public class GameBoardView extends JPanel {
    /**
     * Initializes the game board view with the given game board array and controller.
     *
     * @param gameBoardArray The list of SquareModel objects representing the game board.
     * @param controller     The controller object for handling game board interactions.
     */
    public void initGameBoardView(List<SquareModel> gameBoardArray, Controller controller) {
        this.setBounds(0, 0, 490, 464);
        removeAll(); // Clear existing components
        setLayout(new GridLayout(8, 8)); //Set layout manager
        for(SquareModel square : gameBoardArray) {
            SquareView newSquare = new SquareView(square.getSquarePosition(), square.getSquareColor(), "", controller);
            this.add(newSquare);
        }
        revalidate(); // Revalidate the panel to recalculate the layout
        repaint(); // Repaint the panel to reflect the changes
    }

    /**
     * Redraws the game board view with the given game board array and controller.
     *
     * @param gameBoardArray The list of SquareModel objects representing the game board.
     * @param controller     The controller object for handling game board interactions.
     */

    public void redrawGameBoardView(List<SquareModel> gameBoardArray, Controller controller) {
        removeAll(); // Clear existing components
        setLayout(new GridLayout(8, 8)); //Set layout manager
        for(SquareModel square : gameBoardArray) {
            SquareView newSquare;
            if(square.getPieceOnSquare() != null) {
                newSquare = new SquareView(square.getSquarePosition(), square.getSquareColor(), square.getPieceOnSquare().getName(), controller);
            }else {
                newSquare = new SquareView(square.getSquarePosition(), square.getSquareColor(), "", controller);
            }
            this.add(newSquare);
        }
        revalidate(); // Revalidate the panel to recalculate the layout
        repaint(); // Repaint the panel to reflect the changes
    }
}
