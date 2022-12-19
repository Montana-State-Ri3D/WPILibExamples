
package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.MathUtils;

public class ElevatorSubsystem extends SubsystemBase {

    private final int MOTOR_CAN_ID = 1;
    private final int kMinPosition = 0;
    private final int kMaxPosition = 500;

    // Initial PID coefficients
    private final double kP = 0.001000;
    private final double kI = 0.000000;
    private final double kD = 0.000000;
    private final double kIz = 0;
    private final double kFF = 0;
    private final double kMaxOutput = 1;
    private final double kMinOutput = -1;

    // Live PID coefficients
    private double currentP = kP;
    private double currentI = kI;
    private double currentD = kD;
    private double currentIz = kIz;
    private double currentFF = kFF;
    private double currentMaxOutput = kMaxOutput;
    private double currentMinOutput = kMinOutput;

    private final double kMilimetersPerRevoulution = 118;
    private final double kGearRatio = 1.0 / 5.0;

    private CANSparkMax motor;
    private SparkMaxPIDController motorPid;
    private RelativeEncoder encoder;

    private double currentPosition = 0;
    private double currentTarget = 0;

    // Shuffleboard constants
    private final int kStateRow = 0;
    private final int kConstantsRow = 1;
    private final int kCurrentRow = 2;

     // Network table entries for current state values
     private NetworkTableEntry curPosEntry;
     private NetworkTableEntry curTargetEntry;

    // Network table entries to display default PID coefficients for reference
    private NetworkTableEntry kPEntry;
    private NetworkTableEntry kIEntry;
    private NetworkTableEntry kDEntry;
    private NetworkTableEntry kIzEntry;
    private NetworkTableEntry kFFEntry;
    private NetworkTableEntry kMaxOutputEntry;
    private NetworkTableEntry kMinOutputEntry;

    // Network table entries for active PID coefficient
    private NetworkTableEntry curPEntry;
    private NetworkTableEntry curIEntry;
    private NetworkTableEntry curDEntry;
    private NetworkTableEntry curIzEntry;
    private NetworkTableEntry curFFEntry;
    private NetworkTableEntry curMaxOutputEntry;
    private NetworkTableEntry curMinOutputEntry;

    public ElevatorSubsystem() {

        motor = new CANSparkMax(MOTOR_CAN_ID, MotorType.kBrushless);
        motor.restoreFactoryDefaults();

        motor.setIdleMode(IdleMode.kBrake);
        motor.setInverted(true);

        encoder = motor.getEncoder();
        encoder.setPosition(0);
        encoder.setPositionConversionFactor(kMilimetersPerRevoulution * kGearRatio);

        motor.setSmartCurrentLimit(4);
        motorPid = motor.getPIDController();
        motorPid.setP(kP);
        motorPid.setI(kI);
        motorPid.setD(kD);
        motorPid.setIZone(kIz);
        motorPid.setFF(kFF);
        motorPid.setOutputRange(kMinOutput, kMaxOutput);

        initTelemetry();
    }

    @Override
    public void periodic() {
        motorPid.setReference(currentTarget, CANSparkMax.ControlType.kPosition);

        updateTelemetry();
    }

    public void setTargetAbsolute(double inTarget) {
        currentTarget = MathUtils.ClipToRange(inTarget, kMinPosition, kMaxPosition);
    }

