// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class TestSolenoid extends SubsystemBase {
  /** Creates a new Solenoid. */
  Solenoid exampleSolenoidPCM ;

  public TestSolenoid() {
    exampleSolenoidPCM  = new Solenoid(PneumaticsModuleType.CTREPCM, 0);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }

  public void toggle() {
    exampleSolenoidPCM.toggle();
  }

  public void forward() {
    exampleSolenoidPCM .set(true);
  }

  public void retract() {
    exampleSolenoidPCM .set(false);
  }
}
