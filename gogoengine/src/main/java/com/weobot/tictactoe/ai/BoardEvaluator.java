package com.weobot.tictactoe.ai;

import com.weobot.tictactoe.constants.GameConstants;
import com.weobot.tictactoe.gameboard.BigBoard;
import com.weobot.tictactoe.gameboard.SmallBoard;
import com.weobot.tictactoe.settings.SystemSettings;

/**
 * 碁盤を採点する
 * Created by HuangWeijing on 9/24/2015.
 */
public class BoardEvaluator {
    
    private static final int GAMER_POINT = 10;
    private static final int GAMER_BIG_POINT = 10000;

    /**
     * 大碁盤を採点する
     * @param bigBoard
     * @param player
     * @return
     */
    public static int evaluate(BigBoard bigBoard, int player) {
        Integer totalPoint = 0;
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                SmallBoard smallBoard = bigBoard.getSmallBoard(row, col);
                int smallBoardPoint = evaluate(smallBoard, player);
//                System.out.println(String.format("Board(%s, %s): point=%s", row, col, smallBoardPoint));
                totalPoint = totalPoint + smallBoardPoint;

            }
        }

        //System.out.println("SmallBoardPoint = " + totalPoint);
        int bigBoardPoint = evaluateBigBoard(bigBoard, player);
