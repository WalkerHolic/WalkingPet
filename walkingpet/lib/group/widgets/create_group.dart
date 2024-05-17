import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:walkingpet/services/group/create_group_api.dart';
import 'package:walkingpet/group/group.dart'; // group으로 이동하기 위해
import 'package:walkingpet/home/widgets/mainfontstyle.dart'; //메인폰트스타일

class CreateGroup extends StatefulWidget {
  const CreateGroup({super.key});

  @override
  State<CreateGroup> createState() => _CreateGroupState();
}

class _CreateGroupState extends State<CreateGroup> {
  bool isPrivate = false; //그룹 비공개 여부를 저장할 변수
  final TextEditingController _teamNameController = TextEditingController();
  final TextEditingController _explanationController = TextEditingController();
  final TextEditingController _passwordController = TextEditingController();
  final TextEditingController _confirmPasswordController =
      TextEditingController();
  @override
  void dispose() {
    // 메모리 누수 관리
    _teamNameController.dispose();
    _explanationController.dispose();
    _passwordController.dispose();
    _confirmPasswordController.dispose();
    super.dispose();
  }

  Future<void> _createGroup() async {
    final teamName = _teamNameController.text;
    final explanation = _explanationController.text;
    final password = isPrivate ? _passwordController.text : '';

    if (isPrivate && password != _confirmPasswordController.text) {
      print("비밀번호가 일치하지 않습니다.");
      return;
    }
    await createGroupService(teamName, explanation, password); //그룹 생성이 완료되면
    Navigator.of(context).pushReplacement(
        MaterialPageRoute(builder: (context) => const Group()));
  }

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    return Scaffold(
      body: Stack(
        children: [
          Positioned.fill(
            // 마을 배경
            child: Image.asset(
              'assets/backgrounds/group.png',
              fit: BoxFit.cover,
            ),
          ),
          Positioned.fill(
            // 흰색 불투명 레이어
            child: Container(
              color: Colors.white.withOpacity(0.6),
            ),
          ),
          Align(
            alignment: const Alignment(0, -0.6),
            // 내용 영역
            child: Container(
              width: screenWidth * 0.9,
              height: screenHeight * 0.7,
              decoration: BoxDecoration(
                  color: const Color(0xffffe6b6).withOpacity(0.9),
                  borderRadius: BorderRadius.circular(20)),
              //살구색 불투명 영역의 child로 기타 요소 (text, input...들어감)
              child: Padding(
                //살구색 영역 패딩
                padding: EdgeInsets.symmetric(horizontal: screenWidth * 0.05),
                child: Column(
                  children: [
                    SizedBox(height: screenHeight * 0.02),
                    const MainFontStyle(size: 32, text: "그룹 만들기"),
                    Padding(
                      padding: EdgeInsets.only(
                        top: screenHeight * 0.02,
                      ),
                      child: SizedBox(
                        width: screenWidth * 0.6,
                        height: screenHeight * 0.09,
                        // height: screenHeight * 0.6,
                        child: TextField(
                          controller: _teamNameController,
                          //그룹 이름
                          maxLength: 10, //입력 길이 제한 :10자
                          decoration: InputDecoration(
                            labelText: '그룹 이름',
                            fillColor: Colors.white.withOpacity(0.7),
                            filled: true,
                            counterStyle: const TextStyle(fontSize: 15),
                          ),
                        ),
                      ),
                    ),
                    Padding(
                      padding: EdgeInsets.only(
                        top: screenHeight * 0.02,
                      ),
                      child: SizedBox(
                        width: screenWidth * 0.7,
                        height: screenHeight * 0.09,
                        child: TextField(
                          controller: _explanationController,
                          //그룹 이름
                          maxLength: 20, //입력 길이 제한 :10자
                          minLines: 2,
                          maxLines: 3, // 칸 세로로 확장
                          decoration: InputDecoration(
                            labelText: '한줄 소개',
                            fillColor: Colors.white.withOpacity(0.7),
                            filled: true,
                            counterStyle: const TextStyle(fontSize: 15),
                          ),
                        ),
                      ),
                    ),
                    SwitchListTile(
                      title:
                          const Text('비공개 여부', style: TextStyle(fontSize: 20)),
                      value: isPrivate,
                      onChanged: (bool value) {
                        setState(() {
                          isPrivate = value;
                        });
                      },
                      activeColor: Colors.white, //활성화 상태일 때 스위치 색깔
                      activeTrackColor: const Color.fromARGB(
                          255, 44, 215, 41), //활성화 상태일 때 라인 색상
                      inactiveThumbColor: Colors.grey,
                      inactiveTrackColor: Colors.red,
                    ),
                    Visibility(
                      visible: isPrivate,
                      child: SizedBox(
                        width: screenWidth * 0.4,
                        height: screenHeight * 0.06,
                        child: TextField(
                          controller: _passwordController,
                          decoration: InputDecoration(
                            filled: true,
                            fillColor: Colors.white.withOpacity(0.7),
                            labelText: "비밀번호",
                          ),
                          obscureText: true,
                        ),
                      ),
                    ),
                    // 비밀번호 <-> 비밀번호 확인 사이 공간
                    SizedBox(height: screenHeight * 0.02),
                    Visibility(
                      visible: isPrivate,
                      child: SizedBox(
                        width: screenWidth * 0.4,
                        height: screenHeight * 0.06,
                        child: TextField(
                          controller: _confirmPasswordController,
                          decoration: InputDecoration(
                            filled: true,
                            fillColor: Colors.white.withOpacity(0.7),
                            labelText: "비밀번호 확인",
                          ),
                          obscureText: true,
                        ),
                      ),
                    ),
                    SizedBox(
                      height: screenHeight * 0.01,
                    ),
                    GestureDetector(
                      onTap: _createGroup, //  버튼 클릭시 그룹 생성 함수
                      child: SvgPicture.asset(
                        'assets/buttons/create_button.svg',
                        height: screenHeight * 0.04,
                      ),
                    )
                  ],
                ),
              ),
            ),
          ),
        ],
      ),
    );
  }
}
