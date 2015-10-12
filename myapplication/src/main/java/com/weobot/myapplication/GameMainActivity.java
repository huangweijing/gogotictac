package com.weobot.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.weobot.myapplication.util.SystemUiHider;
import com.weobot.myapplication.util.TestView;
import com.weobot.tictactoe.ai.AIPlayer;
import com.weobot.tictactoe.ai.IAIRecaller;
import com.weobot.tictactoe.constants.GameConstants;
import com.weobot.tictactoe.gameboard.BigBoard;
import com.weobot.tictactoe.gameboard.SmallBoard;
import com.weobot.tictactoe.gamerule.GameController;
import com.weobot.tictactoe.settings.SystemSettings;
//import android.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class GameMainActivity extends Activity implements IAIRecaller{

    private BigBoard bigBoard;
//    /**
//     * Whether or not the system UI should be auto-hidden after
//     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
//     */
//    private static final boolean AUTO_HIDE = true;
//
//    /**
//     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
//     * user interaction before hiding the system UI.
//     */
//    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;
//
//    /**
//     * If set, will toggle the system UI visibility upon interaction. Otherwise,
//     * will show the system UI visibility upon interaction.
//     */
//    private static final boolean TOGGLE_ON_CLICK = true;
//
//    /**
//     * The flags to pass to {@link SystemUiHider#getInstance}.
//     */
//    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;
//
//    /**
//     * The instance of the {@link SystemUiHider} for this activity.
//     */
//    private SystemUiHider mSystemUiHider;

    private Button myButton;

    public TestView[][] getViewBoard() {
        return viewBoard;
    }

    public void setViewBoard(TestView[][] viewBoard) {
        this.viewBoard = viewBoard;
    }

    private TestView[][] viewBoard;


    private Thread aiThread;
    private Thread aiThread2;


    private void initGame() {
        //View testView1 = this.findViewById(R.id.testView1);

        Integer boardSize = 3;
        SystemSettings.getInstance().setBoardSize(boardSize);
        viewBoard = new TestView[boardSize][boardSize];
        viewBoard[0][0] = (TestView) this.findViewById(R.id.testView1);
        viewBoard[0][1] = (TestView) this.findViewById(R.id.testView2);
        viewBoard[0][2] = (TestView) this.findViewById(R.id.testView3);
        viewBoard[1][0] = (TestView) this.findViewById(R.id.testView4);
        viewBoard[1][1] = (TestView) this.findViewById(R.id.testView5);
        viewBoard[1][2] = (TestView) this.findViewById(R.id.testView6);
        viewBoard[2][0] = (TestView) this.findViewById(R.id.testView7);
        viewBoard[2][1] = (TestView) this.findViewById(R.id.testView8);
        viewBoard[2][2] = (TestView) this.findViewById(R.id.testView9);

        bigBoard = new BigBoard();
        bigBoard.initBoard();

        GameController controller = GameController.getInstance();
        controller.setBoard(bigBoard);
        controller.setGameStatus(GameConstants.GAMESTATUS_RUNNING);
        controller.getBoard().setCurrentPlayer(GameConstants.PLAYER1_SQUARE);

        for(int row = 0; row < boardSize; row++ ) {
            for(int col = 0; col < boardSize; col++ ) {
                SmallBoard smallBoard = bigBoard.getSmallBoard(row, col);
                smallBoard.setrPos(row);
                smallBoard.setcPos(col);
                viewBoard[row][col].setSmallBoard(smallBoard);
                viewBoard[row][col].setMainActivity(this);
                viewBoard[row][col].getSmallBoard().setCurrentBoardAvailable(true);
            }
        }

        AIPlayer player = new AIPlayer(this, GameConstants.PLAYER2_SQUARE);
        aiThread = new Thread(player);
        aiThread.start();

//        AIPlayer player2 = new AIPlayer(this, GameConstants.PLAYER2_SQUARE);
//        aiThread2 = new Thread(player2);
//        aiThread2.start();


    }

    public void invalidateAllView() {
        Integer boardSize = SystemSettings.getInstance().getBoardSize();
        if(viewBoard != null) {

            for(int row = 0; row < boardSize; row++ ) {
                for(int col = 0; col < boardSize; col++ ) {
                    viewBoard[row][col].postInvalidate();

                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_game_main);
        this.initGame();

//        final View controlsView = findViewById(R.id.fullscreen_content_controls);
//        final View contentView = findViewById(R.id.testView1);

//        final View contentView = findViewById(R.id.fullscreen_content);

//        // Set up an instance of SystemUiHider to control the system UI for
//        // this activity.
//        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
//        mSystemUiHider.setup();
//        mSystemUiHider
//                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
//                    // Cached values.
//                    int mControlsHeight;
//                    int mShortAnimTime;
//
//                    @Override
//                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//                    public void onVisibilityChange(boolean visible) {
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
//                            // If the ViewPropertyAnimator API is available
//                            // (Honeycomb MR2 and later), use it to animate the
//                            // in-layout UI controls at the bottom of the
//                            // screen.
//                            if (mControlsHeight == 0) {
//                                mControlsHeight = controlsView.getHeight();
//                            }
//                            if (mShortAnimTime == 0) {
//                                mShortAnimTime = getResources().getInteger(
//                                        android.R.integer.config_shortAnimTime);
//                            }
//                            controlsView.animate()
//                                    .translationY(visible ? 0 : mControlsHeight)
//                                    .setDuration(mShortAnimTime);
//                        } else {
//                            // If the ViewPropertyAnimator APIs aren't
//                            // available, simply show or hide the in-layout UI
//                            // controls.
//                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
//                        }
//
//                        if (visible && AUTO_HIDE) {
//                            // Schedule a hide().
//                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
//                        }
//                    }
//                });
        //myButton = (Button) findViewById(R.id.hellobutton);
//        myButton.setOnLongClickListener(mLongClickListener);

        // Set up the user interaction to manually show or hide the system UI.
//        contentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (TOGGLE_ON_CLICK) {
//                    mSystemUiHider.toggle();
//                } else {
//                    mSystemUiHider.show();
//                }
//            }
//        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        Button menuButton = (Button) findViewById(R.id.menu_button);
        menuButton.setOnClickListener(this.mClickListener);
    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
//
//        // Trigger the initial hide() shortly after the activity has been
//        // created, to briefly hint to the user that UI controls
//        // are available.
//        delayedHide(100);
//    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
//    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
//        @Override
//        public boolean onTouch(View view, MotionEvent motionEvent) {
//            if (AUTO_HIDE) {
//                delayedHide(AUTO_HIDE_DELAY_MILLIS);
//            }
//            return false;
//        }
//    };

    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean rollbackResult = GameController.getInstance().rollback(
                    GameController.getInstance().getBoard());
            invalidateAllView();
            System.out.println("long click!! result: " + rollbackResult);
//            Intent i = new Intent((Activity)v.getContext(), SettingsActivity.class);
//            i.putExtra("btnName", myButton.getText());
//
//            startActivity(i);
//
//
//            return false;
        }
    };


    @Override
    public void invalidate() {
        this.invalidateAllView();
    }


//    private Handler mThreadHandler = new Handler() {
//        @Override
//        public void handleMessage(Message message) {
////            switch (message.what) {
////                case
////            }
//            invalidateAllView();
//        }
//    };

//    Handler mHideHandler = new Handler();
//    Runnable mHideRunnable = new Runnable() {
//        @Override
//        public void run() {
//            mSystemUiHider.hide();
//        }
//    };
//
//    /**
//     * Schedules a call to hide() in [delay] milliseconds, canceling any
//     * previously scheduled calls.
//     */
//    private void delayedHide(int delayMillis) {
//        mHideHandler.removeCallbacks(mHideRunnable);
//        mHideHandler.postDelayed(mHideRunnable, delayMillis);
//    }
}
