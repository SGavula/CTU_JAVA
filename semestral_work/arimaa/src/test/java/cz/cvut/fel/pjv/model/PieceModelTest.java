package cz.cvut.fel.pjv.model;

import cz.cvut.fel.pjv.utils.GameColors;
import org.junit.jupiter.api.Test;

import java.util.List;

import static cz.cvut.fel.pjv.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class PieceModelTest {
    @Test
    void testCalculateValidMovesIfWhitePieceRabInMiddle() {
        // Create an instance of the class being tested
        PieceModel piece = new PieceModel(GameColors.WHITE, 1, false, 54, "Rab", CANDIDATE_MOVE_COORDINATES_RAB_WHITE);
        // Prepare the necessary data for the test
        GameBoardModel gameBoardModel = new GameBoardModel(); // Create a game board model
        int numberOfMoves = 1; // Set the number of moves
        // Call the method and get the result
        List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, numberOfMoves);
        // Perform assertions to verify the expected behavior
        assertNotNull(validMoves);
        assertEquals(3, validMoves.size());
    }

    @Test
    void testCalculateValidMovesIfWhitePieceRabIsInEndOfBoard() {
        // Create an instance of the class being tested
        PieceModel piece = new PieceModel(GameColors.WHITE, 1, false, 5, "Rab", CANDIDATE_MOVE_COORDINATES_RAB_WHITE);
        // Prepare the necessary data for the test
        GameBoardModel gameBoardModel = new GameBoardModel(); // Create a game board model
        int numberOfMoves = 1; // Set the number of moves
        // Call the method and get the result
        List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, numberOfMoves);
        // Perform assertions to verify the expected behavior
        assertNotNull(validMoves);
        assertEquals(2, validMoves.size());
    }

    @Test
    void testCalculateValidMovesIfWhitePieceRabIsInTopCorner() {
        // Create an instance of the class being tested
        PieceModel piece = new PieceModel(GameColors.WHITE, 1, false, 7, "Rab", CANDIDATE_MOVE_COORDINATES_RAB_WHITE);
        // Prepare the necessary data for the test
        GameBoardModel gameBoardModel = new GameBoardModel(); // Create a game board model
        int numberOfMoves = 1; // Set the number of moves
        // Call the method and get the result
        List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, numberOfMoves);
        // Perform assertions to verify the expected behavior
        assertNotNull(validMoves);
        assertEquals(1, validMoves.size());
    }

    @Test
    void testCalculateValidMovesIfWhitePieceRabIsInBottomCorner() {
        // Create an instance of the class being tested
        PieceModel piece = new PieceModel(GameColors.WHITE, 1, false, 63, "Rab", CANDIDATE_MOVE_COORDINATES_RAB_WHITE);
        // Prepare the necessary data for the test
        GameBoardModel gameBoardModel = new GameBoardModel(); // Create a game board model
        int numberOfMoves = 1; // Set the number of moves
        // Call the method and get the result
        List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, numberOfMoves);
        // Perform assertions to verify the expected behavior
        assertNotNull(validMoves);
        assertEquals(2, validMoves.size());
    }

    @Test
    void testCalculateValidMovesIfBlackPieceRabIsInBottomCorner() {
        // Create an instance of the class being tested
        PieceModel piece = new PieceModel(GameColors.BLACK, 1, false, 63, "Rab", CANDIDATE_MOVE_COORDINATES_RAB_BLACK);
        // Prepare the necessary data for the test
        GameBoardModel gameBoardModel = new GameBoardModel(); // Create a game board model
        int numberOfMoves = 1; // Set the number of moves
        // Call the method and get the result
        List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, numberOfMoves);
        // Perform assertions to verify the expected behavior
        assertNotNull(validMoves);
        assertEquals(1, validMoves.size());
    }

    @Test
    void testCalculateValidMovesIfBlackPieceRabIsInTopCorner() {
        // Create an instance of the class being tested
        PieceModel piece = new PieceModel(GameColors.BLACK, 1, false, 7, "Rab", CANDIDATE_MOVE_COORDINATES_RAB_BLACK);
        // Prepare the necessary data for the test
        GameBoardModel gameBoardModel = new GameBoardModel(); // Create a game board model
        int numberOfMoves = 1; // Set the number of moves
        // Call the method and get the result
        List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, numberOfMoves);
        // Perform assertions to verify the expected behavior
        assertNotNull(validMoves);
        assertEquals(2, validMoves.size());
    }

    @Test
    void testCalculateValidMovesIfBlackPieceRabIsInMiddle() {
        // Create an instance of the class being tested
        PieceModel piece = new PieceModel(GameColors.BLACK, 1, false, 5, "Rab", CANDIDATE_MOVE_COORDINATES_RAB_BLACK);
        // Prepare the necessary data for the test
        GameBoardModel gameBoardModel = new GameBoardModel(); // Create a game board model
        int numberOfMoves = 1; // Set the number of moves
        // Call the method and get the result
        List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, numberOfMoves);
        // Perform assertions to verify the expected behavior
        assertNotNull(validMoves);
        assertEquals(3, validMoves.size());
    }

    @Test
    void testCalculateValidMovesIfBlackPieceNormalIsInMiddle() {
        // Create an instance of the class being tested
        PieceModel piece = new PieceModel(GameColors.BLACK, 2, false, 52, "Cat", CANDIDATE_MOVE_COORDINATES_NORMAL);
        // Prepare the necessary data for the test
        GameBoardModel gameBoardModel = new GameBoardModel(); // Create a game board model
        int numberOfMoves = 1; // Set the number of moves
        // Call the method and get the result
        List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, numberOfMoves);
        // Perform assertions to verify the expected behavior
        assertNotNull(validMoves);
        assertEquals(4, validMoves.size());
    }
    @Test
    void testCalculateValidMovesIfWhitePieceNormalIsInMiddle() {
        // Create an instance of the class being tested
        PieceModel piece = new PieceModel(GameColors.WHITE, 2, false, 52, "Cat", CANDIDATE_MOVE_COORDINATES_NORMAL);
        // Prepare the necessary data for the test
        GameBoardModel gameBoardModel = new GameBoardModel(); // Create a game board model
        int numberOfMoves = 1; // Set the number of moves
        // Call the method and get the result
        List<MoveModel> validMoves = piece.calculateValidMoves(gameBoardModel, numberOfMoves);
        // Perform assertions to verify the expected behavior
        assertNotNull(validMoves);
        assertEquals(4, validMoves.size());
    }

}