/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preemptive_priority;

import java.util.ArrayList;
import java.util.HashMap;
import process.PPProcess;
import process.Process;

/**
 *
 * @author Rc Zeravla
 */

public class PreemptivePriority {
    private ArrayList<PPProcess> arriving; //processes not arrived yet
    private ArrayList<PPProcess> ready; //processes that arrived or were paused
    private PPProcess running; //the running process
    private ArrayList<PPProcess> finished; //processes completed
    private ArrayList<Process> idle_times; //I made the idle times act like a normal nonpreemptive process
    //private HashMap<Integer, Integer> idle_times;
    private int currentTime;
    private int finishTime;
    private int processCount;
    
    public PreemptivePriority(ArrayList<PPProcess> processes)
    {
        this.arriving = processes;
        processCount = processes.size();
    }
    
    public PPProcess getNextReady() //get the process that will arrive next 
    {
        PPProcess next = arriving.get(0);
        
        for (PPProcess p: arriving)
        {
            if (p.arrival_time < next.arrival_time)
            {
                next = p;
            }
        }
        
        return next;
    }
    
    public void arriveProcess(PPProcess p) //move an arrived proess from the arriving list to the ready list
    {
        arriving.remove(p);
        ready.add(p);
    }
    
    public PPProcess getNextRun() //get the process that will run next by getting the process with the highest priority
    {
        PPProcess next = ready.get(0);
        
        for (PPProcess p: ready)
        {
            if (p.priority < next.priority)
            {
                next = p;
            } else if (p.number < next.number) 
            {
                next = p;
            }
        }
        
        return next;
    }
    
    public void runProcess(PPProcess p) //set the ready process as running and remove it from ready list
    {
        ready.remove(p);
        running = p;
    }
    
    public void finishProcess() //move a completed running process to the finished list
    {
        finished.add(running);
        //setFinishTime();
        running = null;
    }
    
    public void elapseCurrentTime() //let time pass by
    {
        currentTime++;
    }
    
    public void setFinishTime() //each time a process has finished, set the finishTime as the end time of that process
    {
        finishTime = running.getTimePaused(processCount - 1);
    }
    
    public PPProcess getProcess(int number) //get a process by its number. Using get() from ArrayList will not always be reliable.
    {
        ArrayList<PPProcess> all = getAllProcesses();
        
        for (PPProcess p: all)
        {
            if (p.number == number)
            {
                return p;
            }
        }
        
        return null;
    }
    
    public ArrayList<PPProcess> getAllProcesses()
    {
        ArrayList<PPProcess> all = new ArrayList<PPProcess>();
        
        all.addAll(arriving);
        all.addAll(ready);
        all.add(running);
        all.addAll(finished);
        
        return all;
    }
    
    public float getCPUUtil()
    {
        int cu, sum = 0;
        
        for (Process p: idle_times)
        {
            sum += p.burst_time;
        }
        
        cu = (finishTime - sum) / finishTime * 100;
        
        return cu;
    }
    
    public float getThroughput()
    {
        return processCount / finishTime;
    }
    
    public int getWaitingTime(int number)
    {
        return getProcess(number).getWaitingTime();
    }
    
    public int getTurnaroundTime(int number)
    {
        return getProcess(number).getTurnaroundTime();
    }
    
    public int getResponseTime(int number)
    {
        return getProcess(number).getResponseTime();    
    }
    
    public float getAvgWT()
    {
        int sum = 0;
        ArrayList<PPProcess> all = getAllProcesses();
        
        for (PPProcess p: all)
        {
            sum += p.getWaitingTime();
        }
        
        return sum / processCount;
    }
    
    public float getAvgTT()
    {
        int sum = 0;
        ArrayList<PPProcess> all = getAllProcesses();
        
        for (PPProcess p: all)
        {
            sum += p.getTurnaroundTime();
        }
        
        return sum / processCount;
    }
    
    public float getAvgRT()
    {
        int sum = 0;
        ArrayList<PPProcess> all = getAllProcesses();
        
        for (PPProcess p: all)
        {
            sum += p.getResponseTime();
        }
        
        return sum / processCount;
    }
}
