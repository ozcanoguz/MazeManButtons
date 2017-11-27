package com.jforeach.mazegame;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import static com.jforeach.mazegame.GameView.currentMaze;

public class Game extends Activity {
	ImageButton btnBottom;
	ImageButton btnLeft;
	ImageButton btnRight;
	ImageButton btnUp;
	boolean moved = false;
	Maze maze;
	GameView gameView;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		maze = (Maze)extras.get("maze");
		gameView = new GameView(this,maze);
		LayoutInflater inflater = this.getLayoutInflater();
		View viewBtn = inflater.inflate(R.layout.buttonsview, null);
		LinearLayout parent = new LinearLayout(this);
		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		layoutParams.gravity = Gravity.CENTER;
		parent.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
		parent.setOrientation(LinearLayout.VERTICAL);
		parent.addView(viewBtn,layoutParams);
		parent.addView(gameView);
		setContentView(parent);
		btnUp = (ImageButton) this.findViewById(R.id.btnUp);
		btnRight = (ImageButton)this.findViewById(R.id.btnRight);
		btnLeft = (ImageButton) this.findViewById(R.id.btnLeft);
		btnBottom = (ImageButton) this.findViewById(R.id.btnBottom);
		clickButtomBtn();
		clickLeftBtn();
		clickRightBtn();
		clickUpBtn();
	}

	public boolean clickButtomBtn(){
		btnBottom.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("Button","btnBottom basıldı.");
				moved = maze.move(Maze.DOWN);
				if(moved){
					gameView.invalidate();
				}
				if(maze.isGameComplete()){
					gameView.showAlertDialogBuilder();
				}
			}
		});
		return true;
	}
	public boolean clickLeftBtn(){
		btnLeft.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("Button","btnLeft basıldı.");
				moved = maze.move(Maze.LEFT);
				if(moved){
					gameView.invalidate();
				}
				if(maze.isGameComplete()){
					gameView.showAlertDialogBuilder();
				}
			}
		});
		return true;
	}
	public boolean clickRightBtn(){
		btnRight.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("Button","btnRight basıldı.");
				moved = maze.move(Maze.RIGHT);
				if(moved){
					gameView.invalidate();
				}
				if(maze.isGameComplete()){
					gameView.showAlertDialogBuilder();
				}
			}
		});
		return true;
	}
	public boolean clickUpBtn(){
		btnUp.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("Button","btnUp basıldı.");
				moved = maze.move(Maze.UP);
				if(moved){
					gameView.invalidate();
				}
				if(maze.isGameComplete()){
					gameView.showAlertDialogBuilder();
				}
			}
		});
		return true;
	}
}
