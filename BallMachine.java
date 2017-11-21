// BallMachine.java
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

import java.util.Arrays;

public class BallMachine
{
    /*
    public void showUsage()
    {
        System.out.println("Usage:");
        System.out.println("At the '> ' prompt, enter a command.");
        System.out.println("Commands can be:");
        System.out.println("  'shoot' to shoot the next ball");
        System.out.println("  'range:<range>' where 'range' is 'deep' or 'box'");
        System.out.println("  'direction:<direction>' where 'direction' is 'forehand' or 'backhand'");
        System.out.println("  'range:<range>' where 'range' is 'deep' or 'box'");
        System.out.println("  'direction:<direction>' where 'direction' is 'forehand' or 'backhand'");
        System.out.println("  Court: " + params.court);
    }
    */

    public void showStatus(BallMachineParams params)
    {
        System.out.println("The ball machine is now running.");
        System.out.println("Params:");
        System.out.println("  Angle: " + params.angle);
        System.out.println("  Direction: " + params.direction);
        System.out.println("  Range: " + params.range);
        System.out.println("  Play style: " + params.playStyle);
        System.out.println("  Court: " + params.court);
    }

    void start()
    {
        System.out.println("Ball machine is starting.");
    }

    void stop()
    {
        System.out.println("Ball machine is stopping.");
    }

    public BallMachineCommand getCommand()
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
        String param = result.length > 0 ? result[1].trim() : "";

        String command = result[0].trim();
        if (command.toLowerCase().equals("quit"))
        {
            return new QuitCommand();
        }

        if (command.toLowerCase().equals("shoot"))
        {
            return new ShootBallCommand();
        }

        if (command.toLowerCase().equals("range"))
        {
            return new SetRangeCommand(param);
        }

        // by default, just shoot a ball
        return new ShootBallCommand();
    }

    void shootBall()
    {
        System.out.println("Shooting ball");
    }

    void setRange(String range)
    {
        range = range.toLowerCase();

        String[] allowed = {"box", "deep"};

        if (!Arrays.asList(allowed).contains(range))
        {
            System.out.println("'" + range + "' is not an allowed value for range. Range will remain unchanged.");
            //showUsage();
            return;
        }

        _ballMachineParams.range = range.equals("box") ? BallMachineParams.Range.BOX : BallMachineParams.Range.DEEP;

        System.out.println("Range is now '" + range + "'");
    }

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
