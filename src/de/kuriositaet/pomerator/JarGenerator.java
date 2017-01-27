package de.kuriositaet.pomerator;

import static de.kuriositaet.pomerator.Stuff.exec;
import static de.kuriositaet.pomerator.Stuff.p;

/**
 * Created by a2800276 on 2017-01-25.
 */
public class JarGenerator {



	public static void generateJar(String jarFn, String... inputDirs) {
		if (inputDirs.length < 1) {
			throw new RuntimeException( "invalid argument, provide at least one directory" );
		}
		String [] jarCommand = {"/usr/bin/jar", "cvf", jarFn, "-C", inputDirs[0], "."} ;
		//String jarCommand = "/usr/bin/jar cf " + jarFn + "-C" + inputDirs[0] + " .";
		exec( jarCommand   );
		if (inputDirs.length > 1) {
			for (int i = 1; i!=inputDirs.length; ++i) {
			//	jarCommand[1] = "uf";
			//	jarCommand[4] = inputDirs[i];
				exec(jarCommand);
			}
		}
	}
	public static void main (String [] args) throws Throwable {
		p(args);
		generateJar( "jar.jar", args );
	}
}
