package view.extern.components;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import model.RankingItem;
import view.BasicFrame;
import view.FrameComponent;

import view.extern.BasicExternFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class GameInfo extends FrameComponent {
	
	protected JPanel teamInfo, headerInfo;
	protected JLabel lbHomeTeam, lbGuestTeam;
	
	public GameInfo() {
		setLayout(new BorderLayout(0, 0));
		
		lbHomeTeam = new JLabel("New label");
		lbGuestTeam = new JLabel("New label");
	}

	public void setHomeTeam(RankingItem item) {
		lbHomeTeam.setText(item.getTeam().toString());
	}
	
	public void setGuestTeam(RankingItem item) {
		lbGuestTeam.setText(item.getTeam().toString());
	}
	
	@Override
	public void setFrame(BasicFrame frame) {
		super.setFrame(frame);
		headerInfo = new JPanel();
		headerInfo.add(((BasicExternFrame) frame).getTimer());
		add(headerInfo, BorderLayout.NORTH);
		
		teamInfo = new JPanel();
		
		JPanel leftPanel = new JPanel();
		leftPanel.add(lbHomeTeam);
		teamInfo.add(leftPanel);
		
		JPanel rightPanel = new JPanel();
		rightPanel.add(lbGuestTeam);
		teamInfo.add(rightPanel);
		add(teamInfo, BorderLayout.CENTER);
	}

}
