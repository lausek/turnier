package model;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import control.DataProvider;

public class Team {
	
	private static Image standardImage;
	
	private int id;
	private String name, logo, group;
	
	static {
		standardImage = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
	}
	
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
			java.nio.file.Path path = DataProvider.get().getImage(logo);
			return ImageIO.read(path.toFile()).getScaledInstance(64, 64, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			return standardImage;
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
