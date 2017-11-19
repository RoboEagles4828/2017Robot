package org.usfirst.frc.team4828;

import edu.wpi.first.wpilibj.*;
import org.usfirst.frc.team4828.vision.Pixy;

public class Robot extends IterativeRobot {
    private Joystick driveStick, secondaryStick;
    private DriveTrain drive;
    private Ultrasonic ultrasonic;
    private Pixy pixy;
    private Shooter rightShooter, leftShooter;
    private DigitalInput[] dipSwitch;
    private int autonSelect;
    private Climber climb;
    private Hopper hopper;
    private GearGobbler gearGobbler;
    private Thread pixyThread;
    private Thread ultraThread;

    @Override
    public void robotInit() {
        super.robotInit();
        System.out.println("Starting initialization");

        //JOYSTICKS
        driveStick = new Joystick(0);
        secondaryStick = new Joystick(1);

        //DRIVETRAIN
        drive = new DriveTrain(
                Ports.DT_FRONT_LEFT,
                Ports.DT_BACK_LEFT,
                Ports.DT_FRONT_RIGHT,
                Ports.DT_BACK_RIGHT
        );

        //CLIMBING
        climb = new Climber(Ports.CLIMBER_1, Ports.CLIMBER_2, Ports.HALLEFFECT_PORT);

        //HOPPER
        hopper = new Hopper(Ports.AGITATOR, Ports.INTAKE);

        //SHOOTERS
        // Master is the one on the right if you are looking at the back of the shooter
        rightShooter = new Shooter(Ports.MOTOR_RIGHT, Ports.SERVO_RIGHT_MASTER, Ports.SERVO_RIGHT_SLAVE, Ports.INDEXER_RIGHT);
        leftShooter = new Shooter(Ports.MOTOR_LEFT, Ports.SERVO_LEFT_MASTER, Ports.SERVO_LEFT_SLAVE, Ports.INDEXER_LEFT);
        rightShooter.servos.calibrate(1, .3, 0);
        rightShooter.servos.calibrate(2, .6, 1);
        leftShooter.servos.calibrate(1, .75, .5);
        leftShooter.servos.calibrate(2, .35, .7);

        //GEAR GOBBLER
        gearGobbler = new GearGobbler(Ports.LEFT_GEAR_GOBBLER, Ports.RIGHT_GEAR_GOBBLER);
        gearGobbler.servo.calibrate(2, 1, .8);
        gearGobbler.servo.calibrate(1, 0, .25);

        //AUTON SELECT
        dipSwitch = new DigitalInput[4];
        dipSwitch[0] = new DigitalInput(Ports.DIPSWITCH_1);
        dipSwitch[1] = new DigitalInput(Ports.DIPSWITCH_2);
        dipSwitch[2] = new DigitalInput(Ports.DIPSWITCH_3);
        dipSwitch[3] = new DigitalInput(Ports.DIPSWITCH_4);

        //THREADS
        ultrasonic = new Ultrasonic(Ports.US_CHANNEL);
        pixy = new Pixy(ultrasonic);
        pixyThread = new Thread(pixy, "Pixy");
        ultraThread = new Thread(ultrasonic, "Ultrasonic");
        ultraThread.start();

        //ZERO SERVOS
        rightShooter.servos.set(0);
        leftShooter.servos.set(0);
        gearGobbler.retract();
        gearGobbler.close();
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();
        autonSelect = 0;
        for (int i = 0; i < 4; i++) {
            // Bit shift the switches repeatedly to read it into an int
            autonSelect += (dipSwitch[i].get() ? 1 : 0) * (1 << i);
        }
        gearGobbler.retract();
        gearGobbler.close();
        System.out.println("Entering auton number " + autonSelect);
        drive.reset();
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();
        double distance = 93.3;
        double speed = 0.75;
        double shooterServosPos = 0.4;
        switch (autonSelect) {
            case 0:
                // Do nothing
                System.out.println("Auton 0: Doing nothing");
                break;
            case 1:
                // Shoot 10
                rightShooter.servos.set(shooterServosPos);
                leftShooter.servos.set(shooterServosPos);
                rightShooter.spinUp(25000);
                leftShooter.spinUp(25000);
                hopper.stir();
                break;
            case 2:
                // Place gear on left side
                drive.moveDistance(distance, speed);
                drive.placeGearAuton(0, pixy, ultrasonic, gearGobbler);
                break;
            case 3:
                // Place gear on center
                drive.moveDistance(distance / 3, speed);
                drive.placeGearAuton(1, pixy, ultrasonic, gearGobbler);
                break;
            case 4:
                // Place gear on right side
                drive.moveDistance(distance, speed);
                drive.placeGearAuton(2, pixy, ultrasonic, gearGobbler);
                break;
            case 5:

            case 15:
                // Do nothing
                System.out.println("Auton 15: Doing nothing");
                break;
            default:
                // Do nothing
                System.out.println("Auton " + autonSelect + " not found: Doing nothing");
        }
        Timer.delay(.1);
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
        System.out.println("Entering teleop...");
    }

