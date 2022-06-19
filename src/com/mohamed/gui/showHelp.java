package com.mohamed.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.MalformedURLException;
import java.net.URL;

public class showHelp extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea textArea1;
    private JLabel titleLabel;

    public showHelp(ImageIcon imageIcon) {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        //Setting title font and size
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        //Setting the content of the text area and disabling the editable property.
        textArea1.setEditable(false);
        textArea1.setText("Para jugar al juego, primero debes de cargar un archivo de preguntas, si no tienes ninguno, \n" +
                "se intentara cargar el archivo desde la ruta por defecto en tu carpeta de Documentos, si no existe ninguno\n" +
                "deberas crean uno y se se hace creando preguntas en el menu 'Nuevo' " +
                "puedes crear varias preguntas directamente para de esta manera generar el archivo de preguntas.\n" +
                "Una vez creadas las preguntas, ya puedes pulsar el boton de jugar.");

        //Action listeners auto-generated
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

    private void onCancel() {
        dispose();
    }

}
