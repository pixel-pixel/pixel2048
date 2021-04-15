package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bartish.twentyfortyeight.exceptions.AddBlockException;
import com.bartish.twentyfortyeight.exceptions.BoardIsFullException;
import com.bartish.twentyfortyeight.exceptions.MoveBlockException;
import com.bartish.twentyfortyeight.utils.Tones;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.bartish.twentyfortyeight.utils.Constants.*;

/**
 * Contain <code>Block</code>s. Can move, add or random add this or new <code>Block</code>s.
 *
 * @author pixel-pixel
 * @version 1.0
 */
public class GameBoard extends Group {
    /**
     * Width and height <b>in <code>Block</code>s</b> of {@link #matrix}.
     */
    private final int SIZE = 4;
    /**
     * Game score on this <code>GameBoard</code>.
     */
    private int score;

    /**
     * Background image for <code>GameBoard</code>.
     */
    private Image board;
    /**
     * Array that contains all <code>Block</code>s on this <code>GameBoard</code>
     */
    private Block[][] matrix;

    /**
     * Create <code>GameBoard</code>`s objects and config ny {@link #restart()}.
     */
    public GameBoard() {
        board = new Image(new Texture(Gdx.files.internal("game_board.png")));
        addActor(board);

        setWidth(board.getWidth());
        setHeight(board.getHeight());
        setOrigin(getWidth() / 2, getHeight() / 2);

        matrix = new Block[SIZE][];
        for (int i = 0; i < SIZE; i++) {
            matrix[i] = new Block[SIZE];
        }

        restart();
    }

