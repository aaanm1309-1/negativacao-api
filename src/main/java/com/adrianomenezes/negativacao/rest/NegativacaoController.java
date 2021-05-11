package com.adrianomenezes.negativacao.rest;

import com.adrianomenezes.negativacao.model.entity.Cliente;
import com.adrianomenezes.negativacao.model.repository.ClienteRepository;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/negativacao")
public class NegativacaoController {

    private final ClienteRepository repository;

    public NegativacaoController(ClienteRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Cliente> negativacao(@RequestParam(value = "id", defaultValue = "0") Integer id) {

        System.out.println(id);
        List<Cliente> clientesNegativar = new ArrayList<>();
        if (id != 0 ) {
            clientesNegativar = repository.findClientesANegativar(id);
        }
        else {
            clientesNegativar = repository.findClientesANegativar();
            }
        List<Cliente> clientesNegativado = new ArrayList<>();

        clientesNegativar.forEach(cliente -> {
//            System.out.println(cliente);

            URL url = null;
            try {
                url = new URL("https://zu9zzwqbqa.execute-api.us-west-2.amazonaws.com/NegativaBuroA");
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");

                InputStream retorno = con.getInputStream();

                JSONObject jsonObject = new JSONObject(new JSONTokener(retorno));
                System.out.println("RETORNO LAMBDA: " + jsonObject);
                clientesNegativado.add(cliente);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

         return clientesNegativado;
    }

}
