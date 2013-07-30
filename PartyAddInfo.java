/* PartyAddInfo class
 * the party data 
 * by Conor Gilmer(cgilmer@tinet.ie)
 **/

public class PartyAddInfo
{
	public static String getVersion()
	{
		return new String("$Revision: 2 $ $Date: 15.11.04 16:32 $");
	}
	public	PartyAddInfo(String	partyName,	
				double	percent,
				String	partyLetters, 
				int	seats, 
				String	partyColor,
				int 	ministers						   )
	{
		this.partyName		= partyName;
		this.percent		= percent;
		this.partyLetters 	= partyLetters;
		this.seats		= seats;
		this.partyColor		= partyColor;
		this.ministers		= ministers;
	}
	
	/** Party Name. */
	protected String partyName=null;

	/** Vote percentage obtained. */
	protected double percent=0.0;

	/** 2 or 3 letters identifying the party. */
	protected String partyLetters=null;
	protected String partyColor="blue";

	/** Number of Seats elected */
	protected int seats=0;

	/** Cabinet Seats via D'hondt*/
	protected int ministers=0;

	/** display/return the party info added */
	 public String toString() {
			return "[ " + partyName + "("+ partyLetters +"), "
					 + seats + "(Seats), "
					 + percent + "(% Votes), " 
					 + ministers + "(Ministers) " 
					 + partyColor + "(Color) " 
					 + " ]";
    }
	public void addMinister(){
		this.ministers = this.ministers++;
}

}
