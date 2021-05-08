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

public class FacultyRegistration {
    @FXML
    TextField tfName;

    @FXML
    TextField tfEmpId;

    @FXML
    TextField tfPassword;

    @FXML
    Button btnRegistration;

    public Boolean checkInputs(){
        if(tfName.getText().isEmpty()){
            return false;
        }
        if(tfEmpId.getText().isEmpty()){
            return false;
        }
        if(tfPassword.getText().isEmpty()){
            return false;
        }

        return true;
    }

    public void Registration(ActionEvent actionEvent) throws Exception {
        if(checkInputs()){

            Integer response = DBcalls.FacultyRegistration(tfEmpId.getText(),tfName.getText(),tfPassword.getText());

            if(response == 1){
                Alert alert = new Alert(Alert.AlertType.INFORMATION,"Congratulations!! You have been registered Successfully", ButtonType.OK);
                alert.showAndWait();

                if(alert.getResult() == ButtonType.OK){
                    Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                    Parent studentLoginPage = FXMLLoader.load(getClass().getResource("FacultyLogin.fxml"));
                    stage.setTitle("Faculty Login");
                    stage.setScene(new Scene(studentLoginPage));
                    stage.show();
                }
            }else if(response == 2){
                Alert alert = new Alert(Alert.AlertType.ERROR,"You are already registered, please try to login!", ButtonType.OK);
                alert.showAndWait();

                if(alert.getResult() == ButtonType.OK){
                    Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                    Parent studentLoginPage = FXMLLoader.load(getClass().getResource("FacultyLogin.fxml"));
                    stage.setTitle("Faculty Login");
                    stage.setScene(new Scene(studentLoginPage));
                    stage.show();
                }
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
