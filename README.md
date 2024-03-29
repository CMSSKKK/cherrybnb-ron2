# Cherrybnb
- 에버비앤비 스타일 숙박 검색 앱
- 개발 기간: 22.05.23 - 22.06.12

<img src="https://user-images.githubusercontent.com/17468015/173228081-17b727e3-2395-4999-8bfc-5e4d8f472658.png" width="350">


# 기능 소개

## iOS 


|   위치 검색    |   날짜 검색   |   가격 검색    |
| :----------: | :--------: | :----------: |
|  <img src="https://user-images.githubusercontent.com/17468015/173227973-4daf7840-bc63-47f8-abfd-f4dd029a82c8.gif" width="300"> | <img src="https://user-images.githubusercontent.com/17468015/172120869-0b5b1cb6-1dbc-4a17-9655-87b474d03272.gif" width="300">  | <img src="https://user-images.githubusercontent.com/59790540/172773103-21ad4046-28f2-4428-a807-cd858000371d.gif" width="300">  |





## BE ☕

> API 기능 보여주기
api 문서: http://54.180.9.224/swagger-ui/index.html

#### 사용 중인 api
- 숙소 목록 조회하기 /rooms
- 숙박비 빈도값 조회하기 /rooms/prices
- 사용자 위치와 인기여행지 거리 및 시간 /distance/times

# 팀원

