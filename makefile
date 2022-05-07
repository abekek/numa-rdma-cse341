MainMenu.jar:
	javac ./alk224/*.java
	mv **/*.class ./
	jar cfmv MainMenu.jar Manifest.txt PropMngMenu.class TenantMenu.class NUMAMngrMenu.class BusMngrMenu.class MainMenu.class

clean:
	rm -f *.class
	rm -f MainMenu.jar