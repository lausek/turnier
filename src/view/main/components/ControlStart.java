package view.main.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import control.Turnier;
import view.FrameComponent;

@SuppressWarnings("serial")
public class ControlStart extends FrameComponent implements ActionListener {

	protected JButton cmdCreate, cmdLoad;

	public ControlStart() {
		setLayout(new BorderLayout(0, 0));

		Box verticalBox = new Box(BoxLayout.Y_AXIS);
		verticalBox.setAlignmentX(JComponent.CENTER_ALIGNMENT);

		verticalBox.add(Box.createVerticalGlue());

		cmdCreate = new JButton("Neues Turnier anlegen");
		cmdCreate.setAlignmentX(CENTER_ALIGNMENT);
		cmdCreate.addActionListener(this);
		verticalBox.add(cmdCreate);

		verticalBox.add(Box.createVerticalStrut(40));

		cmdLoad = new JButton("Turnier laden");
		cmdLoad.setAlignmentX(CENTER_ALIGNMENT);
		cmdLoad.addActionListener(this);
		verticalBox.add(cmdLoad);

		verticalBox.add(Box.createVerticalGlue());

		add(verticalBox);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getSource() == cmdLoad) {
			
			JFileChooser chooser = new JFileChooser();
			if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
				try {
					Turnier turnier = new Turnier(chooser.getSelectedFile().getPath());
					
					frame.getControl().setTurnier(turnier);
					frame.switchTo(new GameControl());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		} else if (evt.getSource() == cmdCreate) {

		}
	}

}