    @Override
    public void teleopPeriodic() {
        super.teleopPeriodic();

        double magnitude = ((-driveStick.getThrottle()) + 1) / 2;

        //DRIVE
        drive.mecanumDrive(driveStick.getX(), driveStick.getY(), driveStick.getTwist() / 2);

        //INTAKE
        if (driveStick.getRawButton(11) || secondaryStick.getRawButton(11)) {
            hopper.intake();
        } else {
            hopper.stopIntake();
        }

        //GYRO and ENCODERS
        if (driveStick.getRawButton(7) || secondaryStick.getRawButton(7)) {
            drive.reset();
            drive.zeroEncoders();
        }

        //CLIMBER
        if (driveStick.getRawButton(8) || secondaryStick.getRawButton(8)) {
            climb.printDebug();
            if(!climb.isRaised()) {
            	climb.raise();
            }
        } else if (driveStick.getRawButton(9)) {
            climb.reset();
        } else if (secondaryStick.getRawButton(12)) {
            climb.raise(-.3);
        } else {
            climb.stop();
        }

        //GEAR PLACEMENT
        if (driveStick.getRawButton(12)) {
            gearGobbler.open();
            Timer.delay(.5);
            gearGobbler.push();
            Timer.delay(.4);
        } else {
            gearGobbler.retract();
            gearGobbler.close();
        }

        if (driveStick.getRawButton(5)) {
            drive.debugEncoders();
        }

        //SHOOTING
        if (driveStick.getRawButton(1) || secondaryStick.getRawButton(1)) {
            rightShooter.spinUp((int) (magnitude * 30000));
            leftShooter.spinUp((int) (magnitude * 30000));
            //leftShooter.spinUpAbsolute(magnitude);
            //rightShooter.spinUpAbsolute(magnitude*.9);
            hopper.stir();
            System.out.println(leftShooter + "     " + rightShooter);
        } else {
            leftShooter.spinDown();
            rightShooter.spinDown();
            hopper.stopStir();
        }

        //SHOOTER SERVOS
        if (driveStick.getRawButton(10) || secondaryStick.getRawButton(10)) {
            leftShooter.servos.set(magnitude);
            rightShooter.servos.set(magnitude * .8);
            //System.out.println(leftShooter.servos + "      " + rightShooter.servos);
        }

        if (driveStick.getRawButton(2)) {
            System.out.println("navx " + drive + "    pixy data: " + ultrasonic.getDist());
            System.out.println(magnitude * 30000 + "     " + magnitude);
        }
    }

    @Override
    public void testInit() {
        super.testInit();
        System.out.println("Entering test...");
        pixyThread = new Thread(pixy, "Pixy Thread");
        pixyThread.start();
        drive.zeroEncoders();
    }

    @Override
    public void testPeriodic() {
        double temp = ((-driveStick.getThrottle()) + 1) / 2;
        double offset = pixy.horizontalOffset();

        if (driveStick.getRawButton(6)) {
            System.out.print("pixy: " + offset);
            System.out.println("2 blocks?   " + pixy.blocksDetected());
            System.out.println("ultra data: " + ultrasonic.getDist());
            drive.debugGyro();
        }

        if (driveStick.getRawButton(1)) {
            if (driveStick.getRawButton(12)) {
                drive.placeGear(pixy, ultrasonic, gearGobbler);
            } else if (driveStick.getRawButton(7)) {
                drive.mecanumDriveAbsolute(0, 0, drive.scaledRotation(270) * 1.1);
            } else if (driveStick.getRawButton(8)) {
                double tempSpeed = drive.getGyroDebug();
                while (drive.getGyroDebug() < tempSpeed + 3) {
                    drive.mecanumDriveAbsolute(0, -.3, 0);
                }
            } else if (driveStick.getRawButton(5)) {
                drive.mecanumDriveAbsolute(temp - .5, -.015 * Math.signum(temp - .5), 0);
            } else if (driveStick.getRawButton(2)) {
                drive.mecanumDrive(driveStick.getX(), driveStick.getY(), driveStick.getTwist() / 2);
                drive.debugEncoders();
            } else {
                drive.brake();
                drive.gearRoutineProgress = 0;
            }
        }

        if (driveStick.getRawButton(4)) {
            drive.reset();
            drive.zeroEncoders();
        }


        Timer.delay(.05);
    }

    @Override
    public void disabledInit() {
        System.out.println("Disabling robot");
        pixy.terminate();
    }
}
