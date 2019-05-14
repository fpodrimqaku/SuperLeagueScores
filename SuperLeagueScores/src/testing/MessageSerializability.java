package testing;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import message.Message;
import message.MessageFactory;

public class MessageSerializability {

    static InetSocketAddress inet1 = new InetSocketAddress("34.56.56.34", 4), inet2 = new InetSocketAddress("3.6.6.4", 56), inet3 = new InetSocketAddress("34.56.56.34", 4);
   static  int id1 = 4, id2 = 5, id3 = 9;

    public static void main(String args[]) throws Exception {

        Socket s1=null,s2,s3;
        Message m1=MessageFactory.createType0Message("rico", inet1),
                m2=MessageFactory.createType0Message("rico", inet2),
                m3=MessageFactory.createType0Message("rico", inet3);
ArrayList<InetSocketAddress> arr1=new ArrayList();
ArrayList<Integer> arr2=new ArrayList();

arr1.add(new InetSocketAddress("34.34.34.34",34));
arr1.add(new InetSocketAddress("34.34.34.34",34));




arr2.add(4);
m1.setChatMessage("i love coffee");
m1.setOtherUDPs(arr1);
m1.setOtherIDs(arr2);
m1.setMyUDPadd(new InetSocketAddress("34.34.34.34",34));
        
        /*
        conn(s1);
        ObjectInputStream oins=new ObjectInputStream(s1.getInputStream());
        ObjectOutputStream outs=new ObjectOutputStream(s1.getOutputStream());
        outs.writeObject(m1);
        Message message=(Message)oins.readObject();
        
        System.out.println(message.getType());
        
        s1.close();*/

Thread th1=new Thread(()->{try{
ServerSocket ss=new ServerSocket(9099);
Socket s=ss.accept();
ObjectInputStream oins=new ObjectInputStream(s.getInputStream());
long j=System.currentTimeMillis();


Message message=(Message)oins.readObject();

System.out.println(System.currentTimeMillis()-j);
System.out.println(message.getChatMessage());
System.out.println(message.getOtherIDs().get(0));

}catch(Exception m){m.printStackTrace();}
});

Thread th2=new Thread(()->{try{


SocketAddress sa=new InetSocketAddress("127.0.0.1",9099);
Socket s=new Socket();
s.connect(sa);

ObjectOutputStream oouts=new ObjectOutputStream(s.getOutputStream());
long j=System.currentTimeMillis();
oouts.writeObject(m1);
System.out.println(System.currentTimeMillis()-j);

}catch(Exception m){m.printStackTrace();}
});

th1.start();
th2.start();

    }
    
    public static void conn(Socket s){
    try {
            s = new Socket();
            s.connect(new InetSocketAddress("127.0.0.1",9099));
        } catch (Exception m) {
            m.printStackTrace();
        }
    }
}


//testing purpose is Message class completely serializable
//test passed