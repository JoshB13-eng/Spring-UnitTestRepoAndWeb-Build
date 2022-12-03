

package JoshA.Control;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.InputStreamResource;

import java.io.File; 
import java.io.FileOutputStream; 
import java.io.FileInputStream; 
import java.io.IOException;

import java.util.Map;
import java.util.HashMap;
import java.util.List;

import JoshA.DataBox.Fruit;
import JoshA.DataBox.Apple;
import JoshA.Service.AppService;
import JoshA.Repository.AppleRepository;

@RestController
@RequestMapping(path = "/JoshFruit")
public class FruitController{
    //
    public static String Client;
    public  byte[] fileByt;
    
    @Autowired
    private AppService appServe;
    
    @Autowired
    private AppleRepository appleServe;
    
    @PostMapping(value="/UploadToDB", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    //
    public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file) throws IOException{
        //
        fileByt = file.getBytes();
        
        return new ResponseEntity<>("File is upload successfully", HttpStatus.CREATED);
    } 
    
    @PostMapping(path = "/Fruit/Create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> createFruit(@RequestBody Fruit fruitp){
        
        fruitp.setByte(fileByt);
        
        appServe.createFruit(fruitp);
        
        return new ResponseEntity<>("\nFruit is created successfully\n", HttpStatus.CREATED);
    }
    
    @PutMapping(path = "/Fruit/Update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Object> update(@RequestBody Fruit fruitp){
        
        fruitp.setByte(fileByt);
        
        appServe.update(fruitp);
        
        String str = "Fruit Database has been sucessfully updated\n";
        
        return new ResponseEntity<>(str, HttpStatus.OK);
    }
    
    @GetMapping(path = "/Fruit", produces = "application/json")
    public ResponseEntity<Object> getFruit(){
        
        return new ResponseEntity<>(appServe.getAllFruit(), HttpStatus.OK);
    }
    
    @GetMapping(path = "/FruitXY/{offset}/{limit}", produces = "application/json")
    public ResponseEntity<Object> getFruit(@PathVariable("offset") Integer offset,@PathVariable("limit") Integer limit){
        
        return new ResponseEntity<>(appServe.findByRange(offset,limit), HttpStatus.OK);
    }
    
    @GetMapping(path = "/FruitX/{NumberOfFruit}", produces = "application/json")
    public ResponseEntity<Object> getFruitX(@PathVariable("NumberOfFruit") Integer NumberOfFruit){
        
        return new ResponseEntity<>(appServe.findByNumberOfFruit(NumberOfFruit), HttpStatus.OK);
    }
    
    @DeleteMapping(path = "/Delete/{NumberOfFruit}")
    public ResponseEntity<Object> deleteFruit(@PathVariable("NumberOfFruit") Integer NumberOfFruit){
        
        appServe.delete(NumberOfFruit);
        
        return new ResponseEntity<>("\nIndex "+NumberOfFruit+" NumberOfFruit data/FruitRecord has been sucessfully deleted", HttpStatus.NO_CONTENT);
    }
    
    @DeleteMapping(path = "/DeleteAll")
    public ResponseEntity<Object> deleteAll(){
        
        appServe.deleteAll();
        
        return new ResponseEntity<>("\nAll DataBase records has been sucessfully deleted", HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(path = "/Exit")
    public ResponseEntity<Object> Exit(){
        
        return new ResponseEntity<>("\nShutting down server...\n", HttpStatus.OK);
    }
    
}



//To upload File
//curl  -X POST  -i  -F "file=@/storage/internal/Codings/Java/Spring-Boot/Spring-JdbcMySql-Build/FileBox/2DPicx.jpg" -F "additional_parm=param2" http://localhost:8080/JoshFruit/UploadToDB

//To create a fruit record to database
//curl -H "Content-Type: application/json" -X POST -d '{"numberOfFruit":25,"orange":{"numberOfOrange":25,"amount":2500},"apple":{"numberOfApple":25,"amount":12500},"lemon":{"numberOfLemon":25,"amount":7500}}' http://localhost:8080/JoshFruit/Fruit/Create

//To update fruit record in database
//curl -X PUT -d '{"numberOfFruit":1,"orange":{"numberOfOrange":1,"amount":1500},"apple":{"numberOfApple":1,"amount":7500},"lemon":{"numberOfLemon":1,"amount":4500}}' -H "Content-Type: application/json" http://localhost:8080/JoshFruit/Fruit/Update

//To update fruit record in database
//curl -X PUT -d '{"numberOfFruit":2,"orange":{"numberOfOrange":2,"amount":2500},"apple":{"numberOfApple":2,"amount":12500},"lemon":{"numberOfLemon":2,"amount":7500}}' -H "Content-Type: application/json" http://localhost:8080/JoshFruit/Fruit/Update

//To get All Records/FruitDatas from database
//curl http://localhost:8080/JoshFruit/Fruit

//To get a specified Record of FRUIT table from database
//curl http://localhost:8080/JoshFruit/FruitX/1

//To get another specified Record of FRUIT table from database
//curl http://localhost:8080/JoshFruit/FruitX/2

//To get a specified Range of Record of FRUIT table from database.
//curl http://localhost:8080/JoshFruit/FruitXY/1/2

//To delete a specified Record of FRUIT table from database
//curl -X "DELETE" http://localhost:8080/JoshFruit/Delete/0

//To delete ALL Records of FRUIT table from database
//curl -X "DELETE" http://localhost:8080/JoshFruit/DeleteAll

//To view database from browser console 
//http://localhost:8080/h2-console

//To exit program
//curl http://localhost:8080/JoshFruit/Exit

