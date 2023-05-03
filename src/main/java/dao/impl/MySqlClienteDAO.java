package dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import dao.ClienteDAO;
import entity.Categoria;
import entity.Cliente;
import util.MySqlDBConexion;

public class MySqlClienteDAO implements ClienteDAO{


	private static Logger log = Logger.getLogger(MySqlClienteDAO.class.getName());
	
	public int insertaCliente(Cliente  obj) {
		int salida = -1;
		
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = MySqlDBConexion.getConexion();
			
			String sql = "insert into cliente values(null,?,?,?,?,?)";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getNombre());
			pstm.setString(2, obj.getDni());
			pstm.setTimestamp(3, obj.getFechaRegistro());
			pstm.setInt(4, obj.getEstado());
			pstm.setInt(5, obj.getCategoria().getIdCategoria());
			
			log.info(">>>> " + pstm);

			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) pstm.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {}
		}
		
		return salida;
	}

	@Override
	public List<Cliente> listaCliente(String filtro) {
		List<Cliente> lista = new ArrayList<Cliente>();
		Connection conn = null;
		PreparedStatement pstm = null;
		ResultSet rs = null;
		try {
			conn = MySqlDBConexion.getConexion();
			
			String sql = "select cl.*, ca.nombre from cliente cl inner join categoria ca on "
					+ " cl.idCategoria = ca.idCategoria "
					+ " where cl.nombre like ? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, filtro);
			
			log.info(">>>> " + pstm);

			rs = pstm.executeQuery();
			Cliente objCliente = null;
			Categoria objCategoria = null;
			while(rs.next()) {
				objCliente = new Cliente();
				objCliente.setIdCliente(rs.getInt(1));
				objCliente.setNombre(rs.getString(2));
				objCliente.setDni(rs.getString(3));
				objCliente.setFechaRegistro(rs.getTimestamp(4));
				objCliente.setEstado(rs.getInt(5));
				
				objCategoria = new Categoria();
				objCategoria.setIdCategoria(rs.getInt(6));
				objCategoria.setNombre(rs.getString(7));
				objCliente.setCategoria(objCategoria);
				
				lista.add(objCliente);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) pstm.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {}
		}
		
		return lista;
	}

	@Override
	public int eliminaCliente(int idCliente) {
		int salida = -1;
		
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = MySqlDBConexion.getConexion();
			
			String sql = "delete from cliente where idCliente = ?";
			pstm = conn.prepareStatement(sql);
			pstm.setInt(1, idCliente);
			
			log.info(">>>> " + pstm);

			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) pstm.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {}
		}
		
		return salida;
	}

	@Override
	public int actualizaCliente(Cliente obj) {
		int salida = -1;
		
		Connection conn = null;
		PreparedStatement pstm = null;
		try {
			conn = MySqlDBConexion.getConexion();
			
			String sql = "update cliente set nombre = ?, dni = ?, estado = ?, idCategoria = ? where idCliente = ? ";
			pstm = conn.prepareStatement(sql);
			pstm.setString(1, obj.getNombre());
			pstm.setString(2, obj.getDni());
			pstm.setInt(3, obj.getEstado());
			pstm.setInt(4, obj.getCategoria().getIdCategoria());
			pstm.setInt(5, obj.getIdCliente());
			
			log.info(">>>> " + pstm);

			salida = pstm.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstm != null) pstm.close();
				if (conn != null) conn.close();
			} catch (Exception e2) {}
		}
		
		return salida;
	}

	
}






