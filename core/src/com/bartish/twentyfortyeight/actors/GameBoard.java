package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private boolean bordFull;

    private Image board;
    private Block[][] matrix;

    public GameBoard() {
        board = new Image(new Texture(Gdx.files.internal("game_board.png")));
        addActor(board);

        setWidth(board.getWidth());
        setHeight(board.getHeight());

        matrix = new Block[SIZE][];
        for (int i = 0; i < SIZE; i++) {
            matrix[i] = new Block[SIZE];
        }

        restart();
    }

    public void restart() {
        bordFull = false;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(matrix[i][j] != null) {

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
        if(!bordFull) {
            try {
                checkInput();
            } catch (BoardIsFullException e) {
                bordFull = true;
                //gameover();
            }
        }

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

    private boolean isAnimationEnded() {
        for(Block[] arr: matrix) {
            for(Block cell: arr) {
                if(cell != null && cell.hasActions())
                    return false;
            }
        }

        return true;
    }

    private void checkInput(){
        if(isAnimationEnded()) {
            if(isTouchable()) {

                if(Gdx.input.isKeyJustPressed(Input.Keys.UP)) up();
                else if(Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) down();
                else if(Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) right();
                else if(Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) left();
            }
        }
    }

    public void up() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 1; j < SIZE; j++) {
                if (matrix[i][j] != null) {
                    for (int k = j - 1; k >= 0; k--) {
                        if (matrix[i][k] != null) {
                            try {
                                move(i, j, i, k);
                            } catch (MoveBlockException e) {
                                move(i, j, i, k + 1);
                            }

                            break;
                        } else if (k == 0) {
                            move(i, j, i, k);
                            break;
                        }
                    }
                }
            }
        }
        addRandomBlock();
    }

    public void down() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = SIZE - 2; j >= 0; j--) {
                if (matrix[i][j] != null) {
                    for (int k = j + 1; k < SIZE; k++) {
                        if (matrix[i][k] != null) {
                            try {
                                move(i, j, i, k);
                            } catch (MoveBlockException e) {
                                move(i, j, i, k - 1);
                            }

                            break;
                        } else if (k == SIZE - 1) {
                            move(i, j, i, k);
                            break;
                        }
                    }
                }
            }
        }

        addRandomBlock();
    }

    public void left() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = 1; i < SIZE; i++) {
                if (matrix[i][j] != null) {
                    for (int k = i - 1; k >= 0; k--) {
                        if (matrix[k][j] != null) {
                            try {
                                move(i, j, k, j);
                            } catch (MoveBlockException e) {
                                move(i, j, k + 1, j);
                            }

                            break;
                        } else if (k == 0) {
                            move(i, j, k, j);
                            break;
                        }
                    }
                }
            }
        }
        addRandomBlock();
    }

    public void right() {
        for (int j = 0; j < SIZE; j++) {
            for (int i = SIZE - 2; i >= 0; i--) {
                if (matrix[i][j] != null) {
                    for (int k = i + 1; k < SIZE; k++) {
                        if (matrix[k][j] != null) {
                            try {
                                move(i, j, k, j);
                            } catch (MoveBlockException e) {
                                move(i, j, k - 1, j);
                            }

                            break;
                        } else if (k == SIZE - 1) {
                            move(i, j, k, j);
                            break;
                        }
                    }
                }
            }
        }
        addRandomBlock();
    }

    public void addRandomBlock() {
        int emptyBlocks = 0;
        int num;
        int cell;

        try {

            for(Block[] arr: matrix) {
                for(Block cll: arr) {
                    if(cll == null) emptyBlocks++;
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
        if(matrix[x][y] != null)
            throw new AddBlockException("There are Block already");

        matrix[x][y] = new Block(num);
        addActor(matrix[x][y]);
        matrix[x][y].setPosition(
                (x + 1) * BLOCK_SPACING + x * BLOCK_SIZE,
                getHeight() - (y + 1) * (BLOCK_SPACING + BLOCK_SIZE));

        matrix[x][y].toBack();  board.toBack();
        matrix[x][y].setScale(0);
        matrix[x][y].addAction(
                scaleTo(1, 1, BLOCK_MOVE_TIME)
        );
    }

    void move(int oldX, int oldY, int newX, int newY) {
        if (oldX == newX && oldY == newY) return;

        if (matrix[newX][newY] != null) {

            /// newNum must == oldNum
            if (matrix[newX][newY].getNum() != matrix[oldX][oldY].getNum())
                throw new MoveBlockException("NewNum must be equal to oldNum");
            if (matrix[newX][newY].connected)
                throw new MoveBlockException("Cant connect with connected block");

            matrix[oldX][oldY].connected = true;
            matrix[oldX][oldY].doubleTheNumber();
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

    public boolean isBordFull() {
        return bordFull;
    }

    public int[][] getMatrix() {
        int[][] arr = new int[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if(matrix[i][j] == null) arr[i][j] = -1;
                else arr[i][j] = matrix[i][j].getNum();
            }
        }

        return arr;
    }

    public void setMatrix(int[][] arr) {

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                removeActor(matrix[i][j]);

                if(arr[i][j] == -1) matrix[i][j] = null;
                else {
                    matrix[i][j] = new Block(arr[i][j]);
                    addActor(matrix[i][j]);
                    act(0);

                    matrix[i][j].setScale(0);
                    matrix[i][j].addAction(
                            scaleTo(1, 1, BLOCK_MOVE_TIME)
                    );
                }
            }
        }
    }
}