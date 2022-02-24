package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.CRServo;

@Autonomous(name = "Auto Blue Bottom")
public class Autobluebottom extends LinearOpMode {
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    CRServo wheel;

    @Override
    public void runOpMode() throws InterruptedException {
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");

        wheel = hardwareMap.get(CRServo.class, "carousel");

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (isStopRequested()) return;

        if (opModeIsActive()) {
            Forward(0.6);
            sleep(100);
            StrafeR(0.45);
            sleep(950);
            Stop();
            sleep(1000);
            carousel(1);
            sleep(3300);
            Forward(0.761);
            sleep(500);
            StrafeL(0.58);
            sleep(860);
            TurnL(0.629);
            sleep(500);
            Backward(0.35);
            sleep(390);

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

    public void carousel(double power) {
        wheel.setPower(power);
    }

    public void ForwardRight(double Power) { //negative power will be Backwardleft
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(Power);
        motorBackLeft.setPower(0);
    }

    public void ForwardLeft(double Power) { //negative power will be BackwardRight
        motorFrontRight.setPower(Power);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(Power);
    }

    public void Stop() { //stop!!!
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
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
