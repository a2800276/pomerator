package de.kuriositaet.pomerator;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
			long time = System.currentTimeMillis();
			//Process proc = Runtime.getRuntime().exec( cmd );
			ProcessBuilder pb = new ProcessBuilder(cmd);
			pb.inheritIO();
			p(pb.command());
			p(System.getenv());
			pb.directory( new File(".") );
			p(pb.directory());
			Process proc = pb.start();

			int exit = proc.waitFor();
			String finalCmd = String.join(" ", cmd);
			time = System.currentTimeMillis() - time;
			p(String.format("executed `%s` in %dms, returning: %d / %d", finalCmd, time, exit, proc.exitValue()));

		} catch (IOException | InterruptedException e) {
			throw new RuntimeException( e );
		}
	}

	private static final String[] s = new String[0];
	public static String [] list2arr (List<String> list){
		return list.toArray( s );
	}


	// takes map (as returned by utils.json and returns it into a xml string.
	public static String map2xml (Map<String, ?> map) {
		return obj2xml( map, 0, null ).trim();
	}

	static String obj2xml (Object o, int depth, String parent) {
		String prefix = makePrefix(depth);
		StringBuilder b = new StringBuilder();
		if (o instanceof Map) {
			Map<String, ?> map = (Map<String, ?>)o;
			SortedSet<String> sortedKeys = new TreeSet<>( map.keySet() );
			for (String key : sortedKeys) {
				Object child = map.get(key);
				if (child instanceof List) {
					b.append( obj2xml(map.get(key), depth, key));
				} else {
					b.append(String.format("%s<%s>\n", prefix, key));
					b.append( obj2xml(map.get(key), depth+1, key));
					b.append(String.format("%s</%s>\n", prefix, key));
				}
			}
		} else if (o instanceof List) {
			// main purpose is to convert (needlessly nested) pom dependency entries:
			// <dependancies>
			//   <dependency>
			//     <artifactId>asdf</artifactId>
			//     ...
			//   </dependency>
			// </dependancies>
			// {dep : [ 1,2,3]} => <dep>1</dep><dep>2</dep><dep>3</dep>
			for (Object oo : (List)o) {
				if (parent != null) {
					Map<String, Object> newMap = new HashMap<>();
					newMap.put(parent, oo);
					String x = obj2xml(newMap, depth, null);
					b.append(x);
				} else {
					b.append(obj2xml(oo, depth, null));
				}
			}
		} else {
			b.append( String.format( "%s%s\n", prefix, o.toString() ) );
		}
		return b.toString();
	}

	static String makePrefix(int depth) {
		StringBuilder b = new StringBuilder( );
		for (int i = 0 ; i!=depth; ++i) {
			b.append("  ");
		}
		return b.toString();
	}
}
