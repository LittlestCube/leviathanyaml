all: dev

dev:
	javac *.java

clean:
	rm -f *.class || continue
