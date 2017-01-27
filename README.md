# Introduction

This is a (personal, non-production) tool to help me deal with
generating and signing artifacts that are required to publish an
opensource java lib to Maven Central.

At the moment, the tool takes a json file describing the the project(s)
for which artifacsts are generated (see bootstrap.json, which contains a
sample for creating this projects artifacts). It then creates jar files
for sources, classes and javadoc and signs them using gpg (which must be
available in the system PATH and the indicated key can't require a
passphrase at the moment)

# TODOs

- Manifest (main)
- Figure out how to auotmatically upload to sonatype (or whatever)
  (Thinking out loud: it's probably easiest to reverse engineer this
  from the ant task...) 
- Figure out how to automatically set up an account
- Find out whether signing, jar generation and javadoc generation can be
  done from java
- remove hardwired Unix assumptions (mainly path seperator I think)

# License

MIT
