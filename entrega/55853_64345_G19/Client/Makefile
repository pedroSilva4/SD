BUILD=build/classes
IP=127.0.0.1
PORT=50000

default: all

clean:
	rm -rf $(BUILD)/*/*.class Client.jar

clear:
	clear

compile:
	cd src/; javac -d ../$(BUILD)/ client/Client.java

jar:
	cd $(BUILD)/; jar cfe ../../Client.jar client/Client client/ lib/ util/;

exe:
	java -jar Client.jar $(IP) $(PORT)

all: clean compile jar exe
