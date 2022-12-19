
package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ElevatorSubsystem extends SubsystemBase {

    private final int MOTOR_CAN_ID = 1;

    private final double KP = 0.001000;
    private final double KI = 0.000000;
    private final double KD = 0.000000;
    private final double kIz = 0;
    private final double kFF = 0;
    private final double kMaxOutput = 1;
    private final double kMinOutput = -1;

    private final double kMilimetersPerRevoulution = 118;
    private final double kGearRatio = 1.0 / 5.0;

    private CANSparkMax motor;
    private SparkMaxPIDController motorPid;
    private RelativeEncoder encoder;

    private double currentTarget = 0;

    // Shuffleboard constants
    private final int kConstantsRow = 1;
    private final int kCurrentRow = 2;

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
        motorPid.setP(KP);
        motorPid.setI(KI);
        motorPid.setD(KD);
        motorPid.setIZone(kIz);
        motorPid.setFF(kFF);
        motorPid.setOutputRange(kMinOutput, kMaxOutput);
    }

    @Override
    public void periodic() {
        motorPid.setReference(currentTarget, CANSparkMax.ControlType.kPosition);
    }

    public void setTarget(double inTarget) {
        currentTarget = inTarget;
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
    }

    // ---------------------------------------------------------------------------
    // Telemetry
    // ---------------------------------------------------------------------------

    private void initTelemetry() {
        ShuffleboardTab tab = Shuffleboard.getTab("Shooter Tuner");

        // Shooter data values
        currentSpeedEntry = tab.add("Current Speed", 0)
                .withPosition(0, kShooterRow)
                .withSize(1, 1)
                .getEntry();

        targetSpeedEntry = tab.add("Target Speed", 0)
                .withPosition(1, kShooterRow)
                .withSize(1, 1)
                .getEntry();

        shooterEncoderEntry = tab.add("Shooter Encoder", 0)
                .withPosition(2, kShooterRow)
                .withSize(1, 1)
                .getEntry();

        // Hood data values
        currentAngleEntry = tab.add("Current Angle", 0)
                .withPosition(0, kHoodRow)
                .withSize(1, 1)
                .getEntry();

        targetAngleEntry = tab.add("Target Angle", 0)
                .withPosition(1, kHoodRow)
                .withSize(1, 1)
                .getEntry();

        hoodEncoderEntry = tab.add("Hood Encoder", 0)
                .withPosition(2, kHoodRow)
                .withSize(1, 1)
                .getEntry();

        hoodLimitSwitchEntry = tab.add("Hood Limit", false)
                .withPosition(3, kHoodRow)
                .withSize(1, 1)
                .getEntry();

        // Constant value entries
        kPEntry = tab.add("kP", 0)
                .withPosition(0, kConstantsRow)
                .withSize(1, 1)
                .getEntry();

        kIEntry = tab.add("kI", 0)
                .withPosition(1, kConstantsRow)
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
        currentSpeedEntry.setNumber(currentSpeed);
        targetSpeedEntry.setNumber(targetSpeed);
        shooterEncoderEntry.setNumber(shooterEncoder.getPosition());

        currentAngleEntry.setNumber(currentAngle);
        targetAngleEntry.setNumber(targetAngle);
        hoodEncoderEntry.setNumber(hoodEncoder.getPosition());
        hoodLimitSwitchEntry.forceSetBoolean(hoodLimit.get());

        // Update the contant values in the network table even though they never change.
        kPEntry.setDouble(kShooterP);
        kIEntry.setDouble(kShooterI);
        kDEntry.setDouble(kShooterD);
        kIzEntry.setDouble(kShooterIz);
        kFFEntry.setDouble(kShooterFF);
        kMaxOutputEntry.setDouble(kShooterMaxOutput);
        kMinOutputEntry.setDouble(kShooterMinOutput);
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
