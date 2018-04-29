/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Rc Zeravla
 */

public class PreemptiveProcess extends Process {
    public ArrayList<Integer> start_times;
    //public ArrayList<Integer> end_times;
    public ArrayList<Integer> burst_segment;
    //public HashMap<Integer, Integer> burst_segment;
    
    public PreemptiveProcess(int number, int arrival_time, int burst_time)
    {
        super(number, arrival_time, burst_time);
        this.start_times = new ArrayList<Integer>();
        //this.end_times = new ArrayList<Integer>();
        this.burst_segment = new ArrayList<Integer>();
    }
    
    public void setStartTime()
    {
        super.start_time = start_times.get(0);
    }
    
    public void setEndTime() 
    {
        super.end_time = getTimePaused(start_times.size());
    }
    
    public int getTimePaused(int position)
    {
        return start_times.get(position) + burst_segment.get(position);
    }
    
    /*
    public int getBurstSegment(int position) //return burst time of one segment
    {
        return end_times.get(position) - start_times.get(position); 
    }
    */
            
    @Override
    public int getWaitingTime()
    {
        int sum = super.getWaitingTime();
        
        for (int i = 1; i < start_times.size(); i++)
        {
            sum += start_times.get(i) - getTimePaused(i - 1);
        }
        
        return sum;
    }
    
    @Override
    public int getResponseTime()
    {
        return super.getWaitingTime();
    }
}
