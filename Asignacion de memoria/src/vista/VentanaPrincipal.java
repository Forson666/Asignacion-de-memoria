package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

import java.awt.FlowLayout;

import javax.swing.JButton;

import logica.Espacio;
import logica.Memoria;
import logica.Variable;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;
	private ArrayList<Memoria> memoria;
	private int contadorVar;
	private int ulPos;

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 650, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 515, 561);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Espacio aux[] = {new Espacio(8), new Espacio(8), new Espacio(8), new Espacio(2), new Espacio(10), new Espacio(8), new Espacio(4)};
		memoria = new ArrayList<Memoria>();
		memoria.add(new Memoria(aux));
		panel.add(memoria.get(0).graficarMem());
		
		contadorVar = 0;
		ulPos = 0;
		
		JButton btnFirstFit = new JButton("First Fit");
		btnFirstFit.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("unchecked")
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					int tamaño = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el tamaño de la variable","Tamaño de la variable",JOptionPane.PLAIN_MESSAGE));
					contadorVar++;
					Variable var = new Variable(tamaño, "P" + contadorVar);
					Espacio auxEs[] = new Espacio[memoria.get(memoria.size()-1).getNumEspacios()];
					for (int i = 0; i < auxEs.length; i++){
						auxEs[i] = new Espacio(memoria.get(memoria.size()-1).getEspacios()[i].getTamaño());
						for (int j = 0; j < memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().size(); j++){
							auxEs[i].add(new Variable(memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().get(j).getTamaño(), memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().get(j).getNombre()));
							auxEs[i].getVarEnMem().get(j).setColor(Color.BLACK);
						}
						auxEs[i].setMemDisponible(memoria.get(memoria.size()-1).getEspacios()[i].getMemDisponible());
						auxEs[i].setMemUsada(memoria.get(memoria.size()-1).getEspacios()[i].getMemUsada());
					}
					Memoria auxmem = new Memoria(auxEs);
					int auxPos = auxmem.firstFit(var);
					if (auxPos != - 1){
						memoria.add(auxmem);
						ulPos = auxPos;
						panel.add(auxmem.graficarMem());
						panel.repaint();
						scrollPane.repaint();
					}else{
						JOptionPane.showMessageDialog(null,"Memoria insuficiente","error",JOptionPane.PLAIN_MESSAGE);
					}
				}catch(NumberFormatException e){
					JOptionPane.showMessageDialog(null,"No se digito el tamaño","error",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnFirstFit.setBounds(535, 10, 89, 23);
		contentPane.add(btnFirstFit);
		
		
		JButton btnNextFit = new JButton("Next Fit");
		btnNextFit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					int tamaño = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el tamaño de la variable","Tamaño de la variable",JOptionPane.PLAIN_MESSAGE));
					contadorVar++;
					Variable var = new Variable(tamaño, "P" + contadorVar);
					Espacio auxEs[] = new Espacio[memoria.get(memoria.size()-1).getNumEspacios()];
					for (int i = 0; i < auxEs.length; i++){
						auxEs[i] = new Espacio(memoria.get(memoria.size()-1).getEspacios()[i].getTamaño());
						for (int j = 0; j < memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().size(); j++){
							auxEs[i].add(new Variable(memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().get(j).getTamaño(), memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().get(j).getNombre()));
							auxEs[i].getVarEnMem().get(j).setColor(Color.BLACK);
						}
						auxEs[i].setMemDisponible(memoria.get(memoria.size()-1).getEspacios()[i].getMemDisponible());
						auxEs[i].setMemUsada(memoria.get(memoria.size()-1).getEspacios()[i].getMemUsada());
					}
					Memoria auxmem = new Memoria(auxEs);
					int auxPos = auxmem.nextFit(var, ulPos);
					if (auxPos != - 1){
						memoria.add(auxmem);
						ulPos = auxPos;
						panel.add(auxmem.graficarMem());
						panel.repaint();
						scrollPane.repaint();
					}else{
						JOptionPane.showMessageDialog(null,"Memoria insuficiente","error",JOptionPane.PLAIN_MESSAGE);
					}
				}catch(NumberFormatException e1){
					JOptionPane.showMessageDialog(null,"No se digito el tamaño","error",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnNextFit.setBounds(535, 44, 89, 23);
		contentPane.add(btnNextFit);
		
		JButton btnBestFit = new JButton("Best Fit");
		btnBestFit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					int tamaño = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el tamaño de la variable","Tamaño de la variable",JOptionPane.PLAIN_MESSAGE));
					contadorVar++;
					Variable var = new Variable(tamaño, "P" + contadorVar);
					Espacio auxEs[] = new Espacio[memoria.get(memoria.size()-1).getNumEspacios()];
					for (int i = 0; i < auxEs.length; i++){
						auxEs[i] = new Espacio(memoria.get(memoria.size()-1).getEspacios()[i].getTamaño());
						for (int j = 0; j < memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().size(); j++){
							auxEs[i].add(new Variable(memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().get(j).getTamaño(), memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().get(j).getNombre()));
							auxEs[i].getVarEnMem().get(j).setColor(Color.BLACK);
						}
						auxEs[i].setMemDisponible(memoria.get(memoria.size()-1).getEspacios()[i].getMemDisponible());
						auxEs[i].setMemUsada(memoria.get(memoria.size()-1).getEspacios()[i].getMemUsada());
					}
					Memoria auxmem = new Memoria(auxEs);
					int auxPos = auxmem.bestFit(var);
					if (auxPos != - 1){
						memoria.add(auxmem);
						ulPos = auxPos;
						panel.add(auxmem.graficarMem());
						panel.repaint();
						scrollPane.repaint();
					}else{
						JOptionPane.showMessageDialog(null,"Memoria insuficiente","error",JOptionPane.PLAIN_MESSAGE);
					}
				}catch(NumberFormatException e2){
					JOptionPane.showMessageDialog(null,"No se digito el tamaño","error",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnBestFit.setBounds(535, 78, 89, 23);
		contentPane.add(btnBestFit);
		
		JButton btnWorstFit = new JButton("Worst Fit");
		btnWorstFit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try{
					int tamaño = Integer.parseInt(JOptionPane.showInputDialog(null,"Digite el tamaño de la variable","Tamaño de la variable",JOptionPane.PLAIN_MESSAGE));
					contadorVar++;
					Variable var = new Variable(tamaño, "P" + contadorVar);
					Espacio auxEs[] = new Espacio[memoria.get(memoria.size()-1).getNumEspacios()];
					for (int i = 0; i < auxEs.length; i++){
						auxEs[i] = new Espacio(memoria.get(memoria.size()-1).getEspacios()[i].getTamaño());
						for (int j = 0; j < memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().size(); j++){
							auxEs[i].add(new Variable(memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().get(j).getTamaño(), memoria.get(memoria.size()-1).getEspacios()[i].getVarEnMem().get(j).getNombre()));
							auxEs[i].getVarEnMem().get(j).setColor(Color.BLACK);
						}
						auxEs[i].setMemDisponible(memoria.get(memoria.size()-1).getEspacios()[i].getMemDisponible());
						auxEs[i].setMemUsada(memoria.get(memoria.size()-1).getEspacios()[i].getMemUsada());
					}
					Memoria auxmem = new Memoria(auxEs);
					int auxPos = auxmem.worstFit(var);
					if (auxPos != - 1){
						memoria.add(auxmem);
						ulPos = auxPos;
						panel.add(auxmem.graficarMem());
						panel.repaint();
						scrollPane.repaint();
					}else{
						JOptionPane.showMessageDialog(null,"Memoria insuficiente","error",JOptionPane.PLAIN_MESSAGE);
					}
				}catch(NumberFormatException e3){
					JOptionPane.showMessageDialog(null,"No se digito el tamaño","error",JOptionPane.PLAIN_MESSAGE);
				}
			}
		});
		btnWorstFit.setBounds(535, 112, 89, 23);
		contentPane.add(btnWorstFit);
	}
}
