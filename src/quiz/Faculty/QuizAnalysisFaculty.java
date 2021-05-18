package quiz.Faculty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import quiz.DBcalls;
import quiz.TableClasses.TableItemFacultyQuizAnalysis;

import java.util.*;

public class QuizAnalysisFaculty {

    @FXML
    LineChart<Integer,Integer> lineGraph;

    @FXML
    Label labelQuizDetails;

    @FXML
    Label labelQuizSummary;


    @FXML
    ScrollPane scrollPane;

    @FXML
    TableView<TableItemFacultyQuizAnalysis> tableStudentMarks;

    @FXML
    TableColumn<TableItemFacultyQuizAnalysis,String> colStudentName;

    @FXML
    TableColumn<TableItemFacultyQuizAnalysis,Integer> colStudentNumber;


    ObservableList<TableItemFacultyQuizAnalysis> observableList = FXCollections.observableArrayList();



    String quizCode;
    String quizName;
    ArrayList<Integer> marksObtained;
    Float averageMarks = 0f;
    Integer maxMarks = Integer.MIN_VALUE;
    Integer minMarks = Integer.MAX_VALUE;


    public void getQuizCode(String quizCode, String quizName) throws Exception{
        this.quizCode = quizCode;
        this.quizName = quizName;
        labelQuizDetails.setText("ANALYSIS - "+quizName);
        marksObtained = DBcalls.fetchQuizReport(quizCode,observableList);


        displayDetails();
    }


    public void displayDetails(){

        colStudentName.setCellValueFactory(new PropertyValueFactory<TableItemFacultyQuizAnalysis,String>("studentName"));
        colStudentNumber.setCellValueFactory(new PropertyValueFactory<TableItemFacultyQuizAnalysis,Integer>("marks"));
        tableStudentMarks.setItems(observableList);

        Integer totalStudent = marksObtained.size();

        ArrayList<Map.Entry<Integer,Integer>> frequencyArray = processData(marksObtained);

        XYChart.Series series = new XYChart.Series();
        series.setName("Marks Frequency");

        for (Map.Entry<Integer, Integer> entry : frequencyArray)
        {
            Integer frequency = entry.getValue();
            String marks = entry.getKey()+"";

            series.getData().add(new XYChart.Data(marks, frequency));

        }

        lineGraph.getData().add(series);
        lineGraph.setTitle("Class Performance");
        lineGraph.setLayoutX(1);

        averageMarks /= totalStudent;



       String str = "ATTENDANCE : "+totalStudent+"\n\n";
        str += "AVERAGE MARKS : "+averageMarks+"\n\n";
        str += "MAXIMUM MARKS : "+maxMarks+"\n\n";
        str += "MINIMUM MARKS : "+minMarks;

        labelQuizSummary.setText(str);


    }

    private ArrayList<Map.Entry<Integer, Integer>> processData(ArrayList<Integer> marksObtained) {

        Map<Integer, Integer> elementCountMap = new LinkedHashMap<>();

        for (int i = 0; i < marksObtained.size(); i++)
        {

            averageMarks += marksObtained.get(i);

            maxMarks = maxMarks<marksObtained.get(i)?marksObtained.get(i):maxMarks;
            minMarks = minMarks>marksObtained.get(i)?marksObtained.get(i):minMarks;

            if (elementCountMap.containsKey(marksObtained.get(i)))
            {
                elementCountMap.put(marksObtained.get(i), elementCountMap.get(marksObtained.get(i))+1);
            }
            else
            {
                elementCountMap.put(marksObtained.get(i), 1);
            }
        }

        ArrayList<Map.Entry<Integer, Integer>> listOfEntry = new ArrayList<>(elementCountMap.entrySet());
        Collections.sort(listOfEntry, new Comparator<Map.Entry<Integer, Integer>>()
        {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2)
            {
                return o1.getKey().compareTo(o2.getKey());
            }
        });

        return listOfEntry;

    }


}
