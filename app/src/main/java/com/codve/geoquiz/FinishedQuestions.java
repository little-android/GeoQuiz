package com.codve.geoquiz;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class FinishedQuestions {
    // 已完成的题目

    private static FinishedQuestions sFinishedQuestions; // 单例
    private Map<Integer, Boolean> mFinishedQuestions;

    public static FinishedQuestions get() {
        if (sFinishedQuestions == null) {
            sFinishedQuestions = new FinishedQuestions();
        }
        return sFinishedQuestions;
    }

    private FinishedQuestions() {
        mFinishedQuestions = new Hashtable<>();
    }

    // 添加完成的题目索引
    public void add(Integer index, Boolean result) {
        mFinishedQuestions.put(index, result);
    }

    // 查看指定题目是否完成
    public boolean isFinished(Integer index) {
        return mFinishedQuestions.containsKey(index);
    }

    // 获取答题总数
    public int size() {
        return mFinishedQuestions.size();
    }

    // 获取答对的题目总数
    public int getCorrectCount() {
        int num = 0;
        for (int key : mFinishedQuestions.keySet()) {
            if (mFinishedQuestions.get(key)) {
                num++;
            }
        }
        return num;
    }

}
