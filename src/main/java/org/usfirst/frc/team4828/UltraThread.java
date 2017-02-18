package org.usfirst.frc.team4828;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Timer;

import java.util.*;

/** A Thread for finding distance using the MB1200 XL-MaxSonar-EZ0 Sensor.
 *  Can be used to find both Inches and Centimeters. Uses a Median Filter.
 *
 * @see Thread
 */

public class UltraThread extends Thread {

    private static final int WINDOW_SIZE = 30;
    private static final double SUPPLIED_VOLTAGE = 5.0;

    private Thread t;
    private String threadName = "ultrasonic thread";
    private AnalogInput sensor;
    private boolean enabled;
    private Queue<Double> values;

    public double distCm = 0;
    public double distIn = 0;

    /**
     * Create a new UltraThread with a given port.
     * Port should be an Analog port.
     *
     * @param port the port that the sensor is connected to
     */
    public UltraThread(int port) {
        sensor = new AnalogInput(port);
        values = new LinkedList<>();
        System.out.println("Thread starting");
    }

    /**
     * Finds the median in a List of values.
     *
     * @param values a List of Doubles
     * @return the median
     */
    private double medianFilter(Queue<Double> values) {
        int half = values.size() / 2;
        if (values.size() == 0) {
            return 0.0;
        }
        List<Double> temp = new ArrayList<>(values);
        Collections.sort(temp);
        if (values.size() % 2 == 1) {
            return temp.get(half);
        } else {
            return (temp.get(half - 1) + temp.get(half)) / 2;
        }
    }

    /**
     * Converts a number to Centimeters.
     *
     * @param voltage number to convert
     * @return converted number
     */
    private double toCm(double voltage) {
        return voltage / (SUPPLIED_VOLTAGE / 1024);
    }

    /**
     * Converts from Centimeters to Inches.
     *
     * @param voltage number to convert
     * @return converted number
     */
    private double toIn(double voltage) {
        return toCm(voltage) / 2.54;
    }

    /** The contents of the thread
     * loops while it's alive
     */
    public void run() {
        while (enabled) {
            values.add(sensor.getVoltage());

            while (values.size() > WINDOW_SIZE) {
                values.remove();
            }

            distCm = toCm(medianFilter(values));
            distIn = toIn(medianFilter(values));
            Timer.delay(0.1);
        }
    }

    /**
     * Starts the thread.
     */
    public void start() {
        enabled = true;
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }

    /**
     * Stops the thread.
     */
    public void terminate() {
        enabled = false;
        t = null;
    }
}