    /**
     * Config <code>GameBoard</code>`s objects on start and restart.
     */
    public void restart() {
        score = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] != null) {

                    matrix[i][j].addAction(sequence(
                            scaleTo(0, 0, BLOCK_MOVE_TIME),
                            Actions.removeActor(matrix[i][j])
                    ));
                    matrix[i][j] = null;
                }
            }
        }
    }

    /**
     * Put {@link #board} and all <code>Block</code>s in right position by {@link #matrix}.
     *
     * @param delta Not use.
     */
    @Override
    public void act(float delta) {
        super.act(delta);

        board.setBounds(0, 0, getWidth(), getHeight());
        unconnected();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] != null && !matrix[i][j].hasActions()) {
                    matrix[i][j].setPosition(
                            (i + 1) * BLOCK_SPACING + i * BLOCK_SIZE,
                            getHeight() - (j + 1) * (BLOCK_SPACING + BLOCK_SIZE));
                }
            }
        }
    }

    /**
     * Need for right work with moving <code>Block</code>s.
     * Do not use!
     */
    private void unconnected() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] != null)
                    matrix[i][j].connected = false;
            }
        }
    }

    /**
     * Check if animation of all <code>Block</code>s in {@link #matrix} is finished.
     * Check it {@link #up()}, {@link #down()}, {@link #left()}, {@link #right()} before methods!
     *
     * @return Is animation ended.
     */
    public boolean isAnimationEnded() {
        for (Block[] arr : matrix) {
            for (Block cell : arr) {
                if (cell != null && cell.hasActions())
                    return false;
            }
        }

        return true;
    }

    /**
     * Need for {@link #up()}, {@link #down()}, {@link #left()}, {@link #right()}.
     * Do not use.
     */
    private boolean move;

    /**
     * Move or marge all <code>Block</code>s to up.
     * If can it - add new random <code>Block</code>.
     *
     * @see #move(int, int, int, int)
     * @see #addRandomBlock()
     */
    public void up() {
        move = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                if (matrix[i][j] != null) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (matrix[i][k] != null) {
                            try {
                                move(i, j, i, k);
                                move = true;
                            } catch (MoveBlockException e) {
                                if (j != k + 1) {
                                    move(i, j, i, k + 1);
                                    move = true;
                                }
                            }

                            break;
                        } else if (k == 0) {
                            move(i, j, i, k);

                            move = true;
                            break;
                        }
                    }
                }
            }
        }

        if (move) addRandomBlock();
    }

    /**
     * Move or marge all <code>Block</code>s to down.
     * If can it - add new random <code>Block</code>.
     *
     * @see #move(int, int, int, int)
     * @see #addRandomBlock()
     */
    public void down() {
        move = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 2; j >= 0; j--) {
                if (matrix[i][j] != null) {
                    for (int k = j + 1; k < SIZE; k++) {
                        if (matrix[i][k] != null) {
                            try {
                                move(i, j, i, k);
                                move = true;
                            } catch (MoveBlockException e) {
                                if (j != k - 1) {
                                    move(i, j, i, k - 1);
                                    move = true;
                                }
                            }

                            break;
                        } else if (k == SIZE - 1) {
                            move(i, j, i, k);

                            move = true;
                            break;
                        }
                    }
                }
            }
        }

        if (move) addRandomBlock();
    }

    /**
     * Move or marge all <code>Block</code>s to left.
     * If can it - add new random <code>Block</code>.
     *
     * @see #move(int, int, int, int)
     * @see #addRandomBlock()
     */
    public void left() {
        move = false;

        for (int j = 0; j < SIZE; j++) {
            for (int i = 1; i < SIZE; i++) {
                if (matrix[i][j] != null) {
                    for (int k = i - 1; k >= 0; k--) {
                        if (matrix[k][j] != null) {
                            try {
                                move(i, j, k, j);
                                move = true;
                            } catch (MoveBlockException e) {
                                if (i != k + 1) {
                                    move(i, j, k + 1, j);
                                    move = true;
                                }
                            }

                            break;
                        } else if (k == 0) {
                            move(i, j, k, j);

                            move = true;
                            break;
                        }
                    }
                }
            }
        }

        if (move) addRandomBlock();
    }

    /**
     * Move or marge all <code>Block</code>s to right.
     * If can it - add new random <code>Block</code>.
     *
     * @see #move(int, int, int, int)
     * @see #addRandomBlock()
     */
    public void right() {
        move = false;

        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE - 2; i >= 0; i--) {
                if (matrix[i][j] != null) {
                    for (int k = i + 1; k < SIZE; k++) {
                        if (matrix[k][j] != null) {
                            try {
                                move(i, j, k, j);
                                move = true;
                            } catch (MoveBlockException e) {
                                if (i != k - 1) {
                                    move(i, j, k - 1, j);
                                    move = true;
                                }
                            }

                            break;
                        } else if (k == SIZE - 1) {
                            move(i, j, k, j);

                            move = true;
                            break;
                        }
                    }
                }
            }
        }

        if (move) addRandomBlock();
    }

    /**
     * Randomly selects a free position in {@link #matrix} and adds where a block with Num 0 or 1.
     * <b>Num is position in <code>Tones</code> array. Real number will be 2 or 4</b>
     *
     * @see #addBlock(int, int, int)
     * @see Tones#get(int)
     */
    public void addRandomBlock() {
        int emptyBlocks = 0;
        int num;
        int cell;

        try {

            for (Block[] arr : matrix) {
                for (Block cll : arr) {
                    if (cll == null) emptyBlocks++;
                }
            }
            num = new Random().nextInt(2);
            cell = new Random().nextInt(emptyBlocks);
        } catch (Exception e) {
            throw new BoardIsFullException("Board is Full");
        }

        int x;
        int y;
        for (int i = 0; i < SIZE * SIZE; i++) {
            x = i % SIZE;
            y = i / SIZE;

            if (matrix[x][y] == null) {
                if (cell == 0) {
                    addBlock(x, y, num);
                }
                cell--;
            }
        }
    }

    /**
     * Add new <code>Block</code> to {@link #matrix} if position == null else throw <code>AddBlockException</code>
     *
     * @param x   X position of new <code>Block</code> in {@link #matrix}
     * @param y   Y position of new <code>Block</code> in {@link #matrix}
     * @param num Num of new <code>Block</code> in {@link #matrix}
     * @see AddBlockException
     */
    public void addBlock(int x, int y, int num) {
        if (matrix[x][y] != null)
            throw new AddBlockException("There are Block already");

        matrix[x][y] = new Block(num);
        addActor(matrix[x][y]);
        matrix[x][y].setPosition(
                (x + 1) * BLOCK_SPACING + x * BLOCK_SIZE,
                getHeight() - (y + 1) * (BLOCK_SPACING + BLOCK_SIZE));

        matrix[x][y].toBack();
        board.toBack();
        matrix[x][y].setScale(0);
        matrix[x][y].addAction(
                scaleTo(1, 1, BLOCK_MOVE_TIME)
        );

        score += Math.pow(2, ++num);
    }

    /**
     * Check if can move <code>Block</code> from old position to new in {@link #matrix}
     * or throw <code>MoveBlockException</code>.
     *
     * @param oldX X position of <code>Block</code>
     * @param oldY Y position of <code>Block</code>
     * @param newX New X position of <code>Block</code>
     * @param newY New Y position of <code>Block</code>
     * @throws MoveBlockException If block cant be moved to new position.
     */
    public void canMove(int oldX, int oldY, int newX, int newY) throws MoveBlockException {
        if (oldX == newX && oldY == newY)
            throw new MoveBlockException("Cant move to old position");

        if (matrix[newX][newY] != null) {

            /// newNum must == oldNum
            if (matrix[newX][newY].getNum() != matrix[oldX][oldY].getNum())
                throw new MoveBlockException("NewNum must be equal to oldNum");
            if (matrix[newX][newY].connected)
                throw new MoveBlockException("Cant connect with connected block");
        }
    }

    /**
     * Move and animate <code>Block</code> from old position to new in {@link #matrix}.
     *
     * @param oldX X position of <code>Block</code>
     * @param oldY Y position of <code>Block</code>
     * @param newX New X position of <code>Block</code>
     * @param newY New Y position of <code>Block</code>
     */
    public void move(int oldX, int oldY, int newX, int newY) {
        canMove(oldX, oldY, newX, newY);

        if (matrix[newX][newY] != null) {
            matrix[oldX][oldY].connected = true;
            matrix[oldX][oldY].addOneToNum();
            matrix[newX][newY].addAction(sequence(
                    Actions.scaleTo(0.8f, 0.8f, BLOCK_MOVE_TIME),
                    Actions.removeActor(matrix[newX][newY])
            ));
        }

        matrix[newX][newY] = matrix[oldX][oldY];
        matrix[oldX][oldY] = null;

        matrix[newX][newY].toFront();
        matrix[newX][newY].addAction(Actions.moveBy(
                (newX - oldX) * (BLOCK_SPACING + BLOCK_SIZE),
                (oldY - newY) * (BLOCK_SPACING + BLOCK_SIZE),
                BLOCK_MOVE_TIME
        ));
    }


    /**
     * Create new <code>int[][]</code> which contains Num of <code>Block</code> in {@link #matrix} by position.
     * Need to save the game.
     *
     * @return Int matrix which contains <code>Block</code>`s Num or -1.
     * @see Block#getNum()
     */
    public int[][] getMatrix() {
        int[][] arr = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] == null) arr[i][j] = -1;
                else arr[i][j] = matrix[i][j].getNum();
            }
        }

        return arr;
    }

    /**
     * Clean the {@link #matrix} and add <code>Block</code>s by its number in {@param arr}.
     * Need for load the game.
     *
     * @param arr Int matrix which contains <code>Block</code>`s Num or -1.
     * @see Block#getNum()
     */
    public void setMatrix(int[][] arr) {
        restart();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (arr[i][j] != -1)
                    addBlock(i, j, arr[i][j]);
            }
        }
    }

    /**
     * Check if you can move or add some <code>Block</code> to this <code>GameBoard</code>
     *
     * @return Is <code>GameBoard</code> full and <code>Block</code>s cant move.
     */
    public boolean isGameOver() {
        for (Block[] arr : matrix) {
            for (Block cell : arr) {
                if (cell == null) return false;
            }
        }

        for (int i = 1; i < matrix.length - 1; i++) {
            for (int j = 1; j < matrix[i].length - 1; j++) {
                if (matrix[i][j].getNum() == matrix[i - 1][j].getNum() ||
                        matrix[i][j].getNum() == matrix[i][j - 1].getNum() ||
                        matrix[i][j].getNum() == matrix[i + 1][j].getNum() ||
                        matrix[i][j].getNum() == matrix[i][j + 1].getNum()
                ) return false;
            }
        }

        return matrix[0][0].getNum() != matrix[1][0].getNum() &&
                matrix[1][0].getNum() != matrix[2][0].getNum() &&
                matrix[2][0].getNum() != matrix[3][0].getNum() &&
                matrix[3][0].getNum() != matrix[3][1].getNum() &&
                matrix[3][1].getNum() != matrix[3][2].getNum() &&
                matrix[3][2].getNum() != matrix[3][3].getNum() &&
                matrix[3][3].getNum() != matrix[2][3].getNum() &&
                matrix[2][3].getNum() != matrix[1][3].getNum() &&
                matrix[1][3].getNum() != matrix[0][3].getNum() &&
                matrix[0][3].getNum() != matrix[0][2].getNum() &&
                matrix[0][2].getNum() != matrix[0][1].getNum() &&
                matrix[0][1].getNum() != matrix[0][0].getNum();
    }

    /**
     * Getter for {@link #score}
     *
     * @return Score of the game in this <code>GameBoard</code>
     */
    public int getScore() {
        return score;
    }
}