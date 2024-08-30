package edu.icet.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import edu.icet.model.Task;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainFormController implements Initializable {
    TaskController service = new TaskController();

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXListView<String> listView;

    @FXML
    private JFXTextArea TxtDescriptionView;

    @FXML
    private JFXButton btnCompleted;

    @FXML
    private JFXButton btnReload;

    @FXML
    private DatePicker dateView;

    @FXML
    private JFXListView<String> listViewC;

    @FXML
    private JFXTextField txtTitleView;

    private int selectedTaskId = -1; // Stores the actual task ID

    ObservableList<Task> taskList;
    ObservableList<String> titleList;
    ObservableList<Task> taskListCom;
    ObservableList<String> titleListCom;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadlist();
        loadlistCom();
        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                int selectedIndex = listView.getSelectionModel().getSelectedIndex();
                if (selectedIndex >= 0) {
                    Task selectedTask = taskList.get(selectedIndex);
                    txtTitleView.setText(selectedTask.getTitle());
                    TxtDescriptionView.setText(selectedTask.getDescription());
                    dateView.setValue(selectedTask.getDate());
                    selectedTaskId = selectedTask.getId(); // Store the task ID
                }
            }
        });
    }

    private void loadlist() {
        taskList = service.getAll();
        titleList = FXCollections.observableArrayList();
        for (Task task : taskList) {
            titleList.add(task.getTitle());
        }
        listView.setItems(titleList);
    }

    private void loadlistCom() {
        System.out.println("1");
        taskListCom = service.getAllCom();
        titleListCom = FXCollections.observableArrayList();
        System.out.println("2");
        for (Task task : taskListCom) {
            titleListCom.add(task.getTitle());
        }
        System.out.println("3");
        listViewC.setItems(titleListCom);
        System.out.println("4");
    }

    @FXML
    void btnAddOnAction(ActionEvent event) {
        Stage stage = new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/add_task.fxml"))));
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to load add task view.").show();
        }
    }

    @FXML
    void btnCompletedOnAction(ActionEvent event) {
        try {
            if (selectedTaskId == -1) {
                new Alert(Alert.AlertType.WARNING, "No task selected!").show();
                return;
            }

            Task updatedTask = new Task(selectedTaskId, txtTitleView.getText(), TxtDescriptionView.getText(), dateView.getValue());
            boolean success = true; // Assume this method marks the task as completed

            if (service.addTaskCom(updatedTask)) {
                try {
                    service.deleteTask(String.valueOf(selectedTaskId));
                } catch (Exception e) {
                    e.printStackTrace(); // Print stack trace for detailed error info
                }
                new Alert(Alert.AlertType.INFORMATION, "Task Completed!").show();
                clearFields();
                loadlist();
                loadlistCom();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to complete the task.").show();
            }

        } catch (Exception e) {
            //throw new RuntimeException(e);
            System.out.println("errrrr");
        }
    }

    @FXML
    void btnReloadOnAction(ActionEvent event) {
        loadlist();
        clearFields();
    }

    private void clearFields() {
        txtTitleView.setText(null);
        TxtDescriptionView.setText(null);
        dateView.setValue(null);
        selectedTaskId = -1;
    }
}
