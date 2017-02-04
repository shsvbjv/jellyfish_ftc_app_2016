package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

/**
 * Created by nandinithakur on 11/27/16.
 */

public abstract class JellyfishAutoBase extends LinearOpMode {

    HardwareJellyfish         robot   = new HardwareJellyfish();   // Use a Pushbot's hardware
    protected ElapsedTime runtime = new ElapsedTime();

    static final double     COUNTS_PER_MOTOR_REV    = 1220 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1 ;     // This is < 1.0 if geared UP
    static final double     WHEEL_DIAMETER_INCHES   = 3.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);
    static final double     DRIVE_SPEED             = 06;
    static final double     TURN_SPEED              = 0.2;
    public int number = 0;

    public void encoderDriverange (double speed,
                                         double xInches, double yInches, double inches,
                                         double timeoutS) throws InterruptedException {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        yInches = -yInches;

        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            // Determine new target position, and pass to motor controller
            newFrontLeftTarget = robot.frontLeftMotor.getCurrentPosition() + (int) (((yInches - xInches) * COUNTS_PER_INCH) / Math.sqrt(2));
            newFrontRightTarget = robot.frontRightMotor.getCurrentPosition() + (int) (((yInches + xInches) * COUNTS_PER_INCH) / Math.sqrt(2));
            newBackLeftTarget = robot.backLeftMotor.getCurrentPosition() + (int) (((yInches + xInches) * COUNTS_PER_INCH) / Math.sqrt(2));
            newBackRightTarget = robot.backRightMotor.getCurrentPosition() + (int) (((yInches - xInches) * COUNTS_PER_INCH) / Math.sqrt(2));

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
                            robot.backRightMotor.isBusy()) &&
                    robot.rangeSensor.getDistance(DistanceUnit.INCH) > inches) {


                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d :%7d :%7d", newFrontLeftTarget,
                        newFrontRightTarget,
                        newBackLeftTarget,
                        newBackRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d :%7d :%7d",
                        robot.frontLeftMotor.getCurrentPosition(),
                        robot.frontRightMotor.getCurrentPosition(),
                        robot.backLeftMotor.getCurrentPosition(),
                        robot.backRightMotor.getCurrentPosition());

                //telemetry.addData("right", "%.2f", robot.odsSensorR.getRawLightDetected());
                telemetry.addData("left", "%.2f", robot.odsSensor.getRawLightDetected());


                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }
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

    public void encoderDriveWithODSLeft (double speed,
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
                            robot.backRightMotor.isBusy())&&
                    robot.odsSensor.getRawLightDetected()< .9)
            {


                // Display it for the driver.
                telemetry.addData("Path1", "Running to %7d :%7d :%7d :%7d", newFrontLeftTarget,
                        newFrontRightTarget,
                        newBackLeftTarget,
                        newBackRightTarget);
                telemetry.addData("Path2", "Running at %7d :%7d :%7d :%7d",
                        robot.frontLeftMotor.getCurrentPosition(),
                        robot.frontRightMotor.getCurrentPosition(),
                        robot.backLeftMotor.getCurrentPosition(),
                        robot.backRightMotor.getCurrentPosition());

                //telemetry.addData("right", "%.2f", robot.odsSensorR.getRawLightDetected());
                telemetry.addData("left", "%.2f", robot.odsSensor.getRawLightDetected());


                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }
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

