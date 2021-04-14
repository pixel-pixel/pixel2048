package com.bartish.twentyfortyeight.utils;

import com.bartish.twentyfortyeight.actors.Block;
import com.bartish.twentyfortyeight.actors.GameBoard;
import com.bartish.twentyfortyeight.actors.GameOver;
import com.bartish.twentyfortyeight.shields.GameShield;
import com.bartish.twentyfortyeight.shields.Shield;

/**
 * A class that stores game constants
 *
 * @author Andrew Bartish (pixel-pixel)
 * @version 1.1
 */
public final class Constants {
    /**
     * The game title and name for save
     *
     * @see GameShield#GameShield()
     */
    public static final String GAME_TITLE = "2048";

    /**
     * Original width of game
     *
     * @see Shield
     */
    public static final int GAME_WIDTH = 216;
    /**
     * Original height of game
     *
     * @see Shield
     */
    public static final int GAME_HEIGHT = 380;


    /**
     * Standard window width on desktop
     */
    public static final int WINDOW_WIDTH = 720;
    /**
     * Standard window height on desktop
     */
    public static final int WINDOW_HEIGHT = 720;


    /**
     * Name to check the save of game
     *
     * @see GameShield#save()
     * @see GameShield#load()
     */
    public static final String GAME_IS_SAVE_NAME = "is save?";
    /**
     * Name to check the save of {@link GameBoard}
     *
     * @see GameShield#save()
     * @see GameShield#load()
     */
    public static final String BOARD_SAVE_NAME = "board";
    /**
     * Name to check the save of score {@link GameBoard#getScore()}
     *
     * @see GameShield#save()
     * @see GameShield#load()
     */
    public static final String SCORE_SAVE_NAME = "score";


    /**
     * Width and height of {@link Block}
     *
     * @see Block#Block(int)
     * @see GameBoard#act(float)
     */
    public static final int BLOCK_SIZE = 45;

    /**
     * Space between {@link Block} on {@link GameBoard}
     *
     * @see GameBoard#act(float)
     */
    public static final int BLOCK_SPACING = 4;


    /**
     * The time it takes for the {@link Block} to move on the {@link GameBoard}
     *
     * @see Block
     * @see GameBoard#move(int, int, int, int)
     */
    public static final float BLOCK_MOVE_TIME = 0.1f;

    /**
     * The time it takes to active and disactive for {@link GameOver}
     *
     * @see GameOver#active()
     * @see GameShield#act()
     */
    public static final float GAME_OVER_TIME = 0.2f;

    /**
     * The time it takes to fall when start the game for {@link GameBoard}
     *
     * @see GameBoard
     * @see GameShield#start()
     */
    public static final float BOARD_START_TIME = 0.5f;
}
