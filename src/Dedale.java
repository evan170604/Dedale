/* SAE 1.1  : Réaliser un développement d'applications
 * Equipe 5 : Even Fanch, Cnaepelnickx Evan, Wychowski Theo, Yachir Yanis
 * Date     : 19/12/2023
 * Exercice : 9
*/

import java.io.FileInputStream;
import java.util.Scanner;
import iut.algo.*;

public class Dedale
{
	private Piece[][] tabPiece;
	private Piece pieceHeros;
	private int nbdeplacements, nbpiece; 
	private int cptNiveau;
	private String strNiveau;

	public Dedale()
	{
		this.nbdeplacements=0;
		this.nbpiece=0;
		this.cptNiveau=1;
		this.strNiveau="01";
		this.tabPiece = this.initPiece();

		for (int i=0; i< tabPiece.length; i++)
			for (int j=0; j< tabPiece[0].length; j++)
				if (tabPiece[i][j].getDepart())
					this.pieceHeros = tabPiece[i][j];
	}

	private Piece getPieceAdj(int lig, int col, char dir)
	{
		switch (dir)
		{
			case 'N':
				if (lig > 0) { return      getPiece(lig - 1, col); }
				else         { return new Piece(0, ""); }

			case 'O':
				if (col > 0) { return      getPiece(lig, col - 1); }
				else         { return new Piece(0, ""); }

			case 'S':
				if (lig < getNbLigne() - 1) { return      getPiece(lig + 1, col); }
				else                        { return new Piece(0, ""); }

			case 'E':
				if (col < getNbColonne() - 1) { return      getPiece(lig, col + 1); }
				else                          { return new Piece(0, ""); }

			default:
				return null;
		}
	}

	public boolean estValide()
	{

		String[] dir = { "Nord", "Ouest", "Sud", "Est" };
		int cpt1 = 0;
		int cpt2 = 0;
		int cpt3 = 0;
		char invDir = 'X';

		while (cpt1 < getNbLigne())
		{
			cpt2 = 0;
			while (cpt2 < getNbColonne())
			{

				cpt3 = 0;
				while (cpt3 < dir.length)
				{
					if (dir[cpt3].charAt(0) == 'N') { invDir = 'S'; }
					if (dir[cpt3].charAt(0) == 'S') { invDir = 'N'; }
					if (dir[cpt3].charAt(0) == 'O') { invDir = 'E'; }
					if (dir[cpt3].charAt(0) == 'E') { invDir = 'O'; }

					if (getPiece(cpt1, cpt2).getOuverture(dir[cpt3].charAt(0)) != getPieceAdj(cpt1, cpt2, dir[cpt3].charAt(0)).getOuverture(invDir) )
								if(!getPieceAdj(cpt1, cpt2, dir[cpt3].charAt(0)).getLave() && !getPiece(cpt1, cpt2).getLave())
									return false;

					cpt3++;
				}
				cpt2++;
			}
			cpt1++;
		}
		return true;
	}

	public String validite()
	{
		String[] dir = { "Nord", "Ouest", "Sud", "Est" };
		int cpt1 = 0;
		int cpt2 = 0;
		int cpt3 = 0;
		char invDir = 'X';
		String str1, str2;

		str1 = "";
		str2 = "";

		while (cpt1 < getNbLigne())
		{
			cpt2 = 0;
			while (cpt2 < getNbColonne())
			{
				str1 += "Piece [" + cpt1 + "] [" + cpt2 + "]   " + getPiece(cpt1, cpt2).getValOuvertures() + "		";
				cpt3 = 0;
				while (cpt3 < dir.length) {
					if (dir[cpt3].charAt(0) == 'N') { invDir = 'S'; }
					if (dir[cpt3].charAt(0) == 'S') { invDir = 'N'; }
					if (dir[cpt3].charAt(0) == 'O') { invDir = 'E'; }
					if (dir[cpt3].charAt(0) == 'E') { invDir = 'O'; }

					if (getPiece(cpt1, cpt2).getOuverture(dir[cpt3].charAt(0)) != getPieceAdj(cpt1, cpt2, dir[cpt3].charAt(0)).getOuverture(invDir))
						str2 += "\n \t pb avec piece située au " + dir[cpt3] + "\n";

					cpt3++;
				}

				if (str2 == "")
					str2 = "OK\n";

				str1 += str2;

				str2 = "";
				cpt2++;
			}

			str1 += "\n\n";

			cpt1++;
		}

		return str1;
	}

