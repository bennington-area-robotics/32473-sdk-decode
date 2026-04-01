package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "OmniDriveTest")
public class OmniDriveTest extends LinearOpMode {

    public int ticks;

    private static final double maxSpeed = 0.5;

    public ElapsedTime tickTimer = new ElapsedTime();


    public DcMotor leftFrontDrive;
    public DcMotor leftBackDrive;
    public DcMotor rightFrontDrive;
    public DcMotor rightBackDrive;


    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        run();
        while (opModeIsActive()) {
            tick();
        }
    }

    public void initialize() {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        leftBackDrive = hardwareMap.get(DcMotor.class, "leftBackDrive");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rightBackDrive");

        leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void run() {

    }

    public void tick() {
        ticks++;

        double scaledY = Math.signum(gamepad1.left_stick_y) * Math.pow(Math.abs(gamepad1.left_stick_y), 2) * maxSpeed;
        double scaledX = Math.signum(gamepad1.left_stick_x) * Math.pow(Math.abs(gamepad1.left_stick_x), 2) * maxSpeed;
        double scaledR = Math.signum(gamepad1.right_stick_x) * Math.pow(Math.abs(gamepad1.right_stick_x), 2) * maxSpeed;

        leftFrontDrive.setPower(scaledX - scaledY + scaledR);
        leftBackDrive.setPower(scaledX + scaledY - scaledR);
        rightFrontDrive.setPower(-scaledX - scaledY - scaledR);
        rightBackDrive.setPower(-scaledX + scaledY + scaledR);

        updateTelemetry();
        tickTimer.reset();
    }

    public void updateTelemetry() {
        telemetry.addLine(String.valueOf(tickTimer.milliseconds()));
        telemetry.update();
    }
}
