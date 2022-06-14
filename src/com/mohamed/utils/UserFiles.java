package com.mohamed.utils;
import com.mohamed.user;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public class UserFiles {
    private final String DOC_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    private final String FILENAME_DEF = DOC_PATH+"\\PreguntasYRespuestas\\users.moha";
    private final String FILENAME;


    public UserFiles(String FILENAME) {
        this.FILENAME = FILENAME;
    }
    public UserFiles(){
        this.FILENAME = FILENAME_DEF;
    }

    public void saveUsers(LinkedList<user> list) throws IOException, ClassNotFoundException {
        String finaldir = DOC_PATH+"//PreguntasYRespuestas";
        if (new File(finaldir).mkdirs()){
            JOptionPane.showMessageDialog(null, "Se ha guardado en "+finaldir, "Guardado!!!", JOptionPane.INFORMATION_MESSAGE);
        }
        LinkedList<user> pivot = new LinkedList<>();
        if (Files.exists(Path.of(FILENAME))){
            ObjectInputStream ois = new ObjectInputStream( new FileInputStream(FILENAME));
            LinkedList<user> fileObjects = (LinkedList<user>) ois.readObject();
            pivot.addAll(fileObjects);
            ois.close();
            pivot.addAll(list);
            ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(FILENAME));
            oos.writeObject(pivot);
            oos.close();
            System.out.println("El fichero existia asi que no lo hemos creado");
        }else{
            ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(FILENAME));
            oos.writeObject(list);
            System.out.println("El fichero no existia asi que lo hemos creado");
            oos.close();
        }
    }
    public LinkedList<user> loadUsers() throws IOException, ClassNotFoundException {
        LinkedList<user> list = new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream( new FileInputStream(FILENAME));
        list = (LinkedList<user>) ois.readObject();
        ois.close();
        return list;
    }
    public void deleteUser(String userName) throws IOException, ClassNotFoundException {
        LinkedList<user> userList = new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME));
        userList = (LinkedList<user>) ois.readObject();
        for (user us:userList) {
            String user = us.getUser();
            if (user.equals(userName)){
                userList.remove(us);
            }
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME));
        oos.writeObject(userList);
        oos.close();
        ois.close();
    }
}
