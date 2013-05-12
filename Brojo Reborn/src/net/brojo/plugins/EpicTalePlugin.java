package net.brojo.plugins;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
 
import net.brojo.connection.IConnector;
import net.brojo.message.Message;
 
public class EpicTalePlugin extends BrojoPlugin {
 
    private static Set<String> isRunning = Collections.synchronizedSet(new HashSet<String>());
    private static final ArrayList<String> story = new ArrayList<String>();
 
    @Override
    public String getName() {
      return "Newthead's Epic Tale";
	}
 
	@Override
	public String getVersion() {
		return "1.0";
	}
 
	@Override
	public String getDescription() {
		return "Newthead's Epic Tale, courtesy of 'The Legend of Zelda: The Wind Waker'";
	}
	
	/**
	 * @param m Message received
	 * @return whether this plugin should be loaded
	 */
	public static boolean accepts(Message m) {
		return m.getContents().toLowerCase().startsWith(",epictale");
	}
 
	@Override
	public boolean onActivated(final IConnector impl, Message message) {
		final String channel = message.getRecipient();
		String[] parseMessage = message.getContents().trim().split("\\s+", 2);
 
        if (true) {
 
            if (parseMessage.length > 1 && parseMessage[1].equalsIgnoreCase("stop")) {
                stopRunning(channel);
            } else if (!isRunning.contains(channel)) {
                new Thread() {
                    public void run() {
                        isRunning.add(channel);
                        for (int storyLine = 0; storyLine < story.size() && isRunning.contains(channel); storyLine++) {
                            String line = base64decode(story.get(storyLine));
                            if (line != null && !line.isEmpty()) {
                                impl.send(channel, line);
                                try {
                                    Thread.sleep((new Random()).nextInt(line.length()) * 70 + 6000);
                                } catch (InterruptedException ex) {
                                    System.err.println(ex.getMessage());
                                }
                            }
                        }
                        isRunning.remove(channel);
                    }
                }.start();
            }
        }
		return true;
	}
 
    public void stopRunning(String channel) {
        isRunning.remove(channel);
    }
 
