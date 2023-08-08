# 📷멋사 SNS📷 - _자신의 일상을 보여주는 플랫폼_

##### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;_from:&nbsp; . / 멋쟁이 사자처럼 / 백엔드 스쿨 / 5th / 미션형 프로젝트_2_

## 1️⃣ 프로젝트 소개
- **📷멋사 SNS📷**은 자신의 소소한 일상을 사진,파일과 함께 피드를 등록하여 여러사람과 공유 할수있는 플랫폼입니다.

</br>

### ⚙️ 개발 환경
- `Framework : Spring Boot 3.1.1`
- `Language : Java 17`
- `IDE : IntelliJ IDEA`
- `DataBase : SQLite`
  - `ORM (Object Relational Mapping) : JPA (Java Persistent API)`
- `Security : Spring Security`
</br>

### 📄 참고 ERD
<br>
<img width="600" alt="db_erd" src="https://github.com/likelion-backend-5th/Project_2_JangYongJin/assets/130991633/2fca736d-3ae5-45bf-b6ca-08b38dd6130d">

<br>
</br>

## 2️⃣ 구현 기능

<details>
<summary>
    
  ### 1. Endpoint 정리
</summary>
===========================================================================
<details>
  <summary>
    
  #### a. 유저(User)
  </summary>
<div markdown="1">

_회원 가입 진행_
| 요청 | Method | Mapping URL |
|:-- | :--: | :-- |
| 회원 가입 | POST | /users/register |
| 로그인 | POST | /users/login |
| 프로필이미지 등록 | PUT | /users/image |
| 팔로잉 | POST | /users/{username} |

</div>
</details>
<details>
  <summary>
    
  #### b. 피드(Article)
  </summary>
<div markdown="1">

_중고 거래할 물품을 (판매자) 등록, 수정, 이미지 등록, 삭제 (모두) 전체 조회, 단일 조회_
| 요청 | Method | Mapping URL |
|:-- | :--: | :-- |
| 피드 등록 | POST | /articles |
| 피드 수정 | PUT | /articles/{article_id} |
| 피드 목록 조회 | GET | /articles/{username} |
| 피드 단독 조회 | GET | /articles/{article_id} |
| 피드 삭제 | DELETE | /articles/{article_id} |
| 피드 좋아요 | DELETE | /articles/{article_id}/like |
  
</div>
</details>
<details>
  <summary>
    
  #### c. 댓글(Comments)
  </summary>
<div markdown="1">

_해당 물품에 대한 댓글을 (구매자) 등록, 수정, 삭제 (판매자) 답글 등록, (모두) 댓글 전체 조회_
| 요청 | Method | Mapping URL |
|:-- | :--: | :-- |
| 댓글 등록 | POST | /comments/{article_id} |
| 댓글 수정 | PUT | /comments/{article_id}/{comment_id} |
| 댓글 삭제 |  DELETE | /comments/{article_id}/{comment_id} |
  
</div>
</details>
===========================================================================
</details>

<details>
  <summary>

  ### 2. 상세 API 명세
  </summary>
===========================================================================
  <details>
  <summary>
    
  #### a. 유저(User)
  </summary>

  <details>
    <summary> 회원가입 </summary>
<div markdown="1">

- 요청<br/>
  - `POST /users/register`
  - Header :
    ```javascript
    // 추가요소 없음
    ```
  - Body :
  ```json
  {
    "username" : "gaga",
    "password" : "1234",
    "passwordCheck" : "1234",
    "email" : "gaga@gmail.com",
    "phone" : "010-1588-1588"
  }
  ```
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "message": "회원가입이 완료되었습니다."
  }
  ```

</div>
  </details>
  <details>
    <summary> 로그인 </summary>
<div markdown="1">

- 요청<br/>
  - `POST /users/login`
  - Header :
    ```javascript
    // 추가요소 없음
    ```
  - Body :
  ```json
  {
    "username" : "gaga",
    "password" : "1234"
  }
  ```
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "id": 1,
    "username": "gaga",
    "token": "{jwt-token}"
  }
  ```

</div>
  </details>
  <details>
    <summary> 프로필 이미지 등록 </summary>
<div markdown="1">

- 요청<br/>
  - `PUT /users/image`
  - Header :
    ```javascript
    // 추가
    "authorization" :  "{jwt-token}"
    ```
  - Body :
  ```json
  // form-data
  "image" : 사진.png
  ```
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "message": "프로필 이미지 등록에 성공했습니다."
  }
  ```

</div>
  </details>
  <details>
    <summary> 팔로잉 </summary>
<div markdown="1">

- 요청<br/>
  - `POST /users/nana`
  - Header :
    // 추가
    "authorization" :  "{jwt-token}"
  - Body : X
  
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  // 팔로우
  {
    "message": "[nana]을/를 팔로우 했습니다."
  }
  // 언팔로우
  {
    "message": "[nana]을/를 언팔로우 했습니다."
  }
  ```

