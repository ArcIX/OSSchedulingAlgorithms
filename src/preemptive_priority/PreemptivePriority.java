/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preemptive_priority;

import java.util.ArrayList;
import java.util.Collections;
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
    //private ArrayList<Process> idle_times; //I made the idle times act like a normal nonpreemptive process
    //private HashMap<Integer, Integer> idle_times;
    private int runTime = 0; //how long a running process is running; resets whenever completed running or when preemption occurs
    private int currentTime = 0; //the time at the moment
    private int finishTime; //time every process has completed
    private int processCount; //# of processes in total
    
    public PreemptivePriority(ArrayList<PPProcess> processes)
    {
        this.arriving = processes;
        this.ready = new ArrayList<PPProcess>();
        this.running = null;
        this.finished = new ArrayList<PPProcess>();
        processCount = processes.size();
    }
    
    public void sortArriving()
    {
        Collections.sort(arriving, new ArrivalComparator());
    }
    
    public void sortReady()
    {
        Collections.sort(ready, new PriorityComparator());
    }
    
    public boolean shouldReady()
    {
        if(arriving.isEmpty())
        {
            return false;
        }
        return currentTime == arriving.get(0).arrival_time;
    }
    
    public void readyProcess()
    {
        ready.add(arriving.get(0));
        arriving.remove(0);
    }
    
    public boolean shouldRun()
    {
        return running == null;
    }
    
    public void runProcess()
    {
        running = ready.get(0);
        ready.remove(0);
    }
    
    public boolean shouldFinish()
    {
        if (running == null)
        {
            return false;
        }
        return running.getSumOfSegments() + runTime == running.burst_time;
    }
    
    public void finishProcess()
    {
        finished.add(running);
        running = null;
    }
    
    public boolean shouldPreempt()
    {
        return running.priority > ready.get(0).priority;
    }
    
    public void preemptProcess()
    {
        ready.add(running);
        runProcess();
    }
    
    public void elapseCurrentTime() //let time pass by
    {
        currentTime++;
    }
    
    public PPProcess getProcess(int number) //get a process by its number or id. Using get() from ArrayList will not always be reliable.
    {
        for (PPProcess p: finished)
        {
            if (p.number == number)
            {
                return p;
            }
        }
        
        return null;
    }
    
    public void watcher()
    {
        System.out.println("Arriving: " + arriving.toString());
        System.out.println("Ready: " + ready.toString());
        System.out.println("Running: " + running);
        System.out.println("Finished: " + finished.toString());
    }
    
    public void drawGantt()
    {
        sortArriving();
        
        while (finished.size() < processCount) // or (!arriving.isEmpty() || !ready.isEmpty() || running != null)
        {
            System.out.println("=============================BEGIN==========================="); //LOG
            System.out.println("\tTIME " + currentTime + " T.U."); //LOG
            System.out.println("Part 1: Check running if must finish");
            watcher();
            
            if (shouldFinish())
            {
                running.burst_segments.add(runTime);
                runTime = 0;
                finishProcess();
                if (finished.size() == processCount)
                {
                    break;
                }
            }
            
            System.out.println("Part 2: Check arriving if becomes ready");
            watcher();
            
            while (shouldReady())
            {
                readyProcess();
            }
            
            System.out.println("Part 3: Check if should run or preempt");
            watcher();
            
            if (!ready.isEmpty())
            {
                sortReady();
                if (running == null)
                {
                    runProcess();
                    running.start_times.add(currentTime);
                }
                else if (shouldPreempt())
                {
                    running.burst_segments.add(runTime);
                    runTime = 0;
                    preemptProcess();
                    running.start_times.add(currentTime);
                }
            }
            
            if (running != null)
            {
                runTime++;
            }
            
            System.out.println("Part 4: So far...");
            watcher();
            
            currentTime++;
            
            System.out.println("===========================END==============================="); //LOG
        }
        
        finishTime = currentTime;
    }
    
    public float getCPUUtil()
    {
        float cu, sum = 0;
        
        for (PPProcess p: finished)
        {
            sum += (float)p.burst_time;
        }
        
        cu = (sum / (float)finishTime) * 100;
        
        return cu;
    }
    
    public String getThroughput()
    {
        return processCount + "/" + finishTime;
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
        
        for (PPProcess p: finished)
        {
            sum += p.getWaitingTime();
        }
        
        return (float)sum / (float)processCount;
    }
    
    public float getAvgTT()
    {
        int sum = 0;
        
        for (PPProcess p: finished)
        {
            sum += p.getTurnaroundTime();
        }
        
        return (float)sum / (float)processCount;
    }
    
    public float getAvgRT()
    {
        int sum = 0;
        
        for (PPProcess p: finished)
        {
            sum += p.getResponseTime();
        }
        
        return (float)sum / (float)processCount;
    }
    
    public ArrayList<PPProcess> getFinished()
    {
        return this.finished;
    }
    
    public PPInfo getInfo()
    {
        return new PPInfo(this);
    }
    
    public String toString() {
        String s = new String();
        for (PPProcess p: finished)
        {
            s += "\n ******** PROCESS " + p.number + " ******** \n"
                 + "\n Arrival Time: " + p.arrival_time
                 + "\n Burst Time: " + p.burst_time
                 + "\n Priority: " + p.priority
                 + "\n Start Times: " + p.start_times.toString()
                 + "\n Burst Segments: " + p.burst_segments.toString()
                 + "\n Waiting Time: " + p.getWaitingTime()
                 + "\n Turnaround Time: " + p.getTurnaroundTime()
                 + "\n Response Time: " + p.getResponseTime()
                 + "\n ************************** \n";
        }
        s += "\nCPU Utilization: " + String.format("%.2f", getCPUUtil())
             + "\nThroughput: " + getThroughput()
             + "\nAverage Waiting Time: " + String.format("%.2f", getAvgWT())
             + "\nAverage Turnaround Time: " + String.format("%.2f", getAvgTT())
             + "\nAverage Response Time: " + String.format("%.2f", getAvgRT());
             
        return s;
    }
    
    public static void main(String a[])
    {
        PPProcess p1 = new PPProcess(1, 0, 10, 6); //id, at, bt, pr
        PPProcess p2 = new PPProcess(2, 4, 15, 5);
        PPProcess p3 = new PPProcess(3, 6, 10, 4);
        PPProcess p4 = new PPProcess(4, 8, 12, 3);
        PPProcess p5 = new PPProcess(5, 0, 11, 8);
        PPProcess p6 = new PPProcess(6, 2, 17, 7);
        PPProcess p7 = new PPProcess(7, 1, 19, 1);
        PPProcess p8 = new PPProcess(8, 10, 14, 2);
        ArrayList<PPProcess> px = new ArrayList<PPProcess>();
        px.add(p1);
        px.add(p2);
        px.add(p3);
        px.add(p4);
        px.add(p5);
        px.add(p6);
        px.add(p7);
        px.add(p8);
        String s = new String();
        for (PPProcess p : px)
        {
            s += "[Process " + p.number + "] ";
        }
        System.out.println("Given: " + s + "\nSize: " + px.size());
        PreemptivePriority pp = new PreemptivePriority(px);
        pp.drawGantt();
        System.out.println(pp.toString());
    }
}
