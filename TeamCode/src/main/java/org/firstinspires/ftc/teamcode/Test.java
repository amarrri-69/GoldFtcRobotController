package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import org.firstinspires.ftc.robotcore.external.Telemetry;

@Autonomous(name = "Duck Test")
public class Test extends LinearOpMode {
    CRServo wheel;
    ColorSensor duck;
    ColorSensor duck2;
    boolean yellow1;
    boolean red1;
    boolean yellow2;
    boolean red2;
    boolean blue1;
    boolean blue2;
    boolean gray1;
    boolean gray2;

    @Override
    public void runOpMode() {
        wheel = hardwareMap.get(CRServo.class, "carousel");
        duck = hardwareMap.get(ColorSensor.class, "duck");
        duck2 = hardwareMap.get(ColorSensor.class, "duck2");

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.addData("Servo Power", wheel.getPower());
            telemetry.addData("red1", duck.red());
            telemetry.addData("red2", duck2.red());
            telemetry.addData("blue1", duck.blue());
            telemetry.addData("blue2", duck2.blue());
            telemetry.addData("green1", duck.green());
            telemetry.addData("green2", duck2.green());
            telemetry.addData("gray1", gray1);
            telemetry.update();
            int difference1 = duck.green() - duck.red();
            yellow1 = (duck.green() > duck.blue() && duck.green() > duck.red() && duck.red() > duck.blue() && difference1 > 20);
            red1 = (duck.green() > duck.blue() && duck.green() < duck.red() && difference1 > 20);
            blue1 = (duck.blue() > duck.red() && duck.blue() > duck.green() && duck.green() > duck.red() && difference1 > 20);
            gray1 = (duck.green() > duck.blue() && duck.green() > duck.red() && duck.red() < duck.blue() && difference1 > 20);

            int difference2 = duck2.green() - duck2.red();
            yellow2 = (duck2.green() > duck2.blue() && duck2.green() > duck2.red() && duck2.red() > duck2.blue() && difference2 > 20);
            red2 = (duck2.green() > duck2.blue() && duck2.green() < duck2.red() && difference2 > 20);
            blue2 = (duck2.blue() > duck2.red() && duck2.blue() > duck2.green() && duck2.green() > duck2.red() && difference2 > 20);
            gray2 = (duck2.green() > duck2.blue() && duck2.green() > duck2.red() && duck.red() < duck.blue() && difference2 > 20);
            //yellow: green > blue, green > red, red > blue, difference > 20
            //red: green > blue, green > red, red > blue, difference < 20
            //blue:
            int Layer = hardwareMap.appContext.getResources().getIdentifier("RelativeLayout", "id", hardwareMap.appContext.getPackageName());

        }
        if (red1 && red2) {

        } else if (yellow1 && red2) {
            wheel.setPower(1);
            TelemetryUpdate();

        } else if (red1 && yellow2) {
            TelemetryUpdate();
        } else {
            TelemetryUpdate();
        }
    }


    public void TelemetryUpdate() {




//        if (red1 || yellow1) {
//            telemetry.addData("DUCK R RED", red1);
//            telemetry.addData("DUCK R YELLOW", yellow1);
//        } else {
//            telemetry.addData("red", duck.red());
//         \   telemetry.addData("green", duck.green());
//            telemetry.addData("blue", duck.blue());
//        }
//
//        if (  red2 || yellow2) {
//            telemetry.addData("DUCK L RED", red2);
//            telemetry.addData("DUCK L YELLOW", yellow2);
//        } else {
//            telemetry.addData("red2", duck2.red());
//            telemetry.addData("green2", duck2.green());
//            telemetry.addData("blue2", duck2.blue());
//        }
        telemetry.update();
    }
}
////In expansion hub, 0 for the sensor is broken (current sensor is 1 and 3)