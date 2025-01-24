# Rose War Score tracker

### 보드게임 점수 계산 서비스

기간 : 2024.10.30-진행중

인원 : 1명 (FE, BE)

**사용스택**

`Vue.js` `Typescript` `Tanstack-Query`
`Java` `Spring Boot` `JPA` `MySQL` `Spring Security` `docker` `EC2`

**구현 기능**

- Spring Security를 이용하여 JWT 방식의 회원가입 및 로그인을 구현하였습니다.
    - 회원가입 직후 로그인되도록 하여 UX 개선
- 보드게임 장미전쟁 점수 계산 서비스를 구현하였습니다.
    - 비회원도 점수 계산 서비스 이용 가능하도록 설계
- 보드게임에 대해 이야기를 나눌 수 있는 게시판을 구축하였습니다.
    - 대댓글 기능 구현
- AWS EC2 배포 후 Docker를 이용하여 백그라운드에서 실행되도록 했습니다.
    - Application Load Balancer(ALB)가 클라이언트의 HTTPS 요청을 처리하고, 이를 복호화하여 대상 그룹의 8080 포트로 전달하도록 설정했습니다.

**의사 결정 과정 및 배운 점**

<aside>

제 3정규화 기반의 릴레이션 분해

👾 문제 상황

- 초기에는 게임 참가자 릴레이션 내에서 PK인 유저 아이디가 닉네임의 종속자가 되고, 닉네임은 게임 승·패·무의 종속자가 되는 구조여서 이행적 함수 종속이 발생했습니다.
- 로그인하지 않은 게스트 유저도 UUID를 부여받아 게임에 참여할 수 있어야 했습니다.
    
    
- 이행적 함수 종속은 이상 현상의 원인이 될 수 있음을 학습한 경험이 있으며, 유저의 개인정보와 게임 통계 정보가 한 릴레이션의 존재할 경우 유지보수가 어려울 것이라는 생각도 들었습니다.

    

해결 과정

- Stat 릴레이션을 별도로 분리하고 유저가 회원가입할 때마다 통계 릴레이션에 초기화된 튜플을 삽입하는 방식으로 기존 참가자 릴레이션과 1:1 관계를 설정.
- 회원 릴레이션에 가입 내역이 없더라도 통계 릴레이션에 초기화된 튜플을 삽입하여 로그인 여부와 관계없이 모든 게임 참가자가 동일한 구조로 기록될 수 있도록 설계.

결과 및 배운 점

- 정규화를 통해 릴레이션을 분리할 경우, 이벤트의 인과 관계를 명확히 인지하여 설계하는 것의 중요성을 깨닫게 되었습니다.
- 회원 테이블과 통계 테이블을 분해할 경우 userService가 statService 구체에 의존하게 되어, 의존성을 제거할 수 있는 방법을 모색하게 되었습니다.
</aside>

<aside>

event publisher를 사용하여 서비스 간 의존성을 낮춤

👾 문제 상황

- 회원가입 직후에 UserService 내에서 새로운 게임 통계 튜플을 생성하기 위해 statService의 createStat 메서드를 호출해야 했습니다.
- 서비스가 다른 서비스의 구현에 의존하면 변경 시 영향을 받을 가능성이 있어 확장성과 유지보수성이 저해됨을 학습한 경험이 있습니다.

```java
    @Transactional
    public SignUpResponseDTO createUser(SignUpFormDTO userRequestDTO, HttpServletResponse response) {

        // 통계 생성 (의존성 낮추는 방법 검토 가능)
        statService.createStat(savedPlayer.getUserId());
    }
```

🔧 해결 과정

- eventPublisher를 사용하여 회원가입 이벤트 발생 시 statService가 이를 구독하도록 함.

```java
    // 사용자 생성
    @Transactional
    public SignUpResponseDTO createUser(SignUpFormDTO userRequestDTO, HttpServletResponse response) {

				// 이벤트 발행/구독으로 서비스 간 결합도를 낮춤
        eventPublisher.publishEvent(new UserCreatedEvent(savedPlayer.getUserId()));

    }
```

✍️ 결과 및 배운점

- 이벤트 기반으로 동작하므로, UserService는 StatService의 구체적인 구현을 알 필요가 없게 되었습니다.
- 서비스는 단순히 이벤트를 발행하고, 구독자는 필요한 경우 이를 처리하기만 하면 되었습니다.
- 서비스 간의 강한 결합이 확장성을 저해할 때, 이벤트 발행/구독으로 결합도를 낮출 수 있는 방법을 습득하게 되었습니다.
</aside>

