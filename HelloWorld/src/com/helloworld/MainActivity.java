package com.helloworld;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.VideoView;

public class MainActivity extends Activity {
	private SeekBar mVolumeSeekbar = null;
	private AudioManager mAudioManager = null;
	int mProgressChanged = 0;
	int mMaxVolume = 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		String stringPath = "android.resource://" + getPackageName() + "/"
				+ R.raw.video;
		final VideoView videoView = (VideoView) findViewById(R.id.VideoView);

		videoView.setVideoPath(stringPath);

		MediaController mediaController = new MediaController(this);
		mediaController.setAnchorView(videoView);
		videoView.setMediaController(mediaController);

		videoView.start();
		initControls();
	}

	private void initControls() {
		try {
			mVolumeSeekbar = (SeekBar) findViewById(R.id.volbar);
			mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);

			mVolumeSeekbar.setMax(mAudioManager
					.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

			mVolumeSeekbar.setProgress(mAudioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC));
			mVolumeSeekbar.setKeyProgressIncrement(1);
			mMaxVolume = mVolumeSeekbar.getMax();
			mVolumeSeekbar
					.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						@Override
						public void onStopTrackingTouch(SeekBar arg0) {
						}

						@Override
						public void onStartTrackingTouch(SeekBar arg0) {
						}

						@Override
						public void onProgressChanged(SeekBar arg0,
								int progress, boolean arg2) {
							mProgressChanged = progress;
							mAudioManager.setStreamVolume(
									AudioManager.STREAM_MUSIC, mProgressChanged, 0);
						}
					});
		} catch (Exception e) {
			e.printStackTrace();
		}
		// audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		// progressChanged, AudioManager.FLAG_SHOW_UI);
		// audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		// progressChanged, AudioManager.FLAG_SHOW_UI);

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
			// Raise the Volume Bar on the Screen
			mVolumeSeekbar.setProgress(mAudioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC));
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			// Adjust the Volume
			mAudioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
					AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
			// Lower the VOlume Bar on the Screen
			mVolumeSeekbar.setProgress(mAudioManager
					.getStreamVolume(AudioManager.STREAM_MUSIC));
			return true;
		default:
			return false;
		}
	}
}
