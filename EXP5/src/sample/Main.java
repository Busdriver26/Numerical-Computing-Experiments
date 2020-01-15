package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL location = getClass().getResource("sample.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(location);
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent root = fxmlLoader.load();
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        //FXMLLoader fxmlLoader= new FXMLLoader();
        primaryStage.setTitle("Exp 5 by 10172100163_GongZezheng");
        primaryStage.setScene(new Scene(root, 285, 551));
        Controller controller = fxmlLoader.getController();   //获取Controller的实例对象
        //Controller中写的初始化方法
        //controller.initialize();
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
