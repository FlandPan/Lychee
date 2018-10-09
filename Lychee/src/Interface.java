import java.awt.Color;
import java.text.DecimalFormat;

public abstract class Interface {

	protected Color shade;
	protected Color selectionShade;
	
	protected Database data;
	protected InterfaceControl ic;
	
	protected DecimalFormat numberFormat = new DecimalFormat("#.0");

	public Interface(InterfaceControl ic, Database data) {
		this.data = data;
		this.ic = ic;
		shade = new Color(250,250,175,100);
		selectionShade = new Color(176,196,222,100);
	}

	public abstract void initialize();

	public abstract void update();

	public abstract void draw(java.awt.Graphics2D g);

	public abstract void keyTyped(char c);

	public abstract void keyPressed(int k);

	public abstract void keyReleased(int k);

}
