package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.lang.Thread;
/*

This is the code for the Autonomous Period

Most of the code is not complete yet.

 */

@Autonomous
public class AutoMain19888 extends RobotMain19888 {

    private boolean done = false;

    public void runOpMode() throws InterruptedException {

        ROBOT_INITIALIZE();

        //---
        waitForStart();

        //------------------------------------------------PROGRAM STARTS HERE------------------------------------------------

        while (opModeIsActive() && !done) {
            telemetry.addData("###############", null);
            telemetry.addData("I have Control", null);
            telemetry.addData("###############", null);
            telemetry.update();
            double power_value=0.5;
            //Drive
            MoveStraight(6,power_value);
            Turn(100,power_value);
            MoveStraight(35,power_value);

            done = true;



            /*MoveStraight(23,power_value);
            Turn(90,power_value);
            MoveStraight(21,power_value);
            Turn(-45,power_value);
            MoveStraight(8,power_value);
            //Place First Cone
            MoveStraight(-12,power_value);
            Thread.sleep(500);
            Turn(45,power_value);
            MoveStraight(28,power_value);
            Turn(90,power_value);
            MoveStraight(55,power_value);
            //pick up second cone
            MoveStraight(-53,power_value);
            Thread.sleep(500);
            Turn(-45,power_value);
            MoveStraight(6,power_value);
            Thread.sleep(500); //remove this when the cone-placing is done
            //place the second coneiuk
            MoveStraight(-6,power_value);
            Thread.sleep(500);
            Turn(45,power_value);

             */
            /*
            if(park_==1) {
                MoveStraight(60,power_value);
            }
            if(park_==2) {
                MoveStraight(19,power_value);
            }
            if(park_==3) {
                MoveStraight(-1,power_value);
            }
            done = true;
            */
        }
        if (done){
            telemetry.addData("###############", null);
            telemetry.addData("Mission CMPL.", null);
            telemetry.addData("###############", null);
            telemetry.update();
        }
    }
}