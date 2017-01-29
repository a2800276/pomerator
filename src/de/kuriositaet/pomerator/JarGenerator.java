package de.kuriositaet.pomerator;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static de.kuriositaet.pomerator.Stuff.*;

/**
 * Created by a2800276 on 2017-01-25.
 */
public class JarGenerator {



	public static void generateJarFromDirs(String jarFn, String... inputDirs) {
		if (inputDirs.length < 1) {
			throw new RuntimeException( "invalid argument, provide at least one directory" );
		}
		String [] jarCommand = {"/usr/bin/jar", "cvf", jarFn, "-C", inputDirs[0], "."} ;
		//String jarCommand = "/usr/bin/jar cf " + jarFn + "-C" + inputDirs[0] + " .";
		exec( jarCommand   );
		if (inputDirs.length > 1) {
			for (int i = 1; i!=inputDirs.length; ++i) {
				jarCommand[1] = "uf";
				jarCommand[4] = inputDirs[i];
				exec(jarCommand);
			}
		}
	}
	public static void generateJarFromFiles(String jarFn, String outputDir, String... files) {
		String [] jarCommand = {"/usr/bin/jar", "cvf", jarFn, "-C", outputDir};
		List list = new LinkedList(Arrays.asList( jarCommand ));
		list.add( String.join(" ", files));
		exec(list2arr(list));
	}
	public static void main (String [] args) throws Throwable {
		p(args);
		generateJarFromDirs( "jar.jar", args );
	}
}
