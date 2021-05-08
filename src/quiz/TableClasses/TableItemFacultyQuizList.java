package quiz.TableClasses;

public class TableItemFacultyQuizList {
    public String quizName;
    public String quizAccessCode;
    public String createdOn;

    public TableItemFacultyQuizList(String quizName, String quizAccessCode, String createdOn) {
        this.quizName = quizName;
        this.quizAccessCode = quizAccessCode;
        this.createdOn = createdOn;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getQuizAccessCode() {
        return quizAccessCode;
    }

    public String getCreatedOn() {
        return createdOn;
    }
}
