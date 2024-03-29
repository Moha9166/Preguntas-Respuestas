package com.mohamed.gui;

import com.mohamed.domain.question;
import com.mohamed.utils.QuestionsFiles;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * This class creates a {@code JDialog}
 */
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
    private ImageIcon logoImage;
    private String filenameFromUser;

    /**
     * This is the constructor to create the {@code JDialog}
     * @param logoImage {@code ImageIcon} a image to use as icon.
     * @param filenameFromUser {@code String} the route to the file.
     */
    public newQuestion(ImageIcon logoImage, String filenameFromUser) {
        this.filenameFromUser = filenameFromUser;
        this.logoImage = logoImage;
        setIconImage(this.logoImage.getImage());
        setTitle("Nueva Pregunta");
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

    /**
     * THis method it creates a new question.
     * @throws IOException if the read/write file fails.
     * @throws ClassNotFoundException if the question was not found.
     */
    private void onOK() throws IOException, ClassNotFoundException {
        //Here we obligate the user to choose one question type
        if (!cortaRadioButton.isSelected() && !testRadioButton.isSelected()){
            JOptionPane.showMessageDialog(contentPane, "Selecciona un tipo de pregunta", "Error", JOptionPane.ERROR_MESSAGE);
        }
        //Here we do diferent things deppending if it is a test or short question
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

    /**
     * This method validates test questions.
     * @param question {@code String} the question statement.
     * @param answer1 {@code String} the correct answer.
     * @param answer2 {@code String} the first decoy answer.
     * @param answer3 {@code String} the second decoy answer.
     * @return {@code true} if the question is valid,  {@code false} otherwise.
     */
    public boolean validatorTest(String question, String answer1, String answer2, String answer3){
        if (question.isBlank() || answer1.isBlank() || answer2.isBlank() || answer3.isBlank()){
            return false;
        }else {
            return true;
        }
    }
    /**
     * This methods validates short questions.
     * @param question {@code String} the question statement.
     * @param answer1 {@code String} the correct answer.
     * @return {@code true} if the question is valid, {@code false} otherwise.
     */
    public boolean validatorShort(String question, String answer1){
        if (question.isBlank() || answer1.isBlank()){
            return false;
        }else {
            return true;
        }
    }

    /**
     * This method creates a new short question.
     * @param title {@code String} the statement for the question.
     * @param answer1 {@code String} the answer for the question.
     * @throws IOException if the read/write of the file fails
     * @throws ClassNotFoundException if the read/write of the class fails
     * @see QuestionsFiles
     * @see LinkedList
     */
    public void newShortQuestion(String title, String answer1) throws IOException, ClassNotFoundException {
        QuestionsFiles qf;
        if (filenameFromUser == null){
            qf = new QuestionsFiles();
        }else{
            qf = new QuestionsFiles(filenameFromUser);
        }
        LinkedList<question> questionLinkedList = new LinkedList<>();
        question qu1 = new question(questionType,comboBox1.getSelectedItem().toString() , title, answer1);
        questionLinkedList.add(qu1);
        qf.saveQuestions(questionLinkedList);
    }
    /**
     * This method creates a new short question.
     * @param title {@code String} the statement for the question.
     * @param answer1 {@code String} the answer for the question.
     * @param answer2 {@code String} the first decoy answer.
     * @param answer3 {@code String} the second decoy answer.
     * @throws IOException if the read/write of the file fails
     * @throws ClassNotFoundException if the read/write of the class fails
     * @see QuestionsFiles
     * @see LinkedList
     */
    public void newTestQuestion(String title, String answer1, String answer2, String answer3) throws IOException, ClassNotFoundException {
        QuestionsFiles qf;
        if (filenameFromUser == null){
            qf = new QuestionsFiles();
        }else{
            qf = new QuestionsFiles(filenameFromUser);
        }
        LinkedList<question> questionLinkedList = new LinkedList<>();
        question qu1 = new question(questionType,comboBox1.getSelectedItem().toString() , title, answer1, answer2, answer3);
        questionLinkedList.add(qu1);
        qf.saveQuestions(questionLinkedList);
    }

}
