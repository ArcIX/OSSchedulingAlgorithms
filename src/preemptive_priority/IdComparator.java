/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preemptive_priority;

import java.util.Comparator;
//import process.PPProcess;
import process.Process;

/**
 *
 * @author Rc Zeravla
 */
public class IdComparator implements Comparator<Process> {
    @Override
    public int compare(Process p1, Process p2) {
        Integer n1 = p1.number;
        Integer n2 = p2.number;
        return n1.compareTo(n2);
    }
}
