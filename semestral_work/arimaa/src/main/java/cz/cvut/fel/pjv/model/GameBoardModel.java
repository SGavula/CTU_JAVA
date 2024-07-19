package cz.cvut.fel.pjv.model;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The `GameBoardModel` class represents the game board in a game. It implements the `Serializable` interface to enable saving class to the file.
 */
public class GameBoardModel implements Serializable {
    private final Color normalSquareColor = new Color(230,193,133);
    private final Color trapSquareColor = new Color(158,129,84);
    private List<SquareModel> board;
    private boolean isMakingMove;
    private boolean showDialog = false;

    /**
     * Creates a new `GameBoardModel` instance with an initialized board.
     */
    public GameBoardModel() {
        this.board = initBoard();
        this.isMakingMove = false;
    }

    /**
     * Initializes the game board with square models.
     *
     * @return The list of square models representing the game board.
     */
    private List<SquareModel> initBoard() {
        List<SquareModel> BoardSquares = new ArrayList<>();
        for(int i = 0; i < 64; i++) {
            SquareModel square;
            String arimaaNotation = createArimaaNotation(i);
            if(i == 18 || i == 21 || i == 42 || i == 45) {
                square = new SquareModel(i, null, true, trapSquareColor, arimaaNotation);
            }else {
                square = new SquareModel(i, null, false, normalSquareColor, arimaaNotation);
            }
            BoardSquares.add(square);
        }
        return BoardSquares;
    }

    /**
     * Creates the Arimaa notation for the given square ID.
     *
     * @param squareId The ID of the square.
     * @return The Arimaa notation string for the square.
     */
    public String createArimaaNotation(int squareId) {
        int row = squareId / 8;
        int col = squareId % 8;
        String rowNotation = String.valueOf(8 - row);
        String colNotation =  String.valueOf((char)('a' + col));

        return colNotation + rowNotation;
    }

    /**
     * Returns a string representation of the `GameBoardModel`.
     *
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("MyClass [");
        for (int i = 0; i < board.size(); i++) {
            sb.append(board.get(i).getSquarePosition());
            sb.append(" ");
            sb.append(board.get(i).getPieceOnSquare());
            sb.append(" ");
            if(board.get(i).getPieceOnSquare() != null) {
                sb.append(board.get(i).getPieceOnSquare().getStrength());
            }else {
                sb.append("0");
            }
            if (i < board.size() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    /**
     * Returns the flag indicating whether a move is being made on the game board.
     *
     * @return `true` if a move is being made, `false` otherwise.
     */
    public boolean getIsMakingMove() {
        return isMakingMove;
    }

    /**
     * Sets the flag indicating whether a move is being made on the game board.
     *
     * @param makingMove The flag value to set.
     */
    public void setMakingMove(boolean makingMove) {
        isMakingMove = makingMove;
    }

    /**
     * Returns the square model at the specified square ID.
     *
     * @param squareId The ID of the square.
     * @return The square model at the specified square ID.
     */
    public SquareModel getSquare(int squareId) {
        return board.get(squareId);
    }

    /**
     * Returns the list of square models representing the game board.
     *
     * @return The list of square models representing the game board.
     */
    public List<SquareModel> getBoard() {
        return this.board;
    }
    /**
     * Returns the flag indicating whether to show a dialog on the game board.
     *
     * @return `true` if the dialog should be shown, `false` otherwise.
     */
    public boolean getShowDialog() {
        return showDialog;
    }

    /**
     * Sets the flag indicating whether to show a dialog on the game board.
     *
     * @param showDialog The flag value to set.
     */
    public void setShowDialog(boolean showDialog) {
        this.showDialog = showDialog;
    }
}
