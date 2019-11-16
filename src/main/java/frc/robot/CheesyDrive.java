package frc.robot;

import static frc.robot.Constants.kDeadZone;
import static frc.robot.Constants.kMaxTurnPower;

public class CheesyDrive {
    private Drive drive = Drive.getInstance();

    private double mLastSteer = 0.0;
    private double kInertiaSteerMore = 0.0;
    private double kInertiaSteerLess = 0.5;

    public void setDrive(double throttle, double turn, boolean spinTurn) {
        double leftPower = 0.0, rightPower = 0.0;
        if (spinTurn) {
            if (Math.abs(turn) >= kDeadZone) {
                leftPower = turn;
                rightPower = -turn;
            }
        }
        else {
            if (Math.abs(throttle) >= kDeadZone) {
                throttle = Math.pow(throttle, 3);
                if (Math.abs(turn) < kDeadZone) {
                    leftPower = throttle;
                    rightPower = throttle;
                }
                else {
                    if (turn > 0.0) {
                        rightPower = 1.0;
                        leftPower = -1.0 + 2.0*turn;
                    }
                    else {
                        leftPower = 1.0;
                        rightPower = 1.0 - 2.0*turn;
                    }
                }
                leftPower *= throttle;
                rightPower *= throttle;
            }
        }
        drive.setOpenLoop(leftPower, rightPower);
    }

    public void setDrive2(double throttle, double steer, boolean turn) {
        double leftPower = 0.0, rightPower = 0.0;
        if (throttle >= kDeadZone) {
            if (!turn) {
                leftPower = rightPower = 1.0;
            }
            else {
                double inertia = steer - mLastSteer;
                double inertiaScalar = (inertia*steer >= 0) ? kInertiaSteerMore : kInertiaSteerLess;
                steer += inertiaScalar*inertia;
                if (steer >= 0.0) {
                    leftPower = 1.0;
                    rightPower = -1.0+2*steer;
                    rightPower = (rightPower > kMaxTurnPower) ? kMaxTurnPower : rightPower;
                }
                else {
                    rightPower = 1.0;
                    leftPower = -1.0-2*steer;
                    leftPower = (rightPower > kMaxTurnPower) ? kMaxTurnPower : leftPower;
                }
            }
        }
        drive.setOpenLoop(leftPower*throttle, rightPower*throttle);
    }
}
