/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
//import sun.jvm.hotspot.runtime.StaticBaseConstructor;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

  

  public class Robot extends TimedRobot {
    public static Joystick joy1 = new Joystick(0);
    private final VictorSPX frontLeftMotor=new VictorSPX(0);
    private final VictorSPX frontRightMotor=new VictorSPX(3);
    private final VictorSPX backLeftMotor=new VictorSPX(2);
    private final VictorSPX backRightMotor=new VictorSPX(4);
    private Encoder encoder=new Encoder(0,1);
    int setpoint=10;
    private final double kDriveTick2Feet=1.0/128*6*Math.PI/12; 
   // private Servo CameraServer
    double errorsum;
    double lastTimestamp;
    double derror;
    double lastError;
  @Override
  public void robotInit() {
  }

  @Override
  public void autonomousInit() {
  encoder.reset();
  errorsum=0;
  lastError=0;
  lastTimestamp=Timer.getFPGATimestamp();
  }

  @Override
  public void autonomousPeriodic() {
  final double kp=0.3;
  double sensorpoistion=encoder.get()*unknown;
  double error=setpoint-sensorpoistion;
  
  final double ki=0.5;
  double dt=Timer.getFPGATimestamp()-lastTimestamp;
  final double limit=1;
  
  final double kd=0.1;
  double derror=(error-lastError)/dt;
  double output=kp*error + ki*errorsum + kd*derror;
  
  
  if(Math.abs(error)<limit){
    errorsum += error*dt;
  }

  if(joy1.getRawButton(1)){
    setpoint=10;
  }
  else if(joy1.getRawButton(2)){
    setpoint=0;
  }
  
  frontLeftMotor.set(ControlMode.PercentOutput,output);
  frontRightMotor.set(ControlMode.PercentOutput,-output);
  backLeftMotor.set(ControlMode.PercentOutput,output);
  backRightMotor.set(ControlMode.PercentOutput,-output);
   lastError=error;
  }


  @Override
  public void teleopInit() {

    }
    
  @Override
  public void teleopPeriodic() {
    
    final double speed = joy1.getRawAxis(4)*0.5;
    final double turn = joy1.getRawAxis(1)*0.5;
    
    frontLeftMotor.set(ControlMode.PercentOutput,speed+turn);;
    frontRightMotor.set(ControlMode.PercentOutput,speed-turn);
    backLeftMotor.set(ControlMode.PercentOutput,speed+turn);
    backRightMotor.set(ControlMode.PercentOutput,speed-turn);
    SmartDashboard.putNumber("speed",speed);
    SmartDashboard.putNumber("turn",turn);
    
    

    
    }

  

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}

  
  
