import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:walkingpet/group/widgets/group_card.dart';

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
    final List<GroupCard> groupCards = widget.myGroups.map((group) {
      return GroupCard(
        groupName: group['teamName'],
        description: group['explain'],
        numOfMember: group['userCount'],
      );
    }).toList();
    return Container(
      // child: Column(
      //   children: groupCards,
      // ),
      child: const Center(
        child: Text("공사 중입니다."),
      ),
    );
  }
}
