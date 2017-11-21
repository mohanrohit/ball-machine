// SetRangeCommand.java

public class SetRangeCommand extends BallMachineCommand
{
    public SetRangeCommand(String range)
    {
        super(BallMachineCommand.SET_RANGE);

        this.range = range;
    }

    public String range;
}
