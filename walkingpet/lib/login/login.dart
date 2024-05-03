import 'package:flutter/material.dart';
import 'package:walkingpet/login/nes_input_dialog.dart';
import 'package:nes_ui/nes_ui.dart';

class Login extends StatelessWidget {
  const Login({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      decoration: const BoxDecoration(
        image: DecorationImage(
          image: AssetImage("assets/backgrounds/day.png"), // 배경 이미지 파일 경로 지정
          fit: BoxFit.cover, // 배경 이미지가 전체 화면을 채우도록 설정
        ),
      ),
      child: Scaffold(
        backgroundColor: Colors.transparent, // Scaffold 배경을 투명하게 설정
        body: Center(
          child: Column(
            children: [
              const SizedBox(
                height: 120,
              ),
              const Text(
                "Walking Pet",
                style: TextStyle(
                  fontSize: 52,
                  color: Color.fromRGBO(141, 198, 63, 1),
                  shadows: [
                    Shadow(
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
              Image.asset('assets/animals/cow/cow_run.gif'),
              const SizedBox(
                height: 40,
              ),
              InkWell(
                onTap: () async {
                  String? nickname = await show(context: context);
                  print("Entered nickname: $nickname"); // 사용자가 입력한 값을 출력
                  //Navigator.pushNamed(context, '/home');
                  if (nickname != null) {
                    Navigator.pushReplacementNamed(context, '/home');
                  }
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

Future<String?> show({
  required BuildContext context,
  NesDialogFrame frame = const NesBasicDialogFrame(),
}) {
  return NesDialog.show<String?>(
    context: context,
    builder: (_) => const CustomNesInputDialog(
      inputLabel: "  확인  ",
      message: "닉네임을 정해주세요 (2~6자)",
    ),
    frame: frame,
  );
}
