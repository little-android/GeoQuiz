package com.codve.geoquiz;

public class Question {

    private int mTextResourceId;
    private boolean mAnswerTrue;

    public Question(int TextResourceId, boolean AnswerTrue) {
        this.mTextResourceId = TextResourceId;
        this.mAnswerTrue = AnswerTrue;
    }

    public int getTextResourceId() {
        return mTextResourceId;
    }

    public void setTextResourceId(int textResourceId) {
        mTextResourceId = textResourceId;
    }

    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
