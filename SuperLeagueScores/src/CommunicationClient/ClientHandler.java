/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CommunicationClient;

import javafxapplication7.FXMLDocumentController;
import message.Message;

public class ClientHandler {

    FXMLDocumentController fdc;
    
    
Thread TCPConn;

Thread UDPMicSend;
Thread UDPCamSend;
Thread UDPReceive;
    


    public ClientHandler(FXMLDocumentController fdc) {
        this.fdc = fdc;
    }

    
    
     //Passive
    
    public void receivedChatMessage_handle(Message message) {
        fdc.addText(message.getMyID(), message.getChatMessage());
    }

    public void occupantDisconnected_handle(Message message){
    
    
    }
    
    public void newOccupant_handle(Message message){}
    
    
    //Active
    
    public void sendChatMessage_handle(Message message){}
    
    public void leaveRoom_handle(){}
    
}
