package ec.edu.monster.phoneshop.service.impl;

import ec.edu.monster.phoneshop.dto.ProductDto;
import ec.edu.monster.phoneshop.entity.Product;
import ec.edu.monster.phoneshop.entity.Sale;
import ec.edu.monster.phoneshop.repository.ProductRepository;
import ec.edu.monster.phoneshop.repository.SaleDetailRepository;
import ec.edu.monster.phoneshop.repository.SaleRepository;
import ec.edu.monster.phoneshop.service.ProductService;
import ec.edu.monster.phoneshop.util.TextUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.imgscalr.Scalr;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final Logger logger = Logger.getLogger(ProductServiceImpl.class.getName());
    private final ProductRepository productRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final SaleRepository saleRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<ProductDto> getProducts() {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        return productRepository.findAll(sort)
                .stream()
                .map(product -> modelMapper.map(product, ProductDto.class))
                .toList();
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto product) {
        Product entity = Product.builder()
                .name(product.getName())
                .price(product.getPrice())
                .createdAt(LocalDateTime.now())
                .build();

        Product saved = productRepository.save(entity);

        if (product.getImageFile() != null) {
            saved.setImage(uploadProductImage(saved, product.getImageFile()));
        } else {
            saved.setImage(uploadDefaultProductImage(saved));
        }

        productRepository.save(saved);
        return modelMapper.map(saved, ProductDto.class);
    }

    @Override
    public ProductDto updateProduct(ProductDto product) {
        Product current = productRepository.findById(product.getId()).orElse(null);

        if (current != null) {
            current.setName(product.getName() != null ? product.getName() : current.getName());
            current.setPrice(product.getPrice() != null ? product.getPrice() : current.getPrice());

            if (product.getImageFile() != null && product.getImageFile().length > 0) {
                String oldImage = current.getImage();
                current.setImage(uploadProductImage(current, product.getImageFile()));

                if (oldImage != null) {
                    Path path = Paths.get(oldImage.substring(1));
                    try {
                        Files.deleteIfExists(path);
                    } catch (IOException e) {
                        logger.severe("Error al eliminar la imagen del producto: " + e.getMessage());
                        throw new ResponseStatusException(
                                HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la imagen del producto");
                    }
                }
            }

            productRepository.save(current);
            return product;
        }

        return modelMapper.map(product, ProductDto.class);
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) throws Exception {
        Product product = productRepository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Producto no encontrado"));

        List<Sale> sales = saleDetailRepository.getAllSalesByProductId(id);
        saleDetailRepository.deleteAllByProductId(id);
        saleRepository.deleteAll(sales);

        if (product.getImage() != null) {
            Path path = Paths.get(product.getImage().substring(1));
            Files.deleteIfExists(path);
        }

        productRepository.deleteById(id);
    }

    @Override
    public ProductDto getProduct(UUID id) {
        return productRepository.findById(id)
                .map(product -> modelMapper.map(product, ProductDto.class))
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Producto no encontrado"));
    }

    private String uploadProductImage(Product product, byte[] image) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String filename = String.format("%s-%s.jpg", product.getId(), timestamp);
        Path path = Path.of("uploads", "images/products", filename);

        try {
            int width = 500;
            int height = 650;
            BufferedImage inputImage = ImageIO.read(new ByteArrayInputStream(image));
            Scalr.Mode mode = (double) width / (double) height >= (double) inputImage.getWidth()
                    / (double) inputImage.getHeight() ? Scalr.Mode.FIT_TO_WIDTH
                    : Scalr.Mode.FIT_TO_HEIGHT;

            BufferedImage bufferedImage = Scalr.resize(inputImage, Scalr.Method.QUALITY, mode, width, height);

            int x = 0;
            int y = 0;

            if (mode == Scalr.Mode.FIT_TO_WIDTH) {
                y = (bufferedImage.getHeight() - height) / 2;
            } else if (mode == Scalr.Mode.FIT_TO_HEIGHT) {
                x = (bufferedImage.getWidth() - width) / 2;
            }

            bufferedImage = Scalr.crop(bufferedImage, x, y, width, height);
            File outputFile = path.toFile();
            Files.createDirectories(path.getParent());
            ImageIO.write(bufferedImage, "jpg", outputFile);

            return "/" + path.toString().replace("\\", "/");
        } catch (IOException e) {
            logger.severe("Error al procesar la imagen del libro: " + e.getMessage());
            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al procesar la imagen del producto");
        }
    }

    private String uploadDefaultProductImage(Product product) {
        try {
            String placeholder = String.format("https://placehold.co/500x650/ced6e0/57606f/jpg?text=%s",
                    UriUtils.encodePath(TextUtils.wrapText(product.getName(), 20, "\n"), "UTF-8"));
            URL url = new URL(placeholder);
            InputStream is = url.openStream();
            byte[] imageBytes = IOUtils.toByteArray(is);

            return uploadProductImage(product, imageBytes);
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
            logger.severe("Error al cargar la imagen por defecto: " + ex.getMessage());

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar la imagen del producto");
        }
    }
}
