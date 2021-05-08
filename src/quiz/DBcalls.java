package quiz;

import javafx.collections.ObservableList;
import quiz.TableClasses.TableItemFacultyQuizAnalysis;
import quiz.TableClasses.TableItemFacultyQuizList;
import quiz.TableClasses.TableItemStudentQuizList;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class DBcalls {

    public static Integer StudentRegistration(String regNo, String name, String password) throws Exception{

        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "SELECT COUNT(*) FROM students WHERE RegNo = '"+regNo+"'";
        ResultSet rs = statement.executeQuery(sql);
        rs.next();

       if(rs.getInt(1) != 0){
           myConnection.close();
           return 2;
       }

        sql = "INSERT INTO students (RegNo, Name, Password) VALUES ('"+regNo+"', '"+name+"', '"+password+"')";

        if(statement.executeUpdate(sql) == 1){
            myConnection.close();
            return 1;
        }

        myConnection.close();
        return -1;

    }

    public static Integer StudentLogin(String regNo, String password) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "SELECT COUNT(*) FROM students WHERE RegNo = '"+regNo+"' AND Password = '"+password+"'";
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        int count = rs.getInt(1);
        myConnection.close();

        if(count == 1){
            UserDetails.USER_ID = regNo;
            return 1;
        }else if(count == 0){
            return 0;
        }

        return -1;
    }

    public static Integer FacultyRegistration(String empId, String name, String password) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "SELECT COUNT(*) FROM faculty WHERE EmpId = '"+empId+"'";
        ResultSet rs = statement.executeQuery(sql);
        rs.next();

        if(rs.getInt(1) != 0){
            myConnection.close();
            return 2;
        }

        sql = "INSERT INTO faculty (EmpId, Name, Password) VALUES ('"+empId+"', '"+name+"', '"+password+"')";

        if(statement.executeUpdate(sql) == 1){
            myConnection.close();
            return 1;
        }

        myConnection.close();
        return -1;

    }

    public static Integer FacultyLogin(String empId, String password) throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "SELECT COUNT(*) FROM faculty WHERE EmpId = '"+empId+"' AND Password = '"+password+"'";
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        int count = rs.getInt(1);
        myConnection.close();

        if(count == 1){
            UserDetails.USER_ID = empId;
            return 1;
        }else if(count == 0){
            return 0;
        }

        return -1;
    }

    public static Integer createQuiz(String quizName, Integer duration, ArrayList<QuestionClass> quizQuestions, String validFrom, String validTill) throws Exception {
        String currentTimeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "INSERT INTO quizmetadata (QuizName, EmpId, Duration, CreatedAt, ValidFrom, ValidTill) VALUES ('"+quizName+"', '"+UserDetails.USER_ID+"', "+duration+",'"+currentTimeStamp+"', '"+validFrom+"', '"+validTill+"')";

        if(statement.executeUpdate(sql) != 1){
            myConnection.close();
            return -1;
        }

        sql = "SELECT QuizId FROM quizmetadata WHERE EmpId = '"+UserDetails.USER_ID+"' AND CreatedAt = '"+currentTimeStamp+"'";
        ResultSet rs = statement.executeQuery(sql);
        rs.next();

        String QuizCode = rs.getString(1);

        sql = "INSERT INTO quizdata (QuizId, Question, Options, Answer) VALUES ("+QuizCode+", ?, ?,?)";

        PreparedStatement query = myConnection.prepareStatement(sql);

        for (int i=0;i<quizQuestions.size();i++){
            query.setString(1,quizQuestions.get(i).getQuestion());
            query.setString(2,quizQuestions.get(i).getOptions().toString());
            query.setString(3,quizQuestions.get(i).getCorrectOption());

            query.executeUpdate();
        }

        return Integer.valueOf(QuizCode);

    }

    public static QuizMetaData fetchQuizMetaData(Integer quizCode) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "SELECT * FROM quizmetadata WHERE QuizId = "+quizCode;
        ResultSet rs = statement.executeQuery(sql);

        if(!rs.next()){
            return null;
        }

        String quizName = rs.getString(2);
        Integer duration = rs.getInt(4);
        Timestamp validFrom = rs.getTimestamp(6);
        Timestamp validTill = rs.getTimestamp(7);

        return new QuizMetaData(quizCode,quizName,duration,validFrom,validTill);

    }

    public static boolean checkAttempt(String regNo, Integer quizCode) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "SELECT COUNT(*) FROM response WHERE StudentRegNo = '"+regNo+"' AND QuizId = "+quizCode;
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        int count = rs.getInt(1);
        myConnection.close();

        if(count == 1){
            return true;
        }

        return false;
    }

    public static ArrayList<QuestionClass> fetchQuiz(Integer quizCode) throws Exception{
        ArrayList<QuestionClass> quizQuestions = new ArrayList<>();

        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "SELECT * FROM quizdata WHERE QuizId = "+quizCode;
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            String currentQuestion = rs.getString(3);
            String str = rs.getString(4);
            str = str.substring(1,str.length()-1);
            String[] options = str.split(", ");
            ArrayList<String> currentOptions = new ArrayList<String>(Arrays.asList(options));
            String currentAns = rs.getString(5);

            quizQuestions.add(new QuestionClass(currentQuestion,currentOptions,currentAns));
        }

        return quizQuestions;

    }

    public static void addStudentResponse(Integer quizCode, String userId, Integer rightAttempt, Integer wrongAttempt, Integer notAttempted) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "INSERT INTO response (QuizId, StudentRegNo, MarksObtained, CorrectAttempt, WrongAttempt, NotAttempt) VALUES ("+quizCode+", '"+userId+"',"+rightAttempt+", "+rightAttempt+", "+wrongAttempt+", "+notAttempted+")";
        statement.executeUpdate(sql);
    }

    public static ArrayList<Integer> getStudentQuizReport(Integer quizCode, String regNo) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "SELECT * FROM response WHERE QuizId = "+quizCode+" AND StudentRegNo = '"+regNo+"'";
        ResultSet rs = statement.executeQuery(sql);

        rs.next();

        ArrayList<Integer> report = new ArrayList<>();

        report.add(rs.getInt(5));
        report.add(rs.getInt(6));
        report.add(rs.getInt(7));

        return report;

    }

    public static void fetchQuizListFaculty(ObservableList<TableItemFacultyQuizList> observableList, String employeeId) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql = "SELECT * FROM quizmetadata WHERE EmpId = '"+employeeId+"' ORDER BY CreatedAt DESC";
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            String quizName = rs.getString(2);
            String quizCode = rs.getString(1);

            Timestamp timestamp = rs.getTimestamp(5);
            Date dt = new Date(timestamp.getTime());
            String createdOn = dt.toString();

            observableList.add(new TableItemFacultyQuizList(quizName,quizCode,createdOn));

        }

    }

    public static ArrayList<Integer> fetchQuizReport(String quizCode, ObservableList<TableItemFacultyQuizAnalysis> observableList) throws Exception {

        ArrayList<Integer> listOfMarks = new ArrayList<>();

        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();
        String sql1 = "SELECT StudentRegNo FROM response WHERE QuizId = "+quizCode;
        ResultSet rs1 = statement.executeQuery(sql1);

        String sql2 = "SELECT MarksObtained FROM response WHERE QuizId = "+quizCode+" AND StudentRegNo = ?";
        String sql3 = "SELECT Name FROM students WHERE RegNo = ?";

        PreparedStatement queryMarks = myConnection.prepareStatement(sql2);
        PreparedStatement queryName = myConnection.prepareStatement(sql3);

        while (rs1.next()){
            String regNo = rs1.getString(1);
            queryMarks.setString(1,regNo);
            queryName.setString(1,regNo);

            ResultSet rs2 = queryMarks.executeQuery();
            ResultSet rs3 = queryName.executeQuery();
            rs2.next();
            Integer marks = rs2.getInt(1);
            rs3.next();
            String name = rs3.getString(1);

            listOfMarks.add(marks);
            observableList.add(new TableItemFacultyQuizAnalysis(name,marks));

        }

        return listOfMarks;
    }

    public static void fetchQuizListStudent(ObservableList<TableItemStudentQuizList> observableList, String regNo) throws Exception{

        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/quizplatform_java_da1";
        String user = "root";
        String pass = "";
        Connection myConnection = DriverManager.getConnection(url,user,pass);
        Statement statement = myConnection.createStatement();

        String sql1 = "SELECT * FROM response WHERE StudentRegNo = '"+regNo+"'";
        String sql2 = "SELECT QuizName FROM quizmetadata WHERE QuizId = ?";

        ResultSet rs1 = statement.executeQuery(sql1);
        PreparedStatement preparedStatement = myConnection.prepareStatement(sql2);

        while (rs1.next()){
            String quizCode = rs1.getString(2);
            String marks = rs1.getString(4);

            preparedStatement.setString(1,quizCode);
            ResultSet rs2 = preparedStatement.executeQuery();
            rs2.next();

            String quizName = rs2.getString(1);

            observableList.add(new TableItemStudentQuizList(quizName,marks,quizCode));

        }

    }
}
