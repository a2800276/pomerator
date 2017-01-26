package de.kuriositaet.pomerator;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;
import java.util.List;

import static de.kuriositaet.pomerator.Stuff.exec;
import static de.kuriositaet.pomerator.Stuff.list2arr;


/**
 * Created by a2800276 on 2017-01-25.
 */
public class JavadocGenerator {

	public static void generateJavadoc(String [] input, String output ) {
		String [] javadocCommand = {"javadoc", "-d", output};
		List<String> fileNames = FileCollector.findJavaFiles( input );
		List<String> l = new LinkedList<>();
		for (String s : javadocCommand) {
			l.add( s );
		}
		l.addAll( fileNames );
		exec(list2arr(l));
	}

	public static void removeJavadocDir(String dir) {
		Path directory = Paths.get(dir);
		try {
			Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					Files.delete(file);
					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
					Files.delete(dir);
					return FileVisitResult.CONTINUE;
				}
			});
		} catch (IOException e) {
			throw new RuntimeException( e );
		}

	}

	public static void main (String [] args) {
		generateJavadoc( args, "javadoc" );
	}
}
