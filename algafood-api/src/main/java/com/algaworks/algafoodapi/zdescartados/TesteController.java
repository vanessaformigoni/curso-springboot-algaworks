//package com.algaworks.algafoodapi.zdescartados;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Optional;
//
//import com.algaworks.algafoodapi.domain.Repository.CozinhaRepository;
//import com.algaworks.algafoodapi.domain.Repository.RestauranteRepository;
//import com.algaworks.algafoodapi.domain.model.Cozinha;
//import com.algaworks.algafoodapi.domain.model.Restaurante;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/teste")
//public class TesteController {
//
//    @Autowired
//    private CozinhaRepository cozinhaRepository;
//
//    @Autowired
//    private RestauranteRepository restauranteRepository;
//
//    @GetMapping("/cozinhas/por-nome") //Quando não especifica se é @PathVariable, passa pelo header.
//    public List<Cozinha> cozinhasPorNome(String nome) {
//        return cozinhaRepository.findByNomeContaining(nome);
//    }
//
//    @GetMapping("/cozinhas/unica-por-nome")
//    public Optional<Cozinha> cozinhaPorNome(String nome) {
//        return cozinhaRepository.findByNome(nome);
//    }
//
//    @GetMapping("/restaurantes/por-taxa-frete")
//    public List<Restaurante> restaurantesPorTaxaFrete(
//            BigDecimal taxaInicial, BigDecimal taxaFinal) {
//        return restauranteRepository.queryByTaxaFreteBetween(taxaInicial, taxaFinal);
//    }
//
////    @GetMapping("/restaurantes/por-nome")
////    public List<Restaurante> restaurantesPorTaxaFrete(
////            String nome, Long cozinhaId) {
////        return restauranteRepository.consultarPorNome(nome, cozinhaId);
////    }
//
//    @GetMapping("/restaurantes/por-nome-e-frete") // nome=a&taxaFreteInicial=9.00&taxaFreteFinal=42.00
//    public List<Restaurante> restaurantesPorNomeFrete(String nome,
//                                                      BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal) {
//        return restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal);
//    }
//
////    @GetMapping("/restaurantes/count-por-cozinha")
////    public int restaurantesCountPorCozinha(Long cozinhaId) {
////        return restauranteRepository.countByCozinhaId(cozinhaId);
////    }
//
//    @GetMapping("/restaurantes/com-frete-gratis") //Endpoint usando padrão Specifications (DDD) com SDJ
//    public  List<Restaurante> restaurantesComFreteGratis(String nome) {
//
//        return restauranteRepository.findComFreteGratis(nome);
//    }
//
//    @GetMapping("/restaurantes/primeiro")
//    public Optional<Restaurante> restaurantePrimeiro() {
//        return restauranteRepository.buscarPrimeiro();
//    }
//
//}
