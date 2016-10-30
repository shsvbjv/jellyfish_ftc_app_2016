package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;

/**
 * This is NOT an opmode.
 * NANDINI IS THE MOST AMAZING PERSON EVER!!!!!
 * This class can be used to define all the specific hardware for a single robot.
 * In this case that robot is a K9 robot.
 *
 * This hardware class assumes the following device names have been configured on the robot:
 * Note:  All names are lower case and some have single spaces between words.
 *
 * Motor channel:  Left  drive motor:        "left motor"
 * Motor channel:  Right drive motor:        "right motor"
 */
public class HardwareJellyfishTeleop
{
    /* Public OpMode members. */
    public DcMotor  frontLeftMotor   = null;
    public DcMotor  frontRightMotor  = null;
    public DcMotor  backLeftMotor    = null;
    public DcMotor  backRightMotor   = null;
    public DcMotor  intakeBeltMotor = null;
    public DcMotor  flywheelTopMotor = null;
    public DcMotor  flywheelBottomMotor= null;
    public DcMotor conveyerBeltMotor = null;

    public RampedMotorControl flywheelTopMotorRampControl = null;
    public RampedMotorControl flywheelBottomMotorRampControl = null;

    public Servo    leftButtonPusherServo = null;
    public Servo    rightButtonPusherServo = null;

    //public ModernRoboticsI2cGyro gyro = null;


    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareJellyfishTeleop() {
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap, Telemetry telemetry) {
        // save reference to HW Map
        hwMap = ahwMap;

        // Define and Initialize Motors
        frontLeftMotor   = hwMap.dcMotor.get("motor_lf");
        frontRightMotor  = hwMap.dcMotor.get("motor_rf");
        backLeftMotor   = hwMap.dcMotor.get("motor_lb");
        backRightMotor  = hwMap.dcMotor.get("motor_rb");
        intakeBeltMotor = hwMap.dcMotor.get("intake");
        flywheelTopMotor = hwMap.dcMotor.get("flywheeltop");
        flywheelBottomMotor = hwMap.dcMotor.get("flywheelbottom");
        conveyerBeltMotor = hwMap.dcMotor.get("conveyerbelt");


        //gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("gyro");
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        // Set all motors to zero power
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        intakeBeltMotor.setPower(0);
        flywheelTopMotor.setPower(0);
        flywheelBottomMotor.setPower(0);
        conveyerBeltMotor.setPower(0);

        //Set direction of all motors
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        intakeBeltMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        flywheelTopMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        flywheelBottomMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        conveyerBeltMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeBeltMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheelTopMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flywheelBottomMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        conveyerBeltMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        leftButtonPusherServo = hwMap.servo.get("left button push");
        leftButtonPusherServo.setPosition(0);
        leftButtonPusherServo.setDirection(Servo.Direction.FORWARD);

        rightButtonPusherServo = hwMap.servo.get("right button push");
        rightButtonPusherServo.setPosition(1);
        rightButtonPusherServo.setDirection(Servo.Direction.FORWARD);

        flywheelTopMotorRampControl = new RampedMotorControl(flywheelTopMotor, 5.0);
        flywheelBottomMotorRampControl = new RampedMotorControl(flywheelBottomMotor, 5.0);

//        telemetry.addData(">", "Calibrating Gyro");    //
//        telemetry.update();

       // gyro.calibrate();

        // make sure the gyro is calibrated before continuing
//        while (gyro.isCalibrating())  {
//            try {
//                Thread.sleep(50);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }
        telemetry.addData(">", "Robot Ready.");    //
        telemetry.update();


    }

    /***
     *
     * waitForTick implements a periodic delay. However, this acts like a metronome with a regular
     * periodic tick.  This is used to compensate for varying processing times for each cycle.
     * The function looks at the elapsed cycle time, and sleeps for the remaining time interval.
     *
     * @param periodMs  Length of wait cycle in mSec.
     * @throws InterruptedException
     */
    public void waitForTick(long periodMs)  throws InterruptedException {

        long  remaining = periodMs - (long)period.milliseconds();

        // sleep for the remaining portion of the regular cycle period.
        if (remaining > 0)
            Thread.sleep(remaining);

        // Reset the cycle clock for the next pass.
        period.reset();
    }
}