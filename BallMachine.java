// BallMachine.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

//import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

// The Command class encapsulates one command for the ball machine
class Command
{
    // command ids
    public static final int QUIT = 0;
    public static final int SHOOT_BALL = 1;
    public static final int SET_DEPTH = 2;
    public static final int SET_ZONE = 3;
    public static final int SET_DIRECTION = 4;
    public static final int SET_COURT = 5;
    public static final int SHOW_HELP = 6;
    public static final int SHOW_SETTINGS = 7;
    public static final int SHOW_RANGES = 8;

    public Command(int id, String[] params)
    {
        this.id = id;
        this.params = params;
    }

    public int id;
    public String[] params;
}

// Class to store the parameters for operating the ball machine
class BallMachineParams
{
    enum Zone
    {
        NARROW, // down the service line
        BODY, // to the body -- middle of the box
        WIDE
    };

    enum Direction // will be different for right- vs. left-
    // handed player
    {
        FOREHAND,
        BACKHAND
    };

    enum Depth
    {
        SERVICEBOX, // the shot lands in the service box
        OPENCOURT // the shot lands deep in the open court
    };

    // changes the calculations of the ranges
    enum PlayStyle
    {
        SINGLES,
        DOUBLES
    };

    enum Court
    {
        DEUCE,
        AD
    };

    public Zone zone = Zone.NARROW;
    public Direction direction = Direction.FOREHAND;
    public Depth depth = Depth.SERVICEBOX;
    public PlayStyle playStyle = PlayStyle.SINGLES;
    public Court court = Court.DEUCE;
}

// The ball machine. Assumes singles court dimensions
public class BallMachine
{
    // all the following measurements are in feet
    private static final double BASELINE_TO_NET_DISTANCE = 39;

    private static final double NET_TO_OPPOSITE_SERVICELINE_DISTANCE = 21;
    private static final double NET_TO_OPPOSITE_BASELINE_DISTANCE = 39;

    // widths of the three "zones" in which the ball can land
    private static final double NARROW_WIDTH_MIN = 0;
    private static final double NARROW_WIDTH_MAX = 4.5;
    private static final double BODY_WIDTH_MIN = 4.5;
    private static final double BODY_WIDTH_MAX = 9.0;
    private static final double WIDE_WIDTH_MIN = 9.0;
    private static final double WIDE_WIDTH_MAX = 13.5;

    private BallMachineParams _ballMachineParams = new BallMachineParams();

    private BufferedReader _reader = new BufferedReader(new InputStreamReader(System.in));

    private Map<String, Integer> _commandMap = new HashMap<String, Integer>();

    BallMachine()
    {
        initialize();
    }

    void initializeCommands()
    {
        _commandMap.put("quit", Command.QUIT);
        _commandMap.put("q", Command.QUIT);
        _commandMap.put("shoot", Command.SHOOT_BALL);
        _commandMap.put("depth", Command.SET_DEPTH);
        _commandMap.put("zone", Command.SET_ZONE);
        _commandMap.put("direction", Command.SET_DIRECTION);
        _commandMap.put("court", Command.SET_COURT);
        _commandMap.put("help", Command.SHOW_HELP);
        _commandMap.put("?", Command.SHOW_HELP);
        _commandMap.put("settings", Command.SHOW_SETTINGS);
        _commandMap.put("range", Command.SHOW_RANGES);
    }

    void initialize()
    {
        System.out.println("Ball machine is initializing.");

        initializeCommands();
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
        // read a command and its parameters (if any) from the console...
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

        // ...then return a Command object encapsulating that command.
        String command = result[0].trim();

        if (command.length() == 0)
        {
            command = "shoot";
        }

        if (!_commandMap.containsKey(command))
        {
            System.out.println(command + " is not a valid command. Ball machine will just shoot a ball with current settings.");

            // by default, just shoot a ball
            return new Command(Command.SHOOT_BALL, new String[]{});
        }

        return new Command(_commandMap.get(command), new String[]{param});
    }

    double getMinDepth()
    {
        // the minimum depth is from the location of the ball machine to the
        // net plus, say, half a foot (so the ball at least goes OVER the net)
        double minDepth = BASELINE_TO_NET_DISTANCE + 0.5;

        switch (_ballMachineParams.depth)
        {
        case SERVICEBOX: return minDepth;
        case OPENCOURT: return BASELINE_TO_NET_DISTANCE + NET_TO_OPPOSITE_SERVICELINE_DISTANCE;
        }

        return minDepth; // default to service line
    }

    double getMaxDepth()
    {
        switch (_ballMachineParams.depth)
        {
        case SERVICEBOX: return BASELINE_TO_NET_DISTANCE + NET_TO_OPPOSITE_SERVICELINE_DISTANCE;
        case OPENCOURT: return BASELINE_TO_NET_DISTANCE + NET_TO_OPPOSITE_BASELINE_DISTANCE;
        }

        return BASELINE_TO_NET_DISTANCE + NET_TO_OPPOSITE_SERVICELINE_DISTANCE; // default to service line
    }

    double getMinWidth()
    {
        switch (_ballMachineParams.zone)
        {
        case NARROW: return NARROW_WIDTH_MIN;
        case BODY: return BODY_WIDTH_MIN;
        case WIDE: return WIDE_WIDTH_MIN;
        }

        return NARROW_WIDTH_MIN; // default
    }

