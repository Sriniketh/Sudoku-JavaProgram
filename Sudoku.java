import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.util.*;

/*
<applet code = "Sudoku" width = "1200" height = "600">
</applet>
*/

class Checker
{
 public static int[][] question= new int[9][9];
 public static int[][] solution= new int[9][9];
 public static int level=1;
 public boolean horiz(int x,int y,int num)
 {
  boolean check=true;
  for(int i=0;i<9;i++)
   if(question[x][i]== num && i!=y)
    check=false;
	
  return check;
 }
 public boolean vert(int x,int y,int num)
 {
  boolean check=true;
  for(int i=0;i<9;i++)
   if(question[i][y]== num && i!=x)
    check=false;
	
  return check;
 }	  
 public boolean box(int x,int y,int num)
 {
  boolean check=true;
  int tempx,tempy;
  tempx=x/3;
  tempy=y/3;
  tempx=tempx*3;
  tempy=tempy*3;
  for(int i=tempx;i<tempx+3;i++)
   for(int j=tempy;j<tempy+3;j++)
    if(question[i][j]== num && (i!=x && j!=y))
	 check=false;
   
  return check;
 }
 public boolean solve(int i, int j) 
 {
  if (i == 9) 
  {
   i = 0;
   j++;
   if (j == 9)
    return true;
  }
  if (solution[i][j] != 0)
   return solve(i+1,j);
  for (int val = 1; val <= 9; val++) 
  {
   if (legal(i,j,val)) 
   {
    solution[i][j] = val;
    if (solve(i+1,j))
     return true;
   }
  }
  solution[i][j] = 0;
  return false;
 }
 public boolean legal(int i, int j, int num) 
 {
  for (int k = 0; k < 9; k++) 
   if (num == solution[k][j])
    return false;

  for (int k = 0; k < 9; k++)
   if (num == solution[i][k])
    return false;

  int boxRowOffset = (i / 3)*3;
  int boxColOffset = (j / 3)*3;
  for (int k = 0; k < 3; k++)
   for (int m = 0; m < 3; m++)
    if (num == solution[boxRowOffset+k][boxColOffset+m])
      return false;

  return true;
 }
}

class Sudoku {
public static void main(String[] args) {
	new SudokuFrame();
}
}


class SudokuFrame extends Frame implements ActionListener, MouseListener, KeyListener, ItemListener, WindowListener{

	private JPanel[][] subPanel = new JPanel[9][9];
	private Button newGame, check, hint;
	private Label[][] subPanelLabel = new Label[9][9];
	private int keyFlag = 0;
	private int selectedX, selectedY;
	private int[][] question = new int[9][9];
	//private int[][] answer = new int[9][9];
	private int[][] user = new int[9][9];
	private CheckboxMenuItem easy, medium, hard, evil;
	Checker c = new Checker();
	private int incompleteFlag = 0;	
	private	int wrongFlag = 0;
	private JFrame result = new JFrame("Result");
	private int gameEndFlag = 0;
	
