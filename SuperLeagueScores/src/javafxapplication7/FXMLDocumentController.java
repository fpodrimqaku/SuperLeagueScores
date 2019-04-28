/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication7;

import com.sun.prism.paint.Paint;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;

/**
 *
 * @author Guesst
 */
public class FXMLDocumentController implements Initializable {
      final String MyHexColors[]=new String[]{"#2ecc71","#6495ed","#9b59b6","#e74c3c"};
    @FXML
    private Label label;
   
  
   @FXML
    private ScrollPane scrollPn_Chat;
   
   @FXML
   private FlowPane flowPn_Chat;
   
   @FXML
   public void sendText(ActionEvent actionEvent){
   }
   
   @FXML
   public void addText(int user,String text){
       
   
    try{
       HBox hbox=FXMLLoader.load(getClass().getResource("userChats/chatText"+user+".fxml"));
      Text text1=new Text(20,20,text);
      text1.setFill(Color.WHITE);
      text1.setFont(Font.font("Roman", FontWeight.BOLD, 13));
      if(user!=0)
      ((TextFlow)hbox.getChildren().get(1)).getChildren().add(text1);
 else
           ((TextFlow)hbox.getChildren().get(0)).getChildren().add(text1);
       flowPn_Chat.getChildren().add(hbox);
      //System.out.println(scrollPn_Chat);
        
        
    }catch(Exception m){System.out.println(m);} 
   
   }
   
   
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          
    }
    
    
    


}
