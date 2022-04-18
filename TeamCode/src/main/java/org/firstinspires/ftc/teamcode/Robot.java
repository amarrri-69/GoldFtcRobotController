package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Robot {
    DcMotorEx motorFrontLeft;
    DcMotorEx motorBackLeft;
    DcMotorEx motorFrontRight;
    DcMotorEx motorBackRight;

    DcMotorEx arm;

    DcMotorEx intake;

    CRServo wheel;

    ColorSensor colorSensor;

    final double     TICKS   = 537.7 ;//TICKS_PER_MOTOR_ROTATION
    final double     GEAR   = 1; //TICKS_PER_MOTOR_ROTATION
    final double     WHEEL   = 3.77953;//WHEEL_DIAMETER_INCHES
    final double     TICKS_PER_INCH  = (TICKS)/(WHEEL * 3.1415);

    public void hardwareMap (HardwareMap hardwareMap) {
        motorFrontLeft = hardwareMap.get(DcMotorEx.class,"frontLeft");
        motorBackLeft = hardwareMap.get(DcMotorEx.class,"backLeft");
        motorFrontRight = hardwareMap.get(DcMotorEx.class,"frontRight");
        motorBackRight = hardwareMap.get(DcMotorEx.class,"backRight");

        arm = hardwareMap.get(DcMotorEx.class,"arm");

        intake = hardwareMap.get(DcMotorEx.class,"claw");

        wheel = hardwareMap.get(CRServo.class, "carousel");

        colorSensor = hardwareMap.get(ColorSensor.class, "duck");

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        intake.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void encodersForward(int inches, double power) {
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
        motorFrontRight.setTargetPosition(motorFrontRight.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
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

    public void encodersRight (int inches, double power) {
        motorFrontLeft.setTargetPosition(motorFrontLeft.getCurrentPosition() +(int)(inches*TICKS_PER_INCH));
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

    public void encoderArm(int ticks, double power) {
        arm.setTargetPosition(-80);
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        arm.setPower(1);

        while(arm.isBusy()){ }

        arm.setPower(0);
    }

    public void stopDriving() {
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
        arm.setPower(0);
    }

    public void Forward(double Power) {
        motorFrontRight.setPower(-Power); //everything is reversed because of the rotation of our gears - Carter
        motorFrontLeft.setPower(-Power);
        motorBackRight.setPower(Power);
        motorBackLeft.setPower(-Power);
    }

    public void Backward(double Power) {
        motorFrontRight.setPower(Power);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(-Power);
        motorBackLeft.setPower(Power);
    }

    public void StrafeL(double Power) { //right strafe
        motorFrontRight.setPower(-Power);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(-Power);
        motorBackLeft.setPower(-Power);
    }

    public void StrafeR(double Power) { //right strafe
        motorFrontRight.setPower(Power);
        motorFrontLeft.setPower(-Power);
        motorBackRight.setPower(Power);
        motorBackLeft.setPower(Power);
    }

    public void TurnL(double Power) {
        motorFrontRight.setPower(-Power);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(Power);
        motorBackLeft.setPower(Power);
    }

    public void TurnR(double Power) {
        motorFrontRight.setPower(Power);
        motorFrontLeft.setPower(-Power);
        motorBackRight.setPower(-Power);
        motorBackLeft.setPower(-Power);
    }

    public void Stop() { //stop!!!
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
    }

    public void carousel(double power) {
        wheel.setPower(power);
    }
}
