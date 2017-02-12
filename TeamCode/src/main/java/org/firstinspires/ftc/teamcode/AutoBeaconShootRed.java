package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;

/**
 * Hit beacons
 * Push cap ball
 * Park on center vortex
 * NEED TO TEST
 */

@Autonomous(name="Beacon+shoot Red ", group="Jellyfish")

public class AutoBeaconShootRed extends JellyfishAutoBase {

    /* Declare OpMode members. */


    @Override
    public void runOpMode() throws InterruptedException {

        /*
         * Initialize the drive system variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap, telemetry);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        robot.frontLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        idle();

        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Send telemetry message to indicate successful Encoder reset
        telemetry.addData("Path0",  "Starting at %7d :%7d :%7d :%7d",
                robot.frontLeftMotor.getCurrentPosition(),
                robot.frontRightMotor.getCurrentPosition(),
                robot.backLeftMotor.getCurrentPosition(),
                robot.backRightMotor.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)
        //encoderDrive(DRIVE_SPEED,  -50,  -50, 5.0);  // S1: Forward 12 Inches with 5 Sec timeout
        //encoderDrive(DRIVE_SPEED,  0 ,32, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout

        encoderDrive(DRIVE_SPEED, 0, -22, 4.0);

        shoot(.75, 8);

        encoderDrive(DRIVE_SPEED, 0, -12, 4.0);

        encoderTurn(TURN_SPEED, -90, 4.0);

        encoderDrive(DRIVE_SPEED, 0, 47, 4.0);

        encoderDriverange( 0.1, 0, 5, 4, 4.0);

        encoderDriveWithODSLeft(.1, 24, 0, 4.0);

        beaconPressR();

        encoderDrive(DRIVE_SPEED, 0, -48, 6.0);


        telemetry.addData("Path", "Complete");
        //elemetry.addData("Gyro",  ":%3d", robot.gyro.getHeading());

        telemetry.update();

        sleep(10000);
    }

    /*
     *  Method to perform a relative move, based on encoder counts.
     *  Encoders are not reset as the move is based on the current position.
     *  Move will stop if any of three conditions occur:
     *  1) Move gets to the desired position
     *  2) Move runs out of time
     *  3) Driver stops the opmode running.
     */



}