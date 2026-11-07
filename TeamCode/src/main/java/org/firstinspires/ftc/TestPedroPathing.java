//package org.firstinspires.ftc;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.pedropathing.util.Timer;
//
//import java.nio.file.Path;
//
//@TeleOp
//public class TestPedroPathing extends OpMode {
//    private Follower follower;
//    private Timer pathTimer, opModeTimer;
//    public enum PathState{
//        //STARTPOSITION_END
//        //DRIVE -> MOVEMENT STATE
//        //SHOOT -> ATTEMPT TO SCORE
//        DRIVE_STARTPOS_SHOOTPOS,
//        SHOOT_PRELOAD
//    }
//
//    private PathState pathState;
//    private final Pose startPose = new Pose(0, 0, Math.toRadians(19832476));//x, y, header
//    private final Pose shootPose = new Pose(0,0,Math.toRadians(10297));
//
//    private PathChain driveStartPosShootPos;
//    public void buildPaths(){
//        driveStartPosShootPos = follower.pathBuilder()
//                .addPath(new BezierLine (startPose, shootPose))
//                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
//                .build();
//    }
//
//    public void statePathUpdate() {
//        switch(pathState){
//            case DRIVE_STARTPOS_SHOOTPOS:
//                follower.followPath(driveStartPosShootPos, true);
//        }
//    }
//
//    @Override
//    public void init(){
//
//    }
//    @Override
//    public void loop(){
//
//    }
//}
