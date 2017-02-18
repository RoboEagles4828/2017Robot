package org.usfirst.frc.team4828;

import edu.wpi.first.wpilibj.Spark;

public class Hopper {

    private static final double AGITATOR_SPEED = 0.5;
    private static final double INTAKE_SPEED = 0.5;

    private Spark leftMotor;
    private Spark rightMotor;
    private Spark intakeMotor;

    /**
     * Create hopper encapsulating the intake and agitator functionality.
     * @param leftPort port of the left agitator motor
     * @param rightPort port of the right agitator motor
     * @param intakePort port of the intake motor
     */
    public Hopper(int leftPort, int rightPort, int intakePort){
        leftMotor = new Spark(leftPort);
        rightMotor = new Spark(rightPort);
        intakeMotor = new Spark(intakePort);
    }

    public void stir(){
        leftMotor.set(AGITATOR_SPEED);
        rightMotor.set(AGITATOR_SPEED);
    }

    public void stopStir(){
        leftMotor.set(0);
        rightMotor.set(0);
    }

    public void intake(){
        intakeMotor.set(INTAKE_SPEED);
    }

    public void stopIntake(){
        intakeMotor.set(0);
    }

}
