//RYAN HUELSMAN
//MR YEE PEROID 2
//
//DONE//1) Allow rocket to only move right.  Initial and
//      minimum speed of the rocket will be positive one.
//
//DONE//2) Use the variables numMaxLives and currentNumLives to
//   allow the rocket to have multiple lives.  Each time
//   the rocket runs into a yellow star, the rocket will
//   lose one life.  Display game over when the rocket
//   has no more lives.  Pause game when game over.  
//
//3) Display rocket images to show how many lives
//   are left.
//
//DONE//4) Add to method readFile(), the name value pair
//   for NumMaxLives.  This will set the maximum
//   number of lives that the rocket can have.  Even if
//   info.txt has a value for NumMaxLives that is less
//   than 1 or greater than 8, don't allow numMaxLives 
//   to be smaller than 1 or larger than 8.
//
//DONE//5) Complete Star class.  Constructor will receive 2 values.
//   The first value is the window width and the second
//   value is the window height.  The constructor should
//   initialize xpos and ypos to random values.
//
//6) An enemy star will be yellow and non-enemy star
//   will be blue.  stars[0] will be the only non-enemy
//   star.  When the rocket runs into an enemy star,
//   the rocket will lose one life.  When the rocket
//   runs into a non-enemy star, the rocket will add
//   one life.  The rocket can not add more lives than
//   numMaxLives.

package spaceshiptest;

import java.io.*;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;

public class SpaceshipTest extends JFrame implements Runnable {
    static final int WINDOW_WIDTH = 420;
    static final int WINDOW_HEIGHT = 445;
    final int XBORDER = 20;
    final int YBORDER = 20;
    final int YTITLE = 25;
    boolean animateFirstTime = true;
    int xsize = -1;
    int ysize = -1;
    Image image;
    Graphics2D g;

    Image outerSpaceImage;

//Variables for rocket.
    Image rocketImage;
    int rocketXPos;
    int rocketYPos;    
    int rocketXSpeed;
    int rocketYSpeed;
    boolean rocketRight;
    int rocketMaxSpeed;

//add or modify.  Code already added for you.
    Star stars[]= new Star[Star.num];
//Variables for missiles.
    Missile missiles[] = new Missile[Missile.numMissiles] ;
    
//add or modify.  Code already added for you.
    int numMaxLives = 4;
    int currentNumLives;
    boolean gameOver;
    
    static SpaceshipTest frame;
    public static void main(String[] args) {
        frame = new SpaceshipTest();
        frame.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public SpaceshipTest() {
        addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                 if (e.BUTTON3 == e.getButton()) {
                    //right button
                    reset();
                }
                 
                 if (gameOver)
            return;
        
                 if (e.BUTTON1 == e.getButton()) {
                    //left button

// location of the cursor.
                    int xpos = e.getX();
                    int ypos = e.getY();

                }
               
                repaint();
            }
        });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseDragged(MouseEvent e) {
        repaint();
      }
    });

    addMouseMotionListener(new MouseMotionAdapter() {
      public void mouseMoved(MouseEvent e) {

        repaint();
      }
    });

        addKeyListener(new KeyAdapter() {

            public void keyPressed(KeyEvent e) {
//Arrow keys that affect the speed of the rocket.
                if (e.VK_G == e.getKeyCode()) {
                    gameOver = true;
                }
                
                if (e.VK_UP == e.getKeyCode()) {
                    rocketYSpeed++;
                } else if (e.VK_DOWN == e.getKeyCode()) {
                    rocketYSpeed--;
                } else if (e.VK_LEFT == e.getKeyCode()) {
                    rocketXSpeed--;
                    if (rocketXSpeed < 1)
                        rocketXSpeed = 1;
                } else if (e.VK_RIGHT == e.getKeyCode())
                {
                    rocketXSpeed++;
                    if (rocketXSpeed > rocketMaxSpeed)
                        rocketXSpeed = rocketMaxSpeed;
                }
                else if (e.VK_SPACE == e.getKeyCode()) {
//Fire a missile.
                    missiles[Missile.current].active = true;
                    missiles[Missile.current].xpos = rocketXPos;
                    missiles[Missile.current].ypos = rocketYPos;
                    missiles[Missile.current].right = rocketRight;
                    Missile.current++;
                    if (Missile.current >= Missile.numMissiles)
                        Missile.current = 0;
                }
                repaint();
            }
        });
        init();
        start();
    }
    Thread relaxer;
