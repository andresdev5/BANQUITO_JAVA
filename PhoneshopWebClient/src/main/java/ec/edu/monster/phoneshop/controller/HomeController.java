package ec.edu.monster.phoneshop.controller;

import ec.edu.monster.phoneshop.common.ShoppingCartHolder;
import ec.edu.monster.phoneshop.dto.*;
import ec.edu.monster.phoneshop.service.*;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Scope("session")
public class HomeController {
    private final AuthService authService;
    private final MovementService movementService;
    private final TransactionService transactionService;
    private final PurchaseService purchaseService;
    private final ProductService productService;
    private final CreditService creditService;
    private final HttpSession session;
    private final ShoppingCartHolder shoppingCartHolder;

    @GetMapping("/")
    public String home(Model model, @RequestParam(name = "show-cart", defaultValue = "false") boolean showCart) {
        String host = (String) session.getAttribute("EUREKABANK_SERVER_IP");
        String port = (String) session.getAttribute("EUREKABANK_SERVER_PORT");

        List<ProductDto> products = productService.getProducts();
        model.addAttribute("products", products);
        model.addAttribute("apiServerUrl", String.format("http://%s:%s", host, port));
        model.addAttribute("showCart", showCart);

        return "index";
    }

    @GetMapping("/checkout")
    public String checkout(Model model, RedirectAttributes redirectAttributes) {
        if (shoppingCartHolder.getShoppingCart().getTotalProducts() == 0 && !model.containsAttribute("purchaseResult")) {
            model.addAttribute("ferror", "No hay productos en el carrito");
            return "redirect:/";
        }

        return "checkout";
    }

    @GetMapping("/edit-product/{id}")
    public String updateProduct(@PathVariable("id") UUID id, Model model) {
        ProductDto product = null;

        try {
            product = productService.getProduct(id);
        } catch (Exception e) {
            model.addAttribute("ferror", e.getMessage());
            return "redirect:/";
        }

        String host = (String) session.getAttribute("EUREKABANK_SERVER_IP");
        String port = (String) session.getAttribute("EUREKABANK_SERVER_PORT");
        model.addAttribute("product", product);
        model.addAttribute("apiServerUrl", String.format("http://%s:%s", host, port));
        return "edit-product";
    }

    @PostMapping("/edit-product")
    public String updatePhone(ProductDto phone, @RequestParam("image") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            if (!file.isEmpty()) {
                phone.setImageFile(file.getBytes());
            }

            productService.updateProduct(phone);

            ProductDto product = productService.getProduct(phone.getId());
            shoppingCartHolder.getShoppingCart().updateProduct(product);
            redirectAttributes.addFlashAttribute("fsuccess", "Producto actualizado con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ferror", e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/add-product")
    public String addProduct() {
        return "add-product";
    }

    @PostMapping("/add-product")
    public String addProduct(ProductDto product, @RequestParam("image") MultipartFile file, RedirectAttributes redirectAttributes) {
        try {
            if (!file.isEmpty()) {
                product.setImageFile(file.getBytes());
            }

            productService.createProduct(product);
            redirectAttributes.addFlashAttribute("fsuccess", "Producto agregado con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ferror", e.getMessage());
        }

        return "redirect:/";
    }

    @GetMapping("/shopping-cart/add/{id}")
    public ModelAndView addToCart(@PathVariable UUID id, @RequestParam(defaultValue = "false") boolean checkout, RedirectAttributes redirectAttributes) {
        try {
            ProductDto product = productService.getProduct(id);
            shoppingCartHolder.getShoppingCart().add(product);
            redirectAttributes.addFlashAttribute("fsuccess", "Carrito actualizado con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ferror", e.getMessage());
        }

        return new ModelAndView("redirect:/" + (checkout ? "checkout" : "?show-cart=true"));
    }

    @GetMapping("/shopping-cart/clear")
    public ModelAndView clearCart(RedirectAttributes redirectAttributes) {
        shoppingCartHolder.getShoppingCart().clear();
        redirectAttributes.addFlashAttribute("fsuccess", "Carrito limpiado con éxito");
        return new ModelAndView("redirect:/?show-cart=true");
    }

    @GetMapping("/shopping-cart/set/{id}")
    public ModelAndView setProductQuantity(@PathVariable UUID id, @RequestParam(defaultValue = "0") int quantity, RedirectAttributes redirectAttributes) {
        try {
            shoppingCartHolder.getShoppingCart().setProductQuantity(id, Math.max(0, quantity));
            redirectAttributes.addFlashAttribute("fsuccess", "Carrito actualizado con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ferror", e.getMessage());
        }

        return new ModelAndView("redirect:/?show-cart=true");
    }

    @GetMapping("/shopping-cart/remove/{id}")
    public ModelAndView removeFromCart(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int quantity,
            @RequestParam(defaultValue = "false") boolean checkout,
            RedirectAttributes redirectAttributes) {
        try {
            shoppingCartHolder.getShoppingCart().remove(id, Math.max(0, quantity));
            redirectAttributes.addFlashAttribute("fsuccess", "Carrito actualizado con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ferror", e.getMessage());
        }

        return new ModelAndView("redirect:/" + (checkout ? "checkout" : "?show-cart=true"));
    }

    @GetMapping("/delete-product/{id}")
    public String deleteProduct(@PathVariable("id") UUID id, RedirectAttributes redirectAttributes) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("fsuccess", "Producto eliminado con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ferror", e.getMessage());
        }

        return "redirect:/";
    }

    @PostMapping("/transaction")
    public String transaction(TransactionDto transaction, RedirectAttributes redirectAttributes) {
        try {
            transactionService.transfer(transaction);
            redirectAttributes.addFlashAttribute("fsuccess", "Transacción realizada con éxito");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ferror", e.getMessage());
        }

        return "redirect:/";
    }

    @PostMapping("/checkCredit")
    public String checkCredit(CheckCreditRequestDto checkCredit, RedirectAttributes redirectAttributes) {
        try {
            CheckCreditResultDto result = creditService.checkCredit(checkCredit);
            redirectAttributes.addFlashAttribute("creditMessage", result.getMessage());
            redirectAttributes.addFlashAttribute("creditMaxAmount", result.getMaxAmount());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ferror", e.getMessage());
        }

        return "redirect:/";
    }

    @PostMapping("/purchase")
    public String purchase(PurchaseRequestDto purchase, RedirectAttributes redirectAttributes) {
        try {
            PurchaseResponseDto result = purchaseService.purchase(purchase);

            if (result.getStatus() == ResponseStatus.ERROR) {
                redirectAttributes.addFlashAttribute("ferror", result.getMessage().isBlank() ? "Error al realizar la compra" : result.getMessage());
                return "redirect:/checkout";
            }

            shoppingCartHolder.getShoppingCart().clear();

            redirectAttributes.addFlashAttribute("fsuccess", "Compra realizada con éxito");
            redirectAttributes.addFlashAttribute("purchase", purchase);
            redirectAttributes.addFlashAttribute("purchaseResult", result);
            redirectAttributes.addFlashAttribute("installments", purchase.getMethod() == PurchaseMethod.CREDIT ? result.getCredit().getInstallments() : new ArrayList<>());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("ferror", e.getMessage());
        }

        return "redirect:/checkout";
    }
}
