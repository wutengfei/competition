package com.example.five_two.tabs;

import android.content.Context;
import android.view.View;

public interface TabHost {
	
	public void addTab(Tab tab);
	
	public void setSelection(int selection);
	
	public View getHostView(Context context);
	
}
