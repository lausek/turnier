package model;

public class Team {

	private String name, logo, group;
	
	public Team(String name, String logo) {
		this.name = name;
		this.logo = logo;
		this.group = "0";
	}
	
	public void setGroup(String group) {
		this.group = group;
	}

	public String getGroup() {
		return this.group;
	}
	
	@Override
	public String toString() {
		return this.name;
	}
	
}
