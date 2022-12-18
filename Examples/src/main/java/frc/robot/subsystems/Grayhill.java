// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Grayhill extends SubsystemBase {

  private Encoder encoder;

  /** Creates a new ExampleSubsystem. */
  public Grayhill() {
    encoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);

    encoder.setDistancePerPulse(1. / 256.);
    encoder.setMaxPeriod(.1);
    encoder.setMinRate(10);
    encoder.setReverseDirection(false);
    encoder.setSamplesToAverage(5);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("Gray Hill Distance", encoder.getDistance());
    SmartDashboard.putNumber("Gray Hill Velocity", encoder.getRate());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
