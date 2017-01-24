package org.usfirst.frc.team4828;

import org.junit.Assert;
import org.junit.Test;

public class RobotTest {
    @Test
    public void testTheThings() {
        System.out.println("Test ran!");
        Assert.assertTrue(true);
    }

    @Test
    public void anotherTest() {
        Assert.assertEquals("Test that 1 = 1", 1, 1);
    }

    @Test
    public void checkPorts() {
        int[] ports = {1, 2, 3, 4};

        Robot r = new Robot();
        Assert.assertArrayEquals(r.drive.getPorts(), ports);
    }
}
