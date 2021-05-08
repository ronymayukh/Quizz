package quiz.Faculty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FacultyHomePage {

    @FXML
    public void CreateAQuiz(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        Parent createQuizPage = FXMLLoader.load(getClass().getResource("CreateQuizFaculty.fxml"));
        stage.setTitle("Create Quiz");
        stage.setScene(new Scene(createQuizPage));
        stage.show();
    }

    @FXML
    public void AnalyseAQuiz(ActionEvent actionEvent) throws Exception{
        Stage stage = new Stage();
        Parent listQuizPage = FXMLLoader.load(getClass().getResource("ListQuizFaculty.fxml"));
        stage.setTitle("Quiz List");
        stage.setScene(new Scene(listQuizPage));
        stage.show();
    }
}
