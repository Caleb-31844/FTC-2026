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
    final double ticksPerRev = ((1+(46/17)) * (1+(46/11)) * 28);
    final double inchesPerRev = 11.873736; //96 * pi (mm -> inches)
    final double ticksPerInch = ticksPerRev/inchesPerRev;
    final double TRACK_WIDTH = 16*(1.3); //1.3 is correction based on experiment

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

        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //DcMotorEX stuff
        launcher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //CRServo power + direction
        leftIntakeServo.setPower(0);
        rightIntakeServo.setPower(0);
        windmillServo.setPower(0);

        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);
        windmillServo.setDirection(DcMotorSimple.Direction.REVERSE);
        telemetry.addLine("All Set :)");

        waitForStart();

        moveForward(24);
        sleep(500);
        rotateRight(360);

    }
    public void moveForward(double inches) {

        int moveInches = (int) Math.round(ticksPerInch * inches);
        leftDrive.setTargetPosition(leftDrive.getCurrentPosition() + moveInches);
        rightDrive.setTargetPosition(rightDrive.getCurrentPosition() + moveInches);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(0.5);
        rightDrive.setPower(0.5);

        while (opModeIsActive() &&
                (leftDrive.isBusy() || rightDrive.isBusy())) {

            telemetry.addData("Left Position", leftDrive.getCurrentPosition());
            telemetry.addData("Right Position", rightDrive.getCurrentPosition());
            telemetry.update();
        }

        leftDrive.setPower(0);
        rightDrive.setPower(0);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void rotateRight(int moveDegrees) {
        double turningDistance = Math.PI * TRACK_WIDTH * moveDegrees / 360.0;
        int degreesTurned = (int) Math.round(ticksPerInch * turningDistance);

        leftDrive.setTargetPosition(leftDrive.getCurrentPosition() + degreesTurned);
        rightDrive.setTargetPosition(rightDrive.getCurrentPosition() - degreesTurned);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(0.5);
        rightDrive.setPower(0.5);

        while (opModeIsActive() &&
                (leftDrive.isBusy() || rightDrive.isBusy())) {
        }

        leftDrive.setPower(0);
        rightDrive.setPower(0);

        leftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

}
