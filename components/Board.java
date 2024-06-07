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

    private Point firstClickedPoint = null;
    private char firstClickedPointCollor = 0;

    private char currentPlayer = 'w';

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
                //JButton tempButton = new JButton("Height:" + Integer.toString(height + 1)+ " Width:" + LETTERS_IN_BOARD[width]);
                JButton tempButton = new JButton();
                Point tempPoint = new Point(width, height);
                this.buttons.put(tempPoint,tempButton);
                this.buttons.get(tempPoint).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e){
                        JButton tempButton = (JButton) e.getSource();
                        Point currPoint = null;
                        for(Point key : buttons.keySet()){
                            if(buttons.get(key) == tempButton){
                                currPoint = key;
                                break;
                            }
                        }
                        if(currPoint != null){
                            changeColors(tempPoint);
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
                            this.whitePices.put(tempPoint,new Pice(width, height, 'w'));
                            //System.out.println("białe szer:" +width + " wys:" + height);
                        } 
                    }
                    else if(currPices.compareTo("black:") == 0){
                        String temp[] = readLine.split(";");
                        for(int iterator = 0; iterator < temp.length;iterator ++){
                            String numbers[] = temp[iterator].split(",");
                            int height = Integer.parseInt(numbers[0]);
                            int width = Integer.parseInt(numbers[1]);
                            Point tempPoint = new Point(width, height);
                            this.blackPices.put(tempPoint,new Pice(width, height, 'b'));
                            //System.out.println("czarne szer:" +width + " wys:" + height);
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
                if(this.whitePices.get(key).getType() == PiceType.QUEEN){
                    this.buttons.get(key).setForeground(Color.black);
                    this.buttons.get(key).setText("Q");
                }
                this.buttons.get(key).setBackground(Color.WHITE);
                this.buttons.get(key).setOpaque(true);
            }
            else if(blackPices.containsKey(key)){
                if(this.blackPices.get(key).getType() == PiceType.QUEEN){
                    this.buttons.get(key).setText("Q");
                }
                this.buttons.get(key).setBackground(Color.BLACK);    
                this.buttons.get(key).setOpaque(true);
            }
        }
    }

    public void refreshBoard(){
        for(Point key : this.buttons.keySet()){
            this.buttons.get(key).setText("");
            this.buttons.get(key).setBackground(Color.GRAY);
            this.buttons.get(key).setOpaque(true);
        }
        this.drawPices();
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

    public boolean checkForBeating(Point point,int sideOfBeating){
        /*
             * If sideOfBeating is positive it means it is right
             * If sideOfBeating is negative it means it is left
        */
        Point nextPoint;
        if(firstClickedPointCollor != 0){    
            if(firstClickedPointCollor == 'w'){
                if(!blackPices.containsKey(point) || whitePices.containsKey(point)){
                    return false;       
                }
                nextPoint = new Point(point.getWidth() - sideOfBeating , point.getHeight() - 1);
                if(blackPices.containsKey(nextPoint) || whitePices.containsKey(nextPoint) || !buttons.containsKey(nextPoint)){
                    return false;
                }
                return true;
            }
            else if(firstClickedPointCollor == 'b'){
                if(blackPices.containsKey(point) || !whitePices.containsKey(point)){
                    return false;       
                }
                nextPoint = new Point(point.getWidth() - sideOfBeating , point.getHeight() + 1);
                if(blackPices.containsKey(nextPoint) || whitePices.containsKey(nextPoint) || !buttons.containsKey(nextPoint)){
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    public void makeMove(TreeMap<Point,Pice> picesToMove, TreeMap<Point,Pice> picesThatWaits,int differencesInHeights,int differencesInWidth,int sideOfBeating, Point point){
        boolean moveDone = false;
        Point newPointAfterMove = null;
        char color = picesToMove.get(firstClickedPoint).getCollor();
        int heightOfBeating = color == 'w' ? -1 : +1;
        if(picesToMove.get(firstClickedPoint).getType() == PiceType.CHECKER){
            //System.out.println("Jest pion");
            if(differencesInHeights == 1 && differencesInWidth == 1){
                if(picesThatWaits.containsKey(point) && checkForBeating(point, sideOfBeating)){
                    picesToMove.put(point, picesToMove.get(firstClickedPoint));
                    picesThatWaits.remove(point);
                    newPointAfterMove = new Point(point.getWidth() - sideOfBeating,point.getHeight() + heightOfBeating);
                    picesToMove.put(newPointAfterMove, picesToMove.get(point));
                    picesToMove.remove(point);
                    picesToMove.remove(firstClickedPoint);
                    moveDone = true;
                }
                else if(!picesThatWaits.containsKey(point) && !picesToMove.containsKey(point)){
                    picesToMove.put(point, picesToMove.get(firstClickedPoint));
                    newPointAfterMove = point;
                    picesToMove.remove(firstClickedPoint);
                    moveDone = true;
                }
                if(moveDone){
                    if(color == 'w'){
                        if(newPointAfterMove.getHeight() == 0){
                            picesToMove.get(newPointAfterMove).setType(PiceType.QUEEN);
                        }
                    }
                    else if(color == 'b'){
                        if(newPointAfterMove.getHeight() == 7){
                            picesToMove.get(newPointAfterMove).setType(PiceType.QUEEN);
                        }
                    }
                }
            }
        }
        else if(picesToMove.get(firstClickedPoint).getType() == PiceType.QUEEN){
            if(differencesInHeights == differencesInWidth){
                //System.out.println("Jest damka");
                if(picesThatWaits.containsKey(point) && checkForBeating(point, sideOfBeating)){
                    picesToMove.put(point, picesToMove.get(firstClickedPoint));
                    picesThatWaits.remove(point);
                    newPointAfterMove = new Point(point.getWidth() - sideOfBeating,point.getHeight() + heightOfBeating);
                    picesToMove.put(newPointAfterMove, picesToMove.get(point));
                    picesToMove.remove(point);
                    picesToMove.remove(firstClickedPoint);
                    moveDone = true;
                }
                else if(!picesThatWaits.containsKey(point) && !picesToMove.containsKey(point)){
                    picesToMove.put(point, picesToMove.get(firstClickedPoint));
                    newPointAfterMove = point;
                    picesToMove.remove(firstClickedPoint);
                    moveDone = true;
                }
            }
        }
    }

    public void changeColors(Point point){
        Pice currPice = null;
        System.out.println("Kliknięto : wys: " + point.getHeight() + " szer: " + point.getWidth());
        if(firstClickedPoint == null){
            
            if (whitePices.containsKey(point)) {
                currPice = whitePices.get(point);
            }
            else if (blackPices.containsKey(point)) {
                currPice = blackPices.get(point);
            }

            if(currPice == null){
                firstClickedPoint = null;
                firstClickedPointCollor = 0;
                return;
            }
            firstClickedPoint = point;
            firstClickedPointCollor = currPice.getCollor();
        }
        else{
            int differencesInHeights = firstClickedPoint.getHeight() - point.getHeight();
            int differencesInWidth = firstClickedPoint.getWidth() - point.getWidth();
            int sideOfBeating = differencesInWidth;
            differencesInHeights = differencesInHeights < 0 ? differencesInHeights * -1 : differencesInHeights;
            differencesInWidth = differencesInWidth < 0 ? differencesInWidth * -1 : differencesInWidth;
                
            if(firstClickedPointCollor == 'w'){
                makeMove(whitePices, blackPices, differencesInHeights, differencesInWidth, sideOfBeating, point);
            }
            else if(firstClickedPointCollor == 'b'){
                makeMove(blackPices, whitePices, differencesInHeights, differencesInWidth, sideOfBeating, point);
            }
            this.refreshBoard();
            firstClickedPoint = null;
            firstClickedPointCollor = 0;
        }

    }

    public void gameLoop(){
        
    }

}
