public class BallMachineParams
{
    enum Angle
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

    enum Range
    {
        BOX, // the shot lands in the service box
        DEEP // the shot lands deep in the corner
    };

    // changes the calculations of the ranges
    enum PlayStyle
    {
        SINGLES,
        DOUBLES
    };

    enum Court
    {
        DEUCECOURT,
        ADCOURT
    };

    public Angle angle = Angle.NARROW;
    public Direction direction = Direction.FOREHAND;
    public Range range = Range.BOX;
    public PlayStyle playStyle = PlayStyle.SINGLES;
    public Court court = Court.DEUCECOURT;
}
