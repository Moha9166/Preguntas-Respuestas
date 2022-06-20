package com.mohamed.utils;

import com.mohamed.question;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

/**
 * This class creates the object that is in charge of managing the question files
 */
public class QuestionsFiles{
    //here I get the Documents default directory
    private final String DOC_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    private final String FILENAME_DEF = DOC_PATH+"\\PreguntasYRespuestas\\questions.moha";
    protected final String FINALDIR = DOC_PATH+"//PreguntasYRespuestas";
    private final String FILENAME;

    /**
     * This constructor is used when the user wants to change the question files save directory.
     * @param FILENAME {@code String} with the file route.
     */
    public QuestionsFiles(String FILENAME) {
        this.FILENAME = FILENAME;
    }

    /**
     * This constructor is used when the user doesn't provide a question files save directory.
     */
    public QuestionsFiles(){
        this.FILENAME = FILENAME_DEF;
    }

    /**
     * This method saves a list of questions in a file
     * @param list {@code LinkedList} with all the questions.
     * @throws IOException when the read/write of the file fails.
     * @throws ClassNotFoundException when the one of the questions is corrupt.
     */
    public void saveQuestions(LinkedList<question> list) throws IOException, ClassNotFoundException {
        //Here I create a new directory
        if (new File(FINALDIR).mkdirs()){
            JOptionPane.showMessageDialog(null, "Se ha guardado en "+ FINALDIR, "Guardado!!!", JOptionPane.INFORMATION_MESSAGE);
        }
        LinkedList<question> pivotUser = new LinkedList<>();
        if (Files.exists(Path.of(FILENAME))){
            //here if a questions file is already created it reads it and re-writes it.
            ObjectInputStream ois = new ObjectInputStream( new FileInputStream(FILENAME));
            LinkedList<question> fileContent = (LinkedList<question>) ois.readObject();
            pivotUser.addAll(fileContent);
            ois.close();
            pivotUser.addAll(list);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME));
            oos.writeObject(pivotUser);
            oos.close();
        }else {
            //here if a questions file is not created it creates it.
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME));
            oos.writeObject(list);
            oos.close();
        }
    }

    /**
     * This method recovers all the questions from a file.
     * @return {@code LinkedList} with all the user.
     * @throws IOException if the read/write of the file fails.
     * @throws ClassNotFoundException if one of the question is corrupted.
     * @see LinkedList
     * @see ObjectInputStream
     */
    public LinkedList<question> loadQuestions() throws IOException, ClassNotFoundException {
        LinkedList<question> list = new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream( new FileInputStream(FILENAME));
        list = (LinkedList<question>) ois.readObject();
        ois.close();
        return list;
    }

    /**
     * This method deletes question.
     * @param titulo {@code String} with the question statement.
     * @throws IOException if the read/write of the file fails.
     * @throws ClassNotFoundException if one of the question is corrupted.
     * @see LinkedList
     * @see ObjectInputStream
     * @see question
     */
    public void deleteQuestion(String titulo) throws IOException, ClassNotFoundException {
        LinkedList<question> questionLinkedList = new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILENAME));
        questionLinkedList = (LinkedList<question>) ois.readObject();
        for (question qu:questionLinkedList) {
            String ques = qu.getQuestion();
            if (ques.equals(titulo)){
                questionLinkedList.remove(qu);
            }
        }
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME));
        oos.writeObject(questionLinkedList);
        oos.close();
        ois.close();
    }

    public String getFILENAME() {
        return FILENAME;
    }
}
