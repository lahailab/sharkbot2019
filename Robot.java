/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import sun.java2d.pipe.ValidatePipe;
import edu.wpi.first.wpilibj.PWMVictorSPX;
//import edu.wpi.first.hal.PWMJNI;
// import edu.wpi.first.wpilibj.RobotDrive; // deprecated
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.CameraServer; //
// mport javax.lang.model.util.ElementScanner6;
import edu.wpi.first.wpilibj.Compressor;
// import edu.wpi.first.wpilibj.Controller;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
// import edu.wpi.first.wpilibj.Joystick.ButtonType;
// import edu.wpi.first.wpilibj.SolenoidBase;
// import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Joystick;
// import edu.wpi.first.wpilibj.Timer; // limit switch
// import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
// import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.buttons.Button;
//import edu.wpi.first.wpilibj.DigitalInput;
// import com.revrobotics.CANError;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private final DifferentialDrive m_robotBase = new DifferentialDrive(new PWMVictorSPX(0), new PWMVictorSPX(1));
  public static Compressor compressor = new Compressor(0);
  public static DoubleSolenoid pistonOut = new DoubleSolenoid(0, 1);
  private final Joystick logController = new Joystick(0);
  private final VictorSP elevatorVictorSP = new VictorSP(2);
  //private final Spark ballGrabber = new Spark(3);
 

  //private final DigitalInput limitSwitchUpper = new DigitalInput(1);
  //private final DigitalInput limitSwitchLower = new DigitalInput(0);
  private CANSparkMax m_grabber = new CANSparkMax(1, MotorType.kBrushless);

  // limit switch
  // private final Timer limitswitchTimer = new Timer();
  // DigitalInput limitSwitch;

  Button D1 = new JoystickButton(logController, 1);
  Button D2 = new JoystickButton(logController, 2);
  Button D3 = new JoystickButton(logController, 3);
  Button D4 = new JoystickButton(logController, 4);
  Button D5 = new JoystickButton(logController, 5);
  Button D6 = new JoystickButton(logController, 6);
  Button D7 = new JoystickButton(logController, 7);
  Button D8 = new JoystickButton(logController, 8);
  Button D9 = new JoystickButton(logController, 9);
  Button D10 = new JoystickButton(logController, 10);

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    compressor.setClosedLoopControl (true);

    CameraServer.getInstance().startAutomaticCapture();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // autoSelected = SmartDashboard.getString("Auto Selector",
    // defaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }

    double xSpeed = logController.getY() * .10;
    //.78//
      double zRotation = logController.getX() * .10;
      //.69//
      m_robotBase.arcadeDrive(xSpeed, zRotation);

    //m_robotBase.arcadeDrive(
    //    logController.getX() * (-logController.getThrottle() * 1),
    //    logController.getY() * (-logController.getThrottle() * 1)
    //);

      elevatorVictorSP.set(logController.getRawAxis(5) * .25);//50//

      //m_grabber.set(logController.getRawAxis(4));


      if (logController.getRawButton(1)) {
        pistonOut.set(Value.kForward.kForward);
      } 

      if (logController.getRawButton(2)) {
        pistonOut.set(Value.kForward.kReverse);

      }

      // periodically read voltage, temperature, and applied output and publish to SmartDashboard
      SmartDashboard.putNumber("Voltage", m_grabber.getBusVoltage());
      SmartDashboard.putNumber("Temperature", m_grabber.getMotorTemperature());
      SmartDashboard.putNumber("Output", m_grabber.getAppliedOutput());


      
      if (logController.getRawButton(6)) {
        m_grabber.set(1);
      }

      if (logController.getRawButton(4)) {
        m_grabber.set(0);
      }

      if (logController.getRawButton(5)) {
        m_grabber.set(-1);
      }

      //public Spark ballGrabberSpark()
      //m_grabber.enableDeadbandElimination(true);
      //ballGrabber.set(logController.getRawAxis(4));


      //if (logController.getRawButton(4)) {
       //ballGrabber.set(-0.7);
      //}

      // limit switch
      //while (limitSwitch.get()) {
      //  Timer.delay(10);
      //  if (limitSwitchLower.get() && speed < 0) {
      //   speed = 0;
      // }

      // if or while
      // 1 open, 0 closed
      //while (limitSwitchUpper.get()) {
      //  pistonOut.set(Value.kOff);
      //}
      //while (limitSwitchLower.get()) {
      //  pistonOut.set(Value.kOff);
      //}

      // {Joystick * ((throttle+1) / 2) }
  }

  
  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
      double xSpeed = logController.getY() * .78; //.78 is regular speed (.39 half speed)
      double zRotation = logController.getX() * .5; //.65
      m_robotBase.arcadeDrive(xSpeed, zRotation);

      elevatorVictorSP.set(logController.getRawAxis(5));

      //m_grabber.set(logController.getRawAxis(4));


      if (logController.getRawButton(1)) {
        pistonOut.set(Value.kForward.kForward);
      } 

      if (logController.getRawButton(2)) {
        pistonOut.set(Value.kForward.kReverse);

      }

      // periodically read voltage, temperature, and applied output and publish to SmartDashboard
      SmartDashboard.putNumber("Voltage", m_grabber.getBusVoltage());
      SmartDashboard.putNumber("Temperature", m_grabber.getMotorTemperature());
      SmartDashboard.putNumber("Output", m_grabber.getAppliedOutput());


      
      if (logController.getRawButton(6)) {
        m_grabber.set(1);
      }

      if (logController.getRawButton(4)) {
        m_grabber.set(0);
      }

      if (logController.getRawButton(5)) {
        m_grabber.set(-1);
      }

      //public Spark ballGrabberSpark()
      //m_grabber.enableDeadbandElimination(true);
      //ballGrabber.set(logController.getRawAxis(4));


      //if (logController.getRawButton(4)) {
       //ballGrabber.set(-0.7);
      //}

      // limit switch
      //while (limitSwitch.get()) {
      //  Timer.delay(10);
      //  if (limitSwitchLower.get() && speed < 0) {
      //   speed = 0;
      // }

      // if or while
      // 1 open, 0 closed
      //while (limitSwitchUpper.get()) {
      //  pistonOut.set(Value.kOff);
      //}
      //while (limitSwitchLower.get()) {
      //  pistonOut.set(Value.kOff);
      //}

      // {Joystick * ((throttle+1) / 2) }

    // Compressor //
    // compressor.setClosedLoopControl (true);
    //boolean enabled = compressor.enabled();
    // boolean pressureSwitch = compressor.getPressureSwitchValue();
    //double current = compressor.getCompressorCurrent();

    }

   /* This function is called periodically during test mode.
   */
  @Override
    public void testPeriodic() {
  }
}
