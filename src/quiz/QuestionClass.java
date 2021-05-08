package quiz;

import java.util.ArrayList;

public class QuestionClass {
    String question;
    ArrayList<String> options;
    String correctOption;

    public QuestionClass(String question, ArrayList<String> options, String correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public String getCorrectOption() {
        return correctOption;
    }
}
