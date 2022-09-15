package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class RobotMain19888 extends LinearOpMode {
    final double DRIVE_FACTOR = 1.0;

    protected DcMotor m1;
    protected DcMotor m2;
    protected DcMotor m3;
    protected DcMotor m4;

    public void ROBOT_INITIALIZE() throws InterruptedException {
        m1 = hardwareMap.dcMotor.get("motor1");
        m2 = hardwareMap.dcMotor.get("motor2");
        m3 = hardwareMap.dcMotor.get("motor3");
        m4 = hardwareMap.dcMotor.get("motor4");

        m1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        m4.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        m1.setDirection(DcMotor.Direction.REVERSE);
        m2.setDirection(DcMotor.Direction.REVERSE);
    }

    public void MoveV(double inch, double power) {
        int ticks = (int) (inch * DRIVE_FACTOR);

        m1.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m3.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        m1.setTargetPosition(m1.getCurrentPosition() - ticks);
        m3.setTargetPosition(m3.getCurrentPosition() - ticks);

        m1.setPower(power);
        m3.setPower(power);

        m1.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m3.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (m1.isBusy() && m2.isBusy()) {
            telemetry.addData("m1", -m1.getCurrentPosition());
            telemetry.addData("m3", -m3.getCurrentPosition());
            telemetry.update();
        }

        sleep(200);
    }

    public void MoveH(double inch, double power) {
        int ticks = (int) (inch * DRIVE_FACTOR);

        m2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        m4.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        m2.setTargetPosition(m1.getCurrentPosition() - ticks);
        m4.setTargetPosition(m3.getCurrentPosition() - ticks);

        m2.setPower(power);
        m4.setPower(power);

        m2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        m4.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (m2.isBusy() && m4.isBusy()) {
            telemetry.addData("m2", -m2.getCurrentPosition());
            telemetry.addData("m4", -m4.getCurrentPosition());
            telemetry.update();
        }

        sleep(200);
    }

    /*--------------Code for mecanum wheel---------------*/

    public void TeleStraight() {
        m1.setPower(gamepad2.left_stick_y);
        m2.setPower(gamepad2.left_stick_y);
        m3.setPower(gamepad2.left_stick_y);
        m4.setPower(gamepad2.left_stick_y);
    }

    public void TeleStrafe() {
        m1.setPower(gamepad2.left_stick_x);
        m2.setPower(gamepad2.left_stick_x);
        m3.setPower(-gamepad2.left_stick_x);
        m4.setPower(-gamepad1.left_stick_x);
    }

    public void TeleDiagonal() {
        double x = gamepad2.right_stick_x, y = gamepad2.right_stick_y;
        if (x < 0) {
            m2.setPower(y);
            m4.setPower(y);
        }else {
            m1.setPower(y);
            m3.setPower(y);
        }
    }

    public void TeleTurn() {
        int dir = 0;
        if (gamepad2.dpad_right) dir = 1;
        if (gamepad2.dpad_left) dir = -1;
        m1.setPower(0.8 * dir);
        m2.setPower(0.8 * dir);
        m3.setPower(-0.8 * dir);
        m4.setPower(-0.8 * dir);
    }

}
