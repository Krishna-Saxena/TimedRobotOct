package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;


public class Drive extends Subsystem {
    private TalonSRX leftM;
    private TalonSRX leftS;
    private TalonSRX rightM;
    private TalonSRX rightS;

    private CANSparkMax lSparkM;
    private CANSparkMax lSparkS;
    private CANSparkMax rSparkM;
    private CANSparkMax rSparkS;

    private static final Drive mDrive = new Drive();
    private static PeriodicIO mPerIO = new PeriodicIO();

    public static Drive getInstance() {
        return mDrive;
    }

    private Drive() {
        //initialization of motors
        leftM = new TalonSRX(0);
        leftS = new TalonSRX(1);
        rightM = new TalonSRX(2);
        rightS = new TalonSRX(3);

        lSparkM = new CANSparkMax(0, CANSparkMaxLowLevel.MotorType.kBrushless);
        lSparkS = new CANSparkMax(1, CANSparkMaxLowLevel.MotorType.kBrushless);
        rSparkM = new CANSparkMax(2, CANSparkMaxLowLevel.MotorType.kBrushless);
        rSparkS = new CANSparkMax(3, CANSparkMaxLowLevel.MotorType.kBrushless);

        //invert right motors
        rightM.setInverted(true);
        rightS.setInverted(true);

        rSparkM.setInverted(true);
        rSparkS.setInverted(true);

        //set slave motors
        leftS.set(ControlMode.Follower, 0);
        rightS.set(ControlMode.Follower, 2);

        lSparkS.follow(lSparkM);
        rSparkS.follow(rSparkM);
    }

    @Override
    public void stop() {
        leftM.set(ControlMode.PercentOutput, 0.0);
        rightM.set(ControlMode.PercentOutput, 0.0);
    }

    public void setOpenLoop(double leftPwr, double rightPwr) {
        /*ouble leftPwr = throttle + turn;
        double rightPwr = throttle - turn;

        if ((leftPwr > 1.0) || (rightPwr > 1.0)) {
            leftPwr /= Math.max(leftPwr, rightPwr);
            rightPwr /= Math.max(leftPwr, rightPwr);
        }*/

        mPerIO.left_demand = leftPwr;
        mPerIO.right_demand = rightPwr;
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

        lSparkM.set(mPerIO.left_demand);
        rSparkM.set(mPerIO.right_demand);
    }

    private static class PeriodicIO {
        //No inputs
        //Outputs
        private double left_demand, right_demand;
    }
}
