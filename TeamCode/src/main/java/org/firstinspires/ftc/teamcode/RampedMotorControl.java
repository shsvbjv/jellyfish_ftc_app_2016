package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;


//
//
// To use this class, instantiate a RampedMotorControl somewhere in your OpMode, passing the
// DcMotor object that you want to ramp.
//
//      rampedLeftMotor = new RampedMotorControl(leftMotor, 5.0);
//
// The percentage represents the amount of motor power to be added per 10ms period.
//
// In the main loop, call:
//      rampedLeftMotor.checkMotor()
//
// When you want to change the motor speed, instead fo calling the motor controller's
// methods directly, call the "rampPowerTo" or "setPowerTo" methods depending on whether
// you want ramped power or not.
//



public class RampedMotorControl {

    DcMotor theMotor;
    double changeRate = 1.0;        // Maximum change rate, in "percent per 10ms tick (1.0 = 100%)"
    double targetPower = 0.0;       // Power we're aiming for.
    double currentPower = 0.0;      // Power we're at now
    long lastTime = 0;

    RampedMotorControl(DcMotor motor)
    {
        theMotor = motor;
    }

    RampedMotorControl(DcMotor motor, double defaultChangeRatePercent)
    {
        theMotor = motor;
        setChangeRate(defaultChangeRatePercent);
    }

    //
    // setChangeRate(percent) - set the percentage change maximum per time slice.
    //
    void setChangeRate(double percent)
    {
        changeRate = percent / 100.0;
    }

    //
    // rampPowerTo(power) - set power to specified value, ramping
    //
    void rampPowerTo(double power)
    {
        targetPower = power;                   // our desired target power
        currentPower = theMotor.getPower();     // beginning power level.
    }

    //
    // setPowerTo(power) - set power to specified value, without ramping
    //
    void setPowerTo(double power)
    {
        targetPower = power;
        currentPower = power;
        theMotor.setPower(power);
    }

    //
    // checkMotor() - this should be called during the main loop. If a motor
    // isn't at the target speed, we'll move it towards that speed.
    //
    void checkMotor()
    {
        long t = System.currentTimeMillis();

        if ((t - lastTime) >= 10) {
            // 10ms have elapsed.  Update the motor.

            // Compute the difference in power that we need to apply.
            double powerToGo = targetPower - currentPower;

            // Don't do anything if we're already at the target motor speed.

            if (powerToGo != 0) {

                // Compute the amount we want to change the power by (this is a
                // relative amount, to be added to the current power setting).
                // Bound the power by the max change rate.   Note that the
                // calculation is different if we're reducing the power.

                double powerChange;
                if (powerToGo < 0) {
                    powerChange = Math.max(powerToGo, -changeRate);
                } else {
                    powerChange = Math.min(powerToGo, changeRate);
                }

                // Add change into the current motor power and set new power.
                currentPower = currentPower + powerChange;
                theMotor.setPower(currentPower);
            }
        }

        lastTime = t;
    }

}

