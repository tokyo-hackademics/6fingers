package com.fingers.six.elarm;

import com.fingers.six.elarm.common.QuestionItem;
import com.fingers.six.elarm.common.QuestionList;
import com.fingers.six.elarm.common.Word;
import com.fingers.six.elarm.dbHandlers.MasterDbHandler;
import com.fingers.six.elarm.dbHandlers.WordListDbHandler;
import com.fingers.six.elarm.util.SystemUiHider;
import com.fingers.six.elarm.utils.TestUtils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class QuestionActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;


    Button mAns1;
    Button mAns2;
    Button mAns3;
    Button mAns4;

    TextView question_word;
    QuestionItem questionItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getActionBar().hide();

        setContentView(R.layout.activity_question);

        final View controlsView = findViewById(R.id.fullscreen_content_controls);
        final View contentView = findViewById(R.id.fullscreen_content);

        // Set up an instance of SystemUiHider to control the system UI for
        // this activity.
        mSystemUiHider = SystemUiHider.getInstance(this, contentView, HIDER_FLAGS);
        mSystemUiHider.setup();
        mSystemUiHider
                .setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
                    // Cached values.
                    int mControlsHeight;
                    int mShortAnimTime;

                    @Override
                    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
                    public void onVisibilityChange(boolean visible) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
                            // If the ViewPropertyAnimator API is available
                            // (Honeycomb MR2 and later), use it to animate the
                            // in-layout UI controls at the bottom of the
                            // screen.
                            if (mControlsHeight == 0) {
                                mControlsHeight = controlsView.getHeight();
                            }
                            if (mShortAnimTime == 0) {
                                mShortAnimTime = getResources().getInteger(
                                        android.R.integer.config_shortAnimTime);
                            }
                            controlsView.animate()
                                    .translationY(visible ? 0 : mControlsHeight)
                                    .setDuration(mShortAnimTime);
                        } else {
                            // If the ViewPropertyAnimator APIs aren't
                            // available, simply show or hide the in-layout UI
                            // controls.
                            controlsView.setVisibility(visible ? View.VISIBLE : View.GONE);
                        }

                        if (visible && AUTO_HIDE) {
                            // Schedule a hide().
                            delayedHide(AUTO_HIDE_DELAY_MILLIS);
                        }
                    }
                });

        // Set up the user interaction to manually show or hide the system UI.
        contentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TOGGLE_ON_CLICK) {
                    mSystemUiHider.toggle();
                } else {
                    mSystemUiHider.show();
                }
            }
        });


        // Get parts
        mAns1 = (Button)findViewById(R.id.answer_1);
        mAns2 = (Button)findViewById(R.id.answer_2);
        mAns3 = (Button)findViewById(R.id.answer_3);
        mAns4 = (Button)findViewById(R.id.answer_4);

        question_word = (TextView)findViewById(R.id.txt_question_word);

        // Generate a quick question
        QuestionList questionList = new MasterDbHandler(getApplicationContext()).getAQuestionList(1);

        try {
            ArrayList<Word> wordArrayList = (ArrayList<Word>) (
                    new WordListDbHandler(getApplicationContext(),
                    "KANJI_N5").getAllWords());
            questionList.set_wordList(wordArrayList);
            autoGenerateQuestion(questionList);

        } catch (Exception e) {
            Log.d("Error", e.toString());
        }


        mAns1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionItem.set_userAnswer(mAns1.getText().toString());
                finish();
            }
        });

        mAns2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionItem.set_userAnswer(mAns2.getText().toString());
                Log.d("Button", questionItem.get_userAnswer());
                finish();
            }
        });
        mAns3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionItem.set_userAnswer(mAns3.getText().toString());
                finish();
            }
        });
        mAns4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionItem.set_userAnswer(mAns4.getText().toString());
                finish();
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }


    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    Handler mHideHandler = new Handler();
    Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            mSystemUiHider.hide();
        }
    };

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    private void autoGenerateQuestion(QuestionList questionList) {
        questionItem = TestUtils.generateQuickQuestion(questionList);

        if(questionItem.is_isEngToJap()) {
            question_word.setText(questionItem.get_word().get_eng());
        } else question_word.setText(questionItem.get_word().get_jap());

        String[] ans = questionItem.get_answers();
        mAns1.setText(ans[0]);
        mAns2.setText(ans[1]);
        mAns3.setText(ans[2]);
        mAns4.setText(ans[3]);
    }
}
