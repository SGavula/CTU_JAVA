package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.utils.GameColors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * The `ComputerPlayerModel` class represents a computer player in a game. It extends the `PlayerModel` class
 * and implements the `Serializable` interface to enable saving class to the file.
 */
public class ComputerPlayerModel extends PlayerModel implements Serializable {
    /**
     * Creates a new `ComputerPlayerModel` instance with the specified player color and name.
     *
     * @param playerColor The color of the player.
     * @param playerName  The name of the player.
     */
    public ComputerPlayerModel(GameColors playerColor, String playerName) {
        super(playerColor, playerName);
        this.initPiecesArray();
    }

    /**
     * Sets the pieces of the computer player on the game board.
     * It overloads this methods
     *
     * @param gameBoardModel The game board model on which the pieces are set.
     */
    public void setPieceOnBoard(GameBoardModel gameBoardModel) {
        List<Integer> startingPositions = getStartingPositions();
        Random rand = new Random();
        while (!startingPositions.isEmpty()) {
            int randomPosition = getRandomPosition(startingPositions, rand);
            PieceModel piece = initPieces.get(0);
            piece.setPiecePosition(randomPosition);
            gameBoardModel.getSquare(randomPosition).setPieceOnSquare(piece);
            String pieceFirstLetter = getPieceLetter(piece.getName());
            addNotationToCurrentNotation(randomPosition, gameBoardModel, pieceFirstLetter);
            initPieces.remove(0);
        }

        if (initPieces.isEmpty()) {
            hasSetBoard = true;
        }
    }

    private List<Integer> getStartingPositions() {
        List<Integer> startingPositions = new ArrayList<>();
        if (getPlayerColor() == GameColors.BLACK) {
            for (int i = 0; i < 16; i++) {
                startingPositions.add(i);
            }
        } else {
            for (int i = 48; i < 64; i++) {
                startingPositions.add(i);
            }
        }
        return startingPositions;
    }

    private int getRandomPosition(List<Integer> positions, Random rand) {
        int upperRange = positions.size();
        int randomIndex = rand.nextInt(upperRange);
        int randomIndexInRange = randomIndex == 0 ? randomIndex : randomIndex - 1;
        return positions.remove(randomIndexInRange);
    }

    /**
     * Makes random moves for the computer player on the game board.
     *
     * @param gameBoardModel The game board model on which the moves are made.
     */
    public void makeMoves(GameBoardModel gameBoardModel) {
        List<MoveModel> allPossibleMoves = collectAllPossibleMoves(gameBoardModel);
        if (allPossibleMoves.isEmpty()) {
            return;
        }

        Random rand = new Random();
        int upperRange = allPossibleMoves.size();
        int randomIndex = rand.nextInt(upperRange);
        MoveModel randomMove = allPossibleMoves.get(randomIndex);

        if (randomMove instanceof PushMoveModel) {
            makePushMove(gameBoardModel, (PushMoveModel) randomMove, rand);
        }

        if (randomMove instanceof PullMoveModel && this.getNumberOfMoves() > 1) {
            List<MoveModel> pullMoves = ((PullMoveModel) randomMove).calculatePullMoves(gameBoardModel, this.getPlayerColor());
            makeMoveWithOwnPiece(gameBoardModel, randomMove);
            makePullMove(gameBoardModel, pullMoves, rand);
        } else {
            makeMoveWithOwnPiece(gameBoardModel, randomMove);
        }
    }

    //This function iterates over the board squares, checks for occupied squares belonging to the player, and calculates the valid moves for each piece.
    private List<MoveModel> collectAllPossibleMoves(GameBoardModel gameBoardModel) {
        List<MoveModel> allPossibleMoves = new ArrayList<>();

        for (SquareModel square : gameBoardModel.getBoard()) {
            if (!square.getIsOccupate()) {
                continue;
            }

            PieceModel piece = square.getPieceOnSquare();
            if (piece.getPieceColor() != this.getPlayerColor()) {
                continue;
            }

            List<MoveModel> piecePossibleMoves = piece.calculateValidMoves(gameBoardModel, this.getNumberOfMoves());
            allPossibleMoves.addAll(piecePossibleMoves);
        }

        return allPossibleMoves;
    }

    private void makePushMove(GameBoardModel gameBoardModel, PushMoveModel randomMove, Random rand) {
        List<MoveModel> pushMoves = randomMove.calculatePushMoves(gameBoardModel);
        if (pushMoves.isEmpty()) {
            return;
        }

        int upperRange = pushMoves.size();
        int randomPushIndex = rand.nextInt(upperRange);
        int randomPushIndexInRange = randomPushIndex == 0 ? randomPushIndex : randomPushIndex - 1;
        MoveModel randomPushMove = pushMoves.get(randomPushIndexInRange);

        SquareModel startSquare = gameBoardModel.getSquare(randomPushMove.getStartPosition());
        SquareModel endSquare = gameBoardModel.getSquare(randomPushMove.getEndPosition());
        PieceModel pushPiece = startSquare.getPieceOnSquare();

        startSquare.deletePieceOnSquare();
        pushPiece.setPiecePosition(randomPushMove.getEndPosition());
        endSquare.setPieceOnSquare(pushPiece);

        makeArimaaNotationOfMove(randomPushMove.getEndPosition(), randomPushMove.getStartPosition(), gameBoardModel, pushPiece);

        this.numberOfMoves--;
    }

    private void makeMoveWithOwnPiece(GameBoardModel gameBoardModel, MoveModel randomMove) {
        PieceModel piece = gameBoardModel.getSquare(randomMove.getStartPosition()).getPieceOnSquare();
        //Create arimaa notation
        makeArimaaNotationOfMove(randomMove.getEndPosition(), randomMove.getStartPosition(), gameBoardModel, piece);
        gameBoardModel.getSquare(randomMove.getStartPosition()).deletePieceOnSquare();
        piece.setPiecePosition(randomMove.getEndPosition());
        gameBoardModel.getSquare(randomMove.getEndPosition()).setPieceOnSquare(piece);
        this.numberOfMoves--;
    }

    private void makePullMove(GameBoardModel gameBoardModel, List<MoveModel> pullMoves, Random rand) {
        int upperRange = pullMoves.size();
        if (upperRange == 0) {
            return;
        }

        int randomPullIndex = rand.nextInt(upperRange);
        int randomPullIndexInRange = randomPullIndex == 0 ? randomPullIndex : randomPullIndex - 1;
        MoveModel randomPullMove = pullMoves.get(randomPullIndexInRange);

        SquareModel startSquare = gameBoardModel.getSquare(randomPullMove.getStartPosition());
        SquareModel endSquare = gameBoardModel.getSquare(randomPullMove.getEndPosition());
        PieceModel pullPiece = startSquare.getPieceOnSquare();

        startSquare.deletePieceOnSquare();
        pullPiece.setPiecePosition(randomPullMove.getEndPosition());
        endSquare.setPieceOnSquare(pullPiece);

        makeArimaaNotationOfMove(randomPullMove.getEndPosition(), randomPullMove.getStartPosition(), gameBoardModel, pullPiece);

        this.numberOfMoves--;
    }

    @Override
    public void setNumberOfMoves(int numberOfMoves) {
        Random rand = new Random();
        this.numberOfMoves = rand.nextInt(numberOfMoves) + 1;
    }
}
