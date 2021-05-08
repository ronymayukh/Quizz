package quiz.Faculty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import quiz.DBcalls;
import quiz.TableClasses.TableItemFacultyQuizList;
import quiz.UserDetails;


public class ListQuizFaculty {

    @FXML
    TableView<TableItemFacultyQuizList> tableQuizList;

    @FXML
    TableColumn<TableItemFacultyQuizList,String> colQuizName;

    @FXML
    TableColumn<TableItemFacultyQuizList,String> colAccessCode;

    @FXML
    TableColumn<TableItemFacultyQuizList,String> colCreatedOn;

    ObservableList<TableItemFacultyQuizList> observableList = FXCollections.observableArrayList();


    public void initialize() throws Exception{
        DBcalls.fetchQuizListFaculty(observableList, UserDetails.USER_ID);

        colQuizName.setCellValueFactory(new PropertyValueFactory<TableItemFacultyQuizList,String>("quizName"));
        colAccessCode.setCellValueFactory(new PropertyValueFactory<TableItemFacultyQuizList,String>("quizAccessCode"));
        colCreatedOn.setCellValueFactory(new PropertyValueFactory<TableItemFacultyQuizList,String>("createdOn"));

        tableQuizList.setItems(observableList);

    }



    public void goToAnalysis(ActionEvent actionEvent) throws Exception {
        TableItemFacultyQuizList currentSelection = tableQuizList.getSelectionModel().getSelectedItem();

        if(currentSelection == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Select a Quiz to Analyse");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizAnalysisFaculty.fxml"));
        Parent root = loader.load();

        QuizAnalysisFaculty quizAnalysisFaculty = loader.getController();
        quizAnalysisFaculty.getQuizCode(currentSelection.getQuizAccessCode(), currentSelection.getQuizName());

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle(currentSelection.getQuizName());
        stage.show();
    }
}
