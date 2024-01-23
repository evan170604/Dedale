/* SAE 1.1  : Réaliser un développement d'applications
 * Equipe 5 : Even Fanch, Cnaepelnickx Evan, Wychowski Theo, Yachir Yanis
 * Date     : 19/12/2023
 * Exercice : 5
*/

public class Piece
{
	private String[] tabDir = { "Nord", "Ouest", "Sud", "Est" };
	private String nom;
	private boolean[] informations;

	public Piece(int valeur, String nom)
	{
		this.nom = nom;
		this.informations = Conversion.entier2Tab(valeur, 7);
	}

	public Piece(int valeur)
	{
		this.informations = Conversion.entier2Tab(valeur, 7);
		this.nom = "";
	}

	public String toString()
	{
		String sRet = "";

		sRet += String.format("%2d", Conversion.tab2Entier(this.informations)) + " (" + this.nom + ")";

		if (this.getDepart())
			sRet += "(Départ)";

		if (this.getArrive())
			sRet += "(Arrivé)";

		sRet += "    ==> Nord("
				+ indiceDir('N')
				+ ")   :" + String.format("%-5s", getOuverture('N')) + "   Ouest(" + indiceDir('O') + ")   :"
				+ String.format("%-5s", getOuverture('O')) +
				"   Sud(" + indiceDir('S') + ")   :" + String.format("%-5s", getOuverture('S')) + "   Est("
				+ indiceDir('E') + ")   :"
				+ getOuverture('E');

		return sRet;
	}

	public int getValOuvertures()
	{
		boolean[] tab = new boolean[4];

		for (int i = 0; i < 4; i++)
			tab[i] = informations[i];

		return Conversion.tab2Entier(tab);
	}

	public boolean getOuverture(char dir) { return informations[indiceDir(dir)]; }

	private int indiceDir(char caractere)
	{
		for (int i = 0; i < this.tabDir.length; i++)
			if (this.tabDir[i].charAt(0) == caractere)
				return i;

		return -1;
	}

	public boolean getDepart() { return this.informations[4]; }
	public boolean getArrive() { return this.informations[5]; }
	public boolean getLave  () { return this.informations[6]; }
}