package quiz;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.RadioButton;
import javafx.stage.Stage;

import java.io.IOException;


public class WelcomePage {

    @FXML
    private RadioButton rBtnStudent;

    @FXML
    private RadioButton rBtnFaculty;



    @FXML
    private void Login(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        if(rBtnStudent.isSelected()){
            Parent studentLoginPage = FXMLLoader.load(getClass().getResource("Student/StudentLogin.fxml"));
            stage.setTitle("Student Login");
            stage.setScene(new Scene(studentLoginPage));
            stage.show();

        }

        if(rBtnFaculty.isSelected()){
            Parent facultyLoginPage = FXMLLoader.load(getClass().getResource("Faculty/FacultyLogin.fxml"));
            stage.setTitle("Faculty Login");
            stage.setScene(new Scene(facultyLoginPage));
            stage.show();
        }
    }

    @FXML
    private void Register(ActionEvent event) throws IOException {

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();

        if(rBtnStudent.isSelected()){
            Parent studentRegistrationPage = FXMLLoader.load(getClass().getResource("Student/StudentRegistration.fxml"));
            stage.setTitle("Student Registration");
            stage.setScene(new Scene(studentRegistrationPage));
            stage.show();

        }

        if(rBtnFaculty.isSelected()){
            Parent facultyRegistrationPage = FXMLLoader.load(getClass().getResource("Faculty/FacultyRegistration.fxml"));
            stage.setTitle("Faculty Registration");
            stage.setScene(new Scene(facultyRegistrationPage));
            stage.show();
        }
    }

}
