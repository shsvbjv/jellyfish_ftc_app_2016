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
 */

@TeleOp(name="Jellyfish: TeleOp Omni Linear", group="Jellyfish")

public class JellyfishTeleopOmni_Linear extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareJellyfish   robot           = new HardwareJellyfish();
    //double          buttonPosition     = robot.BUTTON_HOME;
    final double    BUTTON_SPEED       = 0.01 ;
    boolean prevY = false;
    boolean prevA = false;
    boolean intakeout = false;
    boolean intakein = false;
    boolean prevRB = false;
    //boolean flywheel = false;
//    static final double INITIAL_FLYWHEEL_SPEED = .5;
//    static final double FLYWHEEL_SPEED_INCREMENT = 0.05;
//    double topflywheelSpeed = INITIAL_FLYWHEEL_SPEED;
//    double bottomflywheelSpeed = INITIAL_FLYWHEEL_SPEED;


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

//            if ((prevY == false) &&
//                    (gamepad2.y)) {
//                intakeout = !intakeout;
//
//            }
//
//            prevY = gamepad2.y;
//
//            if ((prevA == false) &&
//                    (gamepad2.a)) {
//                intakein = !intakein;
//
//            }

//            prevA = gamepad2.a;
//
//            if (intakein) {
//                robot.intakeBeltMotor.setPower(1);
//            } else if (intakeout) {
//                robot.intakeBeltMotor.setPower(-1);
//                //robot.conveyerBeltMotor.setPower(-1);
//
//            } else robot.intakeBeltMotor.setPower(0);
            //robot.conveyerBeltMotor.setPower(0);

            //pushing rb once will turn flywheels on. pushing again will turn them off

//            if ((prevRB == false) &&
//                    (gamepad2.right_bumper)) {
//                //flywheel = !flywheel;
//
//            }
//            prevRB = gamepad2.right_bumper;

            //if (flywheel) {
            //robot.flywheelTopMotorRampControl.setPowerTo(topflywheelSpeed);
//                robot.flywheelBottomMotorRampControl.setPowerTo(bottomflywheelSpeed);
//
//            } else {
//                robot.flywheelTopMotorRampControl.setPowerTo(0);
//                robot.flywheelBottomMotorRampControl.setPowerTo(0);
//            }


            // Run wheels in omni mode (note: The joystick goes negative when pushed forwards, so negate it)
            y = gamepad1.right_stick_y;
            x = gamepad1.right_stick_x;
            x2 = gamepad1.left_stick_x;

//
                robot.backLeftMotor.setPower(Range.clip(y + x - x2, -1, 1));
                robot.frontLeftMotor.setPower(Range.clip(y - x - x2, -1, 1));
                robot.backRightMotor.setPower(Range.clip(y - x + x2, -1, 1));
                robot.frontRightMotor.setPower(Range.clip(y + x + x2, -1, 1));
//

            //left and right beacon button pushers
            //start at .1 and +- .15

//            if (gamepad2.x) {
//                robot.leftButtonPusherServo.setPosition(.25);
//            }
//            else if(gamepad2.b) {
//                robot.leftButtonPusherServo.setPosition(.65);
//            }
//            else {
//                robot.leftButtonPusherServo.setPosition(.44);
//            }



            if(gamepad2.a) {
                robot.intakeBeltMotor.setPower(1);
            }
            else if(gamepad2.y) {
                robot.intakeBeltMotor.setPower(-1);
            }
            else robot.intakeBeltMotor.setPower(0);




            //flywheel motors go faster or slower

//            if (gamepad2.dpad_down && gamepad2.right_trigger > 0) {
//                bottomflywheelSpeed += FLYWHEEL_SPEED_INCREMENT;
//                bottomflywheelSpeed = Range.clip(bottomflywheelSpeed, INITIAL_FLYWHEEL_SPEED, 1.0);
//                robot.flywheelBottomMotorRampControl.setPowerTo(bottomflywheelSpeed);
//
//            }
//
//            if (gamepad2.dpad_down && gamepad2.left_trigger > 0) {
//                bottomflywheelSpeed -= FLYWHEEL_SPEED_INCREMENT;
//                bottomflywheelSpeed = Range.clip(bottomflywheelSpeed, INITIAL_FLYWHEEL_SPEED, 1.0);
//                robot.flywheelBottomMotorRampControl.setPowerTo(bottomflywheelSpeed);
//
//            }

//            if (gamepad2.dpad_up && gamepad2.right_trigger > 0) {
//                topflywheelSpeed += FLYWHEEL_SPEED_INCREMENT;
//                topflywheelSpeed = Range.clip(topflywheelSpeed, INITIAL_FLYWHEEL_SPEED, 1.0);
//                robot.flywheelTopMotorRampControl.setPowerTo(topflywheelSpeed);
//
//            }
//
//            if (gamepad2.dpad_up && gamepad2.left_trigger > 0) {
//                topflywheelSpeed -= FLYWHEEL_SPEED_INCREMENT;
//                topflywheelSpeed = Range.clip(topflywheelSpeed, INITIAL_FLYWHEEL_SPEED, 1.0);
//                robot.flywheelTopMotorRampControl.setPowerTo(topflywheelSpeed);
//
//            }

            //motors start slow and get faster
//            robot.flywheelTopMotorRampControl.checkMotor();
//            robot.flywheelBottomMotorRampControl.checkMotor();

            // Send telemetry message to signify robot running;

            telemetry.addData("Clear", robot.colorSensor.alpha());
            telemetry.addData("Red  ", robot.colorSensor.red());
            telemetry.addData("Green", robot.colorSensor.green());
            telemetry.addData("Blue ", robot.colorSensor.blue());

            telemetry.addData("Raw Left",  "%.2f",  robot.odsSensorL.getRawLightDetected());
            telemetry.addData("Raw Right", "%.2f", robot.odsSensorR.getRawLightDetected());

            telemetry.addData("Y", "%.2f", gamepad1.right_stick_y);
            telemetry.addData("X", "%.2f", gamepad1.right_stick_x);



            //telemetry.addData("Hue", hsvValues[0]);

            //telemetry.addData("conveyer", "%.2f", robot.conveyerBeltMotor.getPower());
            telemetry.addData("gyro", "%7d", robot.gyro.getHeading());
            telemetry.update();


            // Pause for metronome tick.  40 mS each cycle = update 25 times a second.
            robot.waitForTick(20);
            idle(); // Always call idle() at the bottom of your while(opModeIsActive()) loop
        }
    }

}
