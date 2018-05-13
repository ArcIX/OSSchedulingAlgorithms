/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

/**
 *
 * @author Rc Zeravla
 */

//this is a process for nonpreemptive scheduling algorithms
public class Process { 
    public int number; //must be unique
    public int arrival_time;
    public int burst_time;
    public int start_time;
    //private int end_time;
    
    public Process()
    {
        super();
    }
    
    public Process(int start_time, int burst_time) //this is for idle times
    {
        this.start_time = start_time;
        this.burst_time = burst_time;
    }
    
    /*
    public Process(int start_time, int end_time) //this is for idle times
    {
        this.start_time = start_time;
        this.end_time =  end_time;
    }
    */
    
    public Process(int number, int arrival_time, int burst_time)
    {
        this.number = number;
        this.arrival_time = arrival_time;
        this.burst_time = burst_time;
    }
    
    //not sure if useful
    /*
    public void setStartTime(int start_time)
    {
        this.start_time = start_time;
        //setEndTime();
    }
    */
    /*
    private void setEndTime() {
        this.end_time = start_time + burst_time;
    }
    */
    //up to here
    
    public int getEndTime()
    {
        return start_time + burst_time;
    }
    
    public int getWaitingTime() 
    {
        return start_time - arrival_time;
    }
    
    public int getTurnaroundTime()
    {
        return getEndTime() - arrival_time;
    }
    
    public int getResponseTime() //response time = waiting time for nonpreemptive
    {
        return getWaitingTime();
    }
}
