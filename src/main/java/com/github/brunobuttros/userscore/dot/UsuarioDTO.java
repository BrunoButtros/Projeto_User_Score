package dot;

import entity.EnderecoEntity;

import java.util.List;

public record UsuarioDTO(Long id, String nome, String email, String telefone, String cpfOuRg, int score, List<EnderecoDTO> enderecos) {
}
