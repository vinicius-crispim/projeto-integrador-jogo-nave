package Jogo;

import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.JFrame;

import Auxilia.Fase;

import Auxilia.Fase;

public class Main extends JFrame {
	public Main() {

		add(new Fase());
		setTitle("Space Wars");
		setSize(900, 708);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);

	}

	public static void main(String[] args) {

		new Main();

	}

}
