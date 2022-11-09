package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import java.lang.Thread;
/*

This is the code for the Autonomous Period

Most of the code is not complete yet.

 */

@Autonomous
public class AutoMain19888 extends RobotMain19888 {

    private boolean done = false;
    OpenCvCamera camera;
    SleeveDetection sleeveDetection = new SleeveDetection();

    public void runOpMode() throws InterruptedException {

        ROBOT_INITIALIZE();

        //-----------------------------------------------SLEEVE DETECTION-----------------------------------------------

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        camera = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);


        sleeveDetection = new SleeveDetection();
        camera.setPipeline(sleeveDetection);

        camera.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                camera.startStreaming(320,240, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode) {}
        });
        int park_ = 0;
        while (!isStarted()) {
            SleeveDetection.ParkingPosition parking_=sleeveDetection.getPosition();
            telemetry.addData("ROTATION: ", parking_);
            switch(parking_) {
                case LEFT:
                    park_=1;
                    break;
                case CENTER:
                    park_=2;
                    break;
                case RIGHT:
                    park_=3;
                    break;
            }
            telemetry.update();
        }

        waitForStart();

        //------------------------------------------------PROGRAM STARTS HERE------------------------------------------------

        while (opModeIsActive() && !done) {
            telemetry.addData("Program Started", null);
            telemetry.update();
            double power_value=0.3;
            //Drive
            MoveStraight(6,power_value);
            Thread.sleep(500);
            Turn(-90,power_value);
            MoveStraight(23,power_value);
            Turn(90,power_value);
            MoveStraight(21,power_value);
            Turn(-45,power_value);
            MoveStraight(8,power_value);
            //Place First Cone
            MoveStraight(-12,power_value);
            Thread.sleep(500);
            Turn(45,power_value);
            MoveStraight(28,power_value);
            Turn(88,power_value);
            MoveStraight(55,power_value);
            //pick up second cone
            MoveStraight(-53,power_value);
            Thread.sleep(500);
            Turn(-43,power_value);
            MoveStraight(6,power_value);
            Thread.sleep(500);
            //place the second coneiuk
            MoveStraight(-6,power_value);
            Thread.sleep(500);
            Turn(43,power_value);
            MoveStraight(55,power_value);
            //pick up the third cone
            Thread.sleep(500);
            MoveStraight(-53,power_value);
            Thread.sleep(500);
            Turn(-43,power_value);
            MoveStraight(6,power_value);
            //place the third cone
            MoveStraight(-6,power_value);
            Thread.sleep(500);
            Turn(43,power_value);
            if(park_==1) {
                MoveStraight(50,power_value);
            }
            if(park_==2) {
                MoveStraight(23,power_value);
            }
            if(park_==3) {
                MoveStraight(-2,power_value);
            }
            done = true;
        }
    }
}