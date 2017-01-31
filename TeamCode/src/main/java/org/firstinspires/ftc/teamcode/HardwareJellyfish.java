package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsAnalogOpticalDistanceSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cColorSensor;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cRangeSensor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
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
public class HardwareJellyfish

{
    /* Public OpMode members. */
    public DcMotor  frontLeftMotor   = null;
    public DcMotor  frontRightMotor  = null;
    public DcMotor  backLeftMotor    = null;
    public DcMotor  backRightMotor   = null;
    public DcMotor  intakeBeltMotor = null;
    public DcMotor  conveyorbelt   = null;
    // public DcMotor  flywheelRightMotor= null;
     public DcMotor  flywheelLeftMotor = null;


    public ColorSensor colorSensor;
    OpticalDistanceSensor odsSensor;
    //OpticalDistanceSensor odsSensorR;

    public Servo servo = null;

    public RampedMotorControl flywheelLeftMotorRampControl = null;
 //   public RampedMotorControl flywheelRightMotorRampControl = null;


    ModernRoboticsI2cGyro gyro    = null;
    ModernRoboticsI2cRangeSensor rangeSensor;



    /* Local OpMode members. */
    HardwareMap hwMap  = null;
    private ElapsedTime period  = new ElapsedTime();

    /* Constructor */
    public HardwareJellyfish() {
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
        conveyorbelt = hwMap.dcMotor.get("conveyor");
        intakeBeltMotor = hwMap.dcMotor.get("intake");
        flywheelLeftMotor = hwMap.dcMotor.get("flywheelleft");

     //   flywheelRightMotor = hwMap.dcMotor.get("flywheelright");


        gyro = (ModernRoboticsI2cGyro)hwMap.gyroSensor.get("gyro");
        rangeSensor = hwMap.get(ModernRoboticsI2cRangeSensor.class, "range");
        servo = hwMap.servo.get("servo");


        gyro.setHeadingMode(ModernRoboticsI2cGyro.HeadingMode.HEADING_CARTESIAN);

        // Set all motors to zero power
        frontLeftMotor.setPower(0);
        frontRightMotor.setPower(0);
        backLeftMotor.setPower(0);
        backRightMotor.setPower(0);
        intakeBeltMotor.setPower(0);
        flywheelLeftMotor.setPower(0);
     //   flywheelRightMotor.setPower(0);

        frontLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        //Set direction of all motors
        

        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeBeltMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        conveyorbelt.setDirection(DcMotorSimple.Direction.REVERSE);

        //MAKE SURE THESE ARE OPPOSITE DIRECTIONS OTHERWISE YOU WILL BREAK MOTORS
        flywheelLeftMotor.setDirection(DcMotorSimple.Direction.FORWARD);
       // flywheelRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        // Set all motors to run without encoders.
        // May want to use RUN_USING_ENCODERS if encoders are installed.
        frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        intakeBeltMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
      //  flywheelLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        conveyorbelt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        ///  flywheelRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        servo.setPosition(.5);

        hwMap.logDevices();


        boolean bLedOn = true;

        // get a reference to our ColorSensor object.
        colorSensor = hwMap.colorSensor.get("color");

        // Set the LED in the beginning
        colorSensor.enableLed(bLedOn);

        odsSensor = hwMap.opticalDistanceSensor.get("odsleft");
        //
        // odsSensorR = hwMap.opticalDistanceSensor.get("odsright");


        flywheelLeftMotorRampControl = new RampedMotorControl(flywheelLeftMotor, 5.0);
     //   flywheelRightMotorRampControl = new RampedMotorControl(flywheelRightMotor, 5.0);

        telemetry.addData(">", "Calibrating Gyro");    //
        telemetry.update();

        gyro.calibrate();

        //make sure the gyro is calibrated before continuing
        while (gyro.isCalibrating())  {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
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
