package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.controllers.PlayerVsPlayerController;
import cz.cvut.fel.pjv.utils.GameColors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static cz.cvut.fel.pjv.utils.Constants.*;

/**
 * The PlayerModel class represents a player in the Arimaa game.
 * It contains information about the player's pieces, current moves, game state, and other attributes.
 */
public class PlayerModel implements Serializable {
    private static final Logger logger = Logger.getLogger(PlayerModel.class.getName());
    private PieceModel piece;
    private PieceModel enemyPiece;
    private SquareModel sourceSquare;
    private SquareModel currentSquare;
    private List<MoveModel> validMoves = new ArrayList<>();
    private List<MoveModel> enemyMoves = new ArrayList<>();
    private List<MoveModel> pullMoves = new ArrayList<>();
    private GameColors playerColor;
    protected boolean hasSetBoard = false;
    protected int numberOfMoves = NUMBER_OF_MOVES;
    private boolean makePullMove = false;
    private String playerName;
    private int numberOfRabbits = 8;
    protected List<PieceModel> initPieces = new ArrayList<>();
    private int playerElapsedTime = 0;
    private int moveNumber = 0;
    private String currentArimaaNotation = "";

    /**
     * Constructs a PlayerModel object with the specified player color and name.
     * @param playerColor The color of the player (WHITE or BLACK)
     * @param playerName The name of the player
     */
    public PlayerModel(GameColors playerColor, String playerName) {
        this.playerColor = playerColor;
        this.playerName = playerName;
        this.initPiecesArray();
    }

    /**
     * Makes a move for the player based on the selected square and game board model.
     * @param squareId The ID of the selected square
     * @param gameBoardModel The game board model
     */
    public void makeMove(int squareId, GameBoardModel gameBoardModel) {
        unselectValidMoves(gameBoardModel, this.validMoves);
        unselectValidMoves(gameBoardModel, this.enemyMoves);
        unselectStartPositions(gameBoardModel, this.pullMoves);

        this.currentSquare = gameBoardModel.getSquare(squareId);

        if (isSelectablePiece(currentSquare)) {
            selectPiece(gameBoardModel);
        } else {
            selectMove(squareId, gameBoardModel);
        }
    }

    /**
     * Unselects the valid moves on the game board.
     *
     * @param gameBoardModel The game board model.
     * @param moves          The list of moves to be unselected.
     */
    private void unselectValidMoves(GameBoardModel gameBoardModel, List<MoveModel> moves) {
        if (!moves.isEmpty()) {
            for (MoveModel move : moves) {
                gameBoardModel.getSquare(move.getEndPosition()).setIsMoveSquare(false);
            }
        }
    }

    /**
     * Unselects the start positions on the game board.
     *
     * @param gameBoardModel The game board model.
     * @param moves          The list of moves to be unselected.
     */
    private void unselectStartPositions(GameBoardModel gameBoardModel, List<MoveModel> moves) {
        if (!moves.isEmpty()) {
            for (MoveModel move : moves) {
                gameBoardModel.getSquare(move.getStartPosition()).setIsMoveSquare(false);
            }
        }
    }

    private boolean isSelectablePiece(SquareModel square) {
        PieceModel piece = square.getPieceOnSquare();
        return square.getIsOccupate() && piece.getPieceColor() == this.playerColor && !piece.isFrozen();
    }

    /**
     * Selects the piece on the current square and calculates the valid moves for that piece.
     *
     * @param gameBoardModel The game board model.
     */
    private void selectPiece(GameBoardModel gameBoardModel) {
        //Select square on the board and get piece
        this.sourceSquare = this.currentSquare;
        this.piece = this.currentSquare.getPieceOnSquare();
        //Calculate valid moves for current piece
        this.validMoves = this.piece.calculateValidMoves(gameBoardModel, this.numberOfMoves);
        //Show the moves
        for (MoveModel validMove : this.validMoves) {
            gameBoardModel.getSquare(validMove.getEndPosition()).setIsMoveSquare(true);
        }
        //Clear pull moves if I select my piece
        if(this.pullMoves.size() != 0) {
            this.pullMoves.clear();
        }
    }

