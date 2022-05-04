package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;


@Autonomous (name = "Sensor Auto Red Bottom")
public class Autosensorredbottom extends LinearOpMode {

    int level;

    DcMotorEx motorFrontLeft;
    DcMotorEx motorBackLeft;
    DcMotorEx motorFrontRight;
    DcMotorEx motorBackRight;

    DcMotorEx arm;

    DcMotorEx intake;

    CRServo wheel;

    ColorSensor duck;
    ColorSensor duck2;

    final double     TICKS   = 537.7 ;//TICKS_PER_MOTOR_ROTATION
    final double     GEAR   = 1; //gears??
    final double     WHEEL   = 3.77953; //WHEEL_DIAMETER_INCHES
    final double     TICKS_PER_INCH  = (TICKS * GEAR)/(WHEEL * 3.1415);

    boolean red1;
    boolean red2;
    boolean yellow1;
    boolean yellow2;
    boolean gray1;
    boolean gray2;


    @Override
    public void runOpMode() throws InterruptedException {
        //frontleft = frontright
        //frontright = backleft
        //backleft = frontleft
        //backright = backright

        motorFrontLeft = hardwareMap.get(DcMotorEx.class,"frontRight");
        motorBackLeft = hardwareMap.get(DcMotorEx.class,"frontLeft");
        motorFrontRight = hardwareMap.get(DcMotorEx.class,"backLeft");
        motorBackRight = hardwareMap.get(DcMotorEx.class,"backRight");

        arm = hardwareMap.get(DcMotorEx.class,"arm");

        intake = hardwareMap.get(DcMotorEx.class,"claw");

        wheel = hardwareMap.get(CRServo.class, "carousel");

        duck = hardwareMap.get(ColorSensor.class, "duck");
        duck2 = hardwareMap.get(ColorSensor.class, "duck2");

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {
            TelemetryUpdate();
            encodersForward(3, 0.23);
            sleep(500);
            encodersTurnLeft(2, 0.23);
            sleep(500);//2 front right 3//back right //
            encodersStrafeLeft(10, 0.23);
            sleep(500);
            encodersTurnRight(4, 0.23);
            sleep(500);
            encodersForward(20, 0.23);
            sleep(500);
            encodersTurnLeft(1, 0.23);
            sleep(500);


            // encodersStrafeRight(2, 0.2);
            // sleep(500);
            // encodersForward(23, 0.23);
            sleep(500);
            // encodersTurnLeft(3,2);
            stopDriving();
            sleep(1000);

            //let the color sensor do its thing
            GetLevel(duck.red(), duck.green(), duck.blue(), duck2.red(), duck2.green(), duck2.blue());

            //TODO:
            // 1. move the robot to the shipping hub
            // 2. get the freight on the right level
            // 3. drop the freight
            // 4. park in the storage unit

            //encodersStrafeLeft(20, 0.2);
            //RaiseArm();
            //intake.setPower(0.3);
            //sleep(200);
            //encodersStrafeLeft(58, 0.2);
            //encodersForward(5, 0.2);

            stopDriving();
            telemetry.update();
            sleep(10000000);

        }
    }

    public void RaiseArm() {
        if (level == 1) {
            arm.setPower(0.2);
            sleep(1000);
        }

        else if (level == 2) {
            arm.setPower(0.2);
            sleep(2000);
        } else if (level == 3) {
            arm.setPower(0.2);
            sleep (3000);
        }
    }

    public void GetLevel(float r1, float g1, float b1, float r2, float g2, float b2) {
        int difference1 = duck.green() - duck.red();
        yellow1 = (duck.green() > duck.blue() && duck.green() > duck.red() && duck.red() > duck.blue() && difference1 > 20);
        red1 = (duck.green() > duck.blue() && duck.green() < duck.red() && difference1 > 20);
        //blue1 = (duck.blue() > duck.red() && duck.blue() > duck.green() && duck.green() > duck.red() && difference1 > 20);
        gray1 = (duck.green() > duck.blue() && duck.green() > duck.red() && duck.red() < duck.blue() && difference1 > 20);

        int difference2 = duck2.green() - duck2.red();
        yellow2 = (duck2.green() > duck2.blue() && duck2.green() > duck2.red() && duck2.red() > duck2.blue() && difference2 > 20);
        red2 = (duck2.green() > duck2.blue() && duck2.green() < duck2.red() && difference2 > 20);
        // blue2 = (duck2.blue() > duck2.red() && duck2.blue() > duck2.green() && duck2.green() > duck2.red() && difference2 > 20);
        gray2 = (duck2.green() > duck2.blue() && duck2.green() > duck2.red() && duck2.red() < duck2.blue() && difference2 > 20);
        //yellow: green > blue, green > red, red > blue, difference > 20
        //red: green > blue, green > red, red > blue, difference < 20
        //blue:

        if (red1) {
            level = 1;
            duck.enableLed(false);
            duck2.enableLed(false);
            TelemetryUpdate();
        }

        else if (yellow1) {
            level = 2;
            duck.enableLed(false);
            duck2.enableLed(false);
            TelemetryUpdate();
        }

        else if (red1 && yellow2) {
            level = 3;
            duck.enableLed(false);
            duck2.enableLed(false);
        }

        else {
            level = 0;
            TelemetryUpdate();
        }
    }

