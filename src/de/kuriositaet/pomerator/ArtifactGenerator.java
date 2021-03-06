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
	String outputDirArtifacts;
	String pomFn;
	String sourceFn;
	String classFn;
	String javadocFn;

	public ArtifactGenerator(Map<String, ?> cfg) {
		this.cfg = cfg;
		this.outputDir = (String)cfg.get("output");
		this.outputDirArtifacts = String.format("%s/%s-%s", this.outputDir, cfg.get("artifactId"), cfg.get("version"));
		if (new File(this.outputDirArtifacts).mkdirs());
		this.sourceDirs = list2arr((List<String>) this.cfg.get("sources") );
		generateAllArtifacts();
	}

	public void generateAllArtifacts() {
		generatePOM();
		generateSourceJar();
		generateClassesJar();
		generateJavadocJar();
		signFiles();
		generateBundle();
	}

	void generateBundle() {
		String jarFn = String.format("%s/bundle-%s-%s.jar", this.outputDir, cfg.get("artifactId"), cfg.get("version"));
		JarGenerator.generateJarFromDirs(
				jarFn,
				this.outputDirArtifacts
		);
	}

	static String base (String fn) {
		return new File(fn).getName();
	}
	static String asc(String fn) {
		return new File(String.format("%s.asc", fn)).getName();
	}

	void signFiles() {
		String key = (String) cfg.get("keyId");
		GPGSigner.sign( pomFn, key );
		GPGSigner.sign( sourceFn, key );
		GPGSigner.sign( classFn, key );
		GPGSigner.sign( javadocFn, key );
	}

	void generateJavadocJar() {
		String javadocOut = String.format("%s/javadoc", outputDirArtifacts);
		JavadocGenerator.generateJavadoc( this.sourceDirs, javadocOut );
		this.javadocFn = String.format("%s/%s-%s-javadoc.jar", this.outputDirArtifacts, cfg.get("artifactId"), cfg.get("version"));
		JarGenerator.generateJarFromDirs( this.javadocFn, javadocOut );
		JavadocGenerator.removeJavadocDir( javadocOut );
	}

	void generateClassesJar() {
		this.classFn = String.format("%s/%s-%s.jar", this.outputDirArtifacts, cfg.get("artifactId"), cfg.get("version"));
		String [] classDirs = list2arr( (List<String>) cfg.get("classes") );
		JarGenerator.generateJarFromDirs( this.classFn, classDirs);
	}


	void generatePOM() {
		this.pomFn = String.format("%s/%s-%s.pom", this.outputDirArtifacts, cfg.get("artifactId"), cfg.get("version"));
		POMGenerator.POMConfig cfg = new POMGenerator.POMConfig( (Map<String, String>) this.cfg );
		POMGenerator.generatePOM( this.pomFn, cfg );
	}

	void generateSourceJar() {
		this.sourceFn = String.format("%s/%s-%s-sources.jar", this.outputDirArtifacts, cfg.get("artifactId"), cfg.get("version"));

		JarGenerator.generateJarFromDirs( this.sourceFn, this.sourceDirs);
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
