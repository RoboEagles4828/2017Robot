package org.usfirst.frc.team4828;

import com.ctre.CANTalon;

public class DriveTrain {
    private CANTalon frontLeft;
    private CANTalon frontRight;
    private CANTalon backLeft;
    private CANTalon backRight;

    private static final double TWIST_THRESHOLD = 0.15;

    /**
     * Create drive train object containing mecanum motor functionality.
     * @param frontLeftPort port of the front left motor
     * @param backLeftPort port of the back left motor
     * @param frontRightPort port of the front right motor
     * @param backRightPort port of the back right motor
     */
    public DriveTrain(int frontLeftPort, int backLeftPort, int frontRightPort, int backRightPort) {
        frontLeft = new CANTalon(frontLeftPort);
        frontRight = new CANTalon(frontRightPort);
        backLeft = new CANTalon(backLeftPort);
        backRight = new CANTalon(backRightPort);
        frontLeft.setPID(0.6, 0, 0);
        frontRight.setPID(0.6, 0, 0);
        backLeft.setPID(0.6, 0, 0);
        backRight.setPID(0.6, 0, 0);
    }

    public DriveTrain(){
        //for testing purposes
        System.out.println("Created dummy drivetrain");
    }

    /**
     * Ensures that wheel speeds are valid numbers
     */
    public static void normalize(double[] wheelSpeeds) {
        double maxMagnitude = Math.abs(wheelSpeeds[0]);
        for (int i = 1; i < 4; i++) {
            double temp = Math.abs(wheelSpeeds[i]);
            if (maxMagnitude < temp) {
                maxMagnitude = temp;
            }
        }
        if (maxMagnitude > 1.0) {
            for (int i = 0; i < 4; i++) {
                wheelSpeeds[i] = wheelSpeeds[i] / maxMagnitude;
            }
        }
    }

    /**
     * Rotate a vector in Cartesian space.
     *
     * @param xcomponent X component of the vector
     * @param ycomponent Y component of the vector
     * @param angle      Angle by which to rotate the vector
     * @return The resultant vector as a double[2]
     */
    public static double[] rotateVector(double xcomponent, double ycomponent, double angle) {
        double cosA = Math.cos(angle * (3.14159 / 180.0));
        double sinA = Math.sin(angle * (3.14159 / 180.0));
        double[] out = new double[2];
        out[0] = xcomponent * cosA - ycomponent * sinA;
        out[1] = xcomponent * sinA + ycomponent * cosA;
        return out;
    }

    /**
     * Adjust motor speeds according to joystick input.
     */
    public void mecanumDrive(double xcomponent, double ycomponent, double rotation) {
        mecanumDrive(xcomponent, ycomponent, rotation, 0);
    }

    /**
     * Adjust motor speeds according to heading and joystick input.
     * Uses input from the gyroscope to determine field orientation.
     */
    public void mecanumDrive(double xcomponent, double ycomponent,
                             double rotation, double gyroAngle) {
        // Ignore tiny inadvertent joystick rotations
        if (Math.abs(rotation) <= TWIST_THRESHOLD) {
            rotation = 0.0;
        }

        // Negate y for the joystick.
        ycomponent = -ycomponent;
        // Compensate for gyro angle.
        double[] rotated = rotateVector(xcomponent, ycomponent, gyroAngle);
        xcomponent = rotated[0];
        ycomponent = rotated[1];

        double[] wheelSpeeds = new double[4];
        wheelSpeeds[0] = xcomponent + ycomponent + rotation;
        wheelSpeeds[1] = -xcomponent + ycomponent - rotation;
        wheelSpeeds[2] = -xcomponent + ycomponent + rotation;
        wheelSpeeds[3] = xcomponent + ycomponent - rotation;

        normalize(wheelSpeeds);
        frontLeft.set(wheelSpeeds[0]);
        frontRight.set(wheelSpeeds[1]);
        backLeft.set(wheelSpeeds[2]);
        backRight.set(wheelSpeeds[3]);
    }

    /**
     * Turn all wheels slowly for testing purposes.
     */
    public void testMotors() {
        frontLeft.set(.2);
        frontRight.set(.2);
        backLeft.set(.2);
        backRight.set(.2);
    }

    /**
     * Turn all wheels at set speeds
     *
     * @param fl Speed for Front Left Wheel
     * @param fr Speed for Front Right Wheel
     * @param bl Speed for Back Left Wheel
     * @param br Speed for Back Right Wheel
     */
    public void testMotors(int fl, int fr, int bl, int br) {
        frontLeft.set(fl);
        frontRight.set(fr);
        backLeft.set(bl);
        backRight.set(br);
    }

    /**
     * Use PID to lock the robot in its current position
     */
    public void lock(){
        frontLeft.changeControlMode(CANTalon.TalonControlMode.Position);
        frontRight.changeControlMode(CANTalon.TalonControlMode.Position);
        backRight.changeControlMode(CANTalon.TalonControlMode.Position);
        backLeft.changeControlMode(CANTalon.TalonControlMode.Position);
        frontLeft.set(frontLeft.get());
        frontRight.set(frontRight.get());
        backRight.set(backRight.get());
        backLeft.set(backLeft.get());
    }

    /**
     * Set the motors back to normal speed control
     */
    public void unlock(){
        frontLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        frontRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        backRight.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        backLeft.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }
}
