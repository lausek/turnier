package view.extern;

import model.ScheduleItem;
import view.extern.components.GameInfo;

@SuppressWarnings("serial")
public class FullscreenFrame extends BasicExternFrame implements Runnable {

	protected GameInfo gameInfo;

	public FullscreenFrame() {
		gameInfo = new GameInfo();
		switchTo(gameInfo);
	}

	@Override
	protected void handle(EventParams evt) {
		switch (evt.type) {
		case START:
			break;

		case NEW_EVENT:
			ScheduleItem scheduleItem = (ScheduleItem) evt.params;
			
			gameInfo.setHomeTeam(scheduleItem.getHomeTeam());
			gameInfo.setGuestTeam(scheduleItem.getGuestTeam());	
			
			break;

		case GOAL:
			break;

		case END:
			synchronized (this) {
				setVisible(false);
			}
			return;

		}
	}

}
