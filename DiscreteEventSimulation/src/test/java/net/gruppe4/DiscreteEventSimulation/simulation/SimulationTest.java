package net.gruppe4.DiscreteEventSimulation.simulation;

import net.gruppe4.DiscreteEventSimulation.evaluation.LogEvaluator;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

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


        Operation op1 = new Operation("op1", null, null, 0, 8, mA, 1., 2.);
        Operation op2 = new Operation("op2", op1, null, 0, 5, mA, 0., 0.);
        Operation op3 = new Operation("op3", op2, null, 0, 3, mA, 0., 0.);

        Operation op4 = new Operation("op4", null, null, 0, 4, mB, 0., 0.);
        Operation op5 = new Operation("op5", op4, null, 0, 8, mB, 0., 0.);
        Operation op6 = new Operation("op6", op5, null, 0, 6, mB, 0., 0.);

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

        ArrayList<EventLog> logs = new ArrayList<EventLog>();
        logs.add(log);
        LogEvaluator evaluator = new LogEvaluator(logs);
        System.out.println(evaluator.calculateAbsoluteMachineUsage(log.getMachineLog(mC)));
        System.out.println(evaluator.calculateMachineCapacityUtilizationMean(mC));

        // Versuche den Test auszuführen und schau dir die entstehenden Fehler an
        assertEquals(3, 3);
    }
}