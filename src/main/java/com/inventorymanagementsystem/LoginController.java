package com.inventorymanagementsystem;

import com.inventorymanagementsystem.entity.User;
import com.inventorymanagementsystem.config.Database;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Label c_logo;

    @FXML
    private Label f_logo;

    @FXML
    private Button login_btn;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private AnchorPane login_form;


    private Connection connection;

    private Statement statement;

    private PreparedStatement preparedStatement;

    private ResultSet resultSet;
    private double x;
    private double y;


    public void textfieldDesign(){
        if(username.isFocused()){
            username.setStyle("-fx-background-color:#fff;"+"-fx-border-width:2px");
            password.setStyle("-fx-background-color:transparent;"+"-fx-border-width:1px");
        }else if(password.isFocused()){
            username.setStyle("-fx-background-color:transparent;"+"-fx-border-width:1px");
            password.setStyle("-fx-background-color:#fff;"+"-fx-border-width:2px");

        }
    }


    public void onExit(){
        System.exit(0);
    }

    public void login(){
        connection= Database.getInstance().connectDB();
        String sql="SELECT * FROM users WHERE username=? and password=?";
        try{
            preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,username.getText());
            preparedStatement.setString(2, password.getText());
            resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                User.name=username.getText();
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mesaj");
                alert.setHeaderText(null);
                alert.setContentText("Giriş Başarılı !");
                alert.showAndWait();
                login_btn.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("dashboard.fxml"));
                Scene scene = new Scene(root);
                Stage stage=new Stage();

                root.setOnMousePressed((event)->{
                    x=event.getSceneX();
                    y=event.getSceneY();
                });
                root.setOnMouseDragged((event)->{
                    stage.setX(event.getScreenX()-x);
                    stage.setY(event.getScreenY()-y);
                });
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }else{
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Kullanıcı Adı veya Şifre yanlış.");
                alert.showAndWait();
            }
        }catch (Exception err){
            err.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}