    private void selectMove(int squareId, GameBoardModel gameBoardModel) {
        if (!pullMoves.isEmpty()) {
            pullPiece(squareId, gameBoardModel);
        }

        MoveModel validMove = findValidMove(squareId);
        if (validMove == null && enemyMoves.isEmpty()) {
            logger.warning("Move is not valid");
            return;
        }

        if (currentSquare.getIsOccupate()) {
            hightlightPushMoves(squareId, gameBoardModel, (PushMoveModel) validMove);
        } else {
            if (enemyMoves.isEmpty()) {
                movePiece(squareId, gameBoardModel, (PullMoveModel) validMove);
            } else {
                pushPiece(squareId, gameBoardModel);
            }
        }
    }

    private MoveModel findValidMove(int squareId) {
        for (MoveModel validMove : validMoves) {
            if (validMove.getEndPosition() == squareId) {
                return validMove;
            }
        }
        return null;
    }

    private void movePiece(int squareId, GameBoardModel gameBoardModel, PullMoveModel move) {
        if(this.numberOfMoves >= 2) {
            this.pullMoves = move.calculatePullMoves(gameBoardModel, this.playerColor);
        }
        //Make arimaa notation of move
        makeArimaaNotationOfMove(squareId, this.sourceSquare.getSquarePosition(), gameBoardModel, this.piece);
        //Move piece logic
        gameBoardModel.getSquare(this.sourceSquare.getSquarePosition()).deletePieceOnSquare();
        this.piece.setPiecePosition(squareId);
        gameBoardModel.getSquare(squareId).setPieceOnSquare(this.piece);
        this.numberOfMoves--;
        this.validMoves.clear();
        if(this.pullMoves.size() != 0) {
            gameBoardModel.setShowDialog(true);
        }
    }

    private void hightlightPushMoves(int squareId, GameBoardModel gameBoardModel, PushMoveModel move) {
        this.enemyPiece = gameBoardModel.getSquare(squareId).getPieceOnSquare();
        this.enemyMoves = move.calculatePushMoves(gameBoardModel);

        for (MoveModel validMove : this.enemyMoves) {
            gameBoardModel.getSquare(validMove.getEndPosition()).setIsMoveSquare(true);
        }
    }

    /**
     * Highlights the pull moves on the game board.
     *
     * @param gameBoardModel The game board model representing the current state of the game.
     */
    public void hightlightPullMoves(GameBoardModel gameBoardModel) {
        if(this.makePullMove == false) {
            gameBoardModel.setShowDialog(false);
            // Clear the pull ArrayList (remove all elements)
            this.pullMoves.clear();
            return;
        }

        for (MoveModel validMove : this.pullMoves) {
            gameBoardModel.getSquare(validMove.getStartPosition()).setIsMoveSquare(true);
        }

        gameBoardModel.setShowDialog(false);
    }

    private void pushPiece(int squareId, GameBoardModel gameBoardModel) {
        MoveModel enemyMove = findValidEnemyMove(squareId);
        if (enemyMove == null) {
            return;
        }

        // Delete pieces from the board
        gameBoardModel.getSquare(sourceSquare.getSquarePosition()).deletePieceOnSquare();
        gameBoardModel.getSquare(enemyPiece.getPiecePosition()).deletePieceOnSquare();

        // Set player's piece
        piece.setPiecePosition(enemyPiece.getPiecePosition());
        gameBoardModel.getSquare(enemyPiece.getPiecePosition()).setPieceOnSquare(piece);

        // Set enemy piece
        enemyPiece.setPiecePosition(squareId);
        gameBoardModel.getSquare(squareId).setPieceOnSquare(enemyPiece);

        // Make Arimaa notation of enemy piece move
        makeArimaaNotationOfMove(enemyPiece.getPiecePosition(), piece.getPiecePosition(), gameBoardModel, enemyPiece);

        // Make Arimaa notation of player piece move
        makeArimaaNotationOfMove(piece.getPiecePosition(), sourceSquare.getSquarePosition(), gameBoardModel, piece);

        numberOfMoves -= 2;
        validMoves.clear();
        enemyMoves.clear();
    }

    private MoveModel findValidEnemyMove(int squareId) {
        for (MoveModel enemyMove : enemyMoves) {
            if (enemyMove.getEndPosition() == squareId) {
                return enemyMove;
            }
        }
        return null;
    }

