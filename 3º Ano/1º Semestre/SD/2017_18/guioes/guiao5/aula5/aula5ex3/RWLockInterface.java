package aula5ex3;

public interface RWLockInterface {

	public abstract void readLock();

	public abstract void readUnlock();

	public abstract void writeLock();

	public abstract void writeUnlock();

}