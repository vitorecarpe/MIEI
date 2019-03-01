package readwritelock;

public interface ReadWriteLock {
	public enum OperationType {
		Read, Write
	}

	public void lock(OperationType op);
	public void unlock(OperationType op);
	public String printCurrent();
}
