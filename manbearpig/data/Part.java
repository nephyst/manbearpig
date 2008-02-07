package data;

public enum Part {
	TAIL(0), BODY(1), HEAD(2);
	private int part;

	private Part(int part) {
		this.part = part;
	}
	public int getPart() {
		return part;
	}
}
