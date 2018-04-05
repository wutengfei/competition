package com.example.five_two.pages;

import com.mob.jimu.gui.Page;
import com.mob.jimu.gui.Theme;
import com.example.five_two.UMSGUI;

public class ChangePasswordPage extends Page<ChangePasswordPage> {

	public ChangePasswordPage(Theme theme) {
		super(theme);
	}
	
	public void onCreate() {
		if (!UMSGUI.isDebug()) {
			disableScreenCapture();
		}
		super.onCreate();
	}
	
}
