package de.kuriositaet.pomerator;

import java.io.IOException;
import java.util.List;

/**
 * Created by a2800276 on 2017-01-25.
 */
public class Stuff {
	public static void p (Object o) {
		System.out.println(o);
	}


	public static void p (Object [] os) {
		for (int i = 0; i!= os.length; ++i) {
			p (String.format("%d : %s", i, os[i]));
		}
	}

	public static void exec (String... cmd)  {
		try {
			//ProcessBuilder pb = new ProcessBuilder( cmd );
			//pb.inheritIO();
			//pb.start();
			Runtime.getRuntime().exec( cmd ).waitFor();

		} catch (IOException e) {
			throw new RuntimeException( e );
		} catch (InterruptedException e) {
			throw new RuntimeException( e );
		}
	}

	private static final String[] s = new String[0];
	public static String [] list2arr (List<String> list){
		return list.toArray( s );
	};
}
