package net.brojo.plugins;
 
import java.util.Random;

import net.brojo.irc.IConnector;
import net.brojo.message.Message;
 
public class DancePlugin extends BrojoPlugin {
 
        @Override
        public String getName() {
                return "Test";
        }
         
        @Override
        public String getVersion() {
                return "0.01";
        }
         
        @Override
        public String getDescription() {
                return "Test Plugin for BrojoBot, outputs 'Doop' when the user types ',hello'";
        }
        /**
        * @param m Message received
        * @return whether this plugin should be loaded
        */
        public static boolean accepts(Message m) {
            return m.getContents().startsWith(":D") && m.getContents().length() > 2;
        }
         
        @Override
        public boolean onActivated(IConnector impl, Message message) {
                String msg = "";
                Random rand = new Random();
                int choice = rand.nextInt(4);
                if (choice == 0) msg = ":D]-<";
                if (choice == 1) msg = ":D[-<";
                if (choice == 2) msg = ":D/-<";
                if (choice == 3) msg = ":D\\-<";
                impl.send(message.getRecipient(), msg);
                return false;
        }
 
}