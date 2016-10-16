package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwareK9bot;

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


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void init() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

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


            // Run wheels in omni mode (note: The joystick goes negative when pushed forwards, so negate it)
        y = gamepad1.right_stick_y;
        x = gamepad1.right_stick_x;
        x2 = gamepad1.left_stick_x;

            robot.backLeftMotor.setPower(Range.clip(y + x - x2, -1, 1));
            robot.frontLeftMotor.setPower(Range.clip(y - x - x2, -1, 1));
            robot.backRightMotor.setPower(Range.clip(y - x + x2, -1, 1));
            robot.frontRightMotor.setPower(Range.clip(y + x + x2, -1, 1));

            if (gamepad1.a)
                robot.buttonPusherServo.setPosition(0.8);
            else robot.buttonPusherServo.setPosition(0.5);




            // Send telemetry message to signify robot running;
//            telemetry.addData("arm",   "%.2f", armPosition);
//            telemetry.addData("claw",  "%.2f", clawPosition);
            telemetry.addData("x",  "%.2f", x);
            telemetry.addData("y", "%.2f", y);
            telemetry.addData("backleft", "%.2f", Range.clip(y + x, -1, 1));
            telemetry.addData("frontleft", "%.2f", Range.clip(y - x, -1, 1));
            telemetry.update();
            }

        }