////////////////////////////////////////////////////////////////////////////
    public void init() {
        requestFocus();
    }
////////////////////////////////////////////////////////////////////////////
    public void destroy() {
    }



////////////////////////////////////////////////////////////////////////////
    public void paint(Graphics gOld) {
        if (image == null || xsize != getSize().width || ysize != getSize().height) {
            xsize = getSize().width;
            ysize = getSize().height;
            image = createImage(xsize, ysize);
            g = (Graphics2D) image.getGraphics();
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
        }
//fill background
        g.setColor(Color.cyan);
        g.fillRect(0, 0, xsize, ysize);

        int x[] = {getX(0), getX(getWidth2()), getX(getWidth2()), getX(0), getX(0)};
        int y[] = {getY(0), getY(0), getY(getHeight2()), getY(getHeight2()), getY(0)};
//fill border
        g.setColor(Color.white);
        g.fillPolygon(x, y, 4);
// draw border
        g.setColor(Color.red);
        g.drawPolyline(x, y, 5);

        if (animateFirstTime) {
            gOld.drawImage(image, 0, 0, null);
            return;
        }
//Display the background image.
        g.drawImage(outerSpaceImage,getX(0),getY(0),
                getWidth2(),getHeight2(),this);
//Display the stars.
        
        for (int i=0;i<Star.num;i++)
        {
           g.setColor(Color.yellow);
           
            if(stars[i].enemy= false)
             g.setColor(Color.blue);
            
            drawCircle(getX(stars[i].XPos),getYNormal(stars[i].YPos),
                0.0,1.0,1.0 );
        }
//Display the missiles.
        g.setColor(Color.white);
        for (int i=0;i<missiles.length;i++)
        {
            if (missiles[i].active)
                drawCircle(getX(missiles[i].xpos),getYNormal(missiles[i].ypos),
                0.0,0.3,0.3 );
        }        
//Display the rocket.
        if (rocketRight)
            drawRocket(rocketImage,getX(rocketXPos),getYNormal(rocketYPos),0.0,1.0,1.0 );
        else
            drawRocket(rocketImage,getX(rocketXPos),getYNormal(rocketYPos),0.0,-1.0,1.0 );
        
        if(currentNumLives > 1)
        {
            drawRocket(rocketImage,getX(10),getYNormal(getHeight2() + 10),0.0,1.0,1.0 );
        }
        if(currentNumLives > 2)
        {
            drawRocket(rocketImage,getX(40),getYNormal(getHeight2() + 10),0.0,1.0,1.0 );
        }
        if(currentNumLives > 3)
        {
            drawRocket(rocketImage,getX(70),getYNormal(getHeight2() + 10),0.0,1.0,1.0 );
        }
        if(currentNumLives > 4)
        {
            drawRocket(rocketImage,getX(100),getYNormal(getHeight2() + 10),0.0,1.0,1.0 );
        }
        if(currentNumLives > 5)
        {
            drawRocket(rocketImage,getX(120),getYNormal(getHeight2() + 10),0.0,1.0,1.0 );
        }
        if(currentNumLives > 6)
        {
            drawRocket(rocketImage,getX(150),getYNormal(getHeight2() + 10),0.0,1.0,1.0 );
        }
        if(currentNumLives > 7)
        {
            drawRocket(rocketImage,getX(180),getYNormal(getHeight2() + 10),0.0,1.0,1.0 );
        }
        if(currentNumLives > 8)
        {
            drawRocket(rocketImage,getX(210),getYNormal(getHeight2() + 10),0.0,1.0,1.0 );
        }
        g.setColor(Color.white);
        g.setFont(new Font("Impact",Font.BOLD,40));
            g.drawString("Lives = "  + currentNumLives, 70, 250);
//add or modify.  Code already added for you.
        if (gameOver)
        {
            g.setColor(Color.white);
            g.setFont(new Font("Impact",Font.BOLD,60));
            g.drawString("GAME OVER", 70, 250);
        }        
        
        
        gOld.drawImage(image, 0, 0, null);
    }
