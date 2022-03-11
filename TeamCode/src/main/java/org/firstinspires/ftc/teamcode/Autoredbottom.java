package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;


@Autonomous (name = "Auto Red Bottom")
public class Autoredbottom extends LinearOpMode {

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
            encodersForward(24, 0.23);
            encodersTurnLeft(3,0.2);
            Stop();
            sleep(1000);

//            color sensor path
            GetLevel(duck.red(), duck.green(), duck.blue(), duck2.red(), duck2.green(), duck2.blue());
            //encoderstrafeRight(10,0.5);
            //encoderstrafeLeft(10, 0.5);
            //encodersTurnLeft(10, 0.3);
            //encodersTurnRight(10, 0.3);

            Stop();
            sleep(10000000);
        }
    }

    public void GetLevel(float r1, float g1, float b1, float r2, float g2, float b2) {
        int VALUE = 30;
        boolean diff1 = (g1 - r1) < VALUE;
        red1 = (g1 > b1 && g1 > r1 && r1 > b1 && diff1);
        yellow1 = (g1 > b1 && g1 > r1 && !diff1);
        //boolean blue1 = (b1 > r1 && b1 > g1 && g1 > r1 && diff1 > 20);

        boolean diff2 = (g2 - r2) < VALUE;
        red2 = (g2 > b2 && g2 > r2 && r2 > b2 && diff2);
        yellow2 = (g2 > b2 && g2 > r2 && !diff2);
        //boolean blue2 = (b2 > r2 && b2 > g2 && g2 > r2 && diff2 > 20);

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

//        else if (red1 && yellow2) {
//            level = 3;
//            duck.enableLed(false);
//            duck2.enableLed(false);
//        }

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

    public void encodersStrafeLeft(int inches, double power) {
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
        arm.setTargetPosition(-80);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        arm.setPower(1);

        while(arm.isBusy())  {
        }

        arm.setPower(0);
    }

    public void encoderIntakePower (int ticks, double power) {
        intake.setTargetPosition(-80);
        intake.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        intake.setVelocity(1000);

        while(intake.isBusy()){ }

        intake.setVelocity(0);
    }

    public int inchesToTicks(int inches) {
        double tickPerInch = 360 / (3.77 * 3.14);
        return (int) (inches * tickPerInch);
        //inches; //312 RPM, diameter: 3.77 inches
    }

    public void stopDriving() {
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
        arm.setPower(0);
    }

    public void Stop() { //stop!!!
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
    }

//    public void carousel(double power) {
//        wheel.setPower(power);
//  }

    public void TelemetryUpdate() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Level",level);
        //telemetry.addData("Servo Power", wheel.getPower());
        //telemetry.addData("Arm Power", arm.getPower());
        //telemetry.addData("Claw Power", claw.getPosition());
        telemetry.addData("red R", duck.red());
        telemetry.addData("blue R", duck.blue());
        telemetry.addData("green R", duck.green());
        telemetry.addData("red L", duck2.red());
        telemetry.addData("blue L", duck2.blue());
        telemetry.addData("green L", duck2.green());

        if (red1 || yellow1) {
            telemetry.addData("DUCK R RED", red1);
            telemetry.addData("DUCK R YELLOW", yellow1);
        } else {
            telemetry.addData("red", duck.red());
            telemetry.addData("green", duck.green());
            telemetry.addData("blue", duck.blue());
        }

        if (red2 || yellow2) {
            telemetry.addData("DUCK L RED", red2);
            telemetry.addData("DUCK L YELLOW", yellow2);
        } else {
            telemetry.addData("red2", duck2.red());
            telemetry.addData("green2", duck2.green());
            telemetry.addData("blue2", duck2.blue());
        }
        telemetry.update();
    }
}
