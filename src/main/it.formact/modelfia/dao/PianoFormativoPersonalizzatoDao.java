package modelfia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import model.dao.DaoInterface;
import model.entity.IscrizioneEntity;
import model.entity.PercorsoFormativoEntity;
import model.entity.PreferenzaStudenteEntity;
import pianoformativopersonalizzato.geneticalgorithm.Stato;

/**
 * 
 * @author GianlucaScisciolo
 * 
 * Questa classe si occupa di interrogare il DB per la parte del modulo intelligente.
 *
 */
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
	
	/**
	 * 
	 * Il seguente metodo restituisce un array di giorni liberi in base alle preferenze dello studente.
	 * 
	 * @param preferenze: preferenze studente
	 * @return giorni liberi studente
	 * @throws SQLException
	 */
	public ArrayList<String> doRetrieveGiorniLiberi(ArrayList<PreferenzaStudenteEntity> preferenze) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		ArrayList<String> giorniLiberi = new ArrayList<String>();
		
		// Se non ci sono preferenze allora ritorno un array vuoto:
		if (preferenze == null || preferenze.size() <= 0) {
			return giorniLiberi;
		}
		
		// Se è presente almeno una preferenza allora continuo.
		
		// interrogo il database che ritorna un array di giorni costituiti solamente da quelli che lo studente 
		// ha inserito come disponibilità:
		String selectSQL = "SELECT GIORNO FROM GIORNO_SETTIMANA";
		selectSQL += " WHERE ( ";
		selectSQL += " GIORNO_SETTIMANA.ID = " + preferenze.get(0).getDisponibilita();
		for (int i = 1; i < preferenze.size(); i++) {
			selectSQL += " OR GIORNO_SETTIMANA.ID = " + preferenze.get(i).getDisponibilita();
		}
		selectSQL += " )";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				String giornoLibero = rs.getString("GIORNO");	
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
		
		// ritorno un array di giorni liberi non vuoto
		return giorniLiberi;
	}
	
	/**
	 * 
	 * Il seguente metodo restituisce lo spazio degli stati tramite i giorni liberi e le iscrizioni di uno studente:
	 * per ogni stato presente nel DB, esso verrà selezionato:
	 * - se è disponibile per almeno un giorno libero;
	 * - se è un percorso formativo che NON fa parte delle iscrizioni dello studente.
	 * 
	 * @param giorniLiberi: giorni liberi dello studente
	 * @param iscrizioni: iscrizioni dello studente
	 * @return un array di stati (spazio stati)
	 * @throws SQLException
	 */
	public ArrayList<Stato> doRetrieveSpazioStati(ArrayList<String> giorniLiberi, ArrayList<IscrizioneEntity> iscrizioni) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<Stato> spazioStati = new ArrayList<Stato>();
		
		// se non sono presenti dei giorni liberi allora ritorno un array vuoto
		if (giorniLiberi == null || giorniLiberi.size() <= 0) {
			return spazioStati;
		}
		
		// interrogo il database che ritorna un array di stati che non hanno un percorso formativo che:
		// - non fa parte delle iscrizioni dello studente;
		// - viene insegnato nei giorni liberi dello studente.
		// gli stati dell'array sono ordinati in modo pseudo-casuale
		
		String selectSQL = "SELECT * FROM percorso_formativo, disponibilità";
		selectSQL += " WHERE ";
		selectSQL += " percorso_formativo.idpercorso_formativo = disponibilità.percorsoFormativo";
		
		if (iscrizioni.size() > 0) {
			for (int i = 0; i < iscrizioni.size(); i++) {
				selectSQL += " AND percorso_formativo.idpercorso_formativo != " + iscrizioni.get(i).getPercorsoFormativo();
			}
		}
		
		if (giorniLiberi.size() > 0) {
			selectSQL += " AND ( disponibilità.giornoSettimana = '" + giorniLiberi.get(0) + "'";
			for (int i = 1; i < giorniLiberi.size(); i++) {
				selectSQL += " OR disponibilità.giornoSettimana = '" + giorniLiberi.get(i) + "'";
			}
		}
				
		selectSQL += " )";
		
		selectSQL += " ORDER BY RAND()";
		
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
		
		// ritorno un array di stati NON vuoto
		return spazioStati;
	}
	
	/**
	 * Il seguente metodo restituisce gli interessi dello studente.
	 * 
	 * @param idStudente: identificativo studente
	 * @return gli interessi dello studente
	 * @throws SQLException
	 */
	public ArrayList<String> doRetrieveInteressiStudente(int idStudente) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		ArrayList<String> interessiStudente = new ArrayList<>();
		
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
				String nomeInteresse = rs.getString("nome");
				
				interessiStudente.add(nomeInteresse);
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
		
		// ritorno un array di interessi
		return interessiStudente;
	}
	
	/**
	 * Il seguente metodo restituisce tutte le categorie di percorsi formativi.
	 * 
	 * @return le categorie di percorsi formativi
	 * @throws SQLException
	 */
	public Map<Integer,String> doRetrieveCategorie() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;

		Map<Integer,String> categorie = new HashMap<Integer,String>();
		
		String selectSQL = "SELECT IDCATEGORIA, NOME FROM categoria ORDER BY IDCATEGORIA";
		
		try {
			connection = ds.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			
			ResultSet rs = preparedStatement.executeQuery();
			
			while (rs.next()) {
				Integer idCategoria = rs.getInt("IDCATEGORIA");
				String nome = rs.getString("NOME");
				
				categorie.put(idCategoria, nome);
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
		
		return categorie;
	}

	
	
	
	
	@Override
	public int doSave(Object bean) throws SQLException {
		return 0;
	}
	
	@Override
	public boolean doDelete(int id) throws SQLException {
		return false;
	}
	
	@Override
	public Object doRetrieveByKey(int id) throws SQLException {
		return null;
	}

	@Override
	public Object doRetrieveAll() throws SQLException {
		return null;
	}
	
}








