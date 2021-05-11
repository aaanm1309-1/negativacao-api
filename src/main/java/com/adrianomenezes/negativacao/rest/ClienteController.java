package com.adrianomenezes.negativacao.rest;

import com.adrianomenezes.negativacao.model.entity.Cliente;
import com.adrianomenezes.negativacao.model.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

    private final ClienteRepository repository;

    @Autowired
    public ClienteController(ClienteRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public Cliente addCliente(@RequestBody Cliente cliente) {
        boolean exists = !repository.findById(cliente.getId()).isEmpty();
        if (!exists){
            return repository.save(cliente);
        }
        else {
            throw  new ResponseStatusException(HttpStatus.CONFLICT, "Cliente Já cadastrado");
        }

    }

    @GetMapping
    public List<Cliente> getClientes(){
        return repository.findAll();
    }

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente acharPorId(@PathVariable("id") Integer id){
        return repository
                .findById(id)
                .orElseThrow( ()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));

    }

    @GetMapping("/buscanome")
    @ResponseStatus(HttpStatus.OK)
    public List<Cliente> buscaClientes(
            @RequestParam(value = "nome", required = false) String nome
            )
    {
        nome = '%'+nome+'%';
        System.out.println(nome);
        return repository.findByNomes(nome);
    }

}
