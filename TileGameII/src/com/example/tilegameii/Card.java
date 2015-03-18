package com.example.tilegameii;


import android.widget.ImageView;


public class Card{

	public int x;
	public int y;
	public ImageView ivButton;
	
	public Card(ImageView ivButton, int x,int y) {
		this.x = x;
		this.y=y;
		this.ivButton=ivButton;
	}
	

}