</div>
  </details>
</details>

<details>
  <summary>
    
  #### b. 피드(Article)
  </summary>
  <details>
    <summary>피드 등록</summary>
<div markdown="1">

- 요청<br/>
  - `POST /articles`
  - Header :
  ```javascript
    // 추가
    "authorization" :  "{ jwt-token }"
  ```
  - Body :
  ```json
  // form-data
  "title" : "제목"
  "content" : "내용"
  "image" : 사진1.png (여러개 가능)
  "image" : 사진2.png
  ```
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "id": 1,
    "username": "gaga",
    "message": "피드 등록에 성공하였습니다.",
    "imageIdList": [
        1,
        2
    ]
  }
  ```

</div>
  </details>
  <details>
    <summary>피드 수정</summary>
<div markdown="1">

- 요청<br/>
  - `PUT /articles/1`
  - Header :
  ```javascript
    // 추가
    "authorization" :  "{피드 등록 유저의 jwt-token }"
  ``` 
  - Body :
  ```json
  "title" : "제목"
  "content" : "내용"
  "delete-img" : "{삭제할 이미지의 id}" (여러개 가능)
  "image" : 추가할사진.png (여러개 가능)
  ```
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "id": 1,
    "username": "gaga",
    "message": "게시글(피드) 수정을 완료했습니다.",
    "imageIdList": [
        2,
        3
    ]
  }
  ```

</div>
  </details>
  <details>
    <summary>피드 목록 조회</summary>
<div markdown="1">

- 요청<br/>
  - `/articles/read/gaga?page=0&limit=10`
  - Header : X
  - Body : X
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "content": [
        {
            "username": "gaga",
            "title": "첫번째 피드 제목 수정",
            "firstImgUrl": "/static/feed/1/20230808T210024745129800.png"
        },
        {
            "username": "gaga",
            "title": "두번재 피드 제목",
            "firstImgUrl": "/static/feed/2/20230808T210724471024900.png"
        }
    ],
    "pageable": {
        "sort": {
            "empty": false,
            "sorted": true,
            "unsorted": false
        },
        "offset": 0,
        "pageNumber": 0,
        "pageSize": 10,
        "unpaged": false,
        "paged": true
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 2,
    "size": 10,
    "number": 0,
    "sort": {
        "empty": false,
        "sorted": true,
        "unsorted": false
    },
    "numberOfElements": 2,
    "first": true,
    "empty": false
  }
  ```

</div>
  </details>
  <details>
    <summary>피드 단독 조회</summary>
<div markdown="1">

- 요청<br/>
  - `GET /articles/1`
  - Header :
  ```javascript
    // 추가
    "authorization" :  "{로그인한 유저의 jwt-token }"
  ``` 
  - Body : X
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "id": 1,
    "username": "gaga",
    "title": "첫번째 피드 제목 수정",
    "content": "첫번째 피드 내용 수정",
    "imgUrlList": [
        "/static/feed/1/20230808T210024745129800.png",
        "/static/feed/1/20230808T210604542293600.png"
    ],
    "comments": [
        {
            "id": 1,
            "username": "nana",
            "content": "귀여워요~ 1"
        },
        {
            "id": 2,
            "username": "nana",
            "content": "귀여워요~ 2"
        },
        {
            "id": 3,
            "username": "nana",
            "content": "귀여워요~ 3"
        }
    ]
  }
  ```

</div>
  </details>
  <details>
    <summary>피드 삭제</summary>
<div markdown="1">

- 요청<br/>
  - `DELETE /articles/2`
  - Header :
  ```javascript
    // 추가
    "authorization" :  "{피드 등록 유저의 jwt-token }"
  ```   
  - Body : X
  
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "message": "해당 게시글(피드)가 삭제되었습니다."
  }
  ```

</div>
  </details>
  <details>
    <summary>피드-좋아요</summary>
<div markdown="1">

- 요청<br/>
  - `POST /articles/1/like`
  - Header:
  ```javascript
    // 추가
    "authorization" :  "{피드 등록자가 아닌 유저의 jwt-token }"
  ``` 
  - Body : X

- 응답<br/>
  - Status : 200
  - Body :
  ```json
  // 좋아요
  {
    "articleId": 1,
    "status": "like"
  }
  or
  // 좋아요 취소
  {
    "articleId": 1,
    "status": "null"
  }
  ```

</div>
  </details>
</details>
<details>
  <summary>
    
  #### c. 댓글(Comment)
  </summary>
  <details>
    <summary>댓글 등록</summary>
<div markdown="1">

- 요청<br/>
  - `POST /comments/1`
  - Header :
  ```javascript
    // 추가
    "authorization" :  "{유저의 jwt-token }"
  ``` 
  - Body :
  ```json
  {
    "content": "귀여워요~ 3"
  }
  ```
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "id": 3,
    "username": "nana",
    "content": "귀여워요~ 3"
  }
  ```

