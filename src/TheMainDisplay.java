import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import static java.lang.Math.abs;

public class TheMainDisplay extends Canvas implements KeyListener, MouseListener {
    public static Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
    public static int screenWidth = (int)size.getWidth();
    public static int screenHeight = (int)size.getHeight();
    public static JFrame mainframe = new JFrame("mainframe");
    public static JLabel theCenterLabel, theScoreLabel, theCharacterLabel, theItemLabel, theIslandLabel, theCoinLabel, thePortalLabel;
    public static int theCharacterLabelWidth, theCharacterLabelHeight, theCharacterLabelx, theCharacterLabely;
    public static int theItemLabelx,theItemLabely,theItemLabelWidth, theItemLabelHeight;
    public static int theCoinLabelx,theCoinLabely,theCoinLabelWidth, theCoinLabelHeight;
    public static int theIslandLabelx,theIslandLabely,theIslandLabelWidth, theIslandLabelHeight;
    public static int thePortalLabelx,thePortalLabely,thePortalLabelWidth, thePortalLabelHeight;

    public static int MovementDistx = screenWidth/40;
    public static int MovementDisty = screenHeight/40;
    public static int Score = 0, MovementsMade, MovementCapIncrement = 10, MovementCap = 25, PocketChange;
    public static boolean QuickMovement = true, KeyPressed, Locked, IslandSpawned = false, PortalSpawned = false;

    public void paint(Graphics g) {
        setBackground(new Color (0,0,0,0));
        g.setColor(Color.WHITE);
        g.drawString("Version 1.0",20,20);
        g.drawString("Screen Width: " + screenWidth,20,40);
        g.drawString("Screen Height: " + screenHeight,20,60);
    }

