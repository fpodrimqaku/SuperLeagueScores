/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication7;

import CommunicationClient.ClientHandler;
import com.sun.prism.paint.Paint;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.web.WebView;
import message.Message;
import message.MessageFactory;
import us.sosia.video.stream.agent.StreamClient;

/**
 *
 * @author Guesst
 */
public class FXMLDocumentController implements Initializable {
      final String MyHexColors[]=new String[]{"#2ecc71","#6495ed","#9b59b6","#e74c3c"};
      
      @FXML
      Button btn_LeaveRoom;
      
      @FXML
      TextField txt_SendChatText;
      
      //RedUser
      @FXML
      ImageView iv_UserRedLook;
      @FXML
      ToggleButton swt_RedMute;
      @FXML
      ToggleButton swb_RedCamOff;
      @FXML
      VBox vbox_userRed;
      
      
      
      //BlueUser
      @FXML
      ImageView iv_UserBlueLook;
      @FXML
      ToggleButton swt_BlueMute;
      @FXML
      ToggleButton swb_BlueCamOff;
      @FXML
      VBox vbox_userBlue;
      
      
      
      //GreenUser
      @FXML
      ImageView iv_UserGreenLook;
      @FXML
      ToggleButton swt_GreenMute;
      @FXML
      ToggleButton swb_GreenCamOff;
      @FXML
      VBox vbox_userGreen;
      
      
      //PurpleUser
      @FXML
      ImageView iv_UserPurpleLook;
      @FXML
      ToggleButton swt_PurpleMute;
      @FXML
      ToggleButton swb_PurpleCamOff;
      @FXML
      VBox vbox_userPurple;
      
      @FXML
      private Button btn_sendText;
      
      public void setImages()
      {
          String btnimg="send_text.png";
          btn_sendText.setStyle("-fx-background-image: url('"+btnimg+"')");

      }
      
      
      
      
      
    @FXML
    private Label label;
   
  
   @FXML
    private ScrollPane scrollPn_Chat;
   
   @FXML
   private FlowPane flowPn_Chat;
   
   @FXML
   public void sendText(ActionEvent actionEvent){
    String text=txt_SendChatText.getText();
    if((text.length()==0) || (text==null))
        return;
       ch.sendChatMessage_handle(text);
       txt_SendChatText.setText("");
   }
   
   @FXML
   public void addText(int user,String text){
       Platform.runLater(()->{String resLoc="userChats/chatText";
   if(user==ch.getMyID())
       resLoc="userChats/meChatText";
   System.out.println(resLoc);
    try{
       HBox hbox=FXMLLoader.load(getClass().getResource(resLoc+user+".fxml"));
       scrollPn_Chat.setVvalue(1.0);
      Text text1=new Text(20,20,text);
      text1.setFill(Color.WHITE);
      text1.setFont(Font.font("Roman", FontWeight.BOLD, 13));
      if(user==ch.getMyID())
      ((TextFlow)hbox.getChildren().get(0)).getChildren().add(text1);
 else
           ((TextFlow)hbox.getChildren().get(1)).getChildren().add(text1);
       flowPn_Chat.getChildren().add(hbox);
      //System.out.println(scrollPn_Chat);
        
         
       scrollPn_Chat.setVvalue(1.0);
    }catch(Exception m){System.out.println(m);} 
    });
   }
   
   
   
   
   
   @FXML
   public void leaveRoom(ActionEvent actionEvent){
   ch.leaveRoom();
   
   }
   
   
   
   
   
   
   

    public void setBtn_LeaveRoom(Button btn_LeaveRoom) {
        this.btn_LeaveRoom = btn_LeaveRoom;
    }
   /*
   
   MulticastSocket multicastSocket;
   InetSocketAddress myUDP;
   *///--
   
    ClientHandler ch;
    
    
    
  //  StreamClient clientStream;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
txt_SendChatText.setOnKeyReleased(event -> {
  if (event.getCode() == KeyCode.ENTER){
    sendText(null);
  }
});
        
        



//setImages(); 
        /*
        StreamClient sc=new StreamClient();
SwingNode sn=new SwingNode();
sn.setContent(StreamClient.displayWindow.getVideoCapture());
this.vbox_userBlue.getChildren().add(sn);
        new Thread(sc).start();
*/
        
        ch=new ClientHandler(this,new InetSocketAddress("127.0.0.1",9099));
    // ch.receivedChatMessage_handle(MessageFactory.createType1Message(2, "heheh"));
        ch.startRunning();
        
        
        
        /*   addText(2,"hello team");   
      addText(3,"hello ");
      addText(3,"hello team i will guard bomb site a");
      addText(3,"ill go b \n some one come with me");
      addText(0,"hello ");
      addText(3,"hello ");
      addText(1,"ill be going long some one flash for me and go back to guarding short ");
      addText(3,"hello ");
      addText(3,"hello knsf jnjnsssjjs ssjsjsjsj ");
      addText(3,"hello ");
*/
/*
cont cont1=new cont(this);
cont1.inConn();
cont1.inConn();
cont1.inConn();
cont1.inConn();
*/
}
    
    /*
    class cont{
    FXMLDocumentController fdc;
        public cont(FXMLDocumentController fdc){
    this.fdc=fdc;
    }
        public void inConn(){
        
        new Thread(new Task<Integer>(){
        @Override
        public Integer call(){
        fdc.addText(3,"ygygyg");
        while(true){
        System.out.println("4");
        if(1==2)
            break;
        }
        return 2;
        }
        
        }).start();
        
        
        
        }
        
    }
*/

    
}