</div>
  </details>
  <details>
    <summary>댓글 수정</summary>
<div markdown="1">

- 요청<br/>
  - `PUT /comments/1/1`
  - Header :
  ```javascript
    // 추가
    "authorization" :  "{댓글 등록 유저의 jwt-token }"
  ``` 
  - Body :
  ```json
  {
    "content": "귀여워요~(수정)"
  }
  ```
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "id": 1,
    "username": "nana",
    "content": "귀여워요~(수정)"
  }
  ```

</div>
  </details>
  <details>
    <summary>댓글 삭제</summary>
<div markdown="1">

- 요청<br/>
  - `DELETE /comments/1/3`
  - Header :
  ```javascript
    // 추가
    "authorization" :  "{댓글 등록 유저의 jwt-token }"
  ``` 
  - Body : X
- 응답<br/>
  - Status : 200
  - Body :
  ```json
  {
    "message": "해당 댓글이 삭제 되었습니다."
  }
  ```

</div>
  </details>
</details>
===========================================================================
</details>


<details>
  <summary>
    
  ### 3. 예외 처리
  </summary>
===========================================================================
  <details>
  <summary>
    
  #### a. 유저(User)
  </summary>
<div markdown="1">

  | 예외 클래스명 | 발생 상황 | Staus Code | 에러 메세지 |
  | :--: | :--: | :--: | :--: |
  | `UsernameIsExist()` | 회원가입시 이미 존재하는 이름을 등록하려고 할때 발생 | 400 - Bad Request | `"이미 존재하는 이름 입니다."` |
  | `UsernameNotExist()` | 유저검증시 존재하지 않는 유저일 경우 발생 | 400 - Bad Request | `"존재하지 않는 이름입니다."` |
  | `PasswordDuplicateCheckFail()` | 회원가입시 비밀번호와 비밀번호 확인이 불일치 할때 발생 | 400 - Bad Request | `"비밀번호와 비밀번호확인이 일치하지 않습니다."` |
  | `PasswordNotMatched()` | 로그인시 비밀번호가 일치 하지 않을때 발생 | 400 - Bad Request | `"비밀번호가 일치하지 않습니다."` |
  
</div>
  </details>
  <details>
  <summary>
    
  #### b. 피드(Article)
  </summary>
<div markdown="1">

  | 예외 클래스명 | 발생 상황 | Staus Code | 에러 메세지 |
  | :--: | :--: | :--: | :--: |
  | `ArticleNotExist()` | 작업할 피드를 찾지 못했을 때 발생 | 404 - Not Found | `"해당 게시글(피드)가 존재하지 않습니다."` |
  
</div>
  </details>
  <details>
  <summary>
    
  #### c. 댓글(Comment)
  </summary>
<div markdown="1">

  | 예외 클래스명 | 발생 상황 | Staus Code | 에러 메세지 |
  | :--: | :--: | :--: | :--: |
  | `CommentNotExist()` | 작업할 댓글을 찾지 못했을 때 발생 | 404 - Not Found | `"해당 댓글이 존재하지 않습니다."` |

  
</div>
  </details>
  <details>
  <summary>
    
  #### d. 공통(Common)
  </summary>
<div markdown="1">

  | 예외 클래스명 | 발생 상황 | Staus Code | 에러 메세지 |
  | :--: | :--: | :--: | :--: |
  | `ImageUploadFailed()` | 이미지 등록 과정에 문제가 생겼을때 발생 | 500 - Internal Server Error | `"이미지 등록과정에서 문제가 발생하였습니다."` |
  | `NoAuthUser()` | 해당 작업에 권한이 없는 사용자가 접근했을때 발생 | 403 - Forbidden | `"권한이 없는 사용자 입니다."` |
  
</div>
  </details>
===========================================================================
</details>
</br>

## 3️⃣ 별첨
- PostmanCollection 추가
  - `파일이름 : 멋사-미션형프로젝트-2-jyj.postman_collection.json`
- PR 링크 공유
  - https://github.com/likelion-backend-5th/PeerReview_11Team/pulls


