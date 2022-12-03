
package JoshA.RepositoryTest;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.jdbc.Sql;
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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
//import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
//import static org.mockito.Mockito.*;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import JoshA.DataBox.*;
import JoshA.Repository.*;
import JoshA.Config.RepoAndDataSourceTestConfig;


@Sql("schema_test.sql")
@ActiveProfiles("test")
@SpringBootTest
public class FruitRepTest{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private FruitRepository fruitServ;  
    
    @Autowired
    private AppleRepository appleServ;  
    
    @Autowired
    private LemonRepository lemonServ;  
    
    @Autowired
    private OrangeRepository orangeServ;
    
    private RowMapper<Fruit> rowMapper=new RowMapper<Fruit>() {

		@Override public Fruit mapRow(ResultSet rs, int rowNum) throws SQLException {
			Fruit fruit=new Fruit();
			
			fruit.setNumberOfFruit(rs.getInt("FRUIT.numberOfFruit"));
			//
		    fruit.setByte(rs.getBytes("FRUIT.fileByt"));
			
			return fruit;
		}	
	};
    
    @Test
    void RepShouldGetAllFruit(){
        
        List<Fruit> fruitL = fruitServ.getAllFruit();
        
        assertThat(fruitL, hasSize(5));
    }
    
    @Test
    void RepShouldGetFruitById(){
        
        Fruit fruit = fruitServ.findByNumberOfFruit(3);
        
        assertThat(fruit.getNumberOfFruit(), equalTo(3));
        
        assertThat(fruit.getByte(), equalTo(new byte[]{0,0,0,57}));
    }
    
    @Test
    void RepShouldGetFruitByRange(){
        
        List<Fruit> fruitL = fruitServ.findByRange(0,2);
        
        assertThat(fruitL, hasSize(3));
    }
    
    @Test
    void RepShouldCreateFruit(){
        
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
        
        fruitServ.createFruit(fruit);
        
        Fruit fruitG = jdbcTemplate.queryForObject("SELECT * FROM FRUIT WHERE numberOfFruit=?",new Object[]{10},rowMapper);
        
        assertThat(fruitG.getNumberOfFruit(), equalTo(10));
        
        assertThat(fruitG.getByte(), equalTo(new byte[]{0,9,2,0}));
    }
    
    @Test
    void RepShouldUpdateFruit(){
        
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
        
        fruitServ.createFruit(fruit);
        
        
        Fruit fruitA = new Fruit();
        fruitA.setNumberOfFruit(numberOfFruit);
        fruitA.setByte(new byte[]{0,9,2,0,0,9,2,0});
        
        fruitServ.update(fruitA);
        
        Fruit fruitG = jdbcTemplate.queryForObject("SELECT * FROM FRUIT WHERE numberOfFruit=?",new Object[]{10},rowMapper);
        
        assertThat(fruitG.getNumberOfFruit(), equalTo(10));
        
        assertThat(fruitG.getByte(), equalTo(new byte[]{0,9,2,0,0,9,2,0}));
    }
    
    @Test
    void RepShouldDeleteFruitById(){
        
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
        
        fruitServ.createFruit(fruit);
        
        fruitServ.delete(10);
        
        List<Fruit> fruitL = jdbcTemplate.query("SELECT * FROM FRUIT", rowMapper);
        
        assertThat(fruitL, hasSize(5));
        assertThat(fruitL, not(contains(fruit)));
        
    }
    
    @Test
    void RepShouldDeleteAllFruit(){
        
        orangeServ.deleteAll();
        lemonServ.deleteAll();
        appleServ.deleteAll();
        fruitServ.deleteAll();
        
        List<Fruit> fruitL = jdbcTemplate.query("SELECT * FROM FRUIT", rowMapper);
        
        assertThat(fruitL, empty());
    }
}

