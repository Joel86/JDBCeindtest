package be.vdab;

import java.math.BigDecimal;

public class Rekening {
	private long rekeningNummer;
	private BigDecimal saldo;
	
	public Rekening() {
	}
	
	public Rekening(long rekeningNummer) {
		this.rekeningNummer = rekeningNummer;
	}
	
	public Rekening(long rekeningNummer, BigDecimal saldo) {
		this.rekeningNummer = rekeningNummer;
		this.saldo = saldo;
	}

	public long getRekeningNummer() {
		return rekeningNummer;
	}

	public void setRekeningNummer(long rekeningNummer) {
		this.rekeningNummer = rekeningNummer;

	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}
	
	public boolean isGeldig() {
		if((int)(Math.log10(rekeningNummer)+1) != 12) {
			return false;
		}
		long eersteTienCijfers = rekeningNummer/100;
		int laatsteTweeCijfers = (int)(rekeningNummer%100);
		return(eersteTienCijfers%97 == laatsteTweeCijfers);
	}
}
