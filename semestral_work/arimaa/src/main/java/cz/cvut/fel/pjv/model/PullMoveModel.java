package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.utils.Constants;
import cz.cvut.fel.pjv.utils.GameColors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pull move in the game.
 * Extends the MoveModel class and implements the Serializable interface to enable saving class to the file.
 */
public class PullMoveModel extends MoveModel implements Serializable {
    /**
     * Constructs a new instance of the PullMoveModel class with the specified start and end positions.
     *
     * @param startPosition The start position of the pull move.
     * @param endPosition   The end position of the pull move.
     */
    public PullMoveModel(int startPosition, int endPosition) {
        super(startPosition, endPosition);
    }

    /**
     * Calculates the valid pull moves for a given game board model and piece color.
     *
     * @param gameBoardModel The game board model representing the current state of the game.
     * @param pieceColor     The color of the piece performing the pull move.
     * @return A list of valid pull moves.
     */
    public List<MoveModel> calculatePullMoves(GameBoardModel gameBoardModel, GameColors pieceColor) {
        List<MoveModel> pullMoves = new ArrayList<>();

        for (int currentCandidate : Constants.CANDIDATE_MOVE_COORDINATES_NORMAL) {
            int enemyPiecePosition = getStartPosition() + currentCandidate;
            if (validateEnemyPiecePosition(enemyPiecePosition, gameBoardModel, pieceColor)) {
                if (isBorderSquare(getStartPosition(), currentCandidate)) {
                    continue;
                }

                if (canPullPiece(gameBoardModel, enemyPiecePosition)) {
                    pullMoves.add(createPullMove(enemyPiecePosition));
                }
            }
        }

        return pullMoves;
    }

    /**
     * Checks if the current square is a border square.
     *
     * @param startPosition    The start position of the pull move.
     * @param currentCandidate The candidate move coordinate.
     * @return True if the square is a border square, false otherwise.
     */
    private boolean isBorderSquare(int startPosition, int currentCandidate) {
        if (startPosition % 8 == 0 && currentCandidate == -1) {
            return true;
        }
        if (startPosition % 8 == 7 && currentCandidate == 1) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the piece can pull the enemy piece based on their strengths.
     *
     * @param gameBoardModel      The game board model representing the current state of the game.
     * @param enemyPiecePosition The position of the enemy piece.
     * @return True if the piece can pull the enemy piece, false otherwise.
     */
    private boolean canPullPiece(GameBoardModel gameBoardModel, int enemyPiecePosition) {
        int pieceStrength = gameBoardModel.getSquare(getStartPosition()).getPieceOnSquare().getStrength();
        int enemyPieceStrength = gameBoardModel.getSquare(enemyPiecePosition).getPieceOnSquare().getStrength();

        return pieceStrength > enemyPieceStrength;
    }

    /**
     * Creates a pull move with the specified enemy piece position.
     *
     * @param enemyPiecePosition The position of the enemy piece.
     * @return A pull move model.
     */
    private MoveModel createPullMove(int enemyPiecePosition) {
        return new MoveModel(enemyPiecePosition, getStartPosition());
    }

    /**
     * Validates the enemy piece position for a pull move.
     *
     * @param enemyPiecePosition The position of the enemy piece.
     * @param gameBoardModel     The game board model representing the current state of the game.
     * @param pieceColor         The color of the piece performing the pull move.
     * @return True if the enemy piece position is valid for a pull move, false otherwise.
     */
    private boolean validateEnemyPiecePosition(int enemyPiecePosition, GameBoardModel gameBoardModel, GameColors pieceColor) {
        if (!isWithinBoard(enemyPiecePosition)) {
            return false;
        }

        SquareModel square = gameBoardModel.getSquare(enemyPiecePosition);
        return square.getIsOccupate() && square.getPieceOnSquare().getPieceColor() != pieceColor;
    }

    /**
     * Checks if a position is within the bounds of the game board.
     *
     * @param position The position to check.
     * @return True if the position is within the game board bounds, false otherwise.
     */
    private boolean isWithinBoard(int position) {
        return position >= 0 && position <= 63;
    }
}
