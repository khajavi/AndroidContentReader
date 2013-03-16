package com.elearnever.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

public class Contact extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_form);
		
	}

	public void sendFeedback(View button) {

		
		final EditText nameField = (EditText) findViewById(R.id.EditTextName);
		String name = nameField.getText().toString();
			
		final EditText emailField = (EditText) findViewById(R.id.EditTextEmail);
		String email = emailField.getText().toString();
		
		final EditText feedbackField = (EditText) findViewById(R.id.EditTextFeedbackBody);
		String feedback = feedbackField.getText().toString();
		
		final Spinner feedbackSpinner = (Spinner) findViewById(R.id.SpinnerFeedbackType);
		String feedbackType = feedbackSpinner.getSelectedItem().toString();

		
		final CheckBox responseCheckbox = (CheckBox) findViewById(R.id.CheckBoxResponse);
		boolean bRequiresResponse = responseCheckbox.isChecked();

		// Take the fields and format the message contents
		String subject = formatFeedbackSubject(feedbackType);

		String message = formatFeedbackMessage(feedbackType, name,
			 email, feedback, bRequiresResponse);
		
		// Create the message
		sendFeedbackMessage(subject, message);
	}

	
	protected String formatFeedbackSubject(String feedbackType) {
		
		String strFeedbackSubjectFormat = getResources().getString(
				R.string.feedbackmessagesubject_format);

		String strFeedbackSubject = String.format(strFeedbackSubjectFormat, feedbackType);
		
		return strFeedbackSubject;

	}
	
	protected String formatFeedbackMessage(String feedbackType, String name,
			String email, String feedback, boolean bRequiresResponse) {
		
		String strFeedbackFormatMsg = getResources().getString(
				R.string.feedbackmessagebody_format);

		String strRequiresResponse = getResponseString(bRequiresResponse);

		String strFeedbackMsg = String.format(strFeedbackFormatMsg,
				feedbackType, feedback, name, email, strRequiresResponse);
		
		return strFeedbackMsg;

	}
	

	protected String getResponseString(boolean bRequiresResponse)
	{
		if(bRequiresResponse==true)
		{
			return getResources().getString(R.string.feedbackmessagebody_responseyes);
		} else {
			return getResources().getString(R.string.feedbackmessagebody_responseno);
		}
			
	}

	public void sendFeedbackMessage(String subject, String message) {

		Intent messageIntent = new Intent(android.content.Intent.ACTION_SEND);

		String aEmailList[] = { "feedback@elearnever.com" };
		messageIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);

		messageIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

		messageIntent.setType("plain/text");
		messageIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);

		startActivity(messageIntent);
	}

}