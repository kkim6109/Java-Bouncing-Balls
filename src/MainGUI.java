import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JApplet;

public class MainGUI extends JApplet implements MouseListener, MouseWheelListener, MouseMotionListener, Runnable {
	private boolean canPaint;
	public static ArrayList<Ball> balls;
	private Point origin;
	private boolean pressed;
	private Point release;
	private int ballDiameter;
	public Random2 rain1 = new Random2();
	private Random2 oldRainbow;
	private String cImg;
	private String[] pics = new String[] {"nikosmoji.png", "sahil.png", "dvd.png", "brian.png"};
	
	public void init() {
		pressed = false;
		this.setSize(1280, 720);
		canPaint = false;
		balls = new ArrayList<Ball>();
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
		ballDiameter = 30;
		Thread t = new Thread(this);
		t.start();
		rain1.size = 0.1;
	}
	
	public void paint (Graphics page) {
		//super.paintComponents(g);
		Image temp = createImage(this.getWidth(), this.getHeight());
		Graphics g = temp.getGraphics();
		//g.setColor(rain1.rainbow());
		//g.fillRect(0, 0, this.getWidth(), this.getHeight());
		if (oldRainbow != null) {
			rain1.b = oldRainbow.b;
			rain1.r = oldRainbow.r;
			rain1.g = oldRainbow.g;
			rain1.step = oldRainbow.step;
		}
		else {
			oldRainbow = new Random2();
			oldRainbow.size = rain1.size;
		}
		for (int i = 0; i < this.getWidth() * 2; i++) {
			g.setColor(rain1.rainbow());
			if (i == 1) {
				oldRainbow.b = rain1.b;
				oldRainbow.r = rain1.r;
				oldRainbow.g = rain1.g;
				oldRainbow.step = rain1.step;
			}
			g.drawLine(i - this.getWidth(), 0, i, this.getHeight());
		}
		if (canPaint) {
			for (int i = 0; i < balls.size(); i++) {
				Ball ball = balls.get(i);
				g.setColor(ball.color);
				g.drawImage(ball.getImage(), ball.point.x, ball.point.y, this);
				//g.fillOval(ball.point.x, ball.point.y, ball.diameter, ball.diameter);
				g.setColor(Color.DARK_GRAY);
				g.fillOval((int)(ball.getCenter().x - (double)(ball.getDiameter()/2) * (double)((double)(ball.getDiameter() + ball.getPoint().y) / (this.getHeight() - 1))), (this.getHeight() - 5), ball.getDiameter() * ball.getPoint().y/(this.getHeight() - 1), 5);
				//g.fillRect(ball.getPoint().x + ball1.getPoint().y/(this.getHeight() - 1), this.getHeight() - 1, ball.getDiameter() * ball.getPoint().y/(this.getHeight() - 1), 2);
			}//think about how to adjust it so that it increases from the center
		}
		if (pressed) {
			g.setColor(Color.black);
			g.drawLine(origin.x, origin.y, release.x, release.y);
			Ball val = new Ball(new Point(release.x - ballDiameter/2, release.y - ballDiameter/2), this, origin, release, ballDiameter, cImg);
			g.drawImage(val.getImage(), release.x - ballDiameter/2, release.y - ballDiameter/2, this);
			g.drawOval(release.x - ballDiameter/2, release.y - ballDiameter/2, ballDiameter, ballDiameter);
		}
		page.drawImage(temp, 0, 0, this);
	}

	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		cImg = pics[(int)(Math.random() * pics.length)];
		pressed = true;
		origin = e.getPoint();
		release = e.getPoint();
		if (balls.size() == 0) {
			repaint();
		}
	}
	
	public void mouseDragged(MouseEvent e) {
		release = e.getPoint();
		if (balls.size() == 0) {
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		pressed = false;
		canPaint = true;
		release = e.getPoint();
		Ball ball = new Ball(new Point(release.x - ballDiameter/2, release.y - ballDiameter/2), this, origin, release, ballDiameter, cImg);
		balls.add(ball);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(20);//20
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (int i = 0; i < balls.size(); i++) {
				Ball ball = balls.get(i);
				ball.move();
			}
			repaint();
		}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent arg0) {
		ballDiameter += arg0.getWheelRotation() * -1;
		if (ballDiameter < 30) {
			ballDiameter = 30;
		}
		if (ballDiameter > 200) {
			ballDiameter = 200;
		}                          
		if (balls.size() == 0) {
			repaint();
		}
	}

}