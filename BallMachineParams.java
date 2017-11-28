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
        DEUCECOURT,
        ADCOURT
    };

    public Angle angle = Angle.NARROW;
    public Direction direction = Direction.FOREHAND;
    public Depth depth = Depth.SERVICEBOX;
    public PlayStyle playStyle = PlayStyle.SINGLES;
    public Court court = Court.DEUCECOURT;
}
