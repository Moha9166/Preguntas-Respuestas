package com.mohamed.gui;

import com.mohamed.utils.UserFiles;
import com.mohamed.user;
import com.mohamed.utils.encrypt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

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

    public firstLogin() throws IOException {
        setContentPane(panel);
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
        JMenuBar menuBar = new JMenuBar();
        JMenu regMenu = new JMenu("Nuevo");
        JMenuItem regItem = new JMenuItem("Registrate");
        menuBar.add(regMenu);
        regMenu.add(regItem);
        setJMenuBar(menuBar);
        // todo Setting the frame icon
//        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("images/logo.png"))).getImage());
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
            }else{
                passwordField1.setEchoChar((char)0);
                visible = true;
            }
        }
    };
    ActionListener reg = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            register dialog = new register();
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
                    mainMenu main = new mainMenu(loggedUser);
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
    private boolean login(String user, String pass) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        boolean logSuccees = false;
        if (user.equals("Admin") && pass.equals("fa585d89c851dd338a70dcf535aa2a92fee7836dd6aff1226583e88e0996293f16bc009c652826e0fc5c706695a03cddce372f139eff4d13959da6f1f5d3eabe")){
            passIssues.setText("Hola admin");
            return true;
        }else{
            UserFiles uf = new UserFiles();
            LinkedList<com.mohamed.user> userList = uf.loadUsers();
            for (user u:userList) {
                if (u.getUser().equals(user) && u.getPassword().equals(pass)){
                    loggedUser = u;
                    return logSuccees =  true;
                }else{
                    logSuccees = false;
                }
            }
        }
        return logSuccees;
    }

}


