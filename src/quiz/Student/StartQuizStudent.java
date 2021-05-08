package quiz.Student;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import quiz.DBcalls;
import quiz.QuizMetaData;
import quiz.UserDetails;

import java.sql.Timestamp;
import java.util.Date;


public class StartQuizStudent {

    @FXML
    TextField tfQuizCode;

    public Boolean checkQuizValidity(QuizMetaData quizMetaData) throws Exception {
        Date date = new Date();
        if(quizMetaData.getValidFrom().after(new Timestamp(date.getTime())) || quizMetaData.getValidTill().before(new Timestamp(date.getTime()))){
            return false;
        }

        if(DBcalls.checkAttempt(UserDetails.USER_ID,quizMetaData.getQuizCode())){
            return false;
        }

        return true;
    }

    @FXML
    public void Start(ActionEvent actionEvent) throws Exception{
        Integer quizCode = Integer.valueOf(tfQuizCode.getText());

        QuizMetaData quizMetaData = DBcalls.fetchQuizMetaData(quizCode);

        if(quizMetaData == null){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please check with your faculty on the Quiz Code you entered");
            alert.show();
        }else{

            if(checkQuizValidity(quizMetaData)){
                UserDetails.currentQuizMetaData = quizMetaData;
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Parent studentQuizPage = FXMLLoader.load(getClass().getResource("QuizPageStudent.fxml"));
                stage.setTitle(quizMetaData.getQuizName());
                stage.setScene(new Scene(studentQuizPage));
                stage.show();

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"You are not eligible to give this Quiz");
                alert.show();
            }

        }
    }
}
