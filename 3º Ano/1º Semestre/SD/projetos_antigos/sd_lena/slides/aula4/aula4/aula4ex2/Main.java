package aula4ex2;

public class Main {

	public static void main(String[] args) {

		BoundedBuffer b = new BoundedBuffer(10);	//iniciar buffer com 10 posições
		int tc=500;			//tempo de consumo em ms: (0.5 segundos)
		int tp=1000; 		//tempo de produção em ms (1 segundo)
		int total_ops=100; 	//no total, produzir 100 items e consumir 100 items
		int N = 10;			//número total de threads
		int P = 7;  		//número de produtores
		int C = N - P;  	//número de consumidores
		double maxDebito = 0;
		int maxProd = 0;	//guarda o número de produtores referente ao débito máximo observado

		//Testar várias combinações possíveis de produtores e consumidores até achar o débito máximo
		for(P = 1; P <= 9; P++){
			C = N - P;

			System.out.println("\n==== "+P+" PRODUTORES , "+C+" CONSUMIDORES =====");
			Thread tsps[]=new Thread[P];
			Thread tscs[]=new Thread[C];

			//acertar número de operações a divisão caso não tenha resto 0
			int opsprod = total_ops/P;
			int rest = total_ops%P;
			for (int i=0;i<P;i++){
				if(i<P-1){
					tsps[i]=new Thread(new Producer(b,tp, opsprod));
				}
				else //criar última thread com o resto das operações que faltam
					tsps[i]=new Thread(new Producer(b,tp, opsprod+rest));	
				tsps[i].setName(String.valueOf(i+1));
			}

			//acertar número de operações a divisão caso não tenha resto 0
			int opscons = total_ops/C;
			rest = total_ops%C;
			for (int i=0;i<C;i++){
				if(i<C-1){
					tscs[i]=new Thread(new Consumer(b,tc,opscons));
				}
				else //criar última thread com o resto das operações que faltam
					tscs[i]=new Thread(new Consumer(b,tc,opscons+rest));
				tscs[i].setName(String.valueOf(i+1));
			}

			try {
				//iniciar cronómetro e threads
				long startTime=System.currentTimeMillis()/1000;
				for (int i=0;i<P;i++){
					tsps[i].start();
				}
				for (int i=0;i<C;i++){
					tscs[i].start();
				}

				for (int i=0;i<P;i++){
					tsps[i].join();
				}
				for (int i=0;i<C;i++){
					tscs[i].join();
				}

				//parar cronómetro e calcular débito
				long endTime=System.currentTimeMillis()/1000;
				double debito = (double)total_ops/(endTime-startTime);
				System.out.println("[P="+P+", C="+C+", Tp="+(double)tp/1000+"s, Tc="+(double)tc/1000+"s, Ops="+total_ops+"] Débito total é: " + debito+ " ops/s");
				
				//se débito obtido for maior do que o máximo observado até agora,
				//actualizar o máximo
				if(debito > maxDebito){
					maxDebito = debito;
					maxProd = P;
				}

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("\n ## Débito máximo é "+maxDebito+" ops/s, para "+maxProd+" produtores e "+(N-maxProd)+" consumidores.");
	}
}
