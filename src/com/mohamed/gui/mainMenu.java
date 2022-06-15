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
        JMenu menu3 = new JMenu("Editar");
        JMenu menu4 = new JMenu("Ver");
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu4);
        menuBar.add(menu3);
        //Menu1 items
        JMenuItem open = new JMenuItem("Cargar Archivo de Preguntas");
        JMenuItem exit = new JMenuItem("Salir");
        menu1.add(open);
        menu1.add(exit);
        //Menu2 items
        JMenuItem newQuestion = new JMenuItem("Nueva Pregunta");
        JMenuItem newUser = new JMenuItem("Nuevo Usuario");
        menu2.add(newQuestion);
        menu2.add(newUser);
        //Menu3 items
        JMenuItem editUser = new JMenuItem("Editar Mi Usuario");
        JMenuItem deleteUser = new JMenuItem("Borrar Mi Usuario");
        menu3.add(editUser);
        menu3.add(deleteUser);
        //Menu4 items
        JMenuItem showQuestions = new JMenuItem("Ver Preguntas");
        JMenuItem showUsers = new JMenuItem("Ver Usuarios");
        menu4.add(showQuestions);
        menu4.add(showUsers);

        //Setting listeners
        exit.addActionListener(salir);
        jugarButton.addActionListener(play);
        deleteUser.addActionListener(borar);
        newUser.addActionListener(reg);



    }
    ActionListener salir = e -> System.exit(0);
    ActionListener play = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println(fileLoaded);
            System.out.println(actualUser.toString());
        }
    };
    ActionListener reg = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            register dialog = new register();
            dialog.setMinimumSize(new Dimension(250,350));
            dialog.pack();
            dialog.setLocationRelativeTo(mainPanel);
            dialog.setVisible(true);
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
