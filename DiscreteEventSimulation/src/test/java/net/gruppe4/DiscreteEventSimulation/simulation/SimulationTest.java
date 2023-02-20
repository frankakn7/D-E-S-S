package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.evaluation.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    @Test
    void testLogEvaluator() {
        Machine mA = new Machine("A", 0.0, 4., 2., 3., 4.);
        Machine mB = new Machine("B", 0.2, 5., 2., 2., 3.);
        Machine mC = new Machine("C", 0.2, 5., 2., 2.4, 3.);

        HashMap<String, Machine> machines = new HashMap<String, Machine>();
        machines.put("A", mA);
        machines.put("B", mB);
        machines.put("C", mC);

        Job j1 = new Job("Job1", 13, 3.);
        Job j2 = new Job("Job2", 16, 2.);
        Job j3 = new Job("Job3", 17, 1.);
        Job j4 = new Job("Job4", 14, 3.);
        ArrayList<Job> jobs = new ArrayList<Job>();
        jobs.add(j1);
        jobs.add(j2);
        jobs.add(j3);
        jobs.add(j4);

        Operation op1 = new Operation("op1", null, null, 0, 5, mA, j2, 0., 0.);
        Operation op2 = new Operation("op2", op1, null, 0, 5, mA, j2, 0., 0.);
        Operation op3 = new Operation("op3", op2, null, 0, 3, mA, j1, 0., 0.);

        Operation op4 = new Operation("op4", null, null, 0, 4, mB, j3, 0., 0.);
        Operation op5 = new Operation("op5", op4, null, 0, 8, mB, j3, 0., 0.);
        Operation op6 = new Operation("op6", op5, null, 0, 6, mB, j2, 0., 0.);

        Operation op7 = new Operation("op7", null, null, 0, 6, mC, j1, 0., 0.);
        Operation op8 = new Operation("op8", op7, null, 9, 5, mC, j4, 0., 0.);
        Operation op9 = new Operation("op9", op8, null, 0, 3, mC, j3, 0., 0.);


        ArrayList<Operation> ops = new ArrayList<Operation>();
        ops.add(op1);
        ops.add(op2);
        ops.add(op3);
        ops.add(op4);
        ops.add(op5);
        ops.add(op6);
        ops.add(op7);
        ops.add(op8);
        ops.add(op9);

        Simulation sim = new Simulation(machines, ops);
        EventLog log = sim.runSim();
        System.out.println(log);

        ArrayList<EventLog> logs = new ArrayList<EventLog>();
        LogEvaluator evaluator = new LogEvaluator(log, machines, ops, jobs);

        var jStats = new ArrayList<JobStats>();
        for (Job j : jobs) jStats.add(new JobStats(j));
        HashMap<Job, HashMap<String, Object>> jobValues = evaluator.calculateJobStatValues();
        //System.out.println(jobValues.get(jStats.get(0).getJob()));
        for(JobStats jStat : jStats) {
            /*
            jStat.completionTime.addValue((double) jobValues.get(jStat.getJob()).get("completiontime"));
            jStat.lateness.addValue((double) jobValues.get(jStat.getJob()).get("lateness"));
            jStat.latenessCost.addValue((double) jobValues.get(jStat.getJob()).get("latenesscost"));
            */
            //System.out.println(jobValues.get(jStat));
            jStat.lateness.addValue((double)jobValues.get(jStat.getJob()).get("lateness"));
            jStat.latenessCost.addValue((double)jobValues.get(jStat.getJob()).get("latenesscost"));
            jStat.completionTime.addValue((double)jobValues.get(jStat.getJob()).get("completiondate"));
            System.out.println(jStat.toJsonObject().toString());
        }

        var jMachineStats = new ArrayList<MachineStats>();
        for (Map.Entry<String, Machine> m : machines.entrySet()) jMachineStats.add(new MachineStats(m.getValue()));

        HashMap<Machine, HashMap<String, Object>> machineValues = evaluator.calculateMachineStatValues();
        for(MachineStats mStat : jMachineStats) {
            mStat.utilisationPercent.addValue((double)machineValues.get(mStat.getMachine()).get("utilisation_percent"));
            mStat.utilisationTime.addValue((double)machineValues.get(mStat.getMachine()).get("utilisation_time"));
            mStat.repairCost.addValue((double)machineValues.get(mStat.getMachine()).get("repair_cost"));
            mStat.operationalCost.addValue((double)machineValues.get(mStat.getMachine()).get("operational_cost"));
            mStat.breakdownsTotalDowntime.addValue((double)machineValues.get(mStat.getMachine()).get("breakdowns_downtime"));
            mStat.breakdownsOccurrence.addValue((double)machineValues.get(mStat.getMachine()).get("breakdowns_occurrence"));
            mStat.breakdownsPercent.addValue((double)machineValues.get(mStat.getMachine()).get("breakdowns_percent"));
            mStat.idleTime.addValue((double)machineValues.get(mStat.getMachine()).get("idle_time_absolute"));
        }
        System.out.println(machineValues);

        GeneralStats gStats = new GeneralStats();
        HashMap<String, Object> generalValues = evaluator.calculateGeneralStats(machineValues, jobValues);
        gStats.totalResourceUtilization.addValue((double)generalValues.get("total_resource_utilisation"));
        gStats.totalCost.addValue((double)generalValues.get("total_cost"));
        gStats.totalCompletionTime.addValue((double)generalValues.get("total_completion_time"));
        System.out.println(gStats.toJsonObject().toString());

        var opStats = new ArrayList<OperationStats>();
        for(Operation op : ops) opStats.add(new OperationStats(op));

        HashMap<Operation, HashMap<String, Object>> operationValues = evaluator.calculateOperationStats(machineValues);
        for (OperationStats opStat : opStats) {
            opStat.length.addValue((double)(int) operationValues.get(opStat.getOperation()).get("length"));
        }

        assertEquals(3, 3);
    }
}