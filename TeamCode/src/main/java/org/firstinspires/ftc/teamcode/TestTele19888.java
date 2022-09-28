package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class TestTele19888 extends RobotMain19888{

    public void runOpMode() throws InterruptedException {

        waitForStart();
        ROBOT_INITIALIZE();

        //initializing mid for test chassis
        mid = hardwareMap.dcMotor.get("mid");
        mid.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        while (opModeIsActive()) {
            if (!TestStraight()) {
                TestTurn();
            }
            TestStrafe();

            telemetry.addData("L Joystick x", gamepad1.left_stick_x);
            telemetry.addData("L Joystick y", gamepad1.left_stick_y);
            telemetry.addData("R Joystick x", gamepad1.right_stick_x);
            telemetry.addData("R Joystick y", gamepad1.right_stick_y);
            telemetry.update();

        }
    }

}
