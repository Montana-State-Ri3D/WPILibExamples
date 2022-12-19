package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.SetElevatorPositionCommand;
import frc.robot.subsystems.ElevatorSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

public class RobotContainer {

  @SuppressWarnings({"unused"})
  private final XboxController driveController = new XboxController(Constants.DRIVE_CONTROLLER_PORT); 
  @SuppressWarnings({"unused"})
  private final XboxController operatorController = new XboxController(Constants.OPERATOR_CONTROLLER_PORT);
  @SuppressWarnings({"unused"})
  private final XboxController testController = new XboxController(Constants.TEST_CONTROLLER_PORT);

  private ElevatorSubsystem elevatorSubsystem;

  private SetElevatorPositionCommand elevatorPosCommand;

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
    elevatorSubsystem = new ElevatorSubsystem();
  }

  /**
   * Create all of our robot's command objects here.
   */
  private void createCommands() {
    elevatorPosCommand = new SetElevatorPositionCommand(elevatorSubsystem);

  }

  private void configureButtonBindings() {
    new JoystickButton(operatorController, Button.kA.value).whenPressed(elevatorPosCommand);
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
