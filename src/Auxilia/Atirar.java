package Auxilia;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class Atirar {

	private Image imagem;
	private int x, y;
	private int larg, alt;
	private boolean colidiu;

	private static final int LARG = 938;
	private static int VELO = 3; // velocidade do tiro

	public Atirar(int x, int y) {
		this.x = x;
		this.y = y;
		colidiu = true;
	}

	public void imagemtiro() {
		ImageIcon referencia = new ImageIcon("imagem\\missel.png");
		imagem = referencia.getImage();

		this.alt = imagem.getHeight(null);
		this.larg = imagem.getWidth(null);
	}

	public void atualizatiro() {
		this.x += VELO;
		if (this.x > LARG) {
			colidiu = false; // tiro sumirá
		}
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, larg, alt);
	}

	public boolean isColidiu() {
		return colidiu;
	}

	public void setColidiu(boolean colidiu) {
		this.colidiu = colidiu;
	}

	public static int getVELO() {
		return VELO;
	}

	public static void setVELO(int vELO) {
		VELO = vELO;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImagem() {
		return imagem;
	}

}
