package com.galaxysoftware.Soundwave;

import java.io.Console;
import java.io.File;
import java.io.IOException;

import android.R.string;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.audiofx.EnvironmentalReverb;
import android.media.audiofx.PresetReverb;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.CheckBox;

public class MainActivity extends Activity {

	private MediaPlayer mediaPlayer;
	private MediaRecorder recorder;
	private String OUTPUT_FILE;
	private TextView label;
	private CheckBox checkBox;
	private boolean useEcho = false;
	private boolean recording = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		OUTPUT_FILE = Environment.getExternalStorageDirectory()+"/audioRecording.3gpp";
		label = (TextView) findViewById(R.id.textView1);
		checkBox = (CheckBox) findViewById(R.id.checkBox1);
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
		case R.id.checkBox1:
			if (checkBox.isChecked() == true){
				useEcho = true;
			}else{
				useEcho = false;
			}
			break;
		default:
			break;
		}
	}

	private void stop() {
		
			recorder.stop();
		
	}

	private void playRecording() throws Exception {
		ditchPlayer();
		mediaPlayer = new MediaPlayer();
		mediaPlayer.setDataSource(OUTPUT_FILE);
		if (useEcho == true){
			EnvironmentalReverb  eReverb = new EnvironmentalReverb(0,0); 
	        mediaPlayer.attachAuxEffect(eReverb.getId());
			eReverb.setDecayHFRatio((short) 2000);
			eReverb.setDecayTime(10000);
			eReverb.setDensity((short) 1000);
			eReverb.setDiffusion((short) 1000);
			eReverb.setReverbLevel((short) -1000);
			eReverb.setRoomLevel((short) 0);
			eReverb.setEnabled(true);
			mediaPlayer.attachAuxEffect(eReverb.getId());
			mediaPlayer.setAuxEffectSendLevel(1.0f);
			mediaPlayer.setAuxEffectSendLevel(1.0f);
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
		recorder = new MediaRecorder();
		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.HE_AAC);
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
