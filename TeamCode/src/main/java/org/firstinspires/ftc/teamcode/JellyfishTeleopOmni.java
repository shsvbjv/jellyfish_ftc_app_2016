package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * This OpMode uses the common HardwareJellyfish class to define the devices on the robot.
 * All device access is managed through the HardwareJellyfish class. (See this class for device names)
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a basic Omni Drive Teleop for the Jellyfish bot
 * It moves the bot in all directions
 *
 */

@TeleOp(name="Jellyfish: TeleOp Omni", group="Jellyfish")

public class JellyfishTeleopOmni extends OpMode {

    /* Declare OpMode members. */
    HardwareJellyfishTeleop   robot           = new HardwareJellyfishTeleop();              // Use a K9's hardware
    //double          buttonPosition     = robot.BUTTON_HOME;
    final double    BUTTON_SPEED       = 0.01 ;
    boolean prevY = false;
    boolean intakeout = false;

    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap, telemetry);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        updateTelemetry(telemetry);
    }

    /*
     * Code to run REPEATEDLY after the driver hits INIT, but before they hit PLAY
     */
    @Override
    public void init_loop() {
    }

    /*
     * Code to run ONCE when the driver hits PLAY
     */
    @Override
    public void start() {
    }
    @Override
    public void loop(){
        double x;
        double y;
        double x2;
        
        //pushing y once will turn motor on, pushing it again will turn it off

        if ((prevY == false) &&
                (gamepad2.y)) {
            intakeout = !intakeout;
            //set motors to on or off
            //if intakeout is true motor on
            //if intakeout is false motor off

        }

        prevY = gamepad2.y;

        // Run wheels in omni mode (note: The joystick goes negative when pushed forwards, so negate it)
        y = gamepad1.right_stick_y;
        x = gamepad1.right_stick_x;
        x2 = gamepad1.left_stick_x;

        if(gamepad1.right_bumper) {
            robot.backLeftMotor.setPower(Range.clip((y + x - x2)/2, -1, 1));
            robot.frontLeftMotor.setPower(Range.clip((y - x - x2)/2, -1, 1));
            robot.backRightMotor.setPower(Range.clip((y - x + x2)/2, -1, 1));
            robot.frontRightMotor.setPower(Range.clip((y + x + x2)/2, -1, 1));
        }
        else {
            robot.backLeftMotor.setPower(Range.clip(y + x - x2, -1, 1));
            robot.frontLeftMotor.setPower(Range.clip(y - x - x2, -1, 1));
            robot.backRightMotor.setPower(Range.clip(y - x + x2, -1, 1));
            robot.frontRightMotor.setPower(Range.clip(y + x + x2, -1, 1));
        }

        if (gamepad2.x)
            robot.leftButtonPusherServo.setPosition(0.8);
        else robot.leftButtonPusherServo.setPosition(0.5);

        if (gamepad2.b)
            robot.rightButtonPusherServo.setPosition(0.8);
        else robot.rightButtonPusherServo.setPosition(0.5);

        if(gamepad2.a) {
            //intake in
            robot.intakeBeltMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.intakeBeltMotor.setPower(1);
        }

        if(gamepad2.y) {
            //intake out
            robot.intakeBeltMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            robot.intakeBeltMotor.setPower(-1);
        }

        if(gamepad2.right_bumper)  {
            robot.flywheelTopMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            robot.flywheelBottomMotor.setDirection(DcMotorSimple.Direction.FORWARD);


        }


        // Send telemetry message to signify robot running;
//            telemetry.addData("arm",   "%.2f", armPosition);
//            telemetry.addData("claw",  "%.2f", clawPosition);
        telemetry.addData("x",  "%.2f", x);
        telemetry.addData("y", "%.2f", y);
        telemetry.addData("backleft", "%.2f", Range.clip(y + x, -1, 1));
        telemetry.addData("frontleft", "%.2f", Range.clip(y - x, -1, 1));
        telemetry.addData("gyro", "%7d", robot.gyro.getHeading());
        telemetry.update();
    }

}
