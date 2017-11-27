package com.jforeach.mazegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;


public class GameView extends View {
	
	//width and height of the whole maze and width of lines which
	//make the walls
	private int width, height, lineWidth;
	//size of the maze i.e. number of cells in it
	private int mazeSizeX, mazeSizeY;
	//width and height of cells in the maze
	float cellWidth, cellHeight;
	//the following store result of cellWidth+lineWidth 
	//and cellHeight+lineWidth respectively 
	float totalCellWidth, totalCellHeight;
	//the finishing point of the maze
	private int mazeFinishX, mazeFinishY;
	private Maze maze;
	private Activity context;
	private Paint line, red, background;
	static int currentMaze =0;


	
	public GameView(Context context, final Maze maze) {
		super(context);
		this.context = (Activity)context;
		this.maze = maze;
		mazeFinishX = maze.getFinalX();
		mazeFinishY = maze.getFinalY();
		mazeSizeX = maze.getMazeWidth();
		mazeSizeY = maze.getMazeHeight();
		line = new Paint();
		line.setColor(getResources().getColor(R.color.line));
		red = new Paint();
		red.setColor(getResources().getColor(R.color.position));
		background = new Paint();
		background.setColor(getResources().getColor(R.color.game_bg));
		setFocusable(true);
		this.setFocusableInTouchMode(true);
	}
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		width = (w < h)?w:h;
		height = width;         //for now square mazes
		lineWidth = 1;          //for now 1 pixel wide walls
		cellWidth = (width - ((float)mazeSizeX*lineWidth)) / mazeSizeX;
		totalCellWidth = cellWidth+lineWidth;
		cellHeight = (height - ((float)mazeSizeY*lineWidth)) / mazeSizeY;
		totalCellHeight = cellHeight+lineWidth;
		red.setTextSize(cellHeight*0.75f);
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	protected void onDraw(Canvas canvas) {
		//fill in the background
		canvas.drawRect(0, 0, width, height, background);
		boolean[][] hLines = maze.getHorizontalLines();
		boolean[][] vLines = maze.getVerticalLines();
		//iterate over the boolean arrays to draw walls
		for(int i = 0; i < mazeSizeX; i++) {
			for(int j = 0; j < mazeSizeY; j++){
				float x = j * totalCellWidth;
				float y = i * totalCellHeight;
				if(j < mazeSizeX - 1 && vLines[i][j]) {
					//we'll draw a vertical line
					canvas.drawLine(x + cellWidth,   //start X
									y,               //start Y
									x + cellWidth,   //stop X
									y + cellHeight,  //stop Y
									line);
				}
				if(i < mazeSizeY - 1 && hLines[i][j]) {
					//we'll draw a horizontal line
					canvas.drawLine(x,               //startX 
									y + cellHeight,  //startY 
								    x + cellWidth,   //stopX 
								    y + cellHeight,  //stopY 
									line);
				}
			}
		}
		int currentX = maze.getCurrentX(),currentY = maze.getCurrentY();
		//draw the ball
		/*canvas.drawCircle((currentX * totalCellWidth)+(cellWidth/2),   //x of center
						  (currentY * totalCellHeight)+(cellWidth/2),  //y of center
						  (cellWidth*0.45f),                           //radius
						  red);*/

		Paint p;
		p=new Paint();
		Bitmap b=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
		p.setColor(Color.RED);
		canvas.drawBitmap(b, (currentX * totalCellWidth),  (currentY * totalCellHeight), p);

		/*int[][] cheese = maze.getCheeseCoordinates();
		for(int i = 0; i < cheese.length; i++){
			for(int j = 0; j < cheese[0].length; j++){
				if(cheese[i][j] == 1) {
					b=BitmapFactory.decodeResource(getResources(), R.mipmap.cheese);
					canvas.drawBitmap(b, i,  j, p);
				}
			}
		}*/

		canvas.drawText("F",
						(mazeFinishX * totalCellWidth)+(cellWidth*0.25f),
						(mazeFinishY * totalCellHeight)+(cellHeight*0.75f),
						red);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent evt) {
		boolean moved = false;
		switch(keyCode) {
			case KeyEvent.KEYCODE_DPAD_UP:
				moved = maze.move(Maze.UP);
				break;
			case KeyEvent.KEYCODE_DPAD_DOWN:
				moved = maze.move(Maze.DOWN);
				break;
			case KeyEvent.KEYCODE_DPAD_RIGHT:
				moved = maze.move(Maze.RIGHT);
				break;
			case KeyEvent.KEYCODE_DPAD_LEFT:
				moved = maze.move(Maze.LEFT);
				break;
			default:
				return super.onKeyDown(keyCode,evt);
		}

		if(moved) {
			//the ball was moved so we'll redraw the view
			invalidate();
			if(maze.isGameComplete()) {
				showAlertDialogBuilder();
			}
		}
		return true;
	}
	public void showAlertDialogBuilder(){
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(context.getText(R.string.finished_title));
		LayoutInflater inflater = context.getLayoutInflater();
		View view = inflater.inflate(R.layout.finish, null);
		builder.setView(view);
		View closeButton =view.findViewById(R.id.closeGame);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View clicked) {
				System.out.println("current maze " + currentMaze);
				currentMaze = currentMaze +1 ;
				if(currentMaze == 1 ) {
					Maze maze = MazeCreator.getMaze(2);    //use helper class for creating the Maze
					context.getIntent().putExtra("maze", maze);			//add the maze to the intent which we'll retrieve in the Maze Activity
					context.startActivity(context.getIntent());
				}
				else {
					context.finish();
				}
			}
		});
		AlertDialog finishDialog = builder.create();
		finishDialog.show();
	}
}
