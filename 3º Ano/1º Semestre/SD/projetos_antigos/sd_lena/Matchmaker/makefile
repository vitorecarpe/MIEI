JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	Player.java \
        Players.java \
        EquipaLog.java \
        ClientWrite.java \
	Jogo.java \
        Matchmaker.java \
        Teste.java \
	TreatClientWrite.java \
	WrongPasswordException.java \
        WantToPlay.java \
        TreatClientRead.java 

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class
