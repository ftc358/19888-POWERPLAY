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
public class TeleOp19888 extends LinearOpMode {
    //declaring motors
    private DcMotor motorLeft;
    private DcMotor motorRight;
    private DcMotor motorWheel;
    private DcMotor motorSlide;
    private Servo servoClaw;


    public void runOpMode() throws InterruptedException {
        //initializing motors/servos
        motorLeft = hardwareMap.dcMotor.get("motor1");
        motorRight = hardwareMap.dcMotor.get("motor2");
        motorWheel = hardwareMap.dcMotor.get("motor3");
        motorSlide = hardwareMap.dcMotor.get("motor4");
        servoClaw = hardwareMap.servo.get("servo1");

        waitForStart();

        while (opModeIsActive()) {
            //Moves the claw when corresponding buttons are pressed
            rotateClaw();

            //TODO try Turn2() instead of Turn() to see if it's better
            //Turns the robot when corresponding buttons are pressed
            Turn();

            //Moves the robot forward/backward when corresponding buttons are pressed
            ForwardBack();

            //Spins the spinning wheel when corresponding buttons are pressed
            Spin();

            //Moves the linear slide up/down when corresponding buttons are pressed
            MoveLadder();

            telemetry.addData("forward speed set to", -gamepad1.right_stick_y);
            telemetry.addData("turn speed set to", gamepad1.left_stick_x);
            telemetry.addData("left bumper", gamepad1.left_bumper);
            telemetry.addData("right bumper", gamepad1.right_bumper);
            telemetry.update();
        }
    }

    //functions that move motors
    //you can guess what they do by their names

    public void ForwardBack() {
        motorLeft.setPower(-gamepad1.right_stick_y);
        motorRight.setPower(-gamepad1.right_stick_y);
    }

    public void Turn() {
        motorLeft.setPower(-gamepad1.left_stick_x);
        motorRight.setPower(gamepad1.left_stick_x);
    }

    public void Turn2() {
        if (gamepad1.right_stick_x > 0.5) {
            motorLeft.setPower(-gamepad1.right_stick_x);
        }else if (gamepad1.right_stick_x < -0.5) {
            motorRight.setPower(gamepad1.right_stick_x);
        }
    }

    public void Spin() {
        motorWheel.setPower(gamepad1.right_trigger);
    }

    public void MoveLadder() {
        if (gamepad1.left_bumper) {
            motorSlide.setPower(1);
        }else if (gamepad1.right_bumper) {
            motorSlide.setPower(-1);
        }else {
            motorSlide.setPower(0);
        }
    }

    public void rotateClaw() {
        //TODO fix this function; it keeps rotating after the bumper is released
        //suggestion: maybe change 0.1 to 0.01
        if (gamepad1.a) {
            servoClaw.setPosition(servoClaw.getPosition()+0.1);
        }else if (gamepad1.b) {
            servoClaw.setPosition(servoClaw.getPosition()-0.1);
        }
    }
}