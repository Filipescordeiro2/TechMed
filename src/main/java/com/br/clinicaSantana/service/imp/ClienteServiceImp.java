package com.br.clinicaSantana.service.imp;

import com.br.clinicaSantana.dto.ClienteDTO;
import com.br.clinicaSantana.dto.EnderecoClienteDTO;
import com.br.clinicaSantana.dto.LoginSenhaClienteDTO;
import com.br.clinicaSantana.entity.ClienteEntity;
import com.br.clinicaSantana.entity.EnderecoClienteEntity;
import com.br.clinicaSantana.exception.RegraDeNegocioException;
import com.br.clinicaSantana.repository.ClienteRepository;
import com.br.clinicaSantana.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Implementação da interface ClienteService.
 * Fornece métodos para gerenciar clientes.
 */
@Service
public class ClienteServiceImp implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * Cadastra um novo cliente no sistema.
     *
     * @param clienteDTO os dados do cliente a ser cadastrado
     * @return os dados do cliente cadastrado
     */
    @Override
    @Transactional
    public ClienteDTO cadastrarCliente(ClienteDTO clienteDTO) {
        try {
            ClienteEntity clienteEntity = fromDto(clienteDTO);
            EnderecoClienteEntity enderecoEntity = fromDto(clienteDTO.getEnderecoCliente());

            clienteEntity.getEnderecos().add(enderecoEntity);
            enderecoEntity.setClienteEntity(clienteEntity);

            clienteEntity = clienteRepository.save(clienteEntity);

            return toDto(clienteEntity);

        } catch (Exception e) {
            System.err.println("Erro ao cadastrar cliente: " + e.getMessage());
            throw new RegraDeNegocioException("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    /**
     * Busca um cliente pelo CPF.
     *
     * @param cpf o CPF do cliente a ser buscado
     * @return os dados do cliente encontrado
     */
    @Override
    public ClienteDTO buscarClientePorCpf(String cpf) {
        ClienteEntity clienteEntity = clienteRepository.findByCpf(cpf)
                .orElseThrow(() -> new RegraDeNegocioException("Cliente não encontrado com o CPF: " + cpf));
        return toDto(clienteEntity);
    }

    /**
     * Autentica um cliente pelo login e senha.
     *
     * @param loginSenhaClienteDTO os dados de login e senha do cliente
     * @return os dados do cliente autenticado
     */
    @Override
    public ClienteDTO autenticarCliente(LoginSenhaClienteDTO loginSenhaClienteDTO) {
        ClienteEntity clienteEntity = clienteRepository.findByLogin(loginSenhaClienteDTO.getLogin())
                .orElseThrow(() -> new RegraDeNegocioException("Login ou senha inválidos"));

        if (!clienteEntity.getSenha().equals(loginSenhaClienteDTO.getSenha())) {
            throw new RegraDeNegocioException("Login ou senha inválidos");
        }

        return toDto(clienteEntity);
    }

    /**
     * Converte uma entidade ClienteEntity para um DTO ClienteDTO.
     *
     * @param clienteEntity a entidade do cliente
     * @return o DTO do cliente
     */
    private ClienteDTO toDto(ClienteEntity clienteEntity) {
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setId(clienteEntity.getId());
        clienteDTO.setLogin(clienteEntity.getLogin());
        clienteDTO.setSenha(clienteEntity.getSenha());
        clienteDTO.setNome(clienteEntity.getNome());
        clienteDTO.setSobrenome(clienteEntity.getSobrenome());
        clienteDTO.setEmail(clienteEntity.getEmail());
        clienteDTO.setCpf(clienteEntity.getCpf());
        clienteDTO.setCelular(clienteEntity.getCelular());
        clienteDTO.setEnderecoCliente(toDto(clienteEntity.getEnderecos().get(0)));
        return clienteDTO;
    }

    /**
     * Converte um DTO ClienteDTO para uma entidade ClienteEntity.
     *
     * @param clienteDTO o DTO do cliente
     * @return a entidade do cliente
     */
    private ClienteEntity fromDto(ClienteDTO clienteDTO) {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setLogin(clienteDTO.getLogin());
        clienteEntity.setSenha(clienteDTO.getSenha());
        clienteEntity.setNome(clienteDTO.getNome());
        clienteEntity.setSobrenome(clienteDTO.getSobrenome());
        clienteEntity.setEmail(clienteDTO.getEmail());
        clienteEntity.setCpf(clienteDTO.getCpf());
        clienteEntity.setCelular(clienteDTO.getCelular());
        return clienteEntity;
    }

    /**
     * Converte uma entidade EnderecoClienteEntity para um DTO EnderecoClienteDTO.
     *
     * @param enderecoEntity a entidade do endereço do cliente
     * @return o DTO do endereço do cliente
     */
    private EnderecoClienteDTO toDto(EnderecoClienteEntity enderecoEntity) {
        EnderecoClienteDTO enderecoDTO = new EnderecoClienteDTO();
        enderecoDTO.setId(enderecoEntity.getId());
        enderecoDTO.setCep(enderecoEntity.getCep());
        enderecoDTO.setLogradouro(enderecoEntity.getLogradouro());
        enderecoDTO.setNumero(enderecoEntity.getNumero());
        enderecoDTO.setComplemento(enderecoEntity.getComplemento());
        enderecoDTO.setBairro(enderecoEntity.getBairro());
        enderecoDTO.setCidade(enderecoEntity.getCidade());
        enderecoDTO.setEstado(enderecoEntity.getEstado());
        enderecoDTO.setPais(enderecoEntity.getPais());
        return enderecoDTO;
    }

    /**
     * Converte um DTO EnderecoClienteDTO para uma entidade EnderecoClienteEntity.
     *
     * @param enderecoDTO o DTO do endereço do cliente
     * @return a entidade do endereço do cliente
     */
    private EnderecoClienteEntity fromDto(EnderecoClienteDTO enderecoDTO) {
        EnderecoClienteEntity enderecoEntity = new EnderecoClienteEntity();
        enderecoEntity.setCep(enderecoDTO.getCep());
        enderecoEntity.setLogradouro(enderecoDTO.getLogradouro());
        enderecoEntity.setNumero(enderecoDTO.getNumero());
        enderecoEntity.setComplemento(enderecoDTO.getComplemento());
        enderecoEntity.setBairro(enderecoDTO.getBairro());
        enderecoEntity.setCidade(enderecoDTO.getCidade());
        enderecoEntity.setEstado(enderecoDTO.getEstado());
        enderecoEntity.setPais(enderecoDTO.getPais());
        return enderecoEntity;
    }
}