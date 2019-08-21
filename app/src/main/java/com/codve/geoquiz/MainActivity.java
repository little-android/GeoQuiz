package com.codve.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreButton; // 上一题按钮
    private Button mCheatButton;
    private TextView mQuestionTextView; // 文本框

    private static final String TAG = "QuizActivity";
    private static final String KEY_INDEX = "index"; // 保存当前的题目索引.
    private static final String KEY_IS_CHEATER = "is_cheater"; // 保存是否有作弊

    // 问题数组
    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, false),
            new Question(R.string.question_3, true)
    };

    private int mCurrentIndex = 0; // 当前题目索引

    private static final int REQUEST_CODE_CHEAT = 0; // 请求代码
    private boolean mIsCheater;

    private FinishedQuestions mFinishedQuestions; // 已完成的题目
    private CheatedQuestions mCheatedQuestions; // 作弊记录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate(Bundle) called.");
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mIsCheater = savedInstanceState.getBoolean(KEY_IS_CHEATER, false);
        }

        mFinishedQuestions = FinishedQuestions.get();
        mCheatedQuestions = CheatedQuestions.get();

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex < mQuestions.length - 1) {
                    mCurrentIndex ++;
                    mIsCheater = mCheatedQuestions.isCheated(mCurrentIndex);
                }
                updateQuestion();
            }
        });

        mTrueButton = (Button) findViewById(R.id.true_button);
        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
            }
        });

        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
            }
        });

        mNextButton = (ImageButton) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex < mQuestions.length - 1) {
                    mCurrentIndex ++;
                    mIsCheater = mCheatedQuestions.isCheated(mCurrentIndex);
                }
                updateQuestion();
            }
        });

        mPreButton = (ImageButton) findViewById(R.id.pre_button);
        mPreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrentIndex > 0) {
                    mCurrentIndex --;
                }
                updateQuestion();
            }
        });

        mCheatButton = (Button) findViewById(R.id.cheat_button);
        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();
                Intent intent = CheatActivity.newIntent(MainActivity.this, answerIsTrue);
                startActivityForResult(intent, REQUEST_CODE_CHEAT);
            }
        });

        updateQuestion();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
        savedInstanceState.putBoolean(KEY_IS_CHEATER, mIsCheater);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_CODE_CHEAT) {
            if (data == null) {
                return;
            }
            mIsCheater = CheatActivity.wasAnswerShown(data);
            if (mIsCheater) {
                mCheatedQuestions.add(mCurrentIndex);
            }
        }
    }

    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResourceId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        int messageResourceId = 0;

        if (mFinishedQuestions.isFinished(mCurrentIndex)) { // 如果已经回答了问题, 提前结束
            messageResourceId = R.string.finished_toast;
            Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT)
                    .show();
            getResultToast();
            return;
        }

        boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();

        if (mIsCheater) {
            messageResourceId = R.string.judgment_toast;
        } else {
            if (userPressedTrue == answerIsTrue) {
                messageResourceId = R.string.correct_toast;
                mFinishedQuestions.add(mCurrentIndex, true);
            } else {
                messageResourceId = R.string.incorrect_toast;
                mFinishedQuestions.add(mCurrentIndex, false);
            }
        }
        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT)
                .show();

        getResultToast();
    }

    private void getResultToast() {
        if (mCurrentIndex == mQuestions.length - 1) {
            String message = "you have correctly answered " + mFinishedQuestions.getCorrectCount();
            message += "/" + mFinishedQuestions.size() + " questions";
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

}