////////////////////////////////////////////////////////////////////////////
    public void drawCircle(int xpos,int ypos,double rot,double xscale,double yscale)
    {
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        
        g.fillOval(-10,-10,20,20);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
////////////////////////////////////////////////////////////////////////////
    public void drawRocket(Image image,int xpos,int ypos,double rot,double xscale,
            double yscale) {
        int width = rocketImage.getWidth(this);
        int height = rocketImage.getHeight(this);
        g.translate(xpos,ypos);
        g.rotate(rot  * Math.PI/180.0);
        g.scale( xscale , yscale );

        g.drawImage(image,-width/2,-height/2,
        width,height,this);

        g.scale( 1.0/xscale,1.0/yscale );
        g.rotate(-rot  * Math.PI/180.0);
        g.translate(-xpos,-ypos);
    }
////////////////////////////////////////////////////////////////////////////
// needed for     implement runnable
    public void run() {
        while (true) {
            animate();
            repaint();
            double seconds = 0.04;    //time that 1 frame takes.
            int miliseconds = (int) (1000.0 * seconds);
            try {
                Thread.sleep(miliseconds);
            } catch (InterruptedException e) {
            }
        }
    }
/////////////////////////////////////////////////////////////////////////
    public void reset() {
//Init rocket values.
//init the location of the rocket to the center.
        rocketXPos = getWidth2()/2;
        rocketYPos = getHeight2()/2;
        rocketXSpeed = 1;
        rocketYSpeed = 0;
        rocketRight = true;
        rocketMaxSpeed = 10;
        currentNumLives = numMaxLives;
        gameOver = false;
//Init star values.
        for (int i=0;i<Star.num;i++)
        {
            stars[i] = new Star(getWidth2(), getHeight2());
        }
//add or modify.  Uncomment the line below.
       stars[1].enemy = false;
        
//Init missile values.        
        Missile.current = 0;
        for (int i=0;i<missiles.length;i++)
        {
            missiles[i] = new Missile();
        }
        
    }
/////////////////////////////////////////////////////////////////////////
    public void animate() {
        if (animateFirstTime) {
            animateFirstTime = false;
            if (xsize != getSize().width || ysize != getSize().height) {
                xsize = getSize().width;
                ysize = getSize().height;
            }
            readFile();            
            outerSpaceImage = Toolkit.getDefaultToolkit().getImage("./outerSpace.jpg");
            rocketImage = Toolkit.getDefaultToolkit().getImage("./rocket.GIF");
            reset();
        }
        
        
        if (gameOver)
            return;
        
//Update star movement variables.
        for (int i=0;i<Star.num;i++)
        {
            stars[i].XPos -= rocketXSpeed;
            if (stars[i].XPos < 0)
            {
                stars[i].YPos = (int)(Math.random()*getHeight2());
                stars[i].XPos = getWidth2();
            }
            else if (stars[i].XPos > getWidth2())
            {
                stars[i].YPos = (int)(Math.random()*getHeight2());
                stars[i].XPos = 0;
                
            }
        }
        
//Update rocket movement variables.
        if (rocketXSpeed > 0)
        {
            rocketRight = true;
        }
        else if (rocketXSpeed < 0)
        {
            rocketRight = false;
        }
        rocketYPos += rocketYSpeed;
        if (rocketYPos < 0)
        {
            rocketYSpeed = 0;
            rocketYPos = 0;
        }
        else if (rocketYPos > getHeight2())
        {
            rocketYSpeed = 0;
            rocketYPos = getHeight2();
        }
//Code for rocket running into stars.
        boolean hit = false;
        for (int i=0;i<Star.num;i++)
        {
            if (!hit && rocketXPos + 10 > stars[i].XPos && 
            rocketXPos - 10 < stars[i].XPos &&
            rocketYPos + 10 > stars[i].YPos &&
            rocketYPos - 10 < stars[i].YPos)
            {
                hit = true;
                if (i != Star.whichStarHit)
                {
                    Star.whichStarHit = i;
                     currentNumLives--;
                }
            }
        }
        if (!hit)
            Star.whichStarHit = -1;
        
        if(currentNumLives < 1)
            gameOver = true;
        
//Update missile movement variables.
        for (int i=0;i<missiles.length;i++)
        {
            if (missiles[i].active)
            {
                if (missiles[i].right)
                    missiles[i].xpos++;
                else
                    missiles[i].xpos--;                    
            }            
        }
//Code for missiles hitting stars.
        for (int i=0;i<missiles.length;i++)
        {
            for (int j=0;j<Star.num;j++)
            {
                if (missiles[i].active && missiles[i].xpos+10 > stars[j].XPos &&
                missiles[i].xpos-10 < stars[j].XPos &&
                missiles[i].ypos+10 > stars[j].YPos &&
                missiles[i].ypos-10 < stars[j].YPos)
                {
                     missiles[i].active = false;
                     if (rocketRight)
                     {
                         stars[j].YPos = (int)(Math.random()*getHeight2());
                         stars[j].YPos = getWidth2();
                     }
                     else
                     {
                         stars[j].YPos = (int)(Math.random()*getHeight2());
                         stars[j].XPos = 0;

                     }
                }   
            }
        }   
              
    }

////////////////////////////////////////////////////////////////////////////
    public void start() {
        if (relaxer == null) {
            relaxer = new Thread(this);
            relaxer.start();
        }
    }
////////////////////////////////////////////////////////////////////////////
    public void stop() {
        if (relaxer.isAlive()) {
            relaxer.stop();
        }
        relaxer = null;
    }
/////////////////////////////////////////////////////////////////////////
    public int getX(int x) {
        return (x + XBORDER);
    }

    public int getY(int y) {
        return (y + YBORDER + YTITLE);
    }

    public int getYNormal(int y) {
        return (-y + YBORDER + YTITLE + getHeight2());
    }
    
    public int getWidth2() {
        return (xsize - getX(0) - XBORDER);
    }

    public int getHeight2() {
        return (ysize - getY(0) - YBORDER);
    }
    

    public void readFile() {
        try {
            String inputfile = "info.txt";
            BufferedReader in = new BufferedReader(new FileReader(inputfile));
            String line = in.readLine();
            while (line != null) {
                String newLine = line.toLowerCase();
                if (newLine.startsWith("numstars"))
                {
                    String numStarsString = newLine.substring(9);
                    Star.num = Integer.parseInt(numStarsString.trim());
                }
                 if (newLine.startsWith("nummaxlives"))
                {
                    String numStarsString = newLine.substring(11);
                    numMaxLives = Integer.parseInt(numStarsString.trim());
                }

                line = in.readLine();
            }
            
            in.close();
        } catch (IOException ioe) {
        }
    }
    
}

