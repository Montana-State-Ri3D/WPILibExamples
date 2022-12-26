package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.BlinkyCommand;
import frc.robot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  @SuppressWarnings({"unused"})
  private final XboxController driveController = new XboxController(Constants.DRIVE_CONTROLLER_PORT); 
  @SuppressWarnings({"unused"})
  private final XboxController operatorController = new XboxController(Constants.OPERATOR_CONTROLLER_PORT);
  @SuppressWarnings({"unused"})
  private final XboxController testController = new XboxController(Constants.TEST_CONTROLLER_PORT);

  private LEDSubsystem ledSubsystem;

  private BlinkyCommand blinkCommand;

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */
  public RobotContainer() {

    createSubsystems();
    createCommands();
    configureButtonBindings();
  }

  /**
   * Create all of our robot's subsystem objects here.
   */
  private void createSubsystems() {
    ledSubsystem = new LEDSubsystem();
  }

  /**
   * Create all of our robot's command objects here.
   */
  private void createCommands() {
    blinkCommand = new BlinkyCommand(ledSubsystem, 0);
    ledSubsystem.setDefaultCommand(blinkCommand);
  }

  private void configureButtonBindings() {
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return null;
  }
}