    double getMaxWidth()
    {
        switch (_ballMachineParams.zone)
        {
        case NARROW: return NARROW_WIDTH_MAX;
        case BODY: return BODY_WIDTH_MAX;
        case WIDE: return WIDE_WIDTH_MAX;
        }

        return NARROW_WIDTH_MAX; // default
    }

    void shootBall()
    {
        double baselineToNetDistance = 39; // feet

        double minWidth = getMinWidth();
        double maxWidth = getMaxWidth();
        double minDepth = getMinDepth();
        double maxDepth = getMaxDepth();

        // pick a random point between (minWidth -- maxWidth, minDepth -- maxDepth)
        double x = minWidth + Math.random() * (maxWidth - minWidth);
        double y = minDepth + Math.random() * (maxDepth - minDepth);

        // adjust x for the court -- if the ball machine is at (0, 0), then x is
        // positive for the opposite ad court and negative for the opposite deuce
        // court
        x = _ballMachineParams.court == BallMachineParams.Court.AD ? x : -x;

        x = (Math.round(x * 100)) / 100.0;
        y = (Math.round(y * 100)) / 100.0;

        System.out.println("Shooting ball... landed at (" + x + ", " + y + ")");
    }

    public void showUsage()
    {
        System.out.println("");
        System.out.println("At the '> ' prompt, enter a command:");
        System.out.println("  'shoot' to shoot the next ball");
        System.out.println("  'depth: <depth>' where <depth> is 'opencourt' or 'servicebox'");
        System.out.println("  'direction: <direction>' where <direction> is 'forehand' or 'backhand'");
        System.out.println("  'zone: <zone>' where <zone> is 'narrow', 'body' or 'wide'");
        System.out.println("  'court: <court>' where <court> is 'deuce' or 'ad'");
        System.out.println("  'settings' to show the settings for the ball machine");
        System.out.println("  'range' to show the width and depth ranges for the ball");
        System.out.println("  '?' or 'help' to show help");
        System.out.println("  'q' or 'quit' to stop the ball machine");
        System.out.println("\nJust press <enter> to shoot a ball with the current settings.");
        System.out.println("");
    }

    public void showSettings()
    {
        System.out.println("");
        System.out.println("Current settings:");
        System.out.println("  Court: " + _ballMachineParams.court);
        System.out.println("  Zone: " + _ballMachineParams.zone);
        System.out.println("  Direction: " + _ballMachineParams.direction);
        System.out.println("  Depth: " + _ballMachineParams.depth);
        System.out.println("");
    }

    void showBallRanges()
    {
        System.out.print("The ball will land within [");
        System.out.print(getMinWidth() + " -- " + getMaxWidth() + " feet");
        System.out.print("] horizontally and [");
        System.out.print(getMinDepth() + " -- " + getMaxDepth() + " feet");
        System.out.print("] deep in the " + _ballMachineParams.court + " court.");
        System.out.println("");
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

        showBallRanges();
    }

    void setZone(String zone)
    {
        zone = zone.toUpperCase();

        boolean zoneIsValid = zone.equals("NARROW") || zone.equals("BODY") || zone.equals("WIDE");
        if (!zoneIsValid)
        {
            System.out.println(zone + " is not an allowed value for zone. Zone is still " +  _ballMachineParams.zone + ".");

            return;
        }

        _ballMachineParams.zone = zone.equals("NARROW") ?
            BallMachineParams.Zone.NARROW :
            (zone.equals("BODY") ? BallMachineParams.Zone.BODY : BallMachineParams.Zone.WIDE);

        System.out.println("Zone is now " + zone + ".");

        showBallRanges();
    }

    void setDirection(String direction)
    {
        direction = direction.toUpperCase();

        boolean directionIsValid = direction.equals("FOREHAND") || direction.equals("BACKHAND");
        if (!directionIsValid)
        {
            System.out.println(direction + " is not an allowed value for direction. Direction is still " +  _ballMachineParams.direction + ".");

            return;
        }

        _ballMachineParams.direction = direction.equals("FOREHAND") ? BallMachineParams.Direction.FOREHAND : BallMachineParams.Direction.BACKHAND;

        System.out.println("Direction is now " + direction + ".");

        showBallRanges();
    }

    void setCourt(String court)
    {
        court = court.toUpperCase();

        boolean courtIsValid = court.equals("AD") || court.equals("DEUCE");
        if (!courtIsValid)
        {
            System.out.println(court + " is not an allowed value for court. Court is still " +  _ballMachineParams.court + ".");

            return;
        }

        _ballMachineParams.court = court.equals("DEUCE") ? BallMachineParams.Court.DEUCE : BallMachineParams.Court.AD;

        System.out.println("Court is now " + court + ".");

        showBallRanges();
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

            case Command.SET_ZONE:
                setZone(command.params[0]);
                break;

            case Command.SET_DIRECTION:
                setDirection(command.params[0]);
                break;

            case Command.SET_COURT:
                setCourt(command.params[0]);
                break;

            case Command.SHOW_HELP:
                showHelp();
                break;

            case Command.SHOW_SETTINGS:
                showSettings();
                break;

            case Command.SHOW_RANGES:
                showBallRanges();
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
}
