package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.sun.tools.javac.comp.Todo;

@Autonomous (name = "Auto with robot object")
public class ChopAuto extends LinearOpMode {
    Robot robot = new Robot();

    @Override
    public void runOpMode() throws NullPointerException {
        robot.hardwareMap(hardwareMap);
        waitForStart();
        robot.encodersForward(6, 1);
        robot.stopDriving();
        TelemetryUpdate();
    }

    public void TelemetryUpdate() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Front Left Motor Power", robot.motorFrontLeft.getPower());
        telemetry.addData("Front Right Motor Power", robot.motorFrontRight.getPower());
        telemetry.addData("Back Left Motor Power", robot.motorBackLeft.getPower());
        telemetry.addData("Back Right Motor Power", robot.motorBackRight.getPower());
        telemetry.addData("Servo Power", robot.wheel.getPower());
        telemetry.addData("Arm Power", robot.arm.getPower());
        telemetry.update();
    }
}
