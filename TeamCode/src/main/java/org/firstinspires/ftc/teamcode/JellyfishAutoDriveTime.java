

package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file illustrates the concept of driving a path based on time.
 * It uses the common Pushbot hardware class to define the drive on the robot.
 * The code is structured as a LinearOpMode
 *
 * The code assumes that you do NOT have encoders on the wheels,
 *   otherwise you would use: PushbotAutoDriveByEncoder;
 *
 *   The desired path in this example is:
 *   - Drive forward for 3 seconds
 *   - Spin right for 1.3 seconds
 *   - Drive Backwards for 1 Second
 *
 *  The code is written in a simple form with no optimizations.
 *  However, there are several ways that this type of sequence could be streamlined,
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Jellyfish: Auto Drive By Time", group="Jellyfish")

public class JellyfishAutoDriveTime extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareJellyfish         robot   = new HardwareJellyfish();   // Use a Pushbot's hardware

             private ElapsedTime     runtime = new ElapsedTime();




    static final double     FORWARD_SPEED = 0.6;
    static final double     TURN_SPEED    = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap, telemetry);


        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Ready to run");    //
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path, ensuring that the Auto mode has not been stopped along the way

        // Step 1:  Drive forward for 4 seconds
        //robot will drive forward for 1 second more than given time
        robot.frontLeftMotor.setPower(FORWARD_SPEED);
        robot.frontRightMotor.setPower(FORWARD_SPEED);
        robot.backLeftMotor.setPower(FORWARD_SPEED);
        robot.backRightMotor.setPower(FORWARD_SPEED);
        runtime.reset();

        //runtime < number of seconds to run robot
        while (opModeIsActive() && (runtime.seconds() < 3.0)) {
            telemetry.addData("Path", "Leg 1: %2.5f S Elapsed", runtime.seconds());
            //telemetry.addData(">", "Robot Heading = %d", robot.gyro.getIntegratedZValue());
            //telemetry.update();
             idle();
        }
        robot.frontLeftMotor.setPower(0);
        robot.frontRightMotor.setPower(0);
        robot.backLeftMotor.setPower(0);
        robot.backRightMotor.setPower(0);

//        // Step 2:  Spin right for 1.3 seconds
//
//        robot.frontLeftMotor.setPower(TURN_SPEED);
//        robot.frontRightMotor.setPower(TURN_SPEED);
//        robot.backLeftMotor.setPower(TURN_SPEED);
//        robot.backRightMotor.setPower(TURN_SPEED);
//        runtime.reset();
//        while (opModeIsActive() && (runtime.seconds() < 1.3)) {
//            telemetry.addData("Path", "Leg 2: %2.5f S Elapsed", runtime.seconds());
//            telemetry.update();
//            idle();
//        }
//
//        // Step 3:  Drive Backwards for 1 Second
//        robot.frontLeftMotor.setPower(-FORWARD_SPEED);
//        robot.frontRightMotor.setPower(FORWARD_SPEED);
//        robot.backLeftMotor.setPower(-FORWARD_SPEED);
//        robot.backRightMotor.setPower(FORWARD_SPEED);
//        runtime.reset();



        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(1000);
        idle();
    }
}
