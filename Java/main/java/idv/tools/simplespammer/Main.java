package idv.tools.simplespammer;

import javafx.scene.Scene;
import java.io.IOException;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.application.Application;

public class Main extends Application{
    @Override
    public void start(Stage stage) throws IOException{
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Main.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("아 이름짓기도 귀찮다");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}

// Copyright 2023. Benedictus Park(benedictuspark1220@gmail.com) all rights reserved.