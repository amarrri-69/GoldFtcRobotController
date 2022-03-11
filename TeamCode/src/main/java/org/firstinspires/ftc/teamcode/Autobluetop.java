package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;

@Autonomous(name = "Auto blue top")
public class Autobluetop extends LinearOpMode {
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;

    CRServo wheel;

    ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");

        wheel = hardwareMap.get(CRServo.class, "carousel");

        colorSensor = hardwareMap.get(ColorSensor.class, "duck");

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {
            Forward(1);
            sleep(300);
            TurnL(0.5);
            sleep(600);
            Forward(1);
            sleep(1000);
            Stop();
            // Forward(0.39);
            // sleep(105);
            // StrafeL(0.420);
            // sleep(1200);
            // TurnR(0.4);
            // sleep(800);
            // Backward(0.35);
            // sleep(100);
            // carousel(-0.5);
            // sleep(3300);
            // StrafeR(0.4);
            // sleep(1505);
            // Backward(0.35);
            // sleep(320);
            // TurnL(0.3);
            // sleep(190);

            TelemetryUpdate();
        }
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
        motorBackRight.setPower(Power);
        motorBackLeft.setPower(Power);
    }

    public void StrafeL(double Power) { //right strafe
        motorFrontRight.setPower(-Power);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(Power);
        motorBackLeft.setPower(-Power);
    }

    public void StrafeR(double Power) { //right strafe
        motorFrontRight.setPower(Power);
        motorFrontLeft.setPower(-Power);
        motorBackRight.setPower(-Power);
        motorBackLeft.setPower(Power);
    }

    public void TurnL(double Power) {
        motorFrontRight.setPower(-Power);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(-Power);
        motorBackLeft.setPower(Power);
    }

    public void TurnR(double Power) {
        motorFrontRight.setPower(Power);
        motorFrontLeft.setPower(-Power);
        motorBackRight.setPower(Power);
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

    public void TelemetryUpdate() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Front Left Motor Power", motorFrontLeft.getPower());
        telemetry.addData("Front Right Motor Power", motorFrontRight.getPower());
        telemetry.addData("Back Left Motor Power", motorBackLeft.getPower());
        telemetry.addData("Back Right Motor Power", motorBackRight.getPower());
        telemetry.addData("Servo Power", wheel.getPower());
        //telemetry.addData("Arm Power", arm.getPower());
        //telemetry.addData("Claw Power", Claw.getPosition());
        telemetry.update();
    }
}