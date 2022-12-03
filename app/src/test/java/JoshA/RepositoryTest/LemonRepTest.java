
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
import JoshA.Repository.LemonRepository;
import JoshA.Repository.FruitRepository;
import JoshA.Config.RepoAndDataSourceTestConfig;


@Sql("schema_test.sql")
@ActiveProfiles("test")
@SpringBootTest
public class LemonRepTest{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private LemonRepository lemonServ;
    
    @Autowired
    private FruitRepository fruitServ;
    //LemonServiceImpl lemonServImp;
    
    private RowMapper<Lemon> rowMapper=new RowMapper<Lemon>() {

		@Override public Lemon mapRow(ResultSet rs, int rowNum) throws SQLException {
			Lemon lemon=new Lemon();
			
			lemon.setNumberOfLemon(rs.getInt("LEMON.numberOfLemon"));
			//
		    lemon.setAmount(rs.getInt("LEMON.amount"));
			
			return lemon;
		}	
	};
    
    @Test
    void RepShouldGetAllLemon(){
        
        List<Lemon> lemonL = lemonServ.getAllLemon();
        
        assertThat(lemonL, hasSize(5));
    }
    
    @Test
    void RepShouldGetLemonById(){
        
        Lemon lemon = lemonServ.findByNumberOfLemon(3);
        
        assertThat(lemon.getNumberOfLemon(), equalTo(3));
        
        assertThat(lemon.getAmount(), equalTo(9));
    }
    
    @Test
    void RepShouldGetLemonByRange(){
        
        List<Lemon> lemonL = lemonServ.findByRange(0,2);
        
        assertThat(lemonL, hasSize(3));
    }
    
    @Test
    void RepShouldCreateLemon(){
        
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
        fruit.setApple(apple);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        fruitServ.createFruit(fruit);
        lemonServ.createLemon(lemon);
        
        Lemon lemonG = jdbcTemplate.queryForObject("SELECT * FROM LEMON WHERE numberOfLemon=?",new Object[]{10},rowMapper);
        
        assertThat(lemonG.getNumberOfLemon(), equalTo(10));
        
        assertThat(lemonG.getAmount(), equalTo(30));
    }
    
    @Test
    void RepShouldUpdateLemon(){
        
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
        fruit.setApple(apple);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        fruitServ.createFruit(fruit);
        lemonServ.createLemon(lemon);
        
        
        Lemon lemonA = new Lemon();
        lemonA.setNumberOfLemon(numberOfFruit);
        lemonA.setAmount(numberOfFruit*30);
        
        lemonServ.update(lemonA);
        
        Lemon lemonG = jdbcTemplate.queryForObject("SELECT * FROM LEMON WHERE numberOfLemon=?",new Object[]{10},rowMapper);
        
        assertThat(lemonG.getNumberOfLemon(), equalTo(10));
        
        assertThat(lemonG.getAmount(), equalTo(300));
    }
    
    @Test
    void RepShouldDeleteLemonById(){
        
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
        fruit.setApple(apple);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        fruitServ.createFruit(fruit);
        lemonServ.createLemon(lemon);
        
        lemonServ.delete(10);
        
        List<Lemon> lemonL = jdbcTemplate.query("SELECT * FROM LEMON", rowMapper);
        
        assertThat(lemonL, hasSize(5));
        assertThat(lemonL, not(contains(lemon)));
        
    }
    
    @Test
    void RepShouldDeleteAllLemon(){
        
        lemonServ.deleteAll();
        
        List<Lemon> lemonL = jdbcTemplate.query("SELECT * FROM LEMON", rowMapper);
        
        assertThat(lemonL, empty());
    }
}

