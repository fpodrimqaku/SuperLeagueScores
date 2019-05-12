package message;

import MyUtils.Converter;
import java.net.InetAddress;

public class MessageFactory {

    public static Message createType1Message(int id, String string) {

        Message message = new Message();
        message.setType(1);
        message.setData(new int[][]{new int[]{id}, Converter.byteArrToIntArr(string.getBytes())});
        return message;
    }

    public static Message createType0Message(InetAddress iadd) {
        Message message = new Message();
        message.setType(0);
        // message.setData(Converter.byteArrToIntArr_prefixID(0, arr));
        return null;
    }

    public static void main(String args[]) {

        Message message = MessageFactory.createType1Message(4, "ammmma");
        for (int[] u : message.getData()) {System.out.println("__");
            for (int x : u) {
                System.out.println(x);
            }
        }
    }

}
