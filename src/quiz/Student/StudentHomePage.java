package quiz.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import quiz.UserDetails;


public class StudentHomePage {

    @FXML
    public void GiveAQuiz(ActionEvent actionEvent) throws Exception{
        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        Parent studentQuizPage = FXMLLoader.load(getClass().getResource("StartQuizStudent.fxml"));
        stage.setTitle("Start Quiz");
        stage.setScene(new Scene(studentQuizPage));
        stage.show();
    }

    @FXML
    public void AnalyseAQuiz(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        Parent studentQuizPage = FXMLLoader.load(getClass().getResource("ListQuizStudent.fxml"));
        stage.setTitle("Quiz Analysis");
        stage.setScene(new Scene(studentQuizPage));
        stage.show();
    }
}
