package com.mohamed;

import com.mohamed.gui.firstLogin;
import com.mohamed.utils.UserFiles;
import com.mohamed.utils.encrypt;

import javax.swing.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        try{
            firstLogin d = new firstLogin();
            d.pack();
            d.setLocationRelativeTo(null);
            d.setVisible(true);
        }catch (IOException e){
            JOptionPane.showMessageDialog(null, ""+e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}