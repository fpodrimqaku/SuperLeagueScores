/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import us.sosia.video.stream.agent.*;

/**
 *
 * @author Guesst
 */
public class NewClass {public static void main(String args[]){
StreamClient sc=new StreamClient();
StreamServer sv=new StreamServer();
new Thread(sv).start();
new Thread(sc).start();
}
    
}
