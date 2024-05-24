package components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

public class Board {
    private JFrame mainFrame;
    private final int[][] boardPlacment;
    private TreeMap<Point,JButton> buttons;
    private static final String[] LETTERS_IN_BOARD = {"A","B","C","D","E","F","G","H"};
    private TreeMap<Point,Pice> whitePices;
    private TreeMap<Point,Pice> blackPices;   

    public JFrame getBoardFrame(){
        return this.mainFrame;
    }

    public Board()throws FileNotFoundException{
        this.mainFrame = new JFrame();
        this.boardPlacment = new int[8][8];
        buttons = new TreeMap<>();
        whitePices = new TreeMap<>();
        blackPices = new TreeMap<>();
        for(int height = 0; height < 8;height++){
            for(int width = 0; width < 8; width++){
                this.boardPlacment[height][width] = 0;
                JButton tempButton = new JButton("Height:" + Integer.toString(height + 1)+ " Width:" + LETTERS_IN_BOARD[width]);
                Point tempPoint = new Point(width, height);
                this.buttons.put(tempPoint,tempButton);
                this.buttons.get(tempPoint).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        JButton tempButton;
                        Point currPoint;
                        for(Point key : buttons.keySet()){
                            if(this.equals(buttons.get(key))){
                                tempButton = this;

                            }
                        }
                    }
                });
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
                            Point tempPoint = new Point(width, height);
                            whitePices.put(tempPoint,new Pice(width, height, 'w'));
                            System.out.println("szer:" +width + " wys:" + height);
                            //this.buttons.get(tempPoint).setBackground(Color.WHITE);
                            //this.buttons.get(tempPoint).setOpaque(true);
                        } 
                    }
                    else if(currPices.compareTo("black:") == 0){
                        String temp[] = readLine.split(";");
                        for(int iterator = 0; iterator < temp.length;iterator ++){
                            String numbers[] = temp[iterator].split(",");
                            int height = Integer.parseInt(numbers[0]);
                            int width = Integer.parseInt(numbers[1]);
                            Point tempPoint = new Point(width, height);
                            blackPices.put(tempPoint,new Pice(width, height, 'b'));
                            System.out.println("szer:" +width + " wys:" + height);
                            //this.buttons.get(tempPoint).setBackground(Color.BLACK);
                            //this.buttons.get(tempPoint).setOpaque(true);
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

    public void drawPices(){
        for(Point key : this.buttons.keySet()){
            if(whitePices.containsKey(key)){
                this.buttons.get(key).setBackground(Color.WHITE);
                this.buttons.get(key).setOpaque(true);
                //System.out.println(key);
            }
            else if(blackPices.containsKey(key)){
                this.buttons.get(key).setBackground(Color.BLACK);    
                this.buttons.get(key).setOpaque(true);
                //System.out.println(key);
            }
        }
    }

    public void refreshBoard(){
        for(Point key : this.buttons.keySet()){
            this.buttons.get(key).setBackground(Color.GRAY);
            this.buttons.get(key).setOpaque(true);
        }
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

    public void gameLoop(){

    }

}
