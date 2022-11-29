package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous
public class AutoMain19888coneANDpark extends RobotMain19888 {
    private boolean done = false;
    public void runOpMode() throws InterruptedException {
        ROBOT_INITIALIZE();
        while (opModeIsActive() && !done) {
            telemetry.addData("Program Started", null);
            telemetry.update();
            double power_value=0.5;
            //Cone
            MoveStraight(29, power_value);
            Turn(45, power_value);
            Thread.sleep(500);
            MoveStraight(9, power_value); //the exact length calculated = 9.09556
            //slideOp(); -> after function is completed
            //claw???(); -> after function is completed
            
            //Park

            done = true;
        }
    }
}
