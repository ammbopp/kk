package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.models.Student;
import ku.cs.services.FXRouter;

import java.io.IOException;

public class StudentController {
    @FXML
    Label nameLabel;
    @FXML
    Label idLabel;
    @FXML
    Label scoreLabel;

    @FXML
    public void initialize() {
        Student student = new Student("6410400001", "Tony Stark");
        showStudent(student);
    }

    private void showStudent(Student student) {
        nameLabel.setText(student.getName());
        idLabel.setText(student.getId());
        scoreLabel.setText(student.getScore());
    }
    @FXML
    public void onHandleBackButton(){
        try{
            FXRouter.goTo("hello");
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}