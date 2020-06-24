package date2020_05_28_ASCII_Art.res;

import java.awt.*;

public class ASCIIColor{
	private Color color;
	private Character character;

	public ASCIIColor(Color color, Character character){
		this.color = color;
		this.character = character;
	}

	public Color color(){
		return this.color;
	}

	public Character character(){
		return this.character;
	}
}
