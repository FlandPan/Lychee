
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class GUIDrawer extends JPanel implements Runnable, KeyListener {

	private static final long serialVersionUID = 1L;

	// dimensions
	public static final int WIDTH = 1200;
	public static final int HEIGHT = 800;

	// game thread
	private Thread thread;
	private boolean running;
	private int FPS = 20;
	private long targetTime = 1000 / FPS;

	// image
	private BufferedImage image;
	private Graphics2D g;
	private Color c = new Color(15, 65, 100); // background colour

	// interface control
	private InterfaceControl ic;

	// database
	Database data;

	public GUIDrawer(Database data) {
		super();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setFocusable(true);
		requestFocus();

		this.data = data;
	}

	public void addNotify() {
		super.addNotify();
		if (thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}

	private void initialize() {

		image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) image.getGraphics();
		g.setStroke(new BasicStroke(2));

		running = true;

		ic = new InterfaceControl(data);

	}

	public void stop() {
		running = false;
		thread.interrupt();
	}

	public void run() {

		initialize();

		long start;
		long elapsed;
		long wait;

		// running loop
		while (!thread.isInterrupted() && running && data != null) {

			start = System.currentTimeMillis();

			update();
			draw();
			drawToScreen();

			elapsed = System.currentTimeMillis() - start;

			wait = targetTime - elapsed;

			try {
				if (wait > 0) {
					Thread.sleep(wait);
				}
			} catch (InterruptedException e) {

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void update() {
		ic.update();
	}

	private void draw() {
		g.setColor(c);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		ic.draw(g);
	}

	private void drawToScreen() {
		Graphics g2 = getGraphics();
		g2.drawImage(image, 0, 0, null);
		g2.dispose();
	}

	public void keyTyped(KeyEvent key) {
		char c = key.getKeyChar();
		if (c != '' && c != '' && c != '' && c != '\n' /*&& c != ' '*/) {
			ic.keyTyped(c);
		}
	}

	public void keyPressed(KeyEvent key) {
		ic.keyPressed(key.getKeyCode());
	}

	public void keyReleased(KeyEvent key) {
		ic.keyReleased(key.getKeyCode());
	}

}
