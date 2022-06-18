package com.mohamed.gui;

import com.mohamed.user;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

public class showUsers extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel titleLabel;
    private JTable table1;

    public showUsers(LinkedList<user> userLinkedList, ImageIcon logoImage, JFrame a) {
        JDialog b = this;
        setLocationRelativeTo(null);
        setIconImage(logoImage.getImage());
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        //Setting the table
        DefaultTableModel tableModel = new DefaultTableModel();
        table1.setModel(tableModel);
        tableModel.addColumn(" ");
        tableModel.addColumn("Usuario");
        tableModel.addColumn("Nombre");
        tableModel.addColumn("Apellido");
        fillTable(userLinkedList, tableModel);


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

            }
        });

        table1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                String user = (String)table1.getValueAt(table1.getSelectedRow(), 1);
                String name = (String)table1.getValueAt(table1.getSelectedRow(), 1);
                String surname = (String)table1.getValueAt(table1.getSelectedRow(), 1);
                user tempUser = new user(user, name, surname,"tempPass");
                editUser dialog = new editUser(tempUser, b , logoImage);
                dialog.setLocationRelativeTo(contentPane);
                dialog.pack();
                dialog.setVisible(true);
                dispose();
                showUsers frame = null;
                try {
                    frame = new showUsers(mainMenu.getLLusers(), logoImage, a);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Error al cargar los usuarios", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void fillTable(LinkedList<user> userLinkedList, DefaultTableModel tableModel) {
        while (tableModel.getRowCount() > 0){
            tableModel.removeRow(0);
        }
        int as = 1;
        for (user us: userLinkedList) {
            tableModel.addRow(new Object[]{as,us.getUsername(), us.getName(), us.getSurname()});
            as++;
        }
        pack();
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

}
