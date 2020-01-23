/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.I2C.*;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.util.*;
import edu.wpi.first.wpilibj.TimedRobot;
import com.revrobotics.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
    final Port i2c = Port.kOnboard;
    final ColorSensorV3 sensor = new ColorSensorV3(i2c);
    final ColorMatch matcher = new ColorMatch();
    final Color blue = ColorMatch.makeColor(0.125, 0.43, 0.44);
    final Color green = ColorMatch.makeColor(0.165, 0.585, 0.25);
    final Color red = ColorMatch.makeColor(0.54, 0.34, 0.12);
    final Color yellow = ColorMatch.makeColor(0.32, 0.56, 0.12);

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        matcher.addColorMatch(green);
        matcher.addColorMatch(blue);
        matcher.addColorMatch(red);
        matcher.addColorMatch(yellow);
    }

    @Override
    public void autonomousInit() {
    }

    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
    }

    @Override
    public void teleopPeriodic() {
        String color = "unknown";
        Color returned = sensor.getColor();
        ColorMatchResult result = matcher.matchClosestColor(returned);

        if (result.color == green) {
            color = "green";
        }
        else if (result.color == blue) {
            color = "blue";
        }
        else if (result.color == red) {
            color = "red";
        }
        else if (result.color == yellow) {
            color = "yellow";
        }

        SmartDashboard.putString("Color", color);
        SmartDashboard.putNumber("Red", returned.red);
        SmartDashboard.putNumber("Green", returned.green);
        SmartDashboard.putNumber("Blue", returned.blue);
        SmartDashboard.putNumber("Confidence", result.confidence);
    }

    @Override
    public void testInit() {
    }

    @Override
    public void testPeriodic() {
    }

}
