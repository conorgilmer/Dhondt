
import java.text.DecimalFormat;

public class dhondt {


//get the highest value in the 2D table and remove it!
//returning the row=(party) to which it belongs
	public static int getMaxElement(double [][]dTable, int pN, int pM)
	{
	double maxValue = dTable[0][0];
 	int maxElement = 0;
	int i =0, j =0;
	for (int sn = 0; sn < pN; sn++) {
		for (int sm = 0; sm < pM; sm++) {
		if(dTable[sm][sn] > maxValue){
			maxValue = dTable[sm][sn];
			i =sm;
			j = sn;
			}
		  }
		}
	dTable[i][j] = 0.0; // zero the highest element for next run
	return j;
	}

// Build a d'Hondt Table
    public static void DrawDhondtTable(double [][]dTable ,int pN, int pM) {
		String row = "";
		//DecimalFormat df = new DecimalFormat("##.##");		
		DecimalFormat df = new DecimalFormat();		
		df.setMinimumFractionDigits(2);
		df.setMaximumFractionDigits(2);
		for (int n = 0; n < pN; n++) {
			for (int m = 0; m < pM; m++) {
//				row = row + Math.round(dTable[m][n]) +", ";
				row = row + df.format(dTable[m][n]) + ", ";
			}
			row = row + "\n";
		}

		System.out.println(row);
	}

    public static void main(String[] args) {

        System.out.println("Hello, D'Hondt");
	int chairs =11;
	double []votes1997 =  new double[]  {28, 20, 24, 18, 6, 5,3,2,2};
	String[] party1997 = {"UUP","DUP","SDLP", "SF","Alliance", "IndU", "UUUC", "WC", "PUP"};
	double []votes =  new double[]  {16, 38, 14, 29, 8, 1, 1, 1};
//	double[] votes =  new double[]{12.9, 29.3, 13.9, 26.3, 7.7, 2.4, 0.9, 6.6};
	String[] party = {"UUP","DUP","SDLP", "SF","Alliance", "IndU", "Green", "Ind"};

	int [] allocated = new int[votes.length];
	double [][] dhondtTable = new double [chairs][votes.length];
	System.out.println("Dhondt Table chairs="+chairs+ " parties= " +votes.length+ "\n");
// Build D'Hondt Table
	for (int m = 0; m < chairs; m++) {
		for (int n = 0; n < votes.length; n++) {
			if (m == 0)
				dhondtTable[m][n] = votes[n];
			else
				dhondtTable[m][n] = dhondtTable[0][n]/(m+1);
		}
	}
//Print Table
        System.out.println("Table for D'Hondt");
	DrawDhondtTable(dhondtTable,votes.length, chairs);

/*Allocate Ministers using D'Hondt
 * iterate through the number of cabinet posts to allocate
 * find the highest value in the D'Hondt Table 
 */
	int o = 0;
	for (int c= 1; c < chairs+1; c++) {
		o = getMaxElement(dhondtTable,votes.length, chairs);
		DrawDhondtTable(dhondtTable,votes.length, chairs);
	 	System.out.println(" Seat " + c + " for " + party[o]);
		allocated[o] = allocated[o] + 1;
	}

	DrawDhondtTable(dhondtTable,votes.length, chairs);

//Output Results
	for (int p=0; p < votes.length; p++) {
		System.out.println(party[p] + " gets " + allocated [p] + " Minsters");
	}
	

    }

}
