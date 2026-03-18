package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name="TestOpModeAndroid")
public class MajorTomTestOpMode extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        waitForStart();
        telemetry.addLine("hello");
        telemetry.update();
    }
}