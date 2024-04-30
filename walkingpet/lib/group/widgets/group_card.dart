import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:walkingpet/group/widgets/group_detail.dart';

class GroupCard extends StatelessWidget {
  final String groupName;
  final String description; // 그룹 한줄 설명
  final int numOfMember; //그룹 내 멤버 수

  const GroupCard({
    super.key,
    required this.groupName,
    required this.description,
    required this.numOfMember,
  });

  @override
  Widget build(BuildContext context) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.symmetric(vertical: 10),
        child: Card(
          elevation: 3,
          shadowColor: const Color.fromARGB(238, 95, 31, 2),
          color: const Color.fromARGB(255, 255, 207, 135),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(20),
          ),
          child: ConstrainedBox(
            constraints: const BoxConstraints(
              minWidth: 300,
              maxWidth: 300,
              minHeight: 130,
              maxHeight: 130,
            ),
            child: Padding(
              padding: const EdgeInsets.symmetric(
                horizontal: 20,
                vertical: 15,
              ),
              child: Column(
                children: [
                  Text(
                    groupName,
                    style: const TextStyle(
                      fontSize: 22,
                      fontWeight: FontWeight.w500,
                    ),
                  ),
                  const SizedBox(
                    height: 5,
                  ),
                  Text(
                    description,
                    style: TextStyle(
                      fontSize: 18,
                      backgroundColor: Colors.white.withOpacity(0.6),
                    ),
                  ),
                  const SizedBox(
                    height: 5,
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.spaceAround,
                    children: [
                      Text(
                        '멤버: $numOfMember/6',
                        style: const TextStyle(
                          fontSize: 23,
                          color: Color.fromARGB(255, 88, 37, 19),
                        ),
                      ),
                      GestureDetector(
                        //상세페이지로 이동
                        onTap: () {
                          Navigator.push(
                            context,
                            MaterialPageRoute(
                              builder: (context) =>
                                  const GroupDetail(groupName: "아무그룹"),
                            ),
                          );
                        },
                        child: SvgPicture.asset(
                          'assets/buttons/enter_button.svg',
                          height: 30,
                        ),
                      )
                    ],
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }
}
