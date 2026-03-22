package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name="TestOpModeAndroid")
public class MajorTomTestOpMode extends LinearOpMode {
    public int ticks = 0;
    public ElapsedTime timer = new ElapsedTime();

    public CRServo leftFeeder;
    //public CRServo rightFeeder;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        run();
        while(opModeIsActive()){
            tick();
        }
    }

    public void initialize() {
        leftFeeder = hardwareMap.get(CRServo.class, "left_feeder");
        //rightFeeder = hardwareMap.get(CRServo.class, "right_feeder");
    }

    public void run(){

    }

    public void tick() {
        ticks++;
        if (gamepad1.a) {
            leftFeeder.setPower(1);
            //rightFeeder.setPower(1);
        } else {
            leftFeeder.setPower(0);
            //rightFeeder.setPower(0);
        }
        updateTelemetry();
        timer.reset();
    }

    public void updateTelemetry(){
        telemetry.addLine(String.valueOf(timer.milliseconds()));
        telemetry.addLine("GamepadA " + gamepad1.a);
        telemetry.update();
    }
}