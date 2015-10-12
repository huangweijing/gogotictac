package com.weobot.myapplication.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.weobot.myapplication.GameMainActivity;
import com.weobot.myapplication.R;
import com.weobot.myapplication.SecondActivity;
import com.weobot.tictactoe.constants.GameConstants;
import com.weobot.tictactoe.gameboard.SmallBoard;
import com.weobot.tictactoe.gamerule.GameController;
import com.weobot.tictactoe.gamerule.GameRules;
import com.weobot.tictactoe.gamerule.bean.BoardPosition;
import com.weobot.tictactoe.gamerule.bean.BoardResultBean;
import com.weobot.tictactoe.settings.SystemSettings;

import java.util.List;

/**
 * Created by HuangWeijing on 8/16/2015.
 */
public class TestView extends View {

    private Boolean currentViewIsAvailable;

    private SmallBoard smallBoard;

    private Integer mSquareWidth;

    private Integer mBoardSize;

    private Bitmap mBmpSquare;
    private Bitmap mBmpActiveSquare;
    private Bitmap mBmpPlayer1;
    private Bitmap mBmpPlayer2;
    private Rect mSrcRectSquareActive;
    private Rect mSrcRectSquare;
    private Rect mSrcRectPlayer;
    private Paint mBmpPaint;

    private GameMainActivity mainActivity;

    public void setP(Paint p) {
        this.p = p;
    }
    public Paint getP() {
        return this.p;
    }

