/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aula;

/**
 *
 * @author Pedro
 */
public class ThreadTest extends Thread {
    
    private final int max;
    private Counter counter;
    public ThreadTest(int i,Counter c)
    {
        max = i;
        counter = c;
    }
    @Override
    public void run()
    {
        for(int i = 1; i< max;i++)
        {
            counter.inc();
        }
        System.out.println("Thread nº"+(max/100)+": "+counter.count);
    }
    
}
