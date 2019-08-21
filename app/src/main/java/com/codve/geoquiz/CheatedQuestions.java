package com.codve.geoquiz;

import java.util.ArrayList;
import java.util.List;

public class CheatedQuestions {

    public static CheatedQuestions sCheatedQuestions; // 单例
    private List<Integer> mCheatedQuestions;

    public static CheatedQuestions get() {
        if (sCheatedQuestions == null) {
            sCheatedQuestions = new CheatedQuestions();
        }
        return sCheatedQuestions;
    }

    private CheatedQuestions() {
        mCheatedQuestions = new ArrayList<>();
    }

    // 添加作弊题目
    public void add(int index) {
        if (isCheated(index)) {
            return;
        }
        mCheatedQuestions.add(index);
    }

    // 判断指定题目是否作弊
    public boolean isCheated(int index) {
        return mCheatedQuestions.contains(index);
    }


}
