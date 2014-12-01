package chapter12;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;

//Example4中，User必须要按Enter key, changes才能被提交
//Example5中，Cell只要lose focus,最新的value马上就被提交到model里面去了
public class TableViewExample5_WithInTableEditorCommitChangeOnFocusLost extends Application {
    
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
        stage.setHeight(550);
        final Label label = new Label("Address Book");
        label.setFont(new Font("Arial", 20));
        
        
        //--定义TableView的核心代码-----------------------------------------------------
        TableView<Person> table = new TableView<>();        
        table.setEditable(true); //我完全不知道这一行有什么用，因为设置了editable, table content仍旧不可以被edit
        

        //--这个Functional Interface就是这段代码的核心秘密所在--------------------------------
        //--我们必须使用我们自己定义的一个新的CellFactory
        //所谓CellFactory，其实就是一个CallBack functional interface. 当你传入给我一个TableColumn<Person, String>的时候，我还给你一个TableCell<Person, String>
        //原来的代码中是没有这些个的generic parameters的，这样保证这一个cellFactory可以用在所有类型的columns中
        Callback<TableColumn<Person, String>, TableCell<Person, String>> cellFactory = p -> new EditingCell();
        //--------------------------------------------------------------------------
        
        
        TableColumn<Person, String> firstNameCol = new TableColumn<>("First Name");
        firstNameCol.setMinWidth(100);
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        
        //--这两行代码可以保证firstName Column可以被编辑-----------------------
        firstNameCol.setCellFactory(cellFactory);
        firstNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setFirstName(t.getNewValue());
            }
        });
        //-----------------------------------------------------------
        
        
        TableColumn<Person, String> lastNameCol = new TableColumn<>("Last Name");
        lastNameCol.setMinWidth(100);
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        //--这两行代码可以保证lastName Column可以被编辑-----------------------        
        lastNameCol.setCellFactory(cellFactory);
        lastNameCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLastName(t.getNewValue());
            }
        });     
        //-----------------------------------------------------------
        
        
        TableColumn<Person, String> emailCol = new TableColumn<>("Email");
        emailCol.setMinWidth(200);
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        //--这两行代码可以保证email Column可以被编辑-----------------------              
        emailCol.setCellFactory(cellFactory);
        emailCol.setOnEditCommit(new EventHandler<CellEditEvent<Person, String>>() {
            @Override public void handle(CellEditEvent<Person, String> t) {
                ((Person) t.getTableView().getItems().get(t.getTablePosition().getRow())).setEmail(t.getNewValue());
            }
        });   
        //-----------------------------------------------------------        
        
        table.setItems(data);
        table.getColumns().addAll(firstNameCol, lastNameCol, emailCol);
        //-------------------------------------------------------------------------
        
        
        //--定义Add Person组件的核心代码， 最后全都放在一个HBox里面------------------------------
        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(firstNameCol.getPrefWidth());
        
        final TextField addLastName = new TextField();
        addLastName.setMaxWidth(lastNameCol.getPrefWidth());
        addLastName.setPromptText("Last Name");
        
        final TextField addEmail = new TextField();
        addEmail.setMaxWidth(emailCol.getPrefWidth());
        addEmail.setPromptText("Email");
        
        final Button addButton = new Button("Add");
        addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                data.add(new Person(addFirstName.getText(), addLastName.getText(), addEmail.getText()));
                addFirstName.clear();
                addLastName.clear();
                addEmail.clear();
            }
        });
        HBox hbox = new HBox();
        hbox.getChildren().addAll(addFirstName, addLastName, addEmail, addButton);
        hbox.setSpacing(3);
        //-------------------------------------------------------------------------        
        
        
        //--所有的一切的一切全都放在一个VBox里面----------------------------------------------
        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(label, table, hbox);
        //-------------------------------------------------------------------------        
        
        ((Group) scene.getRoot()).getChildren().addAll(vbox);
        stage.setScene(scene);
        stage.show();
    }
}