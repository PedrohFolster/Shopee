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
            loja.setNome("Kabum");
            loja.setUsuario(usuarioRepository.findByEmail("2@gmail.com"));
            loja.setCategoriaLoja(categoriaLojaRepository.findById(1L).orElse(null));
            loja = lojaRepository.save(loja);
            System.out.println("Loja 'Kabum' criada com ID 1.");
        } else {
            System.out.println("Loja com ID 1 já existe.");
        }


            if (!produtoRepository.existsById((long) 1L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Notebook Gamer Acer Nitro V15 ANV15-51-7837 Intel Core I7, 8GB RAM, DDR5, Nvidia RTX 3050, 512GB SSD, 15.6\", Linux, Preto - NH.QQDAL.008");
                produto.setDescricao("SSD Kingston NV2 500 GB, M.2 2280 PCIe, NVMe, Leitura: 3500 MB/s e Gravação: 2100 MB" );
                produto.setPreco(1000);
                produto.setImagem(
                        "https://images6.kabum.com.br/produtos/fotos/649716/notebook-acer-gamer-nitro-v15-anv15-51-7837-intel-core-i7-8gb-ram-ddr5-15-6-nvidia-rtx-3050-512gb-ssd-linux-preto-nh-qqdal-008_1730306499_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 1 criado.");
            } else {
                System.out.println("Produto com ID 1 já existe.");
            }
            if (!produtoRepository.existsById((long) 2L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Processador Intel Core i7-12700K, 3.6GHz (5.0GHz Max Turbo), 12 Núcleos, 20 Threads, LGA 1700, Vídeo Integrado - BX8071512700K");
                produto.setDescricao("Descrição do Produto 2");
                produto.setPreco(2000);
                produto.setImagem(
                        "https://images8.kabum.com.br/produtos/fotos/241048/processador-intel-core-i7-12700k-cache-25mb-3-6ghz-5-0ghz-max-turbo-lga-1700-bx8071512700k_1634830258_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 2 criado");
            } else {
                System.out.println("Produto com ID 2 já existe.");
            }
            if (!produtoRepository.existsById((long) 3L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Notebook VAIO FE15, AMD Ryzen 5, 8GB, 512GB SSD, Placa de vídeo AMD, Tela 15,6\" FHD Antirreflexo, 60Hz, Linux, Cinza - VJFE59F11XB1911H");
                produto.setDescricao("Descrição do Produto 3");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images7.kabum.com.br/produtos/fotos/641847/notebook-vaio-fe15-amd-ryzen-5-8gb-512gb-ssd-placa-de-video-amd-tela-15-6-fhd-antirreflexo-60hz-linux-cinza-vjfe59f11xb1911h_1729882859_g.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 3 criado");
            } else {
                System.out.println("Produto com ID 3 já existe.");
            }
            if (!produtoRepository.existsById((long) 4L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 5L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Notebook Gamer Acer Nitro V15 ANV15-51-7837 Intel Core I7, 8GB RAM, DDR5, Nvidia RTX 3050, 512GB SSD, 15.6\", Linux, Preto - NH.QQDAL.008");
                produto.setDescricao("Descrição do Produto 1" );
                produto.setPreco(1000);
                produto.setImagem(
                        "https://images6.kabum.com.br/produtos/fotos/649716/notebook-acer-gamer-nitro-v15-anv15-51-7837-intel-core-i7-8gb-ram-ddr5-15-6-nvidia-rtx-3050-512gb-ssd-linux-preto-nh-qqdal-008_1730306499_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 1 criado.");
            } else {
                System.out.println("Produto com ID 1 já existe.");
            }
            if (!produtoRepository.existsById((long) 6L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Processador Intel Core i7-12700K, 3.6GHz (5.0GHz Max Turbo), 12 Núcleos, 20 Threads, LGA 1700, Vídeo Integrado - BX8071512700K");
                produto.setDescricao("Descrição do Produto 2");
                produto.setPreco(2000);
                produto.setImagem(
                        "https://images8.kabum.com.br/produtos/fotos/241048/processador-intel-core-i7-12700k-cache-25mb-3-6ghz-5-0ghz-max-turbo-lga-1700-bx8071512700k_1634830258_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 2 criado");
            } else {
                System.out.println("Produto com ID 2 já existe.");
            }
            if (!produtoRepository.existsById((long) 7L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Notebook VAIO FE15, AMD Ryzen 5, 8GB, 512GB SSD, Placa de vídeo AMD, Tela 15,6\" FHD Antirreflexo, 60Hz, Linux, Cinza - VJFE59F11XB1911H");
                produto.setDescricao("Descrição do Produto 3");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images7.kabum.com.br/produtos/fotos/641847/notebook-vaio-fe15-amd-ryzen-5-8gb-512gb-ssd-placa-de-video-amd-tela-15-6-fhd-antirreflexo-60hz-linux-cinza-vjfe59f11xb1911h_1729882859_g.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 3 criado");
            } else {
                System.out.println("Produto com ID 3 já existe.");
            }
            if (!produtoRepository.existsById((long) 8L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 9L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Notebook Gamer Acer Nitro V15 ANV15-51-7837 Intel Core I7, 8GB RAM, DDR5, Nvidia RTX 3050, 512GB SSD, 15.6\", Linux, Preto - NH.QQDAL.008");
                produto.setDescricao("Descrição do Produto 1" );
                produto.setPreco(1000);
                produto.setImagem(
                        "https://images6.kabum.com.br/produtos/fotos/649716/notebook-acer-gamer-nitro-v15-anv15-51-7837-intel-core-i7-8gb-ram-ddr5-15-6-nvidia-rtx-3050-512gb-ssd-linux-preto-nh-qqdal-008_1730306499_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(1L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 1 criado.");
            } else {
                System.out.println("Produto com ID 1 já existe.");
            }
            if (!produtoRepository.existsById((long) 10L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Processador Intel Core i7-12700K, 3.6GHz (5.0GHz Max Turbo), 12 Núcleos, 20 Threads, LGA 1700, Vídeo Integrado - BX8071512700K");
                produto.setDescricao("Descrição do Produto 2");
                produto.setPreco(2000);
                produto.setImagem(
                        "https://images8.kabum.com.br/produtos/fotos/241048/processador-intel-core-i7-12700k-cache-25mb-3-6ghz-5-0ghz-max-turbo-lga-1700-bx8071512700k_1634830258_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(3L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 2 criado");
            } else {
                System.out.println("Produto com ID 2 já existe.");
            }
            if (!produtoRepository.existsById((long) 11L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Notebook VAIO FE15, AMD Ryzen 5, 8GB, 512GB SSD, Placa de vídeo AMD, Tela 15,6\" FHD Antirreflexo, 60Hz, Linux, Cinza - VJFE59F11XB1911H");
                produto.setDescricao("Descrição do Produto 3");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images7.kabum.com.br/produtos/fotos/641847/notebook-vaio-fe15-amd-ryzen-5-8gb-512gb-ssd-placa-de-video-amd-tela-15-6-fhd-antirreflexo-60hz-linux-cinza-vjfe59f11xb1911h_1729882859_g.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(3L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 3 criado");
            } else {
                System.out.println("Produto com ID 3 já existe.");
            }
            if (!produtoRepository.existsById((long) 12L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(3L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 13L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Notebook Gamer Acer Nitro V15 ANV15-51-7837 Intel Core I7, 8GB RAM, DDR5, Nvidia RTX 3050, 512GB SSD, 15.6\", Linux, Preto - NH.QQDAL.008");
                produto.setDescricao("Descrição do Produto 1" );
                produto.setPreco(1000);
                produto.setImagem(
                        "https://images6.kabum.com.br/produtos/fotos/649716/notebook-acer-gamer-nitro-v15-anv15-51-7837-intel-core-i7-8gb-ram-ddr5-15-6-nvidia-rtx-3050-512gb-ssd-linux-preto-nh-qqdal-008_1730306499_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(3L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 1 criado.");
            } else {
                System.out.println("Produto com ID 1 já existe.");
            }
            if (!produtoRepository.existsById((long) 14L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Processador Intel Core i7-12700K, 3.6GHz (5.0GHz Max Turbo), 12 Núcleos, 20 Threads, LGA 1700, Vídeo Integrado - BX8071512700K");
                produto.setDescricao("Descrição do Produto 2");
                produto.setPreco(2000);
                produto.setImagem(
                        "https://images8.kabum.com.br/produtos/fotos/241048/processador-intel-core-i7-12700k-cache-25mb-3-6ghz-5-0ghz-max-turbo-lga-1700-bx8071512700k_1634830258_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 2 criado");
            } else {
                System.out.println("Produto com ID 2 já existe.");
            }
            if (!produtoRepository.existsById((long) 15L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "Notebook VAIO FE15, AMD Ryzen 5, 8GB, 512GB SSD, Placa de vídeo AMD, Tela 15,6\" FHD Antirreflexo, 60Hz, Linux, Cinza - VJFE59F11XB1911H");
                produto.setDescricao("Descrição do Produto 3");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images7.kabum.com.br/produtos/fotos/641847/notebook-vaio-fe15-amd-ryzen-5-8gb-512gb-ssd-placa-de-video-amd-tela-15-6-fhd-antirreflexo-60hz-linux-cinza-vjfe59f11xb1911h_1729882859_g.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 3 criado");
            } else {
                System.out.println("Produto com ID 3 já existe.");
            }
            if (!produtoRepository.existsById((long) 16L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 17L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 18L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 19L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 20L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 21L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 22L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 23L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
            if (!produtoRepository.existsById((long) 24L)) {
                Produto produto = new Produto();
                produto.setNome(
                        "SSD Kingston NV2, 500 GB, M.2 2280, PCIe 4.0 x4, NVMe, Leitura: 3500 MB/s, Gravação: 2100 MB/s, Azul - SNV2S/500G");
                produto.setDescricao("Descrição do Produto 4");
                produto.setPreco(3000);
                produto.setImagem(
                        "https://images4.kabum.com.br/produtos/fotos/380744/ssd-kingston-nv2-500-gb-m-2-2280-pcie-nvme-leitura-2-100-mb-s-e-gravacao-1-700-mb-s-snv2s-500g_1666032961_m.jpg");
                produto.setEstoque(10);
                produto.setLoja(lojaRepository.findById(1L).orElse(null));
                produto.setCategoriaProduto(categoriaProdutoRepository.findById(2L).orElse(null));
                produto.setStatus(statusRepository.findById(1L).orElse(null));
                produtoRepository.save(produto);
                System.out.println("Produto 4 criado");
            } else {
                System.out.println("Produto com ID 4 já existe.");
            }
        }
}
