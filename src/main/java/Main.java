import DB.Executor;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import readfile.readfile;
import model.model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Main extends Application {
    public ListView<String> list = new ListView<>();
    private TableView<model> tableMaterial = new TableView<model>();
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
        tableMaterial.getColumns().clear();
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

        TextField tf = new TextField();
        tf.setLayoutX(0);
        tf.setLayoutY(300);

        Button apply = new Button("apply");
        apply.setLayoutX(0);
        apply.setLayoutY(350);
        apply.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    exc.submit("UPDATE sch.rel SET description='"+ tf.getText()+" ' where code='"+ combo.getValue()+"'");
                } catch (SQLException e) {

                }
                try {
                    tableMaterial.setItems(getDatamodel());
                } catch (SQLException e) {

                }
            }
        });

        tableMaterial.setEditable(false);
        tableMaterial.setEditable(true);
        tableMaterial.setLayoutX(300);
        tableMaterial.setLayoutY(240);

        TableColumn c1 = new TableColumn("Код");
        c1.setPrefWidth(75);
        c1.setCellValueFactory(
                new PropertyValueFactory<model, String>("Code"));
        TableColumn desc = new TableColumn("Описание");
        desc.setPrefWidth(150);
        desc.setCellValueFactory( new PropertyValueFactory<model, String>("Value"));

        tableMaterial.getColumns().addAll(c1, desc);
        tableMaterial.setItems(getDatamodel());

        root.getChildren().addAll(btn, input, list, combo, tableMaterial, tf, apply);
        secondStage.setTitle("Hello World");
        secondStage.setScene(new Scene(root, 1000, 800));
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

    public ObservableList<model> getDatamodel() throws SQLException {
        ObservableList<model> data_ = FXCollections.observableArrayList();
        Select=exc.submit("SELECT * FROM sch.rel");
        try {
            while (Select.next()) {
                data_.add(new model(Select.getString("code"), Select.getString("description")));           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data_;
    }

}

