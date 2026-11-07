package StarterBot;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Autonomous(name = "BAuto_ShootParkS", group = "StarterBot")
public class BAuto_ShootParkS extends LinearOpMode {
    private DcMotor leftDrive = null;
    private DcMotor rightDrive = null;
    private DcMotorEx intake = null;
    private DcMotorEx launcher = null;
    private CRServo windmillServo = null;
    private CRServo leftIntakeServo = null;
    private CRServo rightIntakeServo = null;

    public final int LAUNCHER_TARGET_VELOCITY = 1000;
    public final int LAUNCHER_MIN_VELOCITY = 900;
    double intakePower;
    double leftDrivePower = 0;
    double rightDrivePower = 0;
    final double STOP_SPEED = 0;
    final double ticksPerRev = ((1+(46/17)) * (1+(46/11)) * 28);
    final double inchesPerRev = 11.873736; //96 * pi (mm -> inches)
    final double ticksPerInch = ticksPerRev/inchesPerRev;
    final double ninetyDegrees = (17 * 3.14159) /4;

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
        if (opModeIsActive()) {
            //Use sleep only to give the robot time to stabilize
            moveForward(12);
            sleep(250);
            shootAll();
            moveForward(84);
            rotateRight(90);
            moveForward(-48);
        }


    }
    public void moveForward(int inches){
        //Reset encoders so we can use relative units
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int moveInches = Math.toIntExact(Math.round(ticksPerInch * inches));

        leftDrive.setTargetPosition(moveInches);
        rightDrive.setTargetPosition(moveInches);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDrive.setPower(.5);
        rightDrive.setPower(.5);
        while (opModeIsActive() && (leftDrive.isBusy() && rightDrive.isBusy())){
            telemetry.addData("Target Ticks:", moveInches);
            telemetry.addData("Left Current", leftDrive.getCurrentPosition());
            telemetry.addData("Right Current", rightDrive.getCurrentPosition());
            telemetry.update();
        }

        //Stop the motors after breaking out of the while() loop
        leftDrive.setPower(STOP_SPEED);
        rightDrive.setPower(STOP_SPEED);
    }
    public void rotateRight(int moveDegrees){
        leftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int degreesTurned = (Math.toIntExact(Math.round(ninetyDegrees/90) * moveDegrees));

        leftDrive.setTargetPosition(degreesTurned);
        rightDrive.setTargetPosition(-degreesTurned);

        leftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftDrive.setPower(.5);
        rightDrive.setPower(.5);
        while (opModeIsActive() && (leftDrive.isBusy() && rightDrive.isBusy())) {
            telemetry.addData("Target Ticks:", degreesTurned);
            telemetry.addData("Left Current", leftDrive.getCurrentPosition());
            telemetry.addData("Right Current", rightDrive.getCurrentPosition());
            telemetry.update();
        }

        leftDrive.setPower(STOP_SPEED);
        rightDrive.setPower(STOP_SPEED);
    }
    public void shootAll() {
        launcher.setVelocity(LAUNCHER_TARGET_VELOCITY);
        while(launcher.getVelocity() < LAUNCHER_MIN_VELOCITY){
            telemetry.addData("Launcher Velocity:", launcher.getVelocity());
        }
        windmillServo.setPower(1);
        intakePower += 0.5;
        sleep(1000);
        windmillServo.setPower(0);

    }

}