	public int getNbLigne  () { return tabPiece   .length; }
	public int getNbColonne() { return tabPiece[0].length; }

	public Piece getPiece(int lig, int col) { return tabPiece[lig][col]; }

	private Piece[][] initPiece()
	{
		Scanner		scFic;
		Piece[][] tabPiece;
		Decomposeur dec;
		int cpt;

		tabPiece = new Piece[5][5];

		try
		{
			scFic = new Scanner(new FileInputStream("../niveau/niveau_" + this.strNiveau + ".data"),"UTF8");
			scFic.nextLine();
			while (scFic.hasNextLine())
			{
				for(int i=0; i<5; i++)
				{
					cpt = 0;
					dec = new Decomposeur(scFic.nextLine());
						for(int j=0; j<5; j++)
						{
							tabPiece[i][j] = new Piece(dec.getInt(cpt), "");
							cpt++;
						}
				}
			}
			scFic.close();

		} 
		catch (Exception e) { e.printStackTrace(); }

		return tabPiece;
	}

 	public void incrementNiveau()
	{
		this.cptNiveau++;
		if(this.cptNiveau <= 16)
		{
			if(this.cptNiveau <= 9)
			{
				this.strNiveau = "0" + this.cptNiveau;
			}
			else
				this.strNiveau = ""+this.cptNiveau;

			this.tabPiece = this.initPiece();
			for (int i=0; i< tabPiece.length; i++)
				for (int j=0; j< tabPiece[0].length; j++)
					if (tabPiece[i][j].getDepart())
						this.pieceHeros = tabPiece[i][j];
		}
		else
			this.cptNiveau--;
	}

	public void decrementeNiveau()
	{
		this.cptNiveau--;
		if(cptNiveau > 0)
		{
			if(cptNiveau <= 9)
				this.strNiveau = "0" + this.cptNiveau;
			else
				this.strNiveau = ""+cptNiveau;

			this.tabPiece = this.initPiece();

			for (int i=0; i< tabPiece.length; i++)
				for (int j=0; j< tabPiece[0].length; j++)
					if (tabPiece[i][j].getDepart())
						this.pieceHeros = tabPiece[i][j];
		}
		else
			this.cptNiveau++;
	}

	public void resetAll()
	{
		this.cptNiveau = 0;
		this.strNiveau = "01";
		this.nbdeplacements = 0;
		this.nbpiece = 0;
		this.tabPiece = this.initPiece();

		for (int i=0; i< tabPiece.length; i++)
			for (int j=0; j< tabPiece[0].length; j++)
				if (tabPiece[i][j].getDepart())
					this.pieceHeros = tabPiece[i][j];
	}


	public char getSymboleHero(int lig, int col)
	{
		if (pieceHeros == getPiece(lig, col)) { return 's'; }
		return ' ';
	}

	private Position rechercherPosition(Piece p)
	{
		Position pos = new Position(0, 0);

		for (int i = 0; i < 5; i++)
			for (int j = 0; j < 5; j++)
				if (getSymboleHero(i, j) == 's')
					pos = new Position(i, j);

		return pos;
	}

