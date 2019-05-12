package message;

import java.io.Serializable;

public class Message implements Serializable{

    public Message(int type,int data[][]){
    this.type=type;
    this.data=data;
    }
    
    public Message(){};

private int type;
private int data[][];
    
public int getType(){
return type;

}

public int[][] getData(){
return data;
}

public void setType(int type){this.type=type;}
public void setData(int [][]data){this.data=data;}



}
