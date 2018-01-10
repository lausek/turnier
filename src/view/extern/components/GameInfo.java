package view.extern.components;

import java.awt.BorderLayout;

import view.BasicFrame;
import view.FrameComponent;

import view.extern.BasicExternFrame;

public class GameInfo extends FrameComponent {

	@Override
	public void setFrame(BasicFrame frame) {
		super.setFrame(frame);
		add(((BasicExternFrame) frame).getTimer(), BorderLayout.NORTH);
	}

}
