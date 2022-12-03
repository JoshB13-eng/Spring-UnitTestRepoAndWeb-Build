
package JoshA.Config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.context.annotation.Bean; 
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.beans.factory.annotation.Qualifier; 
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties; 
import org.springframework.jdbc.core.JdbcTemplate; 
import javax.sql.DataSource; 

import JoshA.Repository.*;

@TestConfiguration
public class RepoAndDataSourceTestConfig{
    
    @MockBean
    private FruitRepository fruitR;

    @MockBean
    private AppleRepository appleR;
    
    @MockBean
    private OrangeRepository orangeR;
    
    @MockBean
    private LemonRepository lemonR;
    
    @Bean
    public FruitRepository getFruitRep(){
        return fruitR;
    }
    
    @Bean
    public AppleRepository getAppleRep(){
        return appleR;
    }
    
    @Bean
    public OrangeRepository getOrangeRep(){
        return orangeR;
    }
    
    @Bean
    public LemonRepository getLemonRep(){
        return lemonR;
    }
    
}