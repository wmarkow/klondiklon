package com.github.wmarkow.klondiklon;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		ServiceRegistry.getInstance().imageReader = new AndroidImageReader();
		GameplayService.getInstance().assetsCopyService = new AndroidAssetsCopyService();

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new GameEntryPoint(), config);
	}
}
