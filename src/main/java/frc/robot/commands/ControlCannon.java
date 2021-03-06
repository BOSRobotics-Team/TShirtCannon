package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Cannon;

public class ControlCannon extends CommandBase {

  private final Cannon m_cannon;

  public XboxController m_controller;

  public ControlCannon(Cannon subsystem) {

    m_cannon = subsystem;
    addRequirements(m_cannon);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    System.out.println("ControlCannon-init");
    m_controller = RobotContainer.getInstance().getXboxController();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    int pov = m_controller.getPOV();
    if (pov == 0) {
      m_cannon.raiseCannon(0.4);
      System.out.println("raiseCannon");
    } else if (pov == 90) {
      m_cannon.rotateRight();
      System.out.println("rotateCannon right");
    } else if (pov == 180) {
      m_cannon.raiseCannon(-0.4);
      System.out.println("lowerCannon");
    } else if (pov == 270) {
      m_cannon.rotateLeft();
      System.out.println("rotateCannon left");
    } else {
      m_cannon.raiseCannon(0);
    }
    if (m_controller.getLeftTriggerAxis() > 0.5) {
      m_cannon.fire();
      System.out.println("fireCannon");
    }
  }

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
