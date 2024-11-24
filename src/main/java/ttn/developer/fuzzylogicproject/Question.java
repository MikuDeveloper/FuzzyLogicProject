package ttn.developer.fuzzylogicproject;

import java.util.HashMap;

public class Question {
    private int id;
    private String question;
    private final HashMap<String, Integer> answers;

    public Question(int id, String question, HashMap<String, Integer> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public HashMap<String, Integer> getAnswers() {
        return answers;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }
}