    private Paint p;

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context, attrs, 0);
    }

    public TestView(Context context, AttributeSet attrs, int style) {
        super(context, attrs, style);
        this.init(context, attrs, style);
    }

    public TestView(Context context) {
        super(context);
        this.init(context, null, 0);
    }

    public void init(Context context, AttributeSet attrs, int style) {
//        this.setOnClickListener(mOnClickListener);
        this.setOnTouchListener(mOnTouchListener);
        //this.p = new Paint(0x00000000);


        this.p = new Paint();
        this.p.setColor(0x99AA9176);
        this.p.setStrokeWidth(20);
        this.p.setStyle(Paint.Style.STROKE);

        mBoardSize = SystemSettings.getInstance().getBoardSize();

        mBmpSquare = getResBitmap(R.drawable.square);
        if(mBmpSquare != null) {
            mSrcRectSquare = new Rect(0, 0, mBmpSquare.getWidth() - 1, mBmpSquare.getHeight() - 1);
        }

        mBmpActiveSquare = getResBitmap(R.drawable.square_active);
        if(mBmpActiveSquare != null) {
            mSrcRectSquareActive = new Rect(0, 0, mBmpActiveSquare.getWidth() - 1, mBmpActiveSquare.getHeight() - 1);
        }

        mBmpPlayer1 = getResBitmap(R.drawable.square_player1);
        mBmpPlayer2 = getResBitmap(R.drawable.square_player2);
        if(mBmpPlayer1 != null) {
            mSrcRectPlayer = new Rect(0, 0, mBmpPlayer1.getWidth() - 1, mBmpPlayer1.getHeight() - 1);
        }
        mBmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        System.out.println("TestView inited!");
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        Integer width = (canvas.getHeight() > canvas.getWidth() ? canvas.getWidth() : canvas.getHeight()) / 3;
        mSquareWidth = width;

        for(int row = 0; row < boardSize; row++) {
            for(int col = 0; col < boardSize; col++) {
                //空白の碁盤を描く
                Rect desRectSquare = new Rect(col * width, row * width,
                        col * width + width, row * width + width );
                if(this.getSmallBoard().isCurrentBoardAvailable()) {
                    canvas.drawBitmap(mBmpActiveSquare, mSrcRectSquareActive, desRectSquare, mBmpPaint);
                } else {
                    canvas.drawBitmap(mBmpSquare, mSrcRectSquare, desRectSquare, mBmpPaint);
                }

                if(smallBoard.getSquare(row, col) == GameConstants.PLAYER1_SQUARE) {
                    //プレイア１を描く
                    canvas.drawBitmap(mBmpPlayer1, mSrcRectPlayer, desRectSquare, mBmpPaint);

                } else if(smallBoard.getSquare(row, col) == GameConstants.PLAYER2_SQUARE) {
                    //プレイア２を描く
                    canvas.drawBitmap(mBmpPlayer2, mSrcRectPlayer, desRectSquare, mBmpPaint);
                }
            }
        }
        this.drawWinnerLine(canvas);
        this.drawBigBoardResult(canvas);

    }

    private void drawBigBoardResult(Canvas canvas) {

        Integer width = (canvas.getHeight() > canvas.getWidth() ? canvas.getWidth() : canvas.getHeight()) / 3;
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        GameController controller = GameController.getInstance();
        BoardResultBean resultBean = controller.getBoard().getResultBean();
        if(controller.getBoard().getResultBean() != null) {

            BoardPosition startPos = resultBean.getStartPos();
            BoardPosition endPos = resultBean.getEndPos();

            int col = startPos.getpCol();
            int row = startPos.getpRow();
            int colStep = (endPos.getpCol() - startPos.getpCol()) / (boardSize - 1);
            int rowStep = (endPos.getpRow() - startPos.getpRow()) / (boardSize - 1);

            int stepCount = boardSize;

//            while((0 <= row && row <= endPos.getpRow()) && (0 <= col && col <= endPos.getpCol())) {
            while(stepCount > 0) {
                if(this.getSmallBoard().getrPos() == row
                        && this.getSmallBoard().getcPos() == col) {

                    //空白の碁盤を描く
                    Rect desRectSquare = new Rect(0, 0, boardSize * width, boardSize * width);
                    if(resultBean.getPlayer() == GameConstants.PLAYER1_SQUARE) {
                        //プレイア１を描く
                        canvas.drawBitmap(mBmpPlayer1, mSrcRectPlayer, desRectSquare, mBmpPaint);

                    } else if(resultBean.getPlayer() == GameConstants.PLAYER2_SQUARE) {
                        //プレイア２を描く
                        canvas.drawBitmap(mBmpPlayer2, mSrcRectPlayer, desRectSquare, mBmpPaint);
                    }

                }

                row = row + rowStep;
                col = col + colStep;
                stepCount--;
            }
        }
    }

    private void drawWinnerLine(Canvas canvas) {

        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        Integer width = (canvas.getHeight() > canvas.getWidth() ? canvas.getWidth() : canvas.getHeight()) / 3;

        BoardResultBean resultBean = smallBoard.getResultBean();
        if(resultBean != null) {
            BoardPosition startPos = resultBean.getStartPos();
            BoardPosition endPos = resultBean.getEndPos();
            //縦線
            if(startPos.getpCol() == endPos.getpCol()) {
                canvas.drawLine(startPos.getpCol() * width + width / 2, 0,
                        endPos.getpCol() * width + width / 2, boardSize * width, p);
            }
            //横線
            else if(startPos.getpRow() == endPos.getpRow()) {
                canvas.drawLine(0, startPos.getpRow() * width + width / 2,
                        boardSize * width,  endPos.getpRow() * width + width / 2, p);
            }
            //斜線
            else {
                if(startPos.getpCol() < endPos.getpCol()) {
                    canvas.drawLine(0, 0, boardSize * width, boardSize * width, p);
                } else {
                    canvas.drawLine(boardSize * width, 0, 0, boardSize * width, p);
                }
            }
        }

    }

    /**
     *
     * @param canvas
     */
    private void drawPlayerAvailableSquare(Canvas canvas) {
        if(this.currentViewIsAvailable) {
            Integer boardSize = SystemSettings.getInstance().getBoardSize();
            Integer width = (canvas.getHeight() > canvas.getWidth() ? canvas.getWidth() : canvas.getHeight()) / 3;
            canvas.drawRect(0, 0, boardSize * width, boardSize * width, this.p);
        }

    }

    private Bitmap getResBitmap(int bmpResId) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inDither = false;

        Resources res = getResources();
        Bitmap bmp = BitmapFactory.decodeResource(res, bmpResId, opts);

        if (bmp == null && isInEditMode()) {
            // BitmapFactory.decodeResource doesn't work from the rendering
            // library in Eclipse's Graphical Layout Editor. Use this workaround instead.

            Drawable d = res.getDrawable(bmpResId);
            int w = d.getIntrinsicWidth();
            int h = d.getIntrinsicHeight();
            bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
            Canvas c = new Canvas(bmp);
            d.setBounds(0, 0, w - 1, h - 1);
            d.draw(c);
        }

        return bmp;
    }

    View.OnTouchListener mOnTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {

            if((event.getAction() != MotionEvent.ACTION_UP)) {
                TestView currentView = (TestView) v;
                currentView.getMainActivity().invalidateAllView();
                return true;
            }

            if (v instanceof TestView) {
                TestView currentView = (TestView) v;
                if(!currentView.getSmallBoard().isCurrentBoardAvailable()) {
                    //失敗した場合メッセージを提示する
                    Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.active_popup);
                    TextView alertMessage = (TextView) dialog.findViewById(R.id.popup_message);
                    alertMessage.setText(R.string.alert_unavailable);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.show();


                    return false;
                }

                setSquare(event.getX(), event.getY());

                //v.invalidate();
//                List<BoardResultBean> resultBeanList =
//                        GameRules.checkWinnerResult(currentView.getSmallBoard());
//                if(resultBeanList != null && resultBeanList.size() > 0) {
//                    currentView.getSmallBoard().setResultBean(
//                            resultBeanList.get(0));
//                }
//
//                GameController controller = GameController.getInstance();
//                List<BoardResultBean> mainResultList = GameRules.checkWinnerResult(controller.getBoard());
//                if(mainResultList != null && mainResultList.size() > 0) {
//                    controller.getBoard().setResultBean(
//                            mainResultList.get(0));
//                }
                currentView.getMainActivity().invalidateAllView();
                //setAvailSquare(currentView);
            }
            return true;
        }


        private void setAvailSquare(TestView currentView) {

            GameController controller = GameController.getInstance();

            Integer currentPlayer = controller.getBoard().getCurrentPlayer();
            BoardPosition boardPosition = controller.getBoard().getPlayerCurrentPosition().get(currentPlayer);
            TestView[][] viewBoard = currentView.getMainActivity().getViewBoard();
            Integer boardSize = SystemSettings.getInstance().getBoardSize();
            //boolean
            for(int row = 0; row < boardSize; row++ ) {
                for(int col = 0; col < boardSize; col++ ) {
                    if(boardPosition != null) {
                        if(row == boardPosition.getpRow()
                                && col == boardPosition.getpCol()) {
                            viewBoard[row][col].getSmallBoard().setCurrentBoardAvailable(true);
                        } else {
                            viewBoard[row][col].getSmallBoard().setCurrentBoardAvailable(false);
                        }
                    } else {
                        viewBoard[row][col].getSmallBoard().setCurrentBoardAvailable(true);
                    }
                }
            }

            currentView.getMainActivity().invalidateAllView();
        }

        private void setSquare(float x, float y) {

            Integer boardSize = SystemSettings.getInstance().getBoardSize();
            GameController controller = GameController.getInstance();

            int cSquare = (int) (x / mSquareWidth );
            int rSquare = (int) (y / mSquareWidth );

            if(rSquare >= boardSize) {
                rSquare = boardSize - 1;
            }
            if(cSquare >= boardSize) {
                cSquare = boardSize - 1;
            }
            if(rSquare < 0) {
                rSquare = 0;
            }
            if(cSquare < 0) {
                cSquare = 0;
            }

            controller.putSquare(controller.getBoard(), smallBoard.getPos(), new BoardPosition(rSquare, cSquare));

//            Integer currentPlayer = controller.getCurrentPlayer();
//            Boolean setResult = smallBoard.setSquare(rSquare, cSquare, currentPlayer);
//            //設定が失敗した場合
//            if(setResult == false) {
//                return;
//            }
//            controller.getPlayerCurrentPosition().put(currentPlayer
//                    , new BoardPosition(rSquare, cSquare));
//            //次回の選手を設置する
//            if(currentPlayer == GameConstants.PLAYER1_SQUARE) {
//                controller.setCurrentPlayer(GameConstants.PLAYER2_SQUARE);
//            } else if (currentPlayer == GameConstants.PLAYER2_SQUARE) {
//                controller.setCurrentPlayer(GameConstants.PLAYER1_SQUARE);
//            }

        }
    };



