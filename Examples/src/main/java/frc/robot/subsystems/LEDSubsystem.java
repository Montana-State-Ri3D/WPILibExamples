// Dummy subsystem class

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class LEDSubsystem extends SubsystemBase {

    private final int ledCount = 4;
    private int rowIndex;

    boolean[] leds = new boolean[ledCount];

    NetworkTableEntry[] entries = new NetworkTableEntry[ledCount];

    public LEDSubsystem(){
        rowIndex = 0;
        initTelemetry();
    }

    public LEDSubsystem(int rowIndex) {
        this.rowIndex = rowIndex;
        initTelemetry();
    }

    @Override
    public void periodic() {
        updateTelemetry();
    }

    public void set(int index, boolean value) {
        leds[index] = value;
    }

    public boolean get(int index) {
        return leds[index];
    }

    private void initTelemetry() {
        ShuffleboardTab tab = Shuffleboard.getTab("LED");

        for (int n = 0; n < ledCount; n++) {
            entries[n] = tab.add(n + " , " + rowIndex, false)
                    .withPosition(n, rowIndex)
                    .withSize(1, 1)
                    .getEntry();
        }
    }

    private void updateTelemetry() {

        for (int n = 0; n < ledCount; n++) {
            entries[n].forceSetBoolean(leds[n]);
        }
    }
}