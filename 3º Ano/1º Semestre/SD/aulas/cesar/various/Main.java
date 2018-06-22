package various;

public class Main {
	public static void main(String[] args) throws InterruptedException{
		Stack s1 = new Stack();
		Stack s2 = new Stack();
		
		TodoDone ds = new TodoDone(s1,s2);
		ds.done.push(1);
		
		
	}
}
