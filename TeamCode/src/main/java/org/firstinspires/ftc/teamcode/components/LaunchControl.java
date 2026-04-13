package org.firstinspires.ftc.teamcode.components;

import com.qualcomm.robotcore.util.ElapsedTime;

public class LaunchControl {

    private final Feeders feeders;
    private final Launcher launcher;

    private String state;

    private final ElapsedTime timer = new ElapsedTime();

    public LaunchControl(
        Feeders feeders,
        Launcher launcher
    ) {
        this.feeders = feeders;
        this.launcher = launcher;

        this.state = "UNJAMMING";
    }

    public void launch() {
        switch (state) {
            case "RESTING": {
                state = "UNJAMMING";
            }
            case "UNJAMMING": {
                state = "BEGIN_LAUNCH";
            }
            case "LAUNCHING": {
                timer.reset();
            }
        }
    }

    public void enterRest() {
        state = "RESTING";
    }

    public void tick() {
        switch (state) {
            case "RESTING": {
                launcher.hardStop();
                feeders.stop();
            }
            case "UNJAMMING": {
                launcher.jamPrevention();
            }
            case "BEGIN_LAUNCH": {
                launcher.launch();
                state = "SPINNING_UP";
            }
            case "SPINNING_UP": {
                if (launcher.atSpeed()) {
                    state = "LAUNCHING";
                    timer.reset();
                }
            }
            case "LAUNCHING": {
                feeders.feed(false);
                if (timer.milliseconds() >= 750) {
                    state = "UNJAMMING";
                    feeders.stop();
                }
            }
        }
    }
}
