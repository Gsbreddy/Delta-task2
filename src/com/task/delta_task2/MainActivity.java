package com.task.delta_task2;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.os.Build;

@SuppressWarnings("unused")
public class MainActivity extends ActionBarActivity {
	private Button start;
	private Button pause;
	private TextView timervalue;
	private ArrayList<String> listitems; 
	private ArrayAdapter<String> adapter;
	private long starttime=0;
	long millisec=0,timeswapbuff=0,updatedtime=0;
	int mins,secs,millisecs,id=1;
	Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timervalue=(TextView)findViewById(R.id.textView1);
        start =(Button)findViewById(R.id.button0);
        start.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		starttime=SystemClock.uptimeMillis();
        		handler.postDelayed(updatetimerthread, 0);
        	}
        });
        pause=(Button)findViewById(R.id.button1);
        pause.setOnClickListener(new OnClickListener(){
        	public void onClick(View view){
        		timeswapbuff+=millisec;
        		handler.removeCallbacks(updatetimerthread);
        	}
        });
        Button reset=(Button)findViewById(R.id.button2);
        final ListView lv = (ListView) findViewById(R.id.listview);
        listitems = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listitems);
        reset.setOnClickListener(new OnClickListener(){
			public void onClick(View view){
				timeswapbuff=0;
        		handler.removeCallbacks(updatetimerthread);
        		String s=(String) timervalue.getText();
        		listitems.add(id+++" ) "+s);	
        		lv.setAdapter(adapter);
        		timervalue.setText("00:00.000");
        	}
        });
      
        }
   public  Runnable updatetimerthread=new Runnable(){
    	public void run(){
    		millisec=SystemClock.uptimeMillis()-starttime;
    		updatedtime=timeswapbuff+millisec;
    		secs=(int)(updatedtime/1000);
    		mins=secs/60;
    		secs=secs%60;
    		millisecs=(int)(updatedtime%1000);
    		timervalue.setText(String.format("%02d",mins)+":"+String.format("%02d", secs)+ "."+ String.format("%03d", millisecs));
    		handler.postDelayed(this, 0);
    	}
    };

}

   