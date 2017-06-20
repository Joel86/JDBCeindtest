package be.vdab;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
	private static final String URL = "jdbc:mysql://localhost/bankjdbc?useSSL=false";
	private static final String USER = "cursist";
	private static final String PASSWORD = "cursist";
	DatabaseCommunicator communicator = new DatabaseCommunicator();
	
	public boolean rekeningAanmaken(long rekeningNummer) {
		boolean aangemaakt = false;
		Rekening rekening = new Rekening(rekeningNummer);
		try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
			communicator.insert(rekening, connection);
			aangemaakt = true;
			} catch (DatabaseException | SQLException ex) {
				System.out.println(ex.getMessage());
			}
		return aangemaakt;
		}
	
	public boolean saldoConsulteren(long rekeningNummer) {
		boolean gevonden = false;
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
			Rekening rekening = communicator.find(rekeningNummer, connection);
			System.out.println("Saldo: " + rekening.getSaldo());
			gevonden = true;
		} catch (DatabaseException | SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return gevonden;
	}
	
	public boolean geldOverschrijven(long rekeningNummerVan, long rekeningNummerNaar, BigDecimal bedrag) {
		boolean overgeschreven = false;
		try(Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
			Rekening rekeningOpdrachtgever = communicator.find(rekeningNummerVan, connection);
			Rekening rekeningBegunstigde = communicator.find(rekeningNummerNaar, connection);
			rekeningOpdrachtgever.setSaldo(rekeningOpdrachtgever.getSaldo().subtract(bedrag));
			rekeningBegunstigde.setSaldo(rekeningBegunstigde.getSaldo().add(bedrag));
			if((rekeningOpdrachtgever.getSaldo()).compareTo(BigDecimal.ZERO) >= 0) {
				connection.setAutoCommit(false);
				communicator.update(rekeningOpdrachtgever, connection);
				communicator.update(rekeningBegunstigde, connection);
				connection.commit();
				overgeschreven = true;
				System.out.println("Overschrijving succesvol.");
			}
			else {
				System.out.println("Overschrijving mislukt! Saldo ontoereikend.");
			}
		} catch(SQLException ex) {
			ex.printStackTrace();
		} catch(DatabaseException ex) {
			System.out.println(ex.getMessage() + ": " + ex.getFoutRekeningNummer());
		}
		return overgeschreven;
	}
}
