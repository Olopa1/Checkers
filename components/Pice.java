package components;

public class Pice {
    private Point position;
    private char collor;
    private PiceType type;

    public Pice(int startPositionWidth, int startPositionHeight,char collor){
        this.position = new Point(startPositionWidth, startPositionHeight);
        this.collor = collor;
    }

    public PiceType getType(){
        return type;
    }

    public Point getPosition(){
        return position;
    }

    public char getCollor(){
        return collor;
    }

    public void setPosition(Point newPoint){
        this.position = newPoint;
    }

    public void setType(PiceType type){
        this.type = type;
    }

    public void movePices(Point newPoint){
        this.position = newPoint;
    }


}
