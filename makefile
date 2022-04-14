MainMenu.jar: MainMenu.class
	jar cfmv MainMenu.jar Manifest.txt MainMenu.class

MainMenu.class: ./alk224/MainMenu.java
	javac ./alk224/MainMenu.java
	mv ./alk224/MainMenu.class ./MainMenu.class

clean:
	rm -f MainMenu.jar MainMenu.class