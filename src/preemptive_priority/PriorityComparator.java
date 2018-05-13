/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preemptive_priority;

import java.util.Comparator;
import process.PPProcess;

/**
 *
 * @author Rc Zeravla
 */
public class PriorityComparator implements Comparator<PPProcess> {
    public int compare(PPProcess p1, PPProcess p2)
    {
        Integer pr1 = p1.priority;
        Integer pr2 = p2.priority;
        return pr1.compareTo(pr2);
    }
}
