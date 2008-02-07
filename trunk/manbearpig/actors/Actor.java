package actors;

import data.Animal;
import data.Part;

public class Actor {
	private Animal[] animal;
	
	public Actor() {
		this(new Animal[] {Animal.COW, Animal.HORSE, Animal.COW});
	}
	
	public Actor (Animal[] animal) {
		if (animal.length != 3) {
			throw new IllegalArgumentException();
		} else {
			this.animal = animal;
		}
	}
	
	public Animal getImage(Part p) {
		return this.animal[p.getPart()];
	}
}
