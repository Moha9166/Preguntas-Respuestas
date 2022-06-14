package com.mohamed.gui;

import com.mohamed.user;
import com.mohamed.utils.UserFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class mainMenu extends JFrame{
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton jugarButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private user actualUser;
    private Boolean fileLoaded = false;

    public mainMenu(user u){
        this.actualUser = u;
        //Setting the font and size fot the title
        titleLabel.setText("Preguntas y Respuestas  ");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));

        textField1.setText(actualUser.getUser());
        textField2.setText(actualUser.getName());
        textField3.setText(actualUser.getSurname());


        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700,400);

        //Creating the menus
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu1 = new JMenu("Archivo");
        JMenu menu2 = new JMenu("Nuevo");
        JMenu menu3 = new JMenu("Ver");
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        //Menu1 items
        JMenuItem open = new JMenuItem("Cargar Archivo de Preguntas");
        JMenuItem exit = new JMenuItem("Salir");
        menu1.add(open);
        menu1.add(exit);
        //Menu2 items
        JMenuItem newQuestion = new JMenuItem("Nueva Pregunta");
        JMenuItem newUser = new JMenuItem("Nueva Pregunta");
        menu2.add(newQuestion);
        menu2.add(newUser);
        //Menu3 items
        JMenuItem showProfile = new JMenuItem("Ver Perfil");
        JMenuItem showQuestions = new JMenuItem("Ver Preguntas");
        JMenuItem showUsers = new JMenuItem("Ver Usuarios");
        JMenuItem borrar = new JMenuItem("Borrar mi usuario");
        menu3.add(showProfile);
        menu3.add(showQuestions);
        menu3.add(showUsers);
        menu3.add(borrar);

        //Setting listeners
        jugarButton.addActionListener(play);
        borrar.addActionListener(borar);



    }
    ActionListener play = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(fileLoaded);
            UserFiles uf = new UserFiles();
            try {
                uf.deleteUser("manu");
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }

        }
    };
    ActionListener borar = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            UserFiles uf = new UserFiles();
            try {
                uf.deleteUser(actualUser.getName());
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    };

}