	public SudokuFrame () {
	
		super("Sudoku - Konichiwa");
		
		addMouseListener(this);
		addKeyListener(this);
		
		MenuBar mb = new MenuBar();
		Menu options = new Menu("Options");
		Menu difficulty = new Menu("Difficulty");
		easy = new CheckboxMenuItem("Easy", true);
		medium = new CheckboxMenuItem("Medium");
		hard = new CheckboxMenuItem("Hard");
		evil = new CheckboxMenuItem("Evil");
		setMenuBar(mb);
		difficulty.add(easy);
		difficulty.add(medium);
		difficulty.add(hard);
		difficulty.add(evil);
		options.add(difficulty);
		easy.addItemListener(this);
		medium.addItemListener(this);
		hard.addItemListener(this);
		evil.addItemListener(this);
		Menu file = new Menu("File");
		MenuItem about = new MenuItem("About");
		file.add(about);
		about.addActionListener(this);
		MenuItem exit = new MenuItem("Exit");
		exit.addActionListener(this);
		file.add(exit);
		mb.add(file);
		mb.add(options);
		
		GridBagLayout gridBag = new GridBagLayout();
		GridBagConstraints c = new GridBagConstraints();
		
		setLayout(gridBag);
		
		c.weightx = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.NORTH;
				
		JPanel mainPanel = new JPanel(new GridLayout(3, 3, 10, 10));
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
				
		gridBag.setConstraints(mainPanel,c);
		
		JPanel one = new JPanel(new GridLayout(3, 3, 5, 5));
		one.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel two = new JPanel(new GridLayout(3, 3, 5, 5));
		two.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel three = new JPanel(new GridLayout(3, 3, 5, 5));
		three.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel four = new JPanel(new GridLayout(3, 3, 5, 5));
		four.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel five = new JPanel(new GridLayout(3, 3, 5, 5));
		five.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel six = new JPanel(new GridLayout(3, 3, 5, 5));
		six.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel seven = new JPanel(new GridLayout(3, 3, 5, 5));
		seven.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel eight = new JPanel(new GridLayout(3, 3, 5, 5));
		eight.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JPanel nine = new JPanel(new GridLayout(3, 3, 5, 5));
		nine.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		
		for(int i=0; i<9 ; ++i){
			for(int j=0; j<9; ++j){
				subPanel[i][j] = createSquareJPanel(Color.white, 50);
			}
		}
		
		for(int i=0; i<3 ; ++i){
			for(int j=0; j<3; ++j){
				one.add(subPanel[i][j]);
			}
		}
		
		for(int i=0; i<3 ; ++i){
			for(int j=3; j<6; ++j){
				two.add(subPanel[i][j]);
			}
		}
		
		for(int i=0; i<3 ; ++i){
			for(int j=6; j<9; ++j){
				three.add(subPanel[i][j]);
			}
		}
		
		for(int i=3; i<6 ; ++i){
			for(int j=0; j<3; ++j){
				four.add(subPanel[i][j]);
			}
		}
		
		for(int i=3; i<6 ; ++i){
			for(int j=3; j<6; ++j){
				five.add(subPanel[i][j]);
			}
		}
		
		for(int i=3; i<6 ; ++i){
			for(int j=6; j<9; ++j){
				six.add(subPanel[i][j]);
			}
		}
		
		for(int i=6; i<9 ; ++i){
			for(int j=0; j<3; ++j){
				seven.add(subPanel[i][j]);
			}
		}
		
		for(int i=6; i<9 ; ++i){
			for(int j=3; j<6; ++j){
				eight.add(subPanel[i][j]);
			}
		}
		
		for(int i=6; i<9 ; ++i){
			for(int j=6; j<9; ++j){
				nine.add(subPanel[i][j]);
			}
		}
						
		mainPanel.add(one);
		mainPanel.add(two);
		mainPanel.add(three);
		mainPanel.add(four);
		mainPanel.add(five);
		mainPanel.add(six);	
		mainPanel.add(seven);
		mainPanel.add(eight);
		mainPanel.add(nine);
		
		mainPanel.setOpaque(true);
		add(mainPanel);
		
		c.weightx = 0.3;
		c.weighty = 1.0;
		c.insets = new Insets(25,425,50,50);
		c.ipadx = 10;
		c.gridwidth = 3;
		c.ipady = 10;
		Font f = new Font("TimesRoman", Font.PLAIN, 18);
		newGame = new Button("New Game");
		setFont(f);
		gridBag.setConstraints(newGame,c);
		add(newGame);
		newGame.addActionListener(this);
		c.gridwidth = GridBagConstraints.RELATIVE;
		c.insets = new Insets(25,-125,50,50);
		hint = new Button("Hint");
		gridBag.setConstraints(hint,c);
		add(hint);
		hint.addActionListener(this);
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.insets = new Insets(25,-125,50,450);
		check = new Button("Check");
		gridBag.setConstraints(check,c);
		add(check);
		check.addActionListener(this);
		
		for(int i=0; i<9 ; ++i){
			for(int j=0; j<9; ++j){
				subPanelLabel[i][j] = new Label("", Label.CENTER);
				subPanelLabel[i][j].setFont(new Font("TimesRoman", Font.PLAIN, 30));
				subPanel[i][j].add(subPanelLabel[i][j]);
				subPanel[i][j].addMouseListener(this);
				subPanelLabel[i][j].addMouseListener(this);
				subPanelLabel[i][j].addKeyListener(this);
			}
		}
		setSize(1200,600);
		setVisible(true);
		addWindowListener(this);
		
		newGameFunction();
		
	}
	
