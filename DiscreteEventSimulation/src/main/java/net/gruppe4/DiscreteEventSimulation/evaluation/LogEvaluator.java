package net.gruppe4.DiscreteEventSimulation.evaluation;

import net.gruppe4.DiscreteEventSimulation.simulation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

public class LogEvaluator {
    private ArrayList<EventLog> logs;
    private EventLog log;
    private HashMap<String, Machine> machines = new HashMap<String, Machine>();
    private ArrayList<Job> jobs = new ArrayList<Job>();
    private ArrayList<Operation> operations = new ArrayList<Operation>();


    public LogEvaluator(EventLog log, HashMap<String, Machine> machines, ArrayList<Operation> operations, ArrayList<Job> jobs) {
        this.log = log;
        this.machines = machines;
        this.operations = operations;
        this.jobs = jobs;
    }

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

    public HashMap<Job, HashMap<String, Object>> calculateJobStatValues() {
        HashMap<Job, HashMap<String, Object>> res = new HashMap<Job, HashMap<String, Object>>();

        ListIterator<Event> i = this.log.getArrayList().listIterator(this.log.getArrayList().size());
        while (i.hasPrevious()) {
            Event event = i.previous();

            if (event.getEventType() != EventType.OPERATION_END) continue;
            Operation op = event.getOperation();
            if (res.containsKey(op.getJob())) continue;

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("completiondate", event.getDate());
            map.put("lateness", ((Integer)map.get("completiondate") - op.getJob().getDueDate()));
            map.put("latenesscost", ((Double)map.get("lateness") * op.getJob().getCostPerLatenessTime()));
            res.put(op.getJob(), map);

            if (res.size() == this.jobs.size()) break;
        }

        return res;
    }

    // For single simulation: Returns a HashMap of the finishing dates of every job passed in the arrayList
    // TODO Doesnt work properly since Dependencies are not implemented properly yet, check if it works after implementing them
    public HashMap<Job, Object> findJobCompletionDates() {
        HashMap<Job, Object> res = new HashMap<Job, Object>();
        ListIterator<Event> i = this.log.getArrayList().listIterator(this.log.getArrayList().size());

        while (i.hasPrevious()) {
            Event event = i.previous();

            if (event.getEventType() != EventType.OPERATION_END) continue;
            Operation op = event.getOperation();
            if (res.containsKey(op.getJob())) continue;

            res.put(op.getJob(), event.getDate());

            if (res.size() == this.jobs.size()) break;
        }

        return res;
    }
}
