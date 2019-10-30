import java.awt.Color;


public class Random2 {
	public double r, g, b;
	public double step;
	public double size;

	//--------------------------------------------------------------
	//sets up the rainbow
	//--------------------------------------------------------------
	public Random2() {
		size = 1;
		r = g = b = 0;
		r = 255;
		step = 1;
	}

	//--------------------------------------------------------------
	//updates the rainbow
	//--------------------------------------------------------------
	public Color rainbow()
	{
		if (step == 0)
		{
			r += size;
			if (r >= 255)
			{
				r = 255;
				step++;
			}
		}
		else if (step == 1)
		{
			g += size;
			if (g >= 255)
			{
				g = 255;
				step++;
			}
		}
		else if (step == 2)
		{
			r -= size;
			if (r <= 0)
			{
				r = 0;
				step++;
			}
		}
		else if (step == 3)
		{
			b += size;
			if (b >= 255)
			{
				b = 255;
				step++;
			}
		}
		else if (step == 4)
		{
			g -= size;
			if (g <= 0)
			{
				g = 0;
				step++;
			}
		}
		else if (step == 5)
		{
			r += size;
			if (r >= 255)
			{
				r = 255;
				step++;
			}
		}
		else if (step == 6)
		{
			b -= size;
			if (b <= 0)
			{
				b = 0;
				step = 1;
			}
		}
		Color rainbowed = new Color((int)r, (int)g, (int)b);
		return rainbowed;
	}

}
