package edu.observers;

public interface Observable {
	void addObserver(Observer observer);

	void notifyAllObservers();
}
