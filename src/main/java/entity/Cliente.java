package entity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {

	private int idCliente;
	private String nombre;
	private String dni;
	private Timestamp fechaRegistro;
	private int estado;
	private Categoria categoria;

	
}
