package com.mohamed.utils;

import com.mohamed.gui.askQuestion;
import com.mohamed.domain.question;
import com.mohamed.domain.user;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

/**
 * This class creates the object {@code round}.
 * @see com.mohamed.domain.user
 */
public class round {
    private user user;
    private int score;
    /**
     * This is the constructor to create a round
     * @param user {@code user} the user that plays the round
     */
    public round(user user) {
        this.user = user;
    }
    /**
     * This method increases the round score by one.
     * @param score {@code int} the actual score.
     */
    public void setScore(int score) {
        this.score = score + 1;
    }
    public int getScore() {
        return score;
    }
    /**
     * This method starts the round and starts showing the windows.
     * @param filenameFromUser {@code String} this is the path for the questions file.
     * @throws IOException if the read/write of the file fails.
     * @throws ClassNotFoundException if one of the question is corrupted.
     * @throws ClassCastException if the opened file does not content
     */
    public void startPlay(String filenameFromUser) throws IOException, ClassNotFoundException, ClassCastException{
        QuestionsFiles qf;
        if (filenameFromUser == null){
            qf = new QuestionsFiles();
        }else{
            qf = new QuestionsFiles(filenameFromUser);
        }
            LinkedList<question> questionLinkedList = qf.loadQuestions();
            if (questionLinkedList.size() == 0){
                System.out.println("ta vasio");
                return;
            }
            LinkedList<Boolean> userScores = new LinkedList<>();
            URL logoURL = new URL("https://raw.githubusercontent.com/Moha9166/logos/main/logo.png");
            ImageIcon logo = new ImageIcon(logoURL, "logo");
            for (question ques:questionLinkedList) {
                askQuestion dialog = new askQuestion(ques, logo);
                dialog.pack();
                dialog.setBounds(0,0,400, 280);
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
