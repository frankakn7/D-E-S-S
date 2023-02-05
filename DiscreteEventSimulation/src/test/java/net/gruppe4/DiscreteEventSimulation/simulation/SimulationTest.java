package net.gruppe4.DiscreteEventSimulation.simulation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {

    @Test
    void testSimulationCoreWithoutDependencies() {
        Machine mA = new Machine("A");
        Machine mB = new Machine("B");
        Machine mC = new Machine("C");

            HashMap<String, Machine> machines = new HashMap<String, Machine>();
        machines.put("A", mA);
        machines.put("B", mB);
        machines.put("C", mC);


        Operation op1 = new Operation("op1", null, null, 0, 5, mA);
        Operation op2 = new Operation("op2", op1, null, 0, 5, mA);
        Operation op3 = new Operation("op3", op2, null, 0, 3, mA);

        Operation op4 = new Operation("op4", null, null, 0, 4, mB);
        Operation op5 = new Operation("op5", op4, null, 0, 8, mB);
        Operation op6 = new Operation("op6", op5, null, 0, 4, mB);

        Operation op7 = new Operation("op7", null, null, 0, 6, mC);
        Operation op8 = new Operation("op8", op7, null, 9, 5, mC);
        Operation op9 = new Operation("op9", op8, null, 0, 3, mC);

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
}