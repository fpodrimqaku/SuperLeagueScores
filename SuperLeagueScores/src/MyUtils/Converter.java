package MyUtils;

import java.net.Socket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
public class Converter {

    public static  byte [] intArrtoByteArr(int [] arr){
        
 byte []byteArr=new byte[arr.length];
 
 for(int it=1;it<byteArr.length;it++)
 byteArr[it]=(byte)arr[it];
 ;
 
 
     return byteArr;
 }

 public static int [] byteArrToIntArr(byte arr[]){
 
 int []intArr=new int[arr.length];
 
 
 for(int it=0;it<intArr.length;it++)
 intArr[it]=arr[it];
 ;
 
 
     return intArr;
 
 }
  
  public static int [] byteArrToIntArr_prefixID(int ID,byte arr[]){
 
 int []intArr=new int[arr.length+1];
 
 
 for(int it=1;it<intArr.length;it++)
 intArr[it]=arr[it-1];
 ;
 
 intArr[0]=ID;
     return intArr;
 
 }
 
 
    public static void main(String args[]){
    
        
        
    }
}
