package be.vdab;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseCommunicator {
	private static final String INSERT_REKENING = 
			"insert into rekeningen(rekeningnr, saldo) values (?,?)";
	private static final String SELECT_REKENING = 
			"select rekeningnr, saldo from rekeningen where rekeningnr = ?";
	private static final String UPDATE_REKENING = 
			"update rekeningen set saldo = ? where rekeningnr = ?";
	
	public void insert (Rekening rekening, Connection connection) throws DatabaseException {		
		try (PreparedStatement statement = connection.prepareStatement(INSERT_REKENING)) {
			statement.setLong(1, rekening.getRekeningNummer());
			statement.setBigDecimal(2, BigDecimal.ZERO);
			statement.executeUpdate();
			System.out.println("Rekening succesvol aangemaakt.");
		} catch(SQLException ex) {
			throw new DatabaseException("Rekening bestaat al.");
		}
	}
	
	
	public Rekening find (long rekeningNummer, Connection connection) throws DatabaseException, SQLException {
		Rekening rekening = null;
		try (PreparedStatement statement = connection.prepareStatement(SELECT_REKENING)) {
			statement.setLong(1, rekeningNummer);
			try(ResultSet resultSet = statement.executeQuery()) {
				if(resultSet.next()) {
					rekening = new Rekening();
					rekening.setRekeningNummer(resultSet.getLong("rekeningnr"));
					rekening.setSaldo(resultSet.getBigDecimal("saldo"));
				}
				else {
					throw new DatabaseException("Rekening niet gevonden.", rekeningNummer);
				}
			}
		}
		return rekening;
	}
	
	public void update (Rekening rekening, Connection connection) throws SQLException {
		try  (PreparedStatement statement = connection.prepareStatement(UPDATE_REKENING)) {
			statement.setBigDecimal(1, rekening.getSaldo());
			statement.setLong(2, rekening.getRekeningNummer());
			statement.executeUpdate();
		}
	}
}
