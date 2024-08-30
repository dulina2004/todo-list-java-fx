package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

public class AddTaskFormController {

    @FXML
    private JFXButton btn;

    @FXML
    private DatePicker dateDue;

    @FXML
    private JFXTextArea txtDescription;

    @FXML
    private JFXTextField txtTitle;

    TaskController service=new TaskController();
    @FXML
    void btnAddOnAction(ActionEvent event) {
        Task task=new Task(txtTitle.getText(),txtDescription.getText(),dateDue.getValue());
        if(service.addTask(task)){
            new Alert(Alert.AlertType.INFORMATION,"Customer Added !!").show();
            txtTitle.setText(null);
            txtDescription.setText(null);
            dateDue.setValue(null);
        }else {
            new Alert(Alert.AlertType.ERROR,"Customer Not Added :(").show();
        }
    }

}
