package com.mohamed.gui;

import com.mohamed.domain.question;
import com.mohamed.utils.QuestionsFiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * This class creates {@code JDialog} to show all the questions.
 */
public class showQuestions extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel titleLabel;
    private JTable table1;
    private JLabel subtitleLabel;

    /**
     * THis is the only constructor for the {@code JDialog}.
     * @param questionsLinkedList {@code LinkedList} with the questions.
     * @param logoImage {@code ImageIcon} to make it icon.
     */
    public showQuestions(LinkedList<question> questionsLinkedList, ImageIcon logoImage, String filenameUser) {
        setIconImage(logoImage.getImage());
        setContentPane(contentPane);
        setModal(true);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 10));
        getRootPane().setDefaultButton(buttonOK);
        //Setting the table
        DefaultTableModel tableModel = new DefaultTableModel();
        table1.setModel(tableModel);
        //adding all the headers for the table
        tableModel.addColumn("Nro");
        tableModel.addColumn("Pregunta");
        tableModel.addColumn("Respuesta Correcta");
        tableModel.addColumn("Tipo");
        tableModel.addColumn("Categoria");
        tableModel.addColumn("Respuesta Adicional 1");
        tableModel.addColumn("Respuesta Adicional 2");
        fillTable(questionsLinkedList, tableModel);



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


        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int userSelection = JOptionPane.showConfirmDialog(null, "Esta seguro de querer borrar la pregunta?",
                        "Confirmar Opcion", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE);
                super.mouseClicked(e);
                if (userSelection == 0){
                    String statement = (String)table1.getValueAt(table1.getSelectedRow(), 1);
                    try{
                        if (filenameUser == null){
                            new QuestionsFiles().deleteQuestion(statement);
                        }else{
                            new QuestionsFiles(filenameUser).deleteQuestion(statement);
                        }
                    }catch (ClassNotFoundException | IOException ex){
                        JOptionPane.showMessageDialog(contentPane, "Error al borrar la Pregunta", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    /**
     * This method is in charge to fill the table with the different questions.
     * @param questionLinkedList {@code LinkedList} with the questions that you want to show.
     * @param tableModel {@code DefaultTableModel} for the table
     * @see question
     * @see LinkedList
     * @see DefaultTableModel
     */
    private void fillTable(LinkedList<question> questionLinkedList, DefaultTableModel tableModel) {
        //here we clear the table before filling it
        while (tableModel.getRowCount() > 0){
            tableModel.removeRow(0);
        }
        //this is a litle counter to count the number of questions.
        int as = 1;
        for (question qu: questionLinkedList) {
            //here i travel the question LinkedList and add the question depending if it is short or test
            if (qu.isTest()){
                tableModel.addRow(new Object[]{as,qu.getQuestion(), qu.getAnswer(), qu.getType(), qu.getCategory(), qu.getAnswer1(), qu.getAnswer2()});
            }else{
                tableModel.addRow(new Object[]{as,qu.getQuestion(), qu.getAnswer(), qu.getType(), qu.getCategory()});
            }
            as++;
        }
        setLocationRelativeTo(null);
        pack();
    }


}
