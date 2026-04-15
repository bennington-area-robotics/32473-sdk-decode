package org.firstinspires.ftc.teamcode.components;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.OpModeCore;
import org.firstinspires.ftc.teamcode.hardware.SmartMotor;
import org.firstinspires.ftc.teamcode.hardware.controllers.VelocityPID;

@Configurable
public class Launcher {

    private final ElapsedTime timer = new ElapsedTime();

    private static double TARGET_VELOCITY = 1000;
    private static double MIN_VELOCITY = TARGET_VELOCITY - 100;

    private final VelocityPID controller;

    public static double kP = 0.0001, kI = 0, kD = 0, kf = 0.001, tolerance = 30;

    private final SmartMotor motor;

    public Launcher(SmartMotor motor) {
        this.controller = new VelocityPID.Builder()
                .setKP(() -> kP)
                .setKI(() -> kI)
                .setKD(() -> kD)
                .setKF(() -> kf)
                .setTolerance(tolerance)
                .build();
        this.motor = motor;
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        OpModeCore.getTelemetry().addData("Launcher Result", controller::result);
    }

    public static void setTargetVelocity(double targetVelocity) {
        TARGET_VELOCITY = targetVelocity;
    }

    public static void setMinVelocity(double minVelocity) {
        MIN_VELOCITY = minVelocity;
    }

    public void incrementTargetVelocity(double increment) {
        double MIN_VELOCITY_DIFFERENCE = -100;

        setTargetVelocity(TARGET_VELOCITY + increment);
        setMinVelocity(TARGET_VELOCITY + MIN_VELOCITY_DIFFERENCE);
    }

    public void hardStop() {
        motor.setPower(0);
    }

    public void jamPrevention() {
        double JAM_PREVENT_VELOCITY = 175;

        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setPower(controller.calcWithVelocity(JAM_PREVENT_VELOCITY, getVelocity()));
    }

    public void launch() {
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setPower(controller.calcWithVelocity(TARGET_VELOCITY, getVelocity()));
    }

    public double getVelocity() {
        float ticksPerDegree = (537.7f / 360f);

        return -motor.getVelocity() / ticksPerDegree;
    }

    public boolean atSpeed() {
        return (getVelocity() >= MIN_VELOCITY);
    }
}
