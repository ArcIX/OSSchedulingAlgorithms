/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preemptive_priority;

import java.util.Comparator;
import process.Process;

/**
 *
 * @author Rc Zeravla
 */
public class ArrivalComparator implements Comparator<Process> {
    public int compare(Process p1, Process p2)
    {
        Integer a1 = p1.arrival_time;
        Integer a2 = p2.arrival_time;
        int compare = a1.compareTo(a2);
        if (compare == 0)
        {
            IdComparator idc = new IdComparator();
            return idc.compare(p1, p2);
        }
        return compare;
    }
}
