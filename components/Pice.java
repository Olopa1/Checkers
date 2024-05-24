package components;

public class Pice {
    private Point position;
    private char collor;


    public Pice(int startPositionWidth, int startPositionHeight,char collor){
        this.position = new Point(startPositionWidth, startPositionHeight);
        this.collor = collor;
    }

    public Point getPosition(){
        return position;
    }

    public Boolean movePices(int moveHeight, int moveWidth){
        int tryMoveWidth = this.position.getWidth() + moveWidth;
        int tryMoveHeight = this.position.getHeight() + moveHeight;
        if(tryMoveHeight > 7 || tryMoveHeight < 0)
            return false;
        if(tryMoveWidth > 7 || tryMoveHeight < 0)
            return false;
        
        this.position.changePoint(tryMoveWidth, tryMoveHeight);
        return true;
    }


}
