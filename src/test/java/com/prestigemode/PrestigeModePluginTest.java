package com.prestigemode;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class PrestigeModePluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(PrestigeModePlugin.class);
		RuneLite.main(args);
	}
}