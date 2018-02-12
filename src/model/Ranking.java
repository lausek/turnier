package model;

import java.util.HashMap;
import java.util.List;

public abstract class Ranking {

	protected HashMap<String, List<RankingItem>> groups;

	public Ranking() {
		groups = new HashMap<>();
	}
	
	public RankingItem getTeam(int id) {
		for(List<RankingItem> group : groups.values()) {
			for(RankingItem item : group) {
				if(item.getTeam().getId() == id) {
					return item;
				}
			}
		}
		return null;
	}

	public void add(String group, Team team) {
		if (!groups.containsKey(group)) {
			groups.put(group, new java.util.ArrayList<>());
		}
		team.setGroup(group);
		groups.get(group).add(new RankingItem(team));
	}
	
	public void add(String group, List<Team> teams) {
		teams.forEach(team -> {
			add(group, team);
		});
	}
	
	public HashMap<String, List<RankingItem>> getGroups() {
		return groups;
	}

}
