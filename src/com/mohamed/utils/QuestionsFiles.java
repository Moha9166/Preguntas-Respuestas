package com.mohamed.utils;

import com.mohamed.question;
import com.mohamed.user;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;

public class QuestionsFiles{
    private final String DOC_PATH = new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
    private final String FILENAME_DEF = DOC_PATH+"\\PreguntasYRespuestas\\questions.moha";
    private final String FILENAME;
    protected final String finaldir = DOC_PATH+"//PreguntasYRespuestas";


    public QuestionsFiles(String FILENAME) {
        this.FILENAME = FILENAME;
    }
    public QuestionsFiles(){
        this.FILENAME = FILENAME_DEF;
    }

    public void saveQuestions(LinkedList<question> list) throws IOException, ClassNotFoundException {
        if (new File(finaldir).mkdirs()){
            JOptionPane.showMessageDialog(null, "Se ha guardado en "+finaldir, "Guardado!!!", JOptionPane.INFORMATION_MESSAGE);
        }
        LinkedList<question> pivot = new LinkedList<>();
        if (Files.exists(Path.of(FILENAME))){
            ObjectInputStream ois = new ObjectInputStream( new FileInputStream(FILENAME));
            LinkedList<question> fileContent = (LinkedList<question>) ois.readObject();
            pivot.addAll(fileContent);
            ois.close();
            pivot.addAll(list);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME));
            oos.writeObject(pivot);
            oos.close();
            System.out.println("El fichero existia asi que no lo hemos creado");
        }else {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILENAME));
            oos.writeObject(list);
            System.out.println("No existia el fichero asi que lo hemos creado");
            oos.close();
        }



        System.out.println("Preguntas guardadas correctamente");
    }
    public LinkedList<question> loadQuestions() throws IOException, ClassNotFoundException {
        LinkedList<question> list = new LinkedList<>();
        ObjectInputStream ois = new ObjectInputStream( new FileInputStream(FILENAME));
        list = (LinkedList<question>) ois.readObject();
        ois.close();
        System.out.println("Preguntas cargadas correctamente");
        return list;
    }
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
