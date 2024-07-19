package cz.cvut.fel.pjv.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameBoardModelTest {

    @Test
    void testInitBoard() {
        //Test init board method
        // Create an instance of the class being tested
        GameBoardModel gameBoardModel = new GameBoardModel();
        //| need to call get board because init board is private class
        List<SquareModel> board = gameBoardModel.getBoard();
        // Perform assertions to verify the expected behavior
        assertNotNull(board); // Ensure the result is not null
        assertEquals(64, board.size()); // Ensure the result has the expected size
    }

    @Test
    void testIfSquareEighteenIsTrapSquare() {
        // Create an instance of the class being tested
        GameBoardModel gameBoardModel = new GameBoardModel();
        boolean isTrapSquare = gameBoardModel.getBoard().get(18).isTrap();
        assertEquals(true, isTrapSquare); // Ensure the result has the expected size
    }

    @Test
    void testIfSquareFourtyFiveIsTrapSquare() {
        // Create an instance of the class being tested
        GameBoardModel gameBoardModel = new GameBoardModel();
        boolean isTrapSquare = gameBoardModel.getBoard().get(45).isTrap();
        assertEquals(true, isTrapSquare); // Ensure the result has the expected size
    }

    @Test
    void testCreateArimaaNotationZeroToA8() {
        GameBoardModel gameBoardModel = new GameBoardModel();
        assertEquals("a8", gameBoardModel.createArimaaNotation(0));
    }

    @Test
    void testCreateArimaaNotationOneToB8() {
        GameBoardModel gameBoardModel = new GameBoardModel();
        assertEquals("b8", gameBoardModel.createArimaaNotation(1));
    }

    @Test
    void testCreateArimaaNotationEightToA7() {
        GameBoardModel gameBoardModel = new GameBoardModel();
        assertEquals("a7", gameBoardModel.createArimaaNotation(8));
    }

    @Test
    void testCreateArimaaNotationSixtyThreeToH1() {
        GameBoardModel gameBoardModel = new GameBoardModel();
        assertEquals("h1", gameBoardModel.createArimaaNotation(63));
    }
}