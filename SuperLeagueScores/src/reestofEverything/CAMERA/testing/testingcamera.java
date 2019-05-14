/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reestofEverything.CAMERA.testing;


import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import reestofEverything.CAMERA.Camera;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.instrument.Instrumentation;
import javafx.scene.image.WritableImage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.



/**
 *
 * @author Guesst
 */
public class testingcamera extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        ImageView imageView = new ImageView();
       
       Camera camera=new Camera();
            
        StackPane root = new StackPane();
        root.getChildren().add(imageView);
        
        Scene scene = new Scene(root, 600, 600);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
     
        
        camera.capture.open(0);
        
    new Thread(
    
        ()->{int i=900;
        Instrumentation ins;
      WritableImage wi;
        while(true){
            try{
                wi=camera.getCapuredShotArray();
               
                System.out.println();
          imageView.setImage(wi);
   
            }catch(Exception m){m.printStackTrace();}     
          
            
        }
        
        }
        ).start();
    
    
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
