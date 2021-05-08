package quiz.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quiz.DBcalls;
import quiz.QuestionClass;
import quiz.QuizMetaData;
import quiz.UserDetails;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QuizPageStudent {

    QuizMetaData quizMetaData;
    ArrayList<QuestionClass> quizQuestions;
    Timer timer;
    ArrayList<ArrayList<RadioButton>> ansInput = new ArrayList<>();
    Integer time;
    Integer notAttempted;
    Integer wrongAttempt;

    @FXML
    VBox vBoxQuestions;

    @FXML
    Label timerLabel;

    @FXML
    public void initialize() throws Exception{

        this.quizMetaData = UserDetails.currentQuizMetaData;
        quizQuestions = DBcalls.fetchQuiz(quizMetaData.getQuizCode());
        time = quizMetaData.getDuration()*60;

        for(int i=0;i<quizQuestions.size();i++){
            VBox vb = makeQuestion(quizQuestions.get(i).getQuestion(),quizQuestions.get(i).getOptions(),ansInput,i);
            vBoxQuestions.getChildren().add(vb);
        }
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Integer min = time/60;
                Integer sec = time%60;
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        if(time == 0){
                            timer.cancel();
                            timerLabel.setText("TIME UP!!");
                            try {
                                finishTestConfirmed();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else{
                            timerLabel.setText("TIME: "+convert(min)+" : "+convert(sec));
                        }
                    }
                });
                time--;

            }
        };

        timer.schedule(timerTask,0,1000);

    }



    VBox makeQuestion(String questionText, ArrayList<String> options, ArrayList<ArrayList<RadioButton>> ansInput, Integer questionNumber){

        ArrayList<Integer> temp1 = new ArrayList<>();
        temp1.add(0);
        temp1.add(0);
        ArrayList<Integer> temp2 = new ArrayList<>();
        temp2.add(0);
        temp2.add(1);
        ArrayList<Integer> temp3 = new ArrayList<>();
        temp3.add(1);
        temp3.add(0);
        ArrayList<Integer> temp4 = new ArrayList<>();
        temp4.add(1);
        temp4.add(1);

        ArrayList<ArrayList<Integer>> pos = new ArrayList<>();
        pos.add(temp1);
        pos.add(temp2);
        pos.add(temp3);
        pos.add(temp4);


        HBox question = new HBox();
        questionNumber++;
        question.getChildren().addAll(new Text(questionNumber+". "),new Text(questionText));

        GridPane ans = new GridPane();
        ans.setVgap(15);
        ans.setHgap(15);
        ans.setPadding(new Insets(10,10,10,10));
        ArrayList<RadioButton> tempRadio = new ArrayList<>();
        ToggleGroup tg = new ToggleGroup();
        for(int i=0;i<options.size();i++){
            RadioButton radioButton = new RadioButton("ABCD".charAt(i)+". "+options.get(i));
            radioButton.setToggleGroup(tg);
            tempRadio.add(radioButton);
            ans.add(radioButton,pos.get(i).get(0), pos.get(i).get(1));
        }

        ansInput.add(tempRadio);

        VBox vBox = new VBox();
        vBox.getChildren().addAll(question,ans);
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(10,10,10,10));

        return vBox;
    }


    public String convert(Integer n) {
        String s = String.valueOf(n);
        if (s.length() == 1)
            s = "0" + s;
        return s;
    }



    @FXML
    public void finishTest(ActionEvent actionEvent) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"Do you want to End the test", ButtonType.YES, ButtonType.NO);
        alert.showAndWait();

        if(alert.getResult() == ButtonType.YES){
            timer.cancel();
            finishTestConfirmed();
        }
    }

    public void finishTestConfirmed() throws Exception {
        ArrayList<String> responses = getResponses(ansInput);
        Integer rightAttempt = getScore(responses);

        DBcalls.addStudentResponse(quizMetaData.getQuizCode(), UserDetails.USER_ID, rightAttempt, wrongAttempt, notAttempted);



        FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizAnalysisStudentFinish.fxml"));
        Parent root = loader.load();

       QuizAnalysisStudentFinish quizAnalysisStudentFinish = loader.getController();
        quizAnalysisStudentFinish.transferData(quizMetaData.getQuizCode());

        Stage stage = (Stage) timerLabel.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("QUIZ REPORT");
        stage.show();

    }

    public ArrayList<String> getResponses(ArrayList<ArrayList<RadioButton>> ansInput) {
        notAttempted = 0;
        ArrayList<String> responses = new ArrayList<>();
        boolean attempted;
        for(int i=0;i<ansInput.size();i++){
            attempted = false;
            ArrayList<RadioButton> radioButtons = ansInput.get(i);
            for(int j=0;j<radioButtons.size();j++){
                if(radioButtons.get(j).isSelected()){
                    responses.add(""+"ABCD".charAt(j));
                    attempted = true;
                }
            }

            if(!attempted){
                notAttempted++;
                responses.add("X");
            }

        }

        return responses;
    }

    public Integer getScore(ArrayList<String> responses) {
        wrongAttempt = 0;
        Integer correct = 0;
        for(int i=0;i<responses.size();i++){
            if(responses.get(i).equals(quizQuestions.get(i).getCorrectOption())){
                correct++;
            }else if(!responses.get(i).equals("X")){
                wrongAttempt++;
            }
        }

        return correct;
    }


}
