package com.adrianomenezes.negativacao;


import com.adrianomenezes.negativacao.model.entity.Cliente;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;
//import com.amazonaws.services.lambda.*;
//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.rdsdata.AWSRDSData;
//import com.amazonaws.services.rdsdata.AWSRDSDataClientBuilder;
//import com.amazonaws.services.rdsdata.model.ExecuteSqlRequest;
//import com.amazonaws.services.rdsdata.model.ExecuteSqlResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.json.JSONTokener;
import org.json.JSONObject;

@SpringBootApplication
@RestController
public class NegativacaoApplication {

	private final List<Cliente> clientes = new ArrayList<>();
	private final String dbName = "negativacao";
	private final String userName = "admin";
	private final String password = "giovanna";
	private final String hostname = "database-negativacao.c8m8cnqok55n.us-west-2.rds.amazonaws.com";
	private final String port = "3306";
	private final String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
			port + "/" + dbName + "?user=" + userName + "&password=" + password;








	public static void main(String[] args) {
		SpringApplication.run(NegativacaoApplication.class, args);
	}

	@GetMapping("/negativacao")
//	public String negativacao(@RequestParam(value = "name", defaultValue = "Cliente 1") String name) {
    public List<Cliente> negativacao(@RequestParam(value = "name", defaultValue = "Cliente 1") String name) {

		List<Cliente> clientesNeg = new ArrayList<>();
    	clientesNeg = clientes.stream().filter(cli -> cli.getNegativar()).collect(Collectors.toList());


		URL url = null;
		try {
			url = new URL("https://zu9zzwqbqa.execute-api.us-west-2.amazonaws.com/NegativaBuroA");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			InputStream retorno = con.getInputStream();

//			//			Lendo Buffer de Retorno
//			BufferedReader in = new BufferedReader(new InputStreamReader(retorno));
//			String inputLine;
//			StringBuffer content = new StringBuffer();
//			while ((inputLine = in.readLine()) != null) {
//				content.append(inputLine);
//			}
//			in.close();
//			System.out.println("RETORNO LAMBDA: " + content);

//			//			Convertendo direto para JSON
			JSONObject jsonObject = new JSONObject(new JSONTokener(retorno));
			System.out.println("RETORNO LAMBDA: " + jsonObject);



			// Finally we have the response
			System.out.println("RETORNO LAMBDA: " + con.getResponseCode());

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return clientesNeg;
//		return String.format("Negativacao de cliente:  %s!", name);
	}



	private List<Cliente> listaClientes(){

		Connection conn = null;
		Statement setupStatement = null;
		Statement readStatement = null;
		Statement execStatement = null;
		ResultSet resultSet = null;
		String results = "";
		String statement = null;
		List<Cliente> resultClientes = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(jdbcUrl);

			readStatement = conn.createStatement();
			resultSet = readStatement.executeQuery("SELECT * FROM tb_clientes;");


			while(resultSet.next()) {
				Cliente cli = new Cliente();
				System.out.println(resultSet.getObject(1));
				System.out.println(resultSet.getObject(2));
				System.out.println(resultSet.getObject(3));
				System.out.println(resultSet.getObject(4));
				cli.setId((Integer) resultSet.getObject(1));
				cli.setNome(resultSet.getObject(2).toString());
				cli.setDivida((Double) resultSet.getObject(3));
				cli.setNegativar((Boolean) resultSet.getObject(4));
				resultClientes.add(cli);
			}


//			JSONArray json = new JSONArray();
//			ResultSetMetaData rsmd = resultSet.getMetaData();
//			while(resultSet.next()) {
//				int numColumns = rsmd.getColumnCount();
//				JSONObject obj = new JSONObject();
//				for (int i=1; i<=numColumns; i++) {
//					String column_name = rsmd.getColumnName(i);
//					obj.put(column_name, resultSet.getObject(column_name));
//				}
//				json.put(obj);
//			}
//
//			ArrayList<Object> listdata = new ArrayList<Object>();
//			JSONArray jArray = (JSONArray)json;
//			if (jArray != null) {
//				for (int i=0;i<jArray.length();i++){
//					listdata.add(jArray.getString(i));
//				}
//			}

//			ArrayList<Cliente> resultClientes = (ArrayList<Cliente>)listdata;
			return resultClientes;

//			resultSet.first();
//			results = resultSet.getString("Nome");
//			resultSet.next();
//			results += ", " + resultSet.getString("Nome");
//
//			System.out.println("Clientes: " + results);
//
//			resultSet.close();
//			readStatement.close();
//			conn.close();

		} catch (SQLException ex) {
			// Handle any errors
			System.out.println("SQLException: " + ex.getMessage());
			System.out.println("SQLState: " + ex.getSQLState());
			System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
			System.out.println("Closing the connection.");
			if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
		}

		return null;
	}

}