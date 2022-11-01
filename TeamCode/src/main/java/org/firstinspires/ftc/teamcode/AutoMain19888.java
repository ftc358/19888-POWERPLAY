package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

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

        while (!isStarted()) {
            telemetry.addData("ROTATION: ", sleeveDetection.getPosition());
            telemetry.update();
        }

        waitForStart();

        //------------------------------------------------PROGRAM STARTS HERE------------------------------------------------

        while (opModeIsActive() && !done) {
            telemetry.addData("Program Started", null);
            telemetry.update();

            //Drive
            MoveStraight(6,1);
            Turn(-90,1);
            MoveStraight(24,1);
            Turn(90,1);
            MoveStraight(26,1);
            Turn(-45,1);
            MoveStraight(17,1);
            //Place First Cone
            MoveStraight(-16,1);
            Turn(135,1);
            MoveStraight(20,1);
            Turn(-90,1);

            done = true;
        }
    }
}