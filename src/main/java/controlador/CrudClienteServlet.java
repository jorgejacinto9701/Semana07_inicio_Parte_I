package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import dao.ClienteDAO;
import entity.Categoria;
import entity.Cliente;
import entity.Respuesta;
import fabricas.Fabrica;

@WebServlet("/crudCliente")
public class CrudClienteServlet extends HttpServlet {

	private static Logger log = Logger.getLogger(CrudClienteServlet.class.getName());

	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info(">>> CrudCliente  >> service");
		String vmetodo = req.getParameter("metodo");
		switch (vmetodo) {
		case "lista":
			lista(req, resp);
			break;
		case "inserta":
			inserta(req, resp);
			break;
		case "actualiza":
			actualiza(req, resp);
			break;
		case "eLogica":
			eliminacionLogica(req, resp);
			break;
		case "eFisica":
			eliminacionFisica(req, resp);
			break;
		}
	}

	protected void lista(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info(">>> CrudCliente  >> lista");

		Fabrica fabrica = Fabrica.getFabrica(Fabrica.MYSQL);
		ClienteDAO dao = fabrica.getCliente();

		String filtro = req.getParameter("filtro");
		List<Cliente> lista = dao.listaCliente(filtro + "%");

		Gson gson = new Gson();
		String json = gson.toJson(lista);

		resp.setContentType("application/json;charset=UTF-8");

		PrintWriter out = resp.getWriter();
		out.println(json);
	}

	protected void inserta(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info(">>> CrudCliente  >> inserta");
		
		String vnombre = req.getParameter("nombre");
		String vdni = req.getParameter("dni");
		String vcategoria = req.getParameter("categoria");

		Categoria objCategoria = new Categoria();
		objCategoria.setIdCategoria(Integer.parseInt(vcategoria));
		
		Cliente objCliente = new Cliente();
		objCliente.setNombre(vnombre);
		objCliente.setDni(vdni);
		objCliente.setCategoria(objCategoria);
		objCliente.setEstado(1);
		objCliente.setFechaRegistro(new Timestamp(System.currentTimeMillis()));
		
		Fabrica fabrica = Fabrica.getFabrica(Fabrica.MYSQL);
		ClienteDAO dao = fabrica.getCliente();
	
		int insertados = dao.insertaCliente(objCliente);
		List<Cliente> lista = dao.listaCliente("%");
		
		Respuesta objRespuesta = new Respuesta();
		if (insertados > 0) {
			objRespuesta.setDatos(lista);
			objRespuesta.setMensaje("Registro exitoso");
		}else {
			objRespuesta.setMensaje("Error en el registro");
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(objRespuesta);
		
		resp.setContentType("application/json;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println(json);
	}

	protected void actualiza(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		log.info(">>> CrudCliente  >> actualiza");
	}

	protected void eliminacionLogica(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info(">>> CrudCliente  >> eliminacionLogica");
	}

	protected void eliminacionFisica(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		log.info(">>> CrudCliente  >> eliminacionFisica");
		
		String idCliente = req.getParameter("idCliente");
		
		Fabrica fabrica = Fabrica.getFabrica(Fabrica.MYSQL);
		ClienteDAO dao = fabrica.getCliente();
	
		int eliminados = dao.eliminaCliente(Integer.parseInt(idCliente));
		List<Cliente> lista = dao.listaCliente("%");
		
		Respuesta objRespuesta = new Respuesta();
		if (eliminados > 0) {
			objRespuesta.setDatos(lista);
			objRespuesta.setMensaje("Eliminación exitosa del cliente de ID : " + idCliente);
		}else {
			objRespuesta.setMensaje("Error en la eliminación");
		}
		
		Gson gson = new Gson();
		String json = gson.toJson(objRespuesta);
		
		resp.setContentType("application/json;charset=UTF-8");
		
		PrintWriter out = resp.getWriter();
		out.println(json);
		
	}

}
