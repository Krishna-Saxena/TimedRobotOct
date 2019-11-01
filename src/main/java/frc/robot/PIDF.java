package frc.robot;

import edu.wpi.first.wpilibj.Timer;

public class PIDF {
    private double k_P, k_I, k_D, k_F, setpoint;
    private double m_integralErr, m_lastErr, m_lastTime = 0.0;

    public PIDF(double k_P, double k_I, double k_D, double k_F, double setpoint) {
        this.k_P = k_P;
        this.k_I = k_I;
        this.k_D = k_D;
        this.k_F = k_F;
        this.setpoint = setpoint;
    }

    public double update(double input) {
        double error = input-setpoint;
        m_integralErr += error;
        double dT = (Timer.getFPGATimestamp() - m_lastTime);
        double output = k_P*error + k_I*m_integralErr*dT + k_D*(error- m_lastErr)/dT + k_F*setpoint;
        m_lastErr = error;
        m_lastTime = Timer.getFPGATimestamp();
        return output;
    }

    public double getK_P() {
        return k_P;
    }

    public void setK_P(double k_P) {
        this.k_P = k_P;
    }

    public double getK_I() {
        return k_I;
    }

    public void setK_I(double k_I) {
        this.k_I = k_I;
    }

    public double getK_D() {
        return k_D;
    }

    public void setK_D(double k_D) {
        this.k_D = k_D;
    }

    public double getK_F() {
        return k_F;
    }

    public void setK_F(double k_F) {
        this.k_F = k_F;
    }

    public double getSetpoint() {
        return setpoint;
    }

    public void setSetpoint(double setpoint) {
        this.setpoint = setpoint;
    }
}
