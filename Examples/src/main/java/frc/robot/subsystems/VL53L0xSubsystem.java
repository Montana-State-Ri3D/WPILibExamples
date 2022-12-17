/*
* Based on info from the Rev GitHub here https://github.com/REVrobotics/2m-Distance-Sensor
*/


package frc.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.*;
import com.revrobotics.Rev2mDistanceSensor.Port;

public class VL53L0xSubsystem extends SubsystemBase {

    private Rev2mDistanceSensor tof; 

  /** Creates a new ExampleSubsystem. */
  public VL53L0xSubsystem() {

    tof = new Rev2mDistanceSensor(Port.kOnboard);
    tof.setRangeProfile(Rev2mDistanceSensor.RangeProfile.kHighAccuracy);
    tof.setAutomaticMode(true);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

    if(tof.isRangeValid()) {
        SmartDashboard.putNumber("Range Onboard", tof.getRange());
        SmartDashboard.putNumber("Timestamp Onboard", tof.getTimestamp());
      }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
