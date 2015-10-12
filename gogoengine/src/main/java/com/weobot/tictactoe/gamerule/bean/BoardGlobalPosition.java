package com.weobot.tictactoe.gamerule.bean;

/**
 * Created by HuangWeijing on 9/6/2015.
 */
public class BoardGlobalPosition {

    public BoardGlobalPosition(BoardPosition bigBoardPos, BoardPosition smallBoardPos) {
        this.bigBoardPos = bigBoardPos;
        this.smallBoardPos = smallBoardPos;
    }

    /**
     * 大碁盤にある位置
     */
    private BoardPosition bigBoardPos;
    /**
     * 小碁盤にある位置
     */
    private BoardPosition smallBoardPos;

    public BoardPosition getSmallBoardPos() {
        return smallBoardPos;
    }

    public void setSmallBoardPos(BoardPosition smallBoardPos) {
        this.smallBoardPos = smallBoardPos;
    }

    public BoardPosition getBigBoardPos() {
        return bigBoardPos;
    }

    public void setBigBoardPos(BoardPosition bigBoardPos) {
        this.bigBoardPos = bigBoardPos;
    }
}
