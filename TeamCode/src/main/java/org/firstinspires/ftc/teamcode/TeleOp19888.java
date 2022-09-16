package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/*

***This is the code the for Driver Control Period***

These are the controls as of right now.
Feel free to edit these as you edit this code

right joystick --> Moving the robot forward/backward
left joystick --> Making the robot turn left/right
right trigger --> Spinning wheel
right bumper --> moving the linear slide up
left bumper --> moving the linear slide down
a --> open claw
b --> close claw

 */

@TeleOp
public class TeleOp19888 extends RobotMain19888 {


    public void runOpMode() throws InterruptedException {

        waitForStart();

        ROBOT_INITIALIZE();

        while (opModeIsActive()) {
            TeleMoveV();
            TeleMoveH();
            TeleTurn();

            telemetry.addData("L Joystick x", -gamepad1.right_stick_y);
            telemetry.addData("L Joystick y", gamepad1.left_stick_x);
            telemetry.addData("R Joystick x", gamepad1.left_bumper);
            telemetry.addData("R Joystick y", gamepad1.right_bumper);
            telemetry.update();
        }
    }
}