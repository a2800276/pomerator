package de.kuriositaet.pomerator;

import static de.kuriositaet.pomerator.Stuff.exec;

/**
 * Created by a2800276 on 2017-01-25.
 */
public class GPGSigner {

	public static String DEFAULT = "A4B924E5";
	public static void sign (String filename, String key) {
		//p("signing: "+filename);
		//String gpgCommand = String.format("gpg2 -ab --no-tty --local-user %s %s", key, filename);
		String [] gpgCommand = {"gpg2", "-ab", "--no-tty", "--local-user", key, filename};
		//p(gpgCommand);
		exec(gpgCommand);
	}

	public static void main (String [] args) {
		sign(args[0], DEFAULT);
	}
}
