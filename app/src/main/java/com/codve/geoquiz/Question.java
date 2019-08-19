package com.codve.geoquiz;

public class Question {

    private int mTextResourceId; // 代表题目文本的 id
    private boolean mAnswerTrue; // 正确答案

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
