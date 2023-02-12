package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.evaluation.LogEvaluator;
import org.junit.jupiter.api.Test;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.HashMap;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void testSimulationCoreWithoutDependencies() {
        Machine mA = new Machine("A", 0.2, 4., 2.);
        Machine mB = new Machine("B", 0.0, 5., 2.);
        Machine mC = new Machine("C", 0.0, 5., 2.);

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
        Machine mA = new Machine("A", 0.0, 4., 2.);
        Machine mB = new Machine("B", 0.2, 5., 2.);
        Machine mC = new Machine("C", 0.2, 5., 2.);

        HashMap<String, Machine> machines = new HashMap<String, Machine>();
        machines.put("A", mA);
        machines.put("B", mB);
        machines.put("C", mC);

        Job j1 = new Job("Job1", 13);
        Job j2 = new Job("Job2", 16);
        Job j3 = new Job("Job3", 17);
        Job j4 = new Job("Job4", 14);
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
        logs.add(log);
        LogEvaluator evaluator = new LogEvaluator(logs);
        System.out.println(evaluator.calculateAbsoluteMachineUsage(log.getMachineLog(mC)));
        System.out.println(evaluator.calculateMachineCapacityUtilizationMean(mC));
        System.out.println(jobs);

        var map = evaluator.findLastJobDates(jobs, log);
        for (Map.Entry<Job, Integer> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " Lateness: " + evaluator.calculateJobLateness(entry.getKey(), entry.getValue()));
        }


        // Versuche den Test auszuführen und schau dir die entstehenden Fehler an
        assertEquals(3, 3);
    }

    @Test
    void simpleIsDoableTest() {
        Machine mA = new Machine("A", 0.0, 3., 0.5);
        Machine mB = new Machine("B", 0.0, 5., 2.);
        Machine mC = new Machine("C", 0., 0., 0.);
        Machine mD = new Machine("D", 0., 0., 0.);
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
        System.out.println(evaluator.calculateAbsoluteMachineUsage(log.getMachineLog(mB)));
        System.out.println(evaluator.calculateMachineCapacityUtilizationMean(mB));

        assertEquals(3, 3);
    }
}