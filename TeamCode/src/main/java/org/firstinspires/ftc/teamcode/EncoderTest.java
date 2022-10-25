package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

@Autonomous
public class EncoderTest extends LinearOpMode {
    private DcMotor motor;
    private boolean done = false;

    public void runOpMode() {
        motor = hardwareMap.dcMotor.get("motor");
        motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();

        while (opModeIsActive() && !done) {
            telemetry.addData(">", "Program Started");
            telemetry.update();

            motor.setPower(0.2);
        }
    }
}
