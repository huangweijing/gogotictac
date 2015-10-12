package com.weobot.tictactoe.gameboard;

import com.weobot.tictactoe.gamerule.bean.BoardGlobalPosition;
import com.weobot.tictactoe.gamerule.bean.BoardPosition;
import com.weobot.tictactoe.gamerule.bean.BoardResultBean;
import com.weobot.tictactoe.settings.SystemSettings;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 大碁盤
 * Created by HuangWeijing on 8/29/2015.
 */
public class BigBoard {

    private SmallBoard board[][];

    private BoardResultBean resultBean;

    private Stack<BoardGlobalPosition> historyPositionStack = new Stack<BoardGlobalPosition>();
    /**
     * 選手今回駒入の場所を記憶する。
     */
    private Map<Integer, BoardPosition> playerCurrentPosition =
            new HashMap<Integer, BoardPosition>();
    /**
     * 今駒を入れようとする選手
     */
    private Integer currentPlayer;

    /**
     * 碁盤を初期化する
     */
    public void initBoard() {
        //システム設定にて碁盤のサイズを取得する
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        board = new SmallBoard[boardSize][boardSize];
        for(int row=0; row<boardSize; row++) {
            for(int col=0; col<boardSize; col++) {
                SmallBoard smallBoard = new SmallBoard();
                board[row][col] = smallBoard;
                //初期設定で全部クリアする
                smallBoard.initBoard();
                smallBoard.setrPos(row);
                smallBoard.setcPos(col);
            }
        }
    }

    /**
     * Deep Clone of BigBoard
     * @return
     */
    public BigBoard clone() {
        BigBoard bigBoard = new BigBoard();
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        bigBoard.board = new SmallBoard[boardSize][boardSize];
        for(int row=0; row<boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                bigBoard.board[row][col] = board[row][col].clone();
            }
        }
        if(this.resultBean != null) {
            bigBoard.setResultBean(this.resultBean.clone());
        }
        bigBoard.setCurrentPlayer(this.currentPlayer);

        Map<Integer, BoardPosition> clonePlayerCurrentPosition = new HashMap<Integer, BoardPosition>();
        clonePlayerCurrentPosition.putAll(this.playerCurrentPosition);
        bigBoard.setPlayerCurrentPosition(clonePlayerCurrentPosition);

        return bigBoard;
    }

    /**
     * 小碁盤の状態を取得する
     * @param row
     * @param col
     * @return
     */
    public SmallBoard getSmallBoard(Integer row, Integer col) {
        return board[row][col];
    }

    /**
     * 小碁盤の状態を取得する
     * @return
     */
    public SmallBoard getSmallBoard(BoardPosition position) {
        return getSmallBoard(position.getpRow(), position.getpCol());
    }


    public BoardResultBean getResultBean()
    {
        return resultBean;
    }

    public void setResultBean(BoardResultBean resultBean) {
        this.resultBean = resultBean;
    }

    public Integer getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Integer currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Map<Integer, BoardPosition> getPlayerCurrentPosition() {
        return playerCurrentPosition;
    }

    public void setPlayerCurrentPosition(Map<Integer, BoardPosition> playerCurrentPosition) {
        this.playerCurrentPosition = playerCurrentPosition;
    }

    public Stack<BoardGlobalPosition> getHistoryPositionStack() {
        return historyPositionStack;
    }
}
