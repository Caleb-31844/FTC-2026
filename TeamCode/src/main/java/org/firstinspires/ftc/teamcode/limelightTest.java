package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class limelightTest extends OpMode {

    private Limelight3A limelight3A;

    private double CameraHeightIN = 00; //find out
    private double CameraAngle = 00; //find out
    private double GoalHeight = 00; //find out
    private double DISTANCE = 0; //keep as is.
    @Override
    public void init() {
        limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
        limelight3A.pipelineSwitch(8); //configure to pipeline 8
    }

    @Override
    public void start() {
        limelight3A.start(); //Move to init if it causes delay
    }

    @Override
    public void loop() {
        LLResult llResult = limelight3A.getLatestResult();
        if (llResult != null && llResult.isValid()); {
            telemetry.addData("Target X offest", llResult.getTx());
            telemetry.addData("Target Y offset", llResult.getTy());
            telemetry.addData("Target Area offset", llResult.getTa());
            //HFOV is 54.5 degrees; VFOV is 42 degrees
            //27.25 and 21
        }
    }

    public double getDISTANCE(double ty){
        double angleToTarget = CameraAngle + ty;
        double heightDifference = GoalHeight - CameraHeightIN;


        return heightDifference / Math.tan(Math.toRadians(angleToTarget));
    }
}
