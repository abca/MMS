package control;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Formatter;

public final class Password {
	
	/* Passwort erstellen
	 * ====================
	 * Für ein neues Passwort wird zunächst ein Salt-Wert mit "generateSalt"
	 * erzeugt und anschließend das Passwort zusammen mit dem Salt-Wert mittels
	 * "hashPassword" in einen Hash-Wert umgewandelt. Dieser Hash-Wert muss
	 * zusammen mit dem Salt-Wert als String <Hash-Wert><Salt-Wert> ohne
	 * Trennzeichen in der Datenbank gespeichert werden.
	 * 
	 * -String salt = generateSalt(<password>);
	 * -String hashValue = hashPassword(<password>, salt);
	 * -Speichere salt und hashValue zusammen als Zeichenkette ohne
	 *  Trennzeichen in der Datenbank ab (Reihenfolge beachten!):
	 *  <salt>+<hashValue>
	 * 
	 * 
	 * Passwort überprüfen
	 * ===================
	 * Um ein Passwort auf Gültigkeit zu prüfen, wird die Methode
	 * "verifyPassword" verwendet. Es werden das eingebene Passwort und der
	 * komplette Eintrag des Passworts in der Datenbank benötigt, also zusammen
	 * mit dem angehängten Salt-Wert, benötigt. Der Rückgabewert vom Typ
	 * boolean entspricht true bei einem korrektem Passwort, andernfalls false.
	 * 
	 * -boolean valid = verifyPassword(<input>,<databaseEntry>);
	  */
	
	
	
	//Hashe die Zeichenkette aus Passwort und Salt
	public String hashPassword (String password, String salt) {
		
		String result = null;
		MessageDigest md = null;
		
		password = salt + password;
		
		byte[] bytes = password.getBytes();
		
		try {
			md = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] hashedPassword = md.digest(bytes);
		
		
		StringBuilder sb = new StringBuilder(hashedPassword.length * 2);
		Formatter f = new Formatter(sb);
		for (byte b : hashedPassword) {
			f.format("%02x", b);		
		}
		result = sb.toString();
		
		
		return result;
	}
	
	//Erzeuge Salt von der Länge des Passworts
	public String generateSalt(String password) {
		String result = null;
		//String null ergibt "null", daher muss "" verwendet werden!
		int n = hashPassword(password, "").length();
		//System.out.println(n);
		SecureRandom sr = new SecureRandom();
		byte[] b = new byte[10];
		
		sr.nextBytes(b);
		String salt = "";
		for (int i = 0; i < n; i++) {
		//salt = salt + String.valueOf(Math.abs(sr.nextInt(10)));
		salt = salt + Integer.toHexString(Math.abs(sr.nextInt(16)));
		}
		
		//result = String.valueOf(salt);
		result = salt;
		
		return result;
	}
	
	//Ein databaseEntry ist der Hashwert des Passworts gefolgt vom Salt
	public boolean verifyPassword (String enteredPassword, String databaseEntry) {
		boolean valid = false;
	
		String hashValue = databaseEntry.substring(((databaseEntry.length()) / 2),
				databaseEntry.length());
		//System.out.println(hashValue);
		
		String salt = databaseEntry.substring(0, ((databaseEntry.length()) / 2));
		//System.out.println(salt);
		
		if (hashPassword(enteredPassword, salt).equals(hashValue)) {
			valid = true;
		}
		
		return valid;
	}
	
	
	public static void main(String[] args) {
		
		//Test
		Password pw = new Password();
		System.out.println(pw.generateSalt("hallo"));
		System.out.println(pw.hashPassword("hallo", "37bee9d3a1144dc428c71610c890769fdc0f4019af0829d654607955c22c839c"));
		System.out.println(pw.verifyPassword("hallo","37bee9d3a1144dc428c71610c890769fdc0f4019af0829d654607955c22c839ca50b1cc166794869252e98f0fc6e102a2bf6751424307be1148bd4d6cded75b4"));
		
		
	}

}
