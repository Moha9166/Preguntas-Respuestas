package com.mohamed.gui;

import com.mohamed.question;
import com.mohamed.utils.QuestionsFiles;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

public class newQuestion extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel titleLabel;
    private JTextField textField1;
    private JRadioButton cortaRadioButton;
    private JRadioButton testRadioButton;
    private JComboBox<String> comboBox1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private String questionType = "CORTA";

    public newQuestion() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        //Disabling two answer fields
        textField3.setEnabled(false);
        textField4.setEnabled(false);


        //Adding options to the drop down list
        comboBox1.addItem("Deportes");
        comboBox1.addItem("Ciencia");
        comboBox1.addItem("Historia");

        //Grouping the radio buttons
        ButtonGroup btGroup = new ButtonGroup();
        btGroup.add(cortaRadioButton);
        btGroup.add(testRadioButton);
        //Disabling annoying border around the radio buttons
        cortaRadioButton.setFocusPainted(false);
        testRadioButton.setFocusPainted(false);
        //Adding listeners to the radio buttons
        cortaRadioButton.addActionListener(fromTestToShort);
        testRadioButton.addActionListener(fromShortToTest);


        //Adding tooltips
        textField2.setToolTipText("Respuesta correcta");

        //Default button listeners
        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (IOException | ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
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
    ActionListener fromTestToShort = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textField3.setEnabled(false);
            textField4.setEnabled(false);
            textField3.setText("");
            textField4.setText(""); 
            questionType = "SHORT";
            System.out.println(questionType);
        }
    };
    ActionListener fromShortToTest = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textField3.setEnabled(true);
            textField4.setEnabled(true);
            questionType = "TEST";
            System.out.println(questionType);
        }
    };
    //Start of methods
    private void onOK() throws IOException, ClassNotFoundException {
        // add your code here
        if (!cortaRadioButton.isSelected() && !testRadioButton.isSelected()){
            JOptionPane.showMessageDialog(contentPane, "Selecciona un tipo de pregunta", "Error", JOptionPane.ERROR_MESSAGE);
        }
        if (questionType.toUpperCase().equals("SHORT")){
            String shortTitle = textField1.getText();
            String answer1 = textField2.getText();
            if (validatorShort(shortTitle, answer1)){
                newShortQuestion(shortTitle, answer1);
                dispose();
            }else{
                JOptionPane.showMessageDialog(contentPane, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (questionType.toUpperCase().equals("TEST")) {
            String shortTitle = textField1.getText();
            String answer1 = textField2.getText();
            String answer2 = textField3.getText();
            String answer3 = textField4.getText();
            if (validatorTest(shortTitle, answer1, answer2, answer3)){
                newTestQuestion(shortTitle, answer1, answer2, answer3);
                dispose();
            }else{
                JOptionPane.showMessageDialog(contentPane, "Completa todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public boolean validatorTest(String question, String answer1, String answer2, String answer3){
        if (question.isBlank() || answer1.isBlank() || answer2.isBlank() || answer3.isBlank()){
            return false;
        }else {
            return true;
        }
    }
    public boolean validatorShort(String question, String answer1){
        if (question.isBlank() || answer1.isBlank()){
            return false;
        }else {
            return true;
        }
    }
    public void newShortQuestion(String title, String answer1) throws IOException, ClassNotFoundException {
        LinkedList<question> questionLinkedList = new LinkedList<>();
        QuestionsFiles qf = new QuestionsFiles();
        question qu1 = new question(questionType,comboBox1.getSelectedItem().toString() , title, answer1);
        System.out.println(qu1.toString());
        questionLinkedList.add(qu1);
        qf.saveQuestions(questionLinkedList);
    }
    public void newTestQuestion(String title, String answer1, String answer2, String answer3) throws IOException, ClassNotFoundException {
        LinkedList<question> questionLinkedList = new LinkedList<>();
        QuestionsFiles qf = new QuestionsFiles();
        question qu1 = new question(questionType,comboBox1.getSelectedItem().toString() , title, answer1, answer2, answer3);
        questionLinkedList.add(qu1);
        System.out.println(qu1.toString());
        qf.saveQuestions(questionLinkedList);
    }

}
