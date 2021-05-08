package quiz.Faculty;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import quiz.DBcalls;

public class FacultyLogin {
    @FXML
    TextField tfEmpId;

    @FXML
    TextField tfPassword;

    @FXML
    Button btnLogin;

    public Boolean checkInputs(){

        if(tfEmpId.getText().isEmpty()){
            return false;
        }
        if(tfPassword.getText().isEmpty()){
            return false;
        }

        return true;
    }

    @FXML
    public void Login(ActionEvent actionEvent) throws Exception{
        if(checkInputs()){

            Integer response = DBcalls.FacultyLogin(tfEmpId.getText(),tfPassword.getText());

            if(response == 1){
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                Parent facultyHomePage = FXMLLoader.load(getClass().getResource("FacultyHomePage.fxml"));
                stage.setTitle("Faculty Home Page");
                stage.setScene(new Scene(facultyHomePage));
                stage.show();
            }else if(response == 0){
                Alert alert = new Alert(Alert.AlertType.ERROR,"No Such User Found!", ButtonType.OK);
                alert.show();

            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR,"Something went Wrong!");
                alert.show();
            }


        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR,"Please provide the Inputs properly!");
            alert.show();
        }
    }
}
