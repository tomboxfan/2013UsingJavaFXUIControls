package chapter12;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TableViewExample2_WithSimpleContent extends Application {
    private TableView<Person> table = new TableView<Person>();
    
    //@formatter:off
    private final ObservableList<Person> data = FXCollections.observableArrayList(
            new Person("Jacob", "Smith", "jacob.smith@example.com"), 
            new Person("Isabella", "Johnson", "isabella.johnson@example.com"), 
            new Person("Ethan", "Williams", "ethan.williams@example.com"), 
            new Person("Emma", "Jones", "emma.jones@example.com"), 
            new Person("Michael", "Brown", "michael.brown@example.com"));
    //@formatter:on
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setTitle("Table View Sample");
        stage.setWidth(450);
        stage.setHeight(500);
        
        
//重点代码开始--------------------------------------------
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
        
        table.setEditable(true);
        
        TableColumn firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("firstName"));
        
        TableColumn lastNameCol = new TableColumn("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<Person, String>("lastName"));
        
        TableColumn emailCol = new TableColumn("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory<Person, String>("email"));
        
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        table.setItems(data);
//重点代码结束--------------------------------------------  
        
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        
        stage.setScene(scene);
        stage.show();
    }
}