package com.bartish.twentyfortyeight.exceptions;

import com.bartish.twentyfortyeight.actors.Block;
import com.bartish.twentyfortyeight.actors.GameBoard;

/**
 * An error is thrown if a {@link Block} cannot be placed cause {@link GameBoard} is full
 *
 * @author pixel-pixel
 * @version 1.0
 * @see GameBoard#addRandomBlock()
 */
public class BoardIsFullException extends RuntimeException {
    public BoardIsFullException(String s) {
        super(s);
    }
}
