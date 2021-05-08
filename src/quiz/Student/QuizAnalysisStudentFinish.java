package quiz.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import quiz.DBcalls;
import quiz.UserDetails;

import java.util.ArrayList;
import java.util.Vector;

public class QuizAnalysisStudentFinish {

    Integer correctAnswer = 0;
    Integer wrongAnswer = 0;
    Integer notAttemptedAnswer = 0;
    Integer totalAnswers = 0;

    @FXML
    PieChart pieChartScore;
    @FXML
    Label labelReport;


    public void finishTest(ActionEvent actionEvent){
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }


    public void transferData(Integer quizCode) throws Exception{

        ArrayList<Integer> report = DBcalls.getStudentQuizReport(quizCode, UserDetails.USER_ID);

        correctAnswer = report.get(0);
        wrongAnswer = report.get(1);
        notAttemptedAnswer = report.get(2);

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Wrong Attempt", wrongAnswer),
                new PieChart.Data("Not Attempted", notAttemptedAnswer),
                new PieChart.Data("Correct Attempt", correctAnswer));
        pieChartScore.setData(pieChartData);


        labelReport.setText("TOTAL QUESTIONS: "+totalAnswers+"\n\nCORRECT ANSWERS: "+correctAnswer+"\n\nWRONG ANSWERS: "+wrongAnswer+"\n\nNOT ATTEMPTED: "+notAttemptedAnswer);

    }
}
