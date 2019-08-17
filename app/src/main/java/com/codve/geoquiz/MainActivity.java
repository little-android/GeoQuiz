package com.codve.geoquiz;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Hashtable;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private Button mTrueButton;
    private Button mFalseButton;
    private ImageButton mNextButton;
    private ImageButton mPreButton; // 上一题按钮
    private TextView mQuestionTextView; // 文本框

    // 问题数组
    private Question[] mQuestions = new Question[]{
            new Question(R.string.question_1, true),
            new Question(R.string.question_2, false),
            new Question(R.string.question_3, true)
    };

    private int mCurrentIndex = 0; // 当前题目索引

    private Map<Integer, Boolean> mFinishedQuestions; // 已完成的题目

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFinishedQuestions = new Hashtable<>();

        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mQuestionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestions.length;
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

        updateQuestion();
    }

    private void updateQuestion() {
        int question = mQuestions[mCurrentIndex].getTextResourceId();
        mQuestionTextView.setText(question);
    }

    private void checkAnswer(boolean userPressedTrue) {
        int messageResourceId = 0;

        if (checkFinish(mCurrentIndex)) { // 如果已经回答了问题, 提前结束
            messageResourceId = R.string.finished_toast;
            Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        boolean answerIsTrue = mQuestions[mCurrentIndex].isAnswerTrue();


        if (userPressedTrue == answerIsTrue) {
            messageResourceId = R.string.correct_toast;
            mFinishedQuestions.put(mCurrentIndex, true);
        } else {
            messageResourceId = R.string.incorrect_toast;
            mFinishedQuestions.put(mCurrentIndex, false);
        }

        Toast.makeText(this, messageResourceId, Toast.LENGTH_SHORT)
                .show();

        if (mCurrentIndex == mQuestions.length - 1) {
            String message = "you have correctly answered " + calcAnswer();
            message += "/" + mQuestions.length + " questions";
            Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.show();
        }
    }

    // 检查问题有没有作答
    private boolean checkFinish(int currentIndex) {
        return mFinishedQuestions.containsKey(currentIndex);
    }

    // 计算回答正确的数量
    private int calcAnswer() {
        int num = 0;
        for (int key : mFinishedQuestions.keySet()) {
            if (mFinishedQuestions.get(key)) {
                num ++;
            }
        }
        return num;
    }
}
