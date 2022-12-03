
package JoshA.ServiceTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.ActiveProfiles;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.empty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;
import java.util.ArrayList;
import JoshA.DataBox.*;
//import JoshA.Repository.FruitRepository;
import JoshA.Service.AppService;
import JoshA.Config.RepoAndDataSourceTestConfig;


@SpringBootTest
@ActiveProfiles("test")
@Sql("schema_test.sql")
public class AppServiceTest{
    
    @Autowired
    AppService appServ;
    
    @Test
    void AppShouldGetAllFruit(){
        
        List<Apple> appleL = new ArrayList<>();
        List<Lemon> lemonL = new ArrayList<>();
        List<Orange> orangeL = new ArrayList<>();
        
        List<Fruit> fruitL = appServ.getAllFruit();
        
        for(Fruit fruitI:fruitL){
            
            appleL.add(fruitI.getApple());
            lemonL.add(fruitI.getLemon());
            orangeL.add(fruitI.getOrange());
        }
        
        assertThat(fruitL, hasSize(5));
        assertThat(appleL, hasSize(5));
        assertThat(lemonL, hasSize(5));
        assertThat(orangeL, hasSize(5));
    }
    
    @Test
    void AppShouldGetFruitById(){
        
        int numberOfFruit = 3;
        
        Fruit fruit = appServ.findByNumberOfFruit(numberOfFruit);
        
        Apple apple = fruit.getApple();
        Lemon lemon = fruit.getLemon();
        Orange orange = fruit.getOrange();
        
        assertThat(fruit.getNumberOfFruit(), equalTo(3));
        assertThat(fruit.getByte(), equalTo(new byte[]{0,0,0,57}));
        assertThat(apple.getNumberOfApple(), equalTo(3));
        assertThat(apple.getAmount(), equalTo(15));
        
        assertThat(lemon.getNumberOfLemon(), equalTo(3));
        assertThat(lemon.getAmount(), equalTo(9));
        
        assertThat(orange.getNumberOfOrange(), equalTo(3));
        assertThat(orange.getAmount(), equalTo(6));
    }
    
    @Test
    void AppShouldGetFruitByRange(){
        
        List<Apple> appleL = new ArrayList<>();
        List<Lemon> lemonL = new ArrayList<>();
        List<Orange> orangeL = new ArrayList<>();
        
        List<Fruit> fruitL = appServ.findByRange(0,2);
        
        for(Fruit fruitI:fruitL){
            
            appleL.add(fruitI.getApple());
            lemonL.add(fruitI.getLemon());
            orangeL.add(fruitI.getOrange());
        }
        
        assertThat(fruitL, hasSize(3));
        assertThat(appleL, hasSize(3));
        assertThat(lemonL, hasSize(3));
        assertThat(orangeL, hasSize(3));
    }
    
    @Test
    void AppShouldCreateFruit(){
        
        int numberOfFruit = 10;
        
        Fruit fruit= new Fruit();
        Apple apple = new Apple();
        Lemon lemon= new Lemon();
        Orange orange = new Orange();
            
        apple.setNumberOfApple(numberOfFruit);
        apple.setAmount(numberOfFruit*5);
            
        lemon.setNumberOfLemon(numberOfFruit);
        lemon.setAmount(numberOfFruit*3);
            
        orange.setNumberOfOrange(numberOfFruit);
        orange.setAmount(numberOfFruit*2);
            
        fruit.setNumberOfFruit(numberOfFruit);
        fruit.setByte(new byte[]{0,9,2,0});
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        fruit.setApple(apple);
        
        appServ.createFruit(fruit);
        
        Fruit fruitG = appServ.findByNumberOfFruit(numberOfFruit);
        
        Apple appleG = fruitG.getApple();
        Lemon lemonG = fruitG.getLemon();
        Orange orangeG = fruitG.getOrange();
        
        assertThat(fruitG.getNumberOfFruit(), equalTo(10));
        assertThat(fruitG.getByte(), equalTo(new byte[]{0,9,2,0}));
        
        assertThat(appleG.getNumberOfApple(), equalTo(10));
        assertThat(appleG.getAmount(), equalTo(50));
        
        assertThat(lemonG.getNumberOfLemon(), equalTo(10));
        assertThat(lemonG.getAmount(), equalTo(30));
        
        assertThat(orangeG.getNumberOfOrange(), equalTo(10));
        assertThat(orangeG.getAmount(), equalTo(20));
    }
    
