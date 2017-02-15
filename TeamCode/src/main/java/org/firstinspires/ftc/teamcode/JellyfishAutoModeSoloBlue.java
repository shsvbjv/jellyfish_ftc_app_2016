
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name="Mode Solo Blue ", group="Jellyfish")

public class JellyfishAutoModeSoloBlue extends JellyfishAutoBase {

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
        telemetry.addData("Path0", "Starting at %7d :%7d :%7d :%7d",
                robot.frontLeftMotor.getCurrentPosition(),
                robot.frontRightMotor.getCurrentPosition(),
                robot.backLeftMotor.getCurrentPosition(),
                robot.backRightMotor.getCurrentPosition());
        telemetry.update();

        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // Step through each leg of the path,
        // Note: Reverse movement is obtained by setting a negative distance (not speed)

        encoderDrive(DRIVE_SPEED, -41, 0, 4.0);

        encoderDrive(DRIVE_SPEED, 0, 50, 4.0);

        encoderDriverange(.1, 0, 20, 4, 4.0);

        encoderDriveWithODSLeft(.1, -24, 0, 4.0);

        encoderDrive(DRIVE_SPEED, 3, 0, 4.0);

        sleep(500);

        if(robot.colorSensor.blue() > robot.colorSensor.red()) {
            encoderDrive(DRIVE_SPEED, 0, 7, 4.0);
            encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            encoderDrive(DRIVE_SPEED, -40, 0, 6.0);
            encoderDriveWithODSLeft(.1, -10, 0, 4.0);
            encoderDriverange(.1, 0, 20, 4, 4.0);
            encoderDrive(DRIVE_SPEED, 3, 0, 4.0);

            if(robot.colorSensor.blue() > robot.colorSensor.red()) {
                encoderDrive(DRIVE_SPEED, 0, 7, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);

            }
            else {

                encoderDrive(DRIVE_SPEED, -10, 0, 4.0);
                encoderDriverange(.1, 0, 20, 4, 2.0);
                encoderDrive(DRIVE_SPEED, 0, 7, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);

            }
        }
        else{
            encoderDrive(DRIVE_SPEED, -9, 0, 4.0);
            encoderDriverange(.1, 0, 5, 5, 2.0);
            encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
            encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            encoderDrive(DRIVE_SPEED, -35, 0, 6.0);
            encoderDriveWithODSLeft(.1, -10, 0, 4.0);

            if(robot.colorSensor.blue() > robot.colorSensor.red()) {
                encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);

            }
            else {

                encoderDrive(DRIVE_SPEED, -9, 0, 4.0);
                encoderDriverange(.1, 0, 5, 5, 2.0);
                encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);

            }

        }




        sleep(1000);     // pause for servos to move

        telemetry.addData("Path", "Complete");
        telemetry.update();
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