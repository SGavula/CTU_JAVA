package cz.cvut.fel.pjv.model;

/**
 * The `MoveModel` class represents a move in the game.
 * It holds the start position and end position of the move on the game board.
 */
public class MoveModel {
    private int startPosition;
    private int endPosition;

    /**
     * Constructs a `MoveModel` object with the specified start position and end position.
     *
     * @param startPosition The start position of the move.
     * @param endPosition   The end position of the move.
     */
    public MoveModel(int startPosition, int endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }

    /**
     * Returns the start position of the move.
     *
     * @return The start position of the move.
     */
    public int getStartPosition() {
        return startPosition;
    }

    /**
     * Sets the start position of the move.
     *
     * @param startPosition The start position to set.
     */
    public void setStartPosition(int startPosition) {
        this.startPosition = startPosition;
    }

    /**
     * Returns the end position of the move.
     *
     * @return The end position of the move.
     */
    public int getEndPosition() {
        return endPosition;
    }

    /**
     * Sets the end position of the move.
     *
     * @param endPosition The end position to set.
     */
    public void setEndPosition(int endPosition) {
        this.endPosition = endPosition;
    }
}