    public void encodersForward(int inches, double power) {
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        motorBackRight.setTargetPosition(motorBackRight.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        motorBackLeft.setTargetPosition(motorBackLeft.getCurrentPosition() + (int)(-inches*TICKS_PER_INCH));

        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);

        while (motorFrontLeft.isBusy() && motorFrontRight.isBusy() && motorBackLeft.isBusy() && motorBackRight.isBusy()) { }

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
    }

    public void encodersBackward(int inches, double power) {
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorBackRight.setTargetPosition(motorBackRight.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorBackLeft.setTargetPosition(motorBackLeft.getCurrentPosition() + (int)(inches*TICKS_PER_INCH));

        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);
        while (motorFrontLeft.isBusy() && motorFrontRight.isBusy() && motorBackLeft.isBusy() && motorBackRight.isBusy()) { }

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
    }

    public void encodersStrafeLeft (int inches, double power) {
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        motorBackRight.setTargetPosition(motorBackRight.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorBackLeft.setTargetPosition(motorBackLeft.getCurrentPosition() + (int)(inches*TICKS_PER_INCH));

        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);
        while (motorFrontLeft.isBusy() && motorFrontRight.isBusy() && motorBackLeft.isBusy() && motorBackRight.isBusy()) { }

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
    }

    public void encodersStrafeRight (int inches, double power) {
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorBackRight.setTargetPosition(motorBackRight.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        motorBackLeft.setTargetPosition(motorBackLeft.getCurrentPosition() + (int)(-inches*TICKS_PER_INCH));

        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);
        while (motorFrontLeft.isBusy() && motorFrontRight.isBusy() && motorBackLeft.isBusy() && motorBackRight.isBusy()) { }

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
    }

    public void encodersTurnLeft (int inches, double power) {
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        motorBackRight.setTargetPosition(motorBackRight.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        motorBackLeft.setTargetPosition(motorBackLeft.getCurrentPosition() + (int)(inches*TICKS_PER_INCH));

        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);
        while (motorFrontLeft.isBusy() && motorFrontRight.isBusy() && motorBackLeft.isBusy() && motorBackRight.isBusy()) { }

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
    }

    public void encodersTurnRight (int inches, double power) {
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorBackRight.setTargetPosition(motorBackRight.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorBackLeft.setTargetPosition(motorBackLeft.getCurrentPosition() + (int)(-inches*TICKS_PER_INCH));

        motorBackLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorFrontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motorBackRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motorFrontLeft.setPower(power);
        motorFrontRight.setPower(power);
        motorBackLeft.setPower(power);
        motorBackRight.setPower(power);

        while (motorFrontLeft.isBusy() && motorFrontRight.isBusy() && motorBackLeft.isBusy() && motorBackRight.isBusy()) { }

        motorBackLeft.setPower(0);
        motorBackRight.setPower(0);
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
    }

    public void encoderarmPower(int ticks, double power) {
        arm.setTargetPosition(ticks);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        arm.setPower(1);

        while(arm.isBusy()){}

        arm.setPower(0);

    }

    public void encoderIntakePower (int ticks, double speed) {
        intake.setTargetPosition(ticks);
        intake.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        intake.setVelocity(speed);

        while(intake.isBusy()){ }

        intake.setVelocity(0);
    }

    public int inchesToTicks(int inches) {
        double tickPerInch = 360 / (3.77 * 3.14);
        return (int) (inches * tickPerInch);
        //inches; //312 RPM, diameter: 3.77 inches
    }


    public void stopDriving() { //stop!!!
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
    }


    public void TelemetryUpdate() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Level",level);

        //telemetry.addData("Servo Power", wheel.getPower());

        telemetry.addData("Arm Power", arm.getPower());

        // telemetry.addData("Claw Power", claw.getPosition());



        if (red1 || yellow1) {
            telemetry.addData("DUCK R RED", red1);
            telemetry.addData("DUCK R YELLOW", yellow1);
            telemetry.addData("DUCK R GRAY",gray1 );
        } else {
            telemetry.addData("red", duck.red());
            telemetry.addData("green", duck.green());
            telemetry.addData("blue", duck.blue());
        }

        if (red2 || yellow2) {
            telemetry.addData("DUCK L RED", red2);
            telemetry.addData("DUCK L YELLOW", yellow2);
            telemetry.addData("DUCK L Gray",gray2 );
        } else {
            telemetry.addData("red2", duck2.red());
            telemetry.addData("green2", duck2.green());
            telemetry.addData("blue2", duck2.blue());
        }

        telemetry.update();
    }
}
