package com.mohamed.utils;

import com.mohamed.question;

import java.io.*;
import java.util.LinkedList;

public class QuestionsFiles{
    private final static String FILENAME_DEF = "questions.moha";
    private final String FILENAME;


    public QuestionsFiles(String FILENAME) {
        this.FILENAME = FILENAME;
    }
    public QuestionsFiles(){
        this.FILENAME = FILENAME_DEF;
    }

    public void saveQuestions(LinkedList<question> list) throws IOException {
        System.out.println("guardando objeto");
        ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream(FILENAME));
        oos.writeObject(list);
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

}
