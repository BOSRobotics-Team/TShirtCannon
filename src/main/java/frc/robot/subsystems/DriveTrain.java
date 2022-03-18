package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** */
public class DriveTrain extends SubsystemBase {
  public enum DriveMode {
    ARCADE,
    TANK,
    CURVATURE
  }

  private WPI_TalonFX leftMaster;
  private WPI_TalonFX rightMaster;
  private DifferentialDrive differentialDrive;
  private WPI_TalonFX leftFollower;
  private WPI_TalonFX rightFollower;

  private DriveMode m_DriveMode = DriveMode.ARCADE;
  private boolean m_UseSquares = true;
  private boolean m_UseDriveScaling = false;
  private double m_DriveScaling = 1.0;
  private boolean m_QuickTurn = false;

  private double _lastLSmoothing = 0.0;
  private double _lastRSmoothing = 0.0;

  /** */
  public DriveTrain() {

    leftMaster = new WPI_TalonFX(0);

    rightMaster = new WPI_TalonFX(1);

    differentialDrive = new DifferentialDrive(leftMaster, rightMaster);
    addChild("Differential Drive", differentialDrive);
    differentialDrive.setSafetyEnabled(true);
    differentialDrive.setExpiration(0.1);
    differentialDrive.setMaxOutput(1.0);

    leftFollower = new WPI_TalonFX(3);

    rightFollower = new WPI_TalonFX(4);

    leftFollower.configFactoryDefault();
    leftFollower.follow(leftMaster);
    leftFollower.setInverted(InvertType.FollowMaster);

    rightFollower.configFactoryDefault();
    rightFollower.follow(rightMaster);
    rightFollower.setInverted(InvertType.FollowMaster);

    differentialDrive.setDeadband(0.02);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run

  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run when in simulation

  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  public void setPercentVoltage(double leftPct, double rightPct) {
    leftMaster.set(ControlMode.PercentOutput, leftPct);
    rightMaster.set(ControlMode.PercentOutput, rightPct);
  }
  /** Completely stop the robot by setting the voltage to each side to be 0. */
  public void fullStop() {
    setPercentVoltage(0, 0);
    _lastLSmoothing = _lastRSmoothing = 0.0;
  }
  /** Disable the motors. */
  public void disable() {
    leftMaster.disable();
    rightMaster.disable();
  }

  public void enableDriveTrain(boolean enable) {
    differentialDrive.setSafetyEnabled(enable);
    if (enable) {
      setPercentVoltage(0, 0);
    } else {
      disable();
    }
  }

  public void enableBrakes(boolean enabled) {
    leftMaster.setNeutralMode(enabled ? NeutralMode.Brake : NeutralMode.Coast);
    rightMaster.setNeutralMode(enabled ? NeutralMode.Brake : NeutralMode.Coast);
  }

  public void setMaxOutput(double maxOutput) {
    differentialDrive.setMaxOutput(maxOutput);
  }

  public void driveArcade(double speed, double rotation, boolean useSquares) {
    differentialDrive.arcadeDrive(speed, rotation, useSquares);
  }

  public void driveTank(double leftSpeed, double rightSpeed) {
    differentialDrive.tankDrive(leftSpeed, rightSpeed);
  }

  public void driveCurvature(double speed, double rotation, boolean quickTurn) {
    differentialDrive.curvatureDrive(speed, rotation, quickTurn);
  }

  public void tankDriveVolts(double leftVolts, double rightVolts) {
    leftMaster.setVoltage(leftVolts);
    rightMaster.setVoltage(rightVolts);
    differentialDrive.feed();
  }

  public void drive(XboxController ctrl) {
    if (m_DriveMode == DriveMode.ARCADE) {
      this.setOutput(-ctrl.getLeftY(), ctrl.getRightX());
    } else if (m_DriveMode == DriveMode.TANK) {
      this.setOutput(-ctrl.getLeftY(), -ctrl.getRightY());
    } else if (m_DriveMode == DriveMode.CURVATURE) {
      this.setOutput(-ctrl.getLeftY(), ctrl.getRightX());
    }
  }

  public void setOutput(double left, double right) {
    double newleft = (_lastLSmoothing + left) / 2.0;
    double newRight = (_lastRSmoothing + right) / 2.0;
    _lastLSmoothing = left;
    _lastRSmoothing = right;

    if (m_DriveMode == DriveMode.ARCADE) {
      this.driveArcade(newleft, newRight, m_UseSquares);
    } else if (m_DriveMode == DriveMode.TANK) {
      this.driveTank(newleft, newRight);
    } else if (m_DriveMode == DriveMode.CURVATURE) {
      this.driveCurvature(newleft, newRight, m_QuickTurn);
    }
  }

  public DriveMode getDriveMode() {
    return m_DriveMode;
  }

  public void setDriveMode(DriveMode mode) {
    m_DriveMode = mode;
    SmartDashboard.putString("DriveTrainMode", m_DriveMode.toString());
  }

  public boolean getUseSquares() {
    return m_UseSquares;
  }

  public void setUseSquares(boolean use) {
    m_UseSquares = use;
    SmartDashboard.putBoolean("UseSquares", m_UseSquares);
  }

  public boolean getUseDriveScaling() {
    return m_UseDriveScaling;
  }

  public void setUseDriveScaling(boolean use) {
    m_UseDriveScaling = use;
    this.setMaxOutput(m_UseDriveScaling ? m_DriveScaling : 1.0);
    SmartDashboard.putBoolean("UseDriveScaling", m_UseDriveScaling);
  }

  public double getDriveScaling() {
    return m_DriveScaling;
  }

  public void setDriveScaling(double scaling) {
    m_DriveScaling = Math.max(Math.min(scaling, 1.0), 0.1);
    this.setMaxOutput(m_UseDriveScaling ? m_DriveScaling : 1.0);
    SmartDashboard.putNumber("DriveScaling", m_DriveScaling);
  }

  public boolean getQuickTurn() {
    return m_QuickTurn;
  }

  public void setQuickTurn(boolean turn) {
    m_QuickTurn = turn;
    SmartDashboard.putBoolean("UseQuickTurn", m_QuickTurn);
  }

  public void toggleDriveMode() {
    switch (m_DriveMode) {
      case ARCADE:
        setDriveMode(DriveMode.TANK);
        break;
      case TANK:
        setDriveMode(DriveMode.CURVATURE);
        break;
      case CURVATURE:
        setDriveMode(DriveMode.ARCADE);
        break;
      default:
        break;
    }
  }
}
