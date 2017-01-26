package de.kuriositaet.pomerator;

import util.io.IO;
import util.json.JSON;

import java.io.File;
import java.util.List;
import java.util.Map;

import static de.kuriositaet.pomerator.Stuff.list2arr;

/**
 * Created by a2800276 on 2017-01-25.
 */
public class ArtifactGenerator {

	final String[] sourceDirs;
	Map<String, ?> cfg;
	String outputDir;
	String pomFn;
	String sourceFn;
	String classFn;
	String javadocFn;

	public ArtifactGenerator(Map<String, ?> cfg) {
		this.cfg = cfg;
		this.outputDir = (String)cfg.get("output");
		if (new File(this.outputDir).mkdirs());
		this.sourceDirs = list2arr((List<String>) this.cfg.get("sources") );
		generateAllArtifacts();
	}

	public void generateAllArtifacts() {
		generatePOM();
		generateSourceJar();
		generateClassesJar();
		generateJavadocJar();
		signFiles();
	}

	void signFiles() {
		String key = (String) cfg.get("keyId");
		GPGSigner.sign( pomFn, key );
		GPGSigner.sign( sourceFn, key );
		GPGSigner.sign( classFn, key );
		GPGSigner.sign( javadocFn, key );
	}

	void generateJavadocJar() {
		String javadocOut = String.format("%s/javadoc", outputDir);
		JavadocGenerator.generateJavadoc( this.sourceDirs, javadocOut );
		this.javadocFn = String.format("%s/%s-%s-javadoc.jar", this.outputDir, cfg.get("artifactId"), cfg.get("version"));
		JarGenerator.generateJar( this.javadocFn, javadocOut );
		JavadocGenerator.removeJavadocDir( javadocOut );
	}

	void generateClassesJar() {
		this.classFn = String.format("%s/%s-%s.jar", this.outputDir, cfg.get("artifactId"), cfg.get("version"));
		String [] classDirs = list2arr( (List<String>) cfg.get("classes") );
		JarGenerator.generateJar( this.classFn, classDirs);
	}


	void generatePOM() {
		this.pomFn = String.format("%s/%s-%s.pom", this.outputDir, cfg.get("artifactId"), cfg.get("version"));
		POMGenerator.POMConfig cfg = new POMGenerator.POMConfig( (Map<String, String>) this.cfg );
		POMGenerator.generatePOM( this.pomFn, cfg );
	}

	void generateSourceJar() {
		this.sourceFn = String.format("%s/%s-%s-sources.jar", this.outputDir, cfg.get("artifactId"), cfg.get("version"));

		JarGenerator.generateJar( this.sourceFn, this.sourceDirs);
	}

	public static void main (String [] args) {
		String fn = args[0];
		String json = IO.readAllString(fn);
		Object jsonConfig = JSON.parse(json);

		if (jsonConfig instanceof Map) {
			ArtifactGenerator gen = new ArtifactGenerator( (Map<String,?>)jsonConfig );
		} else {
			List<Map<String, ?>> cfgs = (List<Map<String, ?>>) jsonConfig;
			for (Map<String, ?> cfg : cfgs) {
				new ArtifactGenerator( cfg );
			}

		}

	}


}
