package testing;

import CommunicationServer.Server;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import message.Message;
import message.MessageFactory;


public class ServerTesting {
public static void main(String args[]){
Server s=new Server(9099);
Thread sth=new Thread(s);
sth.start();

Thread th1=new Thread(()->{try{
Socket sock=new Socket();
sock.connect(new InetSocketAddress("127.0.0.1",9099));
Message mes=MessageFactory.createType0Message("myroom", new InetSocketAddress("1.2.1.2",9087));
ObjectOutputStream oouts =new ObjectOutputStream(sock.getOutputStream());    
ObjectInputStream oins=new ObjectInputStream(sock.getInputStream());

oouts.writeObject(mes);
 System.out.println("object sent init");
//System.out.println(((Message)oins.readObject()).getMyID());
while(true){

}
}catch(Exception m){m.printStackTrace();}
});
th1.start();


}    
}
