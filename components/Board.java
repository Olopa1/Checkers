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
    private JFrame timeFrame;
    private final int[][] boardPlacment;
    private TreeMap<Point,JButton> buttons;
    //private static final String[] LETTERS_IN_BOARD = {"A","B","C","D","E","F","G","H"};
    private TreeMap<Point,Pice> whitePices;
    private TreeMap<Point,Pice> blackPices;

    private Point firstClickedPoint = null;
    private char firstClickedPointCollor = 0;

    private char currentPlayer = 'w';

    private Timer whiteTimer;
    private Timer blackTimer;
    private int whiteTime;
    private int blackTime;
    private JLabel whiteTimeLabel;
    private JLabel blackTimeLabel;

    public JFrame getBoardFrame(){
        return this.mainFrame;
    }

    public Board()throws FileNotFoundException{
        this.mainFrame = new JFrame();
        this.timeFrame = new JFrame();
        this.boardPlacment = new int[8][8];
        buttons = new TreeMap<>();
        whitePices = new TreeMap<>();
        blackPices = new TreeMap<>();
        this.whiteTime = 0;
        this.blackTime = 0;
    
        this.whiteTimeLabel = new JLabel("White Time: 0");
        this.blackTimeLabel = new JLabel("Black Time: 0");
        
        this.mainFrame.setLayout(new GridLayout(9, 8));
        this.mainFrame.setSize(1500, 1500);
        this.timeFrame.setLayout(new GridLayout(2,1));
        this.timeFrame.setSize(400,400); 
        this.timeFrame.add(whiteTimeLabel);
        this.timeFrame.add(blackTimeLabel);
        
        
        this.whiteTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                whiteTime++;
                whiteTimeLabel.setText("White Time: " + whiteTime);
            }
        });
    
        this.blackTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                blackTime++;
                blackTimeLabel.setText("Black Time: " + blackTime);
            }
        });
    
        for(int height = 0; height < 8;height++){
            for(int width = 0; width < 8; width++){
                this.boardPlacment[height][width] = 0;
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

    private void startWhiteTimer() {
        whiteTimer.start();
        blackTimer.stop();
    }
    
    private void startBlackTimer() {
        blackTimer.start();
        whiteTimer.stop();
    }
    
    private void resetTimers() {
        whiteTime = 0;
        blackTime = 0;
        whiteTimeLabel.setText("White Time: " + whiteTime);
        blackTimeLabel.setText("Black Time: " + blackTime);
        whiteTimer.stop();
        blackTimer.stop();
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
        this.timeFrame.setVisible(true);
    }

    public void printBoardCosnosle(){
        for(int width = 0; width < 8;width++){
            for(int height = 0; height < 8; height++){
                System.err.print(this.boardPlacment[width][height] = 0);
            }
            System.out.println("");
        }
    }

    public boolean checkForBeating(Point point, int sideOfBeating) {
        if (firstClickedPointCollor == 0) {
            return false;
        }
    
        Pice firstClickedPiece = (firstClickedPointCollor == 'w') ? whitePices.get(firstClickedPoint) : blackPices.get(firstClickedPoint);
        PiceType pieceType = firstClickedPiece.getType();
    
        if (pieceType == PiceType.CHECKER) {
            return checkForBeatingChecker(point, sideOfBeating);
        } else if (pieceType == PiceType.QUEEN) {
            return checkForBeatingQueen(point);
        }
    
        return false;
    }
    
    private boolean checkForBeatingChecker(Point point, int sideOfBeating) {
        Point nextPoint;
        if (firstClickedPointCollor == 'w') {
            if (!blackPices.containsKey(point) || whitePices.containsKey(point)) {
                return false;
            }
            nextPoint = new Point(point.getWidth() - sideOfBeating, point.getHeight() - 1);
            if (blackPices.containsKey(nextPoint) || whitePices.containsKey(nextPoint) || !buttons.containsKey(nextPoint)) {
                return false;
            }
            return true;
        } else if (firstClickedPointCollor == 'b') {
            if (blackPices.containsKey(point) || !whitePices.containsKey(point)) {
                return false;
            }
            nextPoint = new Point(point.getWidth() - sideOfBeating, point.getHeight() + 1);
            if (blackPices.containsKey(nextPoint) || whitePices.containsKey(nextPoint) || !buttons.containsKey(nextPoint)) {
                return false;
            }
            return true;
        }
        return false;
    }
    
    private boolean checkForBeatingQueen(Point point) {
        int deltaX = Integer.compare(point.getWidth(), firstClickedPoint.getWidth());
        int deltaY = Integer.compare(point.getHeight(), firstClickedPoint.getHeight());
    
        Point checkPoint = new Point(firstClickedPoint.getWidth() + deltaX, firstClickedPoint.getHeight() + deltaY);
        boolean foundOpponentPiece = false;
    
        while (buttons.containsKey(checkPoint)) {
            if (firstClickedPointCollor == 'w') {
                if (blackPices.containsKey(checkPoint)) {
                    foundOpponentPiece = true;
                } else if (whitePices.containsKey(checkPoint)) {
                    return false;
                }
            } else if (firstClickedPointCollor == 'b') {
                if (whitePices.containsKey(checkPoint)) {
                    foundOpponentPiece = true;
                } else if (blackPices.containsKey(checkPoint)) {
                    return false;
                }
            }
    
            if (foundOpponentPiece) {
                Point landingPoint = new Point(checkPoint.getWidth() + deltaX, checkPoint.getHeight() + deltaY);
                if (buttons.containsKey(landingPoint) && !whitePices.containsKey(landingPoint) && !blackPices.containsKey(landingPoint)) {
                    return true;
                } else {
                    return false;
                }
            }
    
            checkPoint = new Point(checkPoint.getWidth() + deltaX, checkPoint.getHeight() + deltaY);
        }
        return false;
    }

    public void makeMove(TreeMap<Point,Pice> picesToMove, TreeMap<Point,Pice> picesThatWaits,int differencesInHeights,int differencesInWidth,int sideOfBeating, Point point){
        boolean moveDone = false;
        Point newPointAfterMove = null;
        char color = picesToMove.get(firstClickedPoint).getCollor();
        int heightOfBeating = color == 'w' ? -1 : +1;
        if(picesToMove.get(firstClickedPoint).getType() == PiceType.CHECKER){
            if ((color == 'w' && point.getHeight() < firstClickedPoint.getHeight()) || (color == 'b' && point.getHeight() > firstClickedPoint.getHeight())) {
                if(differencesInHeights == 1 && differencesInWidth == 1){
                    if(picesThatWaits.containsKey(point) && checkForBeating(point, sideOfBeating)){
                        picesToMove.put(point, picesToMove.get(firstClickedPoint));
                        picesThatWaits.remove(point);
                        newPointAfterMove = new Point(point.getWidth() - sideOfBeating,point.getHeight() + heightOfBeating);
                        picesToMove.put(newPointAfterMove, picesToMove.get(point));
                        picesToMove.remove(point);
                        picesToMove.remove(firstClickedPoint);
                        moveDone = true;
                        this.currentPlayer = (currentPlayer == 'w') ? 'b' : 'w';
                    }
                    else if(!picesThatWaits.containsKey(point) && !picesToMove.containsKey(point)){
                        picesToMove.put(point, picesToMove.get(firstClickedPoint));
                        newPointAfterMove = point;
                        picesToMove.remove(firstClickedPoint);
                        moveDone = true;
                        this.currentPlayer = (currentPlayer == 'w') ? 'b' : 'w';
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
        }
        else if(picesToMove.get(firstClickedPoint).getType() == PiceType.QUEEN){
            if(differencesInHeights == differencesInWidth){
                //System.out.println("Jest damka");
                int deltaX = Integer.compare(point.getWidth(), firstClickedPoint.getWidth());
                int deltaY = Integer.compare(point.getHeight(), firstClickedPoint.getHeight());
                if(picesThatWaits.containsKey(point) && checkForBeating(point, sideOfBeating)){
                    picesToMove.put(point, picesToMove.get(firstClickedPoint));
                    picesThatWaits.remove(point);
                    newPointAfterMove = new Point(point.getWidth() + deltaX,point.getHeight() + deltaY);
                    picesToMove.put(newPointAfterMove, picesToMove.get(point));
                    picesToMove.remove(point);
                    picesToMove.remove(firstClickedPoint);
                    moveDone = true;
                    this.currentPlayer = (currentPlayer == 'w') ? 'b' : 'w';
                }
                else if(!picesThatWaits.containsKey(point) && !picesToMove.containsKey(point)){
                    picesToMove.put(point, picesToMove.get(firstClickedPoint));
                    newPointAfterMove = point;
                    picesToMove.remove(firstClickedPoint);
                    moveDone = true;
                    this.currentPlayer = (currentPlayer == 'w') ? 'b' : 'w';   
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
                
            if(firstClickedPointCollor == 'w' && currentPlayer == 'w'){
                makeMove(whitePices, blackPices, differencesInHeights, differencesInWidth, sideOfBeating, point);
            }
            else if(firstClickedPointCollor == 'b' && currentPlayer == 'b'){
                makeMove(blackPices, whitePices, differencesInHeights, differencesInWidth, sideOfBeating, point);
            }
            if(currentPlayer == 'w'){
                startWhiteTimer();
            }
            else{
                startBlackTimer();
            }
            this.refreshBoard();
            firstClickedPoint = null;
            firstClickedPointCollor = 0;
        }
    }

    private boolean checkWinCondition() {
        if(whitePices.isEmpty()) return true;
        if(blackPices.isEmpty()) return true;
        return false;
    }

    public void gameLoop() {
        while (true) {
            if (checkWinCondition()) {
                JOptionPane.showMessageDialog(mainFrame, "Gracz " + (currentPlayer == 'w' ? "biały" : "czarny") + " wygrał!");
                resetTimers();
                break;
            }
        }
    }
}
