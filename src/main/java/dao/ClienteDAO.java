package dao;

import java.util.List;

import entity.Cliente;

public interface ClienteDAO {

	public abstract int insertaCliente(Cliente obj);
	public abstract List<Cliente> listaCliente(String filtro);
	public abstract int eliminaCliente(int idCliente);
	public abstract int actualizaCliente(Cliente obj);
}
