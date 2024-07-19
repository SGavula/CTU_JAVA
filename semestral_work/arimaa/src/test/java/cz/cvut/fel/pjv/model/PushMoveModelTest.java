package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.utils.GameColors;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cz.cvut.fel.pjv.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PushMoveModelTest {

    @Test
    void testThreeValidPushMovesWhiteCatAndBlackRabbit() {
        GameModel gameModel = new GameModel();
        //Init game board to test this test case
        PieceModel whiteCat = new PieceModel(GameColors.WHITE, 2, false,28, "cat_w", CANDIDATE_MOVE_COORDINATES_NORMAL);
        PieceModel blackRabbit = new PieceModel(GameColors.BLACK, 1, false,27, "rab_b", CANDIDATE_MOVE_COORDINATES_RAB_BLACK);
        gameModel.getGameBoardModel().getSquare(28).setPieceOnSquare(whiteCat);
        gameModel.getGameBoardModel().getSquare(27).setPieceOnSquare(blackRabbit);
        PushMoveModel pushMove = new PushMoveModel(28, 27);
        List<MoveModel> validPushMoves = pushMove.calculatePushMoves(gameModel.getGameBoardModel());
        // Perform assertions to verify the expected behavior
        assertNotNull(validPushMoves);
        assertEquals(3, validPushMoves.size());
    }

    @Test
    void testThreeValidPushMovesWhiteRabbitAndBlackCat() {
        GameModel gameModel = new GameModel();
        //Init game board to test this test case
        PieceModel whiteRabbit = new PieceModel(GameColors.WHITE, 1, false,28, "rab_w", CANDIDATE_MOVE_COORDINATES_RAB_WHITE);
        PieceModel blackCat = new PieceModel(GameColors.BLACK, 2, false,27, "cat_b", CANDIDATE_MOVE_COORDINATES_NORMAL);
        gameModel.getGameBoardModel().getSquare(28).setPieceOnSquare(whiteRabbit);
        gameModel.getGameBoardModel().getSquare(27).setPieceOnSquare(blackCat);
        PushMoveModel pushMove = new PushMoveModel(27, 28);
        List<MoveModel> validPullMoves = pushMove.calculatePushMoves(gameModel.getGameBoardModel());
        // Perform assertions to verify the expected behavior
        assertNotNull(validPullMoves);
        assertEquals(3, validPullMoves.size());
    }

}