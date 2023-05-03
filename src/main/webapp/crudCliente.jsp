<!DOCTYPE html>
<html lang="esS">
<head>
<meta charset="UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
<script type="text/javascript" src="js/bootstrapValidator.js"></script>
<script type="text/javascript" src="js/global.js"></script>
<link rel="stylesheet" href="css/bootstrap.css" />
<link rel="stylesheet" href="css/dataTables.bootstrap.min.css" />
<link rel="stylesheet" href="css/bootstrapValidator.css" />

<script type="text/javascript" src="js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="js/dataTables.bootstrap.min.js"></script>
<link rel="stylesheet" href="css/dataTables.bootstrap.min.css"/>

<title>CRUD Cliente</title>
</head>
<body>


	<div class="container">
		<h1>CRUD Cliente</h1>

		<div class="row" style="margin-top: 5%">
			<div class="col-md-3">
				<label class="control-label" for="id_filtro">Nombres</label> 
			</div>	
			<div class="col-md-6">
				<input	class="form-control" type="text" id="id_filtro" placeholder="Ingrese el nombre">
			</div>	
			<div class="col-md-1">
				<button type="button" class="btn btn-primary" id="id_btn_filtro">Filtro</button>
			</div>	
			<div class="col-md-1">
				<button type="button" class="btn btn-primary"  data-toggle='modal' data-target="#id_div_modal_registra" >Registra</button>
			</div>	
		</div>


		<div class="row" style="margin-top: 4%">
			<table id="id_table" class="table table-bordered table-hover table-condensed" >
				<thead style='background-color:#337ab7; color:white'>
					<tr>
						<th style="width: 5%">Código</th>
						<th style="width: 26%">Nombre</th>
						<th style="width: 15%">DNI</th>
						<th style="width: 15%">Estado</th>
						<th style="width: 15%">Categoría</th>
						<th style="width: 8%"></th>
						<th style="width: 8%"></th>
						<th style="width: 8%"></th>
					</tr>
				</thead>
				<tbody>
				</tbody>
			</table>
		</div>
		
		<div class="modal fade" id="id_div_modal_registra" >
			<div class="modal-dialog" style="width: 60%">
					<div class="modal-content">
					<div class="modal-header" >
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4><span class="glyphicon glyphicon-ok-sign"></span> Registro de Cliente</h4>
					</div>
					<div class="modal-body" >
						 <div class="panel-group" id="steps">
			                   <div class="panel panel-default">
			                   		<div id="stepOne" class="panel-collapse collapse in">
			                   			<form id="id_form_registra">
			                   			<input type="hidden" name="metodo" value="inserta">
			                   			<div class="panel-body">
			                                <div class="form-group" >
		                                        <label class="col-lg-3 control-label" for="id_reg_nombre">Nombre</label>
		                                        <div class="col-lg-8">
													<input class="form-control" id="id_reg_nombre" name="nombre" placeholder="Ingrese el Nombre" type="text" maxlength="100"/>
		                                        </div>
		                                    </div> 	
											<div class="form-group">
		                                        <label class="col-lg-3 control-label" for="id_reg_dni">DNI</label>
		                                        <div class="col-lg-8">
													<input class="form-control" id="id_reg_dni" name="dni" placeholder="Ingrese el DNI" type="text" maxlength="8"/>
		                                        </div>
		                                    </div> 	
			                                <div class="form-group">
		                                        <label class="col-lg-3 control-label" for="id_reg_categoria">Categoría</label>
		                                        <div class="col-lg-8">
													<select class="form-control" id="id_reg_categoria" name="categoria">
														<option value=" ">[Seleccione]</option>
													</select>
		                                        </div>
		                                    </div> 	 
		                                    <div class="form-group">
		                                        <div class="col-lg-12" align="center">
		                                        	<button type="button" style="width: 80px" id="id_btn_registra" class="btn btn-primary btn-sm">Registrar</button>
		                                        	<button type="button" style="width: 80px" id="id_btn_reg_cancelar" class="btn btn-primary btn-sm" data-dismiss="modal">Cancelar</button>
		                                        </div>
		                                    </div>   
			                             </div>
			                             </form>
			                        </div>
			                   </div>
			              </div>
					</div>
				</div>
			</div>
		</div>	 
  
	</div>


	<script type="text/javascript">
		$.getJSON("cargaCategoria", {}, function (data){
			$.each(data, function(index, item){
				$("#id_reg_categoria").append("<option value=" +  item.idCategoria +" >" +  item.nombre+ "</option>");
			});	
		});		
	
		$("#id_btn_registra").click(function (){
				//alert($('#id_form_registra').serialize());
		        $.ajax({
		          type: "POST",
		          url: "crudCliente", 
		          data: $('#id_form_registra').serialize(),
		          success: function(data){
		        	  agregarGrilla(data.datos);
		        	  mostrarMensaje(data.mensaje);
		          },
		          error: function(){
		        	  mostrarMensaje(MSG_ERROR);
		          }
		        });
		});	
	
		$("#id_btn_filtro").click(function() {
			var vfiltro = $("#id_filtro").val();
			$.getJSON("crudCliente", {"metodo":"lista","filtro":vfiltro}, function(data) {
				agregarGrilla(data);
			});
		});
		
		function agregarGrilla(lista){
			 $('#id_table').DataTable().clear();
			 $('#id_table').DataTable().destroy();
			 $('#id_table').DataTable({
					data: lista,
					language: IDIOMA,
					searching: true,
					ordering: true,
					processing: true,
					pageLength: 10,
					lengthChange: false,
					info:true,
					scrollY: 305,
			        scroller: {
			            loadingIndicator: true
			        },
					columns:[
						{data: "idCliente",className:'text-center'},
						{data: "nombre",className:'text-center'},
						{data: "dni",className:'text-center'},
						{data: "estado",className:'text-center'},
						{data: "categoria.nombre",className:'text-center'},
						{data: function(row, type, val, meta){
							var salida='<button type="button" style="width: 90px" class="btn btn-info btn-sm">Editar</button>';
							return salida;
						},className:'text-center'},	
						{data: function(row, type, val, meta){
							var salida='<button type="button" style="width: 90px" class="btn btn-success btn-sm">Activo</button>';
							return salida;
						},className:'text-center'},	
						{data: function(row, type, val, meta){
							var salida='<button type="button" style="width: 90px" class="btn btn-danger btn-sm" onclick="eliminacionFisica(\''+ row.idCliente +'\')"  >Elimina</button>'; 
							return salida;
						},className:'text-center'},	
					]                                     
			    });
		}
		
		function eliminacionFisica(idCliente){
		     //alert(">>" + idCliente);
			 $.ajax({
		          type: "POST",
		          url: "crudCliente", 
		          data: {"metodo":"eFisica", "idCliente":idCliente},
		          success: function(data){
		        	  agregarGrilla(data.datos);
		        	  mostrarMensaje(data.mensaje);
		          },
		          error: function(){
		        	  mostrarMensaje(MSG_ERROR);
		          }
		     });
		 }
		
	</script>

</body>
</html>




