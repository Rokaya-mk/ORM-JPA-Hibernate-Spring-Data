package ma.enset.jpaormtp;

import ma.enset.jpaormtp.entities.Product;
import ma.enset.jpaormtp.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class JpaOrmTpApplication {

    public static void main(String[] args) {
        SpringApplication.run(JpaOrmTpApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ProductRepository productRepository) {
        return args -> {
            Product product = Product.builder()
                    .name("Comp")
                    .price(100.00)
                    .quantity(2)
                    .build();

            productRepository.saveAndFlush(product);
            productRepository.saveAndFlush(
                    Product.builder()
                    .name("tablette")
                    .price(2000)
                    .quantity(2)
                    .build()
            )
            ;
             List<Product> products =  productRepository.findAll();
             products.forEach(pr -> {
               System.out.println(pr.toString());
             });
             //consulter un produit pa nom
            System.out.println(productRepository.findByNameIgnoreCase("comp"));

            System.out.println("Mettre a jour un produit");

          productRepository.findById(1L).ifPresent(pr -> {
                pr.setPrice(5000.0);
                pr.setQuantity(10);
                productRepository.save(pr);
            });

            productRepository.findAll()
                    .forEach(System.out::println);

            System.out.println("supprimer");

            productRepository.deleteById(1L);

            productRepository.findAll()
                    .forEach(System.out::println);


        };
    }
}
