package org.firstinspires.ftc.teamcode.core.implementations;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.components.DriveBase;
import org.firstinspires.ftc.teamcode.core.SmartGamepad;
import org.firstinspires.ftc.teamcode.core.TeleOpCore;

@TeleOp(name = "Major Tom TeleOp")
public class MajorTomTeleOp extends TeleOpCore {
    protected static DriveBase driveBase;

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected void checkGamepads(SmartGamepad gamepad1, SmartGamepad gamepad2) {
        if (driveBase != null) {
            driveBase.moveUsingInput(gamepad1.leftStickX, gamepad1.leftStickY, gamepad1.rightStickX);
        }
    }

    @Override
    public void tick() {
        super.tick();
    }
}