    public void setTargetRelative(double inTarget) {
        setTargetAbsolute(currentTarget + inTarget);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    /**
     * Update the cached coefficients values from the network tables and write them
     * out to the motor controller.
     */
    public void updatePIDCoefficients() {
        currentP = curPEntry.getDouble(kP);
        currentI = curIEntry.getDouble(kI);
        currentD = curDEntry.getDouble(kD);
        currentIz = curIzEntry.getDouble(kIz);
        currentFF = curFFEntry.getDouble(kFF);
        currentMaxOutput = curMaxOutputEntry.getDouble(kMaxOutput);
        currentMinOutput = curMinOutputEntry.getDouble(kMinOutput);

        updatePIDController();
    }

    /**
     * Update the PID coefficents in the motor controller from the cached values
     */
    public void updatePIDController() {
        motorPid.setP(currentP);
        motorPid.setI(currentI);
        motorPid.setD(currentD);
        motorPid.setIZone(currentIz);
        motorPid.setFF(currentFF);
        motorPid.setOutputRange(currentMinOutput, currentMaxOutput);

        updateCurrentValues();
    }

    public void dumpPIDCoefficients() {

        System.out.println("--------------------------------------------------------");
        System.out.printf("private static final double kP = %f;\n", currentP);
        System.out.printf("private static final double kI = %f;\n", currentI);
        System.out.printf("private static final double kD = %f;\n", currentD);
        System.out.printf("private static final double kIz = %f;\n", currentIz);
        System.out.printf("private static final double kFF = %f;\n", currentFF);
        System.out.printf("private static final double kMaxOutput = %f;\n", currentMaxOutput);
        System.out.printf("private static final double kMinOutput = %f;\n", currentMinOutput);
        System.out.println("--------------------------------------------------------");
    }

    // ---------------------------------------------------------------------------
    // Telemetry
    // ---------------------------------------------------------------------------

    private void initTelemetry() {
        ShuffleboardTab tab = Shuffleboard.getTab("Shooter Tuner");

        // State value entries
        kPEntry = tab.add("Position", 0)
                .withPosition(0, kStateRow)
                .withSize(1, 1)
                .getEntry();

        kIEntry = tab.add("Target", 0)
                .withPosition(1, kStateRow)
                .withSize(1, 1)
                .getEntry();

        kDEntry = tab.add("kD", 0)
                .withPosition(2, kConstantsRow)
                .withSize(1, 1)
                .getEntry();

        kIzEntry = tab.add("kIz", 0)
                .withPosition(3, kConstantsRow)
                .withSize(1, 1)
                .getEntry();

        kFFEntry = tab.add("kF", 0)
                .withPosition(4, kConstantsRow)
                .withSize(1, 1)
                .getEntry();

        kMaxOutputEntry = tab.add("kMaxOutput", 0)
                .withPosition(5, kConstantsRow)
                .withSize(1, 1)
                .getEntry();

        kMinOutputEntry = tab.add("kMinOutput", 0)
                .withPosition(6, kConstantsRow)
                .withSize(1, 1)
                .getEntry();

        // Live value entries
        curPEntry = tab.add("Current P", 0)
                .withPosition(0, kCurrentRow)
                .withSize(1, 1)
                .getEntry();

        curIEntry = tab.add("Current I", 0)
                .withPosition(1, kCurrentRow)
                .withSize(1, 1)
                .getEntry();

        curDEntry = tab.add("Current D", 0)
                .withPosition(2, kCurrentRow)
                .withSize(1, 1)
                .getEntry();

        curIzEntry = tab.add("Current Iz", 0)
                .withPosition(3, kCurrentRow)
                .withSize(1, 1)
                .getEntry();

        curFFEntry = tab.add("Current FF", 0)
                .withPosition(4, kCurrentRow)
                .withSize(1, 1)
                .getEntry();

        curMaxOutputEntry = tab.add("Current MaxOutput", 0)
                .withPosition(5, kCurrentRow)
                .withSize(1, 1)
                .getEntry();

        curMinOutputEntry = tab.add("Current MinOutput", 0)
                .withPosition(6, kCurrentRow)
                .withSize(1, 1)
                .getEntry();
    }

    private void updateTelemetry() {
        curPosEntry.setNumber(currentPosition);
        curTargetEntry.setNumber(currentTarget);

        // Update the contant values in the network table even though they never change.
        kPEntry.setDouble(kP);
        kIEntry.setDouble(kI);
        kDEntry.setDouble(kD);
        kIzEntry.setDouble(kIz);
        kFFEntry.setDouble(kFF);
        kMaxOutputEntry.setDouble(kMaxOutput);
        kMinOutputEntry.setDouble(kMinOutput);
    }

    private void updateCurrentValues() {
        curPEntry.setDouble(currentP);
        curIEntry.setDouble(currentI);
        curDEntry.setDouble(currentD);
        curIzEntry.setDouble(currentIz);
        curFFEntry.setDouble(currentFF);
        curMaxOutputEntry.setDouble(currentMaxOutput);
        curMinOutputEntry.setDouble(currentMinOutput);
    }
}