//    public void encoderDriveWithODSRight (double speed,
//                                          double xInches, double yInches,
//                                          double timeoutS) throws InterruptedException {
//        int newFrontLeftTarget;
//        int newFrontRightTarget;
//        int newBackLeftTarget;
//        int newBackRightTarget;
//
//        yInches = -yInches;
//
//        // Ensure that the opmode is still active
//        if (opModeIsActive()) {
//
//            // Determine new target position, and pass to motor controller
//            newFrontLeftTarget = robot.frontLeftMotor.getCurrentPosition() + (int)(((yInches - xInches) * COUNTS_PER_INCH)/Math.sqrt(2));
//            newFrontRightTarget = robot.frontRightMotor.getCurrentPosition() + (int)(((yInches + xInches) * COUNTS_PER_INCH)/Math.sqrt(2));
//            newBackLeftTarget = robot.backLeftMotor.getCurrentPosition() + (int)(((yInches + xInches) * COUNTS_PER_INCH)/Math.sqrt(2));
//            newBackRightTarget = robot.backRightMotor.getCurrentPosition() + (int)(((yInches - xInches) * COUNTS_PER_INCH)/Math.sqrt(2));
//
//            robot.frontLeftMotor.setTargetPosition(newFrontLeftTarget);
//            robot.frontRightMotor.setTargetPosition(newFrontRightTarget);
//            robot.backLeftMotor.setTargetPosition(newBackLeftTarget);
//            robot.backRightMotor.setTargetPosition(newBackRightTarget);
//
//            // Turn On RUN_TO_POSITION
//            robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.backRightMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//            // reset the timeout time and start motion.
//            runtime.reset();
//            robot.frontLeftMotor.setPower(Math.abs(speed));
//            robot.frontRightMotor.setPower(Math.abs(speed));
//            robot.backLeftMotor.setPower(Math.abs(speed));
//            robot.backRightMotor.setPower(Math.abs(speed));
//
//            // keep looping while we are still active, and there is time left, and both motors are running.
//            while (opModeIsActive() &&
//                    (runtime.seconds() < timeoutS) &&
//                    (robot.frontLeftMotor.isBusy() &&
//                            robot.frontRightMotor.isBusy() &&
//                            robot.backLeftMotor.isBusy() &&
//                            robot.backRightMotor.isBusy())&&
//                    robot.odsSensorR.getRawLightDetected()<0.11) {
//
//
//                // Display it for the driver.
//                telemetry.addData("Path1", "Running to %7d :%7d :%7d :%7d", newFrontLeftTarget,
//                        newFrontRightTarget,
//                        newBackLeftTarget,
//                        newBackRightTarget);
//                telemetry.addData("Path2", "Running at %7d :%7d :%7d :%7d",
//                        robot.frontLeftMotor.getCurrentPosition(),
//                        robot.frontRightMotor.getCurrentPosition(),
//                        robot.backLeftMotor.getCurrentPosition(),
//                        robot.backRightMotor.getCurrentPosition());
//
//                telemetry.addData("right", "%.2f", robot.odsSensorR.getRawLightDetected());
//                telemetry.addData("left", "%.2f", robot.odsSensorL.getRawLightDetected());
//
//
//                telemetry.update();
//
//                // Allow time for other processes to run.
//                idle();
//            }
//        }
//
//        // Stop all motion;
//        robot.frontLeftMotor.setPower(0);
//        robot.frontRightMotor.setPower(0);
//        robot.backLeftMotor.setPower(0);
//        robot.backRightMotor.setPower(0);
//
//        // Turn off RUN_TO_POSITION
//        robot.frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        robot.backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//        //  sleep(250);   // optional pause after each move
//    }

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



    public void shoot(double speed,
                      double time) throws InterruptedException {


        // Ensure that the opmode is still active .
        if (opModeIsActive()) {


            robot.flywheelLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            // reset the timeout time and start motion.
            runtime.reset();

            robot.flywheelLeftMotor.setPower(Math.abs(speed));

            sleep(4000);

            robot.servo.setPosition(1);

            intake(1, 10);


        }


        // keep looping while we are still active, and there is time left, and both motors are running.
        while (opModeIsActive() &&
                (runtime.seconds() < time)) {


            telemetry.update();

            // Allow time for other processes to run.
            idle();
        }

        // Stop all motion;
        robot.flywheelLeftMotor.setPower(0);

        // Turn off RUN_TO_POSITION
        robot.flywheelLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //  sleep(250);   // optional pause after each move
    }

    public void intake(double speed,
                       double time) throws InterruptedException {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            robot.intakeBeltMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            // reset the timeout time and start motion.
            runtime.reset();
            robot.intakeBeltMotor.setPower(Math.abs(speed));
            conveyor(-1, time);

 //           robot.leftServo.setPower(1);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < time)) {


                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.intakeBeltMotor.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.intakeBeltMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void flywheel(double speed,
                       double time) throws InterruptedException {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            robot.flywheelLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            // reset the timeout time and start motion.
            runtime.reset();
            robot.flywheelLeftMotor.setPower(Math.abs(speed));

            //           robot.leftServo.setPower(1);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < time)) {


                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.flywheelLeftMotorRampControl.setPowerTo(0);

            // Turn off RUN_TO_POSITION
            robot.flywheelLeftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void conveyor(double speed,
                       double time) throws InterruptedException {


        // Ensure that the opmode is still active
        if (opModeIsActive()) {

            robot.conveyorbelt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


            // reset the timeout time and start motion.
            runtime.reset();
            robot.conveyorbelt.setPower(speed);

            //           robot.leftServo.setPower(1);


            // keep looping while we are still active, and there is time left, and both motors are running.
            while (opModeIsActive() &&
                    (runtime.seconds() < time)) {


                telemetry.update();

                // Allow time for other processes to run.
                idle();
            }

            // Stop all motion;
            robot.conveyorbelt.setPower(0);

            // Turn off RUN_TO_POSITION
            robot.conveyorbelt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

            //  sleep(250);   // optional pause after each move
        }
    }
    public void beaconPressR() throws InterruptedException {

        encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
        encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
        sleep(500);
        number = 1;

        if(robot.colorSensor.red() > robot.colorSensor.blue()) {

            return;

        }
        else{

            sleep(4000);
            encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
            encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            sleep(1000);
            number = 2;

            if(robot.colorSensor.red() > robot.colorSensor.blue()) {

                return;

            }
            else {

                sleep(4000);
                encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
                number = 3;
            }

        }

    }

    public void beaconPressB() throws InterruptedException {

        encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
        encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
        sleep(500);

        if(robot.colorSensor.red() < robot.colorSensor.blue()) {

            return;

        }
        else{

            robot.flywheelLeftMotor.setPower(.75);
            encoderDrive(DRIVE_SPEED, 0, -16, 4.0);
            sleep(2000);
            robot.servo.setPosition(1);
            intake(1, 3);
            robot.flywheelLeftMotor.setPower(0);

            encoderDrive(DRIVE_SPEED, 0, 20, 4.0);
            encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            sleep(1000);

            if(robot.colorSensor.red() < robot.colorSensor.blue()) {

                return;

            }
            else {

                sleep(4000);
                encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            }

        }

    }
    public void beaconPressB2() throws InterruptedException {

        encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
        encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
        sleep(500);
        number = 1;

        if(robot.colorSensor.red() < robot.colorSensor.blue()) {

            return;

        }
        else{

            sleep(5000);
            encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
            encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
            sleep(1000);
            number = 2;

            if(robot.colorSensor.red() < robot.colorSensor.blue()) {

                return;

            }
            else {

                sleep(4000);
                encoderDrive(DRIVE_SPEED, 0, 5, 4.0);
                encoderDrive(DRIVE_SPEED, 0, -3, 4.0);
                number = 3;
            }

        }

    }
}