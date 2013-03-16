package com.elearnever.android;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
public class About extends Activity {
   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.about);
      
      TextView textView = (TextView) findViewById(R.id.about_content);
      textView.setText(Html.fromHtml(this.getString(R.string.about_text)));
      
      Typeface irsans = Typeface.createFromAsset(getAssets(), "irsans.ttf");
      textView.setTypeface(irsans);
   }
}