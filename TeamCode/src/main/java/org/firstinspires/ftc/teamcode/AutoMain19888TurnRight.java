package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/*

This is the code for the Autonomous Period

Most of the code is not complete yet.

 */

@Autonomous
public class AutoMain19888TurnRight extends RobotMain19888 {

    private boolean done = false;

    public void runOpMode() throws InterruptedException {

        ROBOT_INITIALIZE();

        //-----------------------------------------------SLEEVE DETECTION-----------------------------------------------


        //------------------------------------------------PROGRAM STARTS HERE------------------------------------------------

        while (opModeIsActive() && !done) {
            telemetry.addData("Program Started", null);
            telemetry.update();
            double power_value=0.5;
            //Drive
            MoveStraight(6,power_value);
            Turn(-120,power_value);
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
    }
}