package quiz.TableClasses;

public class TableItemStudentQuizList {
    String quizCode;
    String quizName;
    String marksObtained;

    public TableItemStudentQuizList(String quizName, String marksObtained, String quizCode) {
        this.quizName = quizName;
        this.marksObtained = marksObtained;
        this.quizCode = quizCode;
    }

    public String getQuizName() {
        return quizName;
    }

    public String getMarksObtained() {
        return marksObtained;
    }

    public String getQuizCode(){
        return quizCode;
    }
}
