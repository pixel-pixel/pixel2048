package com.bartish.twentyfortyeight.shields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.bartish.twentyfortyeight.Main;
import com.bartish.twentyfortyeight.actors.GameBoard;
import com.bartish.twentyfortyeight.actors.GameOver;
import com.bartish.twentyfortyeight.actors.RestartButton;

import static com.bartish.twentyfortyeight.utils.Constants.*;

public class GameShield extends Shield{
    //private final JsonSaver saver;
    private final Preferences saver;
    private final Json json;

    private final GameBoard gameBoard;
    private final GameOver gameOver;
    private final RestartButton restart;

    public GameShield() {
        super();
        Gdx.input.setInputProcessor(this);
        //saver = new JsonSaver();
        saver = Gdx.app.getPreferences(Main.NAME);
        json = new Json();

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
                gameBoard.addRandomBlock();
                gameBoard.addRandomBlock();

                gameOver.disactive();
            }
        });
        addActor(restart);

        if(!load()) {
            gameBoard.addRandomBlock();
            gameBoard.addRandomBlock();
        }
        resize();
        //restart();
    }

    @Override
    public boolean save() {
        saver.putBoolean(GAME_IS_SAVE_NAME, true);
        saver.putString(BOARD_SAVE_NAME, json.toJson(gameBoard.getMatrix()));
        saver.flush();

        return true;
    }

    @Override
    public boolean load() {
        if(saver.getBoolean(GAME_IS_SAVE_NAME, false)) {
            String jsonText = saver.getString(BOARD_SAVE_NAME);
            gameBoard.setMatrix(json.fromJson(int[][].class, jsonText));

            System.out.println(json.fromJson(int[][].class, jsonText).toString());

            return true;
        }
        return false;
    }

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
