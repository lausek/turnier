package model;

public class Team {
	
	private int id;
	private String name, logo, group;
	
	public Team(int id, String name, String logo) {
		this.id = id;
		this.name = name;
		this.logo = logo;
		this.group = "0";
	}
	
	public int getId() {
		return this.id;
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
