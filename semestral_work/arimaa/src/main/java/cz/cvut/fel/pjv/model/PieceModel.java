package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.utils.Constants;
import cz.cvut.fel.pjv.utils.GameColors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The `PieceModel` class represents a game piece in the game.
 * It holds information about the piece's color, strength, position, and frozen status.
 */
public class PieceModel implements Serializable {
    private int[] candidate_move_coordinates;
    private final GameColors pieceColor;
    private final int strength;
    private final String name;
    private boolean isFrozen;
    private boolean isGuarded = false;
    private int piecePosition;

    /**
     * Constructs a `PieceModel` object with the specified piece color, strength, frozen status,
     * piece position, name, and candidate move coordinates.
     *
     * @param pieceColor               The color of the piece.
     * @param strength                 The strength of the piece.
     * @param isFrozen                 The frozen status of the piece.
     * @param piecePosition            The position of the piece on the game board.
     * @param name                     The name of the piece.
     * @param candidate_move_coordinates The candidate move coordinates for the piece.
     */
    public PieceModel(GameColors pieceColor, int strength, boolean isFrozen, int piecePosition, String name, int[] candidate_move_coordinates) {
        this.pieceColor = pieceColor;
        this.strength = strength;
        this.isFrozen = isFrozen;
        this.piecePosition = piecePosition;
        this.name = name;
        this.candidate_move_coordinates = candidate_move_coordinates;
    }

    /**
     * Calculates the valid moves for the piece on the given game board and with the specified number of moves.
     *
     * @param gameBoardModel The game board model.
     * @param numberOfMoves  The number of moves available to the player.
     * @return A list of valid moves for the piece.
     */
    public List<MoveModel> calculateValidMoves(GameBoardModel gameBoardModel, int numberOfMoves) {
        List<MoveModel> validMoves = new ArrayList<>();

        for (int currentCandidate : candidate_move_coordinates) {
            int candidateDestinationPosition = this.piecePosition + currentCandidate;
            if (validateCandidateDestinationPosition(candidateDestinationPosition, gameBoardModel)) {
                if (isOnBorderAndInvalidMove(currentCandidate)) {
                    continue;
                }
                if (gameBoardModel.getSquare(candidateDestinationPosition).getIsOccupate()) {
                    if (numberOfMoves >= 2) {
                        addValidPushMoves(gameBoardModel, validMoves, candidateDestinationPosition);
                    }
                } else {
                    validMoves.add(createPullMove(candidateDestinationPosition));
                }
            }
        }

        return validMoves;
    }

    private boolean isOnBorderAndInvalidMove(int currentCandidate) {
        return (isOnLeftBorder() && currentCandidate == -1) || (isOnRightBorder() && currentCandidate == 1);
    }

    private boolean isOnLeftBorder() {
        return this.piecePosition % 8 == 0;
    }

    private boolean isOnRightBorder() {
        return this.piecePosition % 8 == 7;
    }

    private void addValidPushMoves(GameBoardModel gameBoardModel, List<MoveModel> validMoves, int candidateDestinationPosition) {
        PushMoveModel newPushMove = new PushMoveModel(this.piecePosition, candidateDestinationPosition);
        List<MoveModel> pushMoves = newPushMove.calculatePushMoves(gameBoardModel);
        if (!pushMoves.isEmpty()) {
            validMoves.add(newPushMove);
        }
    }

    private PullMoveModel createPullMove(int candidateDestinationPosition) {
        return new PullMoveModel(this.piecePosition, candidateDestinationPosition);
    }

    private boolean validateCandidateDestinationPosition(int candidateDestinationPosition, GameBoardModel gameBoardModel) {
        //Validate if destination move is on the board
        if((candidateDestinationPosition < 0) || (candidateDestinationPosition > 63)) {
            return false;
        }
        //Find if on the destination board is my piece
        SquareModel square = gameBoardModel.getSquare(candidateDestinationPosition);
        if(square.getIsOccupate() == true) {
            if(square.getPieceOnSquare().getPieceColor() == this.pieceColor) {
                return false;
            }
            if(square.getPieceOnSquare().getStrength() >= this.strength) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculates whether the piece is frozen based on the current game board configuration.
     * The piece is considered frozen if it is not guarded and there is a stronger opposing piece
     * adjacent to it. The frozen status of the piece is updated accordingly.
     *
     * @param gameBoardModel The game board model containing the current board configuration.
     */
    public void calculateIsPieceFrozen(GameBoardModel gameBoardModel) {
        this.isGuarded = false;

        for (int currentCandidate : Constants.CANDIDATE_MOVE_COORDINATES_NORMAL) {
            int candidateDestinationPosition = this.piecePosition + currentCandidate;
            if (isInvalidDestinationPosition(candidateDestinationPosition)) {
                continue;
            }
            if (!isOccupiedSquare(gameBoardModel, candidateDestinationPosition)) {
                continue;
            }

            PieceModel pieceOnSquare = gameBoardModel.getSquare(candidateDestinationPosition).getPieceOnSquare();
            if (isSameColorPiece(pieceOnSquare)) {
                isGuarded = true;
                isFrozen = false;
            } else {
                checkFrozenCondition(gameBoardModel, candidateDestinationPosition);
            }
        }
    }

    private boolean isInvalidDestinationPosition(int candidateDestinationPosition) {
        return candidateDestinationPosition < 0 || candidateDestinationPosition > 63;
    }

    private boolean isOccupiedSquare(GameBoardModel gameBoardModel, int candidateDestinationPosition) {
        return gameBoardModel.getSquare(candidateDestinationPosition).getIsOccupate();
    }

    private boolean isSameColorPiece(PieceModel piece) {
        return piece.getPieceColor() == this.pieceColor;
    }

    private void checkFrozenCondition(GameBoardModel gameBoardModel, int candidateDestinationPosition) {
        if (!isGuarded && isStrongerPiece(gameBoardModel, candidateDestinationPosition)) {
            isFrozen = true;
        }
    }

    private boolean isStrongerPiece(GameBoardModel gameBoardModel, int candidateDestinationPosition) {
        return gameBoardModel.getSquare(candidateDestinationPosition).getPieceOnSquare().getStrength() > this.strength;
    }

    /**
     * Returns the name of the piece.
     *
     * @return The name of the piece.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the position of the piece on the game board.
     *
     * @param piecePosition The position to set for the piece.
     */
    public void setPiecePosition(int piecePosition) {
        this.piecePosition = piecePosition;
    }

    /**
     * Returns the position of the piece on the game board.
     *
     * @return The position of the piece.
     */
    public int getPiecePosition() {
        return this.piecePosition;
    }

    /**
     * Returns the color of the piece.
     *
     * @return The color of the piece.
     */
    public GameColors getPieceColor() {
        return this.pieceColor;
    }

    /**
     * Returns the strength of the piece.
     *
     * @return The strength of the piece.
     */
    public int getStrength() {
        return strength;
    }

    /**
     * Returns the frozen status of the piece.
     *
     * @return `true` if the piece is frozen, `false` otherwise.
     */
    public boolean isFrozen() {
        return isFrozen;
    }
    /**
     * Returns the guarded status of the piece.
     *
     * @return `true` if the piece is guarded, `false` otherwise.
     */
    public boolean getIsGuared() {return this.isGuarded; }
}
