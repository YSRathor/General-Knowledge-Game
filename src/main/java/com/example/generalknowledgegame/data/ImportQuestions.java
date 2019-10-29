package com.example.generalknowledgegame.data;

import android.content.Context;

import com.example.generalknowledgegame.model.Question;
import com.example.generalknowledgegame.model.QuestionBank;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.Scanner;

public class ImportQuestions {

    public void loadData(QuestionBank qb, Context context) {

        if (qb != null) {

            try {
                DataInputStream textFileStream = new DataInputStream(context.getAssets().open("questions.txt"));

                Scanner sc = new Scanner(textFileStream);

                String line;
                String[] arr;

                while (sc.hasNextLine()) {
                    line = sc.nextLine();
                    arr = line.split(",");

                    qb.addQuestion(new Question(HelperMethods.capitaliseFirstLetter(arr[0]), HelperMethods.capitaliseAllLetters(arr[1]),
                            HelperMethods.capitaliseAllLetters(arr[2]), HelperMethods.capitaliseAllLetters(arr[3]),
                            HelperMethods.capitaliseAllLetters(arr[4]),
                            (HelperMethods.capitaliseFirstLetter(arr[5].substring(0, 8)) + HelperMethods.capitaliseAllLetters(arr[5].substring(9))),
                            Integer.parseInt(arr[6])));
                }

                sc.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}
