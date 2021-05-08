package quiz;

import java.sql.Timestamp;

public class QuizMetaData {
    Integer quizCode;
    String quizName;
    Integer duration;
    Timestamp validFrom;
    Timestamp validTill;

    public QuizMetaData(Integer quizCode, String quizName, Integer duration, Timestamp validFrom, Timestamp validTill) {
        this.quizCode = quizCode;
        this.quizName = quizName;
        this.duration = duration;
        this.validFrom = validFrom;
        this.validTill = validTill;
    }

    public Integer getQuizCode() {
        return quizCode;
    }

    public String getQuizName() {
        return quizName;
    }

    public Integer getDuration() {
        return duration;
    }

    public Timestamp getValidFrom() {
        return validFrom;
    }

    public Timestamp getValidTill() {
        return validTill;
    }
}
