# Spring Boot + MongoDB 연동









## Dependency Version

```
Spring Boot version : v2.4.2
MongoDB version : v4.4.3
Java version : JDK 8
```








## Depedency Setting

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>

<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-mongodb</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-test</artifactId>
	<scope>test</scope>
</dependency>

<dependency>
	<groupId>io.projectreactor</groupId>
	<artifactId>reactor-test</artifactId>
	<scope>test</scope>
</dependency>
<dependency>
	<groupId>org.mongodb</groupId>
	<artifactId>mongo-java-driver</artifactId>
	<version>3.12.7</version>
</dependency>

<!-- springfox-swagger2 -->
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger-ui</artifactId>
	<version>2.9.2</version>
</dependency>
<!-- springfox-swagger-ui -->
<dependency>
	<groupId>io.springfox</groupId>
	<artifactId>springfox-swagger2</artifactId>
	<version>2.9.2</version>
</dependency>
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
	<optional>true</optional>
</dependency>
```









## application.properties

```properties
## MongoDB Config ##
spring.data.mongodb.uri=mongodb://localhost:27017/test
spring.data.mongodb.authentication-database=admin
spring.data.mongodb.database=test
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.username=[mongoDB 계정 id]
spring.data.mongodb.password=[mongoDB 계정 p/w]
spring.datasource.driverClassName=com.mongodb.Mongo
```









## MongoDB Configuration

```Java
@Configuration
public class MongoConfig extends MongoAutoConfiguration {
	@Value("${spring.data.mongodb.username}") 
    	// application.properties에서 정의한 MongoDB에 계정 아이디
	private String userName;

	@Value("${spring.data.mongodb.password}") 
    	// application.properties에서 정의한 MongoDB에 계정 비밀번호
	private String password;

	@Value("${spring.data.mongodb.database}") 
    	// application.properties에서 정의한 MongoDB에있는 데이터베이스
	private String database;
	
	@Value("${spring.data.mongodb.host}") 
    	// application.properties에서 정의한 MongoDB에있는 host
	private String host;
	
	@Value("${spring.data.mongodb.port}") 
    	// application.properties에서 정의한 MongoDB에있는 port 번호
	private String port;

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate((MongoClient) mongoClient(), database);
	}

	@Bean
	public MongoClient mongoClient() {
		MongoClient mongoClient = MongoClients.create(
	            new ConnectionString("mongodb://"+userName+":"+password+"@"+host+":"+port+"/"+database+"?authSource=admin"));
		return mongoClient;
	}
}
```









## DB Model

```Java
@Getter
@Setter
@NoArgsConstructor
@ToString
@Document(collection = "customers")
public class Customer {
	@Id
	private String _id;
	
	private String email;
	private String password;
	private String nickname;
	private boolean status;
	
	private List<Epub> epubList;
	
	public Customer(String email, String password, String nickname){
		this.email = email;
		this.password = password;
		this.nickname = nickname;
	}
}
```

###Lombok Annotation + MongoDB Document Annotation









## MongoDB Repository Class (extends MongoRepository) like JPA

```java
public interface CustomerRepository extends MongoRepository<Customer, String>{
	List<Customer> findByEmail(String email);
}
```











## Controller Example(Create & Read Method only)

```java
@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/customer")
	@ApiOperation(value = "getAllCustomers", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<List<Customer>> getAllCustomers() {
		return new ResponseEntity<>(customerRepository.findAll(),HttpStatus.OK);
	}
	
	@PostMapping("/customer")
	@ApiOperation(value = "addCustomer")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "customer", value = "회원 객체", required = true, dataType = "customer")
	})
	public ResponseEntity<Boolean> addCustomer(@RequestBody Customer customer) {
		try {
			if(customerRepository.save(customer) != null) {
				return new ResponseEntity<>(true, HttpStatus.OK);
			}
			else
				return new ResponseEntity<>(false, HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
```





** It took 8 hours to link...
