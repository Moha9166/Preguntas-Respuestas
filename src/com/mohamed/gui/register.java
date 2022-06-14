package com.mohamed.gui;

import com.mohamed.utils.UserFiles;
import com.mohamed.user;
import com.mohamed.utils.encrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

public class register extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField userField;
    private JTextField nameField;
    private JTextField surnameField;
    private JPasswordField passField;
    private JLabel userLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel passLabel;
    private JLabel passIssueLabel;
    private JLabel titleLabel;
    private JButton mostrarButton;
    private Boolean visible = false;

    public register() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        mostrarButton.addActionListener(showPass);

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
    //Start of Listeners
    ActionListener showPass = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (visible){
                passField.setEchoChar('•');
                visible = false;
            }else{
                passField.setEchoChar((char)0);
                visible = true;
            }
        }
    };
    //Start of methods
    private void onOK() {
        // add your code here
        String user = userField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String pass = String.valueOf(passField.getPassword());
        try {
            if (register(user, name, surname, pass)){
                JOptionPane.showMessageDialog(contentPane, "Registro completado con exito!!!","Registro", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e){
            System.out.println(e.getMessage());
        }
    }
    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    public boolean register(String user, String name, String surname, String pass) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        boolean regSucc = false;
        if (user.isBlank() || name.isBlank() || surname.isBlank() || pass.isBlank()){
            passIssueLabel.setText("El formulario no esta completado correctamente!!!");
            passIssueLabel.setForeground(Color.RED);
            pack();
            return regSucc;
        }else{
            String pass2 = new encrypt().securePass(pass);
            user tempUS = new user(user, name, surname, pass2);
            UserFiles uf = new UserFiles();
            LinkedList<user> userList = new LinkedList<>();
            userList.add(tempUS);
            uf.saveUsers(userList);
            return regSucc = true;
        }
    };

}
