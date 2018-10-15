## Ethereum Trust Service

To increase the trust users have when making transactions, this service computes trust indicators based on transaction history. 

This indicator then could be displayed to users before they makes a transaction.

The indicator T works as follows: If A has transferred ETH to B, B has transferred ETH to C,
and C has transferred ETH to D then:

* T[A->B] = 1
* T[A->C] = 2
* T[A->D] = 3

The depth considered can be configured through src/main/resources/ethereum-trust-service.properties.

### Prerequisites
This service requires Java 8 and Maven.

### Building
```
mvn compile
```

### Running
```
# The service expects an up-to-date node on localhost:8485
# You could also use Infura or any other node by modifying the web3j bean as follows:
    <bean id="web3j" class="org.web3j.protocol.Web3j" factory-method="build">
    	<constructor-arg>
    		<bean id="web3jHttpService" class="org.web3j.protocol.http.HttpService">
    			<constructor-arg><value>${web3.node}</value></constructor-arg>
    		</bean>
    	</constructor-arg>
    </bean>
# and adding the following in ethereum-trust-service.properties:
web3.node = https://rinkeby.infura.io/v3/<apiKey>

    
# If not using infura:
geth --rpcapi personal,db,eth,net,web3 --rpc console

mvn jetty:run

```

### Obtaining Trust Level
```
curl -v 'http://localhost:8080/trustLevels/get.json?sourceAddress=A&destinationAddress=B'
```
