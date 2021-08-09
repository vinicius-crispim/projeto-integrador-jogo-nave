package Auxilia;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener {

	private Image fundo;
	private Jogador jogador;
	private Timer timer;
	private List<Inimigos> inimigos;
	private boolean vivo;
	private String nome;
	private int pontos;

	public Fase() {
		setFocusable(true);
		setDoubleBuffered(true);

		ImageIcon referencia = new ImageIcon("imagem\\background.jpg");
		fundo = referencia.getImage();

		jogador = new Jogador();
		jogador.load();

		addKeyListener(new TecladoAdaptado());

		timer = new Timer(5, this);

		timer.start();

		vivo = true;

		mostrainimigos();

	}

	public void mostrainimigos() {

		inimigos = new ArrayList<Inimigos>();
		int spawn = 500;

		for (int i = 0; i < spawn; i++) {
			int x = (int) (Math.random() * 22000 + 900);
			int y = (int) (Math.random() * 700 + 30);
			inimigos.add(new Inimigos(x, y));

		}

	}

	public void paint(Graphics g) {

		Graphics2D graficos = (Graphics2D) g;
		if (vivo == true) {

			graficos.drawImage(fundo, 0, 0, null);
			graficos.drawImage(jogador.getImagem(), jogador.getX(), jogador.getY(), this);

			List<Atirar> tiro = jogador.getTiro(); // coloca tiros na fase
			for (int lim = 0; lim < tiro.size(); lim++) {
				Atirar i = tiro.get(lim);
				i.imagemtiro();
				graficos.drawImage(i.getImagem(), i.getX(), i.getY(), this);
			}

			for (int h = 0; h < inimigos.size(); h++) {
				Inimigos ini = inimigos.get(h);
				ini.imageminimigo();
				graficos.drawImage(ini.getImagem(), ini.getX(), ini.getY(), this);
			}
		} else if (vivo == false) {

			ImageIcon FIM = new ImageIcon("imagem\\FIM.jpg");
			graficos.drawImage(FIM.getImage(), 250, 200, this);

		}

		g.dispose();

	}

	public void actionPerformed(ActionEvent arg0) {
		jogador.update();

		List<Atirar> tiro = jogador.getTiro(); // coloca tiros na fase
		for (int lim = 0; lim < tiro.size(); lim++) {
			Atirar i = tiro.get(lim);
			if (i.isColidiu()) {
				i.atualizatiro();
			} else {
				tiro.remove(lim);
			}
		}

		for (int h = 0; h < inimigos.size(); h++) {
			Inimigos ini = inimigos.get(h);
			if (ini.isColidiu()) {
				ini.atualizarinimigo();
			} else {
				inimigos.remove(h);
			}
		}

		validarColisao();

		repaint();

	}

	public void registrarpontuação() {

		Scanner sc = new Scanner(System.in);

		Pontuação pontuação = new Pontuação(nome, pontos);
		System.out.println("Informe o seu Nickname:");
		String nome = sc.next();
		pontuação.setNome(nome);
		System.out.println("Sua pontuação foi: " + pontos);
		try {
			File destino = new File("C:\\Users\\vinic\\OneDrive\\Área de Trabalho\\FACULDADE\\RANK.txt");
			FileWriter fw = new FileWriter(destino, true);
			PrintWriter ps = new PrintWriter(fw, true);
			ps.println(pontuação.getNome() + "," + pontuação.getPontos());

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void mostrarrank() {

		try {
			List<Pontuação> pontuacao = new ArrayList<Pontuação>();

			String line;
			BufferedReader data = new BufferedReader(new InputStreamReader(
					new FileInputStream("C:\\Users\\vinic\\OneDrive\\Área de Trabalho\\FACULDADE\\RANK.txt")));

			while ((line = data.readLine()) != null) {
				String[] vetpontuacao = line.split(",");
				Pontuação PontuaçãoTemporaria = new Pontuação(vetpontuacao[0], Integer.parseInt(vetpontuacao[1]));
				pontuacao.add(PontuaçãoTemporaria);
			}

			data.close();

			for (int i = 0; i < pontuacao.size(); i++) {
				for (int j = 0; j < pontuacao.size() - 1; j++) {
					if (pontuacao.get(j).getPontos() < pontuacao.get(j + 1).getPontos()) {

						int aux = 0;
						String auxNome = "";

						aux = pontuacao.get(j).getPontos();
						auxNome = pontuacao.get(j).getNome();
						/* vet[j] = vet[j+1]; */

						pontuacao.get(j).setPontos(pontuacao.get(j + 1).getPontos());
						pontuacao.get(j).setNome(pontuacao.get(j + 1).getNome());

						pontuacao.get(j + 1).setPontos(aux);
						pontuacao.get(j + 1).setNome(auxNome);
					}
				}
			}

			for (Pontuação vetpontuacao : pontuacao) {
				vetpontuacao.printarrank();
			}
		} catch (Exception e) {
			System.out.println("Erro ao mostrar rank: " + e.getMessage());
		}

	}

	public void validarColisao() {

		Scanner obj = new Scanner(System.in);

		Rectangle naveJogador = jogador.getBounds();
		Rectangle inimigoNave;
		Rectangle tironave;

		for (int i = 0; i < inimigos.size(); i++) {
			Inimigos temporario = inimigos.get(i);
			inimigoNave = temporario.getBounds();
			if (naveJogador.intersects(inimigoNave)) {
				jogador.setColidiu(false);
				temporario.setColidiu(false);
				vivo = false;

				registrarpontuação();

				mostrarrank();

				break;

			}
		}

		List<Atirar> tiro = jogador.getTiro();
		for (int t = 0; t < tiro.size(); t++) {
			Atirar temporariotiro = tiro.get(t);
			tironave = temporariotiro.getBounds();
			for (int f = 0; f < inimigos.size(); f++) {
				Inimigos temporario = inimigos.get(f);
				inimigoNave = temporario.getBounds();
				if (tironave.intersects(inimigoNave)) {
					temporariotiro.setColidiu(false);
					temporario.setColidiu(false);
					pontos++;
				}
			}
		}
	}

	private class TecladoAdaptado extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			jogador.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			jogador.keyRelease(e);
		}
	}
}
