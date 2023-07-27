package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import ku.cs.models.Student;
import ku.cs.models.StudentList;
import ku.cs.services.FXRouter;
import ku.cs.services.StudentListFileDatasource;

import java.io.IOException;
import ku.cs.services.Datasource;
public class StudentListController {
    @FXML private ListView<Student> studentListView;
    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label scoreLabel;

    @FXML private Label errorLabel;
    @FXML private TextField giveScoreTextField;

    private StudentList studentList;
    private Student selectedStudent;
    private Datasource<StudentList> datasource;

    @FXML
    public void initialize() {
        errorLabel.setText("");
        clearStudentInfo();
        /* StudentHardCodeDatasource datasource = new StudentHardCodeDatasource(); */
        /* Datasource<StudentList> datasource = new StudentListHardCodeDatasource(); */
        /* Datasource<StudentList> datasource = new StudentListFileDatasource("data", "student-list.csv"); */
        datasource = new StudentListFileDatasource("data", "student-list.csv");
        studentList = datasource.readData();
        showList(studentList);
        /* ChangeListener เกิดเมื่อคลิกในลิสแล้วเปลี่ยนที่ลาเบล */
        studentListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Student>() {
            @Override
            public void changed(ObservableValue<? extends Student> observable, Student oldValue, Student newValue) {
                if (newValue == null) {
                    clearStudentInfo();
                    selectedStudent = null;
                } else {
                    showStudentInfo(newValue);
                    selectedStudent = newValue;
                }
                /* newValue คือตัวใหม่ที่ถูกเลือก อีกอันคืออันเก่า */
            }
        });
    }

    private void showList(StudentList studentList) {
        studentListView.getItems().clear();
        studentListView.getItems().addAll(studentList.getStudents());
    }

    private void showStudentInfo(Student student) {
        idLabel.setText(student.getId());
        nameLabel.setText(student.getName());
        scoreLabel.setText(String.format("%.2f", student.getScore()));
    }

    private void clearStudentInfo() {
        idLabel.setText("");
        nameLabel.setText("");
        scoreLabel.setText("");
    }

    @FXML
    public void onBackButtonClick() {
        try {
            FXRouter.goTo("hello");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void onGiveScoreButtonClick() {
        if (selectedStudent != null) {
            String scoreText = giveScoreTextField.getText();
            String errorMessage = "";
            try {
                double score = Double.parseDouble(scoreText);
                studentList.giveScoreToId(selectedStudent.getId(), score);
                showStudentInfo(selectedStudent);
                datasource.writeData(studentList);
                // แปลง scoreText เป็น double ถ้าแปลงไม่ได้ก็ใช้อันล่าง
                showList(studentList);
            } catch (NumberFormatException e) {
                errorMessage = "Please insert number value";
                errorLabel.setText(errorMessage);
            } finally {
                if (errorMessage.equals("")) {
                    giveScoreTextField.setText("");
                }
            }
        } else {
            giveScoreTextField.setText("");
            errorLabel.setText("");
        }
    }
}