package be.vdab;

public class DatabaseException extends Exception {
	private static final long serialVersionUID = 1L;
	private long foutRekeningNummer;
	
	public DatabaseException(String omschrijving) {
		super(omschrijving);
	}
	
	public DatabaseException(String omschrijving, long foutRekeningNummer) {
		super(omschrijving);
		this.foutRekeningNummer = foutRekeningNummer;
	}
	
	public long getFoutRekeningNummer() {
		return foutRekeningNummer;
	}
}
