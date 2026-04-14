package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.SmartMotor;

public class Launcher {

    private final ElapsedTime timer = new ElapsedTime();

    private static double TARGET_VELOCITY = 1200;
    private static double MIN_VELOCITY = TARGET_VELOCITY - 100;

    private final SmartMotor motor;

    public Launcher(SmartMotor motor) {
        this.motor = motor;
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(
                10,
                0,
                5,
                13
        )); // TUNE THIS
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
        double JAM_PREVENT_VELOCITY = 300;

        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setVelocity(JAM_PREVENT_VELOCITY);
    }

    public void launch() {
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setVelocity(TARGET_VELOCITY);
    }

    public double getVelocity() {
        return motor.getVelocity();
    }

    public boolean atSpeed() {
        return (getVelocity() >= MIN_VELOCITY);
    }
}
