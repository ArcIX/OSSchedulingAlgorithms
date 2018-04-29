/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.util.ArrayList;

/**
 *
 * @author Rc Zeravla
 */

public class PPProcess extends PreemptiveProcess {
    public int priority;
    
    public PPProcess(int number, int arrival_time, int burst_time, int priority)
    {
        super(number, arrival_time, burst_time);
        this.priority = priority;
    }
}
