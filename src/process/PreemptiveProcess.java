/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.util.ArrayList;
//import java.util.HashMap;

/**
 *
 * @author Rc Zeravla
 */

public class PreemptiveProcess extends Process {
    public ArrayList<Integer> start_times;
    public ArrayList<Integer> burst_segments;
    //public ArrayList<Integer> end_times;
    //public HashMap<Integer, Integer> burst_segment;
    
    public PreemptiveProcess()
    {
        super();
        this.start_times = new ArrayList<Integer>();
        this.burst_segments = new ArrayList<Integer>();
    }
    
    public PreemptiveProcess(int number, int arrival_time, int burst_time)
    {
        super(number, arrival_time, burst_time);
        this.start_times = new ArrayList<Integer>();
        this.burst_segments = new ArrayList<Integer>();
        //this.end_times = new ArrayList<Integer>();
    }
    
    public int getSumOfSegments()
    {
        int sum = 0;
        for (Integer bs: this.burst_segments)
        {
            sum += bs;
        }
        return sum;
    }
    
    public int getEndOfSegment(int position)
    {
        return start_times.get(position) + burst_segments.get(position);
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
        evalStartTime();
        int sum = super.getWaitingTime(); //or super.getResponseTime(); or getResponseTime();
        
        for (int i = 1; i < start_times.size(); i++)
        {
            sum += start_times.get(i) - getEndOfSegment(i - 1);
        }
        
        return sum;
    }
    
    @Override
    public int getTurnaroundTime()
    {
        return getEndOfSegment(start_times.size() - 1) - arrival_time;
    }
    
    @Override
    public int getResponseTime()
    {
        return super.getWaitingTime();
    }
    
    private void evalStartTime()
    {
        super.start_time = this.start_times.get(0);
    }
    
    @Override
    public int getEndTime()
    {
        return this.start_times.get(this.start_times.size() - 1) + this.burst_segments.get(this.burst_segments.size() - 1);
    }
}
