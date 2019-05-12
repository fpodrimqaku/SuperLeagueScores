package message;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.List;

public class Message implements Serializable{

 
    public Message(){};

private int type;
private String RoomName;
private InetSocketAddress myUDPadd;
private String ChatMessage;
private int myID;
private List<InetSocketAddress> otherUDPs;
private List<Integer>otherIDs;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRoomName() {
        return RoomName;
    }

    public void setRoomName(String RoomName) {
        this.RoomName = RoomName;
    }

    public InetSocketAddress getMyUDPadd() {
        return myUDPadd;
    }

    public void setMyUDPadd(InetSocketAddress myUDPadd) {
        this.myUDPadd = myUDPadd;
    }

    public String getChatMessage() {
        return ChatMessage;
    }

    public void setChatMessage(String ChatMessage) {
        this.ChatMessage = ChatMessage;
    }

    public int getMyID() {
        return myID;
    }

    public void setMyID(int myID) {
        this.myID = myID;
    }

    public List<InetSocketAddress> getOtherUDPs() {
        return otherUDPs;
    }

    public void setOtherUDPs(List<InetSocketAddress> otherUDPs) {
        this.otherUDPs = otherUDPs;
    }

    public List<Integer> getOtherIDs() {
        return otherIDs;
    }

    public void setOtherIDs(List<Integer> otherIDs) {
        this.otherIDs = otherIDs;
    }
    


}
