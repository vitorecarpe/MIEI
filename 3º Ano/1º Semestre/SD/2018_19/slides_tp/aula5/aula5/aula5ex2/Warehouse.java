package aula5ex2;

import java.util.HashMap;


public class Warehouse {

	private HashMap<String, Item> stock;

	public Warehouse(){
		this.stock= new HashMap<String,Item>();
	}

	public void supply(String item, int quantity){
		//criar novo item se não existir 
		//(usar synchronized para proteger alterações concorrentes ao hashmap)
		synchronized(this){
			if(!this.stock.containsKey(item)){
				this.stock.put(item, new Item());
			}
		}
		this.stock.get(item).supply(quantity);
	}

	public void consume(String[] items){	
		for(int i=0;i<items.length;i++){
			//continuar se item não existir
			if(!this.stock.containsKey(items[i]))
				continue;

			System.out.println("Consumer: a consumir "+items[i]);
			this.stock.get(items[i]).consume();
			System.out.println("Consumer: consumi "+items[i]);
		}
	}
}
