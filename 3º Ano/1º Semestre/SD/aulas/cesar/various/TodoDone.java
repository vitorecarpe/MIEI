package various;

public class TodoDone {
	Stack todo = null, done = null;

	public TodoDone(Stack todo, Stack done) {
		this.todo = todo;
		this.done = done;
	}
	
	public synchronized void task(int v) throws InterruptedException{
		todo.push(v);
	}
	
	public synchronized void execute () throws InterruptedException{
		synchronized (done) {
			synchronized(todo){
				done.push(todo.pop());
			}
		}
	}
	
	public void undo() throws InterruptedException{
		synchronized (todo) {
			synchronized(done){
				todo.push(done.pop());
			}
		}
	}
	
}

