package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
//import com.qualcomm.robotcore.hardware.VoltageSensor;


@Autonomous (name = "simple blue top")
public class Autobluetop extends LinearOpMode {

    int level;

    DcMotorEx motorFrontLeft;
    DcMotorEx motorBackLeft;
    DcMotorEx motorFrontRight;
    DcMotorEx motorBackRight;

    CRServo wheel;
    DcMotor intake;
    DcMotorEx arm;

    //VoltageSensor v;
    double v_scale;

    final double     TICKS   = 537.7 ;//TICKS_PER_MOTOR_ROTATION
    final double     GEAR   = 1; //gears??
    final double     WHEEL   = 3.77953; //WHEEL_DIAMETER_INCHES
    final double     TICKS_PER_INCH  = (TICKS * GEAR)/(WHEEL * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException {
        //frontleft = backright
        //backright = frontleft


        motorFrontLeft = hardwareMap.get(DcMotorEx.class,"frontRight");
        motorBackLeft = hardwareMap.get(DcMotorEx.class,"frontLeft");
        motorFrontRight = hardwareMap.get(DcMotorEx.class,"backLeft");
        motorBackRight = hardwareMap.get(DcMotorEx.class,"backRight");

        intake = hardwareMap.get(DcMotor.class,"claw");

        wheel = hardwareMap.get(CRServo.class, "carousel");
        arm = hardwareMap.get(DcMotorEx.class,"arm");

        motorFrontLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorFrontRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackLeft.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motorBackRight.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //v = hardwareMap.voltageSensor.get("Motor Controller 1");

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {

            encoderarm(30, 0.4);
            encodersStrafeLeft(40, 0.1);

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

    public void carousel(double power, int time) {
        wheel.setPower(-power);
        sleep(time);
        wheel.setPower(0);
    }

    public void IntakePower (double power, int time){
        intake.setPower(power);
        sleep(time);
        intake.setPower(0);

    }

    public void encoderarm (int inches, double power) {
        arm.setTargetPosition(arm.getCurrentPosition() +(int)(-inches*TICKS_PER_INCH));
        arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
        arm.setPower(power);

        while (arm.isBusy()) { }

        arm.setPower(0);
    }

    public void stopWheel() { //stop!!!
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
    }
    public void forward(double power) {
        motorFrontLeft.setPower(-power);
        motorBackRight.setPower(power);
        motorFrontRight.setPower(-power);
        motorBackLeft.setPower(-power);
    }

    public void TelemetryUpdate() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Level", level);
    }

}
