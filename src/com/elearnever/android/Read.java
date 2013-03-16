//package com.elearnever.android;
//
//import java.io.IOException;
//
//import javax.xml.parsers.ParserConfigurationException;
//
//import org.xml.sax.SAXException;
//
//import android.app.Activity;
//import android.content.Context;
//import android.content.res.AssetFileDescriptor;
//import android.content.res.Configuration;
//import android.graphics.Color;
//import android.media.MediaPlayer;
//import android.media.MediaPlayer.OnCompletionListener;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Parcelable;
//import android.support.v4.view.PagerAdapter;
//import android.support.v4.view.ViewPager;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.webkit.WebSettings;
//import android.webkit.WebView;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.SeekBar;
//import android.widget.TextView;
//
//public class Read extends Activity implements OnClickListener, OnTouchListener,
//		OnCompletionListener, Runnable {
//
//	private Button buttonPlayStop;
//	private MediaPlayer mediaPlayer;
//	private SeekBar seekBar;
//	GetContent content;
//	private final Handler handler = new Handler();
//	private int chapterPosition;
//	private int pagePosition;
//	private boolean isEscaped = false;
//	private LinearLayout layout;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.read);
//		
//		try {
//			content = new GetContent(getAssets().open("4.xml"));
//
//			Bundle extras = getIntent().getExtras();
//			chapterPosition = extras.getInt("groupPosition");
//			pagePosition = extras.getInt("childPosition");
//
//			String pageString = content.handler.getCourse().getSessions()
//					.get(chapterPosition).getPages().get(pagePosition)
//					.getContent();
//
//			StringBuffer stringBuffer = new StringBuffer();
//			stringBuffer.append(getString(R.string.template));
//			stringBuffer.append(pageString);
//			stringBuffer.append("</body></html>");
//
//			String pageString2 = stringBuffer.toString();
//
//			WebView webView = (WebView) findViewById(R.id.webview);
//
//			WebSettings settings = webView.getSettings();
//			settings.setDefaultTextEncodingName("utf-8");
//			settings.setFixedFontFamily("Iranian Sans");
//
//			webView.loadDataWithBaseURL(null, pageString2, "text/html",
//					"uft-8", null);
//			settings.setTextSize(WebSettings.TextSize.NORMAL);
//
//		} catch (ParserConfigurationException e) {
//			e.printStackTrace();
//		} catch (SAXException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}	
//
//		initViews();
//	}
//
//	
//
//	@Override
//	protected void onStop() {
//		super.onPause();
//		isEscaped = true;
//		handler.removeCallbacks(this);
//		mediaPlayer.stop();
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle outState) {
//		Log.d("Player", "got to onSaveInstanceState()");
//		super.onSaveInstanceState(outState);
//		outState.putInt("current_position", mediaPlayer.getCurrentPosition());
//		// handler.removeCallbacks(this);
//	}
//
//	@Override
//	public void onConfigurationChanged(Configuration newConfig) {
//		super.onConfigurationChanged(newConfig);
//		// setContentView(R.layout.main);
//		// initViews();
//		// startPlayProgressUpdater();
//		// handler.removeCallbacks(this);
//		// handler.post(this);
//	}
//
//	private void initViews() {
//		Log.d("Player", "got to initViews()");
//		buttonPlayStop = (Button) findViewById(R.id.ButtonPlayStop);
//		buttonPlayStop.setOnClickListener(this);
//
//		try {
//			mediaPlayer = new MediaPlayer();
//
//			String audioUrl = content.handler.getCourse().getSessions()
//					.get(chapterPosition).getPages().get(pagePosition)
//					.getAudioURL();
//
//			AssetFileDescriptor descriptor = getAssets().openFd(
//					"sounds/" + audioUrl + ".ogg");
//
//			mediaPlayer.setDataSource(descriptor.getFileDescriptor(),
//					descriptor.getStartOffset(), descriptor.getLength());
//			mediaPlayer.prepare();
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//		} catch (IllegalStateException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		mediaPlayer.setOnCompletionListener(this);
//		seekBar = (SeekBar) findViewById(R.id.SeekBar);
//		seekBar.setMax(mediaPlayer.getDuration());
//		seekBar.setOnTouchListener(this);
//	}
//
//	@Override
//	public void run() {
//		startPlayProgressUpdater();
//	}
//
//	@Override
//	public void onCompletion(MediaPlayer mp) {
//		mp.seekTo(0);
//	}
//
//	public void pauseMedia() {
//		mediaPlayer.pause();
//	}
//
//	public void restore() {
//	}
//
//	@Override
//	public Object onRetainNonConfigurationInstance() {
//		return mediaPlayer;
//	}
//
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		Log.d("Player", "got to onTouch");
//
//		seekChange(v);
//		return false;
//	}
//
//	private void seekChange(View v) {
//		if (mediaPlayer.isPlaying()) {
//			SeekBar sb = (SeekBar) v;
//			mediaPlayer.seekTo(sb.getProgress());
//		}
//	}
//
//	@Override
//	public void onClick(View v) {
//
//		switch (v.getId()) {
//		case R.id.ButtonPlayStop:
//			buttonClick();
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	public void hide() {
//		layout.setVisibility(View.INVISIBLE);
//	}
//
//	public void startPlayProgressUpdater() {
//		Log.d("Player", "got to startPlayProgressUpdater()");
//		seekBar.setProgress(mediaPlayer.getCurrentPosition());
//		int pos = mediaPlayer.getCurrentPosition();
//		Log.d("Player", "Current position is " + Integer.toString(pos));
//		Log.d("Player", "got to After seekBar.setProgress");
//		if (mediaPlayer.isPlaying()) {
//			Log.d("Player", "got to before handler.postDelayed");
//			handler.postDelayed(this, 1000);
//			Log.d("Player", "got to after handler.postDelayed");
//
//		} else if (mediaPlayer.getCurrentPosition() < mediaPlayer.getDuration()) {
//			mediaPlayer.pause();
//			buttonPlayStop
//					.setBackgroundResource(android.R.drawable.ic_media_play);
//		} else if (isEscaped) {
//			mediaPlayer.pause();
//		}
//	}
//
//	private void buttonClick() {
//		if (!mediaPlayer.isPlaying()) {
//			try {
//				mediaPlayer.start();
//				buttonPlayStop
//						.setBackgroundResource(android.R.drawable.ic_media_pause);
//				Log.d("Player",
//						"got to before startPlayProgressUpdater in buttonClick");
//				startPlayProgressUpdater();
//			} catch (IllegalStateException e) {
//				mediaPlayer.pause();
//			}
//		} else {
//			buttonPlayStop
//					.setBackgroundResource(android.R.drawable.ic_media_play);
//			mediaPlayer.pause();
//			seekBar.setProgress(mediaPlayer.getCurrentPosition());
//
//		}
//	}
//
//}
