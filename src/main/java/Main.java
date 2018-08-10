import DB.Executor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import readfile.readfile;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    public ListView<String> list = new ListView<>();
    ComboBox combo = new ComboBox();
    public readfile foo2;
    public Executor exc ;
    public ResultSet Select ;



    public void setExec(Executor exc){
        this.exc=exc;
    }




    @Override
    public void start(Stage primaryStage) throws Exception{
        setExec(new Executor(new readfile().read(),"postgres","postgres"));
        Group root=new Group();
        Button btn= new Button("Добавить в справочник");
        btn.setLayoutX(0);
        btn.setLayoutY(0);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {
                try {
                    openWindows();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        root.getChildren().add(btn);
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void openWindows() throws SQLException {
        reloadBase();
        Group root=new Group();
        Button btn= new Button("Добавить");
        btn.setLayoutX(0);
        btn.setLayoutY(0);
        Stage secondStage = new Stage();
        TextField input = new TextField();
        input.setLayoutX(0);
        input.setLayoutY(50);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent t) {

                try {
                    exc.submit("INSERT INTO sch.base VALUES('"+input.getText()+"');");
                } catch (SQLException e) {

                }
                try {
                    reloadBase();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
        list.setLayoutX(0);
        list.setLayoutY(100);
        list.setPrefHeight(80);

        combo.setLayoutX(0);
        combo.setLayoutY(240);
        combo.setPrefWidth(200);
        root.getChildren().addAll(btn, input, list, combo);
        secondStage.setTitle("Hello World");
        secondStage.setScene(new Scene(root, 600, 475));
        secondStage.show();
    }


    public void reloadBase() throws SQLException {
        ObservableList<String> data_ = FXCollections.observableArrayList();
        ObservableList<String> data__ = FXCollections.observableArrayList();

        Select=exc.submit("SELECT * FROM sch.base;");
        try {
            while (Select.next()) {
                String out = Select.getString("code");
                System.out.println(out);
                data_.add(out);           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.setItems(data_);

        Select=exc.submit("SELECT * FROM sch.rel;");
        try {
            while (Select.next()) {
                String out = Select.getString("code");
                System.out.println(out);
                data__.add(out);           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        combo.setItems(data__);
    }

    public ObservableList<String> reloadRel() throws SQLException {
        ObservableList<String> data_ = FXCollections.observableArrayList();
        Select=exc.submit("SELECT * FROM sch.rel");
        try {
            while (Select.next()) {
                data_.add(Select.getString("code"));           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
       return data_;
    }

    public ObservableList<String> getDataList() throws SQLException {
        ObservableList<String> data_ = FXCollections.observableArrayList();
        Select=exc.submit("SELECT * FROM sch.rel");
        try {
            while (Select.next()) {
                data_.add(Select.getString("code"));           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        list.setItems(data_);
        return data_;
    }

}

