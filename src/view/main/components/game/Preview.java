package view.main.components.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import view.main.GameFrameComponent;

@SuppressWarnings("serial")
public class Preview extends GameFrameComponent {

	protected ImageIcon imageIcon;
	protected boolean focused;

	// TODO: remove
	int count = 0;

	public Preview(Control gameControl) {
		super(gameControl);

		new Timer().scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				if (focused) {
					update();
				}
			}

		}, 0, 2000);

		imageIcon = new ImageIcon();
		add(new JLabel(imageIcon));
		
	}

	public void update() {
		int width = gameControl.getExternFrame().getContentPane().getWidth();
		int height = gameControl.getExternFrame().getContentPane().getHeight();

		if (0 < width && 0 < height) {
			BufferedImage image = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics = image.createGraphics();
			gameControl.getExternFrame().paintAll(graphics);
			
			// try {
			// if (javax.imageio.ImageIO.write(image, "png", new
			// java.io.File("imgs/output_image"+(count++)+".png")))
			// {
			// System.out.println("-- saved");
			// }
			// } catch (java.io.IOException e) {
			// e.printStackTrace();
			// }

			imageIcon.setImage(image);
			frame.redraw();
		}
	}

	@Override
	public void enter() {
		this.focused = true;
		update();
	}

	@Override
	public void leave() {
		this.focused = false;
	}

}
