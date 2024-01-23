/* SAE 1.1  : Réaliser un développement d'applications
 * Equipe 5 : Even Fanch, Cnaepelnickx Evan, Wychowski Theo, Yachir Yanis
 * Date     : 19/12/2023
 * Exercice : 8
*/

import ihmgui.FrameGrille;
import ihmgui.Controle;

public class Controleur extends Controle
{
	private Dedale metier;
	private FrameGrille frame;
	private String direc;
	private boolean enfer;

	public Controleur()
	{
		this.metier = new Dedale();
		this.frame = new FrameGrille(this);

		frame.setSize(1200, 1200);
		frame.setLocation(10, 10);
		frame.setTitle("Dedale");
		frame.setVisible(true);
		this.direc = "s";
	}

	public int setNbLigne   () { return   5; }
	public int setNbColonne () { return   5; }
	public int setLargeurImg() { return 100; }

	public String setFondGrille()
	{
		if (enfer ) {return "../images/enfer.jpg";}
		return "../images/fond.png"; }

	public String setImage(int ligne, int colonne, int couche)
	{
		String rep = "../images/";
		String sImage = null;
		String dw = "dw_";

		if (couche == 0)
		{
			if (enfer && metier.getPiece(ligne, colonne).getValOuvertures()==0){sImage = rep + "tetedemort.jpg";}
			else
				sImage = rep + "P" + String.format("%02d", metier.getPiece(ligne, colonne).getValOuvertures()) + ".png";
				
			if (metier.getPiece(ligne, colonne).getLave()) { sImage = rep + "lave.png"; }
			
		}

		if (couche == 1)
		{
			if (metier.getSymboleHero(ligne, colonne) == 's')
			{
				dw += this.direc;
				sImage = rep + dw + ".png";
			}else { couche = 2; }
		}

		if (couche == 2)
		{
			if (metier.getPiece(ligne, colonne).getDepart()) { return  "../images/depart.png"; }
			if (metier.getPiece(ligne, colonne).getArrive()) { return "../images/arrivee.png"; }
		}

		return sImage;
	}

	public boolean Tourner(char dir)
	{
		switch (dir)
		{
			case 'N':
				this.direc = "n";
				return true;
			case 'S':
				this.direc = "s";
				return true;
			case 'E':
				this.direc = "e";
				return true;
			case 'O':
				this.direc = "o";
				return true;
			default:
				return false;
		}
	}

	public void jouer(String touche)
	{
		if(enfer)
		{
			int w,x,y,z;

			w = (int) ( Math.random()*100 );
			x = (int) ( Math.random()* 10 );
			y = (int) ( Math.random()* 10 );
			z = (int) ( Math.random()* 10 );

			if( x == 5 )
			{
				for (int i=0; i<y;i++)
					metier.incrementNiveau();
					for (int j=0; j<z;j++)
						metier.decrementeNiveau();
			}

			if( w == 50 )
			{
				metier.resetAll();
			}
			//frame.setLocation((int)(Math.random()*300), (int)(Math.random()*300));
			//frame.setSize(1200+(int)((Math.random()*40)-20), 1200+(int)((Math.random()*40)-20));
		}
		switch (touche)
		{
			case "FL-H":
				if (enfer)
				{
					if (this.direc.equals("s"))
					metier.deplacer('S');
					else
						Tourner('S');
				}
				else
				{
					if (this.direc.equals("n"))
						metier.deplacer('N');
					else
						Tourner('N');
				}
				break;

			case "FL-B":
				if (enfer)
				{
					if (this.direc.equals("n"))
						metier.deplacer('N');
					else
						Tourner('N');
				}
				else 
				{
					if (this.direc.equals("s"))
						metier.deplacer('S');
					else
						Tourner('S');
				}
				break;

			case "FL-G":
				if (enfer)
				{
					if (this.direc.equals("e"))
						metier.deplacer('E');
					else
						Tourner('E');
				}
				else
				{
					if (this.direc.equals("o"))
						metier.deplacer('O');
					else
						Tourner('O');
				}
				
				break;

			case "FL-D":
				if(enfer)
				{
					if (this.direc.equals("o"))
						metier.deplacer('O');
					else
						Tourner('O');
				}
				else
				{
					if (this.direc.equals("e"))
						metier.deplacer('E');
					else
						Tourner('E');
				}
				break;
				
		}

		frame.majIHM();
	}

	public void glisser ( int ligne1, int colonne1, int ligne2,int colonne2)
	{
		this.metier.deplacerPiece(ligne1, colonne1, ligne2, colonne2);
		frame.majIHM();
	}

	public String setBouton(int numBtn)
	{
		String lib;

		switch ( numBtn )
		{
			case 0 : lib = "Niveau Précédent"; 			break;
			case 1 : lib = "Niveau Suivant  ";  	    break;
			case 2 : lib = "Reset Niveau ";  		    break;
			case 3 : lib = "Reset Jeu ";   		    	   break;
			case 4 : lib= "Enfer" 		;			 break;
			default: lib = null;                   // cette dernière ligne est importante, elle met fin à la contruction des boutons
		}

		return lib;
	}

	public void bouton(int numBtn)
	{
		if(numBtn == 0)
		{
			metier.decrementeNiveau();
			frame.majIHM();
		}

		if(numBtn == 1)
		{
			metier.incrementNiveau();
			frame.majIHM();
		}

		if(numBtn == 2)
		{
			metier.incrementNiveau();
			metier.decrementeNiveau();
			frame.majIHM();
		}

		if(numBtn == 3)
		{
			metier.resetAll();
			frame.majIHM();
		}

		if (numBtn == 4)
		{
			enfer=!enfer;
			frame.setVisible(false);
			this.frame = new FrameGrille(this);
			frame.setVisible(true);
			frame.setSize(1200, 1200);
		}

	}

	public String setLabel (int numLbl)
	{
		String lib;

		switch ( numLbl )
		{
			case 0 : lib = "essais";        break;
			case 1 : lib = "pieces";        break;
			case 2 : lib = "niveau : "; 	break;
			default: lib = null;					// cette dernière ligne est importante, elle met fin à la contruction des labels
		}

		return lib;
	}

	public String  setTextLabel  (int numLbl)
	{
		switch(numLbl)
		{
			case 0 : return metier.getMessage();
			case 1 :return metier.getMessage2();
			case 2 : return metier.getMessage3();
			default : return "";
		}
	}

	public static void main(String[] args) { new Controleur(); }
}