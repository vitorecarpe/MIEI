public class QuestaoMaker implements Runnable {
	Controlador c;


	public QuestaoMaker(Controlador c) {
		this.c = c;
	}

	@Override 
	public void run() {

		String[] questao = new String[2];
		int i = 0;

		try {
			while(true) {
				//questao = Util.novaPergunta();
				//c.adiciona(questao[0], questao[1]);
				c.adiciona("lala" + i, "lala" + i);
				i++;
				Thread.sleep(10 * 1000);
			}
		}
		catch(InterruptedException e) {
			e.printStackTrace();
		}
	}
}