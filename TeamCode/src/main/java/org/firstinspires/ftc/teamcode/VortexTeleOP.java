package org.firstinspires.ftc.teamcode;

import android.hardware.TriggerEvent;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
/**
 * Created by robodragons5488 on 10/11/2016.
 */@TeleOp(name="Template: Iterative OpMode", group="Iterative Opmode")
public class VortexTeleOP extends OpMode{
    DcMotor wheel1; //D
    DcMotor wheel2; //C
    DcMotor wheel3; //A
    DcMotor wheel4; //B
    CRServo FrontFeeder;
    DcMotor Shooter;
    ColorSensor Color;
    Servo lift;
    //boolean FrontFeedOn;
    DcMotor MainFeeder;
    OpticalDistanceSensor Light;
    //boolean MainFeedOn;
    //Servo Beacon;
    //Servo Storage;
    @Override
    public void init() {
        Light = hardwareMap.opticalDistanceSensor.get("LightSensor");
        wheel1 = hardwareMap.dcMotor.get("wheel 1");
        wheel2 = hardwareMap.dcMotor.get("wheel 2");
        wheel3 = hardwareMap.dcMotor.get("wheel 3");
        wheel4 = hardwareMap.dcMotor.get("wheel 4");
        FrontFeeder = hardwareMap.crservo.get("Front");
        MainFeeder = hardwareMap.dcMotor.get("Main Feeder");
        Shooter = hardwareMap.dcMotor.get("shooter");
        lift=hardwareMap.servo.get("Lift");
        Color = hardwareMap.colorSensor.get("ColorSensor");
        //FrontFeedOn = false;
        // MainFeedOn = false;
        // Beacon = hardwareMap.servo.get("Beacon Pusher");
        //Storage = hardwareMap.servo.get("Ball Storage");
    }
    @Override
    public void loop() {
        float left_Y = -gamepad1.left_stick_y; // up and down
        float left_X = gamepad1.left_stick_x; // left and right
        float right_X = gamepad1.right_stick_x; // right stick for rotation
        boolean RightBumper = gamepad2.right_bumper;//Front Feeder in
        boolean LeftBumper = gamepad2.left_bumper;//Feedr in
        float LeftTrigger = gamepad2.left_trigger;//feeder out
        float RightTrigger = gamepad2.right_trigger;//Front feeder out
        boolean ButtonY = gamepad2.y;//Shooter pin
        boolean ButtonB = gamepad2.b;
        boolean ButtonX = gamepad2.x;//Lift
        boolean Right = gamepad2.dpad_right;
        int BlueLevel = Color.blue();
        int RedLevel = Color.red();
        double LightLvl = Light.getLightDetected();
        telemetry.addData("Current Position",Shooter.getCurrentPosition());
        telemetry.addData("Front Servo","OFF");
        telemetry.addData("BlueLevel","off");
        telemetry.addData("RedLevel","off");
        if(RedLevel >= 1 && BlueLevel ==0){
            telemetry.addData("ColorStatus","Red");
        }
        if(LightLvl > .03){
            telemetry.addData("Status","On the line");

        }
        else{
            telemetry.addData("Status","Off line");
        }
        if(ButtonX){
            lift.setPosition(.7);

            telemetry.addData("Lift:","ON");
        }
        else{
            lift.setPosition(1);
            telemetry.addData("Lift:","OFF");
        }
        if(Right){
            telemetry.addData("BlueLevel",BlueLevel);
            telemetry.addData("RedLevel",RedLevel);
            telemetry.addData("LightLevel",LightLvl);
        }
        if(ButtonB){
            Shooter.setPower(-1);
        }
        if(ButtonY){
            telemetry.addData("Shooter","ACTIVE");
            Shooter.setPower(1);
        }
        else{
            Shooter.setPower(0);
        }

        if (RightTrigger ==1  && RightBumper == false) {//If trigger is being pushed all the way in and the right bumper is not activley being pushed then it will turn on the fron feeder
            FrontFeeder.setPower(1);
            telemetry.addData("Front Servo", "ON");
        }
        else{//
            if(RightBumper == false) {//Otherwise,if the bumper is still not being pushed, turn off the feeder
                FrontFeeder.setPower(0);
                telemetry.addData("Front Servo","OFF");
            }
            else{
                FrontFeeder.setPower(-1);
                telemetry.addData("Status","Trigger is not being pushed but bumper is");
            }
        }
        if (LeftTrigger == 1 && LeftBumper == false) {//If left trigger is being pushed while the left bumper is not the main feeder will power on
            MainFeeder.setPower(1);
        }
        else {
            if(LeftBumper == false){//if trigger is not  being pushed and the left bumper is also not being pushed turn off motor
                MainFeeder.setPower(0);
            }
            else{//if bumper is being pushed and the trigger is not then set the power to revese the feeder
                MainFeeder.setPower(-1);
            }
        }
        if (Math.abs(right_X) < .1)
            if (Math.abs(left_Y) > Math.abs(left_X)){ // we check which value is greater
                // if (left_Y > 0) { // If stick is pushed forwards ~~ If Y stick has more force on to it than the X then y is taken into account while X is ignored
                wheel3.setPower(left_Y);
                wheel4.setPower(-left_Y);
                wheel2.setPower(left_Y);
                wheel1.setPower(-left_Y);
                //}
                /*/else { //otherwise, pushed back
                    wheel3.setPower(-left_Y);
                    wheel4.setPower(left_Y);
                    wheel2.setPower(-left_Y);
                    wheel1.setPower(left_Y);
                } */
            }
            else{ // Y is ignored and X is taken into Account
                //    if (left_X > 0) { // If stick is pushed forwards
                wheel3.setPower(left_X);
                wheel4.setPower(left_X);
                wheel2.setPower(-(left_X));
                wheel1.setPower(- (left_X));
              /*  }
                else { //otherwise, pushed back
                    wheel3.setPower(-(left_X));
                    wheel4.setPower(-(left_X));
                    wheel2.setPower(left_X);
                    wheel1.setPower(left_X);
                } */
            }
        else{
            wheel3.setPower(-right_X);
            wheel4.setPower(-right_X);
            wheel2.setPower(-right_X);
            wheel1.setPower(-right_X);
        }
    }
}


