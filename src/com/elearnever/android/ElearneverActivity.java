package com.elearnever.android;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.app.AlertDialog;
import android.app.ExpandableListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.elearnever.android.content.ContentHandler;

public class ElearneverActivity extends ExpandableListActivity {

	private String xml = "4.xml";
	private ContentHandler contentHandler;
	private int fontSize = 1;
	Typeface myTypeface;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.contentlist);

		try {
			ExpandableListAdapter myAdapter;

			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser saxParser = factory.newSAXParser();
			contentHandler = new ContentHandler();
			saxParser.parse(getAssets().open(xml), contentHandler);
			myAdapter = new MyExpandableListAdapter(
					contentHandler.getSessions(), contentHandler.getPages());

			myTypeface = Typeface.createFromAsset(this.getAssets(),
					"irsans.ttf");

			TextView courseTitleTextView = (TextView) findViewById(R.id.courseTitle);
			courseTitleTextView.setText(contentHandler.getCourse().getTitle());
			courseTitleTextView.setTypeface(myTypeface);

			ExpandableListView expandableListView = getExpandableListView();
			expandableListView.setAdapter(myAdapter);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater myMenuInflater = getMenuInflater();
		myMenuInflater.inflate(R.menu.menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case (R.id.font_size_menu):
			setFontSize();
			break;

		case (R.id.feedback_menu):
			Intent i = new Intent(this, Contact.class);
			startActivity(i);
			break;

		case (R.id.about_content):
			Intent j = new Intent(this, About.class);
			startActivity(j);
			break;

		}
		return true;
	}

	private void setFontSize() {
		final CharSequence[] items = { "کوچک", "متوسط", "بزرگ" };

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("انتخاب اندازهٔ قلم");

		builder.setSingleChoiceItems(items, fontSize,
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {
						Toast.makeText(getApplicationContext(), items[item],
								Toast.LENGTH_SHORT).show();
						fontSize = item;
						dialog.dismiss();
					}
				});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {

		Intent i = new Intent(this, Reader.class);
		i.putExtra("groupPosition", groupPosition);
		i.putExtra("childPosition", childPosition);
		i.putExtra("contentHandler", contentHandler);
		i.putExtra("fontSize", fontSize);

		startActivity(i);
		return false;
	}

	public class MyExpandableListAdapter extends BaseExpandableListAdapter {

		private String[][] sessions;
		private String[] chapters;

		MyExpandableListAdapter(String[] chapters, String[][] pages) {
			this.chapters = chapters;
			this.sessions = pages;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return sessions[groupPosition][childPosition];
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setText(getChild(groupPosition, childPosition).toString());
			textView.setPadding(0, 0, 35, 0);
			return textView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return sessions[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return chapters[groupPosition];
		}

		@Override
		public int getGroupCount() {
			return chapters.length;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			TextView textView = getGenericView();
			textView.setPadding(0, 0, 15, 0);
			textView.setText(getGroup(groupPosition).toString());
			return textView;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		public TextView getGenericView() {
			// Layout parameters for the ExpandableListView
			AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 50);

			TextView textView = new TextView(ElearneverActivity.this);
			textView.setLayoutParams(lp);
			// Center the text vertically
			textView.setGravity(Gravity.CENTER_VERTICAL);
			textView.setTypeface(myTypeface);
			// Set the text starting position
			// textView.setCompoundDrawablesWithIntrinsicBounds(0, 0,
			// R.drawable.expander_ic_maximized, 0);
			return textView;
		}

	}
}