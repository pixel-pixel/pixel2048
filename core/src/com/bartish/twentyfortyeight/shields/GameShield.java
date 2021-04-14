package com.bartish.twentyfortyeight.shields;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.bartish.twentyfortyeight.actors.GameBoard;
import com.bartish.twentyfortyeight.actors.GameOver;
import com.bartish.twentyfortyeight.actors.RestartButton;

import static com.bartish.twentyfortyeight.utils.Constants.*;
import static java.lang.Math.abs;

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
        saver = Gdx.app.getPreferences(GAME_TITLE);
        json = new Json();

        gameBoard = new GameBoard();
        gameBoard.toFront();
        gameBoard.addAction(Actions.sequence(
                Actions.scaleTo(2, 2),
                Actions.alpha(0),
                Actions.parallel(
                        Actions.scaleTo(1, 1, BOARD_START_TIME),
                        Actions.alpha(1, BOARD_START_TIME)
                )
        ));
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

        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (gameBoard.isAnimationEnded()) {
                    switch (keycode) {
                        case Input.Keys.UP:
                            gameBoard.up();
                            break;
                        case Input.Keys.RIGHT:
                            gameBoard.right();
                            break;
                        case Input.Keys.DOWN:
                            gameBoard.down();
                            break;
                        case Input.Keys.LEFT:
                            gameBoard.left();
                            break;
                    }
                }
                return true;
            }
        });

        addListener(new ActorGestureListener() {
            boolean touch = false;

            @Override
            public void pan(InputEvent event, float x, float y, float deltaX, float deltaY) {
                //super.pan(event, x, y, deltaX, deltaY);

                if(gameBoard.isAnimationEnded() && !touch) {
                    if (deltaX > 5 && abs(deltaX) > abs(deltaY * 2)) {
                        gameBoard.right();
                        touch = true;
                    } else if (deltaX < -5 && abs(deltaX) > abs(deltaY * 2)) {
                        gameBoard.left();
                        touch = true;
                    } else if (deltaY > 5 && abs(deltaY) > abs(deltaX * 2)) {
                        gameBoard.up();
                        touch = true;
                    } else if (deltaY < -5 && abs(deltaY) > abs(deltaX * 2)) {
                        gameBoard.down();
                        touch = true;
                    }
                }
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);

                touch = false;
            }
        });

        resize();
        //restart();
    }

    public void start() {

    }

    public void restart() {

    }

    @Override
    public boolean save() {
        if(!gameBoard.isGameOver()) {
            saver.putBoolean(GAME_IS_SAVE_NAME, true);
            saver.putString(BOARD_SAVE_NAME, json.toJson(gameBoard.getMatrix()));
            saver.putInteger(SCORE_SAVE_NAME, gameBoard.getScore());
            saver.flush();

            return true;
        }
        return false;
    }

    @Override
    public boolean load() {
        if(saver.getBoolean(GAME_IS_SAVE_NAME, false)) {
            String jsonText = saver.getString(BOARD_SAVE_NAME);
            gameBoard.setMatrix(json.fromJson(int[][].class, jsonText));
            return true;
        }
        return false;
    }

    @Override
    public void act() {
        super.act();

        if(gameBoard.isGameOver()) {
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
