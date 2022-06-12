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
<h3>Chez - 00을 해결한 경험 (작성 예정)</h3>
</summary>

- 어떻게 만드는 게 목표였는지,
- 어떤 문제가 있었는지,
- 문제를 해결하기 위해 어떤 시도를 했는지,
- 어떻게 해결했는지.
</details>

<details>
<summary> 
<h3>Eddy: 리팩토링과 객체 지향 설계</h3>
</summary>

* 이번 프로젝트의 중점 학습 목표는, 리팩토링과 객체 지향 설계였다.
	* 전체 개발 시간의 50% 이상을 리팩토링에 사용한 것 같다. 객체의 역할을 의미있게 나누기 위해서 이렇게도 바꿔보고, 저렇게도 바꿔보고 다양한 시도를 했다. 
	* 커스텀 캘린더 기능 구현과 Quick을 이용한 단위 테스트도 있었지만, 그 중에서도 거대 뷰 컨트롤러를 해체하고 의존성을 역전시켜 결합도를 낮춘 것이 기억에 남는다.

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
<h3>Riako - 00을 해결한 경험 (작성 예정)</h3>
</summary>

- 어떻게 만드는 게 목표였는지,
- 어떤 문제가 있었는지,
- 문제를 해결하기 위해 어떤 시도를 했는지,
- 어떻게 해결했는지.
</details>

<details>
<summary> 
<h3>Ron2 - 외부 API 호출을 restTemplate(동기) -> webclient(비동기) 리팩토링</h3>
</summary>
- [RestTemplate에서 WebClient로 리팩토링해보기](https://velog.io/@cmsskkk/RestTemplate-WebClient-refactoring)
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
