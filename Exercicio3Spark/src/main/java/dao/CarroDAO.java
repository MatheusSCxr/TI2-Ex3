package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import modelo.Carro;

public class CarroDAO extends DAO {
	
	public CarroDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Carro carro) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO carro (codigo, marca, modelo, ano) "
				       + "VALUES ("+carro.getCodigo()+ ", '" + carro.getMarca() + "', '"  
				       + carro.getModelo() + "', '" + carro.getAno() + "');";
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}

	
	public Carro get(int codigo) {
		Carro carro = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM carro WHERE codigo=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 carro = new Carro(rs.getInt("codigo"), rs.getString("marca"), rs.getString("modelo"), rs.getInt("ano"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carro;
	}
	
	//get vazio
	public List<Carro> get() {
		return get("");
	}

	//get pelos IDs
	public List<Carro> getOrderByCodigo() {
		return get("codigo");		
	}
	
	public List<Carro> getOrderByMarca() {
		return get("marca");		
	}
	
	public List<Carro> getOrderByModelo() {
		return get("modelo");		
	}
	
	public List<Carro> getOrderByAno() {
		return get("ano");		
	}
	
	private List<Carro> get(String orderBy) {	//substitui a string no SQL, que irá fazer o sort
	
		List<Carro> carros = new ArrayList<Carro>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM carro" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Carro u = new Carro(rs.getInt("codigo"), rs.getString("marca"), rs.getString("modelo"), rs.getInt("ano"));
	            carros.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return carros;
	}


	//codigo de update usando o Spark
	public boolean update(Carro carro) {
		boolean status = false;
		try {  
			String sql = "UPDATE carro SET modelo = '" + carro.getModelo() + "', "
					   + "marca = '" + carro.getMarca() + "', " 
					   + "ano = " + carro.getAno() + " WHERE codigo = " + carro.getCodigo();
			PreparedStatement st = conexao.prepareStatement(sql);
			st.executeUpdate();
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	//pegar o maior código pra ter IDs distintas
	public int getMaxCodigo() {
	    int maxCodigo = 0;
	    try {
	        String sql = "SELECT MAX(codigo) AS max_codigo FROM carro";
	        Statement stmt = conexao.createStatement();
	        ResultSet rs = stmt.executeQuery(sql);
	        if (rs.next()) {
	            maxCodigo = rs.getInt("max_codigo");
	        }
	        rs.close();
	        stmt.close();
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	    return maxCodigo;
	}

	
	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM carro WHERE codigo = " + codigo;
			st.executeUpdate(sql);
			st.close();
			status = true;
			System.out.println("Carro de código " + codigo + " deletado com sucesso!");
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}	
}