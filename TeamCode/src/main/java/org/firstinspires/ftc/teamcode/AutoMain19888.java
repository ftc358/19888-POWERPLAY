package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*

***This is the code for the Autonomous Period***

Most of the code is not complete yet.

 */

@Autonomous
public class AutoMain19888 extends RobotMain19888 {

    private boolean done = false;

    public void runOpMode() throws InterruptedException {

        ROBOT_INITIALIZE();

        waitForStart();

        while (opModeIsActive() && !done) {
            telemetry.addData("Program Started", null);
            telemetry.update();

            MoveStraight(3,0.8);
            Strafe(3,0.8);

            done = true;
        }
    }
}
