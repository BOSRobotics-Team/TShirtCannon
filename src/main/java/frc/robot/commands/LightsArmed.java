package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.RobotContainer;
import frc.robot.subsystems.LEDLights;
import frc.robot.subsystems.LEDLights.LEDColor;

/** */
public class LightsArmed extends InstantCommand {

  private final LEDLights m_lights;
  private final LEDColor m_color;

  public LightsArmed(RobotContainer container, LEDColor color) {
    m_lights = container.m_lights;
    m_color = color;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {}

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_lights.setOnboardLights(m_color);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}
}
