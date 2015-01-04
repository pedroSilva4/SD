default: all

clean:
	rm -rf build/classes/*/*.class Client.jar

clear:
	clear

compile:
	cd src/; javac -d ../build/classes/ client/Client.java

jar:
	cd build/classes/; jar cfe ../../Client.jar client/Client client/ lib/ util/;

exe:
	java -jar Client.jar 127.0.0.1 50000

all: clean compile jar exe