	private JPanel createSquareJPanel(Color color, int size)
    {
        JPanel tempPanel = new JPanel();
        tempPanel.setBackground(color);
        tempPanel.setMinimumSize(new Dimension(size-10, size-10));
        tempPanel.setMaximumSize(new Dimension(size+20, size+20));
        tempPanel.setPreferredSize(new Dimension(size, size));
        return tempPanel;
    }

	public void actionPerformed(ActionEvent ae){
		String but = ae.getActionCommand();
		if(but.equals("New Game")){
			newGameFunction();
		}
		else if(but.equals("Check")){
			checkFunction();
		}
		else if(but.equals("About")){
			JOptionPane.showMessageDialog(result,"Sudoku v1.0\n\nDeveloped By\n\nP.V.Nikilav\nPraneeth Ramesh\nR.Sriniketh\nR.Srivathsan");
		}
		else if(but.equals("Exit")){
			closeAll();
		}
		else if(but.equals("Hint")){
			hintFunction();
		}
		//System.out.println(ae.getSource() + "");	
	}
	
	public void itemStateChanged(ItemEvent ie){
		//System.out.println(ie.getItem() + "");
		if(ie.getItem().equals("Easy")){
			easy.setState(true);
			medium.setState(false);
			hard.setState(false);
			evil.setState(false);
			c.level = 1;
			System.out.println("easy");
		}
		else if(ie.getItem().equals("Medium")){
			easy.setState(false);
			medium.setState(true);
			hard.setState(false);
			evil.setState(false);
			c.level = 2;
			System.out.println("medium");
		}
		else if(ie.getItem().equals("Hard")){
			hard.setState(true);
			medium.setState(false);
			easy.setState(false);
			evil.setState(false);
			c.level = 3;
			System.out.println("hard");
		}
		else if(ie.getItem().equals("Evil")){
			medium.setState(false);
			hard.setState(false);
			easy.setState(false);
			evil.setState(true);
			c.level = 4;
			System.out.println("evil");
		}
	}
	
	public void mouseClicked(MouseEvent me){
		
		keyFlag = 0;
		for(int i = 0; i<9; ++i){
			for(int j=0 ; j<9; ++j){
				subPanelLabel[i][j].setBackground(Color.WHITE);
				subPanel[i][j].setBackground(Color.WHITE);
			}
		}
		for(int i = 0; i<9; ++i){
			for(int j=0 ; j<9; ++j){
				if((me.getSource().equals(subPanelLabel[i][j]) || me.getSource().equals(subPanel[i][j])) && question[i][j]==0){
					keyFlag = 1;
					subPanel[i][j].setBackground(Color.LIGHT_GRAY);
					subPanelLabel[i][j].setBackground(Color.LIGHT_GRAY);
					selectedX = i;
					selectedY = j;
					//System.out.println(i+","+j);
					setFocusable(true);
					requestFocusInWindow();
				}
			}
		}
	}
	public void mouseEntered(MouseEvent me){}
	public void mouseExited(MouseEvent me){}
	public void mousePressed(MouseEvent me){}
	public void mouseReleased(MouseEvent me){}
	
	public void keyPressed(KeyEvent ke){}
	public void keyReleased(KeyEvent ke){}
	public void keyTyped(KeyEvent ke){
		if(keyFlag == 1){
			subPanelLabel[selectedX][selectedY].setFont(new Font("TimesRoman", Font.PLAIN, 30));
			char c = ke.getKeyChar();
			int temp = (int) c;
			if(temp>48 && temp<=57) subPanelLabel[selectedX][selectedY].setText(String.valueOf(temp-48));
			else if(temp == 8 || temp == 127) subPanelLabel[selectedX][selectedY].setText("");
			//answer[selectedX][selectedY] = Integer.parseInt(subPanelLabel[selectedX][selectedY].getText());
		}
	}
	
