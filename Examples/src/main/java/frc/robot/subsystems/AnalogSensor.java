// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AnalogSensor extends SubsystemBase {
  /** Creates a new ExampleSubsystem. */
  AnalogInput sensor;

  public AnalogSensor() {
    sensor = new AnalogInput(3);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Voltage", sensor.getVoltage());

  }
 
  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
