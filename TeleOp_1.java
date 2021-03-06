package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

/**
 * Created by Ramroids 10318 on 12/26/2016.
 */

@TeleOp(name = "TeleOp")
public class TeleOp_1 extends LinearOpMode
{
    private DcMotor front_left;
    private DcMotor back_left;
    private DcMotor front_right;
    private DcMotor back_right;

    private DcMotor collector;

    private DcMotor shooter;

    private DcMotor lifter;
    private DcMotor lifter2;

    @Override
    public void runOpMode() throws InterruptedException
    {
        //INITIALIZE MOTORS AND THINGS

        front_left = hardwareMap.dcMotor.get("front_left");
        back_left = hardwareMap.dcMotor.get("back_left");
        front_right = hardwareMap.dcMotor.get("front_right");
        back_right = hardwareMap.dcMotor.get("back_right");

        collector = hardwareMap.dcMotor.get("collector");

        shooter = hardwareMap.dcMotor.get("shooter");

        lifter = hardwareMap.dcMotor.get("lifter");
        lifter2 = hardwareMap.dcMotor.get("lifter2");

        front_left.setDirection(DcMotor.Direction.REVERSE);
        back_left.setDirection(DcMotor.Direction.REVERSE);
        collector.setDirection(DcMotor.Direction.REVERSE);

        //establish variable for colletor toggle and drive mode
        boolean toggle = false;
        boolean drivemode = true;

        waitForStart();

        while(opModeIsActive()) {
            //RUN CODE

            telemetry.addData("Status:", "TeleOp");
            telemetry.update();

            front_left.setPower(-gamepad1.left_stick_y);
            back_left.setPower(-gamepad1.left_stick_y);

            front_right.setPower(-gamepad1.right_stick_y);
            back_right.setPower(-gamepad1.right_stick_y);

            //left and right trigger
            double mCalib = 0.5;
            front_left.setPower((gamepad1.left_trigger * mCalib) + (-gamepad1.left_stick_y));
            back_left.setPower((-gamepad1.left_trigger) + (-gamepad1.left_stick_y));
            front_right.setPower((-gamepad1.left_trigger * mCalib) + (-gamepad1.right_stick_y));
            back_right.setPower((gamepad1.left_trigger) + (-gamepad1.right_stick_y));

            front_left.setPower((-gamepad1.right_trigger * mCalib) + (-gamepad1.left_stick_y));
            back_left.setPower((gamepad1.right_trigger) + (-gamepad1.left_stick_y));
            front_right.setPower((gamepad1.right_trigger * mCalib) + (-gamepad1.right_stick_y));
            back_right.setPower((-gamepad1.right_trigger) + (-gamepad1.right_stick_y));

            if (gamepad1.right_bumper) {
                double power = 0.5;
                front_left.setPower(power);
                back_left.setPower(power);
                front_right.setPower(-power);
                back_right.setPower(-power);
            }
            if (gamepad1.left_bumper) {
                double power = -0.5;
                front_left.setPower(power);
                back_left.setPower(power);
                front_right.setPower(-power);
                back_right.setPower(-power);
            }

            //collector toggle
            if (gamepad1.a) {
                //toggle state of boolean value
                toggle = !toggle;
            }
            //turns on collector if value is true
            if (toggle) {
                collector.setPower(1.0);
            } else {
                collector.setPower(0);
            }

            //spits out ball
            while (gamepad1.b) {
                collector.setPower(-1.0);
            }

            while (gamepad1.x) {
                shooter.setPower(1.0);
            }
            while (gamepad1.y) {
                shooter.setPower(-1.0);
            }
            shooter.setPower(0.0);

            //Cap Ball Lifter
            if (gamepad1.dpad_up || gamepad1.dpad_down) {
                while (gamepad1.dpad_up) {
                    lifterPower(-1);
                }
                while (gamepad1.dpad_down) {
                    lifterPower(.5);
                }
            }
            lifterPower(0.0);
        }
    }

    public void lifterPower(double power)
    {
        lifter.setPower(power);
        lifter2.setPower(power);
    }
}
