package cz.cvut.fel.pjv.model;

import javax.swing.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static cz.cvut.fel.pjv.utils.Constants.CANDIDATE_MOVE_COORDINATES_NORMAL;

/**
 * The `PushMoveModel` class represents a push move in the game of Arimaa.
 * It extends the `MoveModel` class and implements the `Serializable` interface to enable saving class to the file
 */
public class PushMoveModel extends MoveModel implements Serializable {
    private int startPositionEnemy;

    /**
     * Constructs a `PushMoveModel` object with the specified start and end positions.
     *
     * @param startPosition The starting position of the push move.
     * @param endPosition   The ending position of the push move.
     */
    public PushMoveModel(int startPosition, int endPosition) {
        super(startPosition, endPosition);
        this.startPositionEnemy = endPosition;
    }

    /**
     * Calculates and returns a list of possible push moves from the current enemy position.
     *
     * @param gameBoardModel The game board model to evaluate the push moves on.
     * @return A list of `MoveModel` objects representing possible push moves.
     */
    public List<MoveModel> calculatePushMoves(GameBoardModel gameBoardModel) {
        int candidateDestinationPosition;
        List<MoveModel> pushMoves = new ArrayList<>();

        for(int currectCandidate : CANDIDATE_MOVE_COORDINATES_NORMAL) {
            candidateDestinationPosition = this.startPositionEnemy + currectCandidate;
            if(validateCandidateDestinationPosition(candidateDestinationPosition, gameBoardModel) == true) {
                //Boarder squares
                if(this.startPositionEnemy % 8 == 0 && currectCandidate == -1) {
                    continue;
                }
                if(this.startPositionEnemy % 8 == 7 && currectCandidate == 1) {
                    continue;
                }
                pushMoves.add(new MoveModel(this.startPositionEnemy, candidateDestinationPosition));
            }
        }

        return pushMoves;
    }

    /**
     * Validates if the candidate destination position is a valid push move.
     *
     * @param candidateDestinationPosition The candidate destination position to validate.
     * @param gameBoardModel               The game board model to evaluate the position on.
     * @return True if the candidate destination position is a valid push move, false otherwise.
     */
    private boolean validateCandidateDestinationPosition(int candidateDestinationPosition, GameBoardModel gameBoardModel) {
        //Validate if destination move is on the board
        if((candidateDestinationPosition < 0) || (candidateDestinationPosition > 63)) {
            return false;
        }
        //Find if on the destination board is my piece
        SquareModel square = gameBoardModel.getSquare(candidateDestinationPosition);
        if(square.getIsOccupate() == true) {
            return false;
        }
        return true;
    }
}
