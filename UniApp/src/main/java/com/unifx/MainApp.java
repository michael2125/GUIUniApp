package com.unifx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent; 
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class MainApp extends Application {    // so that we can use MainApp to use javafx.
    private static Stage stage;

    @Override
    public void start(@SuppressWarnings("exports") Stage s) throws IOException {
        stage=s;
        setRoot("primary","");
    }

    /*
        static void setRoot(String fxml) throws IOException {
        String title = stage.getTitle(); 
        Scene scene = new Scene(loadFXML(fxml));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show()
        }
    */
    
    static void setRoot(String fxml) throws IOException {  //Recursive calls inside methods
        setRoot(fxml,stage.getTitle());
    }

    static void setRoot(String fxml, String title) throws IOException {
        Scene scene = new Scene(loadFXML(fxml));
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("/fxml/"+fxml + ".fxml"));//let the fxml to get the URL. and this line can load path with speical filename.
        return fxmlLoader.load(); //use load function to return URL to root object.
    }


    public static void main(String[] args) {
        launch(args); //start the programme.
    }

}
