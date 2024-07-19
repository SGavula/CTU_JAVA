package cz.cvut.fel.pjv.controllers;

import cz.cvut.fel.pjv.model.GameBoardModel;
import cz.cvut.fel.pjv.model.GameModel;
import cz.cvut.fel.pjv.model.PieceModel;
import cz.cvut.fel.pjv.utils.GameColors;
import cz.cvut.fel.pjv.view.GameFrameViewRecord;

import java.util.ArrayList;
import java.util.Arrays;

import static cz.cvut.fel.pjv.utils.Constants.*;
import static cz.cvut.fel.pjv.utils.Constants.CANDIDATE_MOVE_COORDINATES_NORMAL;

/**
 * The controller class for managing game records and playback.
 */
public class RecordController {
    private GameModel gameModel;
    private GameBoardModel gameBoardModel;
    private GameFrameViewRecord gameFrameViewRecord;
    private int idxOfGameMove = 0;
    private String moveString = "";

    private ArrayList<PieceModel> whitePieces = new ArrayList<>();
    private ArrayList<PieceModel> blackPieces = new ArrayList<>();
    /**
     * Constructs a new instance of the RecordController class with the specified game model.
     *
     * @param gameModel The game model associated with the controller.
     */
    public RecordController(GameModel gameModel) {
        this.gameModel = gameModel;
        this.gameBoardModel = new GameBoardModel();
        this.gameFrameViewRecord = new GameFrameViewRecord(this);
        //Init game board
        this.gameFrameViewRecord.getGameBoardView().initGameBoardView(this.gameBoardModel.getBoard(), null);
    }

    /**
     * Handles the "Next Move" button click event.
     * Moves the game forward to the next recorded move.
     */
    public void handleNextMoveBtn() {
        if(this.idxOfGameMove >= this.gameModel.getGameRecords().size()) {
            return;
        }

        if(this.idxOfGameMove < 0) {
            this.idxOfGameMove++;
        }

        getMoveString();
        if(idxOfGameMove == 0) {
            setWhitePieces();
        }else if(idxOfGameMove == 1) {
            setBlackPieces();
        }else {
            makeMoveFromRecord();
        }
        this.gameFrameViewRecord.getGameBoardView().redrawGameBoardView(this.gameBoardModel.getBoard(), null);

        this.idxOfGameMove++;
    }

    /**
     * Handles the "Previous Move" button click event.
     * Moves the game back to the previous recorded move.
     */
    public void handlePrevMoveBtn() {
        if(this.idxOfGameMove > 0) {
            this.idxOfGameMove--;
            getMoveString();
            if(idxOfGameMove == 0) {
                getbackWhitePieces();
            }else if(idxOfGameMove == 1) {
                getBackBlackPieces();
            }else {
                getBackMoveFromRecord();
            }
            this.gameFrameViewRecord.getGameBoardView().redrawGameBoardView(this.gameBoardModel.getBoard(), null);
        }

    }

    private void getMoveString() {
        ArrayList<String> gameRecords = gameModel.getGameRecords();
        this.moveString = gameRecords.get(this.idxOfGameMove);
    }

    private void setWhitePieces() {
        iniPiecesArray(GameColors.WHITE);
        String[] parts = this.moveString.split(" ");

        for (int i = 0; i < parts.length; i++) {
            if(i == 0) {
                continue;
            }
            PieceModel piece = this.whitePieces.get((i - 1));
            int col = parts[i].charAt(1) - 'a';
            int row = '8' - parts[i].charAt(2);
            int squareId = (row * 8) + col;
            this.gameBoardModel.getSquare(squareId).setPieceOnSquare(piece);
        }
    }

    private void setBlackPieces() {
        iniPiecesArray(GameColors.BLACK);
        String[] parts = this.moveString.split(" ");

        for (int i = 0; i < parts.length; i++) {
            if(i == 0) {
                continue;
            }
            PieceModel piece = this.blackPieces.get((i - 1));
            int col = parts[i].charAt(1) - 'a';
            int row = '8' - parts[i].charAt(2);
            int squareId = (row * 8) + col;
            this.gameBoardModel.getSquare(squareId).setPieceOnSquare(piece);
        }
    }

