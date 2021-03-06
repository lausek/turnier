package view.main.components;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import control.Turnier;
import view.FrameComponent;
import view.main.components.game.Control;

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
//			if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
				try {
//					Turnier turnier = new Turnier(chooser.getSelectedFile().getPath());
//					Turnier turnier = new Turnier("/home/lausek/Downloads/newcup181410.zip");
					Turnier turnier = new Turnier("C:\\Users\\wn00086506\\Downloads\\turnier\\newcup140315.zip");
					
					frame.switchTo(new Control(turnier));
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
//			}
			
		} else if (evt.getSource() == cmdCreate) {

		}
	}

}
