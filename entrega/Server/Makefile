SRC=com/warehouse
BUILD=build/classes

default: all

clean:
	rm -rf $(BUILD)/$(SRC)/*/*.class Server.jar

clear:
	clear

compile:
	cd src; javac -d ../$(BUILD)/ $(SRC)/server/Server.java

jar:
	cd $(BUILD)/; jar cfe ../../Server.jar $(SRC)/server/Server $(SRC)/server/ $(SRC)/LocalClient/ $(SRC)/handlers/ $(SRC)/shared/ $(SRC)/tasks/ $(SRC)/tools/ $(SRC)/users/ $(SRC)/util/;

exe:
	java -jar Server.jar 50000

all: clean compile jar exe
