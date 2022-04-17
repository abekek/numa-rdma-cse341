MainMenu.jar: MainMenu.class
	jar cfmv MainMenu.jar Manifest.txt MainMenu.class

MainMenu.class: ./alk224/MainMenu.java
	javac ./alk224/MainMenu.java
	mv ./alk224/MainMenu.class ./MainMenu.class

PropMngMenu.class: ./alk224/PropMngMenu.java
	javac ./alk224/PropMngMenu.java
	mv ./alk224/PropMngMenu.class ./PropMngMenu.class

clean:
	rm -f *.class
	rm -f *.jar