/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 *
 * @author Guesst
 */
public class LittleThingsTesting {
    public static void main(String args[]){
    
    List<Integer> m=new ArrayList();
    Queue<Integer> q=new PriorityQueue();
    q.offer(1);
    q.offer(2);
    q.offer(3);
    q.offer(4);
    q.offer(5);
    q.offer(6);
    
    System.out.println("asd"+q.size());
    while(q.size()>0)
        System.out.println(q.poll());
     
    System.out.println(q.size());
    
    }



}