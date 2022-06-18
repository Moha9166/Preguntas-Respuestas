package com.mohamed;

import com.mohamed.gui.askQuestion;
import com.mohamed.utils.QuestionsFiles;

import javax.swing.*;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;

public class round {
    private user user;
    private int score;


    public round(com.mohamed.user user) {
        this.user = user;
    }

    public void setScore(int score) {
        this.score = score + 1;
    }

    public int getScore() {
        return score;
    }

    public void startPlay(String filenameFromUser) throws IOException, ClassNotFoundException, ClassCastException{
        QuestionsFiles qf;
        if (filenameFromUser == null){
            qf = new QuestionsFiles();
        }else{
            qf = new QuestionsFiles(filenameFromUser);
        }
            LinkedList<question> questionLinkedList = qf.loadQuestions();
            LinkedList<Boolean> userScores = new LinkedList<Boolean>();
            for (question asdf:questionLinkedList) {
                askQuestion dialog = new askQuestion(asdf);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
                userScores.add(dialog.userAnswered());
                //Stop showing new dialogs if it is false
                if (!dialog.isContinuePlay()){
                    break;
                }
            }
        for (Boolean b:userScores) {
            if (b){
                score++;
            }
        }
    }
}
