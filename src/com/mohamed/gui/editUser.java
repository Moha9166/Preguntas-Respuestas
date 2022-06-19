package com.mohamed.gui;

import com.mohamed.user;
import com.mohamed.utils.UserFiles;
import com.mohamed.utils.encrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * This is a class that creates a new {@code JDialog}
 */
public class editUser extends JDialog {
    private JPanel contentPane;
    private JButton buttonSave;
    private JButton buttonCancel;
    private JLabel titleLabel;
    private JTextField userField;
    private JTextField nameField;
    private JLabel userLabel;
    private JLabel nameLabel;
    private JTextField surnameField;
    private JLabel surnameLabel;
    private JPasswordField passwordField1;
    private JLabel passwordLabel;
    private JButton showButton;
    private JLabel passIssues;
    private final user actualUser;
    private boolean visible;
    private JFrame a = null;
    private JDialog b = null;
    private final boolean isUserSure = false;
    private ImageIcon logoImage;

    /**
     * This constructor is used when the object is created from a {@code JFrame}
     * @param userPrev {@code user} the user that you want to edit.
     * @param a {@code JFrame} this is the mainFrame
     * @param logoImage {@code ImageIcon} for the window icon
     */
    public editUser(user userPrev, JFrame a, ImageIcon logoImage) {
        this.logoImage = logoImage;
        this.a=a;
        this.actualUser = userPrev;
        setIconImage(this.logoImage.getImage());
        setContentPane(contentPane);
        //Setting the title and the font for iy
        setTitle("Editar Usuario");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        setModal(true);
        getRootPane().setDefaultButton(buttonSave);
        setLocationRelativeTo(null);


        //Adding listeners to the components
        showButton.addActionListener(showPass);

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (NoSuchAlgorithmException | IOException | ClassNotFoundException ex) {
                    if (actualUser.getUsername().equals("Admin")){
                        JOptionPane.showMessageDialog(contentPane, "No se puede modificar el usuario del Administrador", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(contentPane, "Ha ocurrido un error, vuelve a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
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
    /**
     * This constructor is used when the object is created from a {@code JDialog}
     * @param userPrev {@code user} the user that you want to edit.
     * @param b {@JDialog JFrame} this is the mainFrame
     * @param logoImage {@code ImageIcon} for the window icon
     */
    public editUser(user userPrev, JDialog b, ImageIcon logoImage) {
        this.logoImage = logoImage;
        this.b=b;
        this.actualUser = userPrev;
        setIconImage(this.logoImage.getImage());
        setContentPane(contentPane);
        setTitle("Editar Usuario");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        setModal(true);
        getRootPane().setDefaultButton(buttonSave);
        setLocationRelativeTo(null);
        pack();


        //Adding listeners to the components
        showButton.addActionListener(showPass);

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    onOK();
                } catch (NoSuchAlgorithmException | IOException | ClassNotFoundException ex) {
                    if (actualUser.getUsername().equals("Admin")){
                        JOptionPane.showMessageDialog(contentPane, "No se puede modificar el usuario del Administrador", "Error", JOptionPane.ERROR_MESSAGE);
                    }else{
                        JOptionPane.showMessageDialog(contentPane, "Ha ocurrido un error, vuelve a intentarlo", "Error", JOptionPane.ERROR_MESSAGE);
                    }
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

    /**
     * This method it edits the selected user.
     * @throws NoSuchAlgorithmException when the encryption fails.
     * @throws IOException when the read/write file fails
     * @throws ClassNotFoundException when no user is found.
     * @see UserFiles
     * @see encrypt
     * @see firstLogin
     */
    private void onOK() throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        int userSelection = JOptionPane.showConfirmDialog(null, "Esta seguro de querer editar el usuario?",
                "Confirmar Opcion", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);
        if (userSelection == 0){
            String user = userField.getText();
            String name = nameField.getText();
            String surname = surnameField.getText();
            String pass = new encrypt().securePass(String.valueOf(passwordField1.getPassword()));
            UserFiles uf = new UserFiles();
            if (uf.updateUser(actualUser, new user(user, name, surname, pass))){
                JOptionPane.showMessageDialog(contentPane, "Se ha editado el usuario "+user, "Edicion Completada", JOptionPane.INFORMATION_MESSAGE);
            }
            dispose();
            if (a != null){
                a.dispose();
                try{
                    firstLogin d = new firstLogin(logoImage);
                    d.pack();
                    d.setLocationRelativeTo(null);
                    d.setVisible(true);
                }catch (IOException e){
                    JOptionPane.showMessageDialog(null, ""+e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }else {
            dispose();
        }

    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    //Start of listeners
    ActionListener showPass = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (visible){
                passwordField1.setEchoChar('•');
                visible = false;
            }else{
                passwordField1.setEchoChar((char)0);
                visible = true;
            }
        }
    };


}
