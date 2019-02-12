package org.firstinspires.ftc.teamcode;

//UP!!
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

@Autonomous(name = "Pullup Only")

public class autoPullupOnly extends JackalopeAutoMode {
    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private Servo flipper = null;
    private boolean read = false;
    private ColorSensor CBL;
    private boolean gripped = false;
    private boolean lifted = false;
    private double short_drive_x;
    private boolean modeBool = false;
    private double power;
    private int distance;
    private double short_drive_y;
    private ElapsedTime clock = new ElapsedTime();
    private double startTime = 0.0;
    double scale;
    double drive_scale;

    //for encoders:
//    static final double     TICKS    = 537.6 ;
    static final int     TICKS    = 1120 ;

    double frontLeft;
    double frontRight;
    double backRight;
    double backLeft;

    @Override
    public void strafe(boolean strafe) {
        FR.setDirection(strafe ? DcMotor.Direction.FORWARD : DcMotor.Direction.REVERSE);
        FL.setDirection(strafe ? DcMotor.Direction.FORWARD : DcMotor.Direction.FORWARD);
        BR.setDirection(strafe ? DcMotor.Direction.REVERSE : DcMotor.Direction.REVERSE);
        BL.setDirection(strafe ? DcMotor.Direction.REVERSE : DcMotor.Direction.FORWARD);
        FR.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        FL.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        BR.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        BL.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        pullup.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
    }

    /*
     * Code to run ONCE when the driver hits INIT
     */
    public void runOpMode() throws InterruptedException {
        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        FR = hardwareMap.get(DcMotor.class, "FR");
        FL = hardwareMap.get(DcMotor.class, "FL");
        BR = hardwareMap.get(DcMotor.class, "BR");
        BL = hardwareMap.get(DcMotor.class, "BL");
        pullup = hardwareMap.get(DcMotor.class, "pullup");

        // Set the initial directions of the motors
        FL.setDirection(DcMotor.Direction.REVERSE);
        BL.setDirection(DcMotor.Direction.REVERSE);
        BR.setDirection(DcMotor.Direction.REVERSE);
        FR.setDirection(DcMotor.Direction.REVERSE);
        pullup.setDirection(DcMotorSimple.Direction.FORWARD);

        // Set the behaviour when motors' power is set to zero -- whether to brake
        FR.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        FL.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        BR.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        BL.setZeroPowerBehavior(ZERO_POWER_BEHAVIOR);
        pullup.setDirection(DcMotorSimple.Direction.FORWARD);

//        //encoders modes:
//        pullup.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
//        pullup.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
//        pullup.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        pullup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //encoders:

        //set target position:

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "Resetting Encoders");    //
        telemetry.update();

        pullup.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        pullup.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        // Tell the driver that initialization is complete.
        telemetry.addData("Status", "Initialized");


        // Reset the timer to zero.
        runtime.reset();

        // Wait for the start button to be pressed on the phone.
        waitForStart();

        // Loop until the op mode is stopped. changes
        telemetry.addData("read", read);
        pullup.getCurrentPosition();
//        pullup.setTargetPosition(TICKS / 8);

//            while (pullup.isBusy()) {
//                pullup.setPower(-7);
//            }

        if (5*TICKS > pullup.getCurrentPosition()) {
            pullup.setPower(-.9);
            telemetry.addData("current position", pullup.getCurrentPosition());

        }

//        else if (5*TICKS < pullup.getCurrentPosition()) {
//            pullup.setPower(.9);
//            telemetry.addData("current position", pullup.getCurrentPosition());
//        }

        else {
            pullup.setPower(0);
        }

//        else {
//            pullup.setPower(0);
//        }



//            while (pullup.getCurrentPosition() > TICKS/8) {
//             pullup.setPower(.9);
//             telemetry.addData("encoder value", pullup.getCurrentPosition());
//            }
//
//            while (pullup.getCurrentPosition() == TICKS/8) {
//                pullup.setPower(0);
//                telemetry.addData("encoder value", pullup.getCurrentPosition());
//            }





            // add it running here

//            pullup.setPower(-.7);
//            telemetry.addData("power", pullup.getPower());
//            telemetry.update();
//            sleep(8700);
//            pullup.setPower(0);
//            telemetry.addData("power", pullup.getPower());
//            telemetry.update();

//            goLeft();
//            sleep(1500);
//            goStop();

            // Send the power variables to the driver.
            telemetry.addData("FR", frontRight);
            telemetry.addData("FL", frontLeft);
            telemetry.addData("BR", backRight);
            telemetry.addData("BL", backLeft);

            // Set the powers of the motors to the power variables.
            // Update the displayed values on the driver phone.
            telemetry.update();
            idle();
        }


    /*
     * Scales a value to the appropriate range--used for calculating motor powers/servo positions.
     * For instance, you could use this to map 5 in the range (0,10) to 0.25 in the range (0,0.5)
     */


    public double map(double x, double in_min, double in_max, double out_min, double out_max) {
        return (x - in_min) * (out_max - out_min) / (in_max - in_min) + out_min;
    }

}