<aside>

점수 계산 동시성 문제 해결 - 비관적 락을 선택한 과정

[[DB] 게임 통계 업데이트 시 동시성 문제 고려하기 (낙관적 락, 비관적 락)](https://velog.io/@jasmine0714/lock)

👾 문제 상황

- 한 사용자가 다중 기기로 동시에 점수 계산을 요청하는 시나리오에서 동시성 문제가 발생할 수 있음을 인지하였습니다.


```java
    // 특정 사용자의 통계 업데이트 (예: 승리, 패배, 최고 점수 업데이트)
    @Transactional
    public StatResponseDTO updateStat(String userId, int player1Score, int player2Score) {
    // 2개의 기기에서 동시에 기존 게임 통계 데이터를 읽으면 같은 데이터를 읽게 될텐데
        Stat stat = statRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Stat not found for user: " + userId));
				// 4 + 1 = 5만큼 증가해야 하는데 4만큼 증가할 수도 있는 문제
        stat.setTotalPlayCount(stat.getTotalPlayCount() + 1);
        // 하나가 11로 업데이트 되기 전에 다른 스레드 하나가 이전의 10이라는 maxScore를 읽는 문제
        stat.setMaxScore(Math.max(stat.getMaxScore(), player1Score));
    }
```

🔧 해결 과정

- 낙관적 락과 비관적 락을 학습하고 장단점을 비교하였습니다.
    - 낙관적 락을 선택할 경우, 트랜잭션에 아이디를 부여하는 방식이므로 충돌 발생 시 요청이 ‘거부’ 당할 수 있으므로 UX 측면에서 단점을 인지했습니다.
    - 비관적 락을 선택할 경우, 하나의 트랜잭션이 읽기락 또는 쓰기락을 쥐고 있는 동안 다른 트랜잭션이 접근하지 못하기에 요청이 거부되는 것이 아니라 ‘대기’합니다.
- UX에 최우선 가치를 두었을 때, 충돌 시 요청 거부보다는 대기가 더 나은 사용자 경험을 제공할 것이라 생각하여 비관적 락을 걸었습니다.

```java
    // Pessimistic Lock 적용
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT s FROM Stat s WHERE s.userId = :userId")
    Optional<Stat> findByIdWithLock(@Param("userId") String userId);
```

✍️ 결과 및 배운 점

- 유저 한 명만 접근할 수 있는 데이터(totalPlayCount)이더라도, 다중 기기 사용을 고려할 때 데이터 충돌 방지 및 안정적인 트랜잭션을 구현하는 것이 여전히 중요하다는 것을 배웠습니다.
- UX에 최우선 가치를 둔다는 가정이 유지될 때, 비관적 락을 걸더라도 추후 트래픽이 몰릴 경우 대기 시간이 길어질 수 있으므로 대기 시간을 줄이는 전략을 함께 고민해야겠다는 생각도 들었습니다.
</aside>

<aside>

FK 학습 경험 토대로 게시판 대댓글 구조 설계

👾 문제 상황

- 장미전쟁에 대한 이야기를 나눌 수 있는 게시판에서 대댓글 기능을 구축해야 했습니다.
    - 댓글은 상위 댓글과 대댓글(하위 댓글)을 포함하는 **1:N 관계**를 가져야 하며, 이를 데이터베이스 구조로 표현해야 했습니다.

  

🔧해결 과정

- 외래키는 자기 자신이 속한 릴레이션의 기본키를 참조할 수 있으며, null 값을 가질 수 있다는 점을 상기하였습니다.
- 대댓글의 parentId 속성이 상위 댓글의 ID를 참조하고, 상위 댓글의 경우 null값을 가지도록하여 계층 구조를 설계했습니다.

✍️ 배운 점

- 외래키에 대한 지식을 이전에 학습하지 못했다면, chatGPT에게 의존한 채 외래키가 왜 null 값을 가져도되는지에 대한 물음에 스스로 답할 수 없었을 것이라는 생각이 들었습니다.
- 앞으로도 CS 스터디에서 학습한 기본 개념들을 설계와 기능 구현에 활용하며, 설계의 근본 원리와 데이터를 다루는 방식에 대한 이해를 지속적으로 심화해야겠다는 생각이 들었습니다.
</aside>

<aside>

게시판 대댓글 조회 시간복잡도 개선

👾 문제 상황

- commentService 내에서 댓글의 부모-자식 관계를 매칭할 때 처음에는 이중 for문을 사용하여 구현했습니다.
- 이 방식은 시간 복잡도가 O(N²)으로, 대규모 데이터가 누적될 경우 처리 속도가 느려질 가능성이 있어 사용자 경험에 부정적인 영향을 미칠 수 있었습니다.

🔧해결과정

- 해시 자료구조를 이용하면 key-value를 사용하여 O(N^2)에서 O(N)으로 개선 가능하겠다고 판단하여 리팩토링했습니다.

```java
//        부모-자식 관계 매칭 - O(N^)
//        for (CommentResponseDTO parent : responseDTOs) {
//            for (CommentResponseDTO child : responseDTOs) {
//                if (child.getParentCommentId() != null && child.getParentCommentId().equals(parent.getCommentId())) {
//                    if (parent.getChildrenComments() == null) {
//                        parent.setChildrenComments(new ArrayList<>());
//                    }
//                    parent.getChildrenComments().add(child);
//                }
//            }
//        }

 // Map 생성: 부모 댓글 ID를 키로, 자식 댓글 리스트를 값으로 저장 - O(N)
        Map<Long, List<CommentResponseDTO>> map = new HashMap<>();
        
 // 중략
 
 // 부모 댓글에 자식 댓글 리스트를 설정
        for (CommentResponseDTO parent : allComments) {
            if (map.containsKey(parent.getCommentId())) {
                parent.setChildrenComments(map.get(parent.getCommentId()));
            }
        }
```

✍️ 배운 점

- 시간이 지날수록 기존 코드를 기억하기 어렵고 변경하기도 어려워지므로, 대규모 데이터를 처리할수록 초기 설계 단계에서부터 성능 최적화를 고려하는 습관이 중요하겠다고 느꼈습니다.
</aside>

<aside>

HTTPS 적용에 Nginx vs ALB, ALB를 선택

👾 문제

- 초기에는 Nginx를 리버스 프록시로 두고 HTTPS를 적용하려 했으나, **Let’s Encrypt** 스크립트를 사용해 인증서를 수동으로 관리하고 주기적으로 갱신해야 한다는 점에서 운영 부담이 발생할 가능성을 확인했습니다.

🔧해결 과정

- AWS의 Application Load Balancer(ALB)를 활용하면, 다음과 같은 장점이 있음을 알게 되었습니다:
    
    > ACM을 통해 인증서를 자동 발급 및 갱신하여 인증서 관리가 간소화됨.
    EC2와 자연스럽게 통합되어 네트워크 구성과 운영 부담이 감소.
    HTTPS 설정이 간단히 적용 가능하여 설정 시간을 단축.
    > 

🪓Trouble shooting

- 로드 밸런서의 리스너와 대상그룹의 포트를 모두 443으로 지정하였는데, 대상 그룹의 unhealthy 상태가 지속되었습니다.
- 대상 그룹의 포트를 **8080**으로 수정하여 상태를 **Healthy**로 변경했습니다.

✍️ 배운점

- 로드 밸런서는 HTTPS 프로토콜로 클라이언트로부터 443 포트로 요청을 받고, 이를 대상그룹에 8080 포트로 전달한다는 점을 숙지하였습니다.    
- 로드 밸런싱은 서버 확장뿐만 아니라, AWS ****통합과 HTTPS 인증서 관리의 간소화를 위해서도 활용될 수 있다는 점을 알게 되었습니다.
- 상황에 따라 적절한 도구를 선택하는 것이 운영 효율성과 생산성을 크게 향상시킬 수 있음을 배웠습니다.
- AWS의 ALB는 초기 설정 이후 유지보수 부담이 거의 없었으며, 프로젝트 요구사항에 적합한 선택이었음을 확인했습니다.
- 다만, Nginx에는 ALB와 달리 정적 파일을 캐싱할 수 있다는 장점이 있으므로 리버스 프록시의 캐싱 기능이 중요한 상황에서는 Nginx 사용을 시도하는 것이 더 나은 선택이 되겠다는 생각이 들었습니다.
</aside>
