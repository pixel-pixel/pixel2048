package com.bartish.twentyfortyeight.exceptions;

import com.bartish.twentyfortyeight.actors.Block;
import com.bartish.twentyfortyeight.actors.GameBoard;

/**
 * An error is thrown if a {@link Block} cannot be placed in a certain position on a {@link GameBoard}
 *
 * @author pixel-pixel
 * @version 1.0
 * @see GameBoard#addBlock(int, int, int)
 */
public class AddBlockException extends RuntimeException {
    public AddBlockException(String s) {
        super(s);
    }
}
