package GameWorld;

import rules.Direction;
import actors.GameObject;

public class Perception {

	public GameObject[][] percept;

	public Perception(int x, int y, GameObject[][] board) {

		percept = new GameObject[3][3];
		this.loadPercept(x, y, board);

	}

	public void loadPercept(int x, int y, GameObject[][] board) {

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {

				percept[i + 1][j + 1] = board[x + i][y + j];

			}
		}
	}

	public GameObject getDirection(Direction d) {
		GameObject go = percept[1][1]; //default
		switch (d) {
		case UP:
			go = percept[1][0];
			break;
		case DOWN:
			go = percept[1][2];
			break;
		case LEFT:
			go = percept[0][1];
			break;
		case RIGHT:
			go = percept[2][1];
			break;
		case UPLEFT:
			go = percept[0][0];
			break;
		case UPRIGHT:
			go = percept[2][0];
			break;
		case DOWNLEFT:
			go = percept[0][2];
			break;
		case DOWNRIGHT:
			go = percept[2][2];
			break;
		}
		return go;
	}

}