    public static void SpawnCenterLabel(){
        int theCenterLabelWidth = screenWidth/4;
        int theCenterLabelHeight = screenHeight/8;
        int theCenterLabelx = screenWidth/2 - theCenterLabelWidth/2;
        int theCenterLabely = 0;

        theCenterLabel = new JLabel();
        theCenterLabel.setHorizontalAlignment(0);
        theCenterLabel.setBounds (theCenterLabelx, theCenterLabely, theCenterLabelWidth, theCenterLabelHeight);
        theCenterLabel.setText("<html>Use WASD to move. <br/>Q Toggles quick movement.</html>");
        mainframe.add(theCenterLabel);
    }
    public static void SpawnScoreBoard(){
        int theScoreBoardLabelWidth = screenWidth/4;
        int theScoreBoardLabelHeight = screenHeight/8;
        int theScoreBoardLabelx = screenWidth - theScoreBoardLabelWidth;
        int theScoreBoardLabely = 0;

        theScoreLabel = new JLabel();
        theScoreLabel.setHorizontalAlignment(0);
        theScoreLabel.setBounds (theScoreBoardLabelx, theScoreBoardLabely, theScoreBoardLabelWidth, theScoreBoardLabelHeight);
        UpdateScoreBoard();
        mainframe.add(theScoreLabel);
    }
    public static void SpawnCharacter(){
        theCharacterLabelWidth = screenWidth/40;
        theCharacterLabelHeight = screenHeight/40;
        if(IslandSpawned&&Score==0){
            theCharacterLabelx = theIslandLabelx+theIslandLabelWidth/2 - theCharacterLabelWidth/2;
            theCharacterLabely = theIslandLabely+theIslandLabelHeight/2 - theCharacterLabelHeight/2;
        }
        else if (IslandSpawned&&Score>0){
            Random rand = new Random();
            theCharacterLabelx = rand.nextInt(theIslandLabelx,theIslandLabelx+theIslandLabelWidth-theCharacterLabelWidth);
            theCharacterLabely = rand.nextInt(theIslandLabely,theIslandLabely+theIslandLabelHeight-theCharacterLabelHeight);
        }
        else{
            theCharacterLabelx = screenWidth/2 - theCharacterLabelWidth/2;
            theCharacterLabely = screenHeight/2 - theCharacterLabelHeight/2;
        }

        theCharacterLabel = new JLabel();
        theCharacterLabel.setHorizontalAlignment(0);
        theCharacterLabel.setBounds (theCharacterLabelx, theCharacterLabely, theCharacterLabelWidth, theCharacterLabelHeight);
        theCharacterLabel.setText("O.O");
        mainframe.add(theCharacterLabel);
    }
    public static void SpawnIsland(){
        Random rand = new Random();
        theIslandLabelWidth = rand.nextInt(screenWidth/8,screenWidth*6/8);
        theIslandLabelHeight = rand.nextInt(screenHeight/8,screenHeight*6/8);
        theIslandLabelx = rand.nextInt(0,screenWidth-theIslandLabelWidth);
        theIslandLabely = rand.nextInt(screenHeight/8,screenHeight-theIslandLabelHeight);

        theIslandLabel = new JLabel();
       /* Color myIslandGreen = new Color(5,120,15);
        theIslandLabel.setOpaque(true);
        theIslandLabel.setBackground(myIslandGreen);*/
        theIslandLabel.setHorizontalAlignment(0);

        theIslandLabel.setBounds (theIslandLabelx, theIslandLabely, theIslandLabelWidth, theIslandLabelHeight);
        mainframe.add(theIslandLabel);
        IslandSpawned=true;
    }
    public static void SpawnItem(){
        theItemLabelWidth = screenWidth/40;
        theItemLabelHeight = screenHeight/40;
        Random rand = new Random();
        if(IslandSpawned){
            theItemLabelx = rand.nextInt(theIslandLabelx,theIslandLabelx+theIslandLabelWidth-theItemLabelWidth);
            theItemLabely = rand.nextInt(theIslandLabely+theItemLabelHeight,theIslandLabely+theIslandLabelHeight);
        }
        else {
            theItemLabelx = rand.nextInt(theItemLabelWidth, screenWidth - 2 * theItemLabelWidth);
            theItemLabely = rand.nextInt(theItemLabelHeight * 2, screenHeight - theItemLabelHeight);
        }

        theItemLabel = new JLabel();
        /*Color myItemColor = new Color(100,100,100);
        theItemLabel.setOpaque(true);
        theItemLabel.setBackground(myItemColor);*/
        theItemLabel.setHorizontalAlignment(0);
        theItemLabel.setBounds (theItemLabelx, theItemLabely, theItemLabelWidth, theItemLabelHeight);
        theItemLabel.setText("ITEM");
        mainframe.add(theItemLabel);
    }
    public static void SpawnCoin(){
        theCoinLabelWidth = screenWidth/40;
        theCoinLabelHeight = screenHeight/40;
        Random rand = new Random();
        if(IslandSpawned){
            theCoinLabelx = rand.nextInt(theIslandLabelx,theIslandLabelx + theIslandLabelWidth - theCoinLabelWidth);
            theCoinLabely = rand.nextInt(theIslandLabely + theCoinLabelHeight,theIslandLabely + theIslandLabelHeight);
        }
        else {
            theCoinLabelx = rand.nextInt(theCoinLabelWidth, screenWidth - theCoinLabelWidth);
            theCoinLabely = rand.nextInt(theCoinLabelHeight * 2, screenHeight);
        }

        theCoinLabel = new JLabel();
        /*Color myCoinColor = new Color(120,120,15);
        theCoinLabel.setOpaque(true);
        theCoinLabel.setBackground(myCoinColor);*/
        theCoinLabel.setHorizontalAlignment(0);
        theCoinLabel.setBounds (theCoinLabelx, theCoinLabely, theCoinLabelWidth, theCoinLabelHeight);
        theCoinLabel.setText("COIN");
        mainframe.add(theCoinLabel);
    }
    public static void SpawnPortal(){
        thePortalLabelWidth = screenWidth/20;
        thePortalLabelHeight = screenHeight/40;
        Random rand = new Random();
        if(IslandSpawned){
            thePortalLabelx = rand.nextInt(theIslandLabelx,theIslandLabelx+theIslandLabelWidth-thePortalLabelWidth);
            thePortalLabely = rand.nextInt(theIslandLabely,theIslandLabely+theIslandLabelHeight-thePortalLabelHeight);
        }
        else {
            thePortalLabelx = rand.nextInt(thePortalLabelWidth, screenWidth - 2 * thePortalLabelWidth);
            thePortalLabely = rand.nextInt(thePortalLabelHeight, screenHeight - 2 * thePortalLabelHeight);
        }

        thePortalLabel = new JLabel();
        /*Color myPortalColor = new Color(100,100,200);
        thePortalLabel.setOpaque(true);
        thePortalLabel.setBackground(myPortalColor);*/
        thePortalLabel.setHorizontalAlignment(0);
        thePortalLabel.setBounds (thePortalLabelx, thePortalLabely, thePortalLabelWidth, thePortalLabelHeight);
        thePortalLabel.setText("PORTAL");
        mainframe.add(thePortalLabel);

        PortalSpawned = true;
    }

