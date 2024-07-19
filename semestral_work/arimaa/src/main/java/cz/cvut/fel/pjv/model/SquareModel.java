package cz.cvut.fel.pjv.model;

import java.awt.*;
import java.io.Serializable;

/**
 * The `SquareModel` class represents a square on the game board in the game of Arimaa.
 * It implements the `Serializable` interface.
 */
public class SquareModel implements Serializable {
    private int squarePosition;
    private PieceModel pieceOnSquare;
    private boolean isTrap;
    private Color color;
    private boolean isMoveSquare = false;
    private String arimaaNotation;

    /**
     * Constructs a `SquareModel` object with the specified parameters.
     *
     * @param squarePosition  The position of the square on the game board.
     * @param pieceOnSquare   The piece located on the square.
     * @param isTrap          Indicates whether the square is a trap.
     * @param color           The color of the square.
     * @param arimaaNotation  The Arimaa notation for the square.
     */
    SquareModel(int squarePosition, PieceModel pieceOnSquare, boolean isTrap, Color color, String arimaaNotation) {
        this.squarePosition = squarePosition;
        this.pieceOnSquare = pieceOnSquare;
        this.isTrap = isTrap;
        this.color = color;
        this.arimaaNotation = arimaaNotation;
    }

    /**
     * Checks if the square is occupied by a piece.
     *
     * @return True if the square is occupied, false otherwise.
     */
    public boolean getIsOccupate() {
        if(this.pieceOnSquare == null) {
            return false;
        }else {
            return true;
        }
    }

    /**
     * Sets the square as a move square and updates its color accordingly.
     *
     * @param moveSquare Indicates whether the square is a move square.
     */
    public void setIsMoveSquare(boolean moveSquare) {
        this.isMoveSquare = moveSquare;
        if(this.isMoveSquare == true) {
            this.color = new Color(255, 255, 0);
        }else {
            if(this.isTrap == true) {
                this.color = new Color(158,129,84);
            }else {
                this.color = new Color(230,193,133);
            }
        }
    }

    /**
     * Returns the position of the square on the game board.
     *
     * @return The position of the square.
     */
    public int getSquarePosition() {
        return squarePosition;
    }

    /**
     * Returns the piece located on the square.
     *
     * @return The piece on the square.
     */
    public PieceModel getPieceOnSquare() {
        return pieceOnSquare;
    }

    /**
     * Sets the piece located on the square.
     *
     * @param pieceOnSquare The piece to be placed on the square.
     */
    public void setPieceOnSquare(PieceModel pieceOnSquare) {
        this.pieceOnSquare = pieceOnSquare;
    }

    /**
     * Deletes the piece located on the square (sets it to null).
     */
    public void deletePieceOnSquare() {
        this.pieceOnSquare = null;
    }

    /**
     * Returns the Arimaa notation for the square.
     *
     * @return The Arimaa notation for the square.
     */
    public String getArimaaNotation() {
        return arimaaNotation;
    }

    /**
     * Returns the color of the square.
     *
     * @return The color of the square.
     */
    public Color getSquareColor() {
        return this.color;
    }

    /**
     * Checks if the square is a trap.
     *
     * @return True if the square is a trap, false otherwise.
     */
    public boolean isTrap() {
        return isTrap;
    }
}
