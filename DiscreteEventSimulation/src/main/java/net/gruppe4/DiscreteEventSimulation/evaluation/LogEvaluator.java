package net.gruppe4.DiscreteEventSimulation.evaluation;

import net.gruppe4.DiscreteEventSimulation.simulation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.Map;

/**
 * Represents an evaluator for an {@link EventLog} object resulting from a
 * {@link Simulation} run.
 *
 * It contains methods to calculate various statistics related to machines, jobs, and operations.
 */
public class LogEvaluator {
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

    /**
     * This method calculates operation statistics based on a HashMap of
     * machines and their respective operation lengths. It returns a HashMap
     * where each key is an Operation and the value is a HashMap containing
     * the length of that operation.
     *
     * @param machines a HashMap of machines and their respective operation
     *                 lengths
     * @return a HashMap of operation statistics
     */
    public HashMap<Operation, HashMap<String, Object>> calculateOperationStats(HashMap<Machine, HashMap<String, Object>> machines) {
        HashMap<Operation, HashMap<String, Object>> res = new HashMap<Operation, HashMap<String, Object>>();

        for (Map.Entry<Machine, HashMap<String, Object>> entry : machines.entrySet()) {
            HashMap<Operation, Integer> operationLengths = (HashMap)(entry.getValue().get("operation_lengths"));

            for (Map.Entry<Operation, Integer> opSet : operationLengths.entrySet()) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("length", opSet.getValue());
                res.put(opSet.getKey(), map);
            }
        }

        return res;
    }

    /**
     * Calculates the general statistics for the simulation using the given
     * machines and jobs.
     *
     * @param machines HashMap of Machine objects and their associated
     *                 HashMaps of properties.
     * @param jobs HashMap of Job objects and their associated HashMaps of
     *             properties.
     * @return HashMap containing the calculated statistics.
     */
    public HashMap<String, Object> calculateGeneralStats(HashMap<Machine, HashMap<String, Object>> machines,
                                                         HashMap<Job, HashMap<String, Object>> jobs) {

        HashMap<String, Object> res = new HashMap<String, Object>();

        Integer completionDate = this.log.getArrayList().get(this.log.getArrayList().size() - 1).getDate();
        res.put("total_completion_time", (double)(int)completionDate);

        Double totalCosts = 0.;
        Double machineUtilisation = 0.;
        for (Map.Entry<Machine, HashMap<String, Object>> entry : machines.entrySet()) {
            totalCosts += (double)entry.getValue().get("operational_cost") + (double)entry.getValue().get("repair_cost");
            machineUtilisation += (double)entry.getValue().get("utilisation_percent");
        }
        res.put("total_resource_utilisation", (double)(machineUtilisation / (double)machines.size()));

        for (Map.Entry<Job, HashMap<String, Object>> entry : jobs.entrySet())
            totalCosts += (double)entry.getValue().get("lateness_cost");

        res.put("total_cost", (double)totalCosts);
        return res;
    }


    /**
     * Calculates various statistics values for each machine in the system.
     *
     * @return HashMap<Machine, HashMap<String, Object>> A mapping of each Machine
     *                                                   object to a HashMap of
     *                                                   its corresponding
     *                                                   statistics values.
     */
    public HashMap<Machine, HashMap<String, Object>> calculateMachineStatValues() {
        HashMap<Machine, HashMap<String, Object>> res = new HashMap<Machine, HashMap<String, Object>>();

        for(Map.Entry<String, Machine> entry : this.machines.entrySet()) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            ArrayList<Event> machineLog = this.log.getMachineLog(entry.getValue());

            HashMap<String, Object> machineLogEval = this.evaluateMachineLog(machineLog);

            Integer absoluteMachineUsage = (int)machineLogEval.get("operation_duration") - (int)machineLogEval.get("breakdown_duration");
            Double machineLogLength = (double)(int)machineLog.get(machineLog.size() - 1).getDate();

            map.put("utilisation_percent", (double)absoluteMachineUsage / machineLogLength);
            map.put("utilisation_time", (double)absoluteMachineUsage);
            map.put("repair_cost", (double)entry.getValue().getRepairCostPerTime() * (double)(int)machineLogEval.get("breakdown_duration"));
            map.put("operational_cost", (double)absoluteMachineUsage * entry.getValue().getCostPerTime());
            map.put("breakdowns_downtime", (double)(int)machineLogEval.get("breakdown_duration"));
            map.put("breakdowns_occurrence",(double)(int)machineLogEval.get("breakdown_occurrence"));
            map.put("breakdowns_percent", (double)map.get("breakdowns_downtime") / machineLogLength);
            Double idleTime = Math.max(machineLogLength - (double)(int)machineLogEval.get("operation_duration"),0);
            map.put("idle_time_absolute", idleTime);
            map.put("operation_lengths", machineLogEval.get("operation_lengths"));
            res.put(entry.getValue(), map);
        }

        return res;
    }

    /**
     * This method evaluates a machine log and returns a HashMap containing
     * information on the duration of each operation, the duration of each
     * breakdown, and the number of breakdown occurrences for the machine.
     *
     * @param machineLog An ArrayList of Event objects containing information
     *                   on the events that occurred on the machine.
     * @return A HashMap containing information on the duration of each
     *         operation, the duration of each breakdown, and the number
     *         of breakdown occurrences for the machine.
     */
    private HashMap<String, Object> evaluateMachineLog(ArrayList<Event> machineLog) {
        HashMap<Operation, Event[]> map = new HashMap<Operation, Event[]>();
        ArrayList<Integer> lengths = new ArrayList<Integer>();
        HashMap<Operation, Integer> lengthMap = new HashMap<Operation, Integer>();
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
                lengthMap.put(op, length);
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

        HashMap<String, Object> res = new HashMap<String, Object>();
        res.put("operation_duration", operationDuration);
        res.put("breakdown_duration", breakdownDuration);
        res.put("breakdown_occurrence", breakdownLengths.size());
        res.put("operation_lengths", lengthMap);


        return res;
    }

    /**
     * Calculates job statistics values based on the events log
     *
     * @return a HashMap of job to a HashMap of statistic name to value
     */
    public HashMap<Job, HashMap<String, Object>> calculateJobStatValues() {
        HashMap<Job, HashMap<String, Object>> res = new HashMap<Job, HashMap<String, Object>>();

        ListIterator<Event> i = this.log.getArrayList().listIterator(this.log.getArrayList().size());
        while (i.hasPrevious()) {
            Event event = i.previous();

            if (event.getEventType() != EventType.OPERATION_END) continue;
            Operation op = event.getOperation();
            if (res.containsKey(op.getJob())) continue;

            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("completion_date", (double)(int)event.getDate());
            map.put("lateness", (double)((double)map.get("completion_date") - (double) op.getJob().getDueDate()));
            map.put("lateness_cost", (double)map.get("lateness") * op.getJob().getCostPerLatenessTime());
            res.put(op.getJob(), map);

            if (res.size() == this.jobs.size()) break;
        }

        return res;
    }

    /**
     * Returns a HashMap of the finishing dates of every job passed in the
     * arrayList for a single simulation.
     *
     *
     * @return HashMap<Job, Object> A HashMap of the finishing dates of every
     *                              job passed in the arrayList
     */
    // For single simulation: Returns a HashMap of the finishing dates of every job passed in the arrayList
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
