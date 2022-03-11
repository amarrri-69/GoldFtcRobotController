package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;


@Autonomous(name = "Auto Blue Bottom")
public class Autobluebottom extends LinearOpMode {

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

    final double     TICKS   = 537.7 ;
    final double     GEAR   = 1;
    final double     WHEEL   = 3.77953; //diameter
    final double     TICKS_PER_INCH  = (TICKS * GEAR)/(WHEEL * 3.1415);

    boolean blue1;
    boolean blue2;
    boolean yellow1;
    boolean yellow2;

    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontLeft = hardwareMap.get(DcMotorEx.class, "frontLeft");
        motorBackLeft = hardwareMap.get(DcMotorEx.class, "backLeft");
        motorFrontRight = hardwareMap.get(DcMotorEx.class, "frontRight");
        motorBackRight = hardwareMap.get(DcMotorEx.class,"backRight");

        arm = hardwareMap.get(DcMotorEx.class, "arm");

        intake = hardwareMap.get(DcMotorEx.class, "intake");

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

        motorFrontRight.setPower(power); //everything is reversed because of the rotation of our gears - Carter
        motorFrontLeft.setPower(power);
        motorBackRight.setPower(power);
        motorBackLeft.setPower(power);

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

    public void encodersStrafeL(int inches, double power) { //left strafe
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

    public void encodersStrafeR(int inches, double power) { //strafe right into my heart
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

    public void encodersTurnL(int inches, double power) {
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

    public void encodersTurnR(int inches, double power) {
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

        while (arm.isBusy()) {
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

    public void Stop() {
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
    }

//    public void carousel(double power) {
//        wheel.setPower(power);
//    }

    public void TelemetryUpdate() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Front Left Motor Power", motorFrontLeft.getPower());
        telemetry.addData("Front Right Motor Power", motorFrontRight.getPower());
        telemetry.addData("Back Left Motor Power", motorBackLeft.getPower());
        telemetry.addData("Back Right Motor Power", motorBackRight.getPower());
        //telemetry.addData("Servo Power", wheel.getPower());
        //telemetry.addData("Arm Power", arm.getPower());
        //telemetry.addData("Claw Power", Claw.getPosition());
        telemetry.update();
    }
}
