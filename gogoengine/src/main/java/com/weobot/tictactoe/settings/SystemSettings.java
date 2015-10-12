package com.weobot.tictactoe.settings;

/**
 * Created by HuangWeijing on 8/29/2015.
 */
public class SystemSettings {

    private static SystemSettings instance = null;
    private SystemSettings() { }

    public static SystemSettings getInstance() {
        if(instance == null) {
            instance = new SystemSettings();
        }
        return instance;
    }

    public Integer getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(Integer boardSize) {
        this.boardSize = boardSize;
    }

    private Integer boardSize;

}
