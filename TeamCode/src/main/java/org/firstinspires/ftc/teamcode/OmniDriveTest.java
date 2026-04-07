package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "OmniMotorTest")
public class OmniDriveTest extends LinearOpMode {

    private static final double maxSpeed = 0.5;

    private final ElapsedTime tickTimer = new ElapsedTime();


    private DcMotor leftFrontMotor;
    private DcMotor leftBackMotor;
    private DcMotor rightFrontMotor;
    private DcMotor rightBackMotor;


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
        leftFrontMotor = hardwareMap.get(DcMotor.class, "leftFrontMotor");
        leftBackMotor = hardwareMap.get(DcMotor.class, "leftBackMotor");
        rightFrontMotor = hardwareMap.get(DcMotor.class, "rightFrontMotor");
        rightBackMotor = hardwareMap.get(DcMotor.class, "rightBackMotor");

        leftFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFrontMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBackMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void run() {

    }

    public void tick() {

        double scaledY = Math.signum(gamepad1.left_stick_y) * Math.pow(Math.abs(gamepad1.left_stick_y), 2) * maxSpeed;
        double scaledX = Math.signum(gamepad1.left_stick_x) * Math.pow(Math.abs(gamepad1.left_stick_x), 2) * maxSpeed;
        double scaledR = Math.signum(gamepad1.right_stick_x) * Math.pow(Math.abs(gamepad1.right_stick_x), 2) * maxSpeed;

        double lf = scaledX - scaledY + scaledR;
        double lb = scaledX + scaledY - scaledR;
        double rf = -scaledX - scaledY - scaledR;
        double rb = -scaledX + scaledY + scaledR;

        double maxMagnitude = Math.max(1.0, Math.max(Math.max(Math.abs(lf), Math.abs(lb)), Math.max(Math.abs(rf), Math.abs(rb))));

        leftFrontMotor.setPower(lf / maxMagnitude);
        leftBackMotor.setPower(lb / maxMagnitude);
        rightFrontMotor.setPower(rf / maxMagnitude);
        rightBackMotor.setPower(rb / maxMagnitude);

        updateTelemetry();
        tickTimer.reset();
    }

    public void updateTelemetry() {
        telemetry.addLine(String.valueOf(tickTimer.milliseconds()));
        telemetry.update();
    }
}
