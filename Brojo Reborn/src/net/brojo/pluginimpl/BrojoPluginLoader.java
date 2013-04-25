package net.brojo.pluginimpl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.brojo.plugins.BrojoPlugin;

public class BrojoPluginLoader {
	
	/**
	 * List of all loaded plugins
	 */
	public static List<Class<? extends BrojoPlugin>> pluginList = new ArrayList<Class<? extends BrojoPlugin>>();
	
	/**
	 * List of plugin managers, used for reloading plugins
	 */
	public static List<BrojoPluginManager> pluginManagers = new ArrayList<BrojoPluginManager>();

	public BrojoPluginLoader() {
		
	}
	
	/**
	 * Register a plugin manager
	 * @param manager BrojoPluginManager to be reloaded
	 */
	public static void registerPluginManager(BrojoPluginManager manager) {
		pluginManagers.add(manager);
	}
	
	/**
	 * Read all plugin classes in the net.brojo.plugins package and load them
	 */
    public static void loadPlugins() {
        try {
            File file = new File((net.brojo.plugins.BrojoPlugin.class).getProtectionDomain().getCodeSource().getLocation().toURI());
            ClassLoader classloader = (net.brojo.plugins.BrojoPlugin.class).getClassLoader();
            if(file.isDirectory()) {
                Package pluginpkg = (net.brojo.plugins.BrojoPlugin.class).getPackage();
                if(pluginpkg != null) {
                    String packagedir = pluginpkg.getName().replace('.', File.separatorChar);
                    file = new File(file, packagedir);
                }
                File classfiles[] = file.listFiles();
                if(classfiles != null) {
                    for(int i = 0; i < classfiles.length; i++) {
                        String classname = classfiles[i].getName();
                        if(classfiles[i].isFile() && classname.endsWith(".class")) {
                            classname = pluginpkg.getName() + "." + classname.split("\\.")[0];
                            Class c = classloader.loadClass(classname);
                            
                            if (BrojoPlugin.class.isAssignableFrom(c)) {
                            	if (pluginList.contains(c))
                            		pluginList.remove(c);
                            	
                            	pluginList.add(c);
                            	System.out.printf("Loading plugin: %s\n", c.getName());
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        for (BrojoPluginManager pluginManager : pluginManagers) {
        	pluginManager.loadPlugins();
        }
    }

}
