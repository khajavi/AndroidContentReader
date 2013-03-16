package com.elearnever.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RelativeLayout;

public class WebViewFragment extends Fragment {

	private final String encoding = "utf-8";
	private final String mimeType = "text/html";
	private String html;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (container == null) { // why?
			return null;
		}
		RelativeLayout rl = (RelativeLayout) inflater.inflate(
				R.layout.webview_fragment_layout, container, false);
		WebView wv = (WebView) rl.findViewById(R.id.fragment_web_view);
		html = this.getArguments().getString("html");
		wv.loadDataWithBaseURL(null, html, mimeType, encoding, null);
		WebSettings webSettings = wv.getSettings();

		switch (this.getArguments().getInt("fontSize")) {
		case 0:
			webSettings.setTextSize(WebSettings.TextSize.SMALLER);
			break;
		case 1:
			webSettings.setTextSize(WebSettings.TextSize.NORMAL);
			break;
		case 2:
			webSettings.setTextSize(WebSettings.TextSize.LARGER);
			break;
			
		default:
			break;
		}

		return rl;
	}
}
