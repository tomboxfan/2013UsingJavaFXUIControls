package chapter12;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class TableViewExample1_VerySimple extends Application {
    private TableView<Person> table = new TableView<>();

    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage stage) {
        Scene scene = new Scene(new Group());
        stage.setScene(scene);
        stage.setTitle("Table View Sample");
        stage.setWidth(300);
        stage.setHeight(500);
        
//重点代码开始--------------------------------------------
        
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
        table.setEditable(true);
        
        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        
        
        //--这里展示了复合columns的用法---------------------------------------
        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        TableColumn<Person, String> firstEmailCol = new TableColumn<>("Primary");
        TableColumn<Person, String> secondEmailCol = new TableColumn<>("Secondary");
        emailCol.getColumns().addAll(firstEmailCol, secondEmailCol);
        //-------------------------------------------------------------
        
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        
        
//重点代码结束--------------------------------------------        
        
        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table);
        ((Group) scene.getRoot()).getChildren().addAll(vbox);

        stage.show();
    }
}