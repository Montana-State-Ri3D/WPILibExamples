// Example command that runs forever (doing nothing) unless interrupted.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj.Timer;

import frc.robot.subsystems.LEDSubsystem;

/** An example command that uses an example subsystem. */
public class BlinkyCommand extends CommandBase {

  private final LEDSubsystem ledSubsystem;
  private final int index;
  private final Timer timer = new Timer();;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public BlinkyCommand(LEDSubsystem subsystem, int index) {
    ledSubsystem = subsystem;
    this.index = index;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {

    if (timer.advanceIfElapsed(1.0))
      ledSubsystem.set( index, !ledSubsystem.get(index));

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}