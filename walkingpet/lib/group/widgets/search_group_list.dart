import 'package:flutter/cupertino.dart';
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
    return SizedBox(
      height: screenHeight, //스크롤뷰 기본 크기 지정
      child: SingleChildScrollView(
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
                'assets/buttons/new_create_group.svg',
                width: screenWidth * 0.3,
              ),
            ),
          ),
        ),
        Row(
          //여기 textfield 추가하나끼 오류남
          children: [
            Expanded(
                //검색창 영역
                child: Padding(
              padding: EdgeInsets.only(
                  left: screenWidth * 0.05, right: screenWidth * 0.02),
              child: SizedBox(
                height: screenHeight * 0.09, //textFiled 높이 조정 sizedbox로 가능 
                child: TextField(
                  maxLength: 10,
                  decoration: InputDecoration(
                    labelText: "그룹 이름으로 검색하세요.",
                    fillColor: Colors.white.withOpacity(0.6),
                    filled: true,
                  ),
                ),
              ),
            )),
            Padding(
              padding: EdgeInsets.only(
                  bottom: screenHeight * 0.03, right: screenWidth * 0.05),
              child: Image.asset('assets/buttons/search_icon.png',
                  width: screenWidth * 0.12),
            )
          ],
        ),
        ...groupCards,
      ])
          // return Container(
          //   child: const Center(
          //     child: Text("공사 중입니다."),
          //   ),
          // );
          ),
    );
  }
}