|    BE ☕  |   iOS     |
| -------- | ---------- |
|  Riako [@naneun](https://github.com/naneun)  |  Chez [@asqw887](https://github.com/asqw887)  |
|  Ron2 [@cmsskkk](https://github.com/cmsskkk) |  Eddy [@BumgeunSong](https://github.com/BumgeunSong)  |

# 문제 해결
> 가장 기억에 남는 문제 해결 경험

> 자세한 내용은 기술 회고 참고 👉 https://github.com/naneun/CherryBnB/wiki

<details>
<summary> 
<h3>Chez - 객체지향과, 프로젝트 설정</h3>
</summary>



- 객체지향 관점에서 객체, 함수, 모듈을  `잘 나누기`   
  - 어떻게 객체를 나눠야 잘 나누었다고 할 수 있을지에 대해 고민을 많이 해보았다. 구현을 먼저 한 후 코드 리뷰를 통해서 리팩토링을 하도록 시도했다. 

- merge를 하는과정에서 실행파일 충돌 문제를 매번 해결하는게 번거로워, 프로젝트 파일을 관리할 수 있는 `Tuist` 를 사용보았다.
  - 구성파일(Manifiest)에서 프로젝트 구조를 정의를 코드로 해주어야 되기 때문에, Project와 Target 관계와, 실행파일을 구성하기 위해 줄 수 있는 설정들이 어떤 것들이 있는지 찾아보았다. 

- 라이브러리와 타겟들 간의 dependcy를 설정해주는 과정에서 어려움이 있었다. 
  - SPM 으로 관리하기 위해서 , tuist manifest파일 에서 필요한 라이브러리에 대한 external dependency를 설정을 해주었다.
  - 몇몇 라이브러리들은 SPM을 지원하지 않아, 결국 cocoaPod으로 dependcy 추가를 한 후에도, 여전히 라이브러리가 import 되지 않음.
  - 테스트 타겟이 앱 타겟에 대한 의존성을 부여해주지 않아서 발생했던 문제였음.
  - tuist 설정 파일에서 테스트 타겟이 앱 타겟에 대한 dependencies 설정을 해주어 해결 .
</details>

<details>
<summary> 
<h3>Eddy: 리팩토링과 객체 지향 설계</h3>
</summary>

* 이번 프로젝트의 중점 학습 목표는, 리팩토링과 객체 지향 설계였다.
	* **전체 개발 시간의 50% 이상을 리팩토링에 사용**한 것 같다. 객체의 역할을 의미있게 나누기 위해서 이렇게도 바꿔보고, 저렇게도 바꿔보고 다양한 시도를 했다. 
	* 커스텀 캘린더 기능 구현과 Quick을 이용한 단위 테스트도 있었지만, 그 중에서도 **거대 뷰 컨트롤러를 해체**하고 의존성을 역전시켜 결합도를 낮춘 것이 기억에 남는다.

* 문제 1: 초기에는 위치 검색과 관련된 **모든 역할이 `LocationSearchViewController`에 몰려 있었다.**
	* 이 ViewController의 관심사를 분리해주기 위해 다음과 같은 것들을 시도했다.
	* `CollectionView`의 `DataSource`, `Delegate`를 별도의 객체로 분리했다.
	* 데이터 페칭 로직을 모델로 분리했다.
	* 분리된 객체 간의 데이터 흐름을 일관성있게 만들기 위해 Closure, Delegate의 사용 기준을 세웠다.
	* 상위 컨트롤러와 하위 컨트롤러가 모두 추상 타입에 의존하도록 의존성 역전시켰다. Swinject 라이브러리의 `DIContainer`를 사용해서 의존성을 주입했다.

* 문제 2: **Delegate를 분리**했을 때 오히려 결합도가 높아지고 실행 흐름이 복잡해지는 문제가 있었다.
	* `Delegate`가 `DataSource`나 상위 `ViewController`의 인스턴스 변수를 많이 참조하기 때문이었다. 
  * 무조건적인 분리보다 `DataSource`와 상위 `ViewController` 중 하나에 합치는 것이 낫다고 판단했다. 
  * **상황에 따라 적절한 응집도와 결합도를 만드는 방법은 달라질 수 있다**는 것을 배웠다.

* 문제 3: 구조가 복잡해지면서, **가독성 좋은 코드**를 만드는데 노력이 많이 필요했다.
	* 객체를 분리하고, 관계를 추상화하다보면 자연스럽게 구조가 복잡해진다.
	* 위치 검색 기능 하나만 봐도, `ViewController` 1개, 화면 UI 요소 3개, 하위 컨트롤러 3개, 데이터 페칭 모델 3개, 각 의존성을 추상화하는 프로토콜 6개 등 많은 부품들이 존재하게 되었다.
	* 별도의 조치를 취하지 않으면, 코드의 실행 흐름 파악은 어려워지고 가독성은 낮아진다.
	* 최대한 **클린 코드** 원칙을 적용하고자 노력했다.
	* 일관성 있는 커뮤니케이션 패턴을 사용했다. 함수를 별도로 추출해 작게 쪼갰다. 
	* 타입에 alias를 붙여주거나, 작게 쪼갠 함수와 객체의 네이밍을 통일했다.
  * 훨씬 더 이해하기 쉽고, 단순한 코드로 바꿀 수 있었다.
</details>

<details>
<summary> 
<h3>Riako - OAuth GitHub, Google 로그인 프로세스</h3>
</summary>
	
- [간단한 OAuth 로그인 기능 구현하기](https://velog.io/@naneun/Spring-Boot-OAuth-GitHub-Google-%EB%A1%9C%EA%B7%B8%EC%9D%B8)
	
- OAuth 와 JWT 토큰을 사용하여 로그인 기능을 구현합니다.
	- 여러 벤더에서 로그인 처리하는 로직을 추상화하여 통일시켰습니다.
	- WebClient 를 사용하여 OAuth 로그인 기능을 구현했습니다.
	- OAuth 기능을 사용하여 받아온 사용자 정보로 jwt 토큰을 생성합니다.
	- 해당 토큰을 클라이언트가 저장하고 API 를 요청할 때마다 헤더에 담아 보내고 서버는 이를 검증합니다.
	
</details>

<details>
<summary> 
<h3>Ron2 - 외부 API 호출을 restTemplate(동기) -> webclient(비동기) 리팩토링</h3>
</summary>
	
- [RestTemplate에서 WebClient로 리팩토링해보기](https://velog.io/@cmsskkk/RestTemplate-WebClient-refactoring)
	- 위치 좌표간의 실제 자동차로 이동하는 거리 및 이동시간을 반환하는 Kakao-navi-api를 사용하였습니다.
	- 사용자의 좌표와 인기 여행지 8곳을 RestTemplate으로 거리 및 시간 데이터를 반복문으로 동기적으로 처리하던 것을 리팩토링하였습니다.
	- WebClient를 활용해서 비동기적으로 처리해서 기존 9.xx초 걸리던 로직을 2.xx 이내로 줄였습니다. 
	- WebClient의 사용법 및 비동기 flow를 익히는 계기가 되었습니다. 
</details>

# 사용한 기술

## BE ☕

- java 11
- Spring boot 2.67
- Spring Data Jpa
- querydsl 5.0.0
- MySql 8.x
- Swagger 2.0
- AWS EC2, ubuntu 20.04
- VPC 
- GitHub Actions
- Docker

## iOS 
- Language: `Swift`
- UI: `UIKit`
- Testing: `Quick`, `Nimble`
- Local Search: `MapKit`
- Project File Management: `Tuist`
- Dependency Injection: `Swinject`
- Linter: `SwiftLint`
