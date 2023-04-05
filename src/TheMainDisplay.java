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
    public static JLabel theCenterLabel, theScoreLabel, theCharacterLabel, theFoodLabel, theIslandLabel, theSecondIslandLabel, theCoinLabel, thePortalLabel;

    public static JLabel ControlsMenuLabel;
    public static boolean ControlsMenu = false;
    public static boolean DebugMode = false;

    public static int theCharacterLabelWidth, theCharacterLabelHeight, theCharacterLabelx, theCharacterLabely;
    public static int theFoodLabelx, theFoodLabely, theFoodLabelWidth, theFoodLabelHeight;
    public static int theCoinLabelx,theCoinLabely,theCoinLabelWidth, theCoinLabelHeight;
    public static int theIslandLabelx,theIslandLabely,theIslandLabelWidth, theIslandLabelHeight;
    public static int theSecondIslandLabelx,theSecondIslandLabely,theSecondIslandLabelWidth, theSecondIslandLabelHeight;
    public static int keyCode;

    public static int MovementDistx = screenWidth/80;
    public static int MovementDisty = screenHeight/80;
    public static int Score = 0, MovementsMade, MovementCapIncrement = 10, MovementCap = 25, PocketChange;
    public static boolean QuickMovement = true, KeyPressed, Locked, IslandSpawned, SecondIslandSpawned = false;
    public static boolean UpperBoundaryReached, LeftBoundaryReached, BottomBoundaryReached, RightBoundaryReached, BoundaryReached;

    public void paint(Graphics g) {
        setBackground(new Color (0,0,0,0));
        g.setColor(Color.WHITE);
        g.drawString("Version 1.0 (2023.04.04)",20,20);
        g.drawString("Screen Width: " + screenWidth,20,40);
        g.drawString("Screen Height: " + screenHeight,20,60);
    } //Version and Screen Info

    public static void SpawnCenterLabel(){
        int theCenterLabelWidth = screenWidth/4;
        int theCenterLabelHeight = screenHeight/8;
        int theCenterLabelx = screenWidth/2 - theCenterLabelWidth/2;
        int theCenterLabely = 0;

        theCenterLabel = new JLabel();
        theCenterLabel.setHorizontalAlignment(0);
        theCenterLabel.setBounds (theCenterLabelx, theCenterLabely, theCenterLabelWidth, theCenterLabelHeight);
        theCenterLabel.setText("<html>Use WASD to move. <br/>C Toggles Controls Menu.</html>");
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
    public static void SpawnControlsMenuLabel(){
            int theControlsMenuLabelWidth = screenWidth / 8;
            int theControlsMenuLabelHeight = screenHeight / 2;
            int theControlsMenuLabelx = screenWidth / 12 - theControlsMenuLabelWidth / 2;
            int theControlsMenuLabely = screenHeight / 2 - theControlsMenuLabelHeight / 2;

            ControlsMenuLabel = new JLabel();
            ControlsMenuLabel.setHorizontalAlignment(0);
            ControlsMenuLabel.setBounds(theControlsMenuLabelx, theControlsMenuLabely, theControlsMenuLabelWidth, theControlsMenuLabelHeight);
            ControlsMenuLabel.setText("<html>This is the controls menu.<br/><br/>WASD is used to move.<br/>Q toggles quick movement.<br/>B toggles debug mode.<br/>C toggles the controls menu. </html>");
            ControlsMenuLabel.setVisible(false);
            mainframe.add(ControlsMenuLabel);
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
        theIslandLabelWidth = rand.nextInt(screenWidth/8,screenWidth*2/8);
        theIslandLabelHeight = rand.nextInt(screenHeight/8,screenHeight*2/8);
        theIslandLabelx = rand.nextInt(0,screenWidth-theIslandLabelWidth);
        theIslandLabely = rand.nextInt(screenHeight/8,screenHeight-theIslandLabelHeight);

        theIslandLabel = new JLabel();

        theIslandLabel.setBounds (theIslandLabelx, theIslandLabely, theIslandLabelWidth, theIslandLabelHeight);
        mainframe.add(theIslandLabel);
        IslandSpawned=true;
    }
    public static void SpawnSecondIsland(){
        Random rand = new Random();
        theSecondIslandLabelWidth = rand.nextInt(screenWidth/8,screenWidth*3/8);
        theSecondIslandLabelHeight = rand.nextInt(screenHeight/8,screenHeight*3/8);
        theSecondIslandLabelx = rand.nextInt(theIslandLabelx-theSecondIslandLabelWidth,theIslandLabelx+theIslandLabelWidth);
        theSecondIslandLabely = rand.nextInt(theIslandLabely-theSecondIslandLabelHeight,theIslandLabely+theIslandLabelHeight);

        theSecondIslandLabel = new JLabel();

        theSecondIslandLabel.setBounds (theSecondIslandLabelx, theSecondIslandLabely, theSecondIslandLabelWidth, theSecondIslandLabelHeight);
        mainframe.add(theSecondIslandLabel);
        SecondIslandSpawned=true;
    }
    public static void SpawnFood(){
        theFoodLabelWidth = screenWidth/40;
        theFoodLabelHeight = screenHeight/40;
        Random rand = new Random();
        if(IslandSpawned){
            theFoodLabelx = rand.nextInt(theIslandLabelx,theIslandLabelx+theIslandLabelWidth - theFoodLabelWidth);
            theFoodLabely = rand.nextInt(theIslandLabely + theFoodLabelHeight,theIslandLabely + theIslandLabelHeight- theFoodLabelHeight);
        }
        else {
            theFoodLabelx = rand.nextInt(theFoodLabelWidth, screenWidth - 2 * theFoodLabelWidth);
            theFoodLabely = rand.nextInt(theFoodLabelHeight * 2, screenHeight - theFoodLabelHeight);
        }

        theFoodLabel = new JLabel();
        /*Color myItemColor = new Color(100,100,100);
        theItemLabel.setOpaque(true);
        theItemLabel.setBackground(myItemColor);*/
        theFoodLabel.setHorizontalAlignment(0);
        theFoodLabel.setBounds (theFoodLabelx, theFoodLabely, theFoodLabelWidth, theFoodLabelHeight);
        theFoodLabel.setText("FOOD");
        mainframe.add(theFoodLabel);
    }
    public static void SpawnCoin(){
        theCoinLabelWidth = screenWidth/40;
        theCoinLabelHeight = screenHeight/40;
        Random rand = new Random();
        if(IslandSpawned){
            if(SecondIslandSpawned){
                theCoinLabelx = rand.nextInt(theSecondIslandLabelx, theSecondIslandLabelx + theSecondIslandLabelWidth - theCoinLabelWidth);
                theCoinLabely = rand.nextInt(theSecondIslandLabely + theCoinLabelHeight, theSecondIslandLabely + theSecondIslandLabelHeight - theCharacterLabelHeight);
            }
            else{
                theCoinLabelx = rand.nextInt(theIslandLabelx, theIslandLabelx + theIslandLabelWidth - theCoinLabelWidth);
                theCoinLabely = rand.nextInt(theIslandLabely + theCoinLabelHeight, theIslandLabely + theIslandLabelHeight - theCharacterLabelHeight);
            }
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

    public void CheckAndUpdate(){
        CheckLoseConditions();
        UpdateScoreBoard();
        UpdateCharacter();
        CheckFoodLoc();
        CheckCoinLoc();
    }
    public static void UpdateScoreBoard(){
        theScoreLabel.setText("<html>Your score is " + Score + ". <br/> You made " + MovementsMade + " movements. <br/> You have " + (MovementCap - MovementsMade) + " movements left. <br/> You have " + PocketChange + " coins in pocket change.</html>");
    }
    public void UpdateCharacter(){
        if(!Locked) {
            theCharacterLabel.setBounds(theCharacterLabelx, theCharacterLabely, theCharacterLabelWidth, theCharacterLabelHeight);
        }
        else {theCenterLabel.setText("You ran out of moves. Game Over.");}
    }
    public void CheckBoundaries(){
        if(IslandSpawned){
            UpperBoundaryReached = theCharacterLabely - MovementDisty < theIslandLabely;
            LeftBoundaryReached = theCharacterLabelx - MovementDistx < theIslandLabelx;
            BottomBoundaryReached = theCharacterLabely + MovementDisty > theIslandLabely+theIslandLabelHeight-theCharacterLabelHeight;
            RightBoundaryReached = theCharacterLabelx + MovementDistx > theIslandLabelx+theIslandLabelWidth-theCharacterLabelWidth;
            if (UpperBoundaryReached||LeftBoundaryReached||BottomBoundaryReached||RightBoundaryReached){
                BoundaryReached=true;
            }
            if(SecondIslandSpawned){
                if(theSecondIslandLabely<theIslandLabely) {
                    UpperBoundaryReached = theCharacterLabely - MovementDisty < theSecondIslandLabely;
                }
                else {
                    UpperBoundaryReached = theCharacterLabely - MovementDisty > theSecondIslandLabely;
                }
            }
        }
    }
    public void CheckFoodLoc(){
        if(abs((theCharacterLabelx+theCharacterLabelWidth)/2-(theFoodLabelx + theFoodLabelWidth)/2)<10&&abs((theCharacterLabely+theCharacterLabelHeight)/2-(theFoodLabely + theFoodLabelHeight)/2)<10){
            Score++;
            MovementCap=MovementCap+MovementCapIncrement;
            UpdateScoreBoard();

            theCenterLabel.setText("<html>You collected the food! <br/> Score and movement cap increased.</html>");

            Random rand = new Random();
            if(IslandSpawned){
                theFoodLabelx = rand.nextInt(theIslandLabelx+ theFoodLabelWidth,theIslandLabelx+theIslandLabelWidth- theFoodLabelWidth);
                theFoodLabely = rand.nextInt(theIslandLabely+ theFoodLabelHeight,theIslandLabely+theIslandLabelHeight- theFoodLabelHeight);
            }
            else {
                theFoodLabelx = rand.nextInt(theFoodLabelWidth, screenWidth - 2 * theFoodLabelWidth);
                theFoodLabely = rand.nextInt(theFoodLabelHeight, screenHeight - 2 * theFoodLabelHeight);
            }
            theFoodLabel.setBounds(theFoodLabelx, theFoodLabely, theFoodLabelWidth, theFoodLabelHeight);
        }
    }
    public void CheckCoinLoc(){
        if(abs((theCharacterLabelx+theCharacterLabelWidth)/2-(theCoinLabelx+theCoinLabelWidth)/2)<10&&abs((theCharacterLabely+theCharacterLabelHeight)/2-(theCoinLabely+theCoinLabelHeight)/2)<10){
            PocketChange++;
            Score = Score + 3;
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
            keyCode = e.getKeyCode();
            KeyPressed=true;
            theCenterLabel.setText(e.getKeyChar() + " Key Pressed");

            switch (keyCode) {

                //Movement Controls
                case KeyEvent.VK_W -> {
                    CheckLoseConditions();
                    CheckBoundaries();
                    if (!Locked) {
                        if (IslandSpawned){
                            if(UpperBoundaryReached){
                                theCenterLabel.setText("Can't leave the island yet!");
                            }
                            else{
                                theCharacterLabely = theCharacterLabely - MovementDisty;
                                MovementsMade++;
                                theCenterLabel.setText("Heading North.");
                            }
                        }
                        else {
                            theCharacterLabely = theCharacterLabely - MovementDisty;
                            MovementsMade++;
                            theCenterLabel.setText("Moving up!");
                        }
                    }
                    CheckAndUpdate();
                } //Up
                case KeyEvent.VK_A -> {
                    CheckLoseConditions();
                    CheckBoundaries();
                    if (!Locked) {
                        if (IslandSpawned){
                            if(LeftBoundaryReached){
                                theCenterLabel.setText("Can't leave the island yet!");
                            }
                            else{
                                theCharacterLabelx = theCharacterLabelx - MovementDistx;
                                MovementsMade++;
                                theCenterLabel.setText("Heading West.");
                            }
                        }
                        else {
                            theCharacterLabelx = theCharacterLabelx - MovementDistx;
                            MovementsMade++;
                            theCenterLabel.setText("To the left, to the left...");
                        }
                    }
                    CheckAndUpdate();
                } //Left
                case KeyEvent.VK_S -> {
                    CheckLoseConditions();
                    CheckBoundaries();
                    if (!Locked) {
                        if (IslandSpawned){
                            if(BottomBoundaryReached){
                                theCenterLabel.setText("Can't leave the island yet!");
                            }
                            else{
                                theCharacterLabely = theCharacterLabely + MovementDisty;
                                MovementsMade++;
                                theCenterLabel.setText("Heading South.");
                            }
                        }
                        else {
                            theCharacterLabely = theCharacterLabely + MovementDisty;
                            MovementsMade++;
                            theCenterLabel.setText("Down.");
                        }
                    }
                    CheckAndUpdate();
                } //Down
                case KeyEvent.VK_D -> {
                    CheckLoseConditions();
                    CheckBoundaries();
                    if (!Locked) {
                        if (IslandSpawned){
                            if(RightBoundaryReached){
                                theCenterLabel.setText("Can't leave the island yet!");
                            }
                            else{
                                theCharacterLabelx = theCharacterLabelx + MovementDistx;
                                MovementsMade++;
                                theCenterLabel.setText("Heading East.");
                            }
                        }
                        else {
                            theCharacterLabelx = theCharacterLabelx + MovementDistx;
                            MovementsMade++;
                            theCenterLabel.setText("Not Wrong.");
                        }
                    }
                    CheckAndUpdate();
                } //Right

                //Non-Movement Controls
                case KeyEvent.VK_C -> {
                    if(ControlsMenu){
                        ControlsMenuLabel.setVisible(false);
                        ControlsMenu=false;
                    }
                    else{
                        ControlsMenuLabel.setVisible(true);
                        ControlsMenu=true;
                    }
                } //Toggles Controls Menu
                case KeyEvent.VK_B -> {
                    if(!DebugMode){
                        DebugMode=true;
                        theCenterLabel.setText("Debug Mode Enabled.");
                    }
                    else{
                        DebugMode=false;
                        theCenterLabel.setText("Debug Mode Disabled.");
                    }
                } //Toggles Debug Mode
                case KeyEvent.VK_Q -> {
                    if (!QuickMovement) {
                        QuickMovement = true;
                        theCenterLabel.setText("Quick Movement Enabled.");
                    }
                    else {
                        QuickMovement = false;
                        theCenterLabel.setText("Quick Movement Disabled.");
                    }
                } //Toggles Quick Movement

                //Debug Mode
                case (KeyEvent.VK_UP) -> {
                    if (DebugMode) {
                        MovementCap = MovementCap + MovementCapIncrement;
                        UpdateScoreBoard();
                        theCenterLabel.setText("Movement Cap Increased");
                    }
                    else {
                        theCenterLabel.setText("UP Key Pressed");
                    }
                } //Currently Increases Movement Cap
                case (KeyEvent.VK_DOWN) -> {
                    if (DebugMode) {
                        MovementCap = MovementCap - MovementCapIncrement;
                        UpdateScoreBoard();
                        theCenterLabel.setText("Movement Cap Decreased");
                    }
                    else {
                        theCenterLabel.setText("DOWN Key Pressed");
                    }
                } //Currently Decreases Movement Cap
                case (KeyEvent.VK_LEFT) -> {
                    if (DebugMode) {
                        Score--;
                        UpdateScoreBoard();
                        theCenterLabel.setText("Score Decreased");
                    }
                    else {
                        theCenterLabel.setText("UP Key Pressed");
                    }
                } //Currently Decreases Score
                case (KeyEvent.VK_RIGHT) -> {
                    if (DebugMode) {
                        Score++;
                        UpdateScoreBoard();
                        theCenterLabel.setText("Score Increased");
                    }
                    else {
                        theCenterLabel.setText("UP Key Pressed");
                    }
                } //Currently Increases Score

                //General Key Events
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
                } //Exits Program
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
        SpawnControlsMenuLabel();
        SpawnIsland();
        //SpawnSecondIsland();
        SpawnScoreBoard();
        SpawnCharacter();
        SpawnFood();
        SpawnCoin();

        mainframe.setUndecorated(true);
        mainframe.add(D);
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        initializeMainframe();
    }
}