package map;
import java.io.Serializable;
import java.util.Observable;




public abstract class MapLayer extends Observable implements Serializable {

	int width;
	int height;

	public MapLayer(int width, int height) {
		this.width = width;
		this.height = height;
	}
}
