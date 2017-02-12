/*
Copyright (c) 2016 Robert Atkinson

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Robert Atkinson nor the names of his contributors may be used to
endorse or promote products derived from this software without specific prior
written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESSFOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR
TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
        package org.firstinspires.ftc.teamcode;
        import com.qualcomm.robotcore.hardware.ColorSensor;
        import com.qualcomm.robotcore.hardware.DcMotorSimple;
        import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
        import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
        import com.qualcomm.robotcore.eventloop.opmode.Disabled;
        import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
        import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
        import com.qualcomm.robotcore.hardware.DcMotor;
        import com.qualcomm.robotcore.hardware.Servo;
        import com.qualcomm.robotcore.hardware.UltrasonicSensor;
        import com.qualcomm.robotcore.util.ElapsedTime;
        import com.qualcomm.robotcore.hardware.DcMotorController;
        import com.qualcomm.robotcore.hardware.UltrasonicSensor;
        import com.qualcomm.robotcore.hardware.CRServo;
        import com.qualcomm.robotcore.hardware.TouchSensor;
/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Use Android Studios to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this opmode to the Driver Station OpMode list
 */

@Autonomous(name="Capball Oriented Autonomous", group="auto")  // @Autonomous(...) is the other common choice
public class carballauto extends LinearOpMode {

    /* Declare OpMode members. */
    private ElapsedTime runtime = new ElapsedTime();
    // DcMotor leftMotor =
    Servo lift;
    TouchSensor Detector;
    DcMotor wheel1; //D
    DcMotor wheel2; //C
    DcMotor wheel3; //A
    DcMotor Shooter;
    DcMotor wheel4; //B
    UltrasonicSensor UltraSonic;
    ColorSensor Color;
    String FinalColor;
    OpticalDistanceSensor Light;
    CRServo Hold;

    public class Shooting{
        public void LoadBall(){
            Hold = hardwareMap.crservo.get("BallHold");
            //Hold.setPower(1);
            //sleep(2000);
            /*sleep(2000);
            Hold.setPower(0);
            sleep(3000);
            Hold.setPower(-.5);
            sleep(3000);
            Hold.setPower(0);*/
        }
        public void PrepareShot(){

            lift = hardwareMap.servo.get("Lift");
            //Shooter.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //sleep(500);
            //Shooter.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //Shooter.setDirection(DcMotorSimple.Direction.REVERSE);
           // Shooter.setTargetPosition(-2548);
            //Shooter.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            //telemetry.addData("Current Position",Shooter.getCurrentPosition());
            //Shooter.setPower(1);
            //telemetry.addData("Current Position",Shooter.getCurrentPosition())
            //Shooter.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            lift.setPosition(.7);
        }
        public void Fire(){
            Shooter = hardwareMap.dcMotor.get("shooter");
            Shooter.setPower(1);
            sleep(5000);
           Shooter.setPower(0);
        }
    }
    // DcMotor rightMotor = null;
    public String DetermineColor(ColorSensor Color){
        int BlueLevel = Color.blue();
        int RedLevel = Color.red();

        telemetry.addData("Front Servo","OFF");
        telemetry.addData("BlueLevel","off");
        telemetry.addData("RedLevel","off");
        if(RedLevel >= 1 && BlueLevel ==0){
            FinalColor = "Red";
        }
        if(RedLevel ==0 && BlueLevel >=1){
            FinalColor = "Blue";
        }
        return FinalColor;
    }
    public void move(String Direction) {
        if(Direction == "Forward"){
            wheel3.setPower(1);
            wheel4.setPower(-1);
            wheel2.setPower(1);
            wheel1.setPower(-1);
        }
        if (Direction == "Stop"){
            wheel3.setPower(0);
            wheel4.setPower(0);
            wheel2.setPower(0);
            wheel1.setPower(0);
        }
    }
    @Override
    public void runOpMode() {
        Shooter = hardwareMap.dcMotor.get("shooter");
        wheel1 = hardwareMap.dcMotor.get("wheel 1");
        wheel2 = hardwareMap.dcMotor.get("wheel 2");
        wheel3 = hardwareMap.dcMotor.get("wheel 3");
        wheel4 = hardwareMap.dcMotor.get("wheel 4");
        lift = hardwareMap.servo.get("Lift");
        Color = hardwareMap.colorSensor.get("ColorSensor");
        telemetry.addData("Status","Ready");
        telemetry.addData("Current Position","awaiting results");
        waitForStart();
        String Output = DetermineColor(Color);
        telemetry.addData("color",Output);
      // telemetry.addData("Status","Ready");
        //telemetry.addData("Current Position","awaiting results");
       /* lift.setPosition(.7);
        sleep(1000);
        Shooter.setPower(1);
        sleep(6000);
        lift.setPosition(1);
        Shooter.setPower(0);
        telemetry.addData("Status", "Initialized");
        runtime.reset();*/
        Shooting manager = new Shooting();
        manager.PrepareShot();
        sleep(2000);
        move("Forward");
        sleep(100);
        move("Stop");
        manager.Fire();
        //manager.LoadBall();
        sleep(8500);
        runtime.reset();
        while (opModeIsActive() && (runtime.seconds() < 2.5)) {
            move("Forward");
        }
        move("Stop");
    }
}
