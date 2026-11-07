//package StarterBot;
//
//import com.qualcomm.robotcore.eventloop.opmode.OpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.DcMotorSimple;
//
//@TeleOp
//public class TeleopStarter extends OpMode {
//    TankDrive drive = new TankDrive();
//    double throttle, spin;
//    private DcMotor launcher;
//
//    final double LAUNCH_POWER = 1000;
//
//    public void init(){
//        drive.init(hardwareMap);
//        launcher = hardwareMap.get(DcMotor.class,"Launcher");
//
//        launcher.setDirection(DcMotorSimple.Direction.REVERSE);
//
//        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//
//    }
//
//    public void loop(){
//        throttle = -gamepad1.left_stick_y;
//        spin = gamepad1.right_stick_x;
//        drive.drive(throttle, spin);
//
//        launcher.setPower(LAUNCH_POWER);
//    }
//
//}
