## 과제 설명
* 개발 프레임워크: 스프링 부트 JPA 2.3.1, maven 사용. DB는 H2 메모리 DB사용.
* 빌드/애플리케이션 구동 방법 
    - kakaopay2020 디렉토리 내의 터미널 상에서 "mvn clean package" (테스트 수행) 혹은 
 "mvn clean package -DskipTests"(테스트 생략) 명령어를 수행
    - 빌드가 성공 하면 "kakaopay2020/target/" 경로에 jar파일이 생성됨
    - "java -jar 'jar파일명'" 명령어로 애플리케이션을 실행함
        - 예를 들어 jar 파일명이 payment-0.0.1-SNAPSHOT.jar 인 경우 
        "java -jar payment-0.0.1-SNAPSHOT.jar" 수행

* 애플리케이션 실행 방법      
    - 결제 요청: 
      - 메소드: POST       
      - URL: http://localhost:8080/pay
      - 요청 헤더
        - Content-Type: application/json    
      - 요청 본문 예) 
        - {"cardNo":"1234123412","validPeriod":"0620","cvc":"999","installmentMonths":0,"paymentAmount":50000000,"vat":4545454}

    - 결제 취소 요청: 
      - 메소드: POST       
      - URL: http://localhost:8080/cancel
      - 요청 헤더
        - Content-Type: application/json     
      - 요청 본문 예) 
        - {"mgmtNo":"xg8Lf8ak0PCPPtHLZe9o","cancelAmount":1100,"vat":100}
      
    - 결제 데이터 조회: 
      - 메소드: POST       
      - URL: http://localhost:8080/find/{mgmtNo}
      - URL 예)
        - http://localhost:8080/find/xg8Lf8ak0PCPPtHLZe9o
    
* DB 접속 방법
    - 접속 주소: http://localhost:8080/h2-console/
    - Driver Class: org.h2.Driver
    - JDBC URL: jdbc:h2:mem:payment;
    - User Name: sa
    - Password: 입력하지 않음.        