package de.kuriositaet.pomerator;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import static de.kuriositaet.pomerator.Stuff.p;


/**
 * Created by a2800276 on 2017-01-25.
 */
public class FileCollector {

	/*
	* Return a list of all Java files under the provided directories.
	* */
	public static List<String> findJavaFiles(String ... baseDirs) {
		LinkedList<String> list = new LinkedList<>(  );
		for (String dir : baseDirs) {
			for (File f : new File(dir).listFiles()) {
				if ( f.isDirectory() ) {
					list.addAll( findJavaFiles( f.getAbsolutePath() ) );
				} else if (f.getName().endsWith( ".java" )) {
					try {
						list.add( f.getCanonicalPath() );
					} catch (IOException e) {
						throw new RuntimeException( e );
					}
				}
			}
		}
		return list;
	}

	public static void main (String [] args) {
		for (String n : findJavaFiles( args )) {
			p (n);
		}
	}
}
