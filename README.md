# SmartCart - Backend

<br><br>
<div align="center">
  <img src="https://github.com/user-attachments/assets/8d7b601b-3f10-4bdd-85ab-f2e08e56193b" width="200" height="200" />
  <h3 align="center">Smart Cart&nbsp;&nbsp;🛒</h3>
  <p align="center">
    온/오프라인 마트 최저가 비교 서비스<br>
    <a href="https://github.com/KMS-SmartCart"><strong>Explore the team »</strong></a>
  </p>
</div>
<br>

<details open>
  <summary><strong>&nbsp;📖&nbsp;목차</strong></summary>

1. &nbsp;&nbsp;[🔍 Introduction](#-introduction)
2. &nbsp;&nbsp;[📹 Demo](#-demo)
4. &nbsp;&nbsp;[💻 Architecture](#-architecture)
5. &nbsp;&nbsp;[💡 Tech Stack](#-tech-stack)
6. &nbsp;&nbsp;[🗂️ Database](#%EF%B8%8F-database)
7. &nbsp;&nbsp;[📗 API](#-api)
8. &nbsp;&nbsp;[🤝 Git Convention](#-git-convention)
9. &nbsp;&nbsp;[📂 Package Convention](#-package-convention)
10. &nbsp;&nbsp;[👨‍👩‍👧‍👧 Team](#-team)
</details>
<br>


## 🔍 Introduction

### Description
물가가 치솟는 요즘, 장보기가 부담스럽진 않으신가요?<br>
오프라인 매장의 "특가" 상품이 정말 최저가인지, 소비자의 의문을 해결하고자 합니다.<br>
스마트카트가 오프라인과 온라인의 가격을 실시간으로 비교해 현명한 소비를 도와드립니다.

### Main Feature
- PWA 지원&nbsp;:&nbsp;&nbsp;웹 내에서 간편하게 앱을 설치할 수 있도록 안내
- 소셜 로그인&nbsp;:&nbsp;&nbsp;Google, Naver, Kakao 계정으로 간편하게 로그인
- 체크리스트&nbsp;:&nbsp;&nbsp;장 볼 항목을 손쉽게 관리
- 스마트 렌즈&nbsp;:&nbsp;&nbsp;오프라인 매장의 가격표 촬영 후, 온라인 최저가와 비교 확인
    - 1단계&nbsp;:&nbsp;&nbsp;ChatGPT Vision API - 촬영한 가격표의 '상품명, 용량, 가격' 추출
    - 2단계&nbsp;:&nbsp;&nbsp;Naver Shopping API - 해당 상품의 온라인 최저가 목록 3곳 제공
    - 3단계&nbsp;:&nbsp;&nbsp;ChatGPT Text API - 최저가 목록의 불필요한 내용을 깔끔히 정리
- 장바구니&nbsp;:&nbsp;&nbsp;원하는 상품을 선택하여 담고, 총 결제 금액 및 절약 금액을 확인
- 내 정보&nbsp;:&nbsp;&nbsp;스마트카트로 아낀 누적 절약 금액 확인
<br>


## 📹 Demo
작성 중...
<br><br>


## 💻 Architecture

### System
![smartcart_architecture drawio](https://github.com/user-attachments/assets/92fe928c-42a9-4f15-b213-6ce955db42a9)

### Network
![smartcart_network_architecture drawio](https://github.com/user-attachments/assets/a4faeb21-c58f-477a-86ff-910c570f78ea)

<br>


## 💡 Tech Stack
Frontend|Backend|Security&nbsp;&nbsp;&&nbsp;&nbsp;DB|Deployment|Other|
|:------:|:------:|:------:|:------:|:------:|
|<img src="https://img.shields.io/badge/React-61DBFB?style=flat-square&logo=React&logoColor=white"/></a><br><img src="https://img.shields.io/badge/JavaScript-F7DF1F?style=flat-square&logo=JavaScript&logoColor=white"/></a>|<img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=flat-square&logo=Spring Boot&logoColor=white"/><br><img src="https://img.shields.io/badge/Java-007396?style=flat-square&logo=Java&logoColor=white"/><br><img src="https://img.shields.io/badge/ChatGPT API-74AA9C?style=flat-square&logo=OpenAI&logoColor=white"/></a><br><img src="https://img.shields.io/badge/Naver API-03C75A?style=flat-square&logo=Naver&logoColor=white"/></a>|<img src="https://img.shields.io/badge/Spring Security-00A98F?style=flat-square&logo=Spring Security&logoColor=white"/><br><img src="https://img.shields.io/badge/JSON Web Token-9933CC?style=flat-square&logo=JSON Web Tokens&logoColor=white"/><br><img src="https://img.shields.io/badge/OAuth2-3423A6?style=flat-square&logo=Authelia&logoColor=white"/><br><img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=MySQL&logoColor=white"/>|<img src="https://img.shields.io/badge/Amazon AWS-232F3E?style=flat-square&logo=Amazon Web Services&logoColor=white"/><br><img src="https://img.shields.io/badge/Docker-2496ED?style=flat-square&logo=Docker&logoColor=white"/><br><img src="https://img.shields.io/badge/Github Actions-0063DC?style=flat-square&logo=Github Actions&logoColor=white"/>|<img src="https://img.shields.io/badge/Notion-000000?style=flat-square&logo=Notion&logoColor=white"/><br><img src="https://img.shields.io/badge/Swagger-85EA2E?style=flat-square&logo=Swagger&logoColor=black"/>
```
- Frontend : React, JavaScript
- Backend : Spring Boot, Java | Security, JWT, OAuth2 | MySQL
- External API : ChatGPT, Naver Shopping
- Deployment : AWS Amplify, AWS Elastic Beanstalk, Docker, Github Actions
- Documentation : Notion, Swagger
```
<br>


## 🗂️ Database
<img width="750" alt="smartcart_database" src="https://github.com/user-attachments/assets/135b09b6-9af7-4a5d-a656-efb10f652eb8">
<br><br>


## 📗 API
<img width="750" alt="smartcart_swagger_api" src="https://github.com/user-attachments/assets/2e028957-04b5-4b0a-a9a5-bdbb988cc97c">
<br><br>


## 🤝 Git Convention

### Branch
- Backend 개발은 혼자 진행하므로, 단일 브랜치(main)를 사용한다.
- 단, commit에 대한 prefix 규칙을 준수하도록 한다.
- `main` : 개발 및 배포 CI/CD용 branch

### Commit Prefix

| 종류             | 내용                                             |
|----------------| ------------------------------------------------ |
| ✨ Feat         | 기능 구현                                          |
| 🐛 Fix         | 버그 수정                                           |
| ♻️ Refactor    | 코드 리팩토링                                         |
| ✅ Test         | 테스트 업무                                        |
| 📁 File        | 파일 이동 또는 삭제, 파일명 변경                         |
| 📝 Docs        | md, yml 등의 문서 작업                               |
| 🔧 Chore       | 이외의 애매하거나 자잘한 수정                            |
| ⚙️ Setting     | 빌드 및 패키지 등 프로젝트 설정                           |

```
< Commit Message >
Prefix_종류: 구현_내용
ex) Feat: Security 및 OAuth2 구현
```
<br>


## 📂 Package Convention

### Structure
```
## DevOps ##
├── .ebextensions : AWS EB 환경 설정
├── .github
│   └── workflows : CI/CD 실행
├── .gitmodules : Git 서브모듈 정의
├── Dockerfile : 도커 이미지 빌드 설정
├── Dockerrun.aws.json : 도커 컨테이너 EB 배포 설정
└── submodule-backend : 배포용 properties 관리

+

## Backend ##
├── config
├── controller
├── service
│   └── impl
├── repository
├── domain : Entity
│   └── enums
├── dto
├── external : 외부 API 클라이언트
├── response : API 응답, Exception 핸들러
│   ├── responseitem
│   └── exception
├── security
│   ├── jwt : 토큰 처리
│   │   └── handler
│   └── oauth2 : 소셜 로그인
│       ├── handler
│       └── userinfo
└── util
```
<details>
  <summary>&nbsp;<strong>Detailed Structure</strong>&nbsp;:&nbsp;Open!</summary>
  <br>

```
├── .ebextensions
│   ├── 00-set-timezone.config
│   └── 01-set-swapmemory.config
├── .github
│   └── workflows
│       └── deploy.yml
├── .gitmodules
├── Dockerfile
├── Dockerrun.aws.json
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com
│   │   │       └── sajang
│   │   │           └── smartcart_backend
│   │   │               ├── SmartcartBackendApplication.java
│   │   │               ├── config
│   │   │               │   ├── AwsS3Config.java
│   │   │               │   ├── SecurityConfig.java
│   │   │               │   └── SwaggerConfig.java
│   │   │               ├── controller
│   │   │               │   ├── AuthController.java
│   │   │               │   ├── CheckitemController.java
│   │   │               │   ├── ProductController.java
│   │   │               │   ├── TestController.java
│   │   │               │   └── UserController.java
│   │   │               ├── domain
│   │   │               │   ├── Checkitem.java
│   │   │               │   ├── Product.java
│   │   │               │   ├── User.java
│   │   │               │   ├── enums
│   │   │               │   │   ├── Role.java
│   │   │               │   │   └── SocialType.java
│   │   │               ├── dto
│   │   │               │   ├── AuthDto.java
│   │   │               │   ├── CheckitemDto.java
│   │   │               │   ├── ExternalDto.java
│   │   │               │   ├── ProductDto.java
│   │   │               │   └── UserDto.java
│   │   │               ├── external
│   │   │               │   ├── ChatgptClient.java
│   │   │               │   └── NaverShoppingClient.java
│   │   │               ├── repository
│   │   │               │   ├── CheckitemRepository.java
│   │   │               │   ├── CheckitemBatchRepository.java
│   │   │               │   ├── ProductRepository.java
│   │   │               │   ├── ProductBatchRepository.java
│   │   │               │   └── UserRepository.java
│   │   │               ├── response
│   │   │               │   ├── GlobalExceptionHandler.java
│   │   │               │   ├── ResponseCode.java
│   │   │               │   ├── ResponseData.java
│   │   │               │   ├── exception
│   │   │               │   │   ├── CustomException.java
│   │   │               │   │   ├── Exception400.java
│   │   │               │   │   ├── Exception404.java
│   │   │               │   │   └── Exception500.java
│   │   │               │   └── responseitem
│   │   │               │       ├── MessageItem.java
│   │   │               │       └── StatusItem.java
│   │   │               ├── security
│   │   │               │   ├── jwt
│   │   │               │   │   ├── JwtFilter.java
│   │   │               │   │   ├── TokenProvider.java
│   │   │               │   │   └── handler
│   │   │               │   │       ├── JwtAccessDeniedHandler.java
│   │   │               │   │       ├── JwtAuthenticationEntryPoint.java
│   │   │               │   │       └── JwtExceptionFilter.java
│   │   │               │   └── oauth2
│   │   │               │       ├── CustomOAuth2User.java
│   │   │               │       ├── CustomOAuth2UserService.java
│   │   │               │       ├── OAuthAttributes.java
│   │   │               │       ├── handler
│   │   │               │       │   ├── OAuth2LoginFailureHandler.java
│   │   │               │       │   └── OAuth2LoginSuccessHandler.java
│   │   │               │       └── userinfo
│   │   │               │           ├── GoogleOAuth2UserInfo.java
│   │   │               │           ├── KakaoOAuth2UserInfo.java
│   │   │               │           ├── NaverOAuth2UserInfo.java
│   │   │               │           └── OAuth2UserInfo.java
│   │   │               ├── service
│   │   │               │   ├── AuthService.java
│   │   │               │   ├── AwsS3Service.java
│   │   │               │   ├── CheckitemService.java
│   │   │               │   ├── ExternalService.java
│   │   │               │   ├── ProductService.java
│   │   │               │   ├── UserService.java
│   │   │               │   └── impl
│   │   │               │       ├── AuthServiceImpl.java
│   │   │               │       ├── AwsS3ServiceImpl.java
│   │   │               │       ├── CheckitemServiceImpl.java
│   │   │               │       ├── ExternalServiceImpl.java
│   │   │               │       ├── ProductServiceImpl.java
│   │   │               │       └── UserServiceImpl.java
│   │   │               └── util
│   │   │                   ├── MultipartJackson2HttpMessageConverter.java
│   │   │                   └── SecurityUtil.java
│   │   └── resources
│   │       ├── application-local.properties
│   │       ├── application-secret.properties
│   │       └── application.properties
└── submodule-backend
    ├── application-prod.properties
    └── application-secret.properties
```
</details>
<br>


## 👨‍👩‍👧‍👧 Team
| [사현진](https://github.com/tkguswls1106) | [김희원](https://github.com/Joygarden425) | [목경민](https://github.com/mkm0630) |
| :----------------------------------------: | :----------------------------------------: | :----------------------------------------: |
| <img width = "300" src ="https://github.com/Dev-Race/DevRace-backend/assets/56509933/20a67908-ca87-49ea-aafc-60f306302429"> | <img width = "300" src ="https://github.com/user-attachments/assets/ae4ebddf-5a25-4ff4-9152-dcf15834f984"> | <img width = "300" src ="https://github.com/user-attachments/assets/3d95184b-90cf-4473-8170-7f050444219d"> |
| FullStack Developer,<br>DevOps Engineer | Frontend Developer,<br>Designer | Frontend Developer,<br>Designer |
