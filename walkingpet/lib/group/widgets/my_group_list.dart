import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:walkingpet/group/widgets/group_card.dart';
import 'package:walkingpet/group/widgets/create_group.dart';

class MyGroup extends StatefulWidget {
  final List<Map<String, dynamic>> myGroups;

  // 생성자
  const MyGroup({
    super.key,
    required this.myGroups,
  });

  @override
  State<MyGroup> createState() => _MyGroupState();
}

class _MyGroupState extends State<MyGroup> {
  @override
  Widget build(BuildContext context) {
    //스크린 크기 받아오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    final List<GroupCard> groupCards = widget.myGroups.map((group) {
      return GroupCard(
        groupId: group['teamId'],
        groupName: group['teamName'],
        description: group['teamExplain'],
        numOfMember: group['userCount'],
      );
    }).toList();
    return Container(
      child: Column(children: [
        Align(
          alignment: Alignment.centerRight,
          child: Padding(
            padding: EdgeInsets.all(screenHeight * 0.015),
            child: InkWell(
              onTap: () {
                Navigator.push(
                    context,
                    MaterialPageRoute(
                        builder: (context) => const CreateGroup()));
              },
              child: SvgPicture.asset(
                'assets/buttons/group_join_button.svg',
                width: screenWidth * 0.3,
              ),
            ),
          ),
        ),
        ...groupCards,
      ]),
      // child: const Center(
      //   child: Text("공사 중입니다."),
      // ),
    );
  }
}
