package com.bartish.twentyfortyeight.shields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.bartish.twentyfortyeight.actors.GameBoard;
import com.bartish.twentyfortyeight.actors.GameOver;
import com.bartish.twentyfortyeight.actors.RestartButton;

import static com.bartish.twentyfortyeight.constants.Constants.*;

public class GameShield extends Shield{

    private final GameBoard gameBoard;
    private final GameOver gameOver;
    private final RestartButton restart;

    public GameShield() {
        super();
        Gdx.input.setInputProcessor(this);

        gameBoard = new GameBoard();
        addActor(gameBoard);

        gameOver = new GameOver();
        addActor(gameOver);

        restart = new RestartButton();
        restart.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameBoard.restart();
                gameBoard.setTouchable(Touchable.enabled);

                gameOver.disactive();
            }
        });
        addActor(restart);

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

        restart.setX(gameBoard.getX() + gameBoard.getWidth() - restart.getWidth() - 4);
        restart.setY(gameBoard.getY() - restart.getHeight() - 4);
    }

    @Override
    protected void remove() {
        gameBoard.remove();
    }
}
