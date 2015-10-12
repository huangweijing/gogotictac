package com.weobot.tictactoe.gamerule.bean;

/**
 * Created by HuangWeijing on 8/30/2015.
 */
public class BoardResultBean implements Cloneable {

    private BoardPosition startPos;
    private BoardPosition endPos;
    private Integer player;

    public BoardResultBean(
            BoardPosition startPos, BoardPosition endPos, Integer player) {
        this.startPos = startPos;
        this.endPos = endPos;
        this.player = player;
    }

    public BoardResultBean(Integer startPRow, Integer startPCol
            , Integer endPRow, Integer endPCol, Integer player) {
        this.startPos = new BoardPosition(startPRow, startPCol);
        this.endPos = new BoardPosition(endPRow, endPCol);
        this.player = player;
    }

    public BoardPosition getStartPos() {
        return startPos;
    }

    public void setStartPos(BoardPosition startPos) {
        this.startPos = startPos;
    }

    public BoardPosition getEndPos() {
        return endPos;
    }

    public void setEndPos(BoardPosition endPos) {
        this.endPos = endPos;
    }

    public Integer getPlayer() {
        return player;
    }

    public void setPlayer(Integer player) {
        this.player = player;
    }

    public BoardResultBean clone() {
        return new BoardResultBean(this.startPos.clone()
                , this.endPos.clone(), player);
    }
}
