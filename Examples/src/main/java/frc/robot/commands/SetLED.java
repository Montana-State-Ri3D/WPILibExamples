// Example command that runs forever (doing nothing) unless interrupted.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.LEDSubsystem;

/** An example command that uses an example subsystem. */
public class SetLED extends CommandBase {

  private final LEDSubsystem ledSubsystem;
  private final int index;
  private final boolean value;
  private final boolean toggle;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public SetLED(LEDSubsystem subsystem, int index, boolean value) {
    ledSubsystem = subsystem;
    this.index = index;
    this.value = value;
    toggle = false;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  public SetLED(LEDSubsystem subsystem, int index) {
    ledSubsystem = subsystem;
    this.index = index;
    toggle = true;
    this.value = ledSubsystem.get(index);
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(toggle)
    {
      ledSubsystem.set(index, !ledSubsystem.get(index));
    }
    else
    {
      ledSubsystem.set(index, value);
    }  
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {}

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}