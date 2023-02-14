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

    // Calculates how much a Machine has been actually running
    // TODO Check if its still buggy when breakdowns are happening or if that was only due too breakdowns being buggy

    public HashMap<Machine, HashMap<String, Object>> calculateMachineStatValues() {
        HashMap<Machine, HashMap<String, Object>> res = new HashMap<Machine, HashMap<String, Object>>();

        for(Map.Entry<String, Machine> entry : this.machines.entrySet()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            ArrayList<Event> machineLog = this.log.getMachineLog(entry.getValue());

            HashMap<String, Integer> machineLogEval = this.evaluateMachineLog(machineLog);

            Integer absoluteMachineUsage = machineLogEval.get("operationDuration") - machineLogEval.get("breakdownDuration");
            Double machineLogLength = (double)(int)machineLog.get(machineLog.size() - 1).getDate();

            map.put("utilisation_percent", (double)absoluteMachineUsage / machineLogLength);
            map.put("utilisation_time", (double)absoluteMachineUsage);
            map.put("repair_cost", (double)entry.getValue().getRepairCostPerTime() * (double)(int)machineLogEval.get("breakdownDuration"));
            map.put("operational_cost", (double)absoluteMachineUsage * entry.getValue().getCostPerTime());
            map.put("breakdowns_downtime", (double)(int)machineLogEval.get("breakdownDuration"));
            map.put("breakdowns_occurrence",(double)(int)machineLogEval.get("breakdownOccurence"));
            map.put("breakdowns_percent", (double)map.get("breakdowns_downtime") / machineLogLength);
            Double idleTime = Math.max(machineLogLength - machineLogEval.get("operationDuration"),0);
            map.put("idle_time_absolute", idleTime);
            res.put(entry.getValue(), map);
        }

        return res;
    }

    private HashMap<String, Integer> evaluateMachineLog(ArrayList<Event> machineLog) {
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

        HashMap<String, Integer> res = new HashMap<String, Integer>();
        res.put("operationDuration", operationDuration);
        res.put("breakdownDuration", breakdownDuration);
        res.put("breakdownOccurence", breakdownLengths.size());

        return res;
    }

    public HashMap<Job, HashMap<String, Object>> calculateJobStatValues() {
        HashMap<Job, HashMap<String, Object>> res = new HashMap<Job, HashMap<String, Object>>();

        ListIterator<Event> i = this.log.getArrayList().listIterator(this.log.getArrayList().size());
        while (i.hasPrevious()) {
            Event event = i.previous();

            if (event.getEventType() != EventType.OPERATION_END) continue;
            Operation op = event.getOperation();
            if (res.containsKey(op.getJob())) continue;

            // TODO Fix up this mess wtf
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("completiondate", (double)(int)event.getDate());
            map.put("lateness", (double)((double)map.get("completiondate") - (double) op.getJob().getDueDate()));
            map.put("latenesscost", (double)map.get("lateness") * op.getJob().getCostPerLatenessTime());
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
