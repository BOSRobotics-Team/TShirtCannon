package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

/** */
public class DecrementDriveScaling extends CommandBase {

  private final DriveTrain m_driveTrain;

  public DecrementDriveScaling(DriveTrain subsystem) {

    m_driveTrain = subsystem;
    addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_driveTrain.setDriveScaling(Math.max(m_driveTrain.getDriveScaling() - 0.1, 0.1));
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
    return false;
  }

  @Override
  public boolean runsWhenDisabled() {

    return false;
  }
}
