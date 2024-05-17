package components;

public class Pice {
    private int fieldWidthPosition;
    private int fieldHeightPosition;
    private char collor;


    public Pice(int startPositionWidth, int startPositionHeight,char collor){
        this.fieldHeightPosition = startPositionHeight;
        this.fieldWidthPosition = startPositionWidth;
        this.collor = collor;
    }

    public Boolean movePices(int moveHeight, int moveWidth){
        int tryMoveWidth = this.fieldWidthPosition + moveWidth;
        int tryMoveHeight = this.fieldHeightPosition + moveHeight;
        if(tryMoveHeight > 7 || tryMoveHeight < 0)
            return false;
        if(tryMoveWidth > 7 || tryMoveHeight < 0)
            return false;
        
        this.fieldHeightPosition = tryMoveHeight;
        this.fieldWidthPosition = tryMoveWidth;
        return true;
    }


}
