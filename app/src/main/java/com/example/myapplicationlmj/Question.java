package com.example.myapplicationlmj;

public class Question {
    private int mTextId;//题目
    private boolean mAnswer;//答案

    public Question(int mTextId, boolean mAnswer) {
        this.mTextId = mTextId;
        this.mAnswer = mAnswer;
    }

    public int getmTextId() {
        return mTextId;
    }

    public void setmTextId(int mTextId) {
        this.mTextId = mTextId;
    }

    public boolean ismAnswer() {
        return mAnswer;
    }

    public void setmAnswer(boolean mAnswer) {
        this.mAnswer = mAnswer;
    }
}
