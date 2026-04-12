package org.firstinspires.ftc.teamcode.core.implementations;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.components.DriveBase;
import org.firstinspires.ftc.teamcode.components.Feeders;
import org.firstinspires.ftc.teamcode.components.Intake;
import org.firstinspires.ftc.teamcode.components.LaunchControl;
import org.firstinspires.ftc.teamcode.components.Launcher;
import org.firstinspires.ftc.teamcode.core.SmartGamepad;
import org.firstinspires.ftc.teamcode.core.TeleOpCore;
import org.firstinspires.ftc.teamcode.hardware.Hardware;

@TeleOp(name = "Major Tom TeleOp")
public class MajorTomTeleOp extends TeleOpCore {
    protected static DriveBase driveBase;
    protected static Feeders feeders;
    protected static Intake intake;
    protected static Launcher launcher;
    protected static LaunchControl launchControl;

    @Override
    protected void initialize() {
        super.initialize();

        try {
            driveBase = new DriveBase();
        } catch (Exception e) {
            prettyTelem.error("Drive base failed to initialize, skipping: " + e.getMessage());
        }

        try {
            feeders = new Feeders(
                hardwareMap.get(CRServo.class, "leftFeeder"),
                hardwareMap.get(CRServo.class, "rightFeeder")
            );
        } catch (Exception e) {
            prettyTelem.error("Feeder servos failed to initialize, skipping: " + e.getMessage());
        }

        try {
            intake = new Intake(
                Hardware.getMotor("intakeMotor")
            );
        } catch (Exception e) {
            prettyTelem.error("Intake motor failed to initialize, skipping: " + e.getMessage());
        }

        try {
            launcher = new Launcher(
                Hardware.getMotor("launchMotor")
            );
        } catch (Exception e) {
            prettyTelem.error("Launch motor failed to initialize, skipping: " + e.getMessage());
        }

        try {
            launchControl = new LaunchControl(
                feeders,
                launcher
            );
        } catch (Exception e) {
            prettyTelem.error("Launch controller failed to initialize, skipping: " + e.getMessage());
        }
    }

    @Override
    protected void checkGamepads(SmartGamepad gamepad1, SmartGamepad gamepad2) {
        if (driveBase != null) {
            if (gamepad1.rightBumper) {
                driveBase.setPowerFactor(0.25);
            } else if (gamepad1.leftBumper) {
                driveBase.setPowerFactor(0.85);
            } else {
                driveBase.setPowerFactor(0.5);
            }

            driveBase.moveUsingInput(gamepad1.leftStickX, gamepad1.leftStickY, gamepad1.rightStickX);
        }

        if (feeders != null) {
            if (gamepad1.dpadUp) {
                feeders.feed(false);
            } else if (gamepad1.dpadDown) {
                feeders.feed(true);
            } else {
                feeders.stop();
            }
        }

        if (intake != null) {
            if (gamepad1.back) {
                intake.collect();
            }
            if (gamepad1.start) {
                intake.stop();
            }
        }

        if (launcher != null) {
            if (gamepad1.a) {
                launcher.hardStop();
            }
            if (gamepad1.y) {
                launcher.launch();
            }
            if (gamepad1.x) {
                launcher.jamPrevention();
            }
            if (gamepad1.leftTrigger > 0.9) {
                launcher.incrementTargetVelocity(10);
            }
            if (gamepad1.rightTrigger > 0.9) {
                launcher.incrementTargetVelocity(-10);
            }
        }

        if (launchControl != null) {
            if (gamepad1.b) {
                launchControl.launch();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();
    }
}
