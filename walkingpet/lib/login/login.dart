import 'package:flutter/material.dart';

class Login extends StatelessWidget {
  const Login({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Container(
        decoration: const BoxDecoration(
          image: DecorationImage(
            image: AssetImage("assets/backgrounds/day.png"), // 이미지 파일 경로 지정
            fit: BoxFit.cover, // 배경 이미지가 전체 화면을 채우도록 설정
          ),
        ),
        child: Center(
          child: Column(
            children: [
              const SizedBox(
                height: 120,
              ),
              // 워킹펫 문구 크게
              const Text(
                "Walking Pet",
                style: TextStyle(
                  fontSize: 52,
                  color: Color.fromRGBO(141, 198, 63, 1),
                  shadows: [
                    Shadow(
                      // 외곽선 색상 및 오프셋 설정
                      offset: Offset(-1.5, -1.5),
                      color: Color.fromRGBO(54, 91, 18, 1),
                      blurRadius: 1,
                    ),
                    Shadow(
                      offset: Offset(1.5, -1.5),
                      color: Color.fromRGBO(54, 91, 18, 1),
                      blurRadius: 1,
                    ),
                    Shadow(
                      offset: Offset(1.5, 1.5),
                      color: Color.fromRGBO(54, 91, 18, 1),
                      blurRadius: 1,
                    ),
                    Shadow(
                      offset: Offset(-1.5, 1.5),
                      color: Color.fromRGBO(54, 91, 18, 1),
                      blurRadius: 1,
                    ),
                  ],
                ),
              ),

              const SizedBox(
                height: 200,
              ),
              // 걷는 캐릭터
              Image.asset(
                'assets/animals/cow/cow_run.gif',
              ),

              const SizedBox(
                height: 40,
              ),

              InkWell(
                onTap: () {
                  // 네비게이션 함수 호출 예시
                  Navigator.pushNamed(context, '/home'); // 로그인 후 이동할 페이지 경로 지정
                },
                child: Image.asset('assets/icons/kakao_login_medium_wide.png'),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
