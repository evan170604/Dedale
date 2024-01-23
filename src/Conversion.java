/* SAE 1.1  : Réaliser un développement d'applications
 * Equipe 5 : Even Fanch, Cnaepelnickx Evan, Wychowski Theo, Yachir Yanis
 * Date     : 19/12/2023
 * Exercice : 5
*/

public class Conversion
{
	public static int tab2Entier(boolean[] tab)
	{
		int res = 0;
		int temp;

		for (int cpt = 0; cpt < tab.length; cpt++)
		{
			if (tab[cpt] == true)
				temp = 1;
			else
				temp = 0;

			res += temp * Math.pow(2, cpt);
		}

		return res;
	}

	public static boolean[] entier2Tab(int valeur, int nbElt)
	{
		double valeurdouble;
		boolean tab2[];
		boolean tab[];

		tab = new boolean[nbElt];
		tab2 = new boolean[32];

		valeurdouble = valeur;
		for (int i = tab2.length - 1; i >= 0; i = i - 1)
		{
			if (valeurdouble >= Math.pow(2, i))
			{
				tab2[i] = true;
				valeurdouble = valeurdouble - Math.pow(2, i);
			}else { tab2[i] = false; }
		}

		for (int i = 0; i < nbElt; i++)
			tab[i] = tab2[i];

		if (valeur == -1)
			tab[6] = true;
		else
			tab[6] = false;
		return tab;
	}

	public static String enChaine(boolean[] tab)
	{
		// Variables
		String sRet = "";

		// Instructions

		for (int i = 0; i < tab.length; i++)
			sRet += String.format("%5s", "+-----");
		sRet += "+" + '\n';

		sRet += "|";
		for (int i = 0; i < tab.length; i++)
		{
			sRet += tab[i];
			if (tab[i] == true)
				sRet += " ";
			sRet += "|";
		}
		sRet += '\n';

		for (int i = 0; i < tab.length; i++)
			sRet += String.format("%5s", "+-----");
		sRet += "+";

		sRet += '\n';

		for (int i = 0; i < tab.length; i++)
			sRet += String.format("%5s", i) + " ";

		return sRet;
	}

	public static String grille(Dedale unDedale)
	{
		String str = "+";

		for (int j = 0; j < unDedale.getNbColonne(); j++)
			str += "----+";

		str += "\n|";

		for (int i = 0; i < unDedale.getNbLigne(); i++)
		{
			for (int j = 0; j < unDedale.getNbColonne(); j++) { str += String.format("%3s", unDedale.getPiece(i, j).getValOuvertures()) + " |"; }

			str += "\n+";

			for (int j = 0; j < unDedale.getNbColonne(); j++) { str += "----+"; }

			if (i != unDedale.getNbLigne() - 1)               { str +=   "\n|"; }
		}

		return str;
	}

	public static String detail(Dedale unDedale)
	{
		String str;

		str = "";

		for (int i = 0; i < unDedale.getNbLigne(); i++)
		{
			str += "== Ligne " + i
					+ " =======================================================================================================================================\n";

			for (int j = 0; j < unDedale.getNbColonne(); j++)
				str += unDedale.getPiece(i, j).toString() + "\n";
		}

		return str;
	}
}