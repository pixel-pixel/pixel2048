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

/**
 * Game screen. Contain {@link GameBoard}, {@link GameOver}, {@link RestartButton} and utils for saving.
 *
 * @author pixel-pixel
 * @version 1.0
 */
public class GameShield extends Shield {
    /**
     * A libGDX class for save primitives and Strings.
     *
     * @see Preferences
     */
    private final Preferences saver;
    /**
     * A libGDX util for serialization Objects to JSON. Need to save <code>GameBoard</code> data.
     *
     * @see Json
     */
    private final Json json;

    /**
     * A Space which contains <code>Block</code>`s.
     */
    private final GameBoard gameBoard;
    /**
     * An wrapper over <code>Image</code> which animate when game is over.
     */
    private final GameOver gameOver;
    /**
     * Start new game when was clicked.
     */
    private final RestartButton restart;

    /**
     * Create class` objects.
     */
    public GameShield() {
        super();
        Gdx.input.setInputProcessor(this);
        saver = Gdx.app.getPreferences(GAME_TITLE);
        json = new Json();

        gameBoard = new GameBoard();
        addActor(gameBoard);

        gameOver = new GameOver();
        addActor(gameOver);

        restart = new RestartButton();
        addActor(restart);


        start();
        resize();
    }

    /**
     * Config class` objects.
     */
    public void start() {
        if (!load()) {
            gameBoard.addRandomBlock();
            gameBoard.addRandomBlock();
        }

        gameBoard.toFront();
        gameBoard.addAction(Actions.sequence(
                Actions.scaleTo(2, 2),
                Actions.alpha(0),
                Actions.parallel(
                        Actions.scaleTo(1, 1, BOARD_START_TIME),
                        Actions.alpha(1, BOARD_START_TIME)
                )
        ));

        restart.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                restart();
            }
        });

        addListener(new InputListener() {
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

                if (gameBoard.isAnimationEnded() && !touch) {
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
    }

    /**
     * Reconfig class` objects.
     */
    public void restart() {
        gameBoard.restart();
        gameBoard.setTouchable(Touchable.enabled);
        gameBoard.addRandomBlock();
        gameBoard.addRandomBlock();

        gameOver.disactive();
    }

    /**
     * Save gave if it is`n over.
     *
     * @return Is game over.
     */
    @Override
    public boolean save() {
        if (!gameBoard.isGameOver()) {
            saver.putBoolean(GAME_IS_SAVE_NAME, true);
            saver.putString(BOARD_SAVE_NAME, json.toJson(gameBoard.getMatrix()));
            saver.putInteger(SCORE_SAVE_NAME, gameBoard.getScore());
            saver.flush();

            return true;
        }
        return false;
    }

    /**
     * Load game if game`s save exist.
     *
     * @return Is save exist.
     */
    @Override
    public boolean load() {
        if (saver.getBoolean(GAME_IS_SAVE_NAME, false)) {
            String jsonText = saver.getString(BOARD_SAVE_NAME);
            gameBoard.setMatrix(json.fromJson(int[][].class, jsonText));
            return true;
        }
        return false;
    }

    /**
     * Call this method in all children Actors. Check if game is over.
     */
    @Override
    public void act() {
        super.act();

        if (gameBoard.isGameOver()) {
            gameBoard.setTouchable(Touchable.disabled);
            gameOver.active();
        }
    }

    /**
     * Center <code>GameBord</code>, <code>GameOver</code> and give to <code>RestartButton</code> right position.
     */
    @Override
    protected void resize() {
        gameBoard.setX((getWidth() - gameBoard.getWidth()) / 2);
        gameBoard.setY((getHeight() - gameBoard.getHeight()) / 2);

        gameOver.setX((getWidth() - gameOver.getWidth()) / 2);
        gameOver.setY((getHeight() - gameOver.getHeight()) / 2);

        restart.setX(gameBoard.getX() + gameBoard.getWidth() - restart.getWidth() - 4);
        restart.setY(gameBoard.getY() - restart.getHeight() - 4);
    }

    /**
     * Call <code>remove()</code> method in <code>GameBoard</code>
     */
    @Override
    public void dispose() {
        super.dispose();
        gameBoard.remove();
    }
}
