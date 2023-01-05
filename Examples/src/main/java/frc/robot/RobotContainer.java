package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import frc.robot.commands.BlinkyCommand;
import frc.robot.commands.SetLED;
import frc.robot.subsystems.LEDSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;

public class RobotContainer {

  @SuppressWarnings({ "unused" })
  private final XboxController driveController = new XboxController(Constants.DRIVE_CONTROLLER_PORT);
  @SuppressWarnings({ "unused" })
  private final XboxController operatorController = new XboxController(Constants.OPERATOR_CONTROLLER_PORT);
  @SuppressWarnings({ "unused" })
  private final XboxController testController = new XboxController(Constants.TEST_CONTROLLER_PORT);

  private LEDSubsystem ledSubsystem0;
  private LEDSubsystem ledSubsystem1;

  private SequentialCommandGroup seqCommangGroup1;
  private SequentialCommandGroup seqCommangGroup2;
  private ParallelCommandGroup parrCommandGroup;

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
    ledSubsystem0 = new LEDSubsystem(0);
    ledSubsystem1 = new LEDSubsystem(1);
  }

  /**
   * Create all of our robot's command objects here.
   */
  private void createCommands() {

    seqCommangGroup1 = new SequentialCommandGroup();
    seqCommangGroup1.addCommands(new SetLED(ledSubsystem0, 3, true));
    seqCommangGroup1.addCommands(new WaitCommand(2.0));
    seqCommangGroup1.addCommands(new SetLED(ledSubsystem0, 3, false));

    seqCommangGroup2 = new SequentialCommandGroup();
    seqCommangGroup2.addCommands(new SetLED(ledSubsystem1, 3, true));
    seqCommangGroup2.addCommands(new WaitCommand(1.0));
    seqCommangGroup2.addCommands(new SetLED(ledSubsystem1, 3, false));

    parrCommandGroup = new ParallelCommandGroup();
    parrCommandGroup.addCommands(seqCommangGroup1, seqCommangGroup2);
  }

  private void configureButtonBindings() {

    new JoystickButton(driveController, Button.kA.value).whenPressed(parrCommandGroup);
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
