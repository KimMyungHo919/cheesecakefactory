# 치즈케이크를 안전하고 빠르게! 치즈케이크 배달 앱

![image](https://github.com/user-attachments/assets/9de7035f-de34-4bf7-994a-a233dc24ae2d)

# CheesecakeFactory

2Factoring의 **CheesecakeFactory**는 치즈케이크 매장의 메뉴, 주문, 가게관리 기능을 지원하는 Spring Boot 기반 웹 애플리케이션 입니다.

---

## 🛠️ 치즈케이크팩토리 프로젝트 개요

- **목적**: 치즈케이크를 안전하게 배달할 수 있도록 메뉴와 주문 관리를 통해 고객에게 원활한 서비스 제공
- **목표**: 고객의 요청을 효율적으로 처리하고 데이터를 안전하게 관리
- **주요 특징**:
  - 메뉴, 주문, 매장, 고객 관리를 각각 도메인형식으로 독립적으로 구현
  - 예외 상황에 대한 글로벌 핸들러 적용
  - 데이터 무결성을 위한 Soft Delete 및 영속성 전이 활용

---

## 📂 프로젝트 구조

```
domain/
├── base/                     
├── exception/                
├── menu/                     
│   ├── controller/           
│   ├── dto/                  
│   ├── entity/              
│   ├── repository/           
│   └── service/              
├── order/                    
│   ├── controller/         
│   ├── dto/                 
│   ├── entity/              
│   ├── repository/           
│   └── service/         
├── store/            
│   ├── controller/     
│   ├── dto/                
│   ├── entity/              
│   ├── repository/        
│   └── service/           
├── user/             
│   ├── config/            
│   ├── controller/           
│   ├── dto/                
│   ├── entity/             
│   ├── repository/    
│   └── service/  
└── model/
```

---

## 🛠️ 사용 기술 스택

- **JAVA**: JDK 17
- **Spring Boot**: 3.3.5
- **MySQL**: Ver 8+
- **Gradle**: 빌드 및 의존성 관리
- **IntelliJ IDEA**: 통합 개발 환경(IDE)
- **Lombok**: 코드 간소화

---

## 🧩 주요 기능

### **1. 메뉴 (Menu)**
- **CRUD 기능**: 메뉴 생성, 조회, 수정, 삭제
- **DTO 기반 데이터 처리**: `MenuRequestDto`, `MenuResponseDto`로 요청 및 응답 관리
- **데이터 무결성 보장**: 메뉴 관련 데이터를 안전하게 관리

### **2. 주문 (Order)**
- **CRUD 기능**: 주문 생성, 상태 업데이트, 삭제
- **상태 관리**: 주문 상태를 별도로 업데이트하여 관리
- **유효성 검증**: 정확한 주문 데이터 처리

### **3. 가게 (Store)**
- **CRUD 기능**: 매장 정보 추가, 수정, 조회, 삭제
- **독립적 데이터 관리**: 매장 데이터를 안전하게 처리

### **4. 고객 (User)**
- **회원가입 및 로그인**: 고객 인증 및 계정 관리
- **비밀번호 암호화**: 안전한 데이터 저장을 위한 `PasswordEncoder` 활용
- **데이터 보호**: 민감한 정보는 조회되지 않도록 처리

### **5. 글로벌 예외 처리**
- **`GlobalExceptionHandler`**: 일관된 에러 메시지 반환
- **Soft Delete**: 데이터를 완전히 삭제하지 않고 `deleted` 상태로 관리
- **영속성 전이**: 관련 데이터가 함께 삭제되도록 설정

---

## 🔧 설정

### **데이터베이스**
`application.properties` 파일에서 데이터베이스 연결 정보를 설정합니다:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/cheesecakefactory
spring.datasource.username=your_username
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
```

---

## 📘 API 명세서 및 ERD

- **API 명세서**: [API 문서 링크](https://teamsparta.notion.site/1492dc3ef5148138b2d1d9d5165b441f?v=1492dc3ef5148176aef1000c51090665)
- **ERD 설계**: [ERD 이미지 링크](https://www.erdcloud.com/d/rSDjwR5hiuZoh3kFo)

---

## 🖥️ 와이어프레임 및 시연 영상

- **프로토타입**: [프로토타입 링크](https://miro.com/welcomeonboard/QisrNDVVdVk3SVU5QnFlU3lGQ2RSczlVLzFQZGk0WTU2dmVUa[…]ZTHFsMXp4ckdGb21KL25JVXpHUHJLaFQhZQ==?share_link_id=462330736574)
- **시연 영상**: [시연 영상 링크]()

---

## 🧑‍🤝‍🧑 팀 구성 및 역할

| 이름  | 역할 | 주요 업무                                                                                                             |
|-------|------|-------------------------------------------------------------------------------------------------------------------|
| 김명호 | 팀장 | 회원가입 부분 구현 , 로그인 부분 구현, 발표 영상제작                                                                                   |
| 조현지 | 팀원 | Global Exception Handler 구현, Error, Success message 공통화, 메뉴 CRUD 구현, 주문 CRUD 구현, 리팩토링,각 부분 enum 추가, PPT 제작, SA 작성 |
| 양혁진 | 팀원 | 와이어프레임 작성, 가게부분 구현 및 수정, 리드미 작성 및 수정                                                                              |
| 장대산 | 팀원 | 리뷰 부분 구현 및 수정, 발표 담당                                                                           |

---

## 💡 완성 소감

- **김명호**: 좋은 팀원분들과 좋은 경험했습니다. 스스로 부족한점도 느끼고 더 열심히 해야겠다는 생각이 드네요! 어디선가 다시 만나요 ! 화이팅 !
- **조현지**: 이번 과제가 많이 어렵고 힘들었지만 다같이 마무리 할 수 있어서 좋았습니다! 피드백을 통해 이전까지 했던 방식 중 수정해야 할 점들을 찾을 수 있었고 수정해 나가면서 많이 배울 수 있었습니다. 다음 조에서 더 성장해서 다시 볼 수 있으면 좋겠습니다.
- **양혁진**: 좋은 팀원들을 만나 도움을 많이 받았습니다.팀 과제가 처음이라 너무 어려워서 공부하면서 했습니다. 실력을 더 키워야겠다는 생각이 많이 듭니다.  더 노력하겠습니다 감사합니다
- **장대산**: 저희 팀원분들이 너무 고생많으셨고 감사했습니다! 다음에 팀으로 만난다면 더 잘해보고싶습니다!
