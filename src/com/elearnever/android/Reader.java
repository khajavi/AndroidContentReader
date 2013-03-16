package com.elearnever.android;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import com.elearnever.android.content.ContentHandler;

public class Reader extends FragmentActivity implements OnPageChangeListener,
		OnClickListener, OnCompletionListener, OnTouchListener, Runnable {

	private Button buttonPlayStop;
	private ContentHandler contentHandler;
	private Handler handler = new Handler();
	private String xml = "4.xml";
	private MediaPlayer mediaPlayer = new MediaPlayer();
	private int currentPagePosition = 0;
	private SeekBar seekBar;
	private Bundle readerBundle;

	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read);

		readerBundle = getIntent().getExtras();
		contentHandler = (ContentHandler) readerBundle
				.getSerializable("contentHandler");
		currentPagePosition = contentHandler.getIndexOf(
				readerBundle.getInt("groupPosition"), readerBundle.getInt("childPosition"));

		initFragmentView();
		initPlayerView(currentPagePosition);
	}
	
	@Override
	protected void onPause() {
		handler.removeCallbacks(this);
		mediaPlayer.pause();
		mediaPlayer.release();
		super.onPause();
	}
	
	@Override
	protected void onResume() {
 		initPlayerView(currentPagePosition);
		super.onResume();
	}

	public void initFragmentView() {
		List<Fragment> fragments = new Vector<Fragment>();

		for (int pageNumber = 0; pageNumber < contentHandler.getSize(); pageNumber++) {
			Bundle bundle = new Bundle();

			String html = contentHandler.contentOf(pageNumber);

			StringBuffer stringBuffer = new StringBuffer();
			stringBuffer.append(getString(R.string.template));
			stringBuffer.append(html);
			stringBuffer.append("<p><br /></p></body></html>");

			bundle.putString("html", stringBuffer.toString());
			bundle.putInt("fontSize", readerBundle.getInt("fontSize"));
			
			fragments.add(Fragment.instantiate(this,
					WebViewFragment.class.getName(), bundle));
		}

		WebViewAdapter adapter = new WebViewAdapter(
				super.getSupportFragmentManager(), fragments);
		ViewPager viewPager = (ViewPager) super.findViewById(R.id.viewpager);
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(currentPagePosition);
		viewPager.setOnPageChangeListener(this);
	}

	public void initPlayerView(int position) {
		try {
			buttonPlayStop = (Button) findViewById(R.id.ButtonPlayStop);
			buttonPlayStop.setOnClickListener(this);

			AssetFileDescriptor descriptor = getResources().getAssets()
					.openFd("sounds/" + contentHandler.getAudioUrlOf(position)
							+ ".ogg");

			mediaPlayer = new MediaPlayer();
			mediaPlayer.setDataSource(descriptor.getFileDescriptor(),
					descriptor.getStartOffset(), descriptor.getLength());
			mediaPlayer.prepare();
			mediaPlayer.setOnCompletionListener(this);
			seekBar = new SeekBar(this);
			seekBar = (SeekBar) findViewById(R.id.SeekBar);
			seekBar.setMax(mediaPlayer.getDuration());
			seekBar.setOnTouchListener(this);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		try {
			handler.removeCallbacks(this);
			if (mediaPlayer.isPlaying()) {
				mediaPlayer.stop();
				mediaPlayer.release();
			}
			seekBar.setProgress(0);
			buttonPlayStop
					.setBackgroundResource(android.R.drawable.ic_media_play);

			initPlayerView(position);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onCompletion(MediaPlayer mp) {
		mp.seekTo(0);
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		try {
			SeekBar seekBar = (SeekBar) v;
			mediaPlayer.seekTo(seekBar.getProgress());
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ButtonPlayStop:
			buttonClick();
			break;

		default:
			break;
		}
	}

	private void buttonClick() {
		try {
			if (!mediaPlayer.isPlaying()) {
				mediaPlayer.start();
				buttonPlayStop
						.setBackgroundResource(android.R.drawable.ic_media_pause);
				startPlayProgressUpdater();
			} else {
				buttonPlayStop
						.setBackgroundResource(android.R.drawable.ic_media_play);
				mediaPlayer.pause();
				seekBar.setProgress(mediaPlayer.getCurrentPosition());
			}
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}
	}

	private void startPlayProgressUpdater() {
		seekBar.setProgress(mediaPlayer.getCurrentPosition());
		if (mediaPlayer.isPlaying()) {
			handler.postDelayed(this, 1000);
		} else if (mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration()) {
			mediaPlayer.pause();
			buttonPlayStop
					.setBackgroundResource(android.R.drawable.ic_media_play);
		}
	}

	@Override
	public void run() {
		startPlayProgressUpdater();
	}
}