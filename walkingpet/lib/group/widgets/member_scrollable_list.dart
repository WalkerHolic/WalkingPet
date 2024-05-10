import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';

class MemberScrollableList extends StatelessWidget {
  final List<Map<String, dynamic>> groupMembers = [
    //캐릭터 아이디 : 동물 종류
    {'nickName': '팀원1', 'step': 10000},
    {'nickName': '팀원2', 'step': 5000},
    {'nickName': '팀원3', 'step': 2500},
    {'nickName': '팀원4', 'step': 1250},
    {'nickName': '팀원5', 'step': 625},
    {'nickName': '팀원6', 'step': 300},
  ];
  MemberScrollableList({super.key});

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    return Container(
      width: screenWidth * 0.9,
      height: screenHeight * 0.35,
      decoration: BoxDecoration(
        color: const Color(0xffe9c6a8),
        borderRadius: BorderRadius.circular(20),
      ),
      child: ListView.builder(
        //스크롤 가능한 리스트
        itemCount: groupMembers.length,
        itemBuilder: (context, index) {
          var member = groupMembers[index];
          return ListTile(
            title: Row(
              children: [
                Image.asset(
                  'assets/animals/red_dragon/red_dragon_idle.gif',
                  height: 70,
                ),
                Text(member['nickName'], style: const TextStyle(fontSize: 25)),
              ],
            ),
            trailing: Row(
              mainAxisSize: MainAxisSize.min, // 필요한 만큼의 최소 크기 사용
              children: [
                Text(
                  "${member['step']} ",
                  style: const TextStyle(
                      fontSize: 45, color: Color.fromARGB(255, 243, 48, 5)),
                ),
                const Text("걸음",
                    style: TextStyle(
                      fontSize: 20,
                    )),
              ],
            ),
          );
        },
      ),
    );
  }
}
