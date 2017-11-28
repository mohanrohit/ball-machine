// BallMachine.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Arrays;

public class BallMachine
{
    public void showUsage()
    {
        System.out.println("");
        System.out.println("At the '> ' prompt, enter a command:");
        System.out.println("  'shoot' to shoot the next ball");
        System.out.println("  'depth: <depth>' where <depth> is 'deep' or 'box'");
        System.out.println("  'direction: <direction>' where <direction> is 'forehand' or 'backhand'");
        System.out.println("  'angle: <angle>' where <angle> is 'narrow', 'body' or 'wide'");
        System.out.println("  'q' or 'quit' to stop the ball machine");
        System.out.println("\nJust press <enter> to shoot a ball with the current settings.\n");
    }

    public void showSettings()
    {
        System.out.println("Current settings:");
        System.out.println("  Angle: " + _ballMachineParams.angle);
        System.out.println("  Direction: " + _ballMachineParams.direction);
        System.out.println("  Depth: " + _ballMachineParams.depth);
        System.out.println("");
    }

    void start()
    {
        System.out.println("Ball machine is starting.\n");
        System.out.println("Enter 'help' or '?' for help.\n");
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

        if (command.toLowerCase().equals("depth"))
        {
            return new Command(Command.SET_DEPTH, new String[]{param});
        }

        if (command.toLowerCase().equals("angle"))
        {
            return new Command(Command.SET_ANGLE, new String[]{param});
        }

        if (command.toLowerCase().equals("direction"))
        {
            return new Command(Command.SET_DIRECTION, new String[]{param});
        }

        if (command.toLowerCase().equals("help") || command.equals("?"))
        {
            return new Command(Command.HELP, new String[]{param});
        }

        if (command.length() > 0)
        {
            System.out.println(command.toLowerCase() + " is not a valid command. Ball machine will just shoot a ball.");
        }

        // by default, just shoot a ball
        return new Command(Command.SHOOT_BALL, new String[]{});
    }

    double getHorizontalRange()
    {
        double narrowRange = 4.5; // feet
        double bodyRange = 9.0;
        double wideRange = 13.5;

        switch (_ballMachineParams.angle)
        {
        case NARROW: return narrowRange;
        case BODY: return bodyRange;
        case WIDE: return wideRange;
        }

        return narrowRange; // default
    }

    double getVerticalRange()
    {
        double netToOppositeServiceLineDistance = 21; // feet
        double netToOppositeBaselineDistance = 39;

        switch (_ballMachineParams.depth)
        {
        case SERVICEBOX: return netToOppositeServiceLineDistance;
        case OPENCOURT: return netToOppositeBaselineDistance;
        }

        return netToOppositeServiceLineDistance; // default
    }

    void shootBall()
    {
        double baselineToNetDistance = 39; // feet

        double totalHorizontalRange = getHorizontalRange();
        double totalVerticalRange = getVerticalRange();

        // pick a random point between (0 -- totalHorizontalRange, baselineToNetDistance -- baselineToNetDistance + totalVerticalRange)
        double horizontalRange = Math.random() * totalHorizontalRange;
        double verticalRange = baselineToNetDistance + Math.random() * totalVerticalRange;

        System.out.println("Shooting ball; landed at (" + horizontalRange + ", " + verticalRange + ")");
    }

    void showHelp()
    {
        showUsage();
        showSettings();
    }

    void setDepth(String depth)
    {
        depth = depth.toUpperCase();

        boolean depthIsValid = depth.equals("SERVICEBOX") || depth.equals("OPENCOURT");
        if (!depthIsValid)
        {
            System.out.println(depth + " is not an allowed value for depth. Depth is still " + _ballMachineParams.depth + ".");

            return;
        }

        _ballMachineParams.depth = depth.equals("SERVICEBOX") ? BallMachineParams.Depth.SERVICEBOX : BallMachineParams.Depth.OPENCOURT;

        System.out.println("Depth is now " + depth + ".");
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

            case Command.SET_DEPTH:
                setDepth(command.params[0]);
                break;

            case Command.SET_ANGLE:
                setAngle(command.params[0]);
                break;

            case Command.SET_DIRECTION:
                setDirection(command.params[0]);
                break;

            case Command.HELP:
                showHelp();
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
