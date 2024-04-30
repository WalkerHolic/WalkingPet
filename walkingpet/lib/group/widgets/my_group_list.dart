import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:walkingpet/group/widgets/group_card.dart';

class MyGroup extends StatelessWidget {
  const MyGroup({super.key});

  @override
  Widget build(BuildContext context) {
    return Container(
      child: const Column(
        children: [
          // SvgPicture.asset('assets/buttons/create_team_button.svg'),
          GroupCard(
            groupName: "대전 2팀 모여라!",
            description: "SSAFY 대전 1반 그룹입니다.",
            numOfMember: 4,
          ),
          GroupCard(
            groupName: "워커홀릭 팀",
            description: "워킹펫 많은 관심 부탁드려요!",
            numOfMember: 5,
          ),
          GroupCard(
            groupName: "인당 그룹은",
            description: "최대 3개까지 가입 가능",
            numOfMember: 4,
          ),
        ],
      ),
    );
  }
}
