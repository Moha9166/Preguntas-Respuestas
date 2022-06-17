package com.mohamed.gui;

import com.mohamed.question;

import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class askQuestionTest extends JDialog {
    private JPanel contentPane;
    private JButton answerQuestion;
    private JButton buttonCancel;
    private JTextField statementField;
    private JRadioButton answer1Radio;
    private JRadioButton answer2Radio;
    private JRadioButton answer3Radio;
    private JTextField shortQuesAnsw;
    private JLabel shortQuesLbl;

    public askQuestionTest(question questionIN) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(answerQuestion);
        shortQuesAnsw.setVisible(!questionIN.isTest());
        shortQuesLbl.setVisible(!questionIN.isTest());
        answer1Radio.setVisible(questionIN.isTest());
        answer2Radio.setVisible(questionIN.isTest());
        answer3Radio.setVisible(questionIN.isTest());


        statementField.setText(questionIN.getQuestion());
        //Seting the answers to the radio buttons
        setAnswerQuestion(questionIN);

        //Grouping the radio buttons
        ButtonGroup bt = new ButtonGroup();
        bt.add(answer1Radio);
        bt.add(answer2Radio);
        bt.add(answer3Radio);




        answerQuestion.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

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

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

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

    public static void main(String[] args){
        question qu1 = new question("questionType", "questionCat", "questionStatement", "correctAnswer","answe1", "answer2");
        askQuestionTest dialog = new askQuestionTest(qu1);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
