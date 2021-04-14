package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.bartish.twentyfortyeight.exceptions.AddBlockException;
import com.bartish.twentyfortyeight.exceptions.BoardIsFullException;
import com.bartish.twentyfortyeight.exceptions.MoveBlockException;

import java.util.Random;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.bartish.twentyfortyeight.utils.Constants.*;

public class GameBoard extends Group {
    private final int SIZE = 4;
    private int score;

    private Image board;
    private Block[][] matrix;

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

    private void unconnected() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (matrix[i][j] != null)
                    matrix[i][j].connected = false;
            }
        }
    }

    public boolean isAnimationEnded() {
        for (Block[] arr : matrix) {
            for (Block cell : arr) {
                if (cell != null && cell.hasActions())
                    return false;
            }
        }

        return true;
    }

    private boolean move;

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

    public boolean canMove(int oldX, int oldY, int newX, int newY) throws MoveBlockException {
        if (oldX == newX && oldY == newY)
            throw new MoveBlockException("Cant move to old position");

        if (matrix[newX][newY] != null) {

            /// newNum must == oldNum
            if (matrix[newX][newY].getNum() != matrix[oldX][oldY].getNum())
                throw new MoveBlockException("NewNum must be equal to oldNum");
            if (matrix[newX][newY].connected)
                throw new MoveBlockException("Cant connect with connected block");
        }

        return true;
    }

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

    public void setMatrix(int[][] arr) {
        restart();

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (arr[i][j] != -1)
                    addBlock(i, j, arr[i][j]);
            }
        }
    }

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

    public int getScore() {
        return score;
    }
}