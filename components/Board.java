package components;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Board {
    private JFrame mainFrame;
    private final int[][] boardPlacment;
    private ArrayList<JButton> buttons;
    private static final String[] lettersInBoard = {"A","B","C","D","E","F","G","H"};
 
    public Board(){
        this.mainFrame = new JFrame();
        this.boardPlacment = new int[8][8];
        buttons = new ArrayList<JButton>();
        for(int width = 0; width < 8;width++){
            for(int height = 0; height < 8; height++){
                this.boardPlacment[width][height] = 0;
                JButton tempButton = new JButton(Integer.toString(height + 1)+lettersInBoard[width]);
                this.buttons.add(tempButton);
                this.mainFrame.add(tempButton);
            }
        }
        this.mainFrame.setLayout(new GridLayout(8,8));
        this.mainFrame.setSize(1000,1000);
    }

    public void displayBoard(){
        this.mainFrame.setVisible(true);
    }

    public void printBoardCosnosle(){
        for(int width = 0; width < 8;width++){
            for(int height = 0; height < 8; height++){
                System.err.print(this.boardPlacment[width][height] = 0);
            }
            System.out.println("");
        }
    }

}
