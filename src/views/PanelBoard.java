package views;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

import controllers.Presenter;
import models.Coord2D;

/**
 * Es el tablero de juego contenedor de los cuadros dinamicos
 * 
 * @author Yeison Rodriguez
 *
 */
public class PanelBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graphics graphics;
	private Timer timer;
	private boolean isRuning;
	private boolean notice;
	private MyButton[] buttons;
	private ImageIcon imageBack;
	private int heigthAva;
	private int widthAva;
	private int selector;
	private StateGame state;
	private ImageIcon btnNext;
	private ArrayList<ImageIcon>  images;
	private ImageIcon btnPrevius;
	private ImageIcon imgWinner;
	private ImageIcon imgLoser;

	public PanelBoard() {
		this.isRuning=false;
		this.state=StateGame.CAPTURES;
		this.selector=0;
		this.images=new ArrayList<>();
		init();
		Presenter.instanceOf().addInstance(this);
		tastTimer();
		Presenter.instanceOf().takeCaptures();
		this.addMouseListener(Presenter.instanceOf());
	}
	public void init() {
		this.imageBack=new ImageIcon(this.getClass().getResource(Constants.FILE_NAME_IMAGE_BACGROUND_PRINCIPAL));
		this.btnNext=new ImageIcon(this.getClass().getResource(Constants.PATH_ICON_NEXT));
		this.btnPrevius=new ImageIcon(this.getClass().getResource(Constants.PATH_ICON_PREVIUS));
		this.imgLoser=new ImageIcon(this.getClass().getResource(Constants.PATH_ICON_LOSER));
		this.imgWinner=new ImageIcon(this.getClass().getResource(Constants.PATH_ICON_WINNER));
		this.buttons=new MyButton[5];
	}
	public void changeVisibility() {
		if (this.isVisible()) {
			this.setVisible(false);
		} else {
			this.setVisible(true);
		}
	}
	public void  repaintPanel() {
		this.repaint();
	}
	public void setAction(boolean confirm) {
		this.notice=confirm;
	}
	public void buildNotice() {
			drawMenu();
			graphics.setFont(Constants.MESSAGE_FONT);
			graphics.setColor(Constants.COLOR_FONT);
			if(notice) {
				graphics.drawString(Constants.MESSAGE_WINNER, this.getSize().width/3, 50);
				graphics.drawImage(imgWinner.getImage(), this.getSize().width/3, this.getSize().height/4,  this);
			}else {
				graphics.drawImage(imgLoser.getImage(), this.getSize().width/3, this.getSize().height/4,  this);
				graphics.drawString(Constants.MESSAGE_LOSER, this.getSize().width/3, 50);
			}
	}
	public void tastTimer() {
		this.timer=new Timer(30, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				repaint();
				Toolkit.getDefaultToolkit().sync();
			}
		});	
	}
	public void startMirorRepaint(){
		this.timer.start();
	}
	@Override
	public void paint(Graphics graphics) {
		super.paint(graphics);
		this.graphics = graphics;
		this.heigthAva=(this.getSize().height-100)/13;
		this.widthAva=(this.getSize().width-400)/20;
		assignPanel();
	}
	public void drawPanelCaptures() {
		graphics.setColor(Constants.COLOR_BACK);
		graphics.fillRect(0, 0, this.getSize().width, this.getSize().height);
		graphics.setColor(Color.BLACK);
		graphics.setFont(Constants.ARMADILLO_FONT);
		graphics.drawString("Game Screenshots", this.getSize().width/3,20);
		drawButtons();
		drawCatures();
	}
	public void drawCatures() {
		if(this.images.size()==0){
			this.images=Presenter.instanceOf().getCaptures();
		}
		if(images.size()!=0) {
			graphics.setColor(Constants.COLOR_BACK);
			graphics.drawRoundRect(250,this.getSize().height/2,this.widthAva, this.heigthAva, 10, 10);
			graphics.drawImage(btnPrevius.getImage(),250 ,this.getSize().height/2,this.widthAva,this.heigthAva,this);
			
			graphics.drawRoundRect(this.getSize().width-50,this.getSize().height/2,this.widthAva, this.heigthAva, 10, 10);
			graphics.drawImage(btnNext.getImage(),this.getSize().width-50,this.getSize().height/2,this.widthAva,this.heigthAva,this);
			graphics.drawImage(images.get(selector).getImage(), 320, 50, this.getSize().width-400, this.getSize().height-100,this);
		}
	}
	public void imageChangeSelection(int option) {
		switch (option) {
		case 1:
			selector=selector-1>-1?selector-1:selector;
			break;
		case 2:
			selector=selector+1<images.size()?selector+1:selector;
			break;
		default:
			break;
		}
	}
	public void assignPanel() {
		switch (state) {
		case PANEL_GAME:
			isFull();
			Presenter.instanceOf().getSquares();
			break;
		case CAPTURES:
			drawPanelCaptures();
			break;
		case AVISO:
			buildNotice();
			break;
		default:
			break;
		}
		startMirorRepaint();
	}
	public void isFull() {
		if(!isRuning) {
			Presenter.instanceOf().fillMatrixInit();
			startMirorRepaint();
			isRuning=true;
		}
	}
	public StateGame getState() {
		return state;
	}
	public void drawSquare(Coord2D coord2d, ImageIcon image) {
		graphics.drawImage(image.getImage(),(int) coord2d.getX(), (int) coord2d.getY(),this.widthAva,this.heigthAva,this);
	}
	public void drawSquareSecondCap(Coord2D coord2d,ImageIcon image ,boolean isCamp) {
		graphics.drawImage(image.getImage(),(int) coord2d.getX(), (int) coord2d.getY(),this.widthAva,this.heigthAva,this);
	}
	
	public void drawMenu() {
		graphics.setColor(Constants.COLOR_BACK);
		graphics.fillRect(0, 0, this.getSize().width, this.getSize().height);
		graphics.setFont(Constants.ARMADILLO_FONT);
		graphics.drawImage(imageBack.getImage(),0 , 50,this.widthAva*8,this.heigthAva*13,this);
		if(!(state==StateGame.AVISO || state==StateGame.MATCHS_SAVES)) {
			drawMarkers();
		}
		drawButtons();
	}
	public void drawMarkers() {
		ImageIcon image= new ImageIcon(this.getClass().getResource(Constants.PATH_LIVE));
		for (int i = 0; i <Presenter.instanceOf().getLives() ; i++) {
			graphics.drawImage(image.getImage(), 800+(this.widthAva *i), 20,this.widthAva , this.heigthAva, this);
		}
	}
	public void setImagesCaptures(ArrayList<ImageIcon> images) {
		selector=selector<images.size()?selector:images.size()-1;
		this.images=images;
	}
	public void setState(StateGame state) {
		this.state=state;
	}
	public void drawButtons() {
		checkSelection();
		graphics.setColor(Color.GREEN);
		graphics.fillRoundRect(this.widthAva, this.heigthAva*5,this.widthAva*4, this.heigthAva, 10, 10);
		graphics.setColor(Color.BLACK);
		graphics.drawString("Reiniciar",this.widthAva+10, this.heigthAva*5+(heigthAva/2));
		graphics.setColor(Color.BLUE);
		graphics.fillRoundRect(this.widthAva, this.heigthAva*7,this.widthAva*4, this.heigthAva, 10, 10);
		graphics.setColor(Color.BLACK);
		graphics.drawString("Continuar",this.widthAva+10, this.heigthAva*7+(heigthAva/2));
		graphics.setColor(Color.ORANGE);
		graphics.fillRoundRect(this.widthAva, this.heigthAva*9,this.widthAva*4, this.heigthAva, 10, 10);
		graphics.setColor(Color.BLACK);
		graphics.drawString("Screenshots",this.widthAva+10, this.heigthAva*9+(heigthAva/2));
	}
	public void checkSelection() {
		if(buttons[0]==null) {
			this.buttons[0]=new MyButton(this.widthAva, this.heigthAva*5,this.widthAva*4, this.heigthAva);
			this.buttons[1]=new MyButton(this.widthAva, this.heigthAva*7,this.widthAva*4, this.heigthAva);
			this.buttons[2]=new MyButton(this.widthAva, this.heigthAva*9,this.widthAva*4, this.heigthAva);
			this.buttons[3]=new MyButton(250 ,this.getSize().height/2,this.widthAva,this.heigthAva);
			this.buttons[4]=new MyButton(this.getSize().width-50,this.getSize().height/2,this.widthAva,this.heigthAva);
		}
	}
	public boolean isPlaying() {
		return (isVisible()&&this.state==StateGame.PANEL_GAME)?true:false;
	}
	public void calculateOption(int x, int y) {
		for (int i = 0; i < buttons.length; i++) {
			if(calculateColision(i,x,y)) {
				Presenter.instanceOf().TakeAction(i);
			}
		}
	}
	public boolean calculateColision(int indice,int x, int y) {
		if(buttons[indice].getX()<x &&x<buttons[indice].getX()+buttons[indice].getWidth()) {
			if(buttons[indice].getY()<y &&y<buttons[indice].getY()+buttons[indice].getHeigth()) {
				return true;
			}
		}
		return false;
	}
	class MyButton {
		private int x;
		private int y;
		private int width;
		private int heigth;
		public MyButton(int x,int y,int width,int heigth) {
			this.x=x;
			this.y=y;
			this.width=width;
			this.heigth=heigth;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public int getWidth() {
			return width;
		}
		public int getHeigth() {
			return heigth;
		}
		
	}
}
