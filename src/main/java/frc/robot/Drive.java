package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;


public class Drive extends Subsystem {
    private TalonSRX leftM;
    private TalonSRX leftS;
    private TalonSRX rightM;
    private TalonSRX rightS;

    private static final Drive mDrive = new Drive();
    private static PeriodicIO mPerIO = new PeriodicIO();

    public static Drive getInstance() {
        return mDrive;
    }

    private Drive() {
        leftM = new TalonSRX(0);
        leftS = new TalonSRX(1);
        rightM = new TalonSRX(2);
        rightS = new TalonSRX(3);

        rightM.setInverted(true);
        rightS.setInverted(true);

        leftS.set(ControlMode.Follower, 0);
        rightS.set(ControlMode.Follower, 2);
    }

    @Override
    public void stop() {
        leftM.set(ControlMode.PercentOutput, 0.0);
        rightM.set(ControlMode.PercentOutput, 0.0);
    }

    public void setOpenLoop(double throttle, double turn) {
        double left_pwr = throttle + turn;
        double right_pwr = throttle - turn;

        if ((left_pwr > 1.0) || (right_pwr > 1.0)) {
            left_pwr /= Math.max(left_pwr, right_pwr);
            right_pwr /= Math.max(left_pwr, right_pwr);
        }

        mPerIO.left_demand = left_pwr;
        mPerIO.right_demand = right_pwr;
    }

    @Override
    public boolean checkSystem() {
        return true;
    }

    @Override
    public void outputTelemetry() {

    }

    @Override
    public void writePeriodicOutputs() {
        leftM.set(ControlMode.PercentOutput, mPerIO.left_demand);
        rightM.set(ControlMode.PercentOutput, mPerIO.right_demand);
    }

    private static class PeriodicIO {
        //No inputs
        //Outputs
        private double left_demand, right_demand;
    }
}
