package views;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

import controllers.Presenter;

/*Es la pantalla principal del juego
 * @author Yeison Rodriguez
 */
public class FrameMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private PanelBoard panelBoard;
	private PanelAdmin panelAdmin;
	private ManagerSound soundMove;

	public FrameMain() {
		this.panelAdmin = new PanelAdmin();
		this.soundMove = new ManagerSound(Constants.PATH_SOUND_BACK);
		this.setSize(new Dimension(Toolkit.getDefaultToolkit().getScreenSize().width-50, Toolkit.getDefaultToolkit().getScreenSize().height - 50));
		setLocationRelativeTo(this);
		this.addKeyListener(Presenter.instanceOf());
		this.setFocusable(true);
		setSettingsJframe();
		init(Presenter.instanceOf());
		
	}

	private void init(ActionListener controller) {
		setSettingsViewGame(); 
		this.setVisible(true);
	}

	public void stopSoundAnimate() {
		this.soundMove.stopSound();
	}

	public void playSoundMove(String path) {
		  this.soundMove = new ManagerSound(path); 
		  new Thread(new Runnable() {
		  @Override public void run() { 
			  soundMove.startSound(); 
			  } 
		  }).start();
		 
	}

	private void setSettingsJframe() {
		Presenter.instanceOf().addInstance(this);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setTitle(Constants.APP_NAME);
		setIconImage(new ImageIcon(this.getClass().getResource(Constants.PATH_ICON_FRAME)).getImage());
	}

	public void setSettingsJPanelMatchSaved() {
		panelBoard.setVisible(false);
		this.panelAdmin = new PanelAdmin();
		panelAdmin.updatePanelAdmin(Presenter.instanceOf().getMatchSaved());
		panelAdmin.setVisible(true);
		this.add(panelAdmin,BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
	public boolean isIndex() {
		return panelAdmin.isSelected();
	}
	public long getIdMatch(){
		return panelAdmin.getIdColum();
	}
	public void setSettingsViewGame() {
		playSoundMove(Constants.PATH_SOUND_BACK);
		panelAdmin.setVisible(false);
		this.panelBoard = new PanelBoard();
		Presenter.instanceOf().addInstance(panelBoard);
		this.add(panelBoard, BorderLayout.CENTER);
		panelBoard.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panelBoard.setVisible(true);
		this.add(panelBoard, BorderLayout.CENTER);
		this.revalidate();
		this.repaint();
	}
}
