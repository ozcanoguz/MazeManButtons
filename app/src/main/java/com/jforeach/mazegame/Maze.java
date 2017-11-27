package com.jforeach.mazegame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.Serializable;

public class Maze implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;
	
	private boolean[][] verticalLines;
	private boolean[][] horizontalLines;
	private int sizeX, sizeY;         //stores the width and height of the maze
	private int currentX, currentY;   //stores the current location of the ball
	private int finalX, finalY;       //stores the finishing of the maze
	private boolean gameComplete;
	private int[][] cheese;
	private  int cheeseAmount;

	public int getMazeWidth() {
		return sizeX;
	}
	public int getMazeHeight() {
		return sizeY;
	}
	public boolean move(int direction) {
		boolean moved = false;
		if(direction == UP) {
			if(currentY != 0 && !horizontalLines[currentY-1][currentX]) {
				currentY--;
				moved = true;
			}
		}
		if(direction == DOWN) {
			if(currentY != sizeY-1 && !horizontalLines[currentY][currentX]) {
				currentY++;
				moved = true;
			}
		}
		if(direction == RIGHT) {
			if(currentX != sizeX-1 && !verticalLines[currentY][currentX]) {
				currentX++;
				moved = true;
			}
		}
		if(direction == LEFT) {
			if(currentX != 0 && !verticalLines[currentY][currentX-1]) {
				currentX--;
				moved = true;
			}
		}
		if(moved) {
			if(currentX == finalX && currentY == finalY && cheeseAmount==0) {
				gameComplete = true;
			}
		}
		return moved;
	}
	public boolean isGameComplete() {
		return gameComplete;
	}
	public void setStartPosition(int x, int y) {
		currentX = x;
		currentY = y;
	}
	public int getFinalX() {
		return finalX;
	}
	public int getFinalY() {
		return finalY;
	}
	public void setFinalPosition(int x, int y) {
		finalX = x;
		finalY = y;
	}
	public int getCurrentX() {
		return currentX;
	}
	public int getCurrentY() {
		return currentY;
	}
	public boolean[][] getHorizontalLines() {
		return horizontalLines;
	}
	public void setHorizontalLines(boolean[][] lines) {
		horizontalLines = lines;
		sizeX = horizontalLines[0].length;
	}
	public boolean[][] getVerticalLines() {
		return verticalLines;
	}
	public void setVerticalLines(boolean[][] lines) {
		verticalLines = lines;
		sizeY = verticalLines.length;
	}
	public void setCheeseCoordinates(int[][] cheese) {
		this.cheese = cheese;
	}
	public int[][] getCheeseCoordinates() {
		return cheese;
	}
	public void setCheeseAmount(int cheeseAmount) {
		this.cheeseAmount = cheeseAmount;
	}
}
