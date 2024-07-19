package cz.cvut.fel.pjv;

import cz.cvut.fel.pjv.controllers.NewGameController;
import cz.cvut.fel.pjv.model.GameModel;

public class Main {

    public static void main(String[] args) {
        GameModel gameModel = new GameModel();
        NewGameController newGameWindowController = new NewGameController(gameModel);
    }
}