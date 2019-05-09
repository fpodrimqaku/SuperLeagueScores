package MyUtils;

import java.net.Socket;

public class Converter {

    public static  byte [] intArrtoByteArr(int [] arr){
        
 byte []byteArr=new byte[arr.length];
 System.arraycopy(arr, 0, byteArr, 0, byteArr.length);
     return byteArr;
 }

   
    
    public static void main(String args[]){
    
        
        
    }
}
