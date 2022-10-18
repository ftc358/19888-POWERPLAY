package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.navigation.AngleUnit.DEGREES;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XYZ;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.XZY;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesOrder.YZX;
import static org.firstinspires.ftc.robotcore.external.navigation.AxesReference.EXTRINSIC;
import static org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection.BACK;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

public abstract class RobotMain19888 extends LinearOpMode {
    final double DRIVE_FACTOR = 1.0;
    final double TURN_FACTOR = 1.0;
    final double STRAFE_FACTOR = 1.0;

    //---------------------------------------------Vuforia Stuff-------------------------------------------------------

    private static final VuforiaLocalizer.CameraDirection CAMERA_CHOICE = BACK;
    private static final boolean PHONE_IS_PORTRAIT = true  ;

    private static final String VUFORIA_KEY =
            "AbZ0zWr/////AAABmegucpo600ErmcFxS2aMV7ptSyA1P8gCOnTDvfVMpi0RFTfURYcJzuV7KWLt9LtuufUk0Nuk9W+/mx1ar+uXasAMxVJqPm2YAFUnSHNznxPO7unScJ8RsyTk6M3FPp4ppyWVMHcZFdZoLDDko9RfKgqwZkYetEI/zrrOee/DtBSnendvlndPvrUiUXGqrLa/7hUPOoqyt/yB9bx9sbLyh+R71Atc6BbyJ5Dfjf/jf88cCreydDo5c9iQWeNoRH5R83uOx0eaw5ZihYAWMBveK7pYaYDgcbFyEQqhMj7K5agOgQN3/0gpBWfjmEkEdR+2iUoQGkwoxr5jEUdbq8iUwHZ2br+a3wYknQcnopwfg1jD";

    // Since ImageTarget trackables use mm to specifiy their dimensions, we must use mm for all the physical dimension.
    // We will define some constants and conversions here
    private static final float mmPerInch        = 25.4f;
    private static final float mmTargetHeight   = 6 * mmPerInch;          // the height of the center of the target image above the floor
    private static final float halfField        = 72 * mmPerInch;
    private static final float halfTile         = 12 * mmPerInch;
    private static final float oneAndHalfTile   = 36 * mmPerInch;

    // Class Members
    private OpenGLMatrix lastLocation = null;
    private VuforiaLocalizer vuforia  = null;
    private VuforiaTrackables targets = null;

    public List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();

    private boolean targetVisible = false;
    private float phoneXRotate    = 0;
    private float phoneYRotate    = 0;
    private float phoneZRotate    = 0;

    //----------------------------------------------------------------------------------------------------------------

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





        //=====================================================Vuforia initialization==================================================




        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection   = CAMERA_CHOICE;

        // Turn off Extended tracking.  Set this true if you want Vuforia to track beyond the target.
        parameters.useExtendedTracking = false;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Load the trackable assets.
        targets = this.vuforia.loadTrackablesFromAsset("PowerPlay");

        // For convenience, gather together all the trackable objects in one easily-iterable collection */
        allTrackables.addAll(targets);

        // Name and locate each trackable object
        identifyTarget(0, "Red Audience Wall",   -halfField,  -oneAndHalfTile, mmTargetHeight, 90, 0,  90);
        identifyTarget(1, "Red Rear Wall",        halfField,  -oneAndHalfTile, mmTargetHeight, 90, 0, -90);
        identifyTarget(2, "Blue Audience Wall",  -halfField,   oneAndHalfTile, mmTargetHeight, 90, 0,  90);
        identifyTarget(3, "Blue Rear Wall",       halfField,   oneAndHalfTile, mmTargetHeight, 90, 0, -90);

        if (CAMERA_CHOICE == BACK) {
            phoneYRotate = -90;
        } else {
            phoneYRotate = 90;
        }

        // Rotate the phone vertical about the X axis if it's in portrait mode
        if (PHONE_IS_PORTRAIT) {
            phoneXRotate = 270 ;
        }