	public void deplacer(char dir)
	{
		int lig, col;
		char invDir=' ';
		if (dir == 'N') { invDir = 'S'; }
		if (dir == 'S') { invDir = 'N'; }
		if (dir == 'O') { invDir = 'E'; }
		if (dir == 'E') { invDir = 'O'; }

		lig = rechercherPosition(pieceHeros).getLig();
		col = rechercherPosition(pieceHeros).getCol();

		if (!getPieceAdj(lig, col, dir).getLave() && getPiece(lig, col).getOuverture(dir)==getPieceAdj(lig, col, dir).getOuverture(invDir) )
		{
			switch (dir)
			{
				case 'N':
					if (pieceHeros.getOuverture('N'))
					{
						this.pieceHeros = tabPiece[lig - 1][col];
						this.nbdeplacements++ ;
						fin();
					}
				break;

				case 'O':
					if (pieceHeros.getOuverture('O'))
					{
						this.pieceHeros = tabPiece[lig][col - 1];
						this.nbdeplacements++ ; 
						fin();
					}
				break;

				case 'S':
					if (pieceHeros.getOuverture('S'))
					{
						this.pieceHeros = tabPiece[lig + 1][col];
						this.nbdeplacements++;
						fin();
					}
				break;

				case 'E':
					if (pieceHeros.getOuverture('E'))
					{
						this.pieceHeros = tabPiece[lig][col + 1]; 
						this.nbdeplacements++;
						fin();
					}
				break;
			}
		}
	}
	public boolean deplacerPiece ( int ligne1, int colonne1, int ligne2, int colonne2 )
	{
		Piece temp;

		temp=tabPiece[ligne1][colonne1];

		tabPiece[ligne1][colonne1]=tabPiece[ligne2][colonne2];
		tabPiece[ligne2][colonne2]=temp;
		
		if (rechercherPosition(pieceHeros).getLig() == ligne1 && rechercherPosition(pieceHeros).getCol() == colonne1 || rechercherPosition(pieceHeros).getLig() == ligne2 && rechercherPosition(pieceHeros).getCol() == colonne2)
		{
			temp=tabPiece[ligne1][colonne1];//pas de héros

			tabPiece[ligne1][colonne1]=tabPiece[ligne2][colonne2];
			tabPiece[ligne2][colonne2]=temp;
			System.out.println("Deplacement non valide");

			return false;
		}

		if (!(ligne1-1 == ligne2 && colonne1 == colonne2 || ligne1 == ligne2 && colonne1-1 == colonne2 || ligne1 == ligne2 && colonne1 + 1 == colonne2 || ligne1+1 == ligne2 && colonne1 == colonne2))
		{
			temp=tabPiece[ligne1][colonne1];//pas de diagonale

			tabPiece[ligne1][colonne1]=tabPiece[ligne2][colonne2];
			tabPiece[ligne2][colonne2]=temp;
			System.out.println("Deplacement non valide");

			return false;
		}

		if (!(getPiece(ligne1, colonne1).getLave() || getPiece(ligne2, colonne2).getLave()))//on deplace que la lave
		{
			temp=tabPiece[ligne1][colonne1];

			tabPiece[ligne1][colonne1]=tabPiece[ligne2][colonne2];
			tabPiece[ligne2][colonne2]=temp;
			System.out.println("Deplacement non valide");

			return false;
		}

		if ( getPiece(ligne1, colonne1).getValOuvertures()==0 && !getPiece(ligne1, colonne1).getLave()||getPiece(ligne2, colonne2).getValOuvertures()==0 && !getPiece(ligne2, colonne2).getLave())
		{
			temp=tabPiece[ligne1][colonne1];

			tabPiece[ligne1][colonne1]=tabPiece[ligne2][colonne2];
			tabPiece[ligne2][colonne2]=temp;
			System.out.println("Deplacement non valide");

			return false;
		}

		if (getPiece(ligne2,colonne2).getArrive() || getPiece(ligne1,colonne1).getArrive())
		{
			temp=tabPiece[ligne1][colonne1];

			tabPiece[ligne1][colonne1]=tabPiece[ligne2][colonne2];
			tabPiece[ligne2][colonne2]=temp;
			System.out.println("Deplacement non valide");

			return false;
		}

		this.nbpiece++ ;
		return estValide();
	}

	public String getMessage ()
	{ 
		String message;

		message=""+this.nbdeplacements;
		return message;
	}

	public String getMessage2 ()
	{ 
		String message;

		message=""+this.nbpiece;
		return message;
	}

	public String getMessage3 ()
	{ 
		String message;

		message=""+this.strNiveau+"/16";
		return message;
	}

	private void fin ()
	{
		int lig, col;

		lig = rechercherPosition(pieceHeros).getLig();
		col = rechercherPosition(pieceHeros).getCol();
		if (getPiece(lig, col).getArrive())
		{
			try
			{
				Thread.sleep(200);
				incrementNiveau();
			}
			catch (InterruptedException e) { e.printStackTrace(); }
		}
	}
}