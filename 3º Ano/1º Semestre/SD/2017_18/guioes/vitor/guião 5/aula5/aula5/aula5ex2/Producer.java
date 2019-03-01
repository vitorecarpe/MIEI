package aula5ex2;


public class Producer implements Runnable {
		
		private Warehouse armazem;

		public Producer(Warehouse b){
			this.armazem=b;
		}
		
		@Override
		public void run() {
			
				System.out.println("Producer: adicionei uma unidade de item1");
				this.armazem.supply("item1", 1);
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println("Producer: adicionei uma unidade de item2");
				this.armazem.supply("item2", 1);
				
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				System.out.println("Producer: adicionei uma unidade de item3");
				this.armazem.supply("item3", 1);				
			}
		} 

