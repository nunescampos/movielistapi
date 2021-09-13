# movielistapi

CSV to API
Web services which exposes RESTFUL endpoint for a CSV file using Spring Boot, Spring Data, Spring Security & Cassandra.

How to Run:
Make sure keyspace with name mykeyspace exist in your Aluysio Instance
Compile the project in Eclipse OR compile via JenkinsFile
Deploy it on a Tomcat web server
Enter localhost:8082 endpoints in your favorite REST client such as in RESTED or POSTMAN
To Run Test
Enter in the terminal mvn test

Authentication Logic on Endpoints
A a user in role User can perform GET at:

Retrieve all entries

~/blogpost/all

Retrieve all Unique intervaloanterior

~@GetMapping("/all/{intervaloanterior}"


Retrieve all Unique intervaloproximo

~@GetMapping("/all/{intervaloproximo}"

Paginated all entries.

~/blogpost/{page}

Get unique post using Id

~/blogpost/{id}


DELETE

~/blogpost/{id}

POST

~/blogpost

PUT

~/blogpost/{id}

Credentials
Use Basic Auth:
H2 
Modules
Spring Boot
Spring Security
Spring Cassandra
banco de dados Cassandra
Features
Testcases for security checks & REST endpoints
Pagination using CassandraPageRequest
Jenskins Script to compile & run test
Authentication layer using Sprring Security
Exception Handlers
Bean validation using Bean Validation 2.0 (JSR 38)
###Author Aluysio
