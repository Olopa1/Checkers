package components;

import javax.swing.*;

public class Board {
    private JFrame mainFrame;
    private final int[][] boardPlacment;


    public Board(){
        this.mainFrame = new JFrame();
        this.boardPlacment = new int[8][8];
        for(int width = 0; width < 8;width++){
            for(int height = 0; height < 8; height++){
                this.boardPlacment[width][height] = 0;
            }
        }
    }

    public void addPices(Pice pice){

    }

    public void printBoardCosnosle(){
        for(int width = 0; width < 8;width++){
            for(int height = 0; height < 8; height++){
                System.err.print(this.boardPlacment[width][height] = 0);
            }
        }
    }

}
