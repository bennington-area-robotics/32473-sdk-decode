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
                break;
            }
            case "UNJAMMING": {
                state = "BEGIN_LAUNCH";
                break;
            }
            case "LAUNCHING": {
                timer.reset();
                break;
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
                break;
            }
            case "UNJAMMING": {
                launcher.jamPrevention();
                break;
            }
            case "BEGIN_LAUNCH": {
                launcher.launch();
                state = "SPINNING_UP";
                break;
            }
            case "SPINNING_UP": {
                if (launcher.atSpeed()) {
                    state = "LAUNCHING";
                    timer.reset();
                }
                break;
            }
            case "LAUNCHING": {
                feeders.feed(false);
                if (timer.milliseconds() >= 750) {
                    state = "UNJAMMING";
                    feeders.stop();
                }
                break;
            }
        }
    }
}
