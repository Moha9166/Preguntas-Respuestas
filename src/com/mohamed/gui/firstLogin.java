package com.mohamed.gui;

import com.mohamed.utils.UserFiles;
import com.mohamed.domain.user;
import com.mohamed.utils.encrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;

/**
 * This class it creates a new {@code JFrame}
 */
public class firstLogin extends JFrame{
    public JPanel panel;
    private JLabel pic;
    private JTextField userField1;
    private JPasswordField passwordField1;
    private JLabel userLabel;
    private JLabel passLabel;
    private JLabel titleLabel;
    private JButton salirButton;
    private JButton entrarButton;
    private JLabel passIssues;
    private JButton mostrarButton;
    private user loggedUser;
    private Boolean visible = false;
    private ImageIcon logoImage;

    /**
     * This constructor creates a login window.
     * @param logoImage {@code ImageIcon} to make it windows icon.
     * @throws IOException if there is any problem while login.
     */
    public firstLogin(ImageIcon logoImage) throws IOException {
        this.logoImage = logoImage;
        setContentPane(panel);
        setIconImage(this.logoImage.getImage());
        setTitle("Inicio de Sesion");
        setMinimumSize( new Dimension(300,250));
        //This piece of code it takes care for setting the LookAndFeel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        setTitle("Preguntas & Respuestas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Setting the menu to make the new users able to register
        JMenuBar menuBar = new JMenuBar();
        JMenu regMenu = new JMenu("Nuevo");
        JMenuItem regItem = new JMenuItem("Registrate");
        menuBar.add(regMenu);
        regMenu.add(regItem);
        setJMenuBar(menuBar);
        //Setting Listeners to various components of the GUI
        salirButton.addActionListener(salir);
        entrarButton.addActionListener(entrar);
        regItem.addActionListener(reg);
        mostrarButton.addActionListener(showPass);


    }
    //End Of Constructor
    //Start of Listeners
    ActionListener salir = e -> System.exit(0);
    ActionListener showPass = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (visible){
                passwordField1.setEchoChar('•');
                visible = false;
                mostrarButton.setText("Mostrar");
            }else{
                passwordField1.setEchoChar((char)0);
                visible = true;
                mostrarButton.setText("Ocultar");
            }
        }
    };
    ActionListener reg = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            register dialog = new register(logoImage);
            dialog.setMinimumSize(new Dimension(250,350));
            dialog.pack();
            dialog.setLocationRelativeTo(panel);
            dialog.setVisible(true);
        }
    };
    ActionListener entrar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String user = userField1.getText();
            String pass = null;
            try {
                pass = new encrypt().securePass(String.valueOf(passwordField1.getPassword()));
            } catch (NoSuchAlgorithmException ex) {
                System.out.println(ex.getMessage());
            }
            try {
                if (login(user, pass)){
                    dispose();
                    mainMenu main = new mainMenu(loggedUser, logoImage);
                    main.setVisible(true);
                    main.setLocationRelativeTo(null);
                }else {
                    passIssues.setText("Usuario o Contraseña incorrectos!!!");
                    passIssues.setForeground(Color.RED);
                    pack();
                }
            } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException ex) {
                passIssues.setText("No existe ningun usuario registrado!!!");
                passIssues.setForeground(Color.RED);
                pack();
            }
        }
    };
    //Start of methods

    /**
     * This method is in charge of the user login.
     * @param user {@code String}  the userName of the user.
     * @param pass {@code String} the password of the user.
     * @return  {@code True} if the logging was successful, {@code false} otherwise.
     * @throws IOException if there is a fail reading the users file.
     * @throws ClassNotFoundException if there is no users in the file.
     * @throws NoSuchAlgorithmException if the password encryption fails.
     */
    private boolean login(String user, String pass) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        boolean logSuccees = false;
        String adminpass ="fa585d89c851dd338a70dcf535aa2a92fee7836dd6aff1226583e88e0996293f16bc009c652826e0fc5c706695a03cddce372f139eff4d13959da6f1f5d3eabe";
        if (user.equals("Admin") && pass.equals(adminpass)){
            loggedUser = new user("Admin", "Admin", "Admin", adminpass, true);
            return true;
        }else{
            UserFiles uf = new UserFiles();
            LinkedList<user> userList = uf.loadUsers();
            for (user u:userList) {
                if (u.getUsername().equals(user) && u.getPassword().equals(pass)){
                    loggedUser = u;
                    return true;
                }else{
                    logSuccees = false;
                }
            }
        }
        return logSuccees;
    }

}


