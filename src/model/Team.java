package model;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	
	public Image getLogo() {
		try {
			return ImageIO.read(new File(logo));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
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
