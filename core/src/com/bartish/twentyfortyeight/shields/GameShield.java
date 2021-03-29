package com.bartish.twentyfortyeight.shields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bartish.twentyfortyeight.actors.GameBoard;
import com.bartish.twentyfortyeight.actors.GameOver;

import static com.bartish.twentyfortyeight.constants.Constants.*;

public class GameShield extends Shield{

    private final GameBoard gameBoard;
    private final GameOver gameOver;

    public GameShield() {
        super();

        gameBoard = new GameBoard();
        addActor(gameBoard);

        gameOver = new GameOver();
        addActor(gameOver);

        resize();
        //restart();
    }

//    @Override
//    protected void restart() {
//        gameBoard.setX((getWidth() - gameBoard.getWidth()) / 2);
//        gameBoard.setY((getHeight() - gameBoard.getHeight()) / 2);
//    }


    @Override
    public void act() {
        super.act();

        if(gameBoard.isBordFull()) {
            gameBoard.setTouchable(Touchable.disabled);
            gameOver.active();
        }
    }

    @Override
    protected void resize() {
        gameBoard.setX((getWidth() - gameBoard.getWidth()) / 2);
        gameBoard.setY((getHeight() - gameBoard.getHeight()) / 2);

        gameOver.setX((getWidth() - gameOver.getWidth()) / 2);
        gameOver.setY((getHeight() - gameOver.getHeight()) / 2);
    }

    @Override
    protected void remove() {
        gameBoard.remove();
    }
}
