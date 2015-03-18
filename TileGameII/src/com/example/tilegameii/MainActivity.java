package com.example.tilegameii;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.support.v7.app.ActionBarActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class MainActivity extends ActionBarActivity {
   
	private static int ROW_COUNT = -1;
	private static int COL_COUNT = -1;
	private Context context, context2;
	private Drawable backImage;
	private int [] [] cards;
	private List<Drawable> images;
	private Card firstCard;
	private Card secondCard;
	private ButtonListener buttonListener;
	private Animation animSlideDown, animBounce, animRotate, animFade_Out, animBlack, animZoom_Out, animZoom_In, animZoom_In_Long, animFade_In;
	private static Object lock = new Object();
		
	int turns;
	private TableLayout mainTable;
	private RelativeLayout zoomTable;
	private UpdateCardsHandler handler;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
		
	handler = new UpdateCardsHandler();
	loadImages();       
	backImage =  getResources().getDrawable(R.drawable.icon);
	
	
    buttonListener = new ButtonListener();   
	zoomTable  = (RelativeLayout)findViewById(R.id.RelativeLayout01);//****************************
    //zoomTable.setClipChildren(false);
	mainTable = (TableLayout)findViewById(R.id.TableLayout03); 
	mainTable.setClipChildren(false);
	context  = mainTable.getContext();
	context2 = zoomTable.getContext();
	
  	Spinner s = (Spinner) findViewById(R.id.Spinner01);
  	ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.type, android.R.layout.simple_spinner_item);
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	s.setAdapter(adapter);	        
	s.setOnItemSelectedListener(new OnItemSelectedListener(){
		        	
		    	  @Override
		    	  public void onItemSelected(
		    			  android.widget.AdapterView<?> arg0, 
		    			  View arg1, int pos, long arg3){
		    		  
		    		  ((Spinner) findViewById(R.id.Spinner01)).setSelection(0);
		    		  
		  			int x,y;
		  			
		  			switch (pos) {
					case 1:
						x=4;y=4;
						break;
					case 2:
						x=4;y=5;
						break;
					case 3:
						x=4;y=6;
						break;
					case 4:
						x=5;y=6;
						break;
					case 5:
						x=6;y=6;
						break;
					default:
						return;
					}
		  			newGame(x,y);
		  			
		  		}//end onItemSelected


				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}

		  	});//end s.setOnItemSelectedListener() statement
	    }//end onCreate
	    
	    private void newGame(int c, int r) {
	    	ROW_COUNT = r;
	    	COL_COUNT = c;
	    	
	    	cards = new int [COL_COUNT] [ROW_COUNT];
	    	
	    	
	    	mainTable.removeView(findViewById(R.id.TableRow01));
	    	mainTable.removeView(findViewById(R.id.TableRow02));
	    	
	    	TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
	    	TableRow zr = ((TableRow)findViewById(R.id.TableRow03));
	    	zr.setClipChildren(false);
	    
	    	tr.removeAllViews();
	    	zr.removeAllViews();
	    	
	    	mainTable = new TableLayout(context);
	    	tr.addView(mainTable);
	    	
	    	//**************************************************************
	    	zoomTable = new RelativeLayout(context2);
	   
	    	zr.addView(zoomTable);
	    	
	    	 for (int y = 0; y < ROW_COUNT; y++) {
	    		 mainTable.addView(createRow(y));
	    		 //zoomTable.startAnimation(animZoom_In);//**********************************?????
	          }
	    	 
	    	 firstCard=null;
	    	 loadCards();
	    	 
	    	 turns=0;
	    	 ((TextView)findViewById(R.id.tv1)).setText("attempts: "+turns);
	    	 
				
		} //end newGame
	    
	    private void loadImages() {
	    	images = new ArrayList<Drawable>();
	    	//The image01 is the splash image
	    	//the icon.png is the card background
	    	
	    	images.add(getResources().getDrawable(R.drawable.image01));
	    	images.add(getResources().getDrawable(R.drawable.image02));
	    	images.add(getResources().getDrawable(R.drawable.image03));
	    	images.add(getResources().getDrawable(R.drawable.image04));
	    	images.add(getResources().getDrawable(R.drawable.image05));
	    	images.add(getResources().getDrawable(R.drawable.image06));
	    	images.add(getResources().getDrawable(R.drawable.image07));
	    	images.add(getResources().getDrawable(R.drawable.image08));
	    	images.add(getResources().getDrawable(R.drawable.image09));
	    	images.add(getResources().getDrawable(R.drawable.image10));
	    	images.add(getResources().getDrawable(R.drawable.image11));
	    	images.add(getResources().getDrawable(R.drawable.image12));
	    	images.add(getResources().getDrawable(R.drawable.image13));
	    	images.add(getResources().getDrawable(R.drawable.image14));
	    	images.add(getResources().getDrawable(R.drawable.image15));
	    	images.add(getResources().getDrawable(R.drawable.image16));
	    	images.add(getResources().getDrawable(R.drawable.image17));
	    	images.add(getResources().getDrawable(R.drawable.image18));
	    	images.add(getResources().getDrawable(R.drawable.image19));
	    	images.add(getResources().getDrawable(R.drawable.image20));
	    	images.add(getResources().getDrawable(R.drawable.image21));
			
		} //end loadImages

		private void loadCards(){
			try{
		    	int size = ROW_COUNT*COL_COUNT;
		    	
		    	Log.i("loadCards()","size=" + size);
		    	
		    	ArrayList<Integer> list = new ArrayList<Integer>();
		    	
		    	for(int i=0;i<size;i++){
		    		list.add(new Integer(i));
		    	}
		    	
		    	
		    	Random r = new Random();
		    
		    	for(int i=size-1;i>=0;i--){
		    		int t=0;
		    		
		    		if(i>0){
		    			t = r.nextInt(i);
		    		}
		    		
		    		t=list.remove(t).intValue();
		    		cards[i%COL_COUNT][i/COL_COUNT]=t%(size/2);
		    		//Log to console
		    		Log.i("loadCards()", "card["+(i%COL_COUNT)+
		    				"]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
		    	}// end for loop
		    }//end try
			catch (Exception e) {
				Log.e("loadCards()", e+"");//Log to console
			}//end catch
			
	    }// end loadCards
	    
	    private TableRow createRow(int y){
	    	 TableRow row = new TableRow(context);
	    	 row.setHorizontalGravity(Gravity.CENTER);
	    	 //row.setClipChildren(false);//*****************************************************
	         for (int x = 0; x < COL_COUNT; x++) {
			         row.addView(createImageButton(x,y));
			         
	         }
	         return row;
	    }//end createRow
	    
	    private View createImageButton(int x, int y){
	    	Animation animSlideDown;
	    	ImageView ivButton = new ImageView(context);
	    	animSlideDown = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.slide_down);
	    	
	    	ivButton.startAnimation(animSlideDown);
	    	ivButton.setBackground(backImage);
	    	ivButton.setId(100*x+y);
	    	ivButton.setOnClickListener(buttonListener);
	    	return ivButton;
	    }//end createImageButton
	    
	    class ButtonListener implements OnClickListener {

			@Override
			public void onClick(View v) {
				
				synchronized (lock) {
					if(firstCard!=null && secondCard != null){
						return;
					}
					int id = v.getId();
					int x = id/100;
					int y = id%100;
					turnCard((ImageView)v,x,y);
				}
				
			}// end onClick

			private void turnCard(ImageView ivButton,int x, int y) {
			
				/*Thread timer - new Thread() {
					  public void run(){
						   try{
							   sleep(5000);
							   
						   }catch (InterruptedException e){
							   e.printStackTrace();
						   }
					   }};*/
				   

				
				
				ImageView ivButton2 = new ImageView(context2);
				RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(60,60);
				//params.leftMargin = x*5;
				params.topMargin = 60+(y*200);
				
				zoomTable.addView(ivButton2, params);
				
				ivButton.setBackground(images.get(cards[x][y]));
				ivButton2.setBackground(images.get(cards[x][y]));
				
				 AnimationSet setTwo = new AnimationSet(false);
				 animFade_Out = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.fade_out);
				 animZoom_In_Long = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.zoom_in_long);
				 animRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
	    		 animBlack = AnimationUtils.makeOutAnimation(getApplicationContext(), isDestroyed());
	    		 setTwo.addAnimation(animZoom_In_Long);
	    		 //setTwo.addAnimation(animFade_Out);
	    		 //setTwo.addAnimation(animBlack);
	    		 
		    	ivButton.setAnimation(animRotate);
		    	ivButton.startAnimation(animRotate);
		    	
		    	
		    	
		    
		   		   
		    	
		    	ivButton2.startAnimation(setTwo);
		    	//timer.start();
		 
		    	//final Handler handler1 = new Handler();
		    	
		    	/*handler1.postDelayed(new Runnable() {
		    	@Override
		    	public void run(){
		    	
		    	}
		    	}, 50000);
			*/
		    	/*Runnable timeTask = new Runnable(){
		    		public void run(){
		    			handler1.postDelayed(this, 5000);
		    			
		    		}
		    	};
		    	
		    	handler1.postDelayed(timeTask, 1000);*/
		   
				if(true) {
					ivButton2.setVisibility(View.INVISIBLE);
				}
	    		
				if(firstCard==null)
					firstCard = new Card(ivButton,x,y);
				     
				
				else{ 
					if(firstCard.x == x && firstCard.y == y)
						return; //the user pressed the same card
					secondCard = new Card(ivButton,x,y);
					turns++;
					((TextView)findViewById(R.id.tv1)).setText("attempts: "+turns);
					TimerTask tt = new TimerTask() {			////Timer 
						
							@Override
							public void run() {
								try{
									synchronized (lock) {
										handler.sendEmptyMessage(0);
											}
										}
								catch (Exception e) {
										Log.e("E1", e.getMessage());
										}
									}//end run
							};//end TimerTask
					
				Timer t = new Timer(false);
				t.schedule(tt, 1300);
					}//end if (firstCard==null) else
				
					
			   }  //end turnCard
			
			
			
			
			}//end ButtonListener class
	    
	    
	    
	    class UpdateCardsHandler extends Handler{
	    	
	    	@Override
	    	public void handleMessage(Message msg) {
	    		synchronized (lock) {
	    			checkCards();
	    		}
	    	}
	    	 public void checkCards(){
	    		 AnimationSet setOne = new AnimationSet(false);
	    		 animZoom_Out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom_out);
	    		 animRotate = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.rotate);
	    		 animFade_Out = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.fade_out);
	    		 animFade_In = AnimationUtils.loadAnimation(getApplicationContext(),  R.anim.fade_in);
	    		 animBlack = AnimationUtils.makeOutAnimation(getApplicationContext(), isDestroyed());
	    		 setOne.addAnimation(animRotate);
	    		 setOne.addAnimation(animBlack);
	    		 
	    	    	if(cards[secondCard.x][secondCard.y] == cards[firstCard.x][firstCard.y]){
	    	    		firstCard.ivButton.startAnimation(setOne);
	    	    		secondCard.ivButton.startAnimation(setOne);	
	 
	    				firstCard.ivButton.setVisibility(View.INVISIBLE);
	    				secondCard.ivButton.setVisibility(View.INVISIBLE);
	    			}
	    			else {
	    				
	    				/*firstCard.ivButton.startAnimation(animFade_Out);
	    				secondCard.ivButton.startAnimation(animFade_Out);
	    				secondCard.ivButton.setBackground(backImage);
	    				firstCard.ivButton.setBackground(backImage);
	    				firstCard.ivButton.startAnimation(animFade_In);
	    				secondCard.ivButton.startAnimation(animFade_In);*/
	    				firstCard.ivButton.startAnimation(animZoom_Out);
	    				secondCard.ivButton.startAnimation(animZoom_Out);
	    				secondCard.ivButton.setBackground(backImage);
	    				firstCard.ivButton.setBackground(backImage);
	    				
	    			}
	    	    	
	    	    	firstCard=null;
	    			secondCard=null;
	    	    }
	    }//end UpdateCardsHandler
	    
	   
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}//End onCreateOptionsMenu

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up ivivButton, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}// end onOptionsItemSelected
	
}  //End MainActivity
