package net.gruppe4.DiscreteEventSimulation;

import net.gruppe4.DiscreteEventSimulation.simulation.Simulation;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Job;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Machine;
import net.gruppe4.DiscreteEventSimulation.simulation.model.Operation;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SimulationTest {
    @Test
    void testSimulaton() {
        Machine machineA = new Machine("A");
        Machine machineB = new Machine("B");
        Machine machineC = new Machine("C");

        Job job1 = new Job("1", 0);
        Job job2 = new Job("2", 2);

        ArrayList<Operation> ops = new ArrayList<Operation>();

        Operation operation1A = new Operation(job1, machineA, 20, null);
        Operation operation1B = new Operation(job1, machineB, 3, operation1A);
        Operation operation1C = new Operation(job1, machineC, 60, operation1B);
        Operation operation2A = new Operation(job2, machineA, 30, null);
        Operation operation2B = new Operation(job2, machineB, 3, operation2A);
        Operation operation2C = new Operation(job2, machineC, 60, operation2B);

        ops.add(operation1A);
        ops.add(operation1B);
        ops.add(operation1C);
        ops.add(operation2A);
        ops.add(operation2B);
        ops.add(operation2C);

        Simulation sim = new Simulation(ops);

        assertTrue(true);
    }

}