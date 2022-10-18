package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public abstract class RobotMain19888 extends LinearOpMode {
    final double DRIVE_FACTOR = 1.0;
    final double TURN_FACTOR = 1.0;
    final double STRAFE_FACTOR = 1.0;

    protected DcMotor lf, lr, rf, rr;

    protected DcMotor mid; //test chassis only

    public int AutoDir[] = {1, 1, 1, 1};

    public void ROBOT_INITIALIZE() throws InterruptedException {
        //lf = hardwareMap.dcMotor.get("lf");
        lr = hardwareMap.dcMotor.get("lr");
        rf = hardwareMap.dcMotor.get("rf");
        rr = hardwareMap.dcMotor.get("rr");

        //lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //lf.setDirection(DcMotor.Direction.REVERSE);
        lr.setDirection(DcMotor.Direction.REVERSE);
    }




    /*============================Autonomous Code================================*/




    public void MoveStraight(double inch, double power) {
        for (int i = 0; i < 4; i++) {AutoDir[i] = 1;}
        int ticks = (int) (inch * DRIVE_FACTOR);
        Move(ticks, power);
    }

    public void Strafe(double inch, double power) {
        AutoDir[0] = AutoDir[3] = -1;
        int ticks = (int) (inch * STRAFE_FACTOR);
        Move(ticks, power);
    }

    public void Turn(int degrees, double power) {
        AutoDir[2] = AutoDir[3] = -1;
        int ticks = (int) (degrees * TURN_FACTOR);
        Move(ticks, power);
    }

    public void Move(int ticks, double power) {

        lf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rf.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lf.setTargetPosition(lf.getCurrentPosition() - (ticks * AutoDir[0]));
        lr.setTargetPosition(lr.getCurrentPosition() - (ticks * AutoDir[1]));
        rf.setTargetPosition(rf.getCurrentPosition() - (ticks * AutoDir[2]));
        rr.setTargetPosition(rr.getCurrentPosition() - (ticks * AutoDir[3]));

        lf.setPower(power);
        lr.setPower(power);
        rf.setPower(power);
        rr.setPower(power);

        lf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rf.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rr.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (lf.isBusy() && lr.isBusy() && rf.isBusy() && rr.isBusy()) {
            telemetry.addData("lf", lf.getCurrentPosition());
            telemetry.addData("lr", lr.getCurrentPosition());
            telemetry.addData("rf", rf.getCurrentPosition());
            telemetry.addData("rr", rr.getCurrentPosition());
            telemetry.update();
        }

        sleep(200);
    }




    /*============================TeleOp Code================================*/




    public void TeleStraight() {
        if (gamepad1.left_stick_y > 0.25 || gamepad1.left_stick_y < -0.25) {
            lf.setPower(-gamepad1.left_stick_y);
            lr.setPower(-gamepad1.left_stick_y);
            rf.setPower(-gamepad1.left_stick_y);
            rr.setPower(-gamepad1.left_stick_y);
        }
    }

    public void TeleStrafe() {
        if (gamepad1.left_stick_x > 0.25 || gamepad1.left_stick_x < -0.25) {
            lf.setPower(gamepad1.left_stick_x);
            lr.setPower(-gamepad1.left_stick_x);
            rf.setPower(-gamepad1.left_stick_x);
            rr.setPower(gamepad1.left_stick_x);
        }
    }

    public void TeleTurn() {
        lf.setPower(gamepad1.right_stick_x);
        lr.setPower(gamepad1.right_stick_x);
        rf.setPower(-gamepad1.right_stick_x);
        rr.setPower(-gamepad1.right_stick_x);
    }

//    public void TeleDiagonal() {
//        double x = gamepad1.right_stick_x, y = -gamepad1.right_stick_y;
//        if (x < 0) {
//            lr.setPower(y);
//            rf.setPower(y);
//        }else {
//            lf.setPower(y);
//            rr.setPower(y);
//        }
//    }



    /*================TeleOp code for test chassis (omni wheels)================*/



    public boolean TestStraight() {
        if (gamepad1.left_stick_y > 0.25 || gamepad1.left_stick_y < -0.25) {
            lr.setPower(gamepad1.left_stick_y);
            rr.setPower(gamepad1.left_stick_y);
            return true;
        }
        return false;
    }

    public void TestStrafe() {
        mid.setPower(-gamepad1.left_stick_x);
    }

    public boolean TestTurn() {
        if (-0.25 < gamepad1.right_stick_x || gamepad1.right_stick_x < 0.25) {
            lr.setPower(-gamepad1.right_stick_x);
            rr.setPower(gamepad1.right_stick_x);
            return true;
        }
        return false;
    }


}
