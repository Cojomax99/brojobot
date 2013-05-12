package net.brojo.plugins;

import net.brojo.connection.IConnector;
import net.brojo.message.Message;
import net.brojo.pluginimpl.BrojoPluginLoader;

public class ReloadPluginsPlugin extends BrojoPlugin {

	@Override
	public String getName() {
		return "ReloadPlugins";
	}

	@Override
	public String getVersion() {
		return "1";
	}

	@Override
	public String getDescription() {
		return "Reloads all plugins so the bot does not have to restart in order to load new plugins";
	}
	
	public static boolean accepts(Message m){
		return m.getContents().toLowerCase().startsWith(",reload");
	}

	@Override
	public boolean onActivated(IConnector impl, Message message) {
		BrojoPluginLoader.loadPlugins();
		
		return true;
	}

}
