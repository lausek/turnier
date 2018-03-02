package view.main.components.game;

import view.main.GameFrameComponent;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.ScheduleItem;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Overview extends GameFrameComponent {

	protected JLabel lbHomeTeam, lbGuestTeam;
	protected ImageIcon homeIcon, guestIcon;
	
	public Overview(Control gameControl) {
		super(gameControl);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JPanel top = new JPanel();
		add(top);
		
		homeIcon = new ImageIcon();
		JLabel lbHomeLogo = new JLabel(homeIcon);
		lbHomeTeam = new JLabel("New label");
		
		top.add(lbHomeLogo);
		top.add(lbHomeTeam);
		
		JPanel bottom = new JPanel();
		add(bottom);
		
		guestIcon = new ImageIcon();
		JLabel lbGuestLogo = new JLabel(guestIcon);
		lbGuestTeam = new JLabel("New label");
		
		bottom.add(lbGuestLogo);
		bottom.add(lbGuestTeam);
	}
	
	public void addEvent(ScheduleItem item) {
		lbHomeTeam.setText(item.getHomeTeam().getTeam().toString());
		homeIcon.setImage(item.getHomeTeam().getTeam().getLogo());
		
		lbGuestTeam.setText(item.getGuestTeam().getTeam().toString());
		guestIcon.setImage(item.getGuestTeam().getTeam().getLogo());
	}
	
}
