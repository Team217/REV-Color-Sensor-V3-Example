package frc.robot;

import com.revrobotics.*;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.util.*;

public class ColorSensor {
    public static Port i2c = Port.kOnboard;
    public static ColorSensorV3 sensor = new ColorSensorV3(i2c);
    public static ColorMatch matcher = new ColorMatch();

    public static final Color GREEN = ColorMatch.makeColor(0.165, 0.585, 0.25);
    public static final Color BLUE = ColorMatch.makeColor(0.125, 0.43, 0.44);
    public static final Color YELLOW = ColorMatch.makeColor(0.32, 0.56, 0.12);
    public static final Color RED = ColorMatch.makeColor(0.54, 0.34, 0.12);

    /**
     * Initializes the color matcher for the color sensor.
     */
    public static void init() {
        matcher.addColorMatch(GREEN);
        matcher.addColorMatch(BLUE);
        matcher.addColorMatch(YELLOW);
        matcher.addColorMatch(RED);
    }

    /**
     * Gets color sensor data and posts it on SmartDashboard.
     */
    public static void execute() {
        String color = "unknown";
        Color returned = sensor.getColor();
        ColorMatchResult result = matcher.matchClosestColor(returned);

        if (result.color == GREEN) {
            color = "green";
        }
        else if (result.color == BLUE) {
            color = "blue";
        }
        else if (result.color == YELLOW) {
            color = "yellow";
        }
        else if (result.color == RED) {
            color = "red";
        }

        SmartDashboard.putString("Color", color);
        SmartDashboard.putNumber("Red", returned.red);
        SmartDashboard.putNumber("Green", returned.green);
        SmartDashboard.putNumber("Blue", returned.blue);
        SmartDashboard.putNumber("Confidence", result.confidence);
    }

    /**
     * Returns {@code true} if the color read by the color sensor matches the target color.
     * 
     * @param color
     *        The target color
     */
    public static boolean isTargetColor(Color color) {
        Color returned = sensor.getColor();
        ColorMatchResult result = matcher.matchClosestColor(returned);

        if (result.color == color) {
            return true;
        }
        return false;
    }

    /**
     * Returns {@code true} if the color read by the color sensor matches the FMS-given color.
     */
    public static boolean isFMSColor() {
        String fms = DriverStation.getInstance().getGameSpecificMessage();

        if (fms.length() > 0) {
            Color targetColor;

            switch(fms.charAt(0)) { // 2 color offset
            case 'G':
                targetColor = YELLOW;
                break;
            case 'B':
                targetColor = RED;
                break;
            case 'Y':
                targetColor = GREEN;
                break;
            case 'R':
                targetColor = BLUE;
                break;
            default:
                return false;
            }
    
            return isTargetColor(targetColor);
        }

        return false;
    }
}