package net.brojo.plugins;
 
import java.security.InvalidParameterException;
import java.util.Random;

import net.brojo.irc.IConnector;
import net.brojo.message.Message;
 
public class GetMoneyPlugin extends BrojoPlugin {
 
	private static String[][] d;
	
    @Override
    public String getName() {
        return "Get Money";
    }
    
    @Override
    public String getVersion() {
        return "1.0";
    }
    
    @Override
    public String getDescription() {
        return "'F*** B****es, Get Money' meme generator";
    }
    
    /**
    * @param m Message received
    * @return whether this plugin should be loaded
    */
    public static boolean accepts(Message m) {
        return m.getContents().toLowerCase().equals(",getmoney");
    }
    
    @Override
    public boolean onActivated(IConnector impl, Message message) {
        impl.send(message.getRecipient(), String.format("%s %s, %s %s", r(0), r(1), r(2), r(3)));
        return true;
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
    
    private static String r(int i) {
        return d[i][(new Random()).nextInt(d[i].length)];
    }
    
    static {
        String s = base64decode("RGlzcmVnYXJkLEZ1Y2ssRGlzZGFpbixJZ25vcmUsU2Nv" +
                "cm4sT21pdCxOZWdsZWN0LENvbmRlbW4sRXZhZGUsQWZmcm9udCxPdmVybG9v" +
                "ayxTbGlnaHQsRGlzY291bnQsQXZvaWQsRmFpbCB0byBhY2tub3dsZWRnZSxQ" +
                "YXNzIG9uLEVxdWl2b2NhdGUsUGF5IG5vIG1pbmQgdG8sUG9zdHVsYXRlIHNr" +
                "ZXB0aWNhbGx5IG9uIHRoZSB2YWx1ZSBvZixUdXJuIGEgYmxpbmQgZXllIHRv" +
                "LFVuY29uY2VybixNYW5pZmVzdCBkaXNlc3RlZW0gdG93YXJkcyxTaHVuLE92" +
                "ZXJzaWdodCxSZXNpc3QsRWx1ZGUsQ2FyZSBsZXNzIGFib3V0LEFwcGVhciBk" +
                "aXNkYWluZnVsIHRvd2FyZHM7d29tZW4sYml0Y2hlcyxmZW1hbGVzLGZhcGdp" +
                "cmxzLG1hZGFtcyxmZW1pbmluZSBjcmVhdHVyZXMsZ2lybHMsaG9uZXlzLG1h" +
                "dHJvbnMsZGFtc2Vscyx3b21lbmZvbGssZGFtZXMsbWlzc2VzLGNvdW50ZXJw" +
                "YXJ0IG9mIG1hbGUsd29tZW5raW5kLGJyb2Fkcyxkb3dhZ2VycyxsYXNzaWVz" +
                "LGVmZmVtaW5hdGUgYmVpbmdzLHRoZSBsYWRpZXMsbGFkaWVzLG11bGllYnJp" +
                "b3VzIGJlaW5ncyxza2VlemVycztvYnRhaW4sZ2V0LGFtYXNzLGFxdWlyZSxj" +
                "b2xsZWN0LGF0dGFpbixzZWN1cmUscHJvY3VyZSxyZWFwLHNjb3JlLHRha2Us" +
                "YWNoaWV2ZSxnYWluLGFkZGl0aW9uYWxpemUsY29tZSBpbnRvIHBvc3Nlc3Np" +
                "b24gb2YscmFjayB1cCxjb3JyYWwsZWZmZWN0dWF0ZSxleHRyYWN0IHNvY2lh" +
                "bGx5LWRlZmluZWQgb3duZXJzaGlwIG9mLHNjcmFwZSB0b2dldGhlcixhbm5l" +
                "eCxsb2NrIHVwLGluY3VyLGh1c3RsZSB1cCxyZXNjdWUsaHVudCxleHBvbmVu" +
                "dGlhdGUsc3Vic2lkaXplO2N1cnJlbmN5LG1vbmV5LGZpbmFuY2VzLGJyZWFk" +
                "LGNhcGl0YWwsaW5jb21lLHBhcGVyLHRyZWFzdXJlLHdlYWx0aCxjb2luYWdl" +
                "LGJhbmtyb2xsLGxvb3QsZ3JlZW5iYWNrcyxncmVlbiBzdHVmZixwcmludGVk" +
                "IGdyZWVuIHBhcGVyLHBlc29zLGRpdmlkZW5kcyxmdW5kcyxmaWF0IGN1cnJl" +
                "bmN5LGRpbmVybyxidWNrZXRzLGJhbmtub3RlLEV1cm9zLHdvbnMsbW9vbGFo" +
                "LEJlbmphbWlucyxjaGVkZGFyLGdyYXZ5");
        String[] b = s.split(";");
        d = new String[b.length][];
        for (int i = 0; i < b.length; i++) d[i] = b[i].split(",");
    }
}