//        System.out.println("BigBoardPoint = " + bigBoardPoint);
        totalPoint = totalPoint + bigBoardPoint;
        return totalPoint;
    }

    public static int evaluateBigBoard(BigBoard bigBoard, int player) {
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        Integer totalPoints = 0;

        //横判定
        for(Integer row = 0; row < boardSize; row++) {
            Integer blankCount = 0;
            Integer gamerCount = 0;
            Integer otherGamerCount = 0;
            for(Integer col = 0; col < boardSize; col++) {
                if(bigBoard.getSmallBoard(row, col).getResultBean() == null) {
                    blankCount++;
                } else if(bigBoard.getSmallBoard(row, col).getResultBean().getPlayer() == player) {
                    gamerCount++;
                } else if(bigBoard.getSmallBoard(row, col).getResultBean().getPlayer() != player) {
                    otherGamerCount++;
                }
            }

            if (blankCount == 0 && otherGamerCount == 0) {
                //全部の駒がAI駒の場合、勝利ということでバリューは無限大
                return GameConstants.MAX_VALUE;
            } else if (blankCount == 0 && gamerCount == 0) {
                ////全部の駒が相手駒の場合、勝利ということでバリューは無限小
                return GameConstants.MIN_VALUE;
            } else if (gamerCount != 0 && otherGamerCount != 0) {
                continue;
            } else {
                //純粋な色、かつ勝ってない場合、駒数が多ければ多いほどいい
                totalPoints += gamerCount * GAMER_BIG_POINT -
                        otherGamerCount * ( GAMER_BIG_POINT);
                continue;
            }


        }

        //縦判定
        for(Integer col = 0; col < boardSize; col++) {
            Integer blankCount = 0;
            Integer gamerCount = 0;
            Integer otherGamerCount = 0;
            for (Integer row = 0; row < boardSize; row++) {
                if (bigBoard.getSmallBoard(row, col).getResultBean() == null) {
                    blankCount++;
                } else if (bigBoard.getSmallBoard(row, col).getResultBean().getPlayer() == player) {
                    gamerCount++;
                } else if (bigBoard.getSmallBoard(row, col).getResultBean().getPlayer() != player) {
                    otherGamerCount++;
                }
            }

            if (blankCount == 0 && otherGamerCount == 0) {
                //全部の駒がAI駒の場合、勝利ということでバリューは無限大
                return GameConstants.MAX_VALUE;
            } else if (blankCount == 0 && gamerCount == 0) {
                ////全部の駒が相手駒の場合、勝利ということでバリューは無限小
                return GameConstants.MIN_VALUE;
            } else if (gamerCount != 0 && otherGamerCount != 0) {
                continue;
            } else {
                //純粋な色、かつ勝ってない場合、駒数が多ければ多いほどいい
                totalPoints += gamerCount * GAMER_BIG_POINT -
                        otherGamerCount * ( GAMER_BIG_POINT);
                continue;
            }

        }

        //斜め判定
        Integer blankCount = 0;
        Integer gamerCount = 0;
        Integer otherGamerCount = 0;
        for(Integer row = 0, col = 0; row < boardSize; row++, col++) {
            if (bigBoard.getSmallBoard(row, col).getResultBean() == null) {
                blankCount++;
            } else if (bigBoard.getSmallBoard(row, col).getResultBean().getPlayer() == player) {
                gamerCount++;
            } else if (bigBoard.getSmallBoard(row, col).getResultBean().getPlayer() != player) {
                otherGamerCount++;
            }

        }

        if (blankCount == 0 && otherGamerCount == 0) {
            //全部の駒がAI駒の場合、勝利ということでバリューは無限大
            return GameConstants.MAX_VALUE;
        } else if (blankCount == 0 && gamerCount == 0) {
            ////全部の駒が相手駒の場合、勝利ということでバリューは無限小
            return GameConstants.MIN_VALUE;
        } else if (gamerCount != 0 && otherGamerCount != 0) {

        } else {
            //純粋な色、かつ勝ってない場合、駒数が多ければ多いほどいい
            totalPoints += gamerCount * GAMER_BIG_POINT -
                    otherGamerCount * ( GAMER_BIG_POINT);
        }

        blankCount = 0;
        gamerCount = 0;
        otherGamerCount = 0;
        for(Integer row = 0, col = boardSize - 1; row < boardSize; row++, col--) {
            if (bigBoard.getSmallBoard(row, col).getResultBean() == null) {
                blankCount++;
            } else if (bigBoard.getSmallBoard(row, col).getResultBean().getPlayer() == player) {
                gamerCount++;
            } else if (bigBoard.getSmallBoard(row, col).getResultBean().getPlayer() != player) {
                otherGamerCount++;
            }

        }

        if (blankCount == 0 && otherGamerCount == 0) {
            //全部の駒がAI駒の場合、勝利ということでバリューは無限大
            return GameConstants.MAX_VALUE;
        } else if (blankCount == 0 && gamerCount == 0) {
            ////全部の駒が相手駒の場合、勝利ということでバリューは無限小
            return GameConstants.MIN_VALUE;
        } else if (gamerCount != 0 && otherGamerCount != 0) {

        } else {
            //純粋な色、かつ勝ってない場合、駒数が多ければ多いほどいい
            totalPoints += gamerCount * GAMER_BIG_POINT -
                    otherGamerCount * ( GAMER_BIG_POINT);
        }

        return totalPoints;
    }

    /**
     * 小碁盤を採点する
     * @param smallBoard
     * @param player
     * @return
     */
    public static int evaluate(SmallBoard smallBoard, int player) {
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        Integer totalPoints = 0;

        //勝負が決めれば判定する価値がない
        if(smallBoard.getResultBean() != null) {
            int totalCount = 0;
            for(Integer row = 0; row < boardSize; row++) {
                for (Integer col = 0; col < boardSize; col++) {
                    if(smallBoard.getSquare(row, col) == player) {
                        totalCount++;
                    }
                }
            }

            return totalCount * (-GAMER_POINT);
        }

        //横判定
        for(Integer row = 0; row < boardSize; row++) {
            Integer blankCount = 0;
            Integer gamerCount = 0;
            Integer otherGamerCount = 0;
            for(Integer col = 0; col < boardSize; col++) {
                if(smallBoard.getSquare(row, col) == GameConstants.BLANK_SQUARE) {
                    blankCount++;
                } else if(smallBoard.getSquare(row, col) == player) {
                    gamerCount++;
                } else if(smallBoard.getSquare(row, col) != player) {
                    otherGamerCount++;
                }
            }

            if (blankCount == 0 && otherGamerCount == 0) {
                //全部の駒がAI駒の場合、勝利ということでバリューは無限大
                return GAMER_BIG_POINT;
            } else if (blankCount == 0 && gamerCount == 0) {
                ////全部の駒が相手駒の場合、勝利ということでバリューは無限小
                return -GAMER_BIG_POINT;
            } else if (gamerCount != 0 && otherGamerCount != 0) {
                continue;
            } else
            {
                //純粋な色、かつ勝ってない場合、駒数が多ければ多いほどいい
                totalPoints += gamerCount * GAMER_POINT -
                        otherGamerCount * ( GAMER_POINT);
                continue;
            }


        }

        //縦判定
        for(Integer col = 0; col < boardSize; col++) {
            Integer blankCount = 0;
            Integer gamerCount = 0;
            Integer otherGamerCount = 0;
            for (Integer row = 0; row < boardSize; row++) {
                if (smallBoard.getSquare(row, col) == GameConstants.BLANK_SQUARE) {
                    blankCount++;
                } else if (smallBoard.getSquare(row, col) == player) {
                    gamerCount++;
                } else if (smallBoard.getSquare(row, col) != player) {
                    otherGamerCount++;
                }
            }

            if (blankCount == 0 && otherGamerCount == 0) {
                //全部の駒がAI駒の場合、勝利ということでバリューは無限大
                return GAMER_BIG_POINT;
            } else if (blankCount == 0 && gamerCount == 0) {
                ////全部の駒が相手駒の場合、勝利ということでバリューは無限小
                return -GAMER_BIG_POINT;
            } else if (gamerCount != 0 && otherGamerCount != 0) {
                continue;
            } else
            {
                //純粋な色、かつ勝ってない場合、駒数が多ければ多いほどいい
                totalPoints += gamerCount * GAMER_POINT -
                        otherGamerCount * ( GAMER_POINT);
                continue;
            }

        }

        //斜め判定
        Integer blankCount = 0;
        Integer gamerCount = 0;
        Integer otherGamerCount = 0;
        for(Integer row = 0, col = 0; row < boardSize; row++, col++) {
            if (smallBoard.getSquare(row, col) == GameConstants.BLANK_SQUARE) {
                blankCount++;
            } else if (smallBoard.getSquare(row, col) == player) {
                gamerCount++;
            } else if (smallBoard.getSquare(row, col) != player) {
                otherGamerCount++;
            }

        }

        if (blankCount == 0 && otherGamerCount == 0) {
            //全部の駒がAI駒の場合、勝利ということでバリューは無限大
            return GAMER_BIG_POINT;
        } else if (blankCount == 0 && gamerCount == 0) {
            ////全部の駒が相手駒の場合、勝利ということでバリューは無限小
            return -GAMER_BIG_POINT;
        } else if (gamerCount != 0 && otherGamerCount != 0) {

        } else
        {
            //純粋な色、かつ勝ってない場合、駒数が多ければ多いほどいい
            totalPoints += gamerCount * GAMER_POINT -
                    otherGamerCount * ( GAMER_POINT);
        }

        blankCount = 0;
        gamerCount = 0;
        otherGamerCount = 0;
        for(Integer row = 0, col = boardSize - 1; row < boardSize; row++, col--) {
            if (smallBoard.getSquare(row, col) == GameConstants.BLANK_SQUARE) {
                blankCount++;
            } else if (smallBoard.getSquare(row, col) == player) {
                gamerCount++;
            } else if (smallBoard.getSquare(row, col) != player) {
                otherGamerCount++;
            }

        }

        if (blankCount == 0 && otherGamerCount == 0) {
            //全部の駒がAI駒の場合、勝利ということでバリューは無限大
            return GAMER_BIG_POINT;
        } else if (blankCount == 0 && gamerCount == 0) {
            ////全部の駒が相手駒の場合、勝利ということでバリューは無限小
            return -GAMER_BIG_POINT;
        } else if (gamerCount != 0 && otherGamerCount != 0) {

        } else {
            //純粋な色、かつ勝ってない場合、駒数が多ければ多いほどいい
            totalPoints += gamerCount * GAMER_POINT -
                    otherGamerCount * ( GAMER_POINT);
        }

        return totalPoints;
    }

}
