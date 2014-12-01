package chapter12;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;


//我不知道如何fix这些generics问题
public class TableViewExample6_MapData extends Application {
    public static final String Column1MapKey = "A";
    public static final String Column2MapKey = "B";

    public static void main(String[] args) {
        launch(args);
    }

    @Override public void start(Stage stage) {
        
//核心代码开始-----------------------------------------------------------------------        
        Label label = new Label("Student IDs");
        label.setFont(new Font("Arial", 20));
        
        TableColumn firstDataColumn = new TableColumn<>("Class A");
        firstDataColumn.setCellValueFactory(new MapValueFactory(Column1MapKey));
        firstDataColumn.setMinWidth(130);
        
        TableColumn secondDataColumn = new TableColumn<>("Class B");
        secondDataColumn.setCellValueFactory(new MapValueFactory(Column2MapKey));
        secondDataColumn.setMinWidth(130);
        
        TableView<Map<String, String>> table_view = new TableView<>(generateDataInMap());
        table_view.setEditable(true);
        table_view.getSelectionModel().setCellSelectionEnabled(true);
        table_view.getColumns().setAll(firstDataColumn, secondDataColumn);
        
        Callback<TableColumn<Map, String>, TableCell<Map, String>> cellFactoryForMap = new Callback<TableColumn<Map, String>, TableCell<Map, String>>() {
            @Override public TableCell call(TableColumn p) {
                return new TextFieldTableCell(new StringConverter() {
                    @Override public String toString(Object t) {
                        return t.toString();
                    }

                    @Override public Object fromString(String string) {
                        return string;
                    }
                });
            }
        };
        firstDataColumn.setCellFactory(cellFactoryForMap);
        secondDataColumn.setCellFactory(cellFactoryForMap);
//--核心代码结束 -------------------------------------------------------------
        
//--布局代码开始-------------------------------------------------------------        
        VBox vbox = new VBox(); //第1层是VBox
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table_view);
        
        Group root = new Group(); //第2层是Group - root
        root.getChildren().addAll(vbox);
        
        Scene scene = new Scene(root); //第3层是scene
        
        stage.setScene(scene); //最外面一层 - 第4层才是传入的Stage
        
        stage.setTitle("Table View Sample");
        stage.setWidth(300);
        stage.setHeight(500);
        stage.show();
//--布局代码结束-----------------------------------------------------------
    }

    private ObservableList<Map<String, String>> generateDataInMap() {
        int max = 10;
        ObservableList<Map<String, String>> allData = FXCollections.observableArrayList();
        for (int i = 1; i < max; i++) {
            Map<String, String> dataRow = new HashMap<>();
            String value1 = "A" + i;
            String value2 = "B" + i;
            dataRow.put(Column1MapKey, value1);
            dataRow.put(Column2MapKey, value2);
            allData.add(dataRow);
        }
        return allData;
    }
}