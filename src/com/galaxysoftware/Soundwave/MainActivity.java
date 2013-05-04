package com.galaxysoftware.Soundwave;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import android.R.string;
import android.media.MediaPlayer;
import android.media.audiofx.Equalizer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private String OUTPUT_FILE;
	private TextView label;
	private Boolean effectOn = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		OUTPUT_FILE = Environment.getExternalStorageDirectory()+"/audioRecording.3gpp";
		label = (TextView) findViewById(R.id.textView1);
	}
	
	public void buttonTapped(View view) throws Exception
	{
		switch(view.getId()){
		case R.id.button1:
			beginRecording();
			label.setText("Recording...");
			break;
		
		case R.id.Button01:
			try {
				playRecording();
				label.setText("Playing...");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.Button02:
			stop();
			label.setText("Ready to play");
			break;
		case R.id.imageButton1:
			changeSound();
			break;
		default:
			break;
		}
	}
	private void changeSound() {
		effectOn = !effectOn;
	}

	private void stop() {
		if (recorder != null){
			recorder.stop();
		}
		
	}

	private void playRecording() throws Exception {
		ditchPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(OUTPUT_FILE);
		
		Equalizer eq;
		short band;
		short level;
		if (effectOn) {
			
			int sessionId = mediaPlayer.getAudioSessionId();
			eq = new Equalizer(0, sessionId);
			band = eq.getBand(800);
			level = -1490;
			eq.setBandLevel(band, level);
			short v [] = eq.getBandLevelRange();
			System.out.println(Integer.toString(v[0]) + "   "+ Integer.toString(v[1]));
			
		} else {
			
		}
		mediaPlayer.prepare();
		mediaPlayer.start();
		
	}

	private void ditchPlayer() {
		if (mediaPlayer != null){
			try{
				mediaPlayer.release();
			}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		
		
	}

	private void beginRecording() throws IOException {
		ditchRecorder();
		File outFile = new File(OUTPUT_FILE);
		
		if (outFile.exists())
			outFile.delete();
		
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);
		recorder.setOutputFile(OUTPUT_FILE);
		recorder.prepare();
		recorder.start();
		
	}

	private void ditchRecorder() {
		
		if (recorder != null)
		{
			recorder.release();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
