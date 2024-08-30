package edu.icet.controller;

import edu.icet.db.DBConnection;
import edu.icet.model.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.sql.*;

public class TaskController {
    public boolean addTask(Task task) {
        try {
            String SQL = "INSERT INTO active_tasks (title, description, completion_date) VALUES (?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, task.getTitle());
            psTm.setObject(2, task.getDescription());
            psTm.setObject(3, task.getDate());
            //psTm.setDate(4, Date.valueOf(customer.getDob()));
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Error : "+e.getMessage()).show();
        }
        return false;
    }

    public ObservableList<Task> getAll() {
        ObservableList<Task> customerObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM active_tasks";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
//                System.out.println(resultSet.getString("CustTitle") + resultSet.getString("CustName"));
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("completion_date").toLocalDate()
                );
                customerObservableList.add(task);
                System.out.println(task);
            }
            return customerObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public boolean deleteTask(String id) {
        String SQL = "DELETE FROM active_tasks WHERE id = ?";
        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement psTm = connection.prepareStatement(SQL)) {

            psTm.setString(1, id); // Set the id parameter safely

            boolean bool=psTm.executeUpdate() > 0;
            System.out.println("ddddddddddddddddddddddddddddddddddddddd");
            return bool;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addTaskCom(Task task) {
        try {
            String SQL = "INSERT INTO completed_tasks (id,title, description, completion_date) VALUES (?,?,?,?)";
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, task.getId());
            psTm.setObject(2, task.getTitle());
            psTm.setObject(3, task.getDescription());
            psTm.setObject(4, task.getDate());
            //psTm.setDate(4, Date.valueOf(customer.getDob()));
            return psTm.executeUpdate() > 0;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,"Error : "+e.getMessage()).show();
        }
        return false;
    }

    public ObservableList<Task> getAllCom() {
        ObservableList<Task> customerObservableList = FXCollections.observableArrayList();
        try {
            String SQL = "SELECT * FROM completed_tasks";
            Connection connection = DBConnection.getInstance().getConnection();
            System.out.println(connection);
            PreparedStatement psTm = connection.prepareStatement(SQL);
            ResultSet resultSet = psTm.executeQuery();
            while (resultSet.next()) {
//                System.out.println(resultSet.getString("CustTitle") + resultSet.getString("CustName"));
                Task task = new Task(
                        resultSet.getInt("id"),
                        resultSet.getString("title"),
                        resultSet.getString("description"),
                        resultSet.getDate("completion_date").toLocalDate()
                );
                customerObservableList.add(task);
                System.out.println(task);
            }
            return customerObservableList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
