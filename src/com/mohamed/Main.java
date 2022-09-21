package com.mohamed;

import com.mohamed.gui.firstLogin;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

public class Main {
    public static void main(String[] args){
        try{
            URL logoURL = new URL("https://raw.githubusercontent.com/Moha9166/logos/main/logo.png");
            ImageIcon logo = new ImageIcon(logoURL, "logo");
            firstLogin d = new firstLogin(logo);
            d.pack();
            d.setLocationRelativeTo(null);
            d.setVisible(true);
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, ""+e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}