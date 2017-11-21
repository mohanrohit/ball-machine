// BallMachineCommand.java

public abstract class BallMachineCommand
{
    // command ids
    public static final int QUIT = 0;
    public static final int SHOOT_BALL = 1;
    public static final int SET_RANGE = 2;

    public BallMachineCommand(int id)
    {
        this.id = id;
    }

    public int id;
}
