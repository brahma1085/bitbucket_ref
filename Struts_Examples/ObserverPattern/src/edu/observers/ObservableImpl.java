package edu.observers;

import java.util.ArrayList;
import java.util.Iterator;

public class ObservableImpl implements Observable {
	private ArrayList observersList = new ArrayList();

	@Override
	public void addObserver(Observer observer) {
		if (!(observersList.contains(observer))) {
			observersList.add(observer);
		}
	}

	@Override
	public void notifyAllObservers() {
		Iterator iterator = observersList.iterator();
		while (iterator.hasNext()) {
			((Observer) iterator.next()).handleEvent();
		}
	}

}
