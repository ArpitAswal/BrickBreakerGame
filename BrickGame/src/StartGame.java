
import java.awt.event.*;

import javax.swing.*;

import java.awt.*;

import javax.swing.Timer;

 
public class StartGame extends JPanel implements KeyListener, ActionListener // we can also inherit JComponent then we don't need to create instance of JComponent
//keyListener and ActionListener both are interfaces and class implements interfaces
//KeyListener is for the detecting the arrow(when we move the slider by pressing arrow keys) and actionListener is for moving the ball.        
{

    private boolean play = false;//to show that our game is not start yet
    private int score = 0;//initiallly score is zero which is common in all games

    private int totalBricks = 48;//no of bricks in game which we have to break by ball to win a game

    private final Timer timer,timer2; //this is use for setting the time of ball that how fast it should move and timer2 is for to see the time(to win the game how much second user takes).
    // private final int delay =1;
//  //The delay parameter is used to set both the initial delay and the delay between event firing, in milliseconds. Once the timer has been started, it waits for the initial delay before firing its first ActionEvent to registered listeners. 

    private int playerX = 680;//slider starting x coordinate
    private int ballposX = 723;//starting position of ball on xaxis(horizontal way)
    private int ballposY = 613;//starting position of ball on yaxis(vertical way)
    private int ballXdir = -1;//ball moving on x-direction
    private int ballYdir = -2;//ball moving on y-direction

    private MapGenerator mg;
    int count=0;
    public StartGame() {
        mg = new MapGenerator(4, 12);
        addKeyListener(this);
        setFocusable(true);
        //setFocusable() is actually a method from the Component class in Swing. public void setFocusable(boolean focusable) It lets the component (in your case, JPanel which extends Component ) have the power of getting focused.            
        setFocusTraversalKeysEnabled(false);
        //setFocusTraversalKeysEnabled() decides whether or not focus traversal keys (TAB key, SHIFT+TAB, etc.) are allowed to be used when the current Component has focus.            
        timer = new Timer(1, this);
        timer.start();
        timer2 = new Timer(650, (ActionEvent e) -> {
            count++;
        });
        timer2.setInitialDelay(0);
        timer2.start();
    }
   
//The Canvas class controls and represents a blank rectangular area where the application can draw or trap input events from the user. It inherits the Component class.

    @Override
    public void paint(Graphics g) //It paints the canvas with given Graphics object.
    {
        // background
        g.setColor(Color.black);//when we add this graphics on frame then background color see as black
        g.fillRect(0, 0, 1355, 680);//is used to fill rectangle with the default color and specified width and height.

        // drawing mg
        mg.draw((Graphics2D) g);

        //borders of game screen
        g.setColor(Color.CYAN);
        g.fillRect(0, 0, 4, 680);//leftborder
        g.fillRect(0, 0, 1355, 4);//upperborder
        g.fillRect(1351, 0, 4, 680);//rightborder

        // the paddle
        g.setColor(Color.red);
        g.fillRect(playerX, 640, 105, 10);

        // the ball
        g.setColor(Color.YELLOW);
        g.fillOval(ballposX, ballposY, 25, 25);

        // the scores and time		
        g.setColor(Color.green);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, 1230, 30);
        g.drawString("Time: "+count,1230,55);

        // when we won the game or lose the game
        if (totalBricks == 0 || ballposY > 670) {
            play = false;
            timer2.stop();//stop the time whenever we win the game or lose the game
            ballXdir = 0;
            ballYdir = 0;

            g.setFont(new Font("serif", Font.BOLD, 30));
            if (totalBricks == 0) {
                g.setColor(Color.BLUE);
                g.drawString("You Won", 610, 320);
            } else {
                g.setColor(Color.RED);
                g.drawString("You Lose, Your Scores: " + score, 540, 320);
            }

            g.setColor(Color.GREEN);
            g.drawString("Press (Enter) to Restart", 570, 360);
        }

        g.dispose();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            //right arrow key
            case KeyEvent.VK_RIGHT:
                if (playerX >= 1245)//this is the last x coordinate, after this coordinate paddle can't move further.
                {
                    playerX = 1245;
                } else {
                    moveRight();//to move paddle right side
                }
                break;
            case KeyEvent.VK_LEFT:
                if (playerX <= 5)//this is the last x coordinate, before this coordinate paddle can't move further.
                {
                    playerX = 5;
                } else {
                    moveLeft();//to move paddle left side
                }
                break;
            //press enter to play the game
            case KeyEvent.VK_ENTER:
                if (!play) {              //this mainly use after losing the game to restart the game
                    play = true;
                    ballposX = 723;
                    ballposY = 613;
                    ballXdir = -1;
                    ballYdir = -2;
                    playerX = 680;
                    score = 0;
                    totalBricks = 48;
                    mg = new MapGenerator(4, 12);

                    repaint();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //timer.start();
        // if (play) {
        
        //if ball touch panel less than its half length then ball should be move left side
        if (new Rectangle(ballposX, ballposY, 25, 25).intersects(new Rectangle(playerX, 640, 50, 10))) {
            ballYdir = -ballYdir;
            ballXdir = -2;
        } //if ball touch panel more than its half length then ball should be move right side 
        else if (new Rectangle(ballposX, ballposY, 25, 25).intersects(new Rectangle(playerX + 55, 640, 50, 10))) {
            ballYdir = -ballYdir;
            if (ballXdir < 0) {
                ballXdir = 2;
            }
        } //if ball touch pannel almost its half length then ball should be move almost straight up
        else if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX + 50, 640, 5, 10))) {
            ballYdir = -ballYdir;
            ballXdir = -1;
        }

        // check brick collision with the ball	
        //here A is a label so when we have to exit both loop we break label
        A:
        for (int i = 0; i < mg.map.length; i++) {
            for (int j = 0; j < mg.map[0].length; j++) {
                if (mg.map[i][j] > 0) {
                    Rectangle ballRect = new Rectangle(ballposX, ballposY, 25, 25);
                    Rectangle brickRect = new Rectangle(j * mg.brickWidth + 220, i * mg.brickHeight + 50, mg.brickWidth, mg.brickHeight);

                    if (ballRect.intersects(brickRect)) {
                        mg.setBrickValue(0, i, j);
                        score += 5;
                        totalBricks--;

                        // when ball hit right or left of brick
                        if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
                            ballXdir = -ballXdir;
                        } // when ball hits top or bottom of brick
                        else {
                            ballYdir = -ballYdir;
                        }

                        break A;
                    }
                }
            }
        }

        ballposX += ballXdir;
        ballposY += ballYdir;

        if (ballposX < 5)//if ball touch left border
        {
            ballXdir = -ballXdir;
        } else if (ballposY < 5)//if ball touch upper border
        {
            ballYdir = -ballYdir;
        } else if (ballposX > 1328)//if ball touch right border
        {
            ballXdir = -ballXdir;
        }
        //}
        repaint();// recall the paint to re-drawn the screen(because of moving pannel and ball) 
    }

    private void moveRight() {
        play = true;
        playerX += 15;// move the pannel by 15 incrementing x-coordinate in right direction
    }

    private void moveLeft() {
        play = true;
        playerX -= 15;// move the pannel by 15 deccrementing x-coordinate in left direction
    }
}
