package com.weobot.tictactoe.gamerule.bean;

/**
 * Created by HuangWeijing on 8/30/2015.
 */
public class BoardPosition implements Cloneable {

    private Integer pRow;

    private Integer pCol;

    public BoardPosition(Integer pRow, Integer pCol) {
        this.pRow = pRow;
        this.pCol = pCol;
    }

    public Integer getpRow() {
        return pRow;
    }

    public void setpRow(Integer pRow) {
        this.pRow = pRow;
    }

    public Integer getpCol() {
        return pCol;
    }

    public void setpCol(Integer pCol) {
        this.pCol = pCol;
    }

    public BoardPosition clone() {
        return new BoardPosition(pRow, pCol);
    }
}
