import 'package:flutter/material.dart';
import 'package:walkingpet/group/widgets/search_group_card.dart';
import 'package:walkingpet/group/widgets/create_group.dart';
import 'package:flutter_svg/flutter_svg.dart';

class SearchGroup extends StatefulWidget {
  final List<Map<String, dynamic>> incommingAllGroups;

  const SearchGroup({
    super.key,
    required this.incommingAllGroups,
  });

  @override
  State<SearchGroup> createState() => _SearchGroupState();
}

class _SearchGroupState extends State<SearchGroup> {
  @override
  Widget build(BuildContext context) {
    //스크린 크기 받아오기
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;

    final List<SearchGroupCard> groupCards =
        widget.incommingAllGroups.map((group) {
      return SearchGroupCard(
          groupId: group['teamId'],
          groupName: group['teamName'],
          description: group['teamExplain'],
          numOfMember: group['userCount'],
          isProtected: group['hasPassword']);
    }).toList();
    return SingleChildScrollView(
        child: Column(children: [
      Align(
        alignment: Alignment.centerRight,
        child: Padding(
          padding: EdgeInsets.all(screenHeight * 0.015),
          child: InkWell(
            onTap: () {
              Navigator.push(context,
                  MaterialPageRoute(builder: (context) => const CreateGroup()));
            },
            child: SvgPicture.asset(
              'assets/buttons/group_join_button.svg',
              width: screenWidth * 0.3,
            ),
          ),
        ),
      ),
      ...groupCards,
    ])
        // return Container(
        //   child: const Center(
        //     child: Text("공사 중입니다."),
        //   ),
        // );
        );
  }
}
