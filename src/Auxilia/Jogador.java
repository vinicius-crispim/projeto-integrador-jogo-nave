package Auxilia;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Jogador {

	private int x, y;
	private int dx, dy;
	private Image imagem;
	private int altu, larg;
	private List<Atirar> tiro;
	private boolean isColidiu;

	public Jogador() {
		this.x = 100;
		this.y = 100;

		isColidiu = true;
		tiro = new ArrayList<Atirar>();
	}

	public void load() {
		ImageIcon referencia = new ImageIcon("imagem\\nave.png");
		imagem = referencia.getImage();

		altu = imagem.getHeight(null);
		larg = imagem.getWidth(null);
	}

	public void update() {
		x += dx;
		y += dy;
	}

	public void saitiro() {
		this.tiro.add(new Atirar(x + larg, y + (altu / 2))); // local correto para o tiro sair da nave

	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, larg, altu);
	}

	public void keyPressed(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();

		if (codigo == KeyEvent.VK_UP) {
			if (y > 0) {
				dy = -3;
			} else {
				dy = 0;
				y = 0;

			}
		}

		if (codigo == KeyEvent.VK_DOWN) {
			if (y < 650) {
				dy = 3;
			} else if (y >= 650) {
				y = 609;
				dy = 0;
			}
		}

		if (codigo == KeyEvent.VK_LEFT) {
			if (x > 0) {
				dx = -3;
			} else {
				dx = 0;
				x = 0;
			}

		}

		if (codigo == KeyEvent.VK_RIGHT) {
			if (x <= 800) {
				dx = 3;
			} else {
				x = 790;
				dx = 0;

			}
		}

		if (codigo == KeyEvent.VK_SPACE) {

			saitiro();

		}

	}

	public void keyRelease(KeyEvent tecla) {
		int codigo = tecla.getKeyCode();

		if (codigo == KeyEvent.VK_UP) {
			dy = 0;
		}

		if (codigo == KeyEvent.VK_DOWN) {
			dy = 0;
		}

		if (codigo == KeyEvent.VK_LEFT) {
			dx = 0;
		}

		if (codigo == KeyEvent.VK_RIGHT) {
			dx = 0;
		}

	}

	public boolean isColidiu() {
		return isColidiu;
	}

	public void setColidiu(boolean isColidiu) {
		this.isColidiu = isColidiu;
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

	public List<Atirar> getTiro() {
		return tiro;
	}

}
