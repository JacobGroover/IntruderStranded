package IntruderStranded.controller;

public interface Observer<T> {

	/**
	 * 
	 * @param arg
	 */
	abstract void onUpdate(T arg);

}