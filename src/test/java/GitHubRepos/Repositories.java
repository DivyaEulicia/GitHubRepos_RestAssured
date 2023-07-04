package GitHubRepos;

import static io.restassured.RestAssured.*;
import io.restassured.path.json.JsonPath;
import static org.hamcrest.Matchers.*;

import java.util.Base64;

import org.json.simple.JSONObject;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class Repositories {
	String baseURL = "https://api.github.com";
	int autolinkID;
	String sha_value;
	String updated_sha;
	String base64Content;
	
	@Test(priority = 0)
	public void Create_Repo() {
		
		JSONObject json=new JSONObject();
		json.put("name","Hello");
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.body(json.toJSONString())
		.when()
		.post("/user/repos")
		.then()
		.statusCode(201)
		.log()
		.all();
		
	}
	
	@Test(priority = 1)
	public void Get_Repo() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.get("/repos/DivyaEulicia/Hello")
		.then()
		.statusCode(200)
		.log()
		.all();
	}
	
	@Test(priority = 2)
	public void Update_Repo() {
		
		JSONObject jon=new JSONObject();
		jon.put("name","Hi");
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.body(jon.toJSONString())
		.when()
		.patch("/repos/DivyaEulicia/Hello")
		.then()
		.statusCode(200)
		.log()
		.all();
		
	}
	
	@Test(priority = 3)
	public void Create_Fork() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.post("/repos/kanwarchalotra/AppiumFramework/forks")
		.then()
		.statusCode(202)
		.log()
		.all();
		
	}
	
	@Test
	public void List_Fork() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.get("/repos/kanwarchalotra/AppiumFramework/forks")
		.then()
		.statusCode(200)
		.log()
		.all();
		
	}
	
	@Test(priority = 4)
	public void List_Repos() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.get("/user/repos")
		.then()
		.statusCode(200)
		.log()
		.all();
		
	}
	
    @Test(priority = 5)
    public void List_Repo_Languages() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.get("/repos/DivyaEulicia/Hi/languages")
		.then()
		.statusCode(200)
		.log()
		.all();
		
    }
    
    @Test(priority = 6)
    public void List_Public_Repos() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.get("/repositories")
		.then()
		.statusCode(200)
		.log()
		.all();
		
    }
    
    @Test(priority = 7)
    public void Create_File() {
    	
    	String content = "bXkgbmV3IGZpbGUgY29dGVudHM=";
    	base64Content = Base64.getEncoder().encodeToString(content.getBytes());
    	
    	 JSONObject json = new JSONObject();
         json.put("message", "my commit message");
         JSONObject committer = new JSONObject();
         committer.put("name", "Monalisa Octocat");
         committer.put("email", "octocat@github.com");
         json.put("committer", committer);
         json.put("content", base64Content);        
         Response res = given().baseUri(baseURL)
                 .header("Authorization", "Bearer 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")
                 .contentType(ContentType.JSON)
                 .body(json.toJSONString())
                 .when()
                 .put("/repos/DivyaEulicia/Hi/contents/test.txt")
                 .then()
                 .log().all()
                 .extract().response();
         
        String jsonresponse=res.asString();
 		JsonPath jpath=new JsonPath(jsonresponse);
 		sha_value=jpath.get("content.sha").toString();	
         
    }
    
    @Test(priority = 8)
    public void Update_File() {
    	
    	JSONObject json = new JSONObject();
        json.put("message", "a new commit message");
        JSONObject committer = new JSONObject();
        committer.put("name", "Monalisa Octocat");
        committer.put("email", "octocat@github.com");
        json.put("committer", committer);
        json.put("content", base64Content);
        json.put("sha", sha_value);
        
        Response res = given().baseUri(baseURL)
                .header("Authorization", "Bearer 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")
                .contentType(ContentType.JSON)
                .body(json.toJSONString())
                .when()
                .put("/repos/DivyaEulicia/Hi/contents/test.txt")
                .then()
                .log().all()
                .extract().response();
        
        String jsonresponse=res.asString();
 		JsonPath jpath=new JsonPath(jsonresponse);
 		updated_sha=jpath.get("content.sha").toString();	
    	
    }
    
    @Test(priority = 9)
    public void Delete_File() {
    	

    	JSONObject json = new JSONObject();
        json.put("message", "a new commit message");
        JSONObject committer = new JSONObject();
        committer.put("name", "Monalisa Octocat");
        committer.put("email", "octocat@github.com");
        json.put("committer", committer);
        json.put("content", "bXkgbmV3IGZpbGUgY29dGVudHM=");
        json.put("sha", updated_sha);
        
        given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.delete("/repos/DivyaEulicia/Hi/contents/test.txt")
		.then()
		.log()
		.all();
        
    }
    
    @Test(priority = 10)
    public void List_Repo_Tags() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.get("/repos/DivyaEulicia/Hi/tags")
		.then()
		.statusCode(200)
		.log()
		.all();
		
    }
    
    @Test(priority = 11)
    public void Create_Autolink_Ref() {
    	
    	JSONObject json = new JSONObject();
        json.put("key_prefix", "TICKET-");
        json.put("url_template", "https://example.com/TICKET?query=<num>");
        json.put("is_alphanumeric", true);

    Response res=given().baseUri(baseURL)
    		.header("Authorization", "Bearer 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")
            .header("Content-Type", "application/json")
            .body(json.toJSONString())
            .post("/repos/DivyaEulicia/Hi/autolinks")
            .then()
            .statusCode(201)
            .log().all()
            .extract().response() ;
		
		String jsonresponse=res.asString();
		JsonPath jpath=new JsonPath(jsonresponse);
		autolinkID=jpath.get("id");		
		System.out.println(autolinkID);
		
    }
    
    @Test(priority = 12)
    public void Get_Repo_Topics() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.get("/repos/DivyaEulicia/Hi/topics")
		.then()
		.statusCode(200)
		.log()
		.all();
		
    }
    
    @Test(priority = 13)
    public void Get_Autolink_Ref() {
    	
    	given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.get("/repos/DivyaEulicia/Hi/autolinks/"+autolinkID+"")
		.then()
		.statusCode(200)
		.log()
		.all();
		
    }
    
    @Test(priority = 14)
    public void Delete_Autolink_Ref() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.delete("/repos/DivyaEulicia/Hi/autolinks/"+autolinkID+"")
		.then()
		.statusCode(204)
		.log()
		.all();
		
    }
    
    @Test(priority = 15)
    public void Replace_Repo_Topics() {
    	
    	String Body = "{\"names\":[\"hello\",\"atom\",\"electron\",\"api\"]}";
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.body(Body)
		.when()
		.put("/repos/DivyaEulicia/Hi/topics")
		.then()
		.statusCode(200)
		.log()
		.all();
		
    }
    
	@Test(priority=16)
	public void Delete_Repo() {
		
		given().baseUri(baseURL)
		.header("Authorization", "Bearer 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")
		.header("Content-Type","application/json")
		.delete("/repos/DivyaEulicia/Hi")
		.then()
		.statusCode(204)
		.log()
		.all();
		
	}
    
    

}
