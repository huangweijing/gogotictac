package com.weobot.tictactoe.constants;

/**
 * Created by HuangWeijing on 8/29/2015.
 */
public class GameConstants {

    //駒が入っていない状態
    public static final int BLANK_SQUARE = 0;
    //選手１の駒が入っている状態
    public static final int PLAYER1_SQUARE = 1;
    //選手２の駒が入っている状態
    public static final int PLAYER2_SQUARE = 2;

    public static final int GAMESTATUS_WAIT = 0;

    public static final int GAMESTATUS_RUNNING = 1;

    public static final int GAMESTATUS_END = 2;

    public static final int PLAYER_TYPE_HUMAN = 1;

    public static final int PLAYER_TYPE_AI = 2;

    public static final int MAX_VALUE = 2000000000;
    public static final int MIN_VALUE = -2000000000;
}
