
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

import org.springframework.test.context.ActiveProfiles;
//import static org.mockito.Mockito.*;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import JoshA.DataBox.*;
import JoshA.Repository.AppleRepository;
import JoshA.Repository.FruitRepository;
import JoshA.Config.RepoAndDataSourceTestConfig;


@Sql("schema_test.sql")
@ActiveProfiles("test")
@SpringBootTest
public class AppleRepTest{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private AppleRepository appleServ;
    
    @Autowired
    private FruitRepository fruitServ;
    
    private RowMapper<Apple> rowMapper=new RowMapper<Apple>() {

		@Override public Apple mapRow(ResultSet rs, int rowNum) throws SQLException {
			Apple apple=new Apple();
			
			apple.setNumberOfApple(rs.getInt("APPLE.numberOfApple"));
			//
		    apple.setAmount(rs.getInt("APPLE.amount"));
			
			return apple;
		}	
	};
    
    @Test
    void RepShouldGetAllApple(){
        
        List<Apple> appleL = appleServ.getAllApple();
        
        assertThat(appleL, hasSize(5));
    }
    
    @Test
    void RepShouldGetAppleById(){
        
        Apple apple = appleServ.findByNumberOfApple(3);
        
        assertThat(apple.getNumberOfApple(), equalTo(3));
        
        assertThat(apple.getAmount(), equalTo(15));
    }
    
    @Test
    void RepShouldGetAppleByRange(){
        
        List<Apple> appleL = appleServ.findByRange(0,2);
        
        assertThat(appleL, hasSize(3));
    }
    
    @Test
    void RepShouldCreateApple(){
        
        int numberOfFruit = 10;
        
        Fruit fruit= new Fruit();
        Apple apple = new Apple();
        Orange orange= new Orange();
        Lemon lemon = new Lemon();
            
        apple.setNumberOfApple(numberOfFruit);
        apple.setAmount(numberOfFruit*5);
            
        lemon.setNumberOfLemon(numberOfFruit);
        lemon.setAmount(numberOfFruit*3);
            
        orange.setNumberOfOrange(numberOfFruit);
        orange.setAmount(numberOfFruit*2);
            
        fruit.setNumberOfFruit(numberOfFruit);
        fruit.setByte(new byte[]{0,9,2,0});
        fruit.setApple(apple);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        fruitServ.createFruit(fruit);
        appleServ.createApple(apple);
        
        Apple appleG = jdbcTemplate.queryForObject("SELECT * FROM APPLE WHERE numberOfApple=?",new Object[]{10},rowMapper);
        
        assertThat(appleG.getNumberOfApple(), equalTo(10));
        
        assertThat(appleG.getAmount(), equalTo(50));
    }
    
    @Test
    void RepShouldUpdateApple(){
        
        int numberOfFruit = 10;
        
        Fruit fruit= new Fruit();
        Apple apple = new Apple();
        Orange orange= new Orange();
        Lemon lemon = new Lemon();
            
        apple.setNumberOfApple(numberOfFruit);
        apple.setAmount(numberOfFruit*5);
            
        lemon.setNumberOfLemon(numberOfFruit);
        lemon.setAmount(numberOfFruit*3);
            
        orange.setNumberOfOrange(numberOfFruit);
        orange.setAmount(numberOfFruit*2);
            
        fruit.setNumberOfFruit(numberOfFruit);
        fruit.setByte(new byte[]{0,9,2,0});
        fruit.setApple(apple);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        fruitServ.createFruit(fruit);
        appleServ.createApple(apple);
        
        
        Apple appleA = new Apple();
        appleA.setNumberOfApple(numberOfFruit);
        appleA.setAmount(numberOfFruit*50);
        
        appleServ.update(appleA);
        
        Apple appleG = jdbcTemplate.queryForObject("SELECT * FROM APPLE WHERE numberOfApple=?",new Object[]{10},rowMapper);
        
        assertThat(appleG.getNumberOfApple(), equalTo(10));
        
        assertThat(appleG.getAmount(), equalTo(500));
    }
    
    @Test
    void RepShouldDeleteAppleById(){
        
        int numberOfFruit = 10;
        
        Fruit fruit= new Fruit();
        Apple apple = new Apple();
        Orange orange= new Orange();
        Lemon lemon = new Lemon();
            
        apple.setNumberOfApple(numberOfFruit);
        apple.setAmount(numberOfFruit*5);
            
        lemon.setNumberOfLemon(numberOfFruit);
        lemon.setAmount(numberOfFruit*3);
            
        orange.setNumberOfOrange(numberOfFruit);
        orange.setAmount(numberOfFruit*2);
            
        fruit.setNumberOfFruit(numberOfFruit);
        fruit.setByte(new byte[]{0,9,2,0});
        fruit.setApple(apple);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        fruitServ.createFruit(fruit);
        appleServ.createApple(apple);
        
        appleServ.delete(10);
        
        List<Apple> appleL = jdbcTemplate.query("SELECT * FROM APPLE", rowMapper);
        
        assertThat(appleL, hasSize(5));
        assertThat(appleL, not(contains(apple)));
        
    }
    
    @Test
    void RepShouldDeleteAllApple(){
        
        appleServ.deleteAll();
        
        List<Apple> appleL = jdbcTemplate.query("SELECT * FROM APPLE", rowMapper);
        
        assertThat(appleL, empty());
    }
}

