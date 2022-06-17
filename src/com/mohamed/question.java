package com.mohamed;

import java.io.Serial;
import java.io.Serializable;

/**
 * This class is used to create a {@code Object question}
 * @author Mohamed Yoube Bahmed.
 * @see java.io.Serializable
 */
public class question implements Serializable {
    @Serial
    private static final long serialVersionUID = -2802693203686390473L;
    private final String type;
    private final String question;
    private final String answer;
    private String answer1;
    private String answer2;
    private String category;
    private boolean isTest = false;

    /**
     * This constructor creates the {@code Object question} with the following parameters.
     * @param type {@code String}
     * @param category {@code String}
     * @param question {@code String}
     * @param answer {@code String}
     * @param answer1 {@code String}
     * @param answer2 {@code String}
     */
    public question(String type, String category, String question, String answer, String answer1, String answer2) {
        this.type = type;
        this.category = category;
        this.question = question;
        this.answer = answer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.isTest = true;
    }

    public question(String type, String category, String question, String answer){
        this.type = type;
        this.category = category;
        this.question = question;
        this.answer = answer;
    }

    public boolean isTest(){
        return isTest;
    }
    public boolean repond(String answer){
        return this.answer.equals(answer);
    }

    public String getQuestion() {
        return question;
    }

    @Override
    public String toString() {
        return "question{" +
                "type='" + type + '\'' +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", answer1='" + answer1 + '\'' +
                ", answer2='" + answer2 + '\'' +
                ", category='" + category + '\'' +
                ", isTest=" + isTest +
                '}';
    }
}
