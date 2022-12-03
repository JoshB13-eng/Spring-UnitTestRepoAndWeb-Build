
package JoshA.ControlTest;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.context.annotation.Import;
import org.springframework.util.LinkedMultiValueMap;
import java.io.FileInputStream;
import java.nio.file.Path; 
import java.nio.file.Paths; 

import java.util.List;
import java.util.ArrayList;

import JoshA.Service.AppService;
import JoshA.Control.FruitController;
import JoshA.Config.RepoAndDataSourceTestConfig;
import JoshA.DataBox.*;

@WebMvcTest
@Import(RepoAndDataSourceTestConfig.class)
public class WebAppTest{
    
    @MockBean
    private AppService appServ;
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void shouldUploadFile()throws Exception{
        
        
        FileInputStream file = new FileInputStream("../FileBox/2DPicx.jpg");
        
        MockMultipartFile fileM = new MockMultipartFile("file",file);
        
        mockMvc.perform(multipart("/JoshFruit/UploadToDB").file(fileM)).andExpect(status().isCreated()).andDo(print());
    }
    
    @Test
    public void shouldCreateFruit(){
        
        int numberOfFruit = 5;
        
        try{
            
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
            fruit.setApple(apple);
            fruit.setLemon(lemon);
            fruit.setOrange(orange);
            
            
            mockMvc.perform(post("/JoshFruit/Fruit/Create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(fruit)))
                .andExpect(status().isCreated())
                .andDo(print());
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    //I skipped update for brevity hence it is the same logic, I will apply
    
    @Test
    public void shouldGetAllFruit()throws Exception{
        
        List<Fruit> fruitL = new ArrayList<>();
        
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
        fruit.setApple(apple);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        //Fruit object
        numberOfFruit = 15;
        Fruit fruitA= new Fruit();
        Apple appleA = new Apple();
        Orange orangeA= new Orange();
        Lemon lemonA = new Lemon();
            
        appleA.setNumberOfApple(numberOfFruit);
        appleA.setAmount(numberOfFruit*5);
            
        lemonA.setNumberOfLemon(numberOfFruit);
        lemonA.setAmount(numberOfFruit*3);
            
        orangeA.setNumberOfOrange(numberOfFruit);
        orangeA.setAmount(numberOfFruit*2);
            
        fruitA.setNumberOfFruit(numberOfFruit);
        fruitA.setApple(appleA);
        fruitA.setLemon(lemonA);
        fruitA.setOrange(orangeA);
        //Another Fruit object
        fruitL.add(fruit);
        fruitL.add(fruitA);
        //Fruit List
        when(appServ.getAllFruit()).thenReturn(fruitL);
        
        mockMvc.perform(get("/JoshFruit/Fruit"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(fruitL.size()))
                .andDo(print());
    }
    
    @Test
    public void shouldGetFruitRange()throws Exception{
        
        List<Fruit> fruitL = new ArrayList<>();
        
        int offset=1; int limit=3;
        
        int numberOfFruit = offset;
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
        fruit.setApple(apple);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        numberOfFruit++;
        Fruit fruitA= new Fruit();
        Apple appleA = new Apple();
        Orange orangeA= new Orange();
        Lemon lemonA = new Lemon();
            
        appleA.setNumberOfApple(numberOfFruit);
        appleA.setAmount(numberOfFruit*5);
            
        lemonA.setNumberOfLemon(numberOfFruit);
        lemonA.setAmount(numberOfFruit*3);
            
        orangeA.setNumberOfOrange(numberOfFruit);
        orangeA.setAmount(numberOfFruit*2);
            
        fruitA.setNumberOfFruit(numberOfFruit);
        fruitA.setApple(appleA);
        fruitA.setLemon(lemonA);
        fruitA.setOrange(orangeA);
        
        numberOfFruit=limit;
        Fruit fruitB= new Fruit();
        Apple appleB = new Apple();
        Orange orangeB= new Orange();
        Lemon lemonB = new Lemon();
            
        appleB.setNumberOfApple(numberOfFruit);
        appleB.setAmount(numberOfFruit*5);
            
        lemonB.setNumberOfLemon(numberOfFruit);
        lemonB.setAmount(numberOfFruit*3);
            
        orangeB.setNumberOfOrange(numberOfFruit);
        orangeB.setAmount(numberOfFruit*2);
            
        fruitB.setNumberOfFruit(numberOfFruit);
        fruitB.setApple(appleB);
        fruitB.setLemon(lemonB);
        fruitB.setOrange(orangeB);
        
        fruitL.add(fruit);
        fruitL.add(fruitA);
        fruitL.add(fruitB);
        
        when(appServ.findByRange(offset,limit)).thenReturn(fruitL);
        
        mockMvc.perform(get("/JoshFruit/FruitXY/"+offset+"/"+limit))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()").value((fruitL.size())))
                .andDo(print());
    }
    
    @Test
    public void shouldGetFruitById()throws Exception{
        
        int numberOfFruit = 2;
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
        fruit.setApple(apple);
        fruit.setLemon(lemon);
        fruit.setOrange(orange);
        
        when(appServ.findByNumberOfFruit(numberOfFruit)).thenReturn(fruit);
        
        mockMvc.perform(get("/JoshFruit/FruitX/"+numberOfFruit))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.numberOfFruit").value(fruit.getNumberOfFruit()))
            .andExpect(jsonPath("$.orange.numberOfOrange").value(orange.getNumberOfOrange()))
            .andExpect(jsonPath("$.orange.amount").value(orange.getAmount()))
            .andExpect(jsonPath("$.apple.numberOfApple").value(apple.getNumberOfApple()))
            .andExpect(jsonPath("$.apple.amount").value(apple.getAmount()))
            .andExpect(jsonPath("$.lemon.numberOfLemon").value(lemon.getNumberOfLemon()))
            .andExpect(jsonPath("$.lemon.amount").value(lemon.getAmount()))
            .andDo(print());
    }
    
    @Test
    public void shouldDeleteFruitById()throws Exception{
        
        int numberOfFruit=2;
        
        doNothing().when(appServ).delete(numberOfFruit);
        
        mockMvc.perform(delete("/JoshFruit/Delete/"+numberOfFruit))
         .andExpect(status().isNoContent())
         .andDo(print());
    }
    
    @Test
    public void shouldDeleteAllFruit()throws Exception{
        
        doNothing().when(appServ).deleteAll();
        
        mockMvc.perform(delete("/JoshFruit/DeleteAll"))
         .andExpect(status().isNoContent())
         .andDo(print());
    }
}

//bash ./gradlew clean test --info