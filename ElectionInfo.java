/* ElectionInfo class
 * the party data 
 * by Conor Gilmer(cgilmer@tinet.ie)
 **/

public class ElectionInfo
{
	public static String getVersion()
	{
		return new String("$Revision: 2 $ $Date: 15.11.04 16:32 $");
	}
	public	ElectionInfo(String	electionYear,	
				String  parliament,
				double	percentTurnout,
				String  electionOutcome,
				int	seats, 
				int 	ministersDhondt				   )
	{
		this.electionYear	= electionYear;
		this.parliament		= parliament;
		this.percentTurnout	= percentTurnout;
		this.electionOutcome	= electionOutcome;
		this.seats		= seats;
		this.ministersDhondt	= ministersDhondt;
	}
	
	/** Vote percentage obtained. */
	protected double percentTurnout=0.0;

	protected String electionYear=null;
	protected String parliament="Dail";
	protected String electionOutcome="Hung Dail";

	/** Number of Seats elected */
	protected int seats=0;

	/** Cabinet Seats via D'hondt*/
	protected int ministersDhondt=0;

	/** display/return the party info added */
	 public String toString() {
			return "[ " + electionYear + "("+ parliament +"), "
					 + seats + "(Seats), "
					 + percentTurnout + "(% Turnout), " 
					 + electionOutcome + "(Election Outcome) " 
					 + ministersDhondt + "(Ministers allocated) " 
					 + " ]";
    }
/*	public void addMinister(){
		this.ministers = this.ministers++;
}*/

}
