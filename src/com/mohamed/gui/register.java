package com.mohamed.gui;

import com.mohamed.utils.UserFiles;
import com.mohamed.user;
import com.mohamed.utils.encrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class creates a {@code JDialog} to register a use
 * @see JDialog
 */
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
    private JLabel passStength;
    private Boolean visible = false;
    private ImageIcon logoImage;

    /**
     * This is the main constructor for the {@code JDialog}
     * @param logoImage a image to use as {@ImageIcon}
     */
    public register(ImageIcon logoImage) {
        this.logoImage = logoImage;
        setIconImage(this.logoImage.getImage());
        setTitle("Registro de Usuario");
        pack();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        //Setting the only action listener to the show button
        mostrarButton.addActionListener(showPass);
        //Adding tooltip to the password field
        passField.setToolTipText("Min 8 caracteres 1 Mayuscula y 1 digito");

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

        //This Action Listener is used to check if the password has the required number of chars
        passField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String pass = String.valueOf(passField.getPassword());
                int fontSize = 14;
                if (pass.length()<8){
                    passStength.setText("Débil");
                    passStength.setFont(new Font("Arial", Font.BOLD, fontSize));
                    passStength.setForeground(new Color(255, 0, 0));
                    pack();
                } else if(pass.length()<=12) {
                    passStength.setText("Intermedio");
                    passStength.setFont(new Font("Arial", Font.BOLD, fontSize));
                    passStength.setForeground(new Color(255, 115, 0));
                    pack();
                } else if (pass.length()>12) {
                    passStength.setText("Fuerte");
                    passStength.setFont(new Font("Arial", Font.BOLD, fontSize));
                    passStength.setForeground(new Color(31, 255, 0));
                    pack();
                }
            }
        });
    }
    //Start of Listeners
    /**
     * This action listener when triggered shows/hides the password.
     */
    ActionListener showPass = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (visible){
                passField.setEchoChar('•');
                visible = false;
                mostrarButton.setText("Mostrar");
            }else{
                passField.setEchoChar((char)0);
                visible = true;
                mostrarButton.setText("Ocultar");
            }
        }
    };
    //Start of methods
    private void onOK() {
        String user = userField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String pass = String.valueOf(passField.getPassword());
        try {
            //here we check for the password complexity and if is good proceeds to register.
            if (!isPassComplex(pass)){
                JOptionPane.showMessageDialog(contentPane, "La contraseña no es suficientemente compleja", "Error", JOptionPane.ERROR_MESSAGE);
            }else if (registerUser(user, name, surname, pass)){
                JOptionPane.showMessageDialog(contentPane, "Registro completado con exito!!!","Registro", JOptionPane.INFORMATION_MESSAGE);
                dispose();
            }
        }catch (IOException | ClassNotFoundException | NoSuchAlgorithmException e){
            JOptionPane.showMessageDialog(contentPane, "Error al registrar el usuario", "Error en el Registro", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void onCancel() {
        dispose();
    }
    /**
     * This method is in charge to check the inputs from the user and if all is good proceed to register it.
     * @param user {@code String} the userName for the user
     * @param name {@code String} the name for the user
     * @param surname {@code String} the surname for the user
     * @param pass {@code String} the password for the user
     * @return {@code true} if the user was registered successfully, {@code false} otherwise.
     * @throws IOException if there is some error with the users file.
     * @throws ClassNotFoundException if there is some error creating the user object.
     * @throws NoSuchAlgorithmException if the encryption method fails.
     */
    public boolean registerUser(String user, String name, String surname, String pass) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        boolean regSucc = false;
        //This check is to avoid users creating users named "Admin" otherwise the method automatic stops and returns false.
        if (user.equals("Admin")){
            JOptionPane.showMessageDialog(contentPane, "No puedes crear un usuario Admin", "Error", JOptionPane.ERROR_MESSAGE);
            return regSucc;
        }
        //here it checks if all the fields where filled by the user correctly, otherwise it returns false.
        if (user.isBlank() || name.isBlank() || surname.isBlank() || pass.isBlank()){
            passIssueLabel.setText("El formulario no esta completado correctamente!!!");
            passIssueLabel.setForeground(Color.RED);
            pack();
            return regSucc;
        }else{
            /*Here uses my own method to check if the user is already created, if not it proceeds with the creation of the
            user and saving it and returns true*/
            if (!isCreated(user)){
                String pass2 = new encrypt().securePass(pass);
                user tempUS = new user(user, name, surname, pass2);
                UserFiles uf = new UserFiles();
                LinkedList<user> userList = new LinkedList<>();
                userList.add(tempUS);
                uf.saveUsers(userList);
                return regSucc = true;
            }else {
                //if the execution reaches here it notifies the user.
                passIssueLabel.setText("El usuario ya se encuentra registrado!!!");
                passIssueLabel.setForeground(Color.RED);
                pack();
            }
        }
        return regSucc;
    }

    /**
     * This method checks if a user is created, traveling the users file.
     * @param userName the user name you want to check existence.
     * @return {@code true} if the user is already created, {@code false} otherwise
     * @throws IOException if there is some failure reading the file
     * @throws ClassNotFoundException if the user loaded is corrupted
     */
    public static boolean isCreated(String userName) throws IOException, ClassNotFoundException {
        UserFiles uf = new UserFiles();
        if (Files.exists(Path.of(uf.FILENAME))){
            LinkedList<user> usersList = new LinkedList<>();
            usersList = uf.loadUsers();
            for (user us:usersList) {
                if (us.getUsername().equals(userName)){
                    return true;
                }
            }
        }
        uf = null;
        return false;
    }
    public static boolean isPassComplex(String password) {
        // Regex to check valid password.
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,20}$";
        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        // If the password is empty
        // return false
        if (password == null) {return false;}
        // Pattern class contains matcher() method
        // to find matching between given password
        // and regular expression.
        Matcher m = p.matcher(password);
        // Return if the password matched the ReGex
        return m.matches();
    }
}
