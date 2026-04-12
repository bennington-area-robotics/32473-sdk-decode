package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.hardware.SmartMotor;

public class Launcher {

    private final ElapsedTime timer = new ElapsedTime();

    private static double TARGET_VELOCITY = 1200;

    private final SmartMotor motor;

    public Launcher(SmartMotor motor) {
        this.motor = motor;
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(300, 0, 5, 15));
    }

    public static double getTargetVelocity() {
        return TARGET_VELOCITY;
    }

    public static void setTargetVelocity(double targetVelocity) {
        TARGET_VELOCITY = targetVelocity;
    }

    public void incrementTargetVelocity(double increment) {
        setTargetVelocity(getTargetVelocity() + increment);
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
}
