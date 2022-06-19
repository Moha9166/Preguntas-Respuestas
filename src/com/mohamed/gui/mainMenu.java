package com.mohamed.gui;

import com.mohamed.question;
import com.mohamed.round;
import com.mohamed.user;
import com.mohamed.utils.QuestionsFiles;
import com.mohamed.utils.UserFiles;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public class mainMenu extends JFrame {
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton playButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private final user actualUser;
    private Boolean fileLoaded = false;
    private final JFrame a;
    private final ImageIcon logoImage;
    private String filenameFromUser;

    /**
     * This is a constructor for the main menu window.
     *
     * @param u         It takes as parameter a user object
     * @param logoImage It takes as parameter a ImageIcon
     */
    public mainMenu(user u, ImageIcon logoImage) {
        this.logoImage = logoImage;
        this.a = this;
        this.actualUser = u;
        //Setting window parameters
        setIconImage(this.logoImage.getImage());
        setTitle("Preguntas y Respuestas");
        setContentPane(mainPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 400);
        //Setting the font and size fot the title
        titleLabel.setText("Preguntas y Respuestas  ");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 30));
        textField1.setText(actualUser.getUsername());
        textField2.setText(actualUser.getName());
        textField3.setText(actualUser.getSurname());
        //Checking if there is a Question File loaded
        checkIfFileLoaded();


        //Creating the menus
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        JMenu menu1 = new JMenu("Archivo");
        JMenu menu2 = new JMenu("Nuevo");
        JMenu menu3 = new JMenu("Editar");
        JMenu menu4 = new JMenu("Ver");
        menuBar.add(menu1);
        menuBar.add(menu2);
        menuBar.add(menu3);
        menuBar.add(menu4);
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
        //Disabling the creation of new users from main menu unless you are admin
        newUser.setEnabled(false);
        //Menu3 items
        JMenuItem editUser = new JMenuItem("Editar Mi Usuario");
        JMenuItem deleteUser = new JMenuItem("Borrar Mi Usuario");
        menu3.add(editUser);
        menu3.add(deleteUser);
        //Menu4 items
        JMenuItem showQuestions = new JMenuItem("Ver Preguntas");
        JMenuItem showUsers = new JMenuItem("Ver Usuarios");
        JMenuItem showHelp = new JMenuItem("Ayuda");
        //Disabling the option to see all the users and questions unless you are admin
        showQuestions.setEnabled(false);
        showUsers.setEnabled(false);
        menu4.add(showHelp);
        menu4.addSeparator();
        menu4.add(showQuestions);
        menu4.add(showUsers);
        //enables admin options if admin logged
        if (u.isAdmin()) {
            newUser.setEnabled(true);
            showQuestions.setEnabled(true);
            showUsers.setEnabled(true);
        }
        //Setting listeners
        exit.addActionListener(salirAC);
        playButton.addActionListener(playAC);
        deleteUser.addActionListener(borarAC);
        newUser.addActionListener(regAC);
        newQuestion.addActionListener(newQuesAC);
        editUser.addActionListener(updateUserAC);
        showUsers.addActionListener(showUsersAC);
        open.addActionListener(loadQuestions);
        showQuestions.addActionListener(showQuesions);
        showHelp.addActionListener(showHelpAC);


    }

    //Start of listeners
    ActionListener showHelpAC = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            showHelp dialog = new showHelp(logoImage);
            dialog.pack();
            dialog.setLocationRelativeTo(mainPanel);
            dialog.setVisible(true);
        }
    };
    ActionListener salirAC = e -> System.exit(0);
    ActionListener playAC = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            checkIfFileLoaded();
            if (!fileLoaded) {
                JOptionPane.showMessageDialog(mainPanel, "Carga un archivo de preguntas primero", "No hay preguntas", JOptionPane.INFORMATION_MESSAGE);
            } else {
                round rd1 = new round(actualUser);
                try {
                    rd1.startPlay(filenameFromUser);
                    System.out.println("Puntos: " + rd1.getScore());
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(null, "Error al cargar el archivo de preguntas", "Error!!!", JOptionPane.ERROR_MESSAGE);
                    filenameFromUser = null;
                    fileLoaded = false;
                } catch (ClassCastException exx) {
                    JOptionPane.showMessageDialog(null, "Por favor carga un archivo de preguntas", "Error!!!", JOptionPane.ERROR_MESSAGE);
                    filenameFromUser = null;
                    fileLoaded = false;
                }
            }
        }
    };
    ActionListener updateUserAC = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            editUser dialog = new editUser(actualUser, a, logoImage);
            dialog.pack();
            dialog.setLocationRelativeTo(mainPanel);
            dialog.setVisible(true);
        }
    };
    ActionListener regAC = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            register dialog = new register(logoImage);
            dialog.setMinimumSize(new Dimension(250, 350));
            dialog.pack();
            dialog.setLocationRelativeTo(mainPanel);
            dialog.setVisible(true);
        }
    };
    ActionListener borarAC = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int userSelection = JOptionPane.showConfirmDialog(null, "Esta seguro de querer borrar su usuario?",
                    "Confirmar Opcion", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE);
            if (userSelection == 0) {
                UserFiles uf = new UserFiles();
                try {
                    uf.deleteUser(actualUser.getUsername());
                    dispose();
                    firstLogin d = new firstLogin(logoImage);
                    d.pack();
                    d.setLocationRelativeTo(null);
                    d.setVisible(true);
                } catch (IOException | ClassNotFoundException ex) {
                    JOptionPane.showMessageDialog(mainPanel, "Fallo al borrar el usuario", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    };
    ActionListener newQuesAC = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            newQuestion dialog = new newQuestion(logoImage, filenameFromUser);
            dialog.pack();
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        }
    };
    ActionListener showUsersAC = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                showUsers dialog = new showUsers(getLLusers(), logoImage, a);
                dialog.pack();
                dialog.setLocationRelativeTo(a);
                dialog.setVisible(true);
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error al cargar los usuarios", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };
    ActionListener loadQuestions = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {


            JFileChooser selectFile = new JFileChooser();
            selectFile.addChoosableFileFilter(new FileNameExtensionFilter("Questions File", "moha"));
            selectFile.setVisible(true);
            selectFile.showOpenDialog(mainPanel);
            selectFile.setCurrentDirectory(new File(System.getProperty("user.home")));
            File selectedFile = selectFile.getSelectedFile();
            try {
                filenameFromUser = selectedFile.getAbsolutePath();
                fileLoaded = true;
                JOptionPane.showMessageDialog(mainPanel, "Se ha cargado el archivo seleccionado", "Carga Completada", JOptionPane.INFORMATION_MESSAGE);
            } catch (NullPointerException ex) {
                fileLoaded = true;
                JOptionPane.showMessageDialog(mainPanel, "No se ha selecionado ningun archivo", "Carga Interrumpida", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    };
    ActionListener showQuesions = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                checkIfFileLoaded();
                if (!fileLoaded) {
                    JOptionPane.showMessageDialog(mainPanel, "Carga un archivo de preguntas primero", "No hay preguntas", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    LinkedList<question> asdf = new QuestionsFiles().loadQuestions();
                    showQuestions dialog = new showQuestions(asdf, logoImage);
                    dialog.pack();
                    dialog.setBounds(0, 0, 600, 300);
                    dialog.setLocationRelativeTo(mainPanel);
                    dialog.setVisible(true);
                }
            } catch (IOException | ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error al cargar las Preguntas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    //Start of methods
    public static LinkedList<user> getLLusers() throws IOException, ClassNotFoundException {
        return new UserFiles().loadUsers();
    }

    public void checkIfFileLoaded() {
        if (Files.exists(Path.of(new QuestionsFiles().getFILENAME()))) {
            fileLoaded = true;
        }
    }

}
