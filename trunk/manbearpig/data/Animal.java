package data;

public enum Animal {
	HORSE(0), COW(1), TIGER(2), LION(3);
	private int animal;

	private Animal(int animal) {
		this.animal = animal;
	}
	public int getAnimal() {
		return animal;
	}
}