    private void makeMoveFromRecord() {
        String[] parts = this.moveString.split(" ");

        for (int i = 0; i < parts.length; i++) {
            if(i == 0) {
                continue;
            }
            int col = parts[i].charAt(1) - 'a';
            int row = '8' - parts[i].charAt(2);
            int squareId = (row * 8) + col;
            int direction = getDirectionFromRecord(parts[i].charAt(3));
            PieceModel piece = this.gameBoardModel.getSquare(squareId).getPieceOnSquare();
            this.gameBoardModel.getSquare(squareId).deletePieceOnSquare();
            this.gameBoardModel.getSquare(squareId + direction).setPieceOnSquare(piece);
        }
    }

    private void getbackWhitePieces() {
        for (int i = 48; i < 64; i++) {
            this.gameBoardModel.getSquare(i).deletePieceOnSquare();
        }
    }

    private void getBackBlackPieces() {
        for (int i = 0; i < 16; i++) {
            this.gameBoardModel.getSquare(i).deletePieceOnSquare();
        }
    }

    private void getBackMoveFromRecord() {
        String[] parts = this.moveString.split(" ");
        PieceModel piece = null;

        for (int i = parts.length-1; i > 0; i--) {
            int col = parts[i].charAt(1) - 'a';
            int row = '8' - parts[i].charAt(2);
            int squareId = (row * 8) + col;
            int direction = getDirectionFromRecord(parts[i].charAt(3));
            piece = this.gameBoardModel.getSquare(squareId + direction).getPieceOnSquare();
            this.gameBoardModel.getSquare(squareId + direction).deletePieceOnSquare();
            this.gameBoardModel.getSquare(squareId).setPieceOnSquare(piece);
        }
    }

    protected void iniPiecesArray(GameColors color) {
        PieceModel[] pieces = new PieceModel[16];

        String rabType = (color == GameColors.WHITE) ? "rab_w" : "rab_b";
        String catType = (color == GameColors.WHITE) ? "cat_w" : "cat_b";
        String dogType = (color == GameColors.WHITE) ? "dog_w" : "dog_b";
        String horType = (color == GameColors.WHITE) ? "hor_w" : "hor_b";
        String mcamType = (color == GameColors.WHITE) ? "mcam_w" : "mcam_b";
        String eleType = (color == GameColors.WHITE) ? "ele_w" : "ele_b";

        int index = 0;
        for (int i = 0; i < 8; i++) {
            pieces[index++] = new PieceModel(color, 1, false, 0, rabType,
                    (color == GameColors.WHITE) ?
                            CANDIDATE_MOVE_COORDINATES_RAB_WHITE
                            :
                            CANDIDATE_MOVE_COORDINATES_RAB_BLACK
            );
        }
        for (int i = 0; i < 2; i++) {
            pieces[index++] = new PieceModel(color, 2, false, 0, catType, CANDIDATE_MOVE_COORDINATES_NORMAL);
        }
        for (int i = 0; i < 2; i++) {
            pieces[index++] = new PieceModel(color, 3, false, 0, dogType, CANDIDATE_MOVE_COORDINATES_NORMAL);
        }
        for (int i = 0; i < 2; i++) {
            pieces[index++] = new PieceModel(color, 4, false, 0, horType, CANDIDATE_MOVE_COORDINATES_NORMAL);
        }
        pieces[index++] = new PieceModel(color, 5, false, 0, mcamType, CANDIDATE_MOVE_COORDINATES_NORMAL);
        pieces[index++] = new PieceModel(color, 6, false, 0, eleType, CANDIDATE_MOVE_COORDINATES_NORMAL);

        if(color == GameColors.WHITE) {
            this.whitePieces.addAll(Arrays.asList(pieces));
        }else {
            this.blackPieces.addAll(Arrays.asList(pieces));
        }
    }

    private int getDirectionFromRecord(char direction) {
        int directionInt;
        switch(direction) {
            case 'n':
                directionInt = -8;
                break;
            case 'w':
                directionInt = -1;
                break;
            case 'e':
                directionInt = 1;
                break;
            default:
                directionInt = 8;
                break;
        }
        return directionInt;
    }
}
