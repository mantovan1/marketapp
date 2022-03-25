package com.example.marketapp.observerpattern;

public interface ISubject {

    public void addObserver(IObserver i);
    public void removeObserver(IObserver i);
    public void notifyObserver();

}
