/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preemptive_priority;

import java.util.ArrayList;
import process.PPProcess;
import java.util.Collections;

/**
 *
 * @author Rc Zeravla
 */
public class PPInfo {
    private float cpu_util;
    private String throughput;
    private float avg_wt;
    private float avg_tt;
    private float avg_rt;
    private ArrayList<PPProcess> processes;
    
    public PPInfo(PreemptivePriority pp)
    {
        this.processes = pp.getFinished();
        Collections.sort(processes, new IdComparator());
        this.cpu_util = pp.getCPUUtil();
        this.avg_wt = pp.getAvgWT();
        this.avg_tt = pp.getAvgTT();
        this.avg_rt = pp.getAvgRT();
    }
    
    public float getCPUUtil()
    {
        return this.cpu_util;
    }
    
    public String getThroughput()
    {
        return this.throughput;
    }
    
    public float getAvgWT()
    {
        return this.avg_wt;
    }
    
    public float getAvgTT()
    {
        return this.avg_tt;
    }
    
    public float getAvgRT()
    {
        return this.avg_rt;
    }
    
    public int getNumber(int index)
    {
        return this.processes.get(index).number;
    }
    
    public int getArrivalTime(int index)
    {
        return this.processes.get(index).arrival_time;
    }
    
    public int getBurstTime(int index)
    {
        return this.processes.get(index).burst_time;
    }
    
    public int getPriority(int index)
    {
        return this.processes.get(index).priority;
    }
    
    public float getWaitingTime(int index)
    {
        return this.processes.get(index).getWaitingTime();
    }
    
    public float getTurnaroundTime(int index)
    {
        return this.processes.get(index).getTurnaroundTime();
    }
    
    public float getResponseTime(int index)
    {
        return this.processes.get(index).getResponseTime();
    }
    
    public ArrayList<Integer> getStartTimes(int index)
    {
        return this.processes.get(index).start_times;
    }
    
    public ArrayList<Integer> getBurstSegments(int index)
    {
        return this.processes.get(index).burst_segments;
    }
}
