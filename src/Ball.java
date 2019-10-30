import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Ball {
	public Point point;
	public Color color;
	public int diameter;
	public double angle;
	private int ID;
	private int lastHit;
	private MainGUI main;
	private double speed;
	private Point centerPoint;
	private ImageIcon img;
	private String imgS;
	//public Random2 rain1 = new Random2();

	public Ball(Point pos, MainGUI man, Point origin, Point release, int ballDiameter, String imgSelect) {
		main = man;
		imgS = imgSelect;
		ID = MainGUI.balls.size();
		point = pos;
		diameter = ballDiameter;
		color = new Color((int)(Math.random() * 255), (int)(Math.random() * 255), (int)(Math.random() * 255));
		angle = Math.toDegrees(Math.atan2((double)(origin.y - release.y), (double)(origin.x - release.x)));
		speed = Math.abs(Point.distance(origin.x, origin.y, release.x, release.y))/10;//10
		lastHit = ID;
		centerPoint = new Point(pos.x + diameter/2, pos.y + diameter/2);
		resize();
		//rain1.size = rain1.size2 = 4;
	}
	
	public Image getImage() {
		return img.getImage();
	}
	
	public ImageIcon resize()
	{
		img = new ImageIcon(imgS);
		img.setImage(scaleImage(img.getImage(), diameter, diameter));
		return img;
	}
	
	public Image scaleImage(Image srcImg, int w, int h){
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = img.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(srcImg, 0, 0, w, h, null);
		g2.dispose();
		return img;
	}

	public int getID() {
		return ID;
	}

	public Point getPoint() {
		return point;
	}

	public int getDiameter() {
		return diameter;
	}

	public void setAngle(double ang, int id) {
		angle = ang;
		lastHit = id;
	}

	public double getAngle() {
		return angle;
	}

	public double getSpeed() {
		return speed;
	}

	public void setSpeed(double spd) {
		speed = spd;
	}

	public Point getCenter() {
		return centerPoint;
	}

	public void move() {
		//color = rain1.rainbow();
		if (point.y < 1 || point.y > main.getHeight() - 1 - diameter) {
			angle = 360 - angle;
			lastHit = -1;
			if (point.y < 1) {
				point = new Point(point.x, 0);
			}
			else if (point.y > main.getHeight() - 1 - diameter) {
				point = new Point(point.x, main.getHeight() - 1 - diameter);
			}
		}
		if (point.x < 1 || point.x > main.getWidth() - 1 - diameter) {
			angle = 180 - angle;
			lastHit = -1;
			if (point.x < 1) {
				point = new Point(0, point.y);
			}
			else if (point.x > main.getWidth() - 1 - diameter) {
				point = new Point(main.getWidth() - 1 - diameter, point.y);
			}
		}
		for (int i = 0; i < MainGUI.balls.size(); i++) {
			Ball ball = MainGUI.balls.get(i);
			if (ball != null && ball.getID() != ID && lastHit != ball.getID()) {
				double distance = Math.sqrt(Math.pow(centerPoint.y - ball.getCenter().y, 2) + Math.pow(centerPoint.x - ball.getCenter().x, 2));
				if (distance <= diameter/2 + ball.getDiameter()/2) {
					lastHit = ball.getID();
					double tempAngle = angle;
					angle = ball.getAngle();
					ball.setAngle(tempAngle, ID);
					double netStuff = ball.getDiameter() * ball.getSpeed() + diameter * speed;
					netStuff /= 2;
					speed = netStuff/diameter;
					ball.setSpeed(netStuff/ball.getDiameter());
					if (Math.abs(angle - ball.getAngle()) < 45) {
						if (ball.getDiameter() < diameter) {
							ball.setAngle(180 - ball.getAngle(), ID);
						}
						else if (ball.getDiameter() > diameter) {
							angle = 180 - angle;
						}
						else {
							angle = 180 - angle;
							ball.setAngle(180 - ball.getAngle(), ID);
						}
					}
				}
			}
		}
		point = new Point(point.x + (int)(Math.cos(Math.toRadians(angle)) * speed), point.y + (int)(Math.sin(Math.toRadians(angle)) * speed));
		centerPoint = new Point(point.x + diameter/2, point.y + diameter/2);
	}

}
