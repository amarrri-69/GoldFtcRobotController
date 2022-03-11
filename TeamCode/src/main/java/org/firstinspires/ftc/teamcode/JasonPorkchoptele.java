package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp (name = "teleop")
public class JasonPorkchoptele extends LinearOpMode {
    DcMotor motorFrontLeft;
    DcMotor motorBackLeft;
    DcMotor motorFrontRight;
    DcMotor motorBackRight;
    DcMotor arm;
    DcMotor claw;

    CRServo wheel;

    @Override
    public void runOpMode() {
        // Make sure ID match configuration
        motorFrontLeft = hardwareMap.dcMotor.get("frontLeft");
        motorBackLeft = hardwareMap.dcMotor.get("backLeft");
        motorFrontRight = hardwareMap.dcMotor.get("frontRight");
        motorBackRight = hardwareMap.dcMotor.get("backRight");

        wheel = hardwareMap.get(CRServo.class, "carousel");

        arm = hardwareMap.dcMotor.get("arm");
        claw = hardwareMap.dcMotor.get("claw");

        // Reverse the right side motors
        motorFrontRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorBackRight.setDirection(DcMotorSimple.Direction.REVERSE);


        waitForStart();
        if (isStopRequested()) return;

        while (opModeIsActive()) {
            //wheel motor
            double y = gamepad1.left_stick_y; // Controls speed; forward/backward
            double x = gamepad1.left_stick_x; // Controls strafe; leftward/rightward
            double rx = gamepad1.right_stick_x; // right is turn right, left is turn left

            double frontLeftPower = -(-rx + y - x);
            double backLeftPower = -(-rx + y + x);
            double frontRightPower = -(rx + y + x);
            double backRightPower = (rx + y - x);

            motorFrontLeft.setPower(0.81 * frontLeftPower);
            motorBackLeft.setPower(0.81 * backLeftPower);
            motorFrontRight.setPower(0.81 * frontRightPower);
            motorBackRight.setPower(0.81 * backRightPower);


            //arm motor
            double armPower = 0.01;
            arm.setPower(armPower);

            if (gamepad2.dpad_down) {
                armPower = -1;
                arm.setPower(armPower);
            }

            if (gamepad2.dpad_up) {
                armPower = 1;
                arm.setPower(armPower);
            }
//
//            int ticks = 0;
//
//            if (gamepad2.dpad_down) {
//                ++ticks;
//                //arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//                arm.setTargetPosition(ticks);
//                arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//
//                if (ticks > 0 && ticks < 180) {
//                    arm.setPower(1);
//                } else {
//                    arm.setPower(0);
//                }
//            }
//
//            if (gamepad2.dpad_up) {
//                --ticks;
//                //arm.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//                arm.setTargetPosition(ticks);
//                arm.setMode(DcMotorEx.RunMode.RUN_TO_POSITION);
//
//                if (ticks > 0 && ticks < 180) {
//                    arm.setPower(-1);
//                } else {
//                    arm.setPower(0);
//                }
//            }



//             //claw claw servo
            double clawPower = 0;
            claw.setPower(clawPower);

            if (gamepad2.b) {
                clawPower = 0.4;
                claw.setPower(clawPower);
            }

            if (gamepad2.a) {
                clawPower = -0.4;
                claw.setPower(clawPower);
            }


            //continuous servo
            double servoPower = 0.0;
            wheel.setPower(servoPower);

            if (gamepad2.y) {
                servoPower = 1;
                wheel.setPower(servoPower);
                TelemetryUpdate();
            }

            if (gamepad2.x){
                servoPower = -1;
                wheel.setPower(servoPower);
                TelemetryUpdate();
            }

            TelemetryUpdate();
        }
    }

    public void TelemetryUpdate() {
        telemetry.addData("Status", "Running");
        telemetry.addData("Front Left Motor Power", motorFrontLeft.getPower());
        telemetry.addData("Front Right Motor Power", motorFrontRight.getPower());
        telemetry.addData("Back Left Motor Power", motorBackLeft.getPower());
        telemetry.addData("Back Right Motor Power", motorBackRight.getPower());
        telemetry.addData("Servo Power", wheel.getPower());
        telemetry.addData("Arm Power", arm.getPower());
        telemetry.addData("Claw Power", claw.getPower());
        telemetry.update();
    }
}
