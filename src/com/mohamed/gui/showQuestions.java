package com.mohamed.gui;

import com.mohamed.question;
import com.mohamed.user;
import com.mohamed.utils.QuestionsFiles;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;

public class showQuestions extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel titleLabel;
    private JTable table1;

    public showQuestions(LinkedList<question> questionsLinkedList, ImageIcon logoImage) {
        setIconImage(logoImage.getImage());
        setContentPane(contentPane);
        setModal(true);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        getRootPane().setDefaultButton(buttonOK);
        //Setting the table
        DefaultTableModel tableModel = new DefaultTableModel();
        table1.setModel(tableModel);
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
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    private void fillTable(LinkedList<question> questionLinkedList, DefaultTableModel tableModel) {
        while (tableModel.getRowCount() > 0){
            tableModel.removeRow(0);
        }
        int as = 1;
        for (question qu: questionLinkedList) {
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
