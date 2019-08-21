package com.codve.geoquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {

    private static final String EXTRA_ANSWER_IS_TRUE =
            "com.codve.geoquiz.answer_is_true";

    private static final String EXTRA_ANSWER_SHOWN =
            "com.codve.geoquiz.answer_shown";

    private boolean mAnswerIsTrue;
    private boolean mAnswerIsShown;
    private TextView mAnswerTextView;
    private Button mShowAnswerButton;
    private TextView mVersionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        mAnswerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        if (savedInstanceState != null) {
            mAnswerIsShown = savedInstanceState.getBoolean(EXTRA_ANSWER_SHOWN, false);
            setAnswerShowResult(mAnswerIsShown); // 这一步打包数据
        }

        mAnswerTextView = (TextView) findViewById(R.id.answer_text_view);
        if (mAnswerIsShown) {
            if (mAnswerIsTrue) {
                mAnswerTextView.setText(R.string.true_button);
            } else {
                mAnswerTextView.setText(R.string.false_button);
            }
        }

        mShowAnswerButton = (Button) findViewById(R.id.show_answer_button);
        mShowAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAnswerIsTrue) {
                    mAnswerTextView.setText(R.string.true_button);
                } else {
                    mAnswerTextView.setText(R.string.false_button);
                }
                mAnswerIsShown = true;
                setAnswerShowResult(mAnswerIsShown); // 这一步打包数据
            }
        });

        mVersionTextView = (TextView) findViewById(R.id.version_text_view);
        String api_level = getString(R.string.api_level, Build.VERSION.SDK_INT);
        mVersionTextView.setText(api_level);

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean(EXTRA_ANSWER_SHOWN, mAnswerIsShown);
    }

    public static Intent newIntent(Context packageContext, boolean answerIsTrue) {
        Intent intent = new Intent(packageContext, CheatActivity.class);
        intent.putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue);
        return intent;
    }

    public void setAnswerShowResult(boolean isAnswerShown) {
        Intent data = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(RESULT_OK, data);
    }

    public static boolean wasAnswerShown(Intent result) {
        return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
    }
}
