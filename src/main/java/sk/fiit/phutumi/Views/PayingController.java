package sk.fiit.phutumi.Views;

import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sk.fiit.phutumi.Repository.FoodRepository;
import sk.fiit.phutumi.Repository.OrderRepository;
import sk.fiit.phutumi.Repository.ShoppingCardRepository;
import sk.fiit.phutumi.models.Food;
import sk.fiit.phutumi.models.Menu;
import sk.fiit.phutumi.models.Order;
import sk.fiit.phutumi.models.ShoppingCart;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

@Controller
public class PayingController {
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    FoodRepository foodRepository;
    @Autowired
    ShoppingCardRepository shoppingCardRepository;
    @RequestMapping(value = "/phutumi/shoppingCart")
    public String paymentPage(@RequestParam("orderId") Long orderId, Model model){
        Order orderToPay = orderRepository.findById(orderId).orElse(null);
        List<ShoppingCart> shoppingCarts =  shoppingCardRepository.findOnlyFoodIdsByOrderId(orderId);
        List<Long> foodIds = new ArrayList<>();
        List<Food> foods;
        if (!shoppingCarts.isEmpty()) {
            for (ShoppingCart shoppingCart : shoppingCarts) {
                foodIds.add(shoppingCart.getFoodId());
            }

            foods = foodRepository.findFoodsByIdIn(foodIds);


        } else {
            foods = null;
        }
        model.addAttribute("orderId", orderId);
        model.addAttribute("foods", foods);
        return "paymentPage";
    }
    @RequestMapping(value = "/phutumi/payedOrder")
    public String payOrder(@RequestParam("orderId") Long orderId, Model model){
        Order orderToPay = orderRepository.findById(orderId).orElse(null);
        if(orderToPay.getProcessed()){
            model.addAttribute("text", "already processed");
        }
        else
        {
            model.addAttribute("text", "waiting!");
        }

        return "pageAfterPayment";
    }

    @RequestMapping(value = "/phutumi/orders")
    public String orders(Model model){
        List<Order> allOrders = orderRepository.findAll();
        List<Order> orders = new ArrayList<>();

        for(Order order: allOrders){
            if(!order.getProcessed()){
                orders.add(order);
            }
        }
        model.addAttribute("orders", orders);

        return "ordersPage";
    }

}
