package com.weobot.tictactoe.ai;

import com.weobot.tictactoe.constants.GameConstants;
import com.weobot.tictactoe.gameboard.BigBoard;
import com.weobot.tictactoe.gamerule.GameController;
import com.weobot.tictactoe.gamerule.bean.BoardGlobalPosition;
import com.weobot.tictactoe.settings.SystemSettings;

import java.util.List;

/**
 * Created by HuangWeijing on 9/6/2015.
 */
public class AIPlayer implements Runnable {

    private static final Integer SLEEP_TIME = 100;
    private static final Integer THINKING_TIME = 3000;
    private Integer timeTaken = 0;
    private Integer playerTag = GameConstants.BLANK_SQUARE;

    private IAIRecaller recaller;

    private Runnable thinkTimePlusAction = new Runnable() {

        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(SLEEP_TIME);
                    timeTaken = timeTaken + SLEEP_TIME;

                    //System.out.println("time taken =" + timeTaken);
                } catch (InterruptedException e) {
                }
            }
        }
    };

    public AIPlayer(IAIRecaller recaller, Integer playerTag) {
        this.playerTag = playerTag;
        this.recaller = recaller;
    }

    @Override
    public void run() {
        GameController gameController = GameController.getInstance();
        Thread thinkTimePlusThread = new Thread(thinkTimePlusAction);
        thinkTimePlusThread.start();


        while (gameController.getGameStatus() == GameConstants.GAMESTATUS_RUNNING) {

            try {
                Thread.sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(playerTag == GameConstants.BLANK_SQUARE) {
                return;
            } else {
                Integer currentPlayer = gameController.getBoard().getCurrentPlayer();
                if(currentPlayer == playerTag) {
                    //System.out.println("ai turn!!");
                    timeTaken = 0;
                    Integer score = doMyTurn(gameController);
                    System.out.println("Board: thinking time: " + timeTaken);
                    System.out.println("Board: score: " + score);
                    recaller.invalidate();
                }
            }
        }
    }



    /**
     * 計算して、BigBoardを更新する。
     */
    private Integer doMyTurn(GameController gameController) {

        //System.out.println("Board---------------------------------");

        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        List<BoardGlobalPosition> availablePositions = gameController.getAvailablePositions(gameController.getBoard());
        if(availablePositions.size() == 0) {
            return null;
        }
        //int currentAIPlayer = gameController.getBoard().getCurrentPlayer();

        BoardGlobalPosition bestPosition = availablePositions.get(0);
        int bestResult = GameConstants.MIN_VALUE;
        int alpha = GameConstants.MIN_VALUE;
        int beta = GameConstants.MAX_VALUE;

        int depth = 0;
        if(availablePositions.size() <= boardSize * boardSize) {
            depth = 6;
        }

        for(BoardGlobalPosition boardGlobalPosition : availablePositions) {
            BigBoard cloneBoard = gameController.getBoard().clone();
            gameController.putSquare(cloneBoard, boardGlobalPosition);
            //int evaluateResult = -doPlay(cloneBoard, cloneBoard.getCurrentPlayer(), depth);
            int evaluateResult = -doPlayAlphaBeta(cloneBoard, cloneBoard.getCurrentPlayer(), depth, -beta, -alpha);
//            gameController.rollback(cloneBoard);
            //System.out.println("Board Best Result:" + evaluateResult);
            if(evaluateResult > bestResult) {
                bestPosition = boardGlobalPosition;
                bestResult = evaluateResult;
            }
            if(bestResult > alpha) {
                alpha = bestResult;
            }
//            if(bestResult >= beta) {
//                break;
//            }
        }

//        System.out.println(gameController.printHistory(gameController.getBoard()));
        gameController.putSquare(gameController.getBoard(), bestPosition);

        return alpha;

    }

    private int doPlayAlphaBeta(BigBoard bigBoard, int currentAIPlayer, int depth, int alpha, int beta) {
        if(depth <= 0
//                || this.timeTaken >= THINKING_TIME
                ) {
//            System.out.println(GameController.getInstance().printHistory(bigBoard));
            return BoardEvaluator.evaluate(bigBoard, currentAIPlayer);
        }

        GameController gameController = GameController.getInstance();
        List<BoardGlobalPosition> availablePositions = gameController.getAvailablePositions(bigBoard);
        int bestResult = GameConstants.MIN_VALUE;

        for(BoardGlobalPosition boardGlobalPosition : availablePositions) {
//            BigBoard cloneBoard = bigBoard.clone();
//            System.out.println(String.format("Board try to put on (%s, %s) -> (%s, %s) -------"
//                    , boardGlobalPosition.getBigBoardPos().getpRow()
//                    , boardGlobalPosition.getBigBoardPos().getpCol()
//                    , boardGlobalPosition.getSmallBoardPos().getpRow()
//                    , boardGlobalPosition.getSmallBoardPos().getpCol()
//            ));
//            gameController.putSquare(cloneBoard, boardGlobalPosition);
//            int evaluateResult = -doPlayAlphaBeta(cloneBoard, cloneBoard.getCurrentPlayer(), depth - 1, -beta, -alpha);
            gameController.putSquare(bigBoard, boardGlobalPosition);
            int evaluateResult = -doPlayAlphaBeta(bigBoard, bigBoard.getCurrentPlayer(), depth - 1, -beta, -alpha);
            gameController.rollback(bigBoard);
            if(evaluateResult > bestResult) {
                bestResult = evaluateResult;
            }
            if(bestResult > alpha) {
                alpha = bestResult;
            }
            if(bestResult >= beta) {
                break;
            }
        }
        return bestResult;
    }



    private int doPlayAlphaBeta2(BigBoard bigBoard, int currentAIPlayer, int depth, int alpha, int beta) {
        if(depth <= 0
//                || this.timeTaken >= THINKING_TIME
                ) {
//            System.out.println(GameController.getInstance().printHistory(bigBoard));
            return BoardEvaluator.evaluate(bigBoard, currentAIPlayer);
        }

        GameController gameController = GameController.getInstance();
        List<BoardGlobalPosition> availablePositions = gameController.getAvailablePositions(bigBoard);
        int bestResult = GameConstants.MIN_VALUE;

        for(BoardGlobalPosition boardGlobalPosition : availablePositions) {
            BigBoard cloneBoard = bigBoard.clone();
            gameController.putSquare(cloneBoard, boardGlobalPosition);
            int evaluateResult = -doPlayAlphaBeta2(cloneBoard, cloneBoard.getCurrentPlayer(), depth - 1, -beta, -alpha);
//            gameController.putSquare(bigBoard, boardGlobalPosition);
//            int evaluateResult = -doPlayAlphaBeta(bigBoard, bigBoard.getCurrentPlayer(), depth - 1, -beta, -alpha);
//            gameController.rollback(bigBoard);
            if(evaluateResult > bestResult) {
                bestResult = evaluateResult;
            }
            if(bestResult > alpha) {
                alpha = bestResult;
            }
            if(bestResult >= beta) {
                break;
            }
        }
        return bestResult;
    }

    private int doPlay(BigBoard bigBoard, int currentAIPlayer, int depth) {
        if(depth <= 0 || this.timeTaken >= THINKING_TIME) {
            return BoardEvaluator.evaluate(bigBoard, currentAIPlayer);
        }

        GameController gameController = GameController.getInstance();
        List<BoardGlobalPosition> availablePositions = gameController.getAvailablePositions(bigBoard);
        int bestResult = GameConstants.MIN_VALUE;

        for(BoardGlobalPosition boardGlobalPosition : availablePositions) {
            BigBoard cloneBoard = bigBoard.clone();
//            System.out.println(String.format("Board try to put on (%s, %s) -> (%s, %s) -------"
//                    , boardGlobalPosition.getBigBoardPos().getpRow()
//                    , boardGlobalPosition.getBigBoardPos().getpCol()
//                    , boardGlobalPosition.getSmallBoardPos().getpRow()
//                    , boardGlobalPosition.getSmallBoardPos().getpCol()
//            ));
            gameController.putSquareWithoutCheck(cloneBoard, boardGlobalPosition);
            int evaluateResult = -doPlay(cloneBoard, cloneBoard.getCurrentPlayer(), depth-1);
            if(evaluateResult > bestResult) {
                bestResult = evaluateResult;
            }
        }
        return bestResult;
    }


}
