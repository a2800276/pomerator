package de.kuriositaet.pomerator;

import util.io.IO;
import util.json.JSON;

import java.util.Map;

/**
 * Created by a2800276 on 2017-01-25.
 */
public class POMGenerator {
	static class POMConfig {
		String groupId;
		String artifactId;
		String version;
		String projectName;
		String description;
		String url;
		String licenseName;
		String licenseURL;
		String scmURL;
		String developerEmail;
		String developerName;
		String developerURL;
		String developerId;
		String depedencies;

		POMConfig() {}
		POMConfig(Map<String, String> m) {
			this();
			populate( m );
		}
		void populate(Map<String, String> m) {
			groupId        = m.get("groupId");
			artifactId     = m.get("artifactId");
			version        = m.get("version");
			projectName    = m.get("projectName");
			description    = m.get("description");
			url            = m.get("url");
			licenseName    = m.get("licenseName");
			licenseURL     = m.get("licenseURL");
			scmURL         = m.get("scmURL");
			developerEmail = m.get("developerEmail");
			developerName  = m.get("developerName");
			developerURL   = m.get("developerURL");
			developerId    = m.get("developerId");
			depedencies    = m.get("depedencies");
		}
	}
	// arg:
	// 1 : groupId
	// 2 : artifactId
	// 3 : version
	// 4 : project name
	// 5 : description
	// 6 : url
	// 7 : license name
	// 8 : license url
	// 9 : scm url
	// 10 : developer email
	// 11 : developer name
	// 12 : developer url
	// 13 : developer id
	// 14 : dependencies
	static String template =
			"<project xmlns=\"http://maven.apache.org/POM/4.0.0\"       \n"+
			"   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" \n"+
			"   xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0  \n"+
			"   http://maven.apache.org/xsd/maven-4.0.0.xsd\">          \n"+
			"   <modelVersion>4.0.0</modelVersion>\n"+
			"\n"+
			"   <groupId>%1$s</groupId>\n"+
			"   <artifactId>%2$s</artifactId>\n"+
			"   <version>%3$s</version>\n"+
			" \n"+
			"  <name>%4$s</name>\n"+
			"  <description>%5$s</description>\n"+
			"  <url>%6$s</url>\n"+
			"  <licenses>\n"+
			"    <license>\n"+
			"      <name>%7$s</name>\n"+
			"      <url>%8$s/url>\n"+
			"    </license>\n"+
			"  </licenses>\n"+
			"  <scm>\n"+
			"    <url>%9$s</url>\n"+
			"  </scm>\n"+
			"  <developers>\n"+
			"    <developer>\n"+
			"      <email>%10$s</email>\n"+
			"      <name>%11$s</name>\n"+
			"      <url>%12$s</url>\n"+
			"      <id>%13$s</id>\n"+
			"    </developer>\n"+
			"  </developers>\n"+
			"  %14$s\n"+
			"</project>\n";


	public static void generatePOM (String fn, POMConfig cfg) {
		// arg:
		// 1 : groupId
		// 2 : artifactId
		// 3 : version
		// 4 : project name
		// 5 : description
		// 6 : url
		// 7 : license name
		// 8 : license url
		// 9 : scm url
		// 10 : developer email
		// 11 : developer name
		// 12 : developer url
		// 13 : developer id
		// 14 : dependencies
		String pom = String.format(template,
				cfg.groupId,
				cfg.artifactId,
				cfg.version,
				cfg.projectName,
				cfg.description,
				cfg.url,
				cfg.licenseName,
				cfg.licenseURL,
				cfg.scmURL,
				cfg.developerEmail,
				cfg.developerName,
				cfg.developerURL,
				cfg.developerId,
				cfg.depedencies
		);

		IO.writeAll(pom.getBytes(), fn);
	}

	public static POMConfig loadCfg(String fn) {
		String json = IO.readAllString( fn );
		Map<String, String> jsoncfg = (Map<String, String>) JSON.parse( json );
		POMConfig cfg = new POMConfig(jsoncfg);
		return cfg;
	}

	public static void main (String [] args) {
		POMConfig cfg = loadCfg( args[0] );
		generatePOM( "fuckyou.pom", cfg );
	}
}
