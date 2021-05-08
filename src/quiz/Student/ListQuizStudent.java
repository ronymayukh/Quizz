package quiz.Student;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import quiz.DBcalls;
import quiz.Faculty.QuizAnalysisFaculty;
import quiz.TableClasses.TableItemFacultyQuizList;
import quiz.TableClasses.TableItemStudentQuizList;
import quiz.UserDetails;

public class ListQuizStudent {

    @FXML
    TableView<TableItemStudentQuizList> tableQuizStudent;

    @FXML
    TableColumn<TableItemStudentQuizList,String> colQuizName;

    @FXML
    TableColumn<TableItemStudentQuizList,String> colMarksObtained;

    ObservableList<TableItemStudentQuizList> observableList = FXCollections.observableArrayList();

    public void initialize() throws Exception{
        DBcalls.fetchQuizListStudent(observableList, UserDetails.USER_ID);

        colQuizName.setCellValueFactory(new PropertyValueFactory<TableItemStudentQuizList,String>("quizName"));
        colMarksObtained.setCellValueFactory(new PropertyValueFactory<TableItemStudentQuizList,String>("marksObtained"));

        tableQuizStudent.setItems(observableList);

    }

    public void goToAnalysis(ActionEvent actionEvent) throws Exception {
        TableItemStudentQuizList currentSelection = tableQuizStudent.getSelectionModel().getSelectedItem();

        if(currentSelection == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a Quiz to Analyse");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizAnalysisStudent.fxml"));
        Parent root = loader.load();

        QuizAnalysisStudent quizAnalysisStudent = loader.getController();
        quizAnalysisStudent.sendQuizCode(currentSelection.getQuizCode());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(currentSelection.getQuizName());
        stage.show();
    }
}
