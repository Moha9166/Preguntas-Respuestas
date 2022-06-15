package com.mohamed.gui;

import javax.swing.*;
import java.awt.event.*;

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


        buttonOK.addActionListener(new ActionListener() {
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
    ActionListener fromTestToShort = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textField3.setEnabled(false);
            textField4.setEnabled(false);
            textField3.setText("");
            textField4.setText(""); 
            questionType = "SHORT";
        }
    };
    ActionListener fromShortToTest = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            textField3.setEnabled(true);
            textField4.setEnabled(true);
            questionType = "TEST";
        }
    };
    //Start of methods
    private void onOK() {
        // add your code here

        dispose();
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


    public static void main(String[] args) {
        newQuestion dialog = new newQuestion();
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);
        System.exit(0);
    }
}
