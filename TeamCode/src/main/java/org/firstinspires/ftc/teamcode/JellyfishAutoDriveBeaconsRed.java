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

@Autonomous(name="Jellyfish: Auto Beacons Red ", group="Jellyfish")

public class JellyfishAutoDriveBeaconsRed extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareJellyfish         robot   = new HardwareJellyfish();   // Use a Pushbot's hardware
    private ElapsedTime     runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1220 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

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
        encoderDrive(DRIVE_SPEED,  0 ,32, 4.0);  // S2: Turn Right 12 Inches with 4 Sec timeout

        turn(TURN_SPEED, 45, 4.0); // Turn left 45 degrees

        encoderDrive(DRIVE_SPEED,   0, 30, 4.0);

        encoderTurn(TURN_SPEED, 65, 4.0); // Turn left 65 degress

        encoderDrive(DRIVE_SPEED, 0, 23.5, 4.0 );

        beaconPress();

        encoderDrive(DRIVE_SPEED, 50, 0, 4.0 ); // move right

        encoderDrive(DRIVE_SPEED, 0, 5, 4.0);

        beaconPress();

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
    public void encoderDrive(double speed,
                             double xInches, double yInches,
                             double timeoutS) throws InterruptedException {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        yInches = -yInches;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeftMotor.getCurrentPosition() + (int)(((yInches - xInches) * COUNTS_PER_INCH)/Math.sqrt(2));
            newFrontRightTarget = robot.frontRightMotor.getCurrentPosition() + (int)(((yInches + xInches) * COUNTS_PER_INCH)/Math.sqrt(2));
            newBackLeftTarget = robot.backLeftMotor.getCurrentPosition() + (int)(((yInches + xInches) * COUNTS_PER_INCH)/Math.sqrt(2));
            newBackRightTarget = robot.backRightMotor.getCurrentPosition() + (int)(((yInches - xInches) * COUNTS_PER_INCH)/Math.sqrt(2));

            robot.frontLeftMotor.setTargetPosition(newFrontLeftTarget);
            robot.frontRightMotor.setTargetPosition(newFrontRightTarget);
            robot.backLeftMotor.setTargetPosition(newBackLeftTarget);
            robot.backRightMotor.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeftMotor.setPower(Math.abs(speed));
            robot.frontRightMotor.setPower(Math.abs(speed));
            robot.backLeftMotor.setPower(Math.abs(speed));
            robot.backRightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeftMotor.isBusy() &&
                            robot.frontRightMotor.isBusy() &&
                            robot.backLeftMotor.isBusy() &&
                            robot.backRightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newFrontLeftTarget,
                        newFrontRightTarget,
                        newBackLeftTarget,
                        newBackRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d",
                        robot.frontLeftMotor.getCurrentPosition(),
                        robot.frontRightMotor.getCurrentPosition(),
                        robot.backLeftMotor.getCurrentPosition(),
                        robot.backRightMotor.getCurrentPosition());
                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.frontLeftMotor.setPower(0);
            robot.frontRightMotor.setPower(0);
            robot.backLeftMotor.setPower(0);
            robot.backRightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    public void encoderTurn(double speed,
                            double degrees,
                            double timeoutS) throws InterruptedException {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        double inches = 20.5 * Math.PI * degrees / 360;
        degrees = -degrees;
        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeftMotor.getCurrentPosition() + (int)(((inches) * COUNTS_PER_INCH)/Math.sqrt(2));
            newFrontRightTarget = robot.frontRightMotor.getCurrentPosition() + (int)(((-inches) * COUNTS_PER_INCH)/Math.sqrt(2));
            newBackLeftTarget = robot.backLeftMotor.getCurrentPosition() + (int)(((inches) * COUNTS_PER_INCH)/Math.sqrt(2));
            newBackRightTarget = robot.backRightMotor.getCurrentPosition() + (int)(((-inches) * COUNTS_PER_INCH)/Math.sqrt(2));

            robot.frontLeftMotor.setTargetPosition(newFrontLeftTarget);
            robot.frontRightMotor.setTargetPosition(newFrontRightTarget);
            robot.backLeftMotor.setTargetPosition(newBackLeftTarget);
            robot.backRightMotor.setTargetPosition(newBackRightTarget);

            // Turn On RUN_TO_POSITION
            robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeftMotor.setPower(Math.abs(speed));
            robot.frontRightMotor.setPower(Math.abs(speed));
            robot.backLeftMotor.setPower(Math.abs(speed));
            robot.backRightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.frontLeftMotor.isBusy() &&
                            robot.frontRightMotor.isBusy() &&
                            robot.backLeftMotor.isBusy() &&
                            robot.backRightMotor.isBusy())) {

                // Display it for the driver.
                telemetry.addData("Path1",  "Running to %7d :%7d :%7d :%7d", newFrontLeftTarget,
                        newFrontRightTarget,
                        newBackLeftTarget,
                        newBackRightTarget);
                telemetry.addData("Path2",  "Running at %7d :%7d :%7d :%7d",
                        robot.frontLeftMotor.getCurrentPosition(),
                        robot.frontRightMotor.getCurrentPosition(),
                        robot.backLeftMotor.getCurrentPosition(),
                        robot.backRightMotor.getCurrentPosition());
                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.frontLeftMotor.setPower(0);
            robot.frontRightMotor.setPower(0);
            robot.backLeftMotor.setPower(0);
            robot.backRightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void gyroTurnCounterClock(double speed,
                                  double degrees,
                                  double timeoutS) throws InterruptedException {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller


            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeftMotor.setPower(Math.abs(speed));
            robot.frontRightMotor.setPower(Math.abs(speed));
            robot.backLeftMotor.setPower(Math.abs(speed));
            robot.backRightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.gyro.getHeading() != degrees)) {



                // Display it for the driver.

                telemetry.addData("gyro",  "%7d", robot.gyro.getHeading());

                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.frontLeftMotor.setPower(0);
            robot.frontRightMotor.setPower(0);
            robot.backLeftMotor.setPower(0);
            robot.backRightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    public void gyroTurnClockwise(double speed,
                                  double degrees,
                                  double timeoutS) throws InterruptedException {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller


            // reset the timeout time and start motion.
            runtime.reset();
            robot.frontLeftMotor.setPower(Math.abs(speed));
            robot.frontRightMotor.setPower(Math.abs(speed));
            robot.backLeftMotor.setPower(Math.abs(speed));
            robot.backRightMotor.setPower(Math.abs(speed));

            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (robot.gyro.getHeading() != degrees)) {


                // Display it for the driver.

                telemetry.addData("gyro",  "%7d", robot.gyro.getHeading());

                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.frontLeftMotor.setPower(0);
            robot.frontRightMotor.setPower(0);
            robot.backLeftMotor.setPower(0);
            robot.backRightMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            robot.backRightMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }

    
    public void turn(double speed,
                            double degrees,
                            double timeoutS) throws InterruptedException {

        double final_heading = robot.gyro.getHeading() + degrees;

        encoderTurn(speed, degrees, timeoutS);

        while (robot.gyro.getHeading() < (final_heading - .5) || robot.gyro.getHeading() > (final_heading + .5)) {

            if (robot.gyro.getHeading() < (final_heading - .5)) {

                encoderTurn(speed, 1, 1.0);

            } else {
                encoderTurn(speed, -1, 1.0);
            }

        }
    }

    public void gyroTurn(double speed,
                         double degrees,
                         double timeoutS) throws InterruptedException {

        if(robot.gyro.getHeading() < degrees) {
            gyroTurnClockwise(speed, degrees, timeoutS);
        }
        else {
            gyroTurnCounterClock(speed, degrees, timeoutS);
        }
    }

    public void beaconPress() throws InterruptedException {
        encoderDrive(DRIVE_SPEED, 0, 4, 4.0);

        sleep(500);

        if(robot.colorSensor.red() > robot.colorSensor.blue()) {
            return;
        }
        else {
            sleep(5000);
            encoderDrive(DRIVE_SPEED, 0, -12, 4.0);
        encoderDrive(DRIVE_SPEED, 0, 15, 4.0);
        }

    }
}
