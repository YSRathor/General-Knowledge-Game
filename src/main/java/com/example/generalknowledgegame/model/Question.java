package com.example.generalknowledgegame.model;

import androidx.annotation.NonNull;

public class Question implements Comparable<Question> {

    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private String answer;
    private int questionID;

    public Question(String question, String optionA, String optionB, String optionC, String optionD, String answer, int questionID) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.answer = answer;
        this.questionID = questionID;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public String getAnswer() {
        return answer;
    }

    int getQuestionID() {
        return questionID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this.getClass() != obj.getClass())
            return false;

        Question other = (Question) obj;

        return this.questionID == (other.questionID) && this.question.equals(other.question);
    }

    @Override
    public int compareTo(Question other) {
        int result = Integer.compare(this.questionID, other.questionID);

        if (result == 0) {
            result = this.question.compareTo(other.question);
        }

        return result;
    }

    @NonNull
    @Override
    public String toString() {
        return "Question: [question=" + question + ", optionA=" + optionA + ", optionB=" + optionB
                + ", optionC=" + optionC + ", optionD=" + optionD + ", answer=" + answer + ", questionID=" + questionID + "]";
    }
}
