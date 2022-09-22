package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  private static RobotContainer m_robotContainer = new RobotContainer();

  // The robot's subsystems
  public final Cannon m_cannon = new Cannon();
  public final DriveTrain m_driveTrain = new DriveTrain();

  // Joysticks
  private final XboxController xboxController = new XboxController(0);

  // Driver Controller
  public final JoystickButton a_Button_Driver =
      new JoystickButton(xboxController, XboxController.Button.kA.value);
  public final JoystickButton b_Button_Driver =
      new JoystickButton(xboxController, XboxController.Button.kB.value);
  public final JoystickButton x_Button_Driver =
      new JoystickButton(xboxController, XboxController.Button.kX.value);
  public final JoystickButton y_Button_Driver =
      new JoystickButton(xboxController, XboxController.Button.kY.value);
  public final JoystickButton left_Bumper_Driver =
      new JoystickButton(xboxController, XboxController.Button.kLeftBumper.value);
  public final JoystickButton right_Bumper_Driver =
      new JoystickButton(xboxController, XboxController.Button.kRightBumper.value);
  public final JoystickButton back_Button_Driver =
      new JoystickButton(xboxController, XboxController.Button.kBack.value);
  public final JoystickButton start_Button_Driver =
      new JoystickButton(xboxController, XboxController.Button.kStart.value);
  public final JoystickButton left_Stick_Driver =
      new JoystickButton(xboxController, XboxController.Button.kLeftStick.value);
  public final JoystickButton right_Stick_Driver =
      new JoystickButton(xboxController, XboxController.Button.kRightStick.value);

  // A chooser for autonomous commands
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  private RobotContainer() {
    // Smartdashboard Subsystems

    // SmartDashboard Buttons
    SmartDashboard.putData("Autonomous Command", new AutonomousCommand());
    SmartDashboard.putData("ArmCannon", new ArmCannon(m_cannon));
    SmartDashboard.putData("DisarmCannon", new DisarmCannon(m_cannon));
    SmartDashboard.putData("ShootCannon", new ShootCannon(m_cannon));
    SmartDashboard.putData("DriveCommand", new DriveCommand(m_driveTrain));
    SmartDashboard.putData("ToggleDriveScaling", new ToggleDriveScaling(m_driveTrain));
    SmartDashboard.putData("IncrementDriveScaling", new IncrementDriveScaling(m_driveTrain));
    SmartDashboard.putData("DecrementDriveScaling", new DecrementDriveScaling(m_driveTrain));
    SmartDashboard.putData("RotateMagazine", new RotateMagazine(m_cannon));
    SmartDashboard.putData("ControlCannon", new ControlCannon(m_cannon));
    SmartDashboard.putData("ActivateLights", new ActivateLights(m_cannon));
    SmartDashboard.putData("LightsArmed", new LightsArmed(m_cannon));
    SmartDashboard.putData("LightsOff", new LightsOff(m_cannon));

    // Configure the button bindings
    configureButtonBindings();

    // Configure default commands
    m_cannon.setDefaultCommand(new ControlCannon(m_cannon));
    m_driveTrain.setDefaultCommand(new DriveCommand(m_driveTrain));

    // Configure autonomous sendable chooser
    m_chooser.setDefaultOption("Autonomous Command", new AutonomousCommand());

    SmartDashboard.putData("Auto Mode", m_chooser);
  }

  public static RobotContainer getInstance() {
    return m_robotContainer;
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    a_Button_Driver.whenPressed(new ArmCannon(m_cannon), true);
    SmartDashboard.putData("Arm Cannon", new ArmCannon(m_cannon));

    a_Button_Driver.whenReleased(new DisarmCannon(m_cannon), true);
    SmartDashboard.putData("Disarm Cannon", new DisarmCannon(m_cannon));

    left_Bumper_Driver.whenPressed(new ShootCannon(m_cannon), true);
    SmartDashboard.putData("Shoot Cannon", new ShootCannon(m_cannon));

    left_Stick_Driver.whenPressed(new ToggleDriveScaling(m_driveTrain), true);
    SmartDashboard.putData("Toggle DriveScaling", new ToggleDriveScaling(m_driveTrain));
  }

  public XboxController getXboxController() {
    return xboxController;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // The selected command will be run in autonomous
    return m_chooser.getSelected();
  }
}
