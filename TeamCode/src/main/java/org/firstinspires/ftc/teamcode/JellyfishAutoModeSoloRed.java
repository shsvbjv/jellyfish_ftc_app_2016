
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcontroller.external.samples.HardwarePushbot;



@Autonomous(name="Mode Solo Red ", group="Jellyfish")

public class JellyfishAutoModeSoloRed extends JellyfishAutoBase {

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

        number = 0;

        encoderDrive(DRIVE_SPEED, 41, 0, 4.0);

        encoderDrive(DRIVE_SPEED, 0, 45, 4.0);

        encoderDriverange( 0.1, 0, 20, 4, 4.0);

        //encoderTurn(TURN_SPEED, 15, 4.0);

        encoderDriveWithODSLeft(.1, 22, 0, 4.0);

        encoderDrive(DRIVE_SPEED, -3, 0, 2.0);

        sleep(500);

        if(robot.colorSensor.red() > robot.colorSensor.blue()) {

            encoderDrive(DRIVE_SPEED, -3, 0, 2.0);
            encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
            encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            encoderDrive(DRIVE_SPEED, 40, 0, 6.0);
            encoderDriveWithODSLeft(.1, 10, 0, 4.0);
            encoderDriverange(.1, 0, 20, 4, 4.0);
            encoderDrive(DRIVE_SPEED, -3, 0, 2.0);

            if(robot.colorSensor.red() > robot.colorSensor.blue()) {

                encoderDrive(DRIVE_SPEED, -2, 0, 2.0);
                encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);

            }
            else{
                encoderDrive(DRIVE_SPEED, 6, 0, 4.0);
                encoderDriverange(.1, 0, 5, 4, 2.0);
                encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            }

        }
        else{
            encoderDrive(DRIVE_SPEED, 6, 0, 4.0);
            encoderDriverange(.1, 0, 5, 4, 2.0);
            encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
            encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            encoderDrive(DRIVE_SPEED, 38, 0, 6.0);
            encoderDriveWithODSLeft(.1, 10, 0, 4.0);
            encoderDriverange(.1, 0, 20, 4, 4.0);
            encoderDrive(DRIVE_SPEED, -5, 0, 2.0);

            if(robot.colorSensor.red() > robot.colorSensor.blue()) {

                encoderDrive(DRIVE_SPEED, -2, 0, 2.0);
                encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);

            }
            else{
                encoderDrive(DRIVE_SPEED, 10, 0, 4.0);
                encoderDriverange(.1, 0, 5, 4, 2.0);
                encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            }


        }

        robot.flywheelLeftMotor.setPower(.70);
        robot.servo.setPosition(1);
        encoderDrive(DRIVE_SPEED, 0, -6, 6.0);
        encoderTurn(TURN_SPEED, -55, 3.0);
        encoderDrive(DRIVE_SPEED, 0, -36, 4.0);
        intake(1, 5);
        robot.flywheelLeftMotor.setPower(0);



//        beaconPressR();
//
//        if(number < 3) {
//
//            encoderDrive(DRIVE_SPEED, 37, 0, 4.0);
////
//            encoderDriveWithODSLeft(.1, 11, 0, 4.0);
//
//            encoderDriverange(0.1, 0, 7, 4, 4.0);
////
//            beaconPressR();
//
//            encoderDrive(DRIVE_SPEED, 0, -12, 4.0);
//
//            encoderTurn(TURN_SPEED, -45, 4.0);
//
//            encoderDrive(DRIVE_SPEED, 0, -45, 6.0);
//        }
//        else {
//
//            encoderDrive(DRIVE_SPEED, 0, -45, 10.0);
//
//        }



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
