package message;

import MyUtils.Converter;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class MessageFactory {

    public static Message createType1Message(int id, String string) {

        Message message = new Message();
        message.setType(1);
        message.setChatMessage(string);
        message.setMyID(id);
        return message;
    }

    public static Message createType0Message(String RoomName,InetSocketAddress iadd) {
Message message=new Message();
message.setType(0);
message.setMyUDPadd(iadd);
message.setRoomName(RoomName);
return message;
}

    
    
    public static Message createType3Message(int id){
    Message message=new Message();
    
    message.setType(3);
    message.setMyID(id);
    
    return message;
    }
    
    
    
    public static void main(String args[]) {
/*
        Message message = MessageFactory.createType1Message(4, "ammmma");
        for (int[] u : message.getData()) {
            System.out.println("__");
            for (int x : u) {
                System.out.println(x);
            }
        }*/

InetSocketAddress isa1=new InetSocketAddress("189.3.4.3",3);
InetSocketAddress isa2=new InetSocketAddress("189.3.4.3",6);
InetSocketAddress isa3=new InetSocketAddress("189.3.4.3",1);
InetSocketAddress isa4=new InetSocketAddress("189.3.4.3",9);
InetSocketAddress isa5=new InetSocketAddress("189.3.4.3",89);

Message m=MessageFactory.createType0Message()


    }

}
