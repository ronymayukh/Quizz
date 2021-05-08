package quiz.TableClasses;

public class TableItemFacultyQuizAnalysis {
    String studentName;
    Integer marks;

    public TableItemFacultyQuizAnalysis(String studentName, Integer marks) {
        this.studentName = studentName;
        this.marks = marks;
    }

    public String getStudentName() {
        return studentName;
    }

    public Integer getMarks() {
        return marks;
    }
}
