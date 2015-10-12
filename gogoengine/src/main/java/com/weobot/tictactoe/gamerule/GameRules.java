package com.weobot.tictactoe.gamerule;

import com.weobot.tictactoe.constants.GameConstants;
import com.weobot.tictactoe.gameboard.BigBoard;
import com.weobot.tictactoe.gameboard.SmallBoard;
import com.weobot.tictactoe.gamerule.bean.BoardResultBean;
import com.weobot.tictactoe.settings.SystemSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuangWeijing on 8/30/2015.
 */
public class GameRules {

    /**
     * 大碁盤の勝利判定
     * @param bigBoard
     * @return
     */
    public static List<BoardResultBean> checkWinnerResult(BigBoard bigBoard) {

        List<BoardResultBean> resultBeanList = new ArrayList<BoardResultBean>();

        Integer boardSize = SystemSettings.getInstance().getBoardSize();

        // check rows
        for (int row = 0; row < boardSize; row++) {
            boolean checkRow = true;
            // 一行目のセルを基準とする。
            Integer player = bigBoard.getSmallBoard(row, 0).getWinner();
            if(player == GameConstants.BLANK_SQUARE) {
                continue;
            }

            for(int col=0; col<boardSize; col++) {
                if(bigBoard.getSmallBoard(row, col).getWinner() != player) {
                    checkRow = false;
                    break;
                }
            }
            if(checkRow) {
                BoardResultBean bean = new BoardResultBean(row, 0, row, boardSize -1, player);
                resultBeanList.add(bean);
            }
        }

        // check columns
        for (int col = 0; col < boardSize; col ++) {
            boolean checkCol = true;
            //一列目を基準とする
            Integer player = bigBoard.getSmallBoard(0, col).getWinner();
            if(player == GameConstants.BLANK_SQUARE) {
                continue;
            }

            for(int row = 0; row < boardSize; row++) {
                if(bigBoard.getSmallBoard(row, col).getWinner() != player) {
                    checkCol = false;
                    break;
                }
            }
            if(checkCol) {
                BoardResultBean bean = new BoardResultBean(0, col, boardSize -1, col, player);
                resultBeanList.add(bean);
            }
        }

        // check diagonals
        Integer player = bigBoard.getSmallBoard(0, 0).getWinner();
        if(player != GameConstants.BLANK_SQUARE) {
            boolean checkDiag = true;

            for (int row = 0, col = 0; row < boardSize && col < boardSize; row++, col++) {
                if (bigBoard.getSmallBoard(row, col).getWinner() != player) {
                    checkDiag = false;
                    break;
                }
            }

            if(checkDiag) {
                BoardResultBean bean = new BoardResultBean(0, 0, boardSize -1, boardSize -1, player);
                resultBeanList.add(bean);
            }
        }

        player = bigBoard.getSmallBoard(0, boardSize - 1).getWinner();
        if(player != GameConstants.BLANK_SQUARE) {
            boolean checkDiag = true;

            for (int row = 0, col = boardSize - 1; row < boardSize && col >= 0; row++, col--) {
                if (bigBoard.getSmallBoard(row, col).getWinner() != player) {
                    checkDiag = false;
                    break;
                }
            }
            if(checkDiag) {
                BoardResultBean bean = new BoardResultBean(0, boardSize -1, boardSize -1, 0, player);
                resultBeanList.add(bean);
            }
        }


        return resultBeanList;
    }

    /**
     * 小碁盤の勝利判定
     * @param smallBoard
     * @return
     */
    public static List<BoardResultBean> checkWinnerResult(SmallBoard smallBoard) {

        List<BoardResultBean> resultBeanList = new ArrayList<BoardResultBean>();

        Integer boardSize = SystemSettings.getInstance().getBoardSize();

        // check rows
        for (int row = 0; row < boardSize; row++) {
            boolean checkRow = true;
            // 一行目のセルを基準とする。
            Integer player = smallBoard.getSquare(row, 0);
            if(player == GameConstants.BLANK_SQUARE) {
                continue;
            }

            for(int col=0; col<boardSize; col++) {
                if(smallBoard.getSquare(row, col) != player) {
                    checkRow = false;
                    break;
                }
            }
            if(checkRow) {
                BoardResultBean bean = new BoardResultBean(row, 0, row, boardSize -1, player);
                resultBeanList.add(bean);
            }
        }

        // check columns
        for (int col = 0; col < boardSize; col++) {
            boolean checkCol = true;
            //一列目を基準とする
            Integer player = smallBoard.getSquare(0, col);
            if(player == GameConstants.BLANK_SQUARE) {
                continue;
            }

            for(int row = 0; row < boardSize; row++) {
                if(smallBoard.getSquare(row, col) != player) {
                    checkCol = false;
                    break;
                }
            }
            if(checkCol) {
                BoardResultBean bean = new BoardResultBean(0, col, boardSize -1, col, player);
                resultBeanList.add(bean);
            }
        }

        // check diagonals
        Integer player = smallBoard.getSquare(0, 0);
        if(player != GameConstants.BLANK_SQUARE) {
            boolean checkDiag = true;

            for (int row = 0, col = 0; row < boardSize && col < boardSize; row++, col++) {
                if (smallBoard.getSquare(row, col) != player) {
                    checkDiag = false;
                    break;
                }
            }

            if(checkDiag) {
                BoardResultBean bean = new BoardResultBean(0, 0, boardSize -1, boardSize -1, player);
                resultBeanList.add(bean);
            }
        }

        player = smallBoard.getSquare(0, boardSize - 1);
        if(player != GameConstants.BLANK_SQUARE) {
            boolean checkDiag = true;

            for (int row = 0, col = boardSize - 1; row < boardSize && col >= 0; row++, col--) {
                if (smallBoard.getSquare(row, col) != player) {
                    checkDiag = false;
                    break;
                }
            }
            if(checkDiag) {
                BoardResultBean bean = new BoardResultBean(0, boardSize -1, boardSize -1, 0, player);
                resultBeanList.add(bean);
            }
        }


        return resultBeanList;
    }



}
