package org.usfirst.frc.team4828;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.hal.PDPJNI;

public class Climber {

    private Victor climberMotor1;
    private Victor climberMotor2;
    private DigitalInput hallEffect;
    private static final int MAX_CURRENT = 35;
    private static final int MODULE_1 = 0;
    private static final int MODULE_2 = 13;
    private static final double MOTOR_SPEED = 0.8;
    private static final double RESET_SPEED = 0.2;

    /**
     * Create climber object encapsulating the climber motor.
     *
     * @param motorPort1 port of the first climber motor todo: figure out which one is left/right
     * @param motorPort2 port of the second climber motor
     */
    public Climber(int motorPort1, int motorPort2, int halleffectPort) {
        climberMotor1 = new Victor(motorPort1);
        climberMotor2 = new Victor(motorPort2);
        hallEffect = new DigitalInput(halleffectPort);
        
    }
    
    public boolean isRaised() {
    	return PDPJNI.getPDPTotalCurrent(MODULE_1) > MAX_CURRENT || PDPJNI.getPDPTotalCurrent(MODULE_2) > MAX_CURRENT;
    }

    /**
     * Raise the robot at a given speed.
     *
     */
    public void raise(double speed) {
        climberMotor1.set(-speed);
        climberMotor2.set(speed);
    }

    /**
     * Raise the robot at the default speed.
     */
    public void raise() {
        climberMotor1.set(-MOTOR_SPEED);
        climberMotor2.set(MOTOR_SPEED);
    }

    /**
     * Move the hook to face forward using hall effect sensor.
     */
    public void reset() {
        if (hallEffect.get()) {
            raise(RESET_SPEED);
        }
        else{
            stop();
        }
    }

    public void reset(double speed) {
        if (hallEffect.get()) {
            raise(speed);
        }
        else{
            stop();
        }
    }

    /**
     * Stop raising the robot.
     */
    public void stop() {
        climberMotor1.set(0);
        climberMotor2.set(0);
    }

    public void printDebug() {
        System.out.println(hallEffect.get());
    }

}
