// package com.projeto.shopee.config;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import com.projeto.shopee.entities.CategoriaLoja;
// import com.projeto.shopee.entities.CategoriaProduto;
// import com.projeto.shopee.repository.CategoriaLojaRepository;
// import com.projeto.shopee.repository.CategoriaProdutoRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.stereotype.Component;

// import java.io.IOException;
// import java.util.List;

// @Component
// public class DataLoader implements CommandLineRunner {

//     @Autowired
//     private CategoriaLojaRepository categoriaLojaRepository;

//     @Autowired
//     private CategoriaProdutoRepository categoriaProdutoRepository;

//     @Override
//     public void run(String... args) throws Exception {
//         ObjectMapper objectMapper = new ObjectMapper();
//         try {
//             CategoriasData categoriasData = objectMapper.readValue(
//                 new ClassPathResource("data.json").getFile(), 
//                 CategoriasData.class
//             );

//             for (String nome : categoriasData.getCategoriasLoja()) {
//                 categoriaLojaRepository.save(new CategoriaLoja(null, nome));
//             }

//             for (String nome : categoriasData.getCategoriasProduto()) {
//                 categoriaProdutoRepository.save(new CategoriaProduto(null, nome));
//             }

//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
//     private static class CategoriasData {
//         private List<String> categoriasLoja;
//         private List<String> categoriasProduto;

//         public List<String> getCategoriasLoja() {
//             return categoriasLoja;
//         }

//         public List<String> getCategoriasProduto() {
//             return categoriasProduto;
//         }
//     }
// }