class sound implements Runnable {
    Thread myThread;
    File soundFile;
    public boolean donePlaying = false;
    sound(String _name)
    {
        soundFile = new File(_name);
        myThread = new Thread(this);
        myThread.start();
    }
    public void run()
    {
        try {
        AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
        AudioFormat format = ais.getFormat();
    //    System.out.println("Format: " + format);
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine source = (SourceDataLine) AudioSystem.getLine(info);
        source.open(format);
        source.start();
        int read = 0;
        byte[] audioData = new byte[16384];
        while (read > -1){
            read = ais.read(audioData,0,audioData.length);
            if (read >= 0) {
                source.write(audioData,0,read);
            }
        }
        donePlaying = true;

        source.drain();
        source.close();
        }
        catch (Exception exc) {
            System.out.println("error: " + exc.getMessage());
            exc.printStackTrace();
        }
    }

}

//Missile class
class Missile
{
//Class variables.
    public static int current = 0;
    public static int numMissiles = 6;     
//Instance variables.
    public int xpos;
    public int ypos;
    public boolean active;
    public boolean right;    
    Missile()
    {
        active = false;
    }
}
//add or modify.  Code already added for you.
class Star
{
    static public int num = 200;
    public int XPos;
    public int YPos;
    static public int whichStarHit;
    
    public boolean enemy;
    Star(int winWidth,int winHeight)
    {
        enemy = true;
            XPos = (int)(Math.random()*winWidth);
            YPos = (int)(Math.random()*winHeight);
            whichStarHit = -1;
    }
}