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

    // The Menu Labels
    public static JLabel theCenterLabel, theScoreLabel, ControlsMenuLabel, TradingMenuLabel;
    public static boolean CenterLabelVisibility, ControlsMenuVisibility, TradingMenuVisibility = false;
    public static boolean DebugMode = false;

    // The Character, Island, and Item Labels
    public static JLabel theCharacterLabel, theFoodLabel, theIslandLabel, theSecondIslandLabel, theCoinLabel, theNPCLabel, theSunkenTreasureLabel, theMysteryFoodLabel;
    public static int theCharacterLabelx, theCharacterLabely, theCharacterLabelWidth, theCharacterLabelHeight;
    public static int theFoodLabelx, theFoodLabely, theFoodLabelWidth, theFoodLabelHeight;
    public static int theCoinLabelx,theCoinLabely,theCoinLabelWidth, theCoinLabelHeight;
    public static int theNPCLabelx, theNPCLabely, theNPCLabelWidth, theNPCLabelHeight;
    public static int theSunkenTreasureLabelx,theSunkenTreasureLabely,theSunkenTreasureLabelWidth, theSunkenTreasureLabelHeight;
    public static int theMysteryFoodLabelx,theMysteryFoodLabely,theMysteryFoodLabelWidth, theMysteryFoodLabelHeight;
    public static int theIslandLabelx,theIslandLabely,theIslandLabelWidth, theIslandLabelHeight;
    public static int theSecondIslandLabelx,theSecondIslandLabely,theSecondIslandLabelWidth, theSecondIslandLabelHeight;

    // Constants and Scorekeepers
    public static int MovementDistx = screenWidth/80;
    public static int MovementDisty = screenHeight/80;
    public static int Score = 0, MovementsMade, MovementCapIncrement = 5, MovementCap = 25, PocketChange;

    // Other Misc.
    public static int keyCode;
    public static boolean QuickMovement = true, KeyPressed, Locked, IslandSpawned, SecondIslandSpawned, NearNPC = false, ItemSpawnProgression=false, NewItemSpawned=false;
    public static boolean UpperBoundaryReached, LeftBoundaryReached, BottomBoundaryReached, RightBoundaryReached, BoundaryReached;

    public void paint(Graphics g) {
        setBackground(new Color (0,0,0,0));
        g.setColor(Color.WHITE);
        g.drawString("Version 1.0 (2023.04.25)",20,20);
        g.drawString("Screen Width: " + screenWidth,20,40);
        g.drawString("Screen Height: " + screenHeight,20,60);
    } //Version and Screen Info

    // Menu Labels
    public static void SpawnCenterLabel(){
        int theCenterLabelWidth = screenWidth/6;
        int theCenterLabelHeight = screenHeight/10;
        int theCenterLabelx = screenWidth/2 - theCenterLabelWidth/2;
        int theCenterLabely = 0;

        theCenterLabel = new JLabel();
        theCenterLabel.setHorizontalAlignment(0);
        theCenterLabel.setBounds (theCenterLabelx, theCenterLabely, theCenterLabelWidth, theCenterLabelHeight);
        theCenterLabel.setText("<html><center>Use WASD to move. <br/>C Toggles Controls Menu.</center></html>");
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
            ControlsMenuLabel.setText("<html>This is the controls menu.<br/><br/>WASD is used to move.<br/>Q toggles quick movement.<br/>B toggles debug mode.<br/>C toggles the controls menu.<br/>M toggles the center label.</html>");
            ControlsMenuLabel.setVisible(false);
            mainframe.add(ControlsMenuLabel);
    }
    public static void SpawnTradingMenu(){
        int theTradingMenuLabelWidth = screenWidth / 8;
        int theTradingMenuLabelHeight = screenHeight / 2;
        int theTradingMenuLabelx = 11*screenWidth / 12 - theTradingMenuLabelWidth / 2;
        int theTradingMenuLabely = screenHeight / 2 - theTradingMenuLabelHeight / 2;

        TradingMenuLabel = new JLabel();
        TradingMenuLabel.setHorizontalAlignment(0);
        TradingMenuLabel.setBounds(theTradingMenuLabelx, theTradingMenuLabely, theTradingMenuLabelWidth, theTradingMenuLabelHeight);
        TradingMenuLabel.setText("<html>This is the Trading menu!<br/><br/>The NPC is selling:<br/><br/>1: Item 1<br/>2: Item 2<br/>3: Item 3 </html>");
        TradingMenuLabel.setVisible(false);
        mainframe.add(TradingMenuLabel);
    }

    // Character and Islands Labels
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
        theIslandLabelWidth = rand.nextInt(screenWidth*2/16,screenWidth*4/16);
        theIslandLabelHeight = rand.nextInt(screenHeight*2/16,screenHeight*4/16);
        theIslandLabelx = rand.nextInt(0,screenWidth*11/12-theIslandLabelWidth);
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

    // Item Labels
    public static void SpawnFood(){
        theFoodLabelWidth = screenWidth/35;
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
    public static void SpawnNPC(){
        theNPCLabelWidth = screenWidth/50;
        theNPCLabelHeight = screenHeight/40;
        Random rand = new Random();
        if(IslandSpawned){
            theNPCLabelx = rand.nextInt(theIslandLabelx, theIslandLabelx + theIslandLabelWidth - theNPCLabelWidth);
            theNPCLabely = rand.nextInt(theIslandLabely + theNPCLabelHeight, theIslandLabely + theIslandLabelHeight - theCharacterLabelHeight);
        }
        else {
            theNPCLabelx = rand.nextInt(theNPCLabelWidth, screenWidth - theNPCLabelWidth);
            theNPCLabely = rand.nextInt(theNPCLabelHeight * 2, screenHeight);
        }

        theNPCLabel = new JLabel();
        theNPCLabel.setHorizontalAlignment(0);
        theNPCLabel.setBounds (theNPCLabelx, theNPCLabely, theNPCLabelWidth, theNPCLabelHeight);
        theNPCLabel.setText("NPC");
        mainframe.add(theNPCLabel);
    }
    public static void SpawnSunkenTreasure(){
        theSunkenTreasureLabelWidth = screenWidth/30;
        theSunkenTreasureLabelHeight = screenHeight/40;
        Random rand = new Random();
        if(IslandSpawned){
                theSunkenTreasureLabelx = rand.nextInt(theIslandLabelx, theIslandLabelx + theIslandLabelWidth - theSunkenTreasureLabelWidth);
                theSunkenTreasureLabely = rand.nextInt(theIslandLabely + theSunkenTreasureLabelHeight, theIslandLabely + theIslandLabelHeight - theCharacterLabelHeight);
        }
        else {
            theSunkenTreasureLabelx = rand.nextInt(theSunkenTreasureLabelWidth, screenWidth - theSunkenTreasureLabelWidth);
            theSunkenTreasureLabely = rand.nextInt(theSunkenTreasureLabelHeight * 2, screenHeight);
        }

        theSunkenTreasureLabel = new JLabel();
        theSunkenTreasureLabel.setHorizontalAlignment(0);
        theSunkenTreasureLabel.setBounds (theSunkenTreasureLabelx, theSunkenTreasureLabely, theSunkenTreasureLabelWidth, theSunkenTreasureLabelHeight);
        theSunkenTreasureLabel.setText("COIN?");
        theSunkenTreasureLabel.setVisible(false);
        mainframe.add(theSunkenTreasureLabel);
    }//invisible until close, gain between 0 and 10 pocket change upon collection.
    public static void SpawnMysteryFood(){
        theMysteryFoodLabelWidth = screenWidth/30;
        theMysteryFoodLabelHeight = screenHeight/40;
        Random rand = new Random();
        if(IslandSpawned){
                theMysteryFoodLabelx = rand.nextInt(theIslandLabelx, theIslandLabelx + theIslandLabelWidth - theMysteryFoodLabelWidth);
                theMysteryFoodLabely = rand.nextInt(theIslandLabely + theMysteryFoodLabelHeight, theIslandLabely + theIslandLabelHeight - theCharacterLabelHeight);
        }
        else {
            theMysteryFoodLabelx = rand.nextInt(theMysteryFoodLabelWidth, screenWidth - theMysteryFoodLabelWidth);
            theMysteryFoodLabely = rand.nextInt(theMysteryFoodLabelHeight * 2, screenHeight);
        }

        theMysteryFoodLabel = new JLabel();
        theMysteryFoodLabel.setHorizontalAlignment(0);
        theMysteryFoodLabel.setBounds (theMysteryFoodLabelx, theMysteryFoodLabely, theMysteryFoodLabelWidth, theMysteryFoodLabelHeight);
        theMysteryFoodLabel.setText("FOOD?");
        theMysteryFoodLabel.setVisible(false);
        mainframe.add(theMysteryFoodLabel);
    }//randomly lose up to half movement cap or gain up to 20 movement points upon collection.
    public static void AddItemSpawnProgression(){
        ItemSpawnProgression=true;
    }

    // Checking and Update Methods
    public void CheckAndUpdate(){
        CheckLoseConditions();
        UpdateScoreBoard();
        UpdateCharacter();
        CheckFoodLoc();
        CheckCoinLoc();
        CheckNPCLoc();
        CheckSunkenTreasureLoc();
        CheckMysteryFoodLoc();
        CheckItemProgression();
    }
    public static void UpdateScoreBoard(){
        theScoreLabel.setText("<html><center>Your score is " + Score + ". <br/> You made " + MovementsMade + " movements. <br/> You have " + (MovementCap - MovementsMade) + " movements left. <br/> You have " + PocketChange + " coins in pocket change.</center></html>");
    }
    public static void UpdateCharacter(){
        if(!Locked) {
            theCharacterLabel.setBounds(theCharacterLabelx, theCharacterLabely, theCharacterLabelWidth, theCharacterLabelHeight);
        }
        else {theCenterLabel.setText("<html><center>You ran out of moves.<br/>Game Over.<br/>Press F12 to Exit. </center><html>");}
    }
    public void CheckBoundaries(){
        if(IslandSpawned){
            UpperBoundaryReached = theCharacterLabely - MovementDisty < theIslandLabely;
            LeftBoundaryReached = theCharacterLabelx - MovementDistx < theIslandLabelx;
            BottomBoundaryReached = theCharacterLabely + MovementDisty > theIslandLabely + theIslandLabelHeight - theCharacterLabelHeight;
            RightBoundaryReached = theCharacterLabelx + MovementDistx > theIslandLabelx + theIslandLabelWidth - theCharacterLabelWidth;
            BoundaryReached = UpperBoundaryReached || LeftBoundaryReached || BottomBoundaryReached || RightBoundaryReached;

            if(SecondIslandSpawned){
                if(theSecondIslandLabelx<theIslandLabelx){
                    LeftBoundaryReached = theCharacterLabelx - MovementDistx < theSecondIslandLabelx;
                }
                if(theSecondIslandLabelx+theSecondIslandLabelWidth<theIslandLabelx+theIslandLabelWidth){
                    LeftBoundaryReached = theCharacterLabelx - MovementDistx < theSecondIslandLabelx;
                }
                if(theSecondIslandLabely<theIslandLabely) {
                    UpperBoundaryReached = theCharacterLabely - MovementDisty < theSecondIslandLabely;
                }
                if(theSecondIslandLabely+theSecondIslandLabelHeight>theIslandLabely+theIslandLabelHeight) {
                    UpperBoundaryReached = theCharacterLabely - MovementDisty > theSecondIslandLabely;
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

            theCenterLabel.setText("<html><center>You collected the food! <br/> Score and movement cap increased.</center></html>");

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

            theCenterLabel.setText("<html><center>You collected some pocket change! <br/> Score and movement cap increased.</center></html>");

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
    public void CheckNPCLoc() {
        if (abs((theCharacterLabelx + theCharacterLabelWidth) / 2 - (theNPCLabelx + theNPCLabelWidth) / 2) < 10 && abs((theCharacterLabely + theCharacterLabelHeight) / 2 - (theNPCLabely + theNPCLabelHeight) / 2) < 10) {
            TradingMenuLabel.setVisible(true);
            TradingMenuVisibility = true;
            NearNPC = true;
            theCenterLabel.setText("<html><center>You're near an NPC! <br/> Click T to toggle their trade offers.</center></html>");
        } else {
            if (NearNPC) {
                PocketChange=PocketChange+5;
                UpdateScoreBoard();

                Random rand = new Random();
                if(IslandSpawned){
                    theNPCLabelx = rand.nextInt(theIslandLabelx+theNPCLabelWidth,theIslandLabelx+theIslandLabelWidth-theNPCLabelWidth);
                    theNPCLabely = rand.nextInt(theIslandLabely+theNPCLabelHeight,theIslandLabely+theIslandLabelHeight-theNPCLabelHeight);
                }
                else {
                    theNPCLabelx = rand.nextInt(theNPCLabelWidth, screenWidth - 2 * theNPCLabelWidth);
                    theNPCLabely = rand.nextInt(theNPCLabelHeight, screenHeight - 2 * theNPCLabelHeight);
                }
                theNPCLabel.setBounds(theNPCLabelx,theNPCLabely,theNPCLabelWidth,theNPCLabelHeight);


                /*theNPCLabelx = theNPCLabely = 0;
                mainframe.remove(theNPCLabel);
                mainframe.revalidate();
                mainframe.repaint();*/

            }
            TradingMenuLabel.setVisible(false);
            TradingMenuVisibility = false;
            NearNPC = false;
        }
    }
    public void CheckSunkenTreasureLoc(){
        if(abs((theCharacterLabelx+theCharacterLabelWidth)/2-(theSunkenTreasureLabelx+theSunkenTreasureLabelWidth)/2)<10&&abs((theCharacterLabely+theCharacterLabelHeight)/2-(theSunkenTreasureLabely+theSunkenTreasureLabelHeight)/2)<30){
            theSunkenTreasureLabel.setVisible(true);
        }
        if(abs((theCharacterLabelx+theCharacterLabelWidth)/2-(theSunkenTreasureLabelx+theSunkenTreasureLabelWidth)/2)<10&&abs((theCharacterLabely+theCharacterLabelHeight)/2-(theSunkenTreasureLabely+theSunkenTreasureLabelHeight)/2)<10){

            Random CoinAmount = new Random();
            int SunkenTreasureWorth = CoinAmount.nextInt(0,10);

            PocketChange = PocketChange + SunkenTreasureWorth;
            UpdateScoreBoard();

            theCenterLabel.setText("<html><center>You found a small treasure!</center></html>");

            Random rand = new Random();
            if(IslandSpawned){
                theSunkenTreasureLabelx = rand.nextInt(theIslandLabelx+theSunkenTreasureLabelWidth,theIslandLabelx+theIslandLabelWidth-theSunkenTreasureLabelWidth);
                theSunkenTreasureLabely = rand.nextInt(theIslandLabely+theSunkenTreasureLabelHeight,theIslandLabely+theIslandLabelHeight-theSunkenTreasureLabelHeight);
            }
            else {
                theSunkenTreasureLabelx = rand.nextInt(theSunkenTreasureLabelWidth, screenWidth - 2 * theSunkenTreasureLabelWidth);
                theSunkenTreasureLabely = rand.nextInt(theSunkenTreasureLabelHeight, screenHeight - 2 * theSunkenTreasureLabelHeight);
            }
            theSunkenTreasureLabel.setVisible(false);
            theSunkenTreasureLabel.setBounds(theSunkenTreasureLabelx,theSunkenTreasureLabely,theSunkenTreasureLabelWidth,theSunkenTreasureLabelHeight);
        }
    }
    public void CheckMysteryFoodLoc(){
        if(abs((theCharacterLabelx+theCharacterLabelWidth)/2-(theMysteryFoodLabelx+theMysteryFoodLabelWidth)/2)<10&&abs((theCharacterLabely+theCharacterLabelHeight)/2-(theMysteryFoodLabely+theMysteryFoodLabelHeight)/2)<30){
            theMysteryFoodLabel.setVisible(true);
        }
        if(abs((theCharacterLabelx+theCharacterLabelWidth)/2-(theMysteryFoodLabelx+theMysteryFoodLabelWidth)/2)<10&&abs((theCharacterLabely+theCharacterLabelHeight)/2-(theMysteryFoodLabely+theMysteryFoodLabelHeight)/2)<10){

            Random FoodAmount = new Random();
            int MysteryFoodWorth = FoodAmount.nextInt(-((MovementCap - MovementsMade)/2),20);

            MovementCap = MovementCap + MysteryFoodWorth;
            UpdateScoreBoard();

            theCenterLabel.setText("<html><center>You ate some mystery food you found...</center></html>");

            Random rand = new Random();
            if(IslandSpawned){
                theMysteryFoodLabelx = rand.nextInt(theIslandLabelx+theMysteryFoodLabelWidth,theIslandLabelx+theIslandLabelWidth-theMysteryFoodLabelWidth);
                theMysteryFoodLabely = rand.nextInt(theIslandLabely+theMysteryFoodLabelHeight,theIslandLabely+theIslandLabelHeight-theMysteryFoodLabelHeight);
            }
            else {
                theMysteryFoodLabelx = rand.nextInt(theMysteryFoodLabelWidth, screenWidth - 2 * theMysteryFoodLabelWidth);
                theMysteryFoodLabely = rand.nextInt(theMysteryFoodLabelHeight, screenHeight - 2 * theMysteryFoodLabelHeight);
            }
            theMysteryFoodLabel.setVisible(false);
            theMysteryFoodLabel.setBounds(theMysteryFoodLabelx,theMysteryFoodLabely,theMysteryFoodLabelWidth,theMysteryFoodLabelHeight);
        }
    }
    public void CheckItemProgression(){
        if(ItemSpawnProgression&&!NewItemSpawned&&Score!=0&&Score%10==0){
            Random rand = new Random();
            int ItemSelector = rand.nextInt(0,4);
            switch (ItemSelector){
                case 0 -> {
                    SpawnFood();
                    mainframe.repaint();
                    mainframe.revalidate();
                    theCenterLabel.setText("Food Spawned");
                }
                case 1 -> {
                    SpawnCoin();
                    mainframe.repaint();
                    mainframe.revalidate();
                    theCenterLabel.setText("Coin Spawned");
                }
                case 2 -> {
                    SpawnNPC();
                    mainframe.repaint();
                    mainframe.revalidate();
                    theCenterLabel.setText("NPC Spawned");
                }
                case 3 -> {
                    SpawnMysteryFood();
                    mainframe.repaint();
                    mainframe.revalidate();
                    theCenterLabel.setText("Mystery Food Spawned");
                }
                case 4 -> {
                    SpawnSunkenTreasure();
                    mainframe.repaint();
                    mainframe.revalidate();
                    theCenterLabel.setText("Sunken Treasure Spawned");
                }
                default -> theCenterLabel.setText("No Item Spawned");
            }
            NewItemSpawned = true;
        }
        if (Score%10!=0){NewItemSpawned=false;}
    }
    public void CheckLoseConditions(){
        if(MovementsMade>=MovementCap){
            Locked = true;
        }
        if(MovementsMade<MovementCap){
            Locked = false;
        }
    }

    // Misc. Methods
    public static void MainframeTitleSetter(){
        Random rand = new Random();
        int TitleSelector = rand.nextInt(0,9);
        switch (TitleSelector){
            case 0 -> mainframe.setTitle("*Insert Default Title*");
            case 1 -> mainframe.setTitle("This is, in fact, the main frame.");
            case 2 -> mainframe.setTitle("I don't remember this window...");
            case 3 -> mainframe.setTitle("Yeah, so here's a virtual container.");
            case 4 -> mainframe.setTitle("Another day, another game.");
            case 5 -> mainframe.setTitle("Hello :) Have a nice day");
            case 6 -> mainframe.setTitle("Aaaaaahhhh!!!!");
            case 7 -> mainframe.setTitle("Welp... Here we go again...");
            case 8 -> mainframe.setTitle("Was this an error?");
            default -> mainframe.setTitle("This was probably an error...");
        }
    }
    public static void CenterIsland(){
        theIslandLabelx=screenWidth/2-(theIslandLabelWidth/2);
        theIslandLabely=screenHeight/2-(theIslandLabelHeight/2);
    }
    public static void UpdatetoScreenSize(){

    }

    @Override
    public void keyTyped (KeyEvent e) {}

    @Override
    public void keyPressed (KeyEvent e){
        if(!KeyPressed||QuickMovement) {
            keyCode = e.getKeyCode();
            KeyPressed=true;

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

                //Non-Movement In-Game Controls
                case KeyEvent.VK_C -> {
                    if(ControlsMenuVisibility){
                        ControlsMenuLabel.setVisible(false);
                        ControlsMenuVisibility =false;
                    }
                    else{
                        ControlsMenuLabel.setVisible(true);
                        ControlsMenuVisibility =true;
                    }
                } //Toggles Controls Menu
                case KeyEvent.VK_B -> DebugMode= !DebugMode; //Toggles Debug Mode
                case KeyEvent.VK_Q -> QuickMovement = !QuickMovement; //Toggles Quick Movement
                case KeyEvent.VK_T -> {
                    if (NearNPC && TradingMenuVisibility) {
                        TradingMenuLabel.setVisible(false);
                        TradingMenuVisibility = false;
                    }
                        else if(NearNPC){
                        TradingMenuLabel.setVisible(true);
                        TradingMenuVisibility = true;
                    }
                    else {
                        theCenterLabel.setText("You are not near an NPC.");
                    }
                } //Toggles Trading Menu
                case KeyEvent.VK_M -> {
                    if(CenterLabelVisibility){
                        theCenterLabel.setVisible(false);
                        CenterLabelVisibility =false;
                    }
                    else{
                        theCenterLabel.setVisible(true);
                        CenterLabelVisibility =true;
                    }
                } //Toggles Middle Menu

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

                //Fullscreen Toggle ?
                case KeyEvent.VK_F1 -> {
                    if(screenWidth == (int)size.getWidth() && screenHeight == (int)size.getHeight()){
                        screenWidth = screenWidth/2;
                        screenHeight = screenHeight/2;
                        mainframe.setSize(screenWidth, screenHeight);

                    }
                    else {
                        screenWidth = (int)size.getWidth();
                        screenHeight = (int)size.getHeight();
                        mainframe.setSize(screenWidth, screenHeight);
                    }
                }

                //General Key Events
                case KeyEvent.VK_SHIFT -> theCenterLabel.setText("SHIFT Key Pressed");
                case KeyEvent.VK_CAPS_LOCK -> theCenterLabel.setText("CAPS LOCK Key Pressed");
                case KeyEvent.VK_CONTROL -> theCenterLabel.setText("CONTROL Key Pressed");
                case KeyEvent.VK_BACK_SPACE -> theCenterLabel.setText("BACKSPACE Key Pressed");
                case KeyEvent.VK_ENTER -> theCenterLabel.setText("ENTER Key Pressed");
                case KeyEvent.VK_SPACE -> theCenterLabel.setText("SPACE Key Pressed");
                //case KeyEvent.VK_F1 -> theCenterLabel.setText("F1 Key Pressed");
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
                }//Exits Program
                //default -> theCenterLabel.setText(e.getKeyChar() + " Key Pressed");
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case KeyEvent.VK_B -> {
                if(DebugMode) {
                    theCenterLabel.setText("Debug Mode Enabled.");
                }
                else {
                    theCenterLabel.setText("Debug Mode Disabled.");
                }
            }
            case KeyEvent.VK_C -> {
                if(ControlsMenuVisibility) {
                    theCenterLabel.setText("Controls Menu Opened.");
                }
                else {
                    theCenterLabel.setText("Controls Menu Closed.");
                }
            }
            case KeyEvent.VK_Q -> {
                if(QuickMovement) {
                    theCenterLabel.setText("Quick Movement Enabled.");
                }
                else {
                    theCenterLabel.setText("Quick Movement Disabled.");
                }
            }
            case KeyEvent.VK_T -> {
                if(NearNPC&&TradingMenuVisibility) {
                    theCenterLabel.setText("Trading Menu Opened");
                }
                if (NearNPC&&!TradingMenuVisibility){
                    theCenterLabel.setText("Trading Menu Closed.");
                }
            }
            case KeyEvent.VK_M -> {
                if(CenterLabelVisibility) {
                    theCenterLabel.setText("Center Label Opened.");
                }
            }
            //default -> theCenterLabel.setText ("Press F12 to exit.");
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

    // Sets up the mainframe, including the size, title, and contents.
    public static void initializeMainframe(){

        TheMainDisplay D = new TheMainDisplay();

        mainframe.setSize(screenWidth, screenHeight);
        MainframeTitleSetter();
        mainframe.addKeyListener(D);
        mainframe.addMouseListener(D);

        SpawnCenterLabel();
        SpawnControlsMenuLabel();
        SpawnScoreBoard();
        SpawnTradingMenu();
        SpawnIsland();
        //SpawnSecondIsland();
        SpawnCharacter();
        SpawnFood();
        AddItemSpawnProgression();
       /* SpawnCoin();
        SpawnNPC();
        SpawnSunkenTreasure();
        SpawnMysteryFood();*/

        mainframe.setUndecorated(true);
        mainframe.add(D);
        mainframe.setVisible(true);
        mainframe.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {initializeMainframe();}
}