// Command.java

public class Command
{
    // command ids
    public static final int QUIT = 0;
    public static final int SHOOT_BALL = 1;
    public static final int SET_DEPTH = 2;
    public static final int SET_ANGLE = 3;
    public static final int SET_DIRECTION = 4;
    public static final int HELP = 5;

    public Command(int id, String[] params)
    {
        this.id = id;
        this.params = params;
    }

    public int id;
    public String[] params;
}
