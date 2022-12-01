package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoMain19888BLUEconePark extends RobotMain19888 {
    private boolean done = false;
    public void runOpMode() throws InterruptedException {
        ROBOT_INITIALIZE();
        while (opModeIsActive() && !done) {
            telemetry.addData("Program Started", null);
            telemetry.update();
            double power_value=0.5;
            //Cone
            MoveStraight(29, power_value);
            Turn(-45, power_value);
            Thread.sleep(500);
            MoveStraight(9, power_value); //the exact length calculated = 9.09556
            SlideOP(1, 0.7);
            autoClaw(1);
            //Park
            Turn(45, power_value);
            Thread.sleep(500);
            MoveStraight(24, power_value);
            Turn(90, power_value);
            Thread.sleep(500);
            MoveStraight(29, power_value); //might need to adjust to 28 if robot crashes to the walls

            //if strafe function works this code can be used b/c faster & less complicated
//            //Park
//            Turn(45, power_value);
//            Thread.sleep(500);
//            MoveStraight(24, power_value);
//            Strafe(29, power_value);

            done = true;
        }

        if (done){
            telemetry.addData("###############", null);
            telemetry.addData("Mission CMPL", null);
            telemetry.addData("###############", null);
        }
    }
}
