package quiz.Faculty;

import javafx.beans.value.ObservableStringValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import quiz.DBcalls;
import quiz.QuestionClass;


import java.util.ArrayList;

public class CreateQuizFaculty {

    @FXML
    DatePicker dtValidFrom;

    @FXML
    DatePicker dtValidTill;

    @FXML
    Spinner<Integer> hhValidFrom;

    @FXML
    Spinner<Integer> mmValidFrom;

    @FXML
    Spinner<Integer> hhValidTill;

    @FXML
    Spinner<Integer> mmValidTill;

    @FXML
    TextField quizName;

    @FXML
    ComboBox<Integer> quizTime;

    @FXML
    Button btnAddOneQuestion;

    @FXML
    Button btnCreateQuiz;

    @FXML
    VBox questionBox;


    ArrayList<TextArea> questions = new ArrayList<>();
    ArrayList<ArrayList<TextField>> options = new ArrayList<>();
    ArrayList<ArrayList<RadioButton>> optionsTick = new ArrayList<>();
    VBox tempBox;


    @FXML
    public void initialize(){
        quizTime.getItems().addAll(2,10,15,20,30,45,50,60);
        hhValidTill.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,13));
        hhValidFrom.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,13));
        mmValidTill.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,59,30));
        mmValidFrom.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,23,45));
        tempBox = addQuestion(questions,options,optionsTick);
        questionBox.getChildren().add(tempBox);
        questionBox.setSpacing(15);
        questionBox.setPadding(new Insets(10,10,10,10));
    }

    @FXML
    public  void  addOneQuestion(ActionEvent actionEvent){
        tempBox = addQuestion(questions,options,optionsTick);
        questionBox.getChildren().add(tempBox);
    }

    public static VBox addQuestion(ArrayList<TextArea> questions, ArrayList<ArrayList<TextField>> options, ArrayList<ArrayList<RadioButton>> optionsTick){
        TextArea taQ = new TextArea();
        taQ.setPrefColumnCount(65);
        taQ.setPrefRowCount(1);
        Label questionNumber = new Label(questions.size()+1+". ");

        ToggleGroup toggleGroup = new ToggleGroup();

        TextField tfAa = new TextField();
        RadioButton rBtnA = new RadioButton("A. ");
        rBtnA.requestFocus();
        rBtnA.setToggleGroup(toggleGroup);
        tfAa.setPrefColumnCount(25);

        TextField tfAb = new TextField();
        RadioButton rBtnB = new RadioButton("B. ");
        rBtnB.setToggleGroup(toggleGroup);
        tfAb.setPrefColumnCount(25);

        TextField tfAc = new TextField();
        RadioButton rBtnC = new RadioButton("C. ");
        rBtnC.setToggleGroup(toggleGroup);
        tfAc.setPrefColumnCount(25);

        TextField tfAd = new TextField();
        RadioButton rBtnD = new RadioButton("D. ");
        rBtnD.setToggleGroup(toggleGroup);
        tfAd.setPrefColumnCount(25);

        questions.add(taQ);
        ArrayList<TextField> answers = new ArrayList<>(4);
        answers.add(tfAa);
        answers.add(tfAb);
        answers.add(tfAc);
        answers.add(tfAd);
        options.add(answers);

        ArrayList<RadioButton> answersTick = new ArrayList<>(4);
        answersTick.add(rBtnA);
        answersTick.add(rBtnB);
        answersTick.add(rBtnC);
        answersTick.add(rBtnD);
        optionsTick.add(answersTick);


        VBox vbMain = new VBox();

        FlowPane questionPane = new FlowPane();
        questionPane.getChildren().addAll(questionNumber,taQ);


        HBox hb1 = new HBox();
        hb1.getChildren().addAll(rBtnA,tfAa);
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(rBtnB,tfAb);
        HBox hb3 = new HBox();
        hb3.getChildren().addAll(rBtnC,tfAc);
        HBox hb4 = new HBox();
        hb4.getChildren().addAll(rBtnD,tfAd);


        GridPane ansPane = new GridPane();
        ansPane.add(hb1,0,0);
        ansPane.add(hb2,0,1);
        ansPane.add(hb3,1,0);
        ansPane.add(hb4,1,1);
        ansPane.setHgap(15);
        ansPane.setVgap(15);

        vbMain.getChildren().addAll(questionPane,ansPane);
        vbMain.setSpacing(15);
        vbMain.setPadding(new Insets(10,10,10,10));
        return vbMain;

    }

    public Boolean checkInput(){
        int n = questions.size();
        for(int i=0;i<n;i++){
            ArrayList<RadioButton> rBtnList = optionsTick.get(i);
            if (!rBtnList.get(0).isSelected() && !rBtnList.get(1).isSelected() && !rBtnList.get(2).isSelected() && !rBtnList.get(3).isSelected()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please select a correct Option for question "+ (i+1));
                alert.show();
                return false;
            }
        }

        return true;
    }

    public ArrayList<QuestionClass> getInputQuestions(){
        ArrayList<QuestionClass> quizQuestions = new ArrayList<>();
        int n = questions.size();

        for (int i=0;i<n;i++){

            String currentQuestion = questions.get(i).getText();
            if(currentQuestion.length() == 0)
                continue;

            ArrayList<String> currentOptions = new ArrayList<>();
            ArrayList<TextField> currentOptionsInput = options.get(i);
            ArrayList<RadioButton> rBtnList = optionsTick.get(i);
            String currentAnswer = "";

            for (int j=0;j<4;j++){
                if(currentOptionsInput.get(j).getText().length() == 0){
                    break;
                }

                if(rBtnList.get(j).isSelected()){
                    currentAnswer += "ABCD".charAt(j);
                }
                currentOptions.add(currentOptionsInput.get(j).getText());
            }

            quizQuestions.add(new QuestionClass(currentQuestion,currentOptions,currentAnswer));
        }

        return  quizQuestions;


    }

    public String getTimeStamp(DatePicker dt, Spinner<Integer> hr, Spinner<Integer> mn){
        String date = dt.getValue().toString();
        String hour = hr.getValue()<10?"0"+hr.getValue():hr.getValue().toString();
        String minute = mn.getValue()<10?"0"+mn.getValue():mn.getValue().toString();
        return date+" "+hour+":"+minute+":00";
    }

    ArrayList<QuestionClass> quizQuestions;

    @FXML
    public void createQuiz(ActionEvent actionEvent) throws Exception{

        if(!checkInput()){
            return;
        }

        quizQuestions = getInputQuestions();

        Integer status = DBcalls.createQuiz(quizName.getText(),quizTime.getValue(),quizQuestions, getTimeStamp(dtValidFrom,hhValidFrom,mmValidFrom), getTimeStamp(dtValidTill,hhValidTill,mmValidTill));

        if (status == -1){
            Alert alert = new Alert(Alert.AlertType.ERROR,"Something went Wrong!");
            alert.show();
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION,"Your Quiz Access Code is: "+status,ButtonType.OK);
            alert.showAndWait();

            if(alert.getResult() == ButtonType.OK){
                Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
                stage.close();
            }
        }

    }
}
