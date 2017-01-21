package org.usfirst.frc.team4828;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import com.kauailabs.navx.frc.AHRS;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {
    Joystick driveStick;
    DriveTrain drive;
    static AHRS navx;

    @Override
    public void robotInit() {
        super.robotInit();
        System.out.println("THE ROBOT TURNED ON");
        driveStick = new Joystick(0);
        drive = new DriveTrain(1, 2, 3, 4);
        navx = new AHRS(SPI.Port.kMXP);
    }

    @Override
    public void autonomousInit() {
        super.autonomousInit();
        System.out.println("Entering auton...");
    }

    @Override
    public void autonomousPeriodic() {
        super.autonomousPeriodic();
    }

    @Override
    public void teleopInit() {
        super.teleopInit();
        System.out.println("Entering teleop...");
    }

    @Override
    public void teleopPeriodic() {
        super.teleopPeriodic();
        drive.mecanumDrive(driveStick.getX() / 2, driveStick.getY() / 2, driveStick.getTwist() / 2, navx.getAngle());
        if (driveStick.getRawButton(11)) {
            navx.reset();
        }
        System.out.println("Angle: " + navx.getAngle());
        Timer.delay(0.05);
    }

    @Override
    public void testInit() {
        super.testInit();
        System.out.println("Entering test...");
    }

    @Override
    public void testPeriodic() {
        System.out.println("Angle: " + navx.getAngle());
    }
}
