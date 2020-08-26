package lunchrating;

import lunchrating.repository.DishRepository;
import lunchrating.repository.MenuRepository;
import lunchrating.repository.RestaurantRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "spring/spring-app.xml");

        System.out.println("Bean definition names: " + Arrays.toString(context.getBeanDefinitionNames()));

        RestaurantRepository restaurantRepository = context.getBean(RestaurantRepository.class);
        restaurantRepository.getAll().forEach(System.out::println);

        MenuRepository menuRepository = context.getBean(MenuRepository.class);
        menuRepository.getAll(100000).forEach(System.out::println);

        DishRepository dishRepository = context.getBean(DishRepository.class);
        dishRepository.getAll(100002).forEach(System.out::println);

        context.close();
    }


}
