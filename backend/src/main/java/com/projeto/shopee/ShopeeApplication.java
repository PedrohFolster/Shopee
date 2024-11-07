package com.projeto.shopee;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.projeto.shopee.entities.Endereco;
import com.projeto.shopee.entities.Status;
import com.projeto.shopee.entities.Usuario;
import com.projeto.shopee.entities.UsuarioAutenticar;
import com.projeto.shopee.entities.Loja;
import com.projeto.shopee.entities.Produto;
import com.projeto.shopee.repository.StatusRepository;
import com.projeto.shopee.repository.UsuarioRepository;
import com.projeto.shopee.repository.LojaRepository;
import com.projeto.shopee.repository.ProdutoRepository;
import com.projeto.shopee.repository.CategoriaLojaRepository;
import com.projeto.shopee.repository.CategoriaProdutoRepository;
import com.projeto.shopee.entities.CategoriaLoja;
import com.projeto.shopee.entities.CategoriaProduto;

@SpringBootApplication
public class ShopeeApplication implements CommandLineRunner {

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LojaRepository lojaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaLojaRepository categoriaLojaRepository;

    @Autowired
    private CategoriaProdutoRepository categoriaProdutoRepository;

    public static void main(String[] args) {
        SpringApplication.run(ShopeeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        String[] categoriasLoja = {
                "Eletrônicos", "Hardware", "Moda e Vestuário", "Beleza e Cuidados Pessoais",
                "Casa e Decoração", "Automotivo", "Esportes e Lazer", "Brinquedos e Jogos",
                "Livros e Papelaria", "Ferramentas e Construção", "Alimentos e Bebidas",
                "Saúde e Bem-estar", "Joias e Acessórios", "Pet Shop", "Instrumentos Musicais",
                "Informática", "Bebês e Maternidade", "Jardinagem e Agricultura",
                "Viagens e Turismo", "Móveis e Eletrodomésticos", "Fotografia e Filmagem",
                "Segurança e Vigilância", "Energia Renovável", "Material Escolar",
                "Artigos de Cozinha", "Artigos Religiosos", "Artigos para Festa",
                "Artesanato", "Fitness e Academia"
        };

        for (String nome : categoriasLoja) {
            if (!categoriaLojaRepository.existsByNome(nome)) {
                categoriaLojaRepository.save(new CategoriaLoja(null, nome));
            }
        }

        String[] categoriasProduto = {
                "Smartphone", "Notebook", "Tablet", "Fone de Ouvido", "Monitor",
                "Placa de Vídeo", "Processador", "Memória RAM", "SSD", "HD Externo",
                "Blusa", "Calça Jeans", "Jaqueta", "Camiseta", "Vestido", "Shorts",
                "Saia", "Tênis", "Sandália", "Relógio", "Brinco", "Colar", "Shampoo",
                "Condicionador", "Perfume", "Creme Hidratante", "Secador de Cabelo",
                "Maquiagem", "Base", "Rímel", "Batom", "Tinta de Parede", "Furadeira",
                "Martelo", "Serra Elétrica", "Parafusadeira", "Parafuso", "Cimento",
                "Argamassa", "Piso", "Cabo HDMI", "Mouse", "Teclado", "Roteador",
                "Switch de Rede", "Controle de Videogame", "Console de Videogame",
                "Livro", "Caderno", "Caneta", "Lápis", "Mochila Escolar", "Papel Sulfite",
                "Cadeira Gamer", "Sofá", "Cama Box", "Geladeira", "Micro-ondas",
                "Fogão", "Máquina de Lavar", "Bicicleta", "Patins", "Skate", "Halteres",
                "Esteira", "Roupa de Natação", "Kit de Ferramentas", "Chave Inglesa",
                "Caixa de Som", "Projetor", "Camera Digital", "Drone", "Bola de Futebol",
                "Rede de Vôlei", "Violão", "Teclado Musical", "Guitarra", "Baixo",
                "Bateria", "Petiscos para Cachorro", "Ração para Gato", "Brinquedo para Pet",
                "Caixa de Areia para Gato", "Caminha para Pet", "Planta Ornamentais",
                "Ferramentas de Jardinagem", "Vasos de Plantas", "Sementes", "Adubo",
                "Carrinho de Bebê", "Mamadeira", "Fralda Descartável", "Bebedouro Automático",
                "Mala de Viagem", "Travesseiro", "Edredom", "Lençol", "Tupperware",
                "Panela", "Talheres", "Jogo de Pratos", "Copos de Vidro", "Vinho",
                "Cerveja Artesanal", "Chocolates", "Doces Finos", "Suplemento Alimentar",
                "Roupas Fitness", "Pesos", "Tapete de Yoga"
        };

        for (String nome : categoriasProduto) {
            if (!categoriaProdutoRepository.existsByNome(nome)) {
                categoriaProdutoRepository.save(new CategoriaProduto(null, nome));
            }
        }
        if (!statusRepository.existsById(1L)) {
            Status statusAtivo = new Status();
            statusAtivo.setId(1L);
            statusAtivo.setNomeStatus("Ativo");
            statusRepository.save(statusAtivo);
            System.out.println("Status 'Ativo' criado com ID 1.");
        } else {
            System.out.println("Status com ID 1 já existe.");
        }

        if (!statusRepository.existsById(2L)) {
            Status statusInativo = new Status();
            statusInativo.setId(2L);
            statusInativo.setNomeStatus("Inativo");
            statusRepository.save(statusInativo);
            System.out.println("Status 'Inativo' criado com ID 2.");
        } else {
            System.out.println("Status com ID 2 já existe.");
        }
        if (!usuarioRepository.existsByEmail("1@gmail.com")) {
            Usuario usuario = new Usuario();
            usuario.setNome("João Silva");
            usuario.setCpf("07894276995");
            usuario.setEmail("1@gmail.com");
            usuario.setTelefone("48984868321");
            usuario.setDataNascimento(LocalDate.of(2000, 5, 15));

            Endereco endereco = new Endereco();
            endereco.setCep("88064000");
            endereco.setRua("Rua Exemplo");
            endereco.setNumero("100");
            endereco.setCidade("São Paulo");
            endereco.setEstado("SP");
            endereco.setPais("Brasil");
            endereco.setComplemento("Apt 101");
            usuario.setEndereco(endereco);

            UsuarioAutenticar usuarioAutenticar = new UsuarioAutenticar();
            usuarioAutenticar.setUsername("1@gmail.com");
            usuarioAutenticar.setPasswordHash("Caruso123!");
            usuario.setUsuarioAutenticar(usuarioAutenticar);

            usuarioRepository.save(usuario);
            System.out.println("Usuário 'João Silva' criado.");
        } else {
            System.out.println("Usuário com email 'joao.silva@example.com' já existe.");
        }

        if (!usuarioRepository.existsByEmail("2@gmail.com")) {
            Usuario usuario = new Usuario();
            usuario.setNome("Maria Oliveira");
            usuario.setCpf("12345678901");
            usuario.setEmail("2@gmail.com");
            usuario.setTelefone("48984868322");
            usuario.setDataNascimento(LocalDate.of(1995, 8, 20));

            Endereco endereco = new Endereco();
            endereco.setCep("88064001");
            endereco.setRua("Rua Nova");
            endereco.setNumero("200");
            endereco.setCidade("Rio de Janeiro");
            endereco.setEstado("RJ");
            endereco.setPais("Brasil");
            endereco.setComplemento("Casa 2");
            usuario.setEndereco(endereco);

            UsuarioAutenticar usuarioAutenticar = new UsuarioAutenticar();
            usuarioAutenticar.setUsername("2@gmail.com");
            usuarioAutenticar.setPasswordHash("Caruso123!");
            usuario.setUsuarioAutenticar(usuarioAutenticar);

            usuarioRepository.save(usuario);
            System.out.println("Usuário 'Maria Oliveira' criado.");
        } else {
            System.out.println("Usuário com email 'maria.oliveira@example.com' já existe.");
        }

        if (!lojaRepository.existsById(1L)) {
            Loja loja = new Loja();
            loja.setNome("Loja Exemplo");
            loja.setUsuario(usuarioRepository.findByEmail("2@gmail.com"));
            loja.setCategoriaLoja(categoriaLojaRepository.findById(1L).orElse(null));
            loja = lojaRepository.save(loja);
            System.out.println("Loja 'Loja Exemplo' criada com ID 1.");
        } else {
            System.out.println("Loja com ID 1 já existe.");
        }

        for (int i = 1; i <= 10; i++) {
            if (!produtoRepository.existsById((long) i)) {
                Produto produto = new Produto();
                produto.setNome("Produto " + i);
                produto.setDescricao("Descrição do Produto " + i);
                produto.setPreco(100.0 + i * 10);
                produto.setImagem(
                        "https://images6.kabum.com.br/produtos/fotos/649716/notebook-acer-gamer-nitro-v15-anv15-51-7837-intel-core-i7-8gb-ram-ddr5-15-6-nvidia-rtx-3050-512gb-ssd-linux-preto-nh-qqdal-008_1730306499_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null)); // Supondo que a
                                                                                                   // categoria com ID 1
                                                                                                   // existe
                produto.setStatus(statusRepository.findById(1L).orElse(null)); // Supondo que o status 'Ativo' com ID 1
                                                                               // existe
                produtoRepository.save(produto);
                System.out.println("Produto 'Produto " + i + "' criado.");
            } else {
                System.out.println("Produto com ID " + i + " já existe.");
            }
        }

    }
}
