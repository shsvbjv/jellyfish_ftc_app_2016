package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.Range;

/**
 * This OpMode uses the common HardwareJellyfish class to define the devices on the robot.
 * All device access is managed through the HardwareJellyfish class. (See this class for device names)
 * The code is structured as a LinearOpMode
 *
 * This particular OpMode executes a basic Omni Drive Teleop for the bot
 * It moves the bot in all directions
 *
 *
 */

@TeleOp(name="Jellyfish: TeleOp Omni Linear", group="Jellyfish")

public class JellyfishTeleopOmni_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareJellyfish   robot           = new HardwareJellyfish();
    //double          buttonPosition     = robot.BUTTON_HOME;
    final double    BUTTON_SPEED       = 0.01 ;
    boolean prevY = false;
    boolean prevA = false;
    boolean flywheelleft = false;
    //boolean flywheelright = false;
    boolean prevX = false;
    double speed = 1;
    //boolean flywheel = false;
    static final double INITIAL_FLYWHEEL_SPEED = 1;
    static final double FLYWHEEL_SPEED_INCREMENT = 0.1;
    double leftflywheelSpeed = INITIAL_FLYWHEEL_SPEED;
    //double rightflywheelSpeed = INITIAL_FLYWHEEL_SPEED;


    /*
     * Code to run ONCE when the driver hits INIT
     */
    @Override
    public void runOpMode() throws InterruptedException {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap, telemetry);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver");    //
        telemetry.update();


        waitForStart();

        while (opModeIsActive()) {

            double x;
            double y;
            double x2;

            //pushing y once will turn intake on, pushing it again will turn it off

            if ((prevX == false) &&
                    (gamepad2.x)) {

                flywheelleft = !flywheelleft;
                //flywheelright = !flywheelright;

            }

            prevX = gamepad2.x;


            if(flywheelleft) {
                robot.flywheelLeftMotorRampControl.rampPowerTo(leftflywheelSpeed);
            }
            else robot.flywheelLeftMotorRampControl.setPowerTo(0);


            // Run wheels in omni mode (note: The joystick goes negative when pushed forwards, so negate it)
            y = gamepad1.right_stick_y;
            x = gamepad1.right_stick_x;
            x2 = gamepad1.left_stick_x;

//
            robot.backLeftMotor.setPower(Range.clip(y + x - x2, -1, 1));
            robot.frontLeftMotor.setPower(Range.clip(y - x - x2, -1, 1));
            robot.backRightMotor.setPower(Range.clip(y - x + x2, -1, 1));
            robot.frontRightMotor.setPower(Range.clip(y + x + x2, -1, 1));


            if (gamepad2.a) {
                robot.intakeBeltMotor.setPower(1);
            } else if (gamepad2.y) {
                robot.intakeBeltMotor.setPower(-1);
            } else robot.intakeBeltMotor.setPower(0);


//            if(gamepad2.x) {
//                robot.flywheelLeftMotor.setPower(1);
//            }
//            else robot.flywheelLeftMotor.setPower(0);



            //flywheel motors go faster or slower NANDINI IS AWESOME

            //if (gamepad2.dpad_down && gamepad2.right_trigger > 0) {
                //rightflywheelSpeed += FLYWHEEL_SPEED_INCREMENT;
                //rightflywheelSpeed = Range.clip(rightflywheelSpeed, INITIAL_FLYWHEEL_SPEED, 1.0);
                //robot.flywheelRightMotorRampControl.setPowerTo(rightflywheelSpeed);

            //}

            //if (gamepad2.dpad_down && gamepad2.left_trigger > 0) {
               //rightflywheelSpeed -= FLYWHEEL_SPEED_INCREMENT;
               // rightflywheelSpeed = Range.clip(rightflywheelSpeed, INITIAL_FLYWHEEL_SPEED, 1.0);
                //robot.flywheelRightMotorRampControl.setPowerTo(rightflywheelSpeed);

            //}

            if (gamepad2.dpad_up && gamepad2.right_bumper) {
                leftflywheelSpeed += FLYWHEEL_SPEED_INCREMENT;
                leftflywheelSpeed = Range.clip(leftflywheelSpeed, 0, 1.0);
                robot.flywheelLeftMotorRampControl.rampPowerTo(leftflywheelSpeed);

            }

            if (gamepad2.dpad_up && gamepad2.left_bumper) {
                leftflywheelSpeed -= FLYWHEEL_SPEED_INCREMENT;
                leftflywheelSpeed = Range.clip(leftflywheelSpeed, 0, 1.0);
                robot.flywheelLeftMotorRampControl.rampPowerTo(leftflywheelSpeed);

           }


                //motors start slow and get faster
            robot.flywheelLeftMotorRampControl.checkMotor();
          //  robot.flywheelRightMotorRampControl.checkMotor();

                // Send telemetry message to signify robot running;

                telemetry.addData("Clear", robot.colorSensor.alpha());
                telemetry.addData("Red  ", robot.colorSensor.red());
                telemetry.addData("Green", robot.colorSensor.green());
                telemetry.addData("Blue ", robot.colorSensor.blue());

                telemetry.addData("Raw Left", "%.2f", robot.odsSensorL.getRawLightDetected());
                telemetry.addData("Raw Right", "%.2f", robot.odsSensorR.getRawLightDetected());

                telemetry.addData("Y", "%.2f", gamepad1.right_stick_y);
                telemetry.addData("X", "%.2f", gamepad1.right_stick_x);

                telemetry.addData("flywheel", "%.2f", robot.flywheelLeftMotor.getPower());

                //telemetry.addData("Hue", hsvValues[0]);

                telemetry.addData("gyro", "%7d", robot.gyro.getHeading());
                telemetry.update();


                // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
                robot.waitForTick(20);
                idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
            }
        }
    }