    // Stupid Base64 decoding algorithm here, since Java doesn't have any built-in method
    // Thanks, Oracle....thoracle
    private static String base64table = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
    protected static String base64decode(String s) {
 
        if (s.length() % 4 != 0) {
            throw new InvalidParameterException("String length invalid for Base64 decoding!");
        }
        char[] buffer = new char[3];
 
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i += 4) {
            buffer[0] = (char) ((base64table.indexOf(s.charAt(i + 0)) << 2
                    | base64table.indexOf(s.charAt(i + 1)) >> 4) & 0xff);
            result.append(buffer[0]);
 
            if (s.charAt(i + 2) != '=') {
                buffer[1] = (char) ((base64table.indexOf(s.charAt(i + 1)) << 4
                        | base64table.indexOf(s.charAt(i + 2)) >> 2) & 0xff);
                result.append(buffer[1]);
            }
 
            if (s.charAt(i + 3) != '=') {
                buffer[2] = (char) ((base64table.indexOf(s.charAt(i + 2)) << 6
                        | base64table.indexOf(s.charAt(i + 3))) & 0xff);
                result.append(buffer[2]);
            }
 
        }
        return result.toString();
    }
 
    static {
        story.add("aHR0cDovL3NvdW5kY2xvdWQuY29tL2ljaGFzZWdvb2R3aW4vMS0wMy10aGUtbGVnZW5kYXJ5LWhlcm8jcGxheQ==");
        story.add("UGxlYXNlIHNpdCBhbmQgbGlzdGVuIGFzIEkgdGVsbCB5b3Ugb2YgYSBsb25nLWFnbyBsZWdlbmQu");
        story.add("VGhpcyBpcyBidXQgb25lIG9mIHRoZSBsZWdlbmRzIG9mIHdoaWNoIHRoZSBwZW9wbGUgc3BlYWsuLi4=");
        story.add("TG9uZyBhZ28sIHRoZXJlIGV4aXN0ZWQgYSBraW5nZG9tIHdoZXJlIGEgZ29sZGVuIHBvd2VyIGxheSBoaWRkZW4u");
        story.add("SXQgd2FzIGEgcHJvc3Blcm91cyBsYW5kIGJsZXNzZWQgd2l0aCBncmVlbiBmb3Jlc3RzLCB0YWxsIG1vdW50YWlucywgYW5kIHBlYWNlLg==");
        story.add("QnV0IG9uZSBkYXkgYSBtYW4gb2YgZ3JlYXQgZXZpbCBmb3VuZCB0aGUgZ29sZGVuIHBvd2VyIGFuZCB0b29rIGl0IGZvciBoaW1zZWxmLi4u");
        story.add("V2l0aCBpdHMgc3RyZW5ndGggYXQgaGlzIGNvbW1hbmQsIGhlIHNwcmVhZCBkYXJrbmVzcyBhY3Jvc3MgdGhlIGtpbmdkb20u");
        story.add("QnV0IHRoZW4sIHdoZW4gYWxsIGhvcGUgaGFkIGRpZWQsIGFuZCB0aGUgaG91ciBvZiBkb29tIHNlZW1lZCBhdCBoYW5kLi4u");
        story.add("Li4uYSB5b3VuZyBib3kgY2xvdGhlZCBpbiBncmVlbiBhcHBlYXJlZCBhcyBpZiBmcm9tIG5vd2hlcmUu");
        story.add("V2llbGRpbmcgdGhlIGJsYWRlIG9mIGV2aWwncyBiYW5lLCBoZSBzZWFsZWQgdGhlIGRhcmsgb25lIGF3YXkgYW5kIGdhdmUgdGhlIGxhbmQgbGlnaHQu");
        story.add("VGhpcyBib3ksIHdobyB0cmF2ZWxlZCB0aHJvdWdoIHRpbWUgdG8gc2F2ZSB0aGUgbGFuZCwgd2FzIGtub3duIGFzIHRoZSBIZXJvIG9mIFRpbWUu");
        story.add("VGhlIGJveSdzIHRhbGUgd2FzIHBhc3NlZCBkb3duIHRocm91Z2ggZ2VuZXJhdGlvbnMgdW50aWwgaXQgYmVjYW1lIGxlZ2VuZC4=");
        story.add("QnV0IHRoZW4uLi5hIGRheSBjYW1lIHdoZW4gYSBmZWxsIHdpbmQgYmVnYW4gdG8gYmxvdyBhY3Jvc3MgdGhlIGtpbmdkb20u");
        story.add("VGhlIGdyZWF0IGV2aWwgdGhhdCBhbGwgdGhvdWdoIGhhZCBiZWVuIGZvcmV2ZXIgc2VhbGVkIGF3YXkgYnkgdGhlIGhlcm8uLi4=");
        story.add("Li4ub25jZSBhZ2FpbiBjcmVwdCBmb3J0aCBmcm9tIHRoZSBkZXB0aHMgb2YgdGhlIGVhcnRoLCBlYWdlciB0byByZXN1bWUgaXRzIGRhcmsgZGVzaWducy4=");
        story.add("VGhlIHBlb3BsZSBiZWxpZXZlZCB0aGF0IHRoZSBIZXJvIG9mIFRpbWUgd291bGQgYWdhaW4gY29tZSB0byBzYXZlIHRoZW0u");
        story.add("Li4uQnV0IHRoZSBoZXJvIGRpZCBub3QgYXBwZWFyLg==");
        story.add("RmFjZWQgYnkgYW4gb25zbGF1Z2h0IG9mIGV2aWwsIHRoZSBwZW9wbGUgY291bGQgZG8gbm90aGluZyBidXQgYXBwZWFsIHRvIHRoZSBnb2RzLg==");
        story.add("SW4gdGhlaXIgbGFzdCBob3VyLCBhcyBkb29tIGRyZXcgbmlnaCwgdGhleSBsZWZ0IHRoZWlyIGZ1dHVyZSBpbiB0aGUgaGFuZHMgb2YgZmF0ZS4=");
        story.add("V2hhdCBiZWNhbWUgb2YgdGhhdCBraW5nZG9tLi4uPw==");
        story.add("Tm9uZSByZW1haW4gd2hvIGtub3cu");
        story.add("VGhlIG1lbW9yeSBvZiB0aGUga2luZ2RvbSB2YW5pc2hlZCwgYnV0IGl0cyBsZWdlbmQgc3Vydml2ZWQgb24gdGhlIHdpbmQncyBicmVhdGgu");
        story.add("T24gYSBjZXJ0YWluIGlzbGFuZCwgaXQgYmVjYW1lIGN1c3RvbWFyeSB0byBnYXJiIGJveXMgaW4gZ3JlZW4gd2hlbiB0aGV5IGNhbWUgb2YgYWdlLg==");
        story.add("Q2xvdGhlZCBpbiB0aGUgZ3JlZW4gb2YgZmllbGRzLCB0aGV5IGFzcGlyZWQgdG8gZmluZCBoZXJvaWMgYmxhZGVzIGFuZCBjYXN0IGRvd24gZXZpbC4=");
        story.add("VGhlIGVsZGVycyB3aXNoZWQgb25seSBmb3IgdGhlIHlvdXRocyB0byBrbm93IGNvdXJhZ2UgbGlrZSB0aGUgaGVybyBvZiBsZWdlbmQuLi4=");
        story.add("fiBNeSB0YWxlIGVuZHMgaGVyZSB+");
    }
}