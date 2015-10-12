package com.weobot.tictactoe.gameboard;

import com.weobot.tictactoe.constants.GameConstants;
import com.weobot.tictactoe.gamerule.bean.BoardPosition;
import com.weobot.tictactoe.gamerule.bean.BoardResultBean;
import com.weobot.tictactoe.settings.SystemSettings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 小碁盤
 * Created by HuangWeijing on 8/29/2015.
 */
public class SmallBoard {

    private Integer[][] board;
    private Integer rPos;
    private Integer cPos;
    private BoardResultBean resultBean;
    /**
     * 当碁盤は入力できるかどうか
     */
    private boolean currentBoardAvailable;
    private boolean boardIsFull = false;
    private Integer boardCount = 0;

    /**
     * 碁盤を初期化する
     */
    public void initBoard() {
        //システム設定にて碁盤のサイズを取得する
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        board = new Integer[boardSize][boardSize];
        for(int row=0; row<boardSize; row++) {
            for(int col=0; col<boardSize; col++) {
                //初期設定で全部クリアする
                board[row][col] = GameConstants.BLANK_SQUARE;
            }
        }
        //クリア
        resultBean = null;
        boardIsFull = false;
        boardCount = 0;
    }

    /**
     * 碁盤の状態を取得する
     * @param row
     * @param col
     * @return
     */
    public Integer getSquare(Integer row, Integer col) {
        return board[row][col];
    }

    /**
     * 碁盤の状態を取得する
     * @return
     */
    public Integer getSquare(BoardPosition position) {
        return getSquare(position.getpRow(), position.getpCol());
    }

    /**
     * 碁盤の状態を設定する
     * @param row
     * @param col
     * @return
     */
    public Boolean setSquare(Integer row, Integer col, Integer squareType) {
        if(this.boardIsFull) {
            return false;
        }
        if(board[row][col] == GameConstants.BLANK_SQUARE) {
            board[row][col] = squareType;
            boardCount++;
            updateFullFlag();
            return true;
        }
        return false;
    }

    private void updateFullFlag() {
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        if(boardCount >= boardSize * boardSize) {
            this.boardIsFull = true;
        }

    }

    /**
     * 碁盤の状態を設定する
     * @return
     */
    public Boolean setSquare(BoardPosition position, Integer squareType) {
        return setSquare(position.getpRow(), position.getpCol(), squareType);
    }

    public Integer getrPos() {
        return rPos;
    }

    public void setrPos(Integer rPos) {
        this.rPos = rPos;
    }

    public Integer getcPos() {
        return cPos;
    }

    public BoardPosition getPos() {
        return new BoardPosition(rPos, cPos);
    }

    public void setcPos(Integer cPos) {
        this.cPos = cPos;
    }

    public void setPos(Integer rPos, Integer cPos) {
        this.rPos = rPos;
        this.cPos = cPos;
    }

    public Integer getWinner() {
        if(resultBean == null) {
            return GameConstants.BLANK_SQUARE;
        }
        return resultBean.getPlayer();
    }

    public void setResultBean(BoardResultBean resultBean) {
        if(this.resultBean != null) {
            return;
        }
        this.resultBean = resultBean;
    }

    public BoardResultBean getResultBean() {
        return this.resultBean;
    }

    public boolean isCurrentBoardAvailable() {
        return currentBoardAvailable;
    }

    public void setCurrentBoardAvailable(boolean currentBoardAvailable) {
        this.currentBoardAvailable = currentBoardAvailable;
    }

    public boolean isBoardIsFull() {
        return boardIsFull;
    }

    public void setBoardIsFull(boolean boardIsFull) {
        this.boardIsFull = boardIsFull;
    }

    /**
     * 駒を入れられるスペースをリターンする。
     * @return
     */
    public List<BoardPosition> getAvailablePos() {
        List<BoardPosition> boardPositionList = new ArrayList<BoardPosition>();

        if(this.isBoardIsFull()) {
            return boardPositionList;
        } else {

            Integer boardSize = SystemSettings.getInstance().getBoardSize();

            for(int row = 0; row < boardSize; row++ ) {
                for (int col = 0; col < boardSize; col++) {
                    if(board[row][col] == GameConstants.BLANK_SQUARE) {
                        boardPositionList.add(new BoardPosition(row, col));
                    }
                }
            }
        }

        return boardPositionList;
    }


    public SmallBoard clone() {
        SmallBoard smallBoard = new SmallBoard();
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        smallBoard.board = new Integer[this.board.length][this.board.length];

        for(int row = 0; row < boardSize; row++ ) {
            for (int col = 0; col < boardSize; col++) {
                smallBoard.board[row][col] = board[row][col];
            }
        }

        smallBoard.rPos = this.rPos;
        smallBoard.cPos = this.cPos;
        smallBoard.currentBoardAvailable = this.currentBoardAvailable;
        smallBoard.boardIsFull = this.boardIsFull;
        smallBoard.boardCount = this.boardCount;
        if(resultBean != null) {
            smallBoard.resultBean = this.resultBean.clone();
        }
        return smallBoard;
    }

    public boolean rollBack(BoardPosition boardPosition) {
        if(board[boardPosition.getpRow()][boardPosition.getpCol()]
                == GameConstants.BLANK_SQUARE) {
            return false;
        }
        if(resultBean != null) {
            Integer boardSize = SystemSettings.getInstance().getBoardSize();
            BoardPosition startPos = resultBean.getStartPos();
            BoardPosition endPos = resultBean.getEndPos();
            int rowStep = (endPos.getpRow() - startPos.getpRow()) / ( boardSize - 1);
            int colStep = (endPos.getpCol() - startPos.getpCol()) / ( boardSize - 1);
            int row=startPos.getpRow();
            int col=startPos.getpCol();
            while(boardSize > 0) {
                if(boardPosition.getpRow() == row &&
                        boardPosition.getpCol() == col) {
                    this.resultBean = null;
                    break;
                }
                row = row + rowStep;
                col = col + colStep;
                boardSize--;
            }
        }

        this.boardIsFull = false;
        this.boardCount--;
        board[boardPosition.getpRow()][boardPosition.getpCol()]
                = GameConstants.BLANK_SQUARE;
        //this.currentBoardAvailable
        return true;
    }
}
