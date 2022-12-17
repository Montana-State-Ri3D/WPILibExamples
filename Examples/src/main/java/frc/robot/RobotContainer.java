package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.VL53L0xSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

public class RobotContainer {

  @SuppressWarnings({"unused"})
  private final XboxController driveController = new XboxController(Constants.DRIVE_CONTROLLER_PORT); 
  @SuppressWarnings({"unused"})
  private final XboxController operatorController = new XboxController(Constants.OPERATOR_CONTROLLER_PORT);
  @SuppressWarnings({"unused"})
  private final XboxController testController = new XboxController(Constants.TEST_CONTROLLER_PORT);

  private VL53L0xSubsystem tofSubsystem;

  //private final ExampleCommand autoCommand = new ExampleCommand(exampleSubsystem);

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

    tofSubsystem = new VL53L0xSubsystem();

  }

  /**
   * Create all of our robot's command objects here.
   */
  private void createCommands() {
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
