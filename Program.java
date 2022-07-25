import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Dimension;

public class Program extends JFrame implements Runnable {
	public static Program program;
	public static GamePanel gamePanel;
	public static Thread thread;

	Program() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		
		gamePanel=new GamePanel();

		add(gamePanel);

		pack();
		
		setVisible(true);

		thread=new Thread(this);
		thread.run();
	}

	public void run() {
		for(;;) {
			repaint();
		}
	}

	public static void main(String[] args) {
		program=new Program();
	}	
}



class GamePanel extends JPanel {

	double cx,cy;
	double x,y;
	int frame=0;

	UFO[] ufos=new UFO[4];

	enum GameState {
		INIT,
		START
	};

	GameState gameState=GameState.INIT;

	GamePanel() {
		setPreferredSize(new Dimension(640,480));
		setBackground(new Color(0,80,0));
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		switch(gameState) {
			case INIT:

				cx=getWidth()/2;
				cy=getHeight()/2;

				ufos[0]=new UFO(cx,cy,70,110,Color.RED);
				ufos[1]=new UFO(cx,cy,110,70,Color.GREEN);
				ufos[2]=new UFO(cx,cy,110,110,Color.BLUE);
				ufos[3]=new UFO(cx,cy,130,170,Color.YELLOW);

				gameState=GameState.START;

			break;
			case START:

				for(UFO ufo:ufos) {
					ufo.draw(g);
				}

				for(UFO ufo:ufos) {
					ufo.update(g,frame);
				}

				frame++;

			break;
		} 
	
	}

}

class UFO {

	double x,y;
	double cx,cy;
	double a,b;
	Color color; 

	UFO(double x,double y,double a,double b,Color color) {
		this.x=x;
		this.y=y;
		this.cx=x;
		this.cy=y;
		this.a=a;
		this.b=b;
		this.color=color;	
	}

	public void draw(Graphics g) {
		g.setColor(color);
		g.fillOval((int)x,(int)y,32,32);
	}

	public void update(Graphics g,int frame) {
		x=cy+Math.cos(frame/a)*200;
		y=cy+Math.sin(frame/b)*200;
	}
	
}
