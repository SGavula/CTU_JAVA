package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.utils.GameColors;
import org.junit.jupiter.api.Test;

import static cz.cvut.fel.pjv.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    @Test
    void testGameIsFinishWhenWhiteRabbitIsOnBlackSide() {
        GameModel gameModel = new GameModel();
        //Init game board to test this test case
        PieceModel whiteRabbit = new PieceModel(GameColors.WHITE, 1, false,5, "rab_w", CANDIDATE_MOVE_COORDINATES_RAB_WHITE);
        gameModel.getGameBoardModel().getSquare(5).setPieceOnSquare(whiteRabbit);
        boolean gameFinished = gameModel.checkRabbit(gameModel.getGameBoardModel(), 0, 8, "rab_w");
        //Test
        assertEquals(true, gameFinished);
    }

    @Test
    void testGameIsFinishWhenBlackRabbitIsOnWhiteSide() {
        GameModel gameModel = new GameModel();
        //Init game board to test this test case
        PieceModel blackRabbit = new PieceModel(GameColors.WHITE, 1, false,5, "rab_b", CANDIDATE_MOVE_COORDINATES_RAB_BLACK);
        gameModel.getGameBoardModel().getSquare(56).setPieceOnSquare(blackRabbit);
        boolean gameFinished = gameModel.checkRabbit(gameModel.getGameBoardModel(), 56, 63, "rab_b");
        //Test
        assertEquals(true, gameFinished);
    }

    @Test
    void testGameIsNotFinishedWhenThereIsNoPieceOnWhiteSide() {
        GameModel gameModel = new GameModel();
        boolean gameFinished = gameModel.checkRabbit(gameModel.getGameBoardModel(), 56, 63, "rab_b");
        //Test
        assertEquals(false, gameFinished);
    }
    @Test
    void testGameIsNotFinishedWhenThereIsNoPieceOnBlackSide() {
        GameModel gameModel = new GameModel();
        boolean gameFinished = gameModel.checkRabbit(gameModel.getGameBoardModel(), 0, 8, "rab_w");
        //Test
        assertEquals(false, gameFinished);
    }

}