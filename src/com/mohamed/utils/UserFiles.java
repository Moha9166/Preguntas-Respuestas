package com.mohamed.utils;
import com.mohamed.domain.user;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * This class creates the object that is in charge for managing the user files.
 */
public class UserFiles {
    //This variable gets the default Document folder route.
    private final String DOC_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    private final String FILENAME_DEF = DOC_PATH+"\\PreguntasYRespuestas\\users.moha";
    public final String FILENAME;
    protected final String finaldir = DOC_PATH+"//PreguntasYRespuestas";

    /**
     * This constructor is used when file path is provided.
     * @param FILENAME {@code String} file path.
     */
    public UserFiles(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    /**
     * This constructor is used no when file path is provided.
     */
    public UserFiles(){
        this.FILENAME = FILENAME_DEF;
    }

    /**
     *This method is used to save the users in a file
     * @param list {@code LinkedList} with the users.
     * @throws IOException if the read/write of the file fails.
     * @throws ClassNotFoundException if one of the question is corrupted.
     * @see user
     * @see LinkedList
     * @see ObjectInputStream
     */
    public void saveUsers(LinkedList<user> list) throws IOException, ClassNotFoundException {
        //It creates a directory
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
        }else{
            ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(FILENAME));
            oos.writeObject(list);
            oos.close();
        }
    }

    /**
     * This method is used to load users into a linkedlist
     * @return {@code LinkedList} with the users
     * @throws IOException if the read/write of the file fails.
     * @throws ClassNotFoundException if one of the question is corrupted.
     * @see user
     * @see LinkedList
     * @see ObjectInputStream
     */
    public LinkedList<user> loadUsers() throws IOException, ClassNotFoundException {
        LinkedList<user> list = new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream( new FileInputStream(FILENAME));
        list = (LinkedList<user>) ois.readObject();
        ois.close();
        return list;
    }

    /**
     * This method is used to delete users.
     * @param userName {@code String} the username.
     * @throws IOException if the read/write of the file fails.
     * @throws ClassNotFoundException if one of the question is corrupted.
     * @see LinkedList
     * @see ObjectInputStream
     * @see user
     */
    public void deleteUser(String userName) throws IOException, ClassNotFoundException {
        LinkedList<user> userList = new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME));
        userList = (LinkedList<user>) ois.readObject();
        for (user us:userList) {
            String user = us.getUsername();
            if (user.equals(userName)){
                userList.remove(us);
            }
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME));
        oos.writeObject(userList);
        oos.close();
        ois.close();
    }

    /**
     * This method is used to update a user
     * @param oldUser {@code user} the user you want to edit
     * @param newUser {@code user} the new user that you want to insert.
     * @return {@code true} if the user is updated, {@code false} otherwise.
     * @throws IOException if the read/write of the file fails.
     * @throws ClassNotFoundException if one of the question is corrupted.
     */
    public boolean updateUser(user oldUser, user newUser) throws IOException, ClassNotFoundException {
        boolean edit = false;
        LinkedList<user> userLinkedList = new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME));
        userLinkedList = (LinkedList<user>) ois.readObject();
        user toRemove = null;
        for (user us:userLinkedList) {
            if (us.getUsername().equals(oldUser.getUsername())){
                toRemove=us;
                edit = true;
            }
        }
        if (edit && toRemove != null){
            userLinkedList.remove(toRemove);
            userLinkedList.add(newUser);
        }
        ois.close();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME));
        oos.writeObject(userLinkedList);
        oos.close();
        return edit;

    }
}
