package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public abstract class RobotMain19888 extends LinearOpMode {
    final double DRIVE_FACTOR = 1000.0 / 19.0; //19 inches = 1000 ticks
    final double TURN_FACTOR = 950.0 / 180.0; //180 degrees = 950 ticks
    final double STRAFE_FACTOR = 1.0;

    protected DcMotor lf, lr, rf, rr;
    protected DcMotor mid;
    protected DcMotor slideLeft, slideRight;
    protected Servo leftHand = null;
    protected Servo rightHand = null;

    public int AutoDir[] = {1, 1, 1, 1};

    public void ROBOT_INITIALIZE() throws InterruptedException {
        lf = hardwareMap.dcMotor.get("lf");
        lr = hardwareMap.dcMotor.get("lr");
        rf = hardwareMap.dcMotor.get("rf");
        rr = hardwareMap.dcMotor.get("rr");
        mid = hardwareMap.dcMotor.get("mid");

        slideLeft = hardwareMap.dcMotor.get("leftSlide");
        slideRight = hardwareMap.dcMotor.get("rightSlide");
        leftHand = hardwareMap.get(Servo.class, "left_hand");
        rightHand = hardwareMap.get(Servo.class, "right_hand");

        lf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rf.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        mid.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        slideRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        lf.setDirection(DcMotor.Direction.REVERSE);
        lr.setDirection(DcMotor.Direction.REVERSE);
        slideRight.setDirection(DcMotor.Direction.REVERSE);

//        leftHand.setPosition(0);
//        rightHand.setPosition(0);





    }




    /*============================Autonomous Code================================*/




    public void MoveStraight(double inch, double power) {
        for (int i = 0; i < 4; i++) {AutoDir[i] = -1;}
        int ticks = (int) (inch * DRIVE_FACTOR);
        Move(ticks, power);
    }

    public void Strafe(double inch, double power) {

        int ticks = (int) (inch * STRAFE_FACTOR);

        mid.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        mid.setTargetPosition(mid.getCurrentPosition() - ticks);

        mid.setPower(power);

        mid.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        while (mid.isBusy()) {
            telemetry.addData("mid", mid.getCurrentPosition());
            telemetry.update();
        }

        sleep(200);
    }

    public void Turn(int degrees, double power) {
        //turning left is positive degrees
        AutoDir[2] = AutoDir[3] = -1;
        AutoDir[0] = AutoDir[1] =  1;
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

    public void SlideOP(int direction, double power){
        int ticks = 69 * direction;
        slideLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slideRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slideLeft.setTargetPosition(slideLeft.getCurrentPosition()-(ticks));
        slideRight.setTargetPosition(slideRight.getCurrentPosition()-(ticks));
        slideLeft.setPower(power);
        slideRight.setPower(power);
        while (slideLeft.isBusy() && slideRight.isBusy()) {
            telemetry.addData("slideLeft", slideLeft.getCurrentPosition());
            telemetry.addData("slideRight", slideRight.getCurrentPosition());
            telemetry.update();
        }
    }


    /*============================TeleOp Code================================*/


    public double powerfactor(){
        if (gamepad1.left_bumper){
            return 1;
        }
        else{
            return 0.5;
        }
    }

    public boolean TeleStraight() {
        if (gamepad1.left_stick_y > 0.25 || gamepad1.left_stick_y < -0.25) {
            //right now left stick x = 0
            //if it wants to turn left it will become -1
            //right, +1
            double leftWheels, rightWheels;

            if (gamepad1.left_stick_x<-0.1){
                leftWheels = gamepad1.left_stick_y * (1+gamepad1.left_stick_x);
                rightWheels = (gamepad1.left_stick_y);
            }//for left turns
            else if (gamepad1.left_stick_x>0.1){
                leftWheels = (gamepad1.left_stick_y);
                rightWheels = gamepad1.left_stick_y * (1-gamepad1.left_stick_x);
            }//for right turns
            else{
                leftWheels = (gamepad1.left_stick_y);
                rightWheels = (gamepad1.left_stick_y);
            }

            lf.setPower(-leftWheels*powerfactor());
            lr.setPower(-leftWheels*powerfactor());
            rf.setPower(-rightWheels*powerfactor());
            rr.setPower(-rightWheels*powerfactor());
            return true;
        }
        return false;
    }
    public void TeleStrafe() {
        if (gamepad1.right_stick_x > 0.25 || gamepad1.right_stick_x < -0.25) {
            mid.setPower(gamepad1.right_stick_x);
        }
        else {
            mid.setPower(0);
        }
    }

    public void TeleTurn() {
        double power = gamepad1.left_stick_x * powerfactor();
        lf.setPower(power);
        lr.setPower(power);
        rf.setPower(-power);
        rr.setPower(-power);
    }

    public void TeleSlide() {
        if (gamepad1.right_bumper) {
            slideLeft.setPower(1);
            slideRight.setPower(1);
        }
        if (gamepad1.a){
            slideLeft.setPower(-1);
            slideRight.setPower(-1);

        }
        else{
            slideLeft.setPower(0);
            slideRight.setPower(0);
        }
    }

    public void TeleClaw() {
        //position 0 is the grab function
        // position 1 or -1 is the release?

        //make a toggle
        if (gamepad1.x){
            leftHand.setPosition(0.6);
            rightHand.setPosition(0.4);
        }
        if (gamepad1.b){
            leftHand.setPosition(0.4);
            rightHand.setPosition(0.6);
        }
    }


}