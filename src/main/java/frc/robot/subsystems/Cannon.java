package frc.robot.subsystems;

// import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Compressor;
// import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
// import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/** */
public class Cannon extends SubsystemBase {
  // private Solenoid lights;
  // private WPI_VictorSPX cannonElevator;
  // private WPI_VictorSPX magazineRotator;
  // private DigitalInput leftAlignment;
  // private DigitalInput rightAlignment;
  private Compressor firingMechanism;

  private Boolean _armed = false;
  private Boolean _rotateLeft = false;
  private Boolean _rotateRight = false;
  private Boolean _isNowRotating = false;
  // private long _blinkLights = -1;
  /** */
  public Cannon() {

    // lights = new Solenoid(0, PneumaticsModuleType.CTREPCM, 0);
    // addChild("Lights", lights);

    // cannonElevator = new WPI_VictorSPX(6);

    // magazineRotator = new WPI_VictorSPX(5);

    // leftAlignment = new DigitalInput(0);
    // addChild("LeftAlignment", leftAlignment);

    // rightAlignment = new DigitalInput(1);
    // addChild("RightAlignment", rightAlignment);

    firingMechanism = new Compressor(0, PneumaticsModuleType.CTREPCM);
    addChild("FiringMechanism", firingMechanism);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    // if (!_isNowRotating && (_rotateLeft || _rotateRight)) {
    //   _isNowRotating = !leftAlignment.get() && !rightAlignment.get();
    // }
    // if (_isNowRotating) {
    //   if (_rotateLeft && leftAlignment.get()) {
    //     magazineRotator.set(0.05);
    //   }
    //   if (_rotateRight && rightAlignment.get()) {
    //     magazineRotator.set(-0.05);
    //   }
    //   if (leftAlignment.get() && rightAlignment.get()) {
    //     magazineRotator.stopMotor();
    //     _isNowRotating = _rotateLeft = _rotateRight = false;
    //     if (_armed) {
    //       lightsBlink();
    //     } else {
    //       lightsOn();
    //     }
    //   } else {
    //     lightsOff();
    //   }
    // }
    // if (_blinkLights >= 0) {
    //   _blinkLights += 20;

    //   lights.set(_blinkLights >= 500);

    //   if (_blinkLights >= 1000) {
    //     _blinkLights = _blinkLights % 1000;
    //   }
    // }
    if (!_armed) {
      firingMechanism.disable();
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run when in simulation

  }

  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public void rotateLeft() {
    if (!_isNowRotating) {
      _rotateLeft = true;
      _rotateRight = false;
      // magazineRotator.set(0.2);
    }
  }

  public void rotateRight() {
    if (!_isNowRotating) {
      _rotateLeft = false;
      _rotateRight = true;
      // magazineRotator.set(-0.2);
    }
  }

  public void raiseCannon(double speed) {
    // cannonElevator.set(speed);
  }

  public void arm(boolean armed) {
    _armed = armed;
  }

  public void fire() {
    if (_armed && !_isNowRotating && !_rotateLeft && !_rotateRight) {
      firingMechanism.enableDigital();
      _armed = false;
    }
  }

  public void lightsOn() {
    // _blinkLights = -1;
    // lights.set(true);
  }

  public void lightsOff() {
    // _blinkLights = -1;
    // lights.set(false);
  }

  public void lightsBlink() {
    // _blinkLights = 0;
    // lights.set(true);
  }
}
