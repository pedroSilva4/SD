/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pedro
 */
public class Barreira {
 int N;

 int nt = 0;
int e= 0;
 
 public Barreira(int n)
 {
     N =n ;
    
    // barreira = new int[n];
 }
 public synchronized void espera() throws InterruptedException
 {
    
     nt++;
     int e_chegada = e;
     if(nt<N){
     while(e_chegada==e){
         System.out.println("à espera");
         wait();         
         }
     
     }
     else{
         e++;nt=0;
        notifyAll();    
     }
    
     
 }
 
}
