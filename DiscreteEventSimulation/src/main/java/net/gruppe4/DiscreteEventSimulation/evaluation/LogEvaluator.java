package net.gruppe4.DiscreteEventSimulation.evaluation;

import net.gruppe4.DiscreteEventSimulation.simulation.*;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.HashMap;

public class LogEvaluator {
    private ArrayList<EventLog> logs;

    public LogEvaluator(ArrayList<EventLog> logs) {
        this.logs = logs;
    }

    public Double calculateMachineCapacityUtilizationMean(Machine m) {
        Double sum = 0.;
        for (EventLog log : this.logs) {
            sum += this.calculateMachineCapacityUtilization(m, log);
        }

        return sum / (double)this.logs.size();
    }

    public Double calculateMachineCapacityUtilization(Machine m, EventLog log) {
        ArrayList<Event> machineLog = log.getMachineLog(m);

        Double machineLogLength = (double)machineLog.get(machineLog.size() - 1).getDate();

        return this.calculateAbsoluteMachineUsage(machineLog) / machineLogLength;
    }

    // Calculates how much a Machine has been actually running
    // TODO Check if its still buggy when breakdowns are happening or if that was only due too breakdowns being buggy
    public Double calculateAbsoluteMachineUsage(ArrayList<Event> machineLog) {
        HashMap<Operation, Event[]> map = new HashMap<Operation, Event[]>();
        ArrayList<Integer> lengths = new ArrayList<Integer>();
        ArrayList<Integer> breakdownLengths = new ArrayList<Integer>();
        Integer lastBreakdownBeginDate = 0;

        for (Event e : machineLog) {
            Operation op = e.getOperation();

            if (!map.containsKey(op)) map.put(op, new Event[2]);

            if (e.getEventType() == EventType.OPERATION_BEGIN) map.get(op)[0] = e;
            else if (e.getEventType() == EventType.OPERATION_END) {
                map.get(op)[1] = e;
                Integer length = map.get(op)[1].getDate() - map.get(op)[0].getDate();
                lengths.add(length);
            }
            else if (e.getEventType() == EventType.MACHINE_BREAKDOWN_BEGIN) lastBreakdownBeginDate = e.getDate();
            else if (e.getEventType() == EventType.MACHINE_BREAKDOWN_END) {
                Integer length = e.getDate() - lastBreakdownBeginDate;
                lengths.add(length);
                breakdownLengths.add(e.getDate() - lastBreakdownBeginDate);
            }
        }

        Integer operationDuration = 0;
        Integer breakdownDuration = 0;
        for (Integer l : lengths) operationDuration += l;
        for (Integer l : breakdownLengths) breakdownDuration += l;
        return (double)operationDuration - breakdownDuration;
    }
}
