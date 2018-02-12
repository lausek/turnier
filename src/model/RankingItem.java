package model;

public class RankingItem implements Comparable<RankingItem> {

	protected Team team;
	protected int points, goodGoals, badGoals;

	public RankingItem(Team team) {
		this.team = team;
	}

	public Team getTeam() {
		return this.team;
	}

	public int getGoodGoals() {
		return this.goodGoals;
	}

	public int getBadGoals() {
		return this.badGoals;
	}

	public int getGoalDifference() {
		return getGoodGoals() - getBadGoals();
	}

	public int getPoints() {
		return this.points;
	}

	@Override
	public int compareTo(RankingItem other) {
		if (this.getPoints() == other.getPoints()) {

			if (this.getGoalDifference() == other.getGoalDifference()) {
				
				if(this.getGoodGoals() == other.getGoodGoals()) {
					return 0;
				} else if(this.getGoodGoals() > other.getGoodGoals()) {
					return 1;
				} else {
					return -1;
				}
				
			} else if(this.getGoalDifference() > other.getGoalDifference()) {
				return 1;
			} else {
				return -1;
			}

		} else if (this.getPoints() > other.getPoints()) {
			return 1;
		} else {
			return -1;
		}
	}

}
