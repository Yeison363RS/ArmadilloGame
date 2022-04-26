package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controllers.Presenter;
import models.Match;

public class PanelAdmin extends JPanel implements FocusListener{
	private JPanel panelMenu;
	private JPanel panelContent;
	private JTable table;
	private DefaultTableModel model;
	
	public PanelAdmin() {
		init();
	}
	public void init() {
		this.addFocusListener(this);
		this.setLayout(new BorderLayout(10, 10));
		this.panelMenu= new JPanel();
		this.panelMenu.setBackground(Constants.COLOR_BACK);
		JLabel label =new JLabel("Partidas Autoguardadas");
		label.setFont(Constants.MESSAGE_FONT);
		panelMenu.setLayout(new BorderLayout());
		label.setForeground(Constants.COLOR_FONT);
		panelMenu.add(label,BorderLayout.CENTER);
		this.panelContent=new JPanel();
		panelContent.addMouseListener(Presenter.instanceOf());
		panelContent.setLayout(new BorderLayout(30, 30));
		this.add(panelMenu,BorderLayout.NORTH);
		setSetingsBtn();
		createModelTable();
	}
	public void setSetingsBtn(){
		JButton btn=new JButton("Cargar");
		btn.setActionCommand("cargar");
		btn.setBackground(Color.GREEN);
		btn.setFont(Constants.MY_FONT);
		btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Presenter.instanceOf().cagarPartida();
				
			}
		});
		this.add(btn,BorderLayout.SOUTH);
	}
	public void createModelTable() {
		panelContent.removeAll();
		this.panelContent.setBackground(Constants.COLOR_BACK);
		this.model = new DefaultTableModel();
		model.addColumn(Constants.TEXT_ID_MATCH);
		model.addColumn(Constants.TEXT_NAME_PLAYER);
		model.addColumn(Constants.TEXT_NUMBER_LIVES);
		String[] datas = { Constants.TEXT_ID_MATCH, Constants.TEXT_NAME_PLAYER,
				Constants.TEXT_NUMBER_LIVES};
		model.addRow(datas);
		setSettingsTable();
	}

	public void setSettingsTable() {
		this.table = new JTable(model);
		table.addMouseListener(Presenter.instanceOf());
		table.setFont(Constants.MY_FONT);
		this.panelContent.add(table,BorderLayout.CENTER);
		this.add(panelContent,BorderLayout.CENTER);
	}
	public boolean isSelected() {
		System.out.println("vomenzo");
		return table.getSelectedRow()!= -1?true:false; 
	}
	public long getIdColum() {
		System.out.println("seleciono"+model.getValueAt(table.getSelectedRow(), 0));
		return Long.parseLong((String) model.getValueAt(table.getSelectedRow(), 0));
	}
	public void updatePanelAdmin(ArrayList<Match> listProduct) {
		panelContent.removeAll();
		createModelTable();
		String[] datas = new String[3];
		for (Match product : listProduct) {
			datas[0] = String.valueOf(product.getId());
			datas[1] = product.getName();
			datas[2] = String.valueOf(product.getNumberLives());
			model.addRow(datas);
		}
	}
	@Override
	public void focusGained(FocusEvent e) {
		System.out.println("PANEL AD GANO FOCO");
	}
	@Override
	public void focusLost(FocusEvent e) {
		System.out.println("PANEL AD PERDIO FOCO");
	}
}
