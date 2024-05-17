package components;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Board {
    private JFrame mainFrame;
    private final int[][] boardPlacment;
    private ArrayList<JButton> buttons;
    private static final String[] LETTERS_IN_BOARD = {"A","B","C","D","E","F","G","H"};
    private ArrayList<Pice> whitePices;
    private ArrayList<Pice> blackPices;   

    public Board()throws FileNotFoundException{
        this.mainFrame = new JFrame();
        this.boardPlacment = new int[8][8];
        buttons = new ArrayList<JButton>();
        whitePices = new ArrayList<Pice>();
        blackPices = new ArrayList<Pice>();
        for(int height = 0; height < 8;height++){
            for(int width = 0; width < 8; width++){
                this.boardPlacment[height][width] = 0;
                JButton tempButton = new JButton("Height:" + Integer.toString(height + 1)+ " Width:" + LETTERS_IN_BOARD[width]);
                this.buttons.add(tempButton);
                this.mainFrame.add(tempButton);
            }
        }
        try{
            File innitFile = new File("./config/initBoard.txt");
            Scanner readData = new Scanner(innitFile);
            String readLine;
            String currPices = "";
            while(readData.hasNextLine()){
                readLine = readData.nextLine();
                if(readLine.compareTo("white:") == 0){
                    currPices = readLine;
                    continue;
                }
                else if(readLine.compareTo("black:") == 0){
                    currPices = readLine;
                    continue;
                }
                else{
                    if(currPices.compareTo("white:") == 0){
                        String temp[] = readLine.split(";");
                        for(int iterator = 0; iterator < temp.length;iterator ++){
                            String numbers[] = temp[iterator].split(",");
                            int height = Integer.parseInt(numbers[0]);
                            int width = Integer.parseInt(numbers[1]);
                            whitePices.add(new Pice(width, height, 'w'));
                        }
                    }
                    else if(currPices.compareTo("black:") == 0){
                        String temp[] = readLine.split(";");
                        for(int iterator = 0; iterator < temp.length;iterator ++){
                            String numbers[] = temp[iterator].split(",");
                            int height = Integer.parseInt(numbers[0]);
                            int width = Integer.parseInt(numbers[1]);
                            whitePices.add(new Pice(width, height, 'b'));
                        }
                    }
                    else{
                        //handle it later
                        System.out.println("Oopsie daisy something went wrong...");
                    }
                }
                
            }
            readData.close();

        }catch(FileNotFoundException e){
            //Also this must be handled
            throw e;
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
