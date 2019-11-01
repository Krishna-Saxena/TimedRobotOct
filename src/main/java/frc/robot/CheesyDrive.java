package frc.robot;

import static frc.robot.Constants.kDeadZone;

public class CheesyDrive {
    private Drive drive = Drive.getInstance();

    public void setDrive(double throttle, double turn, boolean spinTurn) {
        double leftPwr = 0.0, rightPwr = 0.0;
        if (spinTurn) {
            if (Math.abs(turn) >= kDeadZone) {
                leftPwr = turn;
                rightPwr = -turn;
            }
        }
        else {
            if (Math.abs(throttle) >= kDeadZone) {
                throttle = Math.pow(throttle, 3);
                if (Math.abs(turn) < kDeadZone) {
                    leftPwr = throttle;
                    rightPwr = throttle;
                }
                else {
                    if (turn > 0.0) {
                        rightPwr = 1.0;
                        leftPwr = -1.0 + 2.0*turn;
                    }
                    else {
                        leftPwr = 1.0;
                        rightPwr = 1.0 - 2.0*turn;
                    }
                }
                leftPwr *= throttle;
                rightPwr *= throttle;
            }
        }
        drive.setOpenLoop(leftPwr, rightPwr);
    }
}