    public void CheckAndUpdate(){
        CheckLoseConditions();
        UpdateScoreBoard();
        UpdateCharacter();
        CheckItemLoc();
        CheckCoinLoc();
        //CheckPortalTime();
        //CheckPortal();
    }
    public static void UpdateScoreBoard() {
        theScoreLabel.setText("<html>Your score is " + Score + ". <br/> You made " + MovementsMade + " movements. <br/> You have " + (MovementCap - MovementsMade) + " movements left. <br/> You have " + PocketChange + " coins in pocket change.</html>");
    }
    public void UpdateCharacter(){
        if(!Locked) {
            theCharacterLabel.setBounds(theCharacterLabelx, theCharacterLabely, theCharacterLabelWidth, theCharacterLabelHeight);
        }
        else {theCenterLabel.setText("You ran out of moves. Game Over.");}
    }
    public void CheckItemLoc(){
        if(abs((theCharacterLabelx+theCharacterLabelWidth)/2-(theItemLabelx+theItemLabelWidth)/2)<10&&abs((theCharacterLabely+theCharacterLabelHeight)/2-(theItemLabely+theItemLabelHeight)/2)<10){
            Score++;
            MovementCap=MovementCap+ MovementCapIncrement;
            UpdateScoreBoard();

            theCenterLabel.setText("<html>You got the item! <br/> Score and movement cap increased.</html>");

            Random rand = new Random();
            if(IslandSpawned){
                theItemLabelx = rand.nextInt(theIslandLabelx+theItemLabelWidth,theIslandLabelx+theIslandLabelWidth-theItemLabelWidth);
                theItemLabely = rand.nextInt(theIslandLabely+theItemLabelHeight,theIslandLabely+theIslandLabelHeight-theItemLabelHeight);
            }
            else {
                theItemLabelx = rand.nextInt(theItemLabelWidth, screenWidth - 2 * theItemLabelWidth);
                theItemLabely = rand.nextInt(theItemLabelHeight, screenHeight - 2 * theItemLabelHeight);
            }
            theItemLabel.setBounds(theItemLabelx,theItemLabely,theItemLabelWidth,theItemLabelHeight);
        }
    }
    public void CheckCoinLoc(){
        if(abs((theCharacterLabelx+theCharacterLabelWidth)/2-(theCoinLabelx+theCoinLabelWidth)/2)<10&&abs((theCharacterLabely+theCharacterLabelHeight)/2-(theCoinLabely+theCoinLabelHeight)/2)<10){
            PocketChange++;
            UpdateScoreBoard();

            theCenterLabel.setText("<html>You collected some pocket change! <br/> Score and movement cap increased.</html>");

            Random rand = new Random();
            if(IslandSpawned){
                theCoinLabelx = rand.nextInt(theIslandLabelx+theCoinLabelWidth,theIslandLabelx+theIslandLabelWidth-theCoinLabelWidth);
                theCoinLabely = rand.nextInt(theIslandLabely+theCoinLabelHeight,theIslandLabely+theIslandLabelHeight-theCoinLabelHeight);
            }
            else {
                theCoinLabelx = rand.nextInt(theCoinLabelWidth, screenWidth - 2 * theCoinLabelWidth);
                theCoinLabely = rand.nextInt(theCoinLabelHeight, screenHeight - 2 * theCoinLabelHeight);
            }
            theCoinLabel.setBounds(theCoinLabelx,theCoinLabely,theCoinLabelWidth,theCoinLabelHeight);
        }
    }
    public void CheckPortal(){
        if(abs((theCharacterLabelx+theCharacterLabelWidth)/2-(thePortalLabelx+thePortalLabelWidth)/2)<20&&abs((theCharacterLabely+theCharacterLabelHeight)/2-(thePortalLabely+thePortalLabelHeight)/2)<20){

            theCenterLabel.setText("<html>You hopped into the portal! <br/> Movement cap reset.</html>");

            mainframe.remove(thePortalLabel);
            mainframe.remove(theCharacterLabel);
            mainframe.remove(theItemLabel);
            mainframe.remove(theCoinLabel);
            mainframe.revalidate();
           /* if(IslandSpawned){
                mainframe.remove(theIslandLabel);
                mainframe.revalidate();
                SpawnIsland();
            }*/
            SpawnCharacter();
            SpawnCoin();
            SpawnItem();

        }
    }
    public void CheckPortalTime(){
        if(Score>9 && !PortalSpawned && Score%10==0 ){
            SpawnPortal();
        }
    }
    public void CheckLoseConditions(){
        if(MovementsMade>=MovementCap){
            Locked = true;
        }
        if(MovementsMade<MovementCap){
            Locked = false;
        }
    }

