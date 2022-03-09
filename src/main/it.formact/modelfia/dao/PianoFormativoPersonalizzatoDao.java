package modelfia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.dao.DaoInterface;

import model.entity.PercorsoFormativoEntity;
import model.entity.PreferenzaStudenteEntity;

import pianoformativopersonalizzato.service.Stato;

public class PianoFormativoPersonalizzatoDao implements DaoInterface {
	
	private static DataSource ds;
	
	static {
		try {
			Context initCtx = new InitialContext();
			Context envCtx = (Context) initCtx.lookup("java:comp/env");

			ds = (DataSource) envCtx.lookup("jdbc/formactds");
          
		} catch (NamingException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}
	
	public ArrayList<String> doRetrieveGiorniLiberi(ArrayList<PreferenzaStudenteEntity> preferenze) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ArrayList<String> giorniLiberi = new ArrayList<String>();
		
		if (preferenze == null || preferenze.size() <= 0) {
			return giorniLiberi;
		}
		
		String selectSQL = "SELECT giornoSettimana FROM disponibilità";
		selectSQL += " WHERE ( ";
		selectSQL += " disponibilità.id = " + preferenze.get(0).getDisponibilita();
		for (int i = 1; i < preferenze.size(); i++) {
			selectSQL += " OR disponibilità.id = " + preferenze.get(i).getDisponibilita();
		}
		selectSQL += " )";
				
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				String giornoLibero = rs.getString("giornoSettimana");	
				giorniLiberi.add(giornoLibero);
			}
		}
		finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} 
			finally {
				if (connection != null)
					connection.close();
			}
		}
		return giorniLiberi;
	}
	
	public ArrayList<Stato> doRetrieveSpazioStati(ArrayList<String> giorniLiberi) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Stato> spazioStati = new ArrayList<Stato>();
		
		if (giorniLiberi == null || giorniLiberi.size() <= 0) {
			return spazioStati;
		}
		
		String selectSQL = "SELECT * FROM percorso_formativo, disponibilità";
		selectSQL += " WHERE ";
		selectSQL += " percorso_formativo.idpercorso_formativo = disponibilità.percorsoFormativo";
		selectSQL += " AND ( disponibilità.giornoSettimana = '" + giorniLiberi.get(0) + "'";
		
		for (int i = 1; i < giorniLiberi.size(); i++) {
			selectSQL += " OR disponibilità.giornoSettimana = '" + giorniLiberi.get(i) + "'";
		}
		
		selectSQL += " ) ORDER BY RAND()";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				Stato stato = new Stato();
				PercorsoFormativoEntity percorsoFormativo = new PercorsoFormativoEntity();
				
				percorsoFormativo.setId(rs.getInt("idpercorso_formativo"));
				percorsoFormativo.setId_formatore(rs.getInt("formatore"));
				percorsoFormativo.setNome(rs.getString("nome"));
				percorsoFormativo.setCategoria(rs.getInt("ambito"));
				percorsoFormativo.setDescrizione(rs.getString("descrizione"));
				percorsoFormativo.setIndice_contenuti(rs.getString("indiceContenuti"));
				percorsoFormativo.setNum_lezioni(rs.getInt("numeroLezioni"));
				percorsoFormativo.setCosto(rs.getDouble("costo"));
				
				stato.setPercorsoFormativo(percorsoFormativo);
				stato.setGiorno(rs.getString("giornoSettimana"));
				stato.setOrario(rs.getObject("orario", LocalTime.class));
				
				spazioStati.add(stato);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return spazioStati;
	}
	
	public ArrayList<String> doRetrieveInteressiStudente(int idStudente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<String> interessiStudente = new ArrayList<String>();
		
		String selectSQL = "SELECT * FROM interesse_studente, interesse ";
		selectSQL += " WHERE ";
		selectSQL += " interesse_studente.interesse = interesse.idinteresse";
		selectSQL += " AND interesse_studente.studente = ?";
				
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setInt(1, idStudente);
			
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String interesseStudente = rs.getString("nome");
				
				interessiStudente.add(interesseStudente);
			}

		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				if (connection != null)
					connection.close();
			}
		}
		return interessiStudente;
	}
	
	
	
	
	
	
	
	
	

	@Override
	public int doSave(Object bean) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean doDelete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object doRetrieveByKey(int id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object doRetrieveAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}
	
}