//    View.OnClickListener mOnClickListener = new OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            if(v instanceof TestView) {
//                TestView testView = ((TestView) v);
//                if(testView.getP().getColor() == Color.RED) {
//                    testView.getP().setColor(Color.WHITE);
//                } else {
//                    testView.getP().setColor(Color.RED);
//                }
//
//                System.out.println();
//                System.out.println(testView.getId() + ":TestView Clicked!");
//                testView.invalidate();
//
//
//                Intent i = new Intent((Activity)v.getContext(), SecondActivity.class);
//
//                System.out.println(testView.getId());
//                System.out.println(R.id.testView1);
//
//                i.putExtra("view", testView.getId());
//                v.getContext().startActivity(i);
////                startacti
//
//            }
//            System.out.println("View Clicked!");
//        }
//    };

    public SmallBoard getSmallBoard() {
        return smallBoard;
    }

    public void setSmallBoard(SmallBoard smallBoard) {
        this.smallBoard = smallBoard;
    }

    public GameMainActivity getMainActivity() {
        return mainActivity;
    }

    public void setMainActivity(GameMainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

//    public Boolean getCurrentViewIsAvailable() {
//        return currentViewIsAvailable;
//    }
//
//    public void setCurrentViewIsAvailable(Boolean currentViewIsAvailable) {
//        this.currentViewIsAvailable = currentViewIsAvailable;
//    }
}
