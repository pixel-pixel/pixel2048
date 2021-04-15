package com.bartish.twentyfortyeight.exceptions;

import com.bartish.twentyfortyeight.actors.Block;
import com.bartish.twentyfortyeight.actors.GameBoard;

/**
 * An error is thrown if a {@link Block} cannot be moved to a certain position on a {@link GameBoard}
 *
 * @author pixel-pixel
 * @version 1.0
 * @see GameBoard#canMove(int, int, int, int)
 */
public class MoveBlockException extends RuntimeException {
    public MoveBlockException(String s) {
        super(s);
    }
}
