package com.mohamed.domain;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

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
     * This constructor should be used to only create "TEST" questions
     * @param type {@code String} The type of the question, it should be "TEST".
     * @param category {@code String} The category of the question.
     * @param question {@code String} the question statement.
     * @param correctAnswer {@code String} the first answer.
     * @param answer1 {@code String} the second answer.
     * @param answer2 {@code String} the third answer.
     */
    public question(String type, String category, String question, String correctAnswer, String answer1, String answer2) {
        this.type = type;
        this.category = category;
        this.question = question;
        this.answer = correctAnswer;
        this.answer1 = answer1;
        this.answer2 = answer2;
        this.isTest = true;
    }
    /**
     * This constructor creates the {@code Object question} with the following parameters.
     * This constructor should be used to only create "SHORT" questions
     * @param type {@code String} The type of the question, it should be "TEST".
     * @param category {@code String} The category of the question.
     * @param question {@code String} the question statement.
     * @param correctAnswer {@code String} the first answer.
     */
    public question(String type, String category, String question, String correctAnswer){
        this.type = type;
        this.category = category;
        this.question = question;
        this.answer = correctAnswer.trim();
    }


    public String getQuestion() {
        return question;
    }
    public String getType() {
        return type;
    }
    public String getAnswer() {
        return answer;
    }
    public String getAnswer1() {
        return answer1;
    }
    public String getAnswer2() {
        return answer2;
    }
    public String getCategory() {
        return category;
    }
    public boolean isTest(){
        return isTest;
    }
    public boolean repond(String answer){
        return this.answer.equals(answer);
    }

    /**
     * This method is used to get the answers of a question.
     * @return {@code ArrayList} with the answers.
     */
    public ArrayList<String> getAnswers(){
        ArrayList<String> answers = new ArrayList<>();
        answers.add(answer);
        if (!isTest){
            return answers;
        }
        answers.add(answer1);
        answers.add(answer2);
        return answers;
    };
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