    @Test
    void AppShouldUpdateFruit(){
        
        int numberOfFruit = 10;
        
        Fruit fruit= new Fruit();
        Apple apple = new Apple();
        Lemon lemon= new Lemon();
        Orange orange = new Orange();
            
        apple.setNumberOfApple(numberOfFruit);
        apple.setAmount(numberOfFruit*5);
            
        lemon.setNumberOfLemon(numberOfFruit);
        lemon.setAmount(numberOfFruit*3);
            
        orange.setNumberOfOrange(numberOfFruit);
        orange.setAmount(numberOfFruit*2);
            
        fruit.setNumberOfFruit(numberOfFruit);
        fruit.setByte(new byte[]{0,9,2,0});
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        fruit.setApple(apple);
        
        appServ.createFruit(fruit);
        
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        Fruit fruitA = new Fruit();
        
        Apple appleA = new Apple();
        appleA.setNumberOfApple(numberOfFruit);
        appleA.setAmount(numberOfFruit*50);
        
        Lemon lemonA = new Lemon();
        lemonA.setNumberOfLemon(numberOfFruit);
        lemonA.setAmount(numberOfFruit*30);
        
        Orange orangeA = new Orange();
        orangeA.setNumberOfOrange(numberOfFruit);
        orangeA.setAmount(numberOfFruit*20);
        
        fruitA.setNumberOfFruit(numberOfFruit);
        fruitA.setByte(new byte[]{0,9,2,0,0,9,2,0});
        fruitA.setLemon(lemonA);
        fruitA.setOrange(orangeA);
        fruitA.setApple(appleA);
        
        appServ.update(fruitA);
        
        
        Fruit fruitG = appServ.findByNumberOfFruit(numberOfFruit);
        
        Apple appleG = fruitG.getApple();
        Lemon lemonG = fruitG.getLemon();
        Orange orangeG = fruitG.getOrange();
        
        assertThat(fruitG.getNumberOfFruit(), equalTo(10));
        assertThat(fruitG.getByte(), equalTo(new byte[]{0,9,2,0,0,9,2,0}));
        
        assertThat(appleG.getNumberOfApple(), equalTo(10));
        assertThat(appleG.getAmount(), equalTo(500));
        
        assertThat(lemonG.getNumberOfLemon(), equalTo(10));
        assertThat(lemonG.getAmount(), equalTo(300));
        
        assertThat(orangeG.getNumberOfOrange(), equalTo(10));
        assertThat(orangeG.getAmount(), equalTo(200));
    }
    
    @Test
    void AppShouldDeleteFruitById(){
        
        int numberOfFruit = 10;
        
        Fruit fruit= new Fruit();
        Apple apple = new Apple();
        Lemon lemon= new Lemon();
        Orange orange = new Orange();
            
        apple.setNumberOfApple(numberOfFruit);
        apple.setAmount(numberOfFruit*5);
            
        lemon.setNumberOfLemon(numberOfFruit);
        lemon.setAmount(numberOfFruit*3);
            
        orange.setNumberOfOrange(numberOfFruit);
        orange.setAmount(numberOfFruit*2);
            
        fruit.setNumberOfFruit(numberOfFruit);
        fruit.setByte(new byte[]{0,9,2,0});
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        fruit.setApple(apple);
        
        appServ.createFruit(fruit);
        
        appServ.delete(10);
        
        List<Apple> appleL = new ArrayList<>();
        List<Lemon> lemonL = new ArrayList<>();
        List<Orange> orangeL = new ArrayList<>();
        
        List<Fruit> fruitL = appServ.getAllFruit();
        
        for(Fruit fruitI:fruitL){
            
            appleL.add(fruitI.getApple());
            lemonL.add(fruitI.getLemon());
            orangeL.add(fruitI.getOrange());
        }
        
        assertThat(fruitL, hasSize(5));
        assertThat(fruitL, not(contains(fruit)));
        
        assertThat(appleL, hasSize(5));
        assertThat(appleL, not(contains(fruit)));
        
        assertThat(orangeL, hasSize(5));
        assertThat(orangeL, not(contains(fruit)));
        
        assertThat(lemonL, hasSize(5));
        assertThat(lemonL, not(contains(fruit)));
    }
    
    @Test
    void AppShouldDeleteAllLemon(){
        
        appServ.deleteAll();
        
        List<Apple> appleL = new ArrayList<>();
        List<Lemon> lemonL = new ArrayList<>();
        List<Orange> orangeL = new ArrayList<>();
        
        List<Fruit> fruitL = appServ.getAllFruit();
        
        for(Fruit fruitI:fruitL){
            
            appleL.add(fruitI.getApple());
            lemonL.add(fruitI.getLemon());
            orangeL.add(fruitI.getOrange());
        }
        
        assertThat(fruitL, empty());
        assertThat(appleL, empty());
        assertThat(orangeL, empty());
        assertThat(lemonL, empty());
    }
        
}