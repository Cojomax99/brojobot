package net.brojo.plugins;
 
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import net.brojo.connection.IConnector;
import net.brojo.message.Message;
 
public class TodoPlugin extends BrojoPlugin {
    
    private static String cmd = ",todo";
    private static Lock fileLock = new ReentrantLock();
    private String fileext = ".txt";
    String[] responses = new String[] {"ok", "sure", "alright", "done", "gotcha"};
    
    @Override
	public String getName(){
		return "Todo List";
	}
 
	@Override
	public String getVersion(){
		return "1.0.0.0";
	}
 
	@Override
	public String getDescription(){
		return "Manage per-channel todo lists";
	}
 
	public static boolean accepts(Message m){
		return m.getContents().toLowerCase().startsWith(cmd);
	}
 
	@Override
	public boolean onActivated(IConnector impl, Message message) {
		
        String[] msg = message.getContents().split("\\s+", 3);
        boolean error = true;
        
        if (msg.length == 2 && msg[1].trim().toLowerCase().equals("list")) {
            List<String> notes = loadFromFile(message.getRecipient());
            for (int i = 0; i < notes.size(); i++) {
                impl.send(message.getRecipient(), String.format("%d. %s", i + 1, notes.get(i)));
            }
            error = false;
        } else if (msg.length == 3) {
            if (msg[1].trim().toLowerCase().equals("add")) {
                if (msg[2].trim().length() > 0) {
                    List<String> notes = loadFromFile(message.getRecipient());
                    notes.add(msg[2].trim());
                    saveToFile(message.getRecipient(), notes);
                    impl.send(message.getRecipient(), responses[(new Random()).nextInt(responses.length)]);
                    error = false;
                }
            } else if (msg[1].trim().toLowerCase().equals("del")) {
                if (msg[2].trim().length() > 0) {
                    try {
                        int delIndex = Integer.parseInt(msg[2]);
                        List<String> notes = loadFromFile(message.getRecipient());
                        if (notes.size() >= delIndex) {
                            notes = deleteNote(notes, delIndex - 1);
                            saveToFile(message.getRecipient(), notes);
                            impl.send(message.getRecipient(), responses[(new Random()).nextInt(responses.length)]);
                            error = false;
                        }
                    } catch (NumberFormatException ex) {
                    }
                }
            }
        }
        
        if (error) {
            String instructions = "Usage: " + cmd + " add <Item> | " + cmd + " del <index> | " + cmd + " list";
            impl.send(message.getRecipient(), instructions);
        }
        return false;
	}
    
    private List<String> deleteNote(List<String> l, int index) {
        List<String> newList = new ArrayList<String>();
        for (int i = 0; i < l.size(); i++) {
            if (i != index) {
                newList.add(l.get(i));
            }
        }
        return newList;
    }
    
    private List<String> loadFromFile(String filename) {
        
        List<String> l = new ArrayList<String>();
        fileLock.lock();
        try {
            try (BufferedReader r = new BufferedReader(new FileReader(filename + fileext))) {
                String line;
                while ((line = r.readLine()) != null) {
                    if (!line.isEmpty()) {
                        l.add(line);
                    }
                }
            }
        } catch (Exception ex) {
        } finally {
            fileLock.unlock();
        }
        
        return l;
    }
    
    private void saveToFile(String filename, List<String> l) {
        
        fileLock.lock();
        try {
            BufferedWriter w = new BufferedWriter(new FileWriter(filename + fileext));
            for (String line : l) {
                w.write(line);
                w.newLine();
            }
            w.close();
        } catch (Exception ex) {
        } finally {
            fileLock.unlock();
        }
        
    }
}