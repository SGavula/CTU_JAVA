package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.utils.GameColors;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cz.cvut.fel.pjv.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class PullMoveModelTest {

    @Test
    void testOneValidPullMoveWhiteCatAndBlackRabbit() {
        GameModel gameModel = new GameModel();
        //Init game board to test this test case
        PieceModel whiteCat = new PieceModel(GameColors.WHITE, 2, false,28, "cat_w", CANDIDATE_MOVE_COORDINATES_NORMAL);
        PieceModel blackRabbit = new PieceModel(GameColors.BLACK, 1, false,27, "rab_b", CANDIDATE_MOVE_COORDINATES_RAB_BLACK);
        gameModel.getGameBoardModel().getSquare(28).setPieceOnSquare(whiteCat);
        gameModel.getGameBoardModel().getSquare(27).setPieceOnSquare(blackRabbit);
        PullMoveModel pullMove = new PullMoveModel(28, 29);
        List<MoveModel> validPullMoves = pullMove.calculatePullMoves(gameModel.getGameBoardModel(), GameColors.WHITE);
        // Perform assertions to verify the expected behavior
        assertNotNull(validPullMoves);
        assertEquals(1, validPullMoves.size());
    }

    @Test
    void testNoValidPullMovesWhiteRabbitAndBlackRabbit() {
        GameModel gameModel = new GameModel();
        //Init game board to test this test case
        PieceModel whiteRabbit = new PieceModel(GameColors.WHITE, 1, false,28, "rab_w", CANDIDATE_MOVE_COORDINATES_RAB_WHITE);
        PieceModel blackRabbit = new PieceModel(GameColors.BLACK, 1, false,27, "rab_b", CANDIDATE_MOVE_COORDINATES_RAB_BLACK);
        gameModel.getGameBoardModel().getSquare(28).setPieceOnSquare(whiteRabbit);
        gameModel.getGameBoardModel().getSquare(27).setPieceOnSquare(blackRabbit);
        PullMoveModel pullMove = new PullMoveModel(28, 29);
        List<MoveModel> validPullMoves = pullMove.calculatePullMoves(gameModel.getGameBoardModel(), GameColors.WHITE);
        // Perform assertions to verify the expected behavior
        assertEquals(0, validPullMoves.size());
    }

    @Test
    void testOneValidPullMovesWhiteRabbitAndBlackCat() {
        GameModel gameModel = new GameModel();
        //Init game board to test this test case
        PieceModel whiteRabbit = new PieceModel(GameColors.WHITE, 1, false,28, "rab_w", CANDIDATE_MOVE_COORDINATES_RAB_WHITE);
        PieceModel blackCat = new PieceModel(GameColors.BLACK, 2, false,27, "cat_b", CANDIDATE_MOVE_COORDINATES_NORMAL);
        gameModel.getGameBoardModel().getSquare(28).setPieceOnSquare(whiteRabbit);
        gameModel.getGameBoardModel().getSquare(27).setPieceOnSquare(blackCat);
        PullMoveModel pullMove = new PullMoveModel(27, 26);
        List<MoveModel> validPullMoves = pullMove.calculatePullMoves(gameModel.getGameBoardModel(), GameColors.BLACK);
        // Perform assertions to verify the expected behavior
        assertNotNull(validPullMoves);
        assertEquals(1, validPullMoves.size());
    }

}