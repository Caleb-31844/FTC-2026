package StarterBot;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Autonomous(name = "BAuto_Park", group = "StarterBot")
public class BAuto_ParkS extends LinearOpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotorEx intake = null;
    private DcMotorEx launcher = null;
    private CRServo windmillServo = null;
    private CRServo leftIntakeServo = null;
    private CRServo rightIntakeServo = null;

    double leftDrivePower = 0;
    double rightDrivePower = 0;
    final double STOP_SPEED = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        //Hardware map
        leftDrive = hardwareMap.get(DcMotor.class, "ld");
        rightDrive = hardwareMap.get(DcMotor.class, "rd");
        intake = hardwareMap.get(DcMotorEx.class, "intake");
        launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        windmillServo = hardwareMap.get(CRServo.class, "windmill");
        leftIntakeServo = hardwareMap.get(CRServo.class, "l_servo");
        rightIntakeServo = hardwareMap.get(CRServo.class, "r_servo");

        //Motor direction + brakes
        leftDrive.setDirection(DcMotor.Direction.FORWARD);
        rightDrive.setDirection(DcMotor.Direction.REVERSE);
        intake.setDirection(DcMotorSimple.Direction.FORWARD);

        leftDrive.setZeroPowerBehavior(BRAKE);
        rightDrive.setZeroPowerBehavior(BRAKE);
        intake.setZeroPowerBehavior(BRAKE);

        //DcMotorEX stuff
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        launcher.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

        //CRServo power + direction
        leftIntakeServo.setPower(0);
        rightIntakeServo.setPower(0);
        windmillServo.setPower(0);

        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
        windmillServo.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addLine("All Set :)");

        waitForStart();
        sleep(1000);

        moveForward(0.5);
        sleep(500);
        moveForward(STOP_SPEED);
        sleep(250);
        moveForward(-0.5);
        sleep(500);
        moveForward(STOP_SPEED);

    }
    public void moveForward(double speed){
        leftDrivePower = speed;
        rightDrivePower = speed;
    }
    public void rotateRight(double speed){
        leftDrivePower = speed;
        rightDrivePower = -speed;
    }

}
