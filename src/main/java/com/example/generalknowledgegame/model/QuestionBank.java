package com.example.generalknowledgegame.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class QuestionBank implements Iterable<Question> {

    private ArrayList<Question> questions;
    private int questionNo, scoreCount;

    public QuestionBank() {
        questions = new ArrayList<>();
        questionNo = 0;
        scoreCount = 0;
    }

    public void addQuestion(Question q) {
        questions.add(q);
    }

    public void setQuestionNo(int questionNo) {
        if (questionNo >= 0 && questionNo <= questions.size()) {
            this.questionNo = questionNo;
        }
    }

    public void setScoreCount(int scoreCount) {
        if (scoreCount >= 0 && scoreCount <= questions.size()) {
            this.scoreCount = scoreCount;
        }
    }

    public void removeDuplicates(ArrayList<Question> list) {
        if (list.size() >= 2) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = i + 1; j < list.size(); j++) {
                    if (list.get(i).getQuestionID() == list.get(j).getQuestionID() || list.get(i).getQuestion().equals(list.get(j).getQuestion())) {
                        list.remove(j);
                        j--;
                    }
                }
            }
            questions = list;
        }
    }

    public void filterQuestions(int n) {
        ArrayList<Question> filteredList = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            int randomIndex = rand.nextInt(questions.size());
            filteredList.add(questions.get(randomIndex));
            questions.remove(randomIndex);
        }
        questions = filteredList;
    }

    public Question getQuestion(int index) {
        if (index < 0 || index >= questions.size()) {
            return null;
        } else {
            return questions.get(index);
        }
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public int getScoreCount() {
        return scoreCount;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public int getNoOfQuestions() {
        return questions.size();
    }

    @NonNull
    @Override
    public Iterator<Question> iterator() {
        return questions.iterator();
    }

    @NonNull
    @Override
    public String toString() {
        return "QuestionBank: [questions=" + questions + "]";
    }
}