    @Override
    public void keyTyped (KeyEvent e) {}

    @Override
    public void keyPressed (KeyEvent e){
        if(!KeyPressed||QuickMovement) {
            KeyPressed=true;
            int keyCode = e.getKeyCode();
            theCenterLabel.setText(e.getKeyChar() + " Key Pressed");
            switch (keyCode) {
                case KeyEvent.VK_W -> {
                    CheckLoseConditions();
                    if (!Locked) {
                        if (IslandSpawned){
                            if(theCharacterLabely - MovementDisty<theIslandLabely){
                            theCenterLabel.setText("Can't leave the island yet!");
                            }
                            else{
                                theCharacterLabely = theCharacterLabely - MovementDisty;
                                MovementsMade++;
                                theCenterLabel.setText("Heading North.");}
                        }
                        else {
                            theCharacterLabely = theCharacterLabely - MovementDisty;
                            MovementsMade++;
                            theCenterLabel.setText("Moving up!");
                        }
                    }
                    CheckAndUpdate();
                }
                case KeyEvent.VK_A -> {
                    CheckLoseConditions();
                    if (!Locked) {
                        if (IslandSpawned){
                            if(theCharacterLabelx - MovementDistx<theIslandLabelx){
                                theCenterLabel.setText("Can't leave the island yet!");
                            }
                            else{
                                theCharacterLabelx = theCharacterLabelx - MovementDistx;
                                MovementsMade++;
                                theCenterLabel.setText("Heading West.");}
                        }
                        else {
                            theCharacterLabelx = theCharacterLabelx - MovementDistx;
                            MovementsMade++;
                            theCenterLabel.setText("To the left, to the left...");
                        }
                    }
                    CheckAndUpdate();
                }
                case KeyEvent.VK_S -> {
                    CheckLoseConditions();
                    if (!Locked) {
                        if (IslandSpawned){
                            if(theCharacterLabely+MovementDisty > theIslandLabely+theIslandLabelHeight-theCharacterLabelHeight){
                                theCenterLabel.setText("Can't leave the island yet!");
                            }
                            else{
                                theCharacterLabely = theCharacterLabely + MovementDisty;
                                MovementsMade++;
                                theCenterLabel.setText("Heading South.");}
                        }
                        else {
                            theCharacterLabely = theCharacterLabely + MovementDisty;
                            MovementsMade++;
                            theCenterLabel.setText("Down.");
                        }
                    }
                    CheckAndUpdate();
                }
                case KeyEvent.VK_D -> {
                    CheckLoseConditions();
                    if (!Locked) {
                        if (IslandSpawned){
                            if(theCharacterLabelx+MovementDistx > theIslandLabelx+theIslandLabelWidth-theCharacterLabelHeight){
                                theCenterLabel.setText("Can't leave the island yet!");
                            }
                            else{
                                theCharacterLabelx = theCharacterLabelx + MovementDistx;
                                MovementsMade++;
                                theCenterLabel.setText("Heading East.");}
                        }
                        else {
                            theCharacterLabelx = theCharacterLabelx + MovementDistx;
                            MovementsMade++;
                            theCenterLabel.setText("Not Wrong.");
                        }
                    }
                    CheckAndUpdate();
                }
                case KeyEvent.VK_Q -> {
                    if (!QuickMovement) {
                        QuickMovement = true;
                        theCenterLabel.setText("Quick Movement Enabled.");
                    }
                    else {
                        QuickMovement = false;
                        theCenterLabel.setText("Quick Movement Disabled.");
                    }
                } //Toggles quick movement

                case (KeyEvent.VK_UP) -> {
                    MovementCap = MovementCap + MovementCapIncrement;
                    UpdateScoreBoard();
                    theCenterLabel.setText("UP Key Pressed");
                }
                case (KeyEvent.VK_DOWN) -> {
                    MovementCap = MovementCap - MovementCapIncrement;
                    UpdateScoreBoard();
                    theCenterLabel.setText("DOWN Key Pressed");
                }
                case (KeyEvent.VK_LEFT) -> {
                    Score--;
                    UpdateScoreBoard();
                    theCenterLabel.setText("LEFT Key Pressed");
                }
                case (KeyEvent.VK_RIGHT) -> {
                    Score++;
                    UpdateScoreBoard();
                    theCenterLabel.setText("WRONG Key Pressed");
                }

                case KeyEvent.VK_SHIFT -> theCenterLabel.setText("SHIFT Key Pressed");
                case KeyEvent.VK_CAPS_LOCK -> theCenterLabel.setText("CAPS LOCK Key Pressed");
                case KeyEvent.VK_CONTROL -> theCenterLabel.setText("CONTROL Key Pressed");
                case KeyEvent.VK_BACK_SPACE -> theCenterLabel.setText("BACKSPACE Key Pressed");
                case KeyEvent.VK_ENTER -> theCenterLabel.setText("ENTER Key Pressed");
                case KeyEvent.VK_SPACE -> theCenterLabel.setText("SPACE Key Pressed");
                case KeyEvent.VK_F1 -> theCenterLabel.setText("F1 Key Pressed");
                case KeyEvent.VK_F2 -> theCenterLabel.setText("F2 Key Pressed");
                case KeyEvent.VK_F3 -> theCenterLabel.setText("F3 Key Pressed");
                case KeyEvent.VK_F4 -> theCenterLabel.setText("F4 Key Pressed");
                case KeyEvent.VK_F5 -> theCenterLabel.setText("F5 Key Pressed");
                case KeyEvent.VK_F6 -> theCenterLabel.setText("F6 Key Pressed");
                case KeyEvent.VK_F7 -> theCenterLabel.setText("F7 Key Pressed");
                case KeyEvent.VK_F8 -> theCenterLabel.setText("F8 Key Pressed");
                case KeyEvent.VK_F9 -> theCenterLabel.setText("F9 Key Pressed");
                case KeyEvent.VK_F10 -> theCenterLabel.setText("F10 Key Pressed");
                case KeyEvent.VK_F11 -> theCenterLabel.setText("F11 Key Pressed");
                case KeyEvent.VK_F12 -> {
                    theCenterLabel.setText("Exiting App...");
                    System.exit(0);
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_Q -> {
                if(QuickMovement) {
                    theCenterLabel.setText("Quick Movement Enabled.");
                }
                else {
                    theCenterLabel.setText("Quick Movement Disabled.");
                }
            }
            default -> theCenterLabel.setText ("Press F12 to exit.");
        }
        KeyPressed=false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    public static void initializeMainframe(){

        TheMainDisplay D = new TheMainDisplay();

        mainframe.setSize(screenWidth, screenHeight);
        mainframe.setTitle("This is, in fact, the main frame");
        mainframe.addKeyListener(D);
        mainframe.addMouseListener(D);

        SpawnCenterLabel();
        SpawnIsland();
        SpawnScoreBoard();
        SpawnCharacter();
        SpawnItem();
        SpawnCoin();
        //SpawnPortal();

        mainframe.setUndecorated(true);
        mainframe.add(D);
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        initializeMainframe();
    }
}