    private void pullPiece(int squareId, GameBoardModel gameBoardModel) {
        MoveModel move = findValidPullMove(squareId);
        if (move != null) {
            SquareModel startSquare = gameBoardModel.getSquare(move.getStartPosition());
            SquareModel endSquare = gameBoardModel.getSquare(move.getEndPosition());
            PieceModel pulledPiece = startSquare.getPieceOnSquare();

            // Make Arimaa notation of player piece move
            endSquare.setPieceOnSquare(pulledPiece);
            startSquare.deletePieceOnSquare();
            pulledPiece.setPiecePosition(move.getEndPosition());
            makeArimaaNotationOfMove(move.getEndPosition(), move.getStartPosition(), gameBoardModel, pulledPiece);

            numberOfMoves--;
            validMoves.clear();
            pullMoves.clear();
        }
    }

    private MoveModel findValidPullMove(int squareId) {
        for (MoveModel pullMove : pullMoves) {
            if (pullMove.getStartPosition() == squareId) {
                return pullMove;
            }
        }
        return null;
    }

    /**
     * Initializes the pieces for the player.
     */
    protected void initPiecesArray() {
        PieceModel[] pieces = new PieceModel[16];

        String rabType = (this.playerColor == GameColors.WHITE) ? "rab_w" : "rab_b";
        String catType = (this.playerColor == GameColors.WHITE) ? "cat_w" : "cat_b";
        String dogType = (this.playerColor == GameColors.WHITE) ? "dog_w" : "dog_b";
        String horType = (this.playerColor == GameColors.WHITE) ? "hor_w" : "hor_b";
        String mcamType = (this.playerColor == GameColors.WHITE) ? "mcam_w" : "mcam_b";
        String eleType = (this.playerColor == GameColors.WHITE) ? "ele_w" : "ele_b";

        int index = 0;
        for (int i = 0; i < 8; i++) {
            pieces[index++] = new PieceModel(this.playerColor, 1, false, 0, rabType,
                    (this.playerColor == GameColors.WHITE) ?
                            CANDIDATE_MOVE_COORDINATES_RAB_WHITE
                            :
                            CANDIDATE_MOVE_COORDINATES_RAB_BLACK
            );
        }
        for (int i = 0; i < 2; i++) {
            pieces[index++] = new PieceModel(this.playerColor, 2, false, 0, catType, CANDIDATE_MOVE_COORDINATES_NORMAL);
        }
        for (int i = 0; i < 2; i++) {
            pieces[index++] = new PieceModel(this.playerColor, 3, false, 0, dogType, CANDIDATE_MOVE_COORDINATES_NORMAL);
        }
        for (int i = 0; i < 2; i++) {
            pieces[index++] = new PieceModel(this.playerColor, 4, false, 0, horType, CANDIDATE_MOVE_COORDINATES_NORMAL);
        }
        pieces[index++] = new PieceModel(this.playerColor, 5, false, 0, mcamType, CANDIDATE_MOVE_COORDINATES_NORMAL);
        pieces[index++] = new PieceModel(this.playerColor, 6, false, 0, eleType, CANDIDATE_MOVE_COORDINATES_NORMAL);

        this.initPieces.addAll(Arrays.asList(pieces));
    }

    /**
     * Sets a piece on the game board at the specified square.
     *
     * @param squareId       The ID of the square where the piece is to be set.
     * @param gameBoardModel The game board model representing the current state of the game.
     */
    public void setPieceOnBoard(int squareId, GameBoardModel gameBoardModel) {
        SquareModel square = gameBoardModel.getSquare(squareId);
        PieceModel piece = this.initPieces.get(0);
        if(this.playerColor == GameColors.WHITE) {
            if(squareId <= 63 && squareId >= 48 && square.getPieceOnSquare() == null) {
                setPieceLogic(squareId, gameBoardModel, piece);
                String pieceFirstLetter = getPieceLetter(piece.getName()).toUpperCase();
                addNotationToCurrentNotation(squareId, gameBoardModel, pieceFirstLetter);
            }
        }else {
            if(squareId <= 15 && squareId >= 0 && square.getPieceOnSquare() == null) {
                setPieceLogic(squareId, gameBoardModel, piece);
                String pieceFirstLetter = getPieceLetter(piece.getName());
                addNotationToCurrentNotation(squareId, gameBoardModel, pieceFirstLetter);
            }
        }

        if(this.initPieces.size() == 0) {
            this.hasSetBoard = true;
        }
    }

    private void setPieceLogic(int squareId, GameBoardModel gameBoardModel, PieceModel piece) {
        piece.setPiecePosition(squareId);
        gameBoardModel.getSquare(squareId).setPieceOnSquare(piece);
        this.initPieces.remove(0);
    }

    protected String getPieceLetter(String name) {
        char firstChar = name.charAt(0);
        String firstCharToString = String.valueOf(firstChar);
        return firstCharToString;
    }

