package com.weobot.tictactoe.gamerule;

import com.weobot.tictactoe.constants.GameConstants;
import com.weobot.tictactoe.gameboard.BigBoard;
import com.weobot.tictactoe.gameboard.SmallBoard;
import com.weobot.tictactoe.gamerule.bean.BoardGlobalPosition;
import com.weobot.tictactoe.gamerule.bean.BoardPosition;
import com.weobot.tictactoe.gamerule.bean.BoardResultBean;
import com.weobot.tictactoe.settings.SystemSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HuangWeijing on 8/30/2015.
 */
public class GameController {


    private static GameController instance = null;

    private GameController() {
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    private Integer gameStatus = GameConstants.GAMESTATUS_END;

    /**
     * 大碁盤
     */
    private BigBoard board;

    public BigBoard getBoard() {
        return board;
    }

    public void setBoard(BigBoard board) {
        this.board = board;
    }


    public boolean putSquare(BigBoard bigBoard, BoardPosition bigBoardPos, BoardPosition smallBoardPos) {
        boolean putResult = putSquareWithoutCheck(bigBoard, bigBoardPos, smallBoardPos);
        if(putResult) {
            this.checkResult(bigBoard, bigBoard.getSmallBoard(bigBoardPos));
        }
        return putResult;
    }

    public boolean putSquare(BigBoard bigBoard, BoardGlobalPosition boardGlobalPosition) {
        return putSquare(bigBoard, boardGlobalPosition.getBigBoardPos(), boardGlobalPosition.getSmallBoardPos());
    }


    public boolean putSquareWithoutCheck(BigBoard bigBoard, BoardPosition bigBoardPos, BoardPosition smallBoardPos) {
        boolean putResult = false;
        Integer squarePlayer = bigBoard.getSmallBoard(bigBoardPos).getSquare(smallBoardPos);
        if(squarePlayer == GameConstants.BLANK_SQUARE) {
            putResult = bigBoard.getSmallBoard(bigBoardPos).setSquare(smallBoardPos, bigBoard.getCurrentPlayer());
            if(putResult) {
                //決めたこまをヒストリーに記録する
                bigBoard.getHistoryPositionStack().push(new BoardGlobalPosition(bigBoardPos, smallBoardPos));
                bigBoard.getPlayerCurrentPosition().put(bigBoard.getCurrentPlayer(), smallBoardPos);
                bigBoard.setCurrentPlayer(this.changePlayer(bigBoard.getCurrentPlayer()));
                this.setAvailSquare(bigBoard);
                this.checkResult(bigBoard, bigBoard.getSmallBoard(bigBoardPos));
            }
        }

        return putResult;
    }

    public boolean putSquareWithoutCheck(BigBoard bigBoard, BoardGlobalPosition boardGlobalPosition) {
        return putSquareWithoutCheck(bigBoard, boardGlobalPosition.getBigBoardPos(), boardGlobalPosition.getSmallBoardPos());
    }


    public Integer getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(Integer gameStatus) {
        this.gameStatus = gameStatus;
    }


    private void setAvailSquare(BigBoard bigBoard) {

        Integer currentPlayer = bigBoard.getCurrentPlayer();
        BoardPosition boardPosition = bigBoard.getPlayerCurrentPosition().get(currentPlayer);
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        //boolean
        for(int row = 0; row < boardSize; row++ ) {
            for(int col = 0; col < boardSize; col++ ) {
                //第一歩あるいは小碁盤がフルじゃなければ、全部入力可能
                if(boardPosition != null && !bigBoard.getSmallBoard(boardPosition).isBoardIsFull()) {
                    if(row == boardPosition.getpRow()
                            && col == boardPosition.getpCol()) {
                        bigBoard.getSmallBoard(row, col).setCurrentBoardAvailable(true);
                    } else {
                        bigBoard.getSmallBoard(row, col).setCurrentBoardAvailable(false);
                    }
                } else {
                    bigBoard.getSmallBoard(row, col).setCurrentBoardAvailable(true);
                }
            }
        }
        //currentView.getMainActivity().invalidateAllView();
    }

    /**
     * 入力可能なところをリターンする
     * @return
     */
    public List<BoardGlobalPosition> getAvailablePositions(BigBoard bigBoard) {
        List<BoardGlobalPosition> availablePositionList = new ArrayList<BoardGlobalPosition>();
        Integer boardSize = SystemSettings.getInstance().getBoardSize();

        for(int row = 0; row < boardSize; row++ ) {
            for (int col = 0; col < boardSize; col++) {
                SmallBoard smallBoard = bigBoard.getSmallBoard(row, col);
                if(smallBoard.isCurrentBoardAvailable()
                        && !smallBoard.isBoardIsFull()) {

                    List<BoardPosition> availablePos = smallBoard.getAvailablePos();
                    for(BoardPosition pos : availablePos) {
                        availablePositionList.add(new BoardGlobalPosition(
                                new BoardPosition(row, col), pos
                        ));
                    }
                }
            }
        }
        return availablePositionList;
    }

    private void checkResult(BigBoard bigBoard, SmallBoard smallBoard) {
        List<BoardResultBean> resultBeanList =
                GameRules.checkWinnerResult(smallBoard);
        if(resultBeanList != null && resultBeanList.size() > 0) {
            smallBoard.setResultBean(
                    resultBeanList.get(0));
        }

        List<BoardResultBean> mainResultList = GameRules.checkWinnerResult(bigBoard);
        if(mainResultList != null && mainResultList.size() > 0) {
            bigBoard.setResultBean(mainResultList.get(0));
        }
    }

    /**
     * プレイアを交換
     * @param currentPlayer
     * @return
     */
    public int changePlayer(int currentPlayer) {
        return currentPlayer == GameConstants.PLAYER1_SQUARE ?
                GameConstants.PLAYER2_SQUARE : GameConstants.PLAYER1_SQUARE;
    }

    public boolean rollback(BigBoard bigBoard) {
        GameController gameController = GameController.getInstance();
        if(bigBoard.getHistoryPositionStack().empty()) {
            //既に最初となった場合は、ロールバックさせない
            return false;
        }
        BoardGlobalPosition lastPosition = bigBoard.getHistoryPositionStack().peek();

        SmallBoard smallBoard = bigBoard.getSmallBoard(lastPosition.getBigBoardPos());
        boolean rollbackSuccess = smallBoard.rollBack(lastPosition.getSmallBoardPos());
        if(rollbackSuccess) {
            bigBoard.setResultBean(null);
            bigBoard.getHistoryPositionStack().pop();

            Integer currentPlayer = bigBoard.getCurrentPlayer();
            Integer lastPlayer = gameController.changePlayer(currentPlayer);
            // 駒をはずすことにより、PLAYER->次のSMALLBOARDの情報が変わったため、前々回の駒をここに使う
            bigBoard.getPlayerCurrentPosition().remove(lastPlayer);
            if(bigBoard.getHistoryPositionStack().size() >= 2) {
                BoardGlobalPosition lastlastPosition = bigBoard.getHistoryPositionStack().get(
                        bigBoard.getHistoryPositionStack().size() - 2);
                bigBoard.getPlayerCurrentPosition().put(lastPlayer, lastlastPosition.getSmallBoardPos());
            }

            //PLAYERを前の人にチェンジする
            bigBoard.setCurrentPlayer(lastPlayer);
            gameController.setAvailSquare(bigBoard);
            return true;
        }
        return false;
    }

    public String printHistory(BigBoard bigBoard) {
        StringBuffer historyBuffer = new StringBuffer();
        for (BoardGlobalPosition globalPosition : bigBoard.getHistoryPositionStack()) {
            historyBuffer.append(String.format("Board: (%s, %s) -> (%s, %s)"
                    , globalPosition.getBigBoardPos().getpRow()
                    , globalPosition.getBigBoardPos().getpCol()
                    , globalPosition.getSmallBoardPos().getpRow()
                    , globalPosition.getSmallBoardPos().getpRow()
            ));
        }
        return historyBuffer.toString();
    }
}
