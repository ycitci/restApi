import com.thoughtworks.gauge.BeforeScenario;
import com.thoughtworks.gauge.Step;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import netscape.javascript.JSObject;
import org.assertj.core.api.Assertions;
import org.example.postMetod;
import org.example.postResponse;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.Request;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ApiBase {

    RequestSpecification spec;

    @BeforeScenario
    @Step("setup")
    public void setup(){
        spec = new RequestSpecBuilder()
                .setBaseUri("https://reqres.in/api")//base urli spec olarak kaydedip her seferinde yazmak zorunda kalmiyo
                .addFilters(Arrays.asList(new RequestLoggingFilter(),new ResponseLoggingFilter()))
                .build();
    }

    @Step("<url> urline get istegi at")
    public void getIstekMetod (String url){
        spec.queryParam("first_name","George"); //filtreleme islemi yapiliyor sadece fistnamei george olanı cagiriyo
        Response response = given(spec).when().contentType(ContentType.JSON)
                .get(url);
        Assert.assertEquals(200,response.statusCode());//status codu dogrulama islemi yapmak daha kolay
     String deneme = response.jsonPath().getJsonObject("data.first_name");//jsonobject ile responsedaki dataları string olarak döndürebiliyor
     Assert.assertEquals("George",deneme);
     response.prettyPrint();
    }

    @Step("<url> urline post istegi at")
    public void postIstekMetod (String url){
        JSONObject body = new JSONObject();//map gibi jsonobject metodu ile karsılastılar yapilabiliyo
        body.put("name","morpheus");
        body.put("job","leader");

        Response response = given(spec).when().contentType(ContentType.JSON).body(body).post(url);
        response.then().statusCode(201);

        List<String> list = response.jsonPath().getList("name");//donen responsdaki tüm isimleri liste atılabiliyor.
        Assert.assertEquals(body.get("name"),"morpheus");

        response.prettyPrint();
    }


    @Step("<url> urline pojo ile post atma")
    public void creatPojo (String url){
        postMetod postMetod = new postMetod("Yunus","Ormancı");
        postMetod postMetod1 = new postMetod("Ali","Deneme");
        Response response = given(spec)
                .contentType(ContentType.JSON)
                .body(postMetod)//pojo classındaki objeleri direkt jsona cevirip body olarak gönderiyo
                .when()
                .post(url);

        response.then()
                .statusCode(201);

        postResponse postResponse = response.as(org.example.postResponse.class);//response u postresponse classindaki degiskenlere aktariyor

        Assert.assertEquals("Yunus",postResponse.getName());//postresponse classındaki Stringlerle dogrulama islemi yapılıyor

        response.prettyPrint();
    }

    @Step("deneme")
    public void deneme (){
        System.out.println("JENKİNS DENEME!");
    }
}
