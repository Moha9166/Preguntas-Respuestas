package com.mohamed.gui;

import com.mohamed.question;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

/**
 * This class creates a new {@code JDialog}.
 */
public class askQuestion extends JDialog {
    private JPanel contentPane;
    private JButton answerQuestion;
    private JButton buttonCancel;
    private JTextField statementField;
    private JRadioButton answer1Radio;
    private JRadioButton answer2Radio;
    private JRadioButton answer3Radio;
    private JTextField shortQuesAnsw;
    private JLabel shortQuesLbl;
    private question questionIN;
    private ButtonGroup bt;
    private static boolean continuePlay;
    private static boolean response;

    /**
     * This constructor is used to create a new {@code JDialog}
     * @param questionIN the question that you want to ask
     */
    public askQuestion(question questionIN) {
        this.questionIN = questionIN;
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(answerQuestion);
        //Setting the different fields to answer the questions
        //depending on if they are question type or no
        shortQuesAnsw.setVisible(!questionIN.isTest());
        shortQuesLbl.setVisible(!questionIN.isTest());
        answer1Radio.setVisible(questionIN.isTest());
        answer2Radio.setVisible(questionIN.isTest());
        answer3Radio.setVisible(questionIN.isTest());
        statementField.setText(questionIN.getQuestion());
        //Setting the answers to the radio buttons
        setAnswerQuestion(questionIN);
        /*Setting an Action Command to previously being able to recover
        the value of the radio button selected*/
        answer1Radio.setActionCommand(answer1Radio.getText());
        answer2Radio.setActionCommand(answer2Radio.getText());
        answer3Radio.setActionCommand(answer3Radio.getText());
        //Grouping the radio buttons
        bt = new ButtonGroup();
        bt.add(answer1Radio);
        bt.add(answer2Radio);
        bt.add(answer3Radio);



        //Action listener to the answer button
        answerQuestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        //Action listener to the cancel button
        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    /**
     * This method is called when the answer button is pressed.
     */
    private void onOK() {
        String userAnswer = null;
        //here we separate between the two type of questions to check one fields or others
        if (questionIN.isTest()){
            //If the question is a test it checks if any of the radio buttons is checked
            if (answer1Radio.isSelected() || answer2Radio.isSelected() || answer3Radio.isSelected()){
                //here we save the action command prev set in a string
                userAnswer = bt.getSelection().getActionCommand();
            }
        }else{
            //here if is not a test we save the content of a text field.
            userAnswer = shortQuesAnsw.getText().trim();
        }
        continuePlay = true;

        if (questionIN.repond(userAnswer)){
            response = true;
            dispose();
        }else{
            response = false;
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
        continuePlay = false;
    }

    /**
     * This method is used to set the different answers form the object question
     * shuffle and set the text fields
     * @param question_IN_Method {@code question} that you want to recover the different answers.
     * @see LinkedList
     * @see ArrayList
     * @see Collections
     * @see question
     */
    public void setAnswerQuestion(question question_IN_Method){
        ArrayList<String> answers = question_IN_Method.getAnswers();
        Collections.shuffle(answers);
        JRadioButton[] answerFields = {answer1Radio, answer2Radio, answer3Radio};
        int cont = 0;
        for (String q:answers) {
            answerFields[cont].setText(q);
            cont++;
        }
    }


    public boolean isContinuePlay() {
        return continuePlay;
    }
    public boolean userAnswered(){
        return response;
    }
}
