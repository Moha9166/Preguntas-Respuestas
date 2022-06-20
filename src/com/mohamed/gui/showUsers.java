package com.mohamed.gui;

import com.mohamed.question;
import com.mohamed.user;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * This class creates the object {@code JDialog} to show the users in a table.
 */
public class showUsers extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel titleLabel;
    private JTable table1;

    /**
     * This is the only constructor for the {@code JDialog}
     * @param userLinkedList {@code LinkedList} with all the users.
     * @param logoImage {@code ImageIcon} to set as image-icon.
     * @param a {@code JFrame} to set the relative position.
     */
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
        //setting the headers
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
                //here when a user is clicked it prompts a window to edit the desired user
                String user = (String)table1.getValueAt(table1.getSelectedRow(), 1);
                String name = (String)table1.getValueAt(table1.getSelectedRow(), 1);
                String surname = (String)table1.getValueAt(table1.getSelectedRow(), 1);
                user tempUser = new user(user, name, surname,"tempPass");
                //here I show the edit dialog
                editUser dialog = new editUser(tempUser, b , logoImage);
                dialog.setLocationRelativeTo(contentPane);
                dialog.pack();
                dialog.setVisible(true);
                dispose();
                try {
                    showUsers frame = new showUsers(mainMenu.getLLusers(), logoImage, a);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(contentPane, "Error al cargar los usuarios", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    /**
     * This method is in charge to fill the table with the different users.
     * @param userLinkedList {@code LinkedList} with the users that you want to show.
     * @param tableModel {@code DefaultTableModel} for the table
     * @see user
     * @see LinkedList
     * @see DefaultTableModel
     */
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