    protected void addNotationToCurrentNotation(int squareId, GameBoardModel gameBoardModel, String pieceFirstLetter) {
        String arimaaNotaionOfMove = pieceFirstLetter + gameBoardModel.getSquare(squareId).getArimaaNotation();
        this.currentArimaaNotation = this.currentArimaaNotation + " " + arimaaNotaionOfMove;
    }

    protected void makeArimaaNotationOfMove(int currentSquareId, int sourceSquareId, GameBoardModel gameBoardModel, PieceModel piece) {
        String pieceFirstLetter = getPieceLetter(piece.getName());
        if(piece.getPieceColor() == GameColors.WHITE) {
            pieceFirstLetter = getPieceLetter(piece.getName().toUpperCase());
        }
        addNotationToCurrentNotation(sourceSquareId, gameBoardModel, pieceFirstLetter);
        String directionOfMove = calculateDirectionOfMove(currentSquareId, sourceSquareId);
        this.currentArimaaNotation = this.currentArimaaNotation + directionOfMove;
    }

    private String calculateDirectionOfMove(int currentSquareId, int sourceSquareId) {
        String result = "";
        if(sourceSquareId - 8 == currentSquareId) {
            result = "n";
        } else if (sourceSquareId - 1 == currentSquareId) {
            result = "w";
        } else if (sourceSquareId + 1 == currentSquareId) {
            result = "e";
        } else if (sourceSquareId + 8 == currentSquareId) {
            result = "s";
        }
        return result;
    }

    /**
     * Increases the move number by 1 and updates the current Arimaa notation.
     */
    public void increaseMoveNumber() {
        this.moveNumber++;
        String stringNumber = String.valueOf(this.moveNumber);
        String stringColor = "";
        if(this.playerColor == GameColors.WHITE) {
            stringColor = "w";
        }else {
            stringColor = "b";
        }
        this.currentArimaaNotation = stringNumber + stringColor;
    }

    /**
     * Returns the number of moves.
     *
     * @return The number of moves.
     */
    public int getNumberOfMoves() {
        return numberOfMoves;
    }

    /**
     * Sets the number of moves.
     *
     * @param numberOfMoves The number of moves to set.
     */
    public void setNumberOfMoves(int numberOfMoves) {
        this.numberOfMoves = numberOfMoves;
    }

    /**
     * Checks if the board has been set.
     *
     * @return True if the board has been set, false otherwise.
     */
    public boolean getHasSetBoard() {
        return hasSetBoard;
    }

    /**
     * Sets the value indicating if the next move is a pull move.
     *
     * @param makePullMove True if the next move is a pull move, false otherwise.
     */
    public void setMakePullMove(boolean makePullMove) {
        this.makePullMove = makePullMove;
    }

    /**
     * Returns the number of remaining rabbits.
     *
     * @return The number of remaining rabbits.
     */
    public int getNumberOfRabbits() {
        return numberOfRabbits;
    }

    /**
     * Decreases the number of remaining rabbits by 1.
     */
    public void decreaseNumberOfRabbits() {
        this.numberOfRabbits = this.numberOfRabbits - 1;
    }

    /**
     * Returns the player's color.
     *
     * @return The player's color.
     */
    public GameColors getPlayerColor() {
        return playerColor;
    }

    /**
     * Returns the player's name.
     *
     * @return The player's name.
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * Returns the player's elapsed time.
     *
     * @return The player's elapsed time.
     */
    public int getPlayerElapsedTime() {
        return playerElapsedTime;
    }

    /**
     * Sets the player's elapsed time.
     *
     * @param playerElapsedTime The player's elapsed time to set.
     */
    public void setPlayerElapsedTime(int playerElapsedTime) {
        this.playerElapsedTime = playerElapsedTime;
    }

    /**
     * Returns the move number.
     *
     * @return The move number.
     */
    public int getMoveNumber() {
        return moveNumber;
    }

    /**
     * Returns the current Arimaa notation.
     *
     * @return The current Arimaa notation.
     */
    public String getCurrentArimaaNotation() {
        return currentArimaaNotation;
    }

    /**
     * Sets the current Arimaa notation.
     *
     * @param currentArimaaNotation The current Arimaa notation to set.
     */
    public void setCurrentArimaaNotation(String currentArimaaNotation) {
        this.currentArimaaNotation = currentArimaaNotation;
    }
}