        // Next, translate the camera lens to where it is on the robot.
        // In this example, it is centered on the robot (left-to-right and front-to-back), and 6 inches above ground level.
        final float CAMERA_FORWARD_DISPLACEMENT  = 0.0f * mmPerInch;   // eg: Enter the forward distance from the center of the robot to the camera lens
        final float CAMERA_VERTICAL_DISPLACEMENT = 6.0f * mmPerInch;   // eg: Camera is 6 Inches above ground
        final float CAMERA_LEFT_DISPLACEMENT     = 0.0f * mmPerInch;   // eg: Enter the left distance from the center of the robot to the camera lens

        OpenGLMatrix robotFromCamera = OpenGLMatrix
                .translation(CAMERA_FORWARD_DISPLACEMENT, CAMERA_LEFT_DISPLACEMENT, CAMERA_VERTICAL_DISPLACEMENT)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, YZX, DEGREES, phoneYRotate, phoneZRotate, phoneXRotate));

        /**  Let all the trackable listeners know where the phone is.  */
        for (VuforiaTrackable trackable : allTrackables) {
            ((VuforiaTrackableDefaultListener) trackable.getListener()).setPhoneInformation(robotFromCamera, parameters.cameraDirection);
        }


    }



    /*============================Vuforia Code================================*/


    void    identifyTarget(int targetIndex, String targetName, float dx, float dy, float dz, float rx, float ry, float rz) {
        VuforiaTrackable aTarget = targets.get(targetIndex);
        aTarget.setName(targetName);
        aTarget.setLocation(OpenGLMatrix.translation(dx, dy, dz)
                .multiplied(Orientation.getRotationMatrix(EXTRINSIC, XYZ, DEGREES, rx, ry, rz)));
    }

    void detectTarget() {
        targets.activate();
        while (!isStopRequested()) {

            // check all the trackable targets to see which one (if any) is visible.
            targetVisible = false;
            for (VuforiaTrackable trackable : allTrackables) {
                if (((VuforiaTrackableDefaultListener)trackable.getListener()).isVisible()) {
                    telemetry.addData("Visible Target", trackable.getName());
                    targetVisible = true;

                    // getUpdatedRobotLocation() will return null if no new information is available since
                    // the last time that call was made, or if the trackable is not currently visible.
                    OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)trackable.getListener()).getUpdatedRobotLocation();
                    if (robotLocationTransform != null) {
                        lastLocation = robotLocationTransform;
                    }
                    break;
                }
            }

            // Provide feedback as to where the robot is located (if we know).
            if (targetVisible) {
                // express position (translation) of robot in inches.
                VectorF translation = lastLocation.getTranslation();
                telemetry.addData("Pos (inches)", "{X, Y, Z} = %.1f, %.1f, %.1f",
                        translation.get(0) / mmPerInch, translation.get(1) / mmPerInch, translation.get(2) / mmPerInch);

                // express the rotation of the robot in degrees.
                Orientation rotation = Orientation.getOrientation(lastLocation, EXTRINSIC, XYZ, DEGREES);
                telemetry.addData("Rot (deg)", "{Roll, Pitch, Heading} = %.0f, %.0f, %.0f", rotation.firstAngle, rotation.secondAngle, rotation.thirdAngle);
            }
            else {
                telemetry.addData("Visible Target", "none");
            }
            telemetry.update();
        }

        // Disable Tracking when we are done;
        targets.deactivate();
    }




    /*============================Autonomous Code================================*/




    public void MoveStraight(double inch, double power) {
        for (int i = 0; i < 4; i++) {AutoDir[i] = 1;}
        int ticks = (int) (inch * DRIVE_FACTOR);
        Move(ticks, power);
    }

    public void Strafe(double inch, double power) {
        AutoDir[0] = AutoDir[3] = -1;
        AutoDir[1] = AutoDir[2] = 1;
        int ticks = (int) (inch * STRAFE_FACTOR);
        Move(ticks, power);
    }

    public void Turn(int degrees, double power) {
        AutoDir[2] = AutoDir[3] = -1;
        AutoDir[0] = AutoDir[1] = 1;
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






}
