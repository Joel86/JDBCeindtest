package be.vdab;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		DatabaseManager manager = new DatabaseManager();
		try(Scanner scanner = new Scanner(System.in)) {
			int keuze = toonMenu(scanner);
			switch(keuze) {
				case 1:
					while(!manager.rekeningAanmaken(vraagRekeningNummer(scanner, "")));
					break;
				case 2:
					while(!manager.saldoConsulteren(vraagRekeningNummer(scanner, "")));
					break;
				case 3:
					while(!manager.geldOverschrijven(vraagRekeningNummer(scanner, " opdrachtgever"), vraagRekeningNummer(scanner, " begunstigde"), vraagBedrag(scanner)));
					break;
			}
		}
	}
	private static int toonMenu(Scanner scanner) {
		boolean stop = false;
		int keuze = 0;
		while(!stop) {
			try {
				System.out.println("1. Nieuwe rekening\n2. Saldo consulteren\n3. Overschrijven");
				keuze = scanner.nextInt();
				if(keuze < 1 || keuze > 3) {
					System.out.println("Ongeldige invoer, maak een keuze uit het menu.");		
				}
				else {
					stop = true;
				}
			} catch(InputMismatchException ex) {
				System.out.println("ongeldige invoer, maak een keuze uit het menu.");
				scanner.nextLine();
			}
		}
		return keuze;
	}
	
	private static long vraagRekeningNummer(Scanner scanner, String identificatie) {
		boolean stop = false;
		long rekeningNummer = 0;
		while(!stop) {
			System.out.println("Rekeningnummer" + identificatie + ": ");
			try {
				rekeningNummer = scanner.nextLong();
				if (new Rekening(rekeningNummer).isGeldig()) {
					stop = true;
				}
				else {
					System.out.println("Ongeldig rekeningnummer, probeer opnieuw");
				}
			} catch (InputMismatchException ex) {
				System.out.println("Ongeldig rekeningnummer, probeer opnieuw.");
				scanner.nextLine();
			}
		}
		return rekeningNummer;
	}
	
	private static BigDecimal vraagBedrag(Scanner scanner) {
		boolean stop = false;
		BigDecimal bedrag = BigDecimal.ZERO;
		while(!stop) {
			System.out.println("Bedrag: ");
			try {
				bedrag = scanner.nextBigDecimal();
				if(bedrag.compareTo(BigDecimal.ZERO) <= 0) {
					System.out.println("Over te schrijven bedrag moet groter zijn dan nul. Probeer opnieuw.");
				}
				else {
					stop = true;
				}
			} catch(InputMismatchException ex) {
				System.out.println("Ongeldige invoer, probeer opnieuw.");
				scanner.nextLine();
			}
		}
		return bedrag;
	}
}