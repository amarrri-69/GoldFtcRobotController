package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Autonomous(name = "Auto Red Top")
public class Autoredtop extends LinearOpMode {
    DcMotorEx motorFrontLeft;
    DcMotorEx motorBackLeft;
    DcMotorEx motorFrontRight;
    DcMotorEx motorBackRight;

    DcMotorEx arm;

    DcMotorEx claw;

    CRServo wheel;

    ColorSensor colorSensor;

    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontLeft = hardwareMap.get(DcMotorEx.class,"frontLeft");
        motorBackLeft = hardwareMap.get(DcMotorEx.class,"backLeft");
        motorFrontRight = hardwareMap.get(DcMotorEx.class,"frontRight");
        motorBackRight = hardwareMap.get(DcMotorEx.class,"backRight");

        arm = hardwareMap.get(DcMotorEx.class,"arm");

        claw = hardwareMap.get(DcMotorEx.class,"claw");

        wheel = hardwareMap.get(CRServo.class, "carousel");

        colorSensor = hardwareMap.get(ColorSensor.class, "duck");

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {
            Forward(0.4);
            sleep(200);
            TurnR(0.4);
            sleep(500);
            Forward(1);
            sleep(1000);
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

    public void TelemetryUpdate() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Front Left Motor Power", motorFrontLeft.getPower());
        telemetry.addData("Front Right Motor Power", motorFrontRight.getPower());
        telemetry.addData("Back Left Motor Power", motorBackLeft.getPower());
        telemetry.addData("Back Right Motor Power", motorBackRight.getPower());
        telemetry.addData("Servo Power", wheel.getPower());
        telemetry.addData("Arm Power", arm.getPower());
        //telemetry.addData("Claw Power", claw.getPosition());
        telemetry.update();
    }
}