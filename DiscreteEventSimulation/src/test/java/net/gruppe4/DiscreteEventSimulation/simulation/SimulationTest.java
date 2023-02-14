package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.evaluation.JobStats;
import net.gruppe4.DiscreteEventSimulation.evaluation.LogEvaluator;
import net.gruppe4.DiscreteEventSimulation.evaluation.MachineStats;
import net.gruppe4.DiscreteEventSimulation.evaluation.StatisticalValues;
import org.junit.jupiter.api.Test;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void testSimulationCoreWithoutDependencies() {
        Machine mA = new Machine("A", 0.2, 4., 2., 4., 3.);
        Machine mB = new Machine("B", 0.0, 5., 2., 2., 2.);
        Machine mC = new Machine("C", 0.0, 5., 2., 2., 3.);

        HashMap<String, Machine> machines = new HashMap<String, Machine>();
        machines.put("A", mA);
        machines.put("B", mB);
        machines.put("C", mC);


        Operation op1 = new Operation("op1", null, null, 0, 5, mA, 0., 0.);
        Operation op2 = new Operation("op2", op1, null, 0, 5, mA, 0., 0.);
        Operation op3 = new Operation("op3", op2, null, 0, 3, mA, 0., 0.);

        Operation op4 = new Operation("op4", null, null, 0, 4, mB, 0., 0.);
        Operation op5 = new Operation("op5", op4, null, 0, 8, mB, 0., 0.);
        Operation op6 = new Operation("op6", op5, null, 0, 4, mB, 0., 0.);

        Operation op7 = new Operation("op7", null, null, 0, 6, mC, 0., 0.);
        Operation op8 = new Operation("op8", op7, null, 9, 5, mC, 0., 0.);
        Operation op9 = new Operation("op9", op8, null, 0, 3, mC, 0., 0.);

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

        // Versuche den Test auszuführen und schau dir die entstehenden Fehler an
        assertEquals(3, 3);
    }

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

        Operation op1 = new Operation("op1", null, null, 0, 5, mA, 0., 0.);
        op1.setJob(j2);
        Operation op2 = new Operation("op2", op1, null, 0, 5, mA, 0., 0.);
        op2.setJob(j2);
        Operation op3 = new Operation("op3", op2, null, 0, 3, mA, 0., 0.);
        op3.setJob(j1);

        Operation op4 = new Operation("op4", null, null, 0, 4, mB, 0., 0.);
        op4.setJob(j3);
        Operation op5 = new Operation("op5", op4, null, 0, 8, mB, 0., 0.);
        op5.setJob(j3);
        Operation op6 = new Operation("op6", op5, null, 0, 6, mB, 0., 0.);
        op6.setJob(j2);

        Operation op7 = new Operation("op7", null, null, 0, 6, mC, 0., 0.);
        op7.setJob(j1);
        Operation op8 = new Operation("op8", op7, null, 9, 5, mC, 0., 0.);
        op8.setJob(j4);
        Operation op9 = new Operation("op9", op8, null, 0, 3, mC, 0., 0.);
        op9.setJob(j3);


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


        assertEquals(3, 3);
    }

    @Test
    void simpleIsDoableTest() {
        Machine mA = new Machine("A", 0.0, 3., 0.5, 4., 2.);
        Machine mB = new Machine("B", 0.0, 5., 2., 5., 2.);
        Machine mC = new Machine("C", 0., 0., 0., 5., 3.);
        Machine mD = new Machine("D", 0., 0., 0., 4., 2.);
        HashMap<String, Machine> machines = new HashMap<String, Machine>();
        machines.put("A", mA);
        machines.put("B", mB);
        machines.put("C", mC);
        machines.put("D", mD);

        Operation op1 = new Operation("op1", null, null, 0, 10, mA, 1., 4.);
        ArrayList<Operation> condPred = new ArrayList<Operation>();
        condPred.add(op1);
        Operation op2 = new Operation("op2", null, condPred, 0, 15, mB, 1., 3.);
        Operation op3 = new Operation("op3", null, null, 0, 5, mC, 1., 1.);
        var condPred2 = new ArrayList<Operation>();
        condPred2.add(op3);
        Operation op4 = new Operation("op4", null, condPred2, 0, 15, mD, 1., 3.);
        Operation op5 = new Operation("op5", op3, null, 0, 5, mC, 1., 2.);

        ArrayList<Operation> ops = new ArrayList<Operation>();
        ops.add(op1);
        ops.add(op2);
        ops.add(op3);
        ops.add(op4);
        ops.add(op5);

        Simulation sim = new Simulation(machines, ops);
        EventLog log = sim.runSim();
        System.out.println(log);

        ArrayList<EventLog> logs = new ArrayList<EventLog>();
        logs.add(log);
        LogEvaluator evaluator = new LogEvaluator(logs);



        assertEquals(3, 3);
    }

    @Test
    void simpleStatCalculationTest(){
        StatisticalValues test = new StatisticalValues();

        test.addValue(1);
        test.addValue(2);
        test.addValue(3);
        test.addValue(4);

        assertEquals(2.5,test.getMean());
        assertEquals(1.25,test.getVariance());
        assertEquals(1,test.getMin());
        assertEquals(4,test.getMax());

    }
}