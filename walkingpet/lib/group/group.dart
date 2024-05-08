import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:nes_ui/nes_ui.dart';
import 'package:walkingpet/common/bottom_nav_bar.dart';
import 'package:walkingpet/group/widgets/my_group_list.dart';
import 'package:walkingpet/group/widgets/search_group.dart';
import 'package:walkingpet/services/group/get_my_group.dart';

class Group extends StatefulWidget {
  const Group({super.key});

  @override
  State<Group> createState() => _GroupState();
}

class _GroupState extends State<Group> {
  // 그룹 데이터 담을 빈 배열
  List<Map<String, dynamic>> myGroups = [];
  // 로딩중인가?
  bool isLoding = true;

  @override
  void initState() {
    super.initState();
    _fetchGroups();
  }

  Future<void> _fetchGroups() async {
    try {
      final groups = await getMyGroup();
      setState(() {
        myGroups = groups;
        isLoding = false;
      });
    } catch (e) {
      setState(() {
        isLoding = false;
        // 에러 처리
        print('에러: $e');
      });
    }
  }

  @override
  Widget build(BuildContext context) {
    double screenWidth = MediaQuery.of(context).size.width;
    double screenHeight = MediaQuery.of(context).size.height;
    return Scaffold(
      body: Stack(
        children: <Widget>[
          Positioned.fill(
            child: Image.asset(
              'assets/backgrounds/group.png',
              fit: BoxFit.cover,
            ),
          ),
          Positioned.fill(
            child: Container(
              color: Colors.white.withOpacity(0.6),
            ),
          ),
          Center(
            child: Container(
              width: screenWidth * 0.9,
              height: screenHeight * 0.7,
              decoration: BoxDecoration(
                  color: const Color(0xffffe6b6).withOpacity(0.9),
                  borderRadius: BorderRadius.circular(20)),
              // child: const Column(
              //   children: [
              //     GroupCard(groupName: "야호"),
              //   ],
              // ), //컨테이너 안 child
            ),
          ),
          Positioned(
            top: screenHeight * 0.09,
            left: screenWidth * 0.05,
            child: Material(
              color: Colors.transparent,
              child: DefaultTextStyle(
                style: const TextStyle(
                  fontSize: 25,
                  color: Colors.black,
                  fontFamily: "DungGeunMo",
                ),
                child: SizedBox(
                  width: screenWidth * 0.9,
                  height: screenHeight * 0.73,
                  child: NesTabView(
                    tabs: [
                      NesTabItem(
                        child: MyGroup(myGroups: myGroups), //인자 넘겨주기
                        label: "내 그룹",
                      ),
                      const NesTabItem(
                        child: SearchGroup(),
                        label: "그룹 검색",
                      ),
                    ],
                  ),
                ),
              ),
            ),
          )
        ],
      ),
      bottomNavigationBar: const BottomNavBar(
        selectedIndex: 4,
      ),
    );
  }
}
