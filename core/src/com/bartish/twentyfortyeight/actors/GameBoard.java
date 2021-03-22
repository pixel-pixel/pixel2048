package com.bartish.twentyfortyeight.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class GameBoard extends Group {
    private int[][] matrix;
    private Image board;
    private Image[] blocks;

    public GameBoard() {
        matrix = new int[4][4];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                matrix[i][j] = 0;
            }
        }

        board = new Image(new Texture(Gdx.files.internal("game_board.png")));
        addActor(board);

        setWidth(board.getWidth());
        setHeight(board.getHeight());

        blocks = new Image[16];
        for(int i = 0; i < blocks.length; i++) {
            blocks[i] = new Image(new Texture(Gdx.files.internal("block.png")));
            blocks[i].setPosition(
                    4 + (3 - i % 4) * (blocks[0].getWidth() + 4),
                    4 + (3 - i / 4) * (blocks[0].getHeight() + 4));
            addActor(blocks[i]);
        }


    }

    @Override
    public void act(float delta) {
        super.act(delta);

        board.setBounds(0, 0, getWidth(), getHeight());
    }
}
