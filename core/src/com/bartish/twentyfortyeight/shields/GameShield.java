package com.bartish.twentyfortyeight.shields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.bartish.twentyfortyeight.actors.GameBoard;

import static com.bartish.twentyfortyeight.constants.Constants.*;

public class GameShield extends Shield{

    private final GameBoard gameBoard;

    public GameShield() {
        super();

        gameBoard = new GameBoard();
        addActor(gameBoard);
        resize();

        //restart();
    }

//    @Override
//    protected void restart() {
//        gameBoard.setX((getWidth() - gameBoard.getWidth()) / 2);
//        gameBoard.setY((getHeight() - gameBoard.getHeight()) / 2);
//    }

    @Override
    protected void resize() {
        gameBoard.setX((getWidth() - gameBoard.getWidth()) / 2);
        gameBoard.setY((getHeight() - gameBoard.getHeight()) / 2);
    }

    @Override
    protected void remove() {
        gameBoard.remove();
    }
}
