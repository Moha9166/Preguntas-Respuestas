package com.mohamed;

import java.io.Serial;
import java.io.Serializable;

public class question implements Serializable {
    @Serial
    private static final long serialVersionUID = -2802693203686390473L;
    private final String id;
    private final String question;
    private final String answer;
    private String answer1;
    private String answer2;
    private boolean isTest = false;

    public question(String id, String question, String answer, String answer1, String answer2) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.isTest = true;
    }

    public question(String id, String question, String answer){
        this.id = id;
        this.question = question;
        this.answer = answer;
    }

    public boolean repond(String answer){
        return this.answer.equals(answer);
    }

    @Override
    public String toString() {
        return "question{" +
                "id='" + id + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                '}';
    }

    public boolean isTest(){
        return isTest;
    }
}