	public void newGameFunction(){
		
		int max_numbers=0;
		gameEndFlag = 0;
		int randomx,randomy,random_num;
		boolean cont=true;
		while(cont){
			for(int i=0;i<9;i++)
				for(int j=0;j<9;j++)
					c.question[i][j]=0;
			if(c.level==1)
				max_numbers=33;
			else if(c.level==2)
				max_numbers=28;
			else if(c.level==3)
				max_numbers=23;
			else if(c.level==4)
				max_numbers=18;
   	
			while(max_numbers !=0){
				randomx= new Random().nextInt(9);
				randomy= new Random().nextInt(9);
				random_num= new Random().nextInt(9)+1; 
				boolean zero=true;
				if(c.question[randomx][randomy]!=0)
					zero=false;
				if(zero && c.horiz(randomx,randomy,random_num) && c.vert(randomx,randomy,random_num) && c.box(randomx,randomy,random_num)){
					c.question[randomx][randomy]=random_num;
					max_numbers--;
				}
			}
			/*for(int i=0;i<9;i++)
			{
				for(int j=0;j<9;j++)
				{
					System.out.print(c.question[i][j]+" ");
				}
				System.out.println(""); 
			}
			System.out.println("");*/
			for(int i=0;i<9;i++){
				for(int j=0;j<9;j++){
					c.solution[i][j]=c.question[i][j];
					question[i][j] = c.question[i][j];
				}
			}
			if(c.solve(0,0))
				cont=false;
		}	
		for(int i=0;i<9;i++){
			for(int j=0;j<9;j++){
				System.out.print(c.solution[i][j]+" ");
			}
			System.out.println(""); 
		}
		
		String temp;  
		for(int i =0 ; i<9 ; ++i){
			for(int j=0; j<9 ; ++j){
				subPanelLabel[i][j].setFont(new Font("TimesRoman", Font.BOLD, 30));
				if(question[i][j] == 0){
					subPanelLabel[i][j].setText("");
				}
				else{
					temp = String.valueOf(question[i][j]);
					subPanelLabel[i][j].setText(temp);
				}
			}
		}
	}
	
	public void checkFunction(){
		
		for(int i=0; i<9 ; ++i){
			for(int j=0 ; j<9 ; ++j){
				if(subPanelLabel[i][j].getText() == "") incompleteFlag = 1;	
			}
		}
		if(incompleteFlag == 0){
			for(int i=0; i<9 ; ++i){
				for(int j=0 ; j<9 ; ++j){
					user[i][j] = Integer.parseInt(subPanelLabel[i][j].getText());
					if(user[i][j] != c.solution[i][j]) wrongFlag = 1;
				}
			}
		}
		
		if(incompleteFlag == 1){
			JOptionPane.showMessageDialog(result,"Sudoku is Incomplete");
			System.out.println("Incomplete");
		}
		else if(wrongFlag == 1){
			JOptionPane.showMessageDialog(result,"Sudoku is Wrong");
			System.out.println("Wrong");
		}
		else{
			JOptionPane.showMessageDialog(result,"Sudoku is Correct!");
			gameEndFlag = 1;
			System.out.println("Correct");
		}
		
		wrongFlag = 0;
		incompleteFlag = 0;
		
		if(gameEndFlag == 1)newGameFunction();
		
	}
	
	public void windowActivated(WindowEvent we){}
	public void windowClosed(WindowEvent we){}
	public void windowClosing(WindowEvent we){
		closeAll();
	}
	public void windowDeactivated(WindowEvent we){}
	public void windowDeiconified(WindowEvent we){}
	public void windowIconified(WindowEvent we){}
	public void windowOpened(WindowEvent we){}
	public void closeAll(){
		setVisible(false);
		dispose();
		result.dispose();
	}
	public void hintFunction(){
		boolean notempty=true;
		while(notempty){
			int randx= new Random().nextInt(9);
			int randy= new Random().nextInt(9);
  
			if(subPanelLabel[randx][randy].getText().equals(""))
			{
				Font f = new Font("TimesRoman", Font.BOLD, 30);
				subPanelLabel[randx][randy].setFont(f);
				subPanelLabel[randx][randy].setText(String.valueOf(c.solution[randx][randy]));
				question[randx][randy]=c.solution[randx][randy];
				notempty=false;
			}
  
			else
			{
				boolean full=true;
				for(int i=0;i<9;i++)
				{
					for(int j=0;j<9;j++)
					{
						if(subPanelLabel[i][j].getText().equals(""))
						{
							full=false;
							break;
						}
					}
				}
				if(full)
				{
					notempty=false;
				}   
			}		 
		}
	}
}
		