// BallMachine.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Arrays;

public class BallMachine
{
    public void showUsage()
    {
        System.out.println("Usage:\n");
        System.out.println("At the '> ' prompt, enter a command:");
        System.out.println("  'shoot' to shoot the next ball");
        System.out.println("  'range:<range>' where 'range' is 'deep' or 'box'");
        System.out.println("  'direction:<direction>' where 'direction' is 'forehand' or 'backhand'");
        System.out.println("  'angle:<angle>' where 'angle' can be 'narrow', 'body' or 'long'");
        System.out.println("\nJust press <enter> to shoot a ball with the current settings.\n");

        showSettings();
    }

    public void showSettings()
    {
        System.out.println("Current settings:");
        System.out.println("  Angle: " + _ballMachineParams.angle);
        System.out.println("  Direction: " + _ballMachineParams.direction);
        System.out.println("  Range: " + _ballMachineParams.range);
    }

    void start()
    {
        System.out.println("Ball machine is starting.\n");

        showUsage();
    }

    void stop()
    {
        System.out.println("Ball machine is stopping.");
    }

    public Command getCommand()
    {
        System.out.print("> ");

        String input = "";
        try
        {
            input = _reader.readLine();
        }
        catch(IOException e)
        {
        }

        String[] result = input.split(":");
        String param = result.length > 1 ? result[1].trim() : "";

        String command = result[0].trim();

        if (command.toLowerCase().equals("quit") || command.toLowerCase().equals("q"))
        {
            return new Command(Command.QUIT, new String[]{});
        }

        if (command.toLowerCase().equals("shoot"))
        {
            return new Command(Command.SHOOT_BALL, new String[]{});
        }

        if (command.toLowerCase().equals("range"))
        {
            return new Command(Command.SET_RANGE, new String[]{param});
        }

        if (command.toLowerCase().equals("angle"))
        {
            return new Command(Command.SET_ANGLE, new String[]{param});
        }

        if (command.toLowerCase().equals("direction"))
        {
            return new Command(Command.SET_DIRECTION, new String[]{param});
        }

        if (command.length() > 0)
        {
            System.out.println(command.toLowerCase() + " is not a valid command. Ball machine will just shoot a ball.");
        }

        // by default, just shoot a ball
        return new Command(Command.SHOOT_BALL, new String[]{});
    }

    void shootBall()
    {
        System.out.println("Shooting ball");
    }

    void setRange(String range)
    {
        range = range.toUpperCase();

        boolean rangeIsValid = range.equals("BOX") || range.equals("DEEP");
        if (!rangeIsValid)
        {
            System.out.println(range + " is not an allowed value for range. Range is still " + _ballMachineParams.range + ".");

            return;
        }

        _ballMachineParams.range = range.equals("BOX") ? BallMachineParams.Range.BOX : BallMachineParams.Range.DEEP;

        System.out.println("Range is now " + range + ".");
    }

    void setAngle(String angle)
    {
        angle = angle.toUpperCase();

        boolean angleIsValid = angle.equals("NARROW") || angle.equals("BODY") || angle.equals("WIDE");
        if (!angleIsValid)
        {
            System.out.println(angle + " is not an allowed value for angle. Angle is still " +  _ballMachineParams.angle + ".");

            return;
        }

        _ballMachineParams.angle = angle.equals("NARROW") ?
            BallMachineParams.Angle.NARROW :
            (angle.equals("BODY") ? BallMachineParams.Angle.BODY : BallMachineParams.Angle.WIDE);

        System.out.println("Angle is now " + angle + ".");
    }

    void setDirection(String direction)
    {
        direction = direction.toUpperCase();

        String[] allowed = {"FOREHAND", "BACKHAND"};

        boolean directionIsValid = direction.equals("FOREHAND") || direction.equals("BACKHAND");
        if (!directionIsValid)
        {
            System.out.println(direction + " is not an allowed value for direction. Direction is still " +  _ballMachineParams.direction + ".");

            return;
        }

        _ballMachineParams.direction = direction.equals("FOREHAND") ? BallMachineParams.Direction.FOREHAND : BallMachineParams.Direction.BACKHAND;

        System.out.println("Direction is now " + direction + ".");
    }

    /*
    public void run()
    {
        start();

        while (true)
        {
            BallMachineCommand command = getCommand();

            if (command.id == BallMachineCommand.QUIT)
            {
                break;
            }

            switch (command.id)
            {
            case BallMachineCommand.SHOOT_BALL:
                shootBall();
                break;

            case BallMachineCommand.SET_RANGE:
                setRange(((SetRangeCommand)command).range);
                break;

            case BallMachineCommand.SET_ANGLE:
                setAngle(((SetAngleCommand)command).angle);
                break;

            case BallMachineCommand.SET_DIRECTION:
                setAngle(((SetDirectionCommand)command).direction);
                break;
            }
        }

        stop();
    }
    */

    public void run()
    {
        start();

        while (true)
        {
            Command command = getCommand();

            if (command.id == Command.QUIT)
            {
                break;
            }

            switch (command.id)
            {
            case Command.SHOOT_BALL:
                shootBall();
                break;

            case Command.SET_RANGE:
                setRange(command.params[0]);
                break;

            case Command.SET_ANGLE:
                setAngle(command.params[0]);
                break;

            case Command.SET_DIRECTION:
                setDirection(command.params[0]);
                break;
            }
        }

        stop();
    }

    public static void main(String[] args)
    {
        BallMachine ballMachine = new BallMachine();
        ballMachine.run();
    }

    private BallMachineParams _ballMachineParams = new BallMachineParams();

    private BufferedReader _reader = new BufferedReader(new InputStreamReader(System.in));
}
