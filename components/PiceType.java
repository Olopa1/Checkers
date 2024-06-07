package components;

public enum PiceType {
    CHECKER(0),
    QUEEN(1);

    private int value;
    
    private PiceType(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}
