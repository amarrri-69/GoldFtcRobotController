package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "Duck Test blue")
public class testblue extends LinearOpMode {
    CRServo wheel;
    ColorSensor duck;
    ColorSensor duck2;
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor arm;
    DcMotor intake;
    int level;

    @Override
    public void runOpMode() {
        wheel = hardwareMap.get(CRServo.class, "carousel");

        duck = hardwareMap.get(ColorSensor.class, "duck"); //right color sensor
        duck2 = hardwareMap.get(ColorSensor.class, "duck2"); //left color sensor

        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");

        arm = hardwareMap.dcMotor.get("arm");
        intake = hardwareMap.dcMotor.get("claw");

        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {

            waitForStart();
            if (isStopRequested()) return;

            if (opModeIsActive()) {
                duck.enableLed(true);
                duck2.enableLed(true);
                Forward(0.6);
                sleep(370);
                StrafeL(0.5);
                sleep(200);
                stop();
                sleep(500);
                GetLevel(duck.red(), duck.green(), duck.blue(), duck2.red(), duck2.green(), duck2.blue());
                TurnL(0.2);
                sleep(500);
                RaiseArmThing();
                //and then we do things

            }
            }
        }

    public void GetLevel(float r1, float g1, float b1, float r2, float g2, float b2) {
        float diff1 = g1 - r1;
        boolean yellow1 = (g1 > b1 && g1 > r1 && r1 > b1 && diff1 < 20);
        boolean red1 = (g1 > b1 && g1 < r1 && diff1 > 20);
        boolean blue1 = (b1 > r1 && b1 > g1 && g1 > r1 && diff1 > 20);

        float diff2 = g2 - r2;
        boolean yellow2 = (g2 > b2 && g2 > r2 && r2 > b2 && diff2 < 20);
        boolean red2 = (g2 > b2 && g2 < r2 && diff2 > 20);
        boolean blue2 = (b2 > r2 && b2 > g2 && g2 > r2 && diff2 > 20);

        if (blue1 && blue2) {
            level = 3;
            duck.enableLed(false);
            duck2.enableLed(false);

        } else if (yellow1 && blue2) {
            level = 2;
            duck.enableLed(false);
            duck2.enableLed(false);

        } else if (blue1 && yellow2) {
            level = 1;
            duck.enableLed(false);
            duck2.enableLed(false);

        } else {
            level = 0;
            TelemetryUpdate();
        }
    }

    public void RaiseArmThing() {
        switch (level) {
            case 1:
                EncoderArm(180, 0.5);
                sleep(200);
                intake.setPower(1);
                sleep(100);
                break;
            case 2:
                arm.setPower(1);
                sleep(300);
                intake.setPower(1);
                sleep(100);
                break;
            case 3:
                arm.setPower(1);
                sleep(400);
                intake.setPower(1);
                sleep(100);
                break;
            default:
                arm.setPower(0);
                break;
        }

    }
    public void EncoderArm(int ticks, double power) {
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setTargetPosition(ticks);

        if (ticks >= 0) {
            arm.setPower(power);
        } else {
            arm.setPower(-power);
        }

        while (arm.isBusy()) {
        }

        StopMoving();
    }

    public void StopMoving() {
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

    public void StrafeL(double Power) { //left strafe
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

    public void TurnL(double Power) { //turn left
        motorFrontRight.setPower(-Power);
        motorFrontLeft.setPower(Power);
        motorBackRight.setPower(-Power);
        motorBackLeft.setPower(Power);
    }

    public void TurnR(double Power) { //turn right
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

    public void Stop() {
        motorFrontRight.setPower(0);
        motorFrontLeft.setPower(0);
        motorBackRight.setPower(0);
        motorBackLeft.setPower(0);
    }


    public void TelemetryUpdate() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Servo Power", wheel.getPower());
        telemetry.addData("red R", duck.red());
        telemetry.addData("blue R", duck.blue());
        telemetry.addData("green R", duck.green());
        telemetry.addData("red L", duck2.red());
        telemetry.addData("blue L", duck2.blue());
        telemetry.addData("green L", duck2.green());
        telemetry.update();
    }
}
//In expansion hub, 0 for the sensor is broken (current sensor is 1 and 3)
