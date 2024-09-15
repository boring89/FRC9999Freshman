// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //private  double o_leftSpeed;
 // private double o_rightspeed;
 private Timer time = new Timer();
 //private double starttime;
 private double nowTime;
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private WPI_VictorSPX leftfront, leftback, rightfront, rightback;
  private Joystick m_Joystick = new Joystick(0);
  private MotorControllerGroup lefGroup, righGroup;
  private DifferentialDrive m_Drive;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    //o_leftSpeed=m_Joystick.getRawAxis(1);
    //o_rightspeed=m_Joystick.getRawAxis(3);
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    //o_leftSpeed=m_Joystick.getRawAxis(1);
    leftfront = new WPI_VictorSPX(1);
    leftback = new WPI_VictorSPX(2);
    rightfront = new WPI_VictorSPX(0);
    rightback = new WPI_VictorSPX(3);
    lefGroup = new MotorControllerGroup(leftfront, leftback);
    righGroup = new MotorControllerGroup(rightfront, rightback);
    m_Drive = new DifferentialDrive(lefGroup,righGroup);

  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    //starttime=Timer.getFPGATimestamp();
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
    time.reset();
    time.start();
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
     nowTime = time.get();
     if(nowTime<3){
      lefGroup.set(-0.6);
      righGroup.set(0.6);
     }else{
      lefGroup.set(0);
      righGroup.set(0);
     }
    
    }
  

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    lefGroup.setInverted(true);
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double leftSpeed = -m_Joystick.getRawAxis(1);
    double rightSpeed = m_Joystick.getRawAxis(2);
    m_Drive.arcadeDrive(leftSpeed, rightSpeed);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
