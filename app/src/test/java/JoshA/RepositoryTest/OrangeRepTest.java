
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
import JoshA.Repository.OrangeRepository;
import JoshA.Repository.FruitRepository;
import JoshA.Config.RepoAndDataSourceTestConfig;

@Sql("schema_test.sql")
@ActiveProfiles("test")
@SpringBootTest
public class OrangeRepTest{
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private OrangeRepository orangeServ;
    
    @Autowired
    private FruitRepository fruitServ;
    
    
    private RowMapper<Orange> rowMapper=new RowMapper<Orange>() {

		@Override public Orange mapRow(ResultSet rs, int rowNum) throws SQLException {
			Orange orange=new Orange();
			
			orange.setNumberOfOrange(rs.getInt("ORANGE.numberOfOrange"));
			//
		    orange.setAmount(rs.getInt("ORANGE.amount"));
			
			return orange;
		}	
	};
    
    @Test
    void RepShouldGetAllOrange(){
        
        List<Orange> orangeL = orangeServ.getAllOrange();
        
        assertThat(orangeL, hasSize(5));
    }
    
    @Test
    void RepShouldGetOrangeById(){
        
        Orange orange = orangeServ.findByNumberOfOrange(3);
        
        assertThat(orange.getNumberOfOrange(), equalTo(3));
        
        assertThat(orange.getAmount(), equalTo(6));
    }
    
    @Test
    void RepShouldGetOrangeByRange(){
        
        List<Orange> orangeL = orangeServ.findByRange(0,2);
        
        assertThat(orangeL, hasSize(3));
    }
    
    @Test
    void RepShouldCreateOrange(){
        
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
        fruit.setOrange(orange);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        fruitServ.createFruit(fruit);
        orangeServ.createOrange(orange);
        
        Orange orangeG = jdbcTemplate.queryForObject("SELECT * FROM ORANGE WHERE numberOfOrange=?",new Object[]{10},rowMapper);
        
        assertThat(orangeG.getNumberOfOrange(), equalTo(10));
        
        assertThat(orangeG.getAmount(), equalTo(20));
    }
    
    @Test
    void RepShouldUpdateOrange(){
        
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
        fruit.setOrange(orange);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        fruitServ.createFruit(fruit);
        orangeServ.createOrange(orange);
        
        
        Orange orangeA = new Orange();
        orangeA.setNumberOfOrange(numberOfFruit);
        orangeA.setAmount(numberOfFruit*20);
        
        orangeServ.update(orangeA);
        
        Orange orangeG = jdbcTemplate.queryForObject("SELECT * FROM ORANGE WHERE numberOfOrange=?",new Object[]{10},rowMapper);
        
        assertThat(orangeG.getNumberOfOrange(), equalTo(10));
        
        assertThat(orangeG.getAmount(), equalTo(200));
    }
    
    @Test
    void RepShouldDeleteOrangeById(){
        
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
        fruit.setOrange(orange);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        fruitServ.createFruit(fruit);
        orangeServ.createOrange(orange);
        
        orangeServ.delete(10);
        
        List<Orange> orangeL = jdbcTemplate.query("SELECT * FROM ORANGE", rowMapper);
        
        assertThat(orangeL, hasSize(5));
        assertThat(orangeL, not(contains(orange)));
        
    }
    
    @Test
    void RepShouldDeleteAllOrange(){
        
        orangeServ.deleteAll();
        
        List<Orange> orangeL = jdbcTemplate.query("SELECT * FROM ORANGE", rowMapper);
        
        assertThat(orangeL, empty());
